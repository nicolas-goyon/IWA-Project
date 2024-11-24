package com.projet_iwa.ms_reservation.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idlocation", nullable = false)
    private Long idLocation;

    @Column(name = "idtraveler", nullable = false)
    private Long idTraveler;

    @Column(name = "date_start", nullable = false)
    private LocalDate dateStart;

    @Column(name = "date_end", nullable = false)
    private LocalDate dateEnd;

    // Nouveau champ "status" de type ENUM
    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.PENDING;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Long idLocation) {
        this.idLocation = idLocation;
    }

    public Long getIdTraveler() {
        return idTraveler;
    }

    public void setIdTraveler(Long idTraveler) {
        this.idTraveler = idTraveler;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    // Méthode calculée pour vérifier si la réservation est expirée
    public boolean isExpired() {
        return LocalDate.now().isAfter(this.dateEnd);
    }
}
