package com.projet_iwa.ms_location.service;

import com.projet_iwa.ms_location.dto.LocationDTO;
import com.projet_iwa.ms_location.dto.LocationDTOFull;
import com.projet_iwa.ms_location.dto.UserDTO;
import com.projet_iwa.ms_location.model.Category;
import com.projet_iwa.ms_location.model.Location;
import com.projet_iwa.ms_location.model.Reservation;
import com.projet_iwa.ms_location.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.projet_iwa.ms_location.repository.LocationRepository;
import org.springframework.web.client.RestTemplate;
import com.projet_iwa.ms_location.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RestTemplate restTemplate;


    @Value("${api.gateway.url}")
    private String apiGatewayUrl;


    public List<LocationDTO> getAllLocations(String authorizationHeader) {
        List<Location> locations = locationRepository.findAll();
        return locations.stream().map(location -> {

            // Extraire le JWT du header
            String jwtToken = Util.extractJwtFromHeader(authorizationHeader);
            String userUrl = apiGatewayUrl + "/users/" + location.getIdUser();

            // Utiliser sendRequestWithJwt pour envoyer la requête avec le JWT
            ResponseEntity<UserDTO> response = Util.sendRequestWithJwt(userUrl, HttpMethod.GET, jwtToken, UserDTO.class, null);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new IllegalArgumentException("L'utilisateur avec l'ID " + location.getIdUser() + " n'existe pas.");
            }

            UserDTO userDTO = response.getBody();
            Category category = categoryService.getCategoryById(location.getIdCategory());

            return new LocationDTO(
                    location.getId(),
                    location.getTitle(),
                    location.getDescription(),
                    location.getAddress(),
                    location.isActive(),
                    location.getImageUrl(),
                    userDTO,
                    category
            );
        }).collect(Collectors.toList());
    }

    public List<LocationDTO> getLocationByUserId(String authorizationHeader, Long userId) {
        List<Location> locations = locationRepository.findByIdUser(userId);

        return locations.stream().map(location -> {
            // Extraire le JWT
            String jwtToken = Util.extractJwtFromHeader(authorizationHeader);
            String userUrl = apiGatewayUrl + "/users/" + location.getIdUser();

            // Utiliser sendRequestWithJwt pour envoyer la requête avec le JWT
            ResponseEntity<UserDTO> response = Util.sendRequestWithJwt(userUrl, HttpMethod.GET, jwtToken, UserDTO.class, null);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new IllegalArgumentException("L'utilisateur avec l'ID " + location.getIdUser() + " n'existe pas.");
            }

            UserDTO userDTO = response.getBody();
            Category category = categoryService.getCategoryById(location.getIdCategory());

            return new LocationDTO(
                    location.getId(),
                    location.getTitle(),
                    location.getDescription(),
                    location.getAddress(),
                    location.isActive(),
                    location.getImageUrl(),
                    userDTO,
                    category
            );
        }).collect(Collectors.toList());
    }

    public Location getSimpleLocationById(String authorizationHeader,Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    public LocationDTOFull getLocationById(String authorizationHeader, Long id) {
        Location location = locationRepository.findById(id).orElse(null);

        if (location == null) {
            throw new IllegalArgumentException("La location avec l'ID " + id + " n'existe pas.");
        }

        // Extraire le JWT
        String jwtToken = Util.extractJwtFromHeader(authorizationHeader);
        String userUrl = apiGatewayUrl + "/users/" + location.getIdUser();

        // Utiliser sendRequestWithJwt pour envoyer la requête avec le JWT
        ResponseEntity<UserDTO> response = Util.sendRequestWithJwt(userUrl, HttpMethod.GET, jwtToken, UserDTO.class, null);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new IllegalArgumentException("L'utilisateur avec l'ID " + location.getIdUser() + " n'existe pas.");
        }

        UserDTO userDTO = response.getBody();
        Category category = categoryService.getCategoryById(location.getIdCategory());

        // Récupérer les réservations pour cette location
        String reservationUrl = apiGatewayUrl + "/reservations/all/location/" + id;
        ResponseEntity<List<Reservation>> responseReservation = Util.sendRequestWithJwt1(reservationUrl, HttpMethod.GET, jwtToken, new ParameterizedTypeReference<List<Reservation>>() {}, null);

        if (!responseReservation.getStatusCode().is2xxSuccessful() || responseReservation.getBody() == null) {
            throw new IllegalArgumentException("Aucune réservation trouvé pour la location avec l'ID " + id);
        }

        // Ajouter les dates de réservation dans la liste des dates bloquées
        List<Date> blockedDates = new ArrayList<>();
        for (Reservation reservation : responseReservation.getBody()) {
            blockedDates.addAll(Util.getDatesBetween(reservation.getDateStart(), reservation.getDateEnd()));
        }

        return new LocationDTOFull(
                location.getId(),
                location.getTitle(),
                location.getDescription(),
                location.getAddress(),
                location.isActive(),
                userDTO,
                category,
                location.getImageUrl(),
                blockedDates
        );
    }


    public Location saveLocation(String authorizationHeader,Location location) {

        // Vérification de l'existence du user
        String jwtToken = Util.extractJwtFromHeader(authorizationHeader);
        String userUrl = apiGatewayUrl + "/users/" + location.getIdUser();
        ResponseEntity<UserDTO> response = Util.sendRequestWithJwt(userUrl, HttpMethod.GET, jwtToken, UserDTO.class, null);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new IllegalArgumentException("L'utilisateur avec l'ID " + location.getIdUser() + " n'existe pas.");
        }

        // Vérification de l'existence de la catégorie avant de créer la location
        Optional<Category> category = categoryRepository.findById(location.getIdCategory());
        if (category.isEmpty()) {
            throw new IllegalArgumentException("La catégorie avec l'ID " + location.getIdCategory() + " n'existe pas.");
        }
        return locationRepository.save(location);
    }

    public void deleteLocation(String authorizationHeader,Long id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isEmpty()) {
            throw new IllegalArgumentException("La location avec l'ID " + id + " n'existe pas.");
        }
        locationRepository.deleteById(id);
    }

    // Méthode pour mettre à jour une location
    public Location updateLocation(String authorizationHeader,Long id, Location location) {
        Optional<Location> existingLocation = locationRepository.findById(id);
        if (existingLocation.isEmpty()) {
            throw new IllegalArgumentException("L'emplacement avec l'ID " + id + " n'existe pas.");
        }

        // Vérification de l'existence de la catégorie
        Optional<Category> category = categoryRepository.findById(location.getIdCategory());
        if (category.isEmpty()) {
            throw new IllegalArgumentException("La catégorie avec l'ID " + location.getIdCategory() + " n'existe pas.");
        }

        // Mise à jour des champs de la location
        Location loc = existingLocation.get();
        loc.setTitle(location.getTitle());
        loc.setDescription(location.getDescription());
        loc.setAddress(location.getAddress());
        loc.setActive(location.isActive());
        loc.setIdCategory(location.getIdCategory());
        loc.setImageUrl(location.getImageUrl());

        return locationRepository.save(loc);
    }


}
