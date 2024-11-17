package com.projet_iwa.ms_reservation.service;


import com.projet_iwa.ms_reservation.Util;
import com.projet_iwa.ms_reservation.dto.LocationDTO;
import com.projet_iwa.ms_reservation.dto.ReservationDTO;
import com.projet_iwa.ms_reservation.exceptions.InvalidReservationException;
import com.projet_iwa.ms_reservation.exceptions.ReservationConflictException;
import com.projet_iwa.ms_reservation.model.Reservation;
import com.projet_iwa.ms_reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static com.projet_iwa.ms_reservation.Util.datesOverlap;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.gateway.url}")
    private String apiGatewayUrl;

    // Get a reservation by ID
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }
    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }
    public List<Reservation> getReservationByTravelerId(Long id){
        return reservationRepository.findByIdTraveler(id);
    }
    public List<Reservation> getReservationByLocationId(Long id){
        return reservationRepository.findByIdLocation(id);
    }



    public ReservationDTO getFullReservation(String authorizationHeader, Long reservationId) {
        System.out.println("Début de la récupération de la réservation avec l'ID " + reservationId);

        // Récupérer la réservation
        Optional<Reservation> reservationOptional = getReservationById(reservationId);
        if (reservationOptional.isEmpty()) {
            System.out.println("Aucune réservation trouvée avec l'ID " + reservationId);
            throw new IllegalArgumentException("La réservation avec l'ID " + reservationId + " n'existe pas.");
        }

        Reservation reservation = reservationOptional.get();
        System.out.println("Réservation récupérée : " + reservation);

        // Récupérer la location associée à la réservation en utilisant le JWT
        String jwtToken = Util.extractJwtFromHeader(authorizationHeader);
        System.out.println("JWT extrait : " + jwtToken);

        String locationUrl = apiGatewayUrl + "/locations/" + reservation.getIdLocation();
        System.out.println("URL pour récupérer la location : " + locationUrl);

        // Utilisation de la méthode utilitaire pour envoyer la requête avec le JWT
        ResponseEntity<LocationDTO> response = Util.sendRequestWithJwt(locationUrl, HttpMethod.GET, jwtToken, LocationDTO.class, null);

        // Vérification de la réponse
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            System.out.println("La réponse n'est pas valide pour la location avec l'ID " + reservation.getIdLocation());
            throw new IllegalArgumentException("La location avec l'ID " + reservation.getIdLocation() + " n'existe pas.");
        }

        System.out.println("Location récupérée : " + response.getBody());

        // Créer le DTO final qui combine les données de la réservation et de la location
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setIdLocation(reservation.getIdLocation());
        reservationDTO.setIdTraveler(reservation.getIdTraveler());
        reservationDTO.setDateStart(reservation.getDateStart().toString());  // Formater selon vos besoins
        reservationDTO.setDateEnd(reservation.getDateEnd().toString());  // Formater selon vos besoins
        reservationDTO.setStatus(reservation.getStatus());
        reservationDTO.setLocation(response.getBody());  // Inclure la location dans le DTO

        System.out.println("ReservationDTO créé : " + reservationDTO);

        return reservationDTO;
    }




    public Reservation createReservation(Reservation reservation, Long userId) {
        // Validation des dates
        if (reservation.getDateStart() == null || reservation.getDateEnd() == null) {
            throw new InvalidReservationException("Les dates de début et de fin sont obligatoires.");
        }
        if (reservation.getDateEnd().isBefore(reservation.getDateStart())) {
            throw new InvalidReservationException("La date de fin doit être après la date de début.");
        }

        // Vérification de disponibilité
        List<Reservation> existingReservations = reservationRepository.findByIdLocation(reservation.getIdLocation());
        boolean isAvailable = existingReservations.stream()
                .noneMatch(existingReservation -> datesOverlap(existingReservation, reservation));

        if (!isAvailable) {
            throw new ReservationConflictException("Les dates se chevauchent avec une réservation existante.");
        }

        // Assignation de l'utilisateur
        reservation.setIdTraveler(userId);

        // Sauvegarde
        return reservationRepository.save(reservation);
    }

    // Update an existing reservation
    public Optional<Reservation> updateReservation(Long id, Reservation reservationDetails) {
        return reservationRepository.findById(id).map(existingReservation -> {
            existingReservation.setStatus(reservationDetails.getStatus());
            return reservationRepository.save(existingReservation);
        });
    }

    // Delete a reservation
    public void deleteReservation(Long id) {
        Optional<Reservation> location = reservationRepository.findById(id);
        if (location.isEmpty()) {
            throw new IllegalArgumentException("La réservation avec l'ID " + id + " n'existe pas.");
        }
        reservationRepository.deleteById(id);
    }
}

