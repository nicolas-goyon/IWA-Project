package com.projet_iwa.ms_location.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.projet_iwa.ms_location.model.Category;
import com.projet_iwa.ms_location.model.Location;

import com.projet_iwa.ms_location.service.LocationService;

import java.util.List;


@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;
    @PostMapping("/create")
    public Location createUser(@RequestBody Location location) {
        return locationService.saveLocation(location);
    }

    @GetMapping("/get_all")
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    public Location getLocationById(@PathVariable Long id) {
        return locationService.getLocationById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
    }

    // Endpoint pour récupérer toutes les locations d'un utilisateur spécifique
    @GetMapping("/by_user/{idUser}")
    public List<Location> getLocationsByUserId(@PathVariable Long idUser) {
        return locationService.getLocationsByUserId(idUser);
    }
    // Endpoint pour mettre à jour une location
    @PutMapping("/{id}")
    public Location updateLocation(@PathVariable Long id, @RequestBody Location location) {
        return locationService.updateLocation(id, location);
    }





    // Gestion d'exception globale pour retourner un message d'erreur
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        return ex.getMessage();
    }
}