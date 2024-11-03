package com.bessy.videoservice.controller;

import com.bessy.videoservice.exc.NotFoundException;
import com.bessy.videoservice.model.dto.NewVideoRepresentation;
import com.bessy.videoservice.model.dto.VideoMetadataRepresentation;
import com.bessy.videoservice.service.StreamBytesInfo;
import com.bessy.videoservice.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;

@RestController
@RequestMapping("/v1/video")
@Slf4j
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String VIDEO_UPLOAD_TOPIC = "video-uploads";

    @GetMapping("/all")
   // @CrossOrigin
    private List<VideoMetadataRepresentation> findAllVideoMetadata() {
        return videoService.findAllRepresentation();
    }


    @GetMapping("/{id}")
   // @CrossOrigin
    public VideoMetadataRepresentation findVideoMetadataById(@PathVariable("id") Long id) {
        return videoService.findRepresentationById(id).orElseThrow(NotFoundException::new);
    }

    @GetMapping(value = "/preview/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
  //  @CrossOrigin
    public ResponseEntity<StreamingResponseBody> getPreviewPicture(@PathVariable("id") Long id) {
        InputStream inputStream = videoService.getPreviewInputStream(id)
                .orElseThrow(NotFoundException::new);

        return ResponseEntity.ok(inputStream::transferTo);
    }

    @GetMapping("/stream/{id}")
    public ResponseEntity<StreamingResponseBody> streamVideo(
            @RequestHeader(value = "range", required = false) String httpRangeHeader,
            @PathVariable("id") Long id) {

        log.info("Requested range [{}] for file `{}`", httpRangeHeader, id);

        List<HttpRange> httpRangeList = HttpRange.parseRanges(httpRangeHeader);

        // Get streaming information for the specific byte range
        StreamBytesInfo streamBytesInfo = videoService
                .getStreamBytes(id, !httpRangeList.isEmpty() ? httpRangeList.get(0) : null)
                .orElseThrow(NotFoundException::new);

        long byteLength = streamBytesInfo.getRangeEnd() - streamBytesInfo.getRangeStart() + 1;

        ResponseEntity.BodyBuilder builder = ResponseEntity
                .status(!httpRangeList.isEmpty() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK)
                .header("Content-Type", streamBytesInfo.getContentType())
                .header("Accept-Ranges", "bytes")
                .header("Cache-Control", "public, max-age=3600")  // Cache for 1 hour, adjust as needed
                .header("Connection", "keep-alive");

        if (!httpRangeList.isEmpty()) {
            builder.header("Content-Range",
                    "bytes " + streamBytesInfo.getRangeStart() + "-" + streamBytesInfo.getRangeEnd() + "/" + streamBytesInfo.getFileSize());
        }

        // Log information for debugging purposes
        log.info("Providing bytes from {} to {}. We are at {}% of overall video.",
                streamBytesInfo.getRangeStart(),
                streamBytesInfo.getRangeEnd(),
                new DecimalFormat("###.##")
                        .format(100.0 * streamBytesInfo.getRangeStart() / streamBytesInfo.getFileSize()));

        // Return the stream response with appropriate headers
        return builder.body(streamBytesInfo.getResponseBody());
    }


    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  //  @CrossOrigin
    public ResponseEntity<Void> uploadVideo(NewVideoRepresentation newVideoRepresentation) {
        log.info(newVideoRepresentation.getDescription());
        try {
            videoService.saveNewVideo(newVideoRepresentation);
            log.info("Video uploaded successfully.");
            String message = String.format("New video %s was recently uploaded",  newVideoRepresentation.getDescription());
            kafkaTemplate.send(VIDEO_UPLOAD_TOPIC, message);
        } catch (Exception ex) {
            log.error("Error uploading video: {}", ex.getMessage());
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}
