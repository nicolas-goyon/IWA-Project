package com.projet_iwa.ms_location.controller;

import com.projet_iwa.ms_location.dto.LocationDTO;
import com.projet_iwa.ms_location.dto.LocationDTOFull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Location createLocation(@RequestBody Location location) {
        return locationService.saveLocation(location);
    }

    @GetMapping("/")
    public List<LocationDTO> getAllLocations() {
        return locationService.getAllLocations();
    }
    @GetMapping("/{id}")
    public LocationDTOFull getLocationById(@PathVariable Long id) {
        return locationService.getLocationById(id);
    }

    @GetMapping("/simple/{id}")
    public Location getSimpleLocationById(@PathVariable Long id) {
        return locationService.getSimpleLocationById(id);
    }

    @GetMapping("/user/{id}")
    public List<LocationDTO> getLocationByUserId(@PathVariable Long id) {
        return locationService.getLocationByUserId(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        try {
            locationService.deleteLocation(id);
            return ResponseEntity.noContent().build();  // Réponse 204 No Content
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();  // Réponse 404 Not Found si la location n'existe pas
        }
    }

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