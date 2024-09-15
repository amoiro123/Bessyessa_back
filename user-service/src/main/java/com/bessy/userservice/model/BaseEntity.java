package com.bessy.userservice.model;


import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;

    @CreationTimestamp
    private LocalDateTime creationTimestamp;

    @UpdateTimestamp
    private LocalDateTime updateTimestamp;
}
