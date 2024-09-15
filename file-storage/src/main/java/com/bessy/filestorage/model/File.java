package com.bessy.filestorage.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "files")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String type;
    private String filePath;
}