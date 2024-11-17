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


    public List<LocationDTO> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream().map(location -> {

            String userUrl = apiGatewayUrl + "/users/" + location.getIdUser();
            ResponseEntity<UserDTO> response = restTemplate.exchange(userUrl, HttpMethod.GET, null, UserDTO.class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new IllegalArgumentException("L'utilisateur avec l'ID " + location.getIdUser() + " n'existe pas.");
            }

            UserDTO userDTO = response.getBody();
            Category category = categoryService.getCategoryById(location.getIdCategory()); // Récupérer la catégorie

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
    public LocationDTOFull getLocationById(Long id) {
        Location location = locationRepository.findById(id).orElse(null);

        if (location == null) {
            throw new IllegalArgumentException("La location avec l'ID " + id + " n'existe pas.");
        }

        String userUrl = apiGatewayUrl + "/users/" + location.getIdUser();
        ResponseEntity<UserDTO> response = restTemplate.exchange(userUrl, HttpMethod.GET, null, UserDTO.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new IllegalArgumentException("L'utilisateur avec l'ID " + location.getIdUser() + " n'existe pas.");
        }

        UserDTO userDTO = response.getBody();
        Category category = categoryService.getCategoryById(location.getIdCategory());

        // récupérer toutes les réservations pour cet idlocation
        String reservationUrl = apiGatewayUrl + "/reservations/all/location/" + id;
        ResponseEntity<List<Reservation>> responseReservation = restTemplate.exchange(reservationUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Reservation>>() {});

        if (!responseReservation.getStatusCode().is2xxSuccessful() || responseReservation.getBody() == null) {
            throw new IllegalArgumentException("Aucune réservation trouvé pour la location avec l'ID " + id );
        }
        // pour chaque, récupérer les start_end, les transformer en liste
        // Liste des dates bloquées de la base de données
        List<Date> blockedDates = new ArrayList<>();

        // Ajouter les dates des réservations existantes
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
    public Location getSimpleLocationById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    public List<LocationDTO> getLocationByUserId(Long userId) {
        List<Location> locations = locationRepository.findByIdUser(userId);

        return locations.stream().map(location -> {
            String userUrl = apiGatewayUrl + "/users/" + location.getIdUser();
            ResponseEntity<UserDTO> response = restTemplate.exchange(userUrl, HttpMethod.GET, null, UserDTO.class);

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new IllegalArgumentException("L'utilisateur avec l'ID " + location.getIdUser() + " n'existe pas.");
            }

            UserDTO userDTO = response.getBody();
            Category category = categoryService.getCategoryById(location.getIdCategory()); // Récupérer la catégorie

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

    public Location saveLocation(Location location) {

        String userUrl = apiGatewayUrl + "/users/" + location.getIdUser();
        ResponseEntity<UserDTO> response = restTemplate.exchange(userUrl, HttpMethod.GET, null, UserDTO.class);

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
    public void deleteLocation(Long id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isEmpty()) {
            throw new IllegalArgumentException("La location avec l'ID " + id + " n'existe pas.");
        }
        locationRepository.deleteById(id);
    }

    // Méthode pour mettre à jour une location
    public Location updateLocation(Long id, Location location) {
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
