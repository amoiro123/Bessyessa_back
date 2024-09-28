package com.bessy.productservice.controller;

import com.bessy.productservice.dto.BrandDTO;
import com.bessy.productservice.dto.ReservationDTO;
import com.bessy.productservice.mappers.ReservationMapper;
import com.bessy.productservice.model.Reservation;
import com.bessy.productservice.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/product/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final ObjectMapper objectMapper;

    // Create or Update a Reservation
    @PostMapping
    public ResponseEntity<ReservationDTO> createOrUpdateReservation(@RequestBody ReservationDTO dto) {
        Reservation savedReservation = reservationService.saveReservation(objectMapper.convertValue(dto, Reservation.class));
        return new ResponseEntity<>(objectMapper.convertValue(savedReservation, ReservationDTO.class), HttpStatus.CREATED);
    }

    // Get a Reservation by ID
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable UUID id) {
        Reservation reservation = reservationService.getReservationById(id);
        return new ResponseEntity<>(objectMapper.convertValue(reservation, ReservationDTO.class), HttpStatus.OK);
    }

    // Get all Reservations
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return new ResponseEntity<>(reservations.stream().map(reservation -> objectMapper.convertValue(reservation, ReservationDTO.class)).toList(), HttpStatus.OK);
    }

    // Delete a Reservation by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable UUID id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
