package com.projet_iwa.ms_reservation.controller;


import com.projet_iwa.ms_reservation.dto.ReservationDTO;
import com.projet_iwa.ms_reservation.model.Reservation;
import com.projet_iwa.ms_reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/all")
    public List<Reservation> getAllReservation() {
        return reservationService.getAllReservation();
    }
    @GetMapping("/all/host/{id}")
    public List<Reservation> getReservationByHostId(@RequestHeader("Authorization") String authorizationHeader,@PathVariable Long id) {
        return reservationService.getReservationByHostId(authorizationHeader,id);
    }
    @GetMapping("/all/{id}")
    public List<Reservation> getReservationByTravelerId(@PathVariable Long id) {
        return reservationService.getReservationByTravelerId(id);
    }

    @GetMapping("/all/location/{id}")
    public List<Reservation> getReservationByLocationId(@PathVariable Long id) {
        return reservationService.getReservationByLocationId(id);
    }

    @GetMapping("/full/{id}")
    public ReservationDTO getFullReservation(@RequestHeader("Authorization") String authorizationHeader,@PathVariable Long id) {
        return reservationService.getFullReservation(authorizationHeader,id);
    }

    @PostMapping
    public ResponseEntity<String> createReservation(
            @RequestBody Reservation reservation,
            @RequestHeader("X-User-Id") Long userId) {
        reservationService.createReservation(reservation, userId);
        // Sauvegarde ou logique métier ici
        return ResponseEntity.ok("Réservation créée pour l'utilisateur : " + userId);
    }

    // Update an existing reservation (for host acceptation)
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        try {
            reservationService.updateReservation(id, reservation);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReservation(@PathVariable Long id) {
        try {
            reservationService.deleteReservation(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
