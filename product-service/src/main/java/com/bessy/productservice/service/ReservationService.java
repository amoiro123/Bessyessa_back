package com.bessy.productservice.service;

import com.bessy.productservice.model.Reservation;
import com.bessy.productservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // Create or Update a Reservation
    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // Get a Reservation by ID
    public Reservation getReservationById(UUID id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id " + id));
    }

    // Get all Reservations
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Delete a Reservation by ID
    public void deleteReservation(UUID id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with id " + id));
        reservationRepository.delete(reservation);
    }
}
