package com.projet_iwa.ms_location.service;

import com.projet_iwa.ms_location.model.Category;
import com.projet_iwa.ms_location.model.Location;
import com.projet_iwa.ms_location.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projet_iwa.ms_location.repository.LocationRepository;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    // Récupérer toutes les locations
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    // Récupérer une location par son ID
    public Location getLocationById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    // Enregistrer une nouvelle location ou mettre à jour une location existante
    public Location saveLocation(Location location) {

        // Vérification de l'existence de la catégorie avant de créer la location
        Optional<Category> category = categoryRepository.findById(location.getIdCategory());
        if (category.isEmpty()) {
            throw new IllegalArgumentException("La catégorie avec l'ID " + location.getIdCategory() + " n'existe pas.");
        }
        return locationRepository.save(location);
    }

    // Supprimer une location par son ID
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
    // Méthode pour récupérer toutes les locations par ID d'utilisateur
    public List<Location> getLocationsByUserId(Long idUser) {
        return locationRepository.findByIdUser(idUser);
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

        return locationRepository.save(loc);
    }


}
