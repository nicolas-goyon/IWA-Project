package com.projet_iwa.ms_reservation.dto;

import com.projet_iwa.ms_reservation.model.ReservationStatus;

public class ReservationDTO {

    private Long id;
    private Long idLocation;
    private Long idTraveler;
    private String dateStart;
    private String dateEnd;
    private ReservationStatus status;  // Si vous avez un enum pour le statut
    private LocationDTO location; // Objet LocationDTO pour les informations de location

    // Getters et setters
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

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }
}



