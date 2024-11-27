package com.projet_iwa.ms_reservation.service;


import com.projet_iwa.ms_reservation.Util;
import com.projet_iwa.ms_reservation.dto.LocationDTO;
import com.projet_iwa.ms_reservation.dto.Notification;
import com.projet_iwa.ms_reservation.dto.ReservationDTO;
import com.projet_iwa.ms_reservation.exceptions.InvalidReservationException;
import com.projet_iwa.ms_reservation.exceptions.ReservationConflictException;
import com.projet_iwa.ms_reservation.model.Reservation;
import com.projet_iwa.ms_reservation.model.ReservationStatus;
import com.projet_iwa.ms_reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Transactional
    public Optional<Reservation> getReservationById(Long id) {
        reservationRepository.updateExpiredReservations();
        return reservationRepository.findById(id);

    }
    @Transactional
    public List<Reservation> getAllReservation() {
        reservationRepository.updateExpiredReservations();
        return reservationRepository.findAll();
    }
    @Transactional
    public List<Reservation> getReservationByTravelerId(Long id){
        reservationRepository.updateExpiredReservations();
        return reservationRepository.findByIdTraveler(id);
    }
    @Transactional
    public List<Reservation> getReservationByLocationId(Long id) {
        // Met à jour les réservations expirées
        reservationRepository.updateExpiredReservations();

        // Récupère les réservations par ID de location et filtre celles ayant un statut REJECTED
        return reservationRepository.findByIdLocation(id).stream()
                .filter(reservation -> !reservation.getStatus().equals(ReservationStatus.REJECTED)) // Filtre les réservations rejetées
                .collect(Collectors.toList());
    }


    @Transactional
    public List<Reservation> getReservationByHostId(String authorizationHeader,Long id){
        reservationRepository.updateExpiredReservations();
        // Récupérer les id de location associée au host
        String jwtToken = Util.extractJwtFromHeader(authorizationHeader);

        String locationUrl = apiGatewayUrl + "/locations/user/" + id;
        ResponseEntity<List<LocationDTO>> response = Util.sendRequestWithJwt1(locationUrl, HttpMethod.GET, jwtToken, new ParameterizedTypeReference<List<LocationDTO>>() {}, null);

        // Vérification de la réponse
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new IllegalArgumentException("Aucune location avec l'ID host : " + id + " a été trouvé.");
        }
        // map pour récupérer que les id pour chaque location
        // Extract location IDs from the response
        List<Long> locationIds = response.getBody().stream()
                .map(LocationDTO::getId) // Assuming LocationDTO has a method getId()
                .toList();

        // Create an empty list to store all reservations
        List<Reservation> allReservations = new ArrayList<>();

        // Fetch reservations for each location ID
        for (Long locationId : locationIds) {
            // Use the method to fetch reservations by location ID
            List<Reservation> reservations = reservationRepository.findByIdLocation(locationId);
            if (reservations != null && !reservations.isEmpty()) {
                allReservations.addAll(reservations);
            }
        }

        // Return the consolidated list of reservations
        return allReservations;
    }


    @Transactional
    public ReservationDTO getFullReservation(String authorizationHeader, Long reservationId) {
        reservationRepository.updateExpiredReservations();
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




    public Reservation createReservation(String authorization,Reservation reservation, Long userId) {
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
                .filter(existingReservation -> !existingReservation.getStatus().equals(ReservationStatus.REJECTED)) // Filtre les réservations rejetées
                .noneMatch(existingReservation -> datesOverlap(existingReservation, reservation));

        if (!isAvailable) {
            throw new ReservationConflictException("Les dates se chevauchent avec une réservation existante.");
        }

        // Assignation de l'utilisateur
        reservation.setIdTraveler(userId);

        // Sauvegarde
        Reservation reservationSaved = reservationRepository.save(reservation);
        ReservationDTO fullRev = getFullReservation(authorization, reservationSaved.getId());
        System.out.println(fullRev.getLocation().getUser().getIduser());

        Notification notif = new Notification();
        notif.setIduser(fullRev.getLocation().getUser().getIduser());
        notif.setBody("Vous avez une nouvelle demande pour le bien suivant: "+ fullRev.getLocation().getTitle());
        notif.setTitle("Demande de réservation");
        notif.setRedirection("reservation");
        Util.createNotification(authorization, notif);



        return reservationSaved;
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

