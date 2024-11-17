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
    public Location createLocation(@RequestHeader("Authorization") String authorizationHeader,@RequestBody Location location) {
        return locationService.saveLocation(authorizationHeader,location);
    }

    @GetMapping("/")
    public List<LocationDTO> getAllLocations(@RequestHeader("Authorization") String authorizationHeader) {
        return locationService.getAllLocations(authorizationHeader);
    }
    @GetMapping("/{id}")
    public LocationDTOFull getLocationById(@RequestHeader("Authorization") String authorizationHeader,@PathVariable Long id) {
        return locationService.getLocationById(authorizationHeader,id);
    }

    @GetMapping("/simple/{id}")
    public Location getSimpleLocationById(@RequestHeader("Authorization") String authorizationHeader,@PathVariable Long id) {
        return locationService.getSimpleLocationById(authorizationHeader,id);
    }

    @GetMapping("/user/{id}")
    public List<LocationDTO> getLocationByUserId(@RequestHeader("Authorization") String authorizationHeader,@PathVariable Long id) {
        return locationService.getLocationByUserId(authorizationHeader,id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@RequestHeader("Authorization") String authorizationHeader,@PathVariable Long id) {
        try {
            locationService.deleteLocation(authorizationHeader,id);
            return ResponseEntity.noContent().build();  // Réponse 204 No Content
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();  // Réponse 404 Not Found si la location n'existe pas
        }
    }

    @PutMapping("/{id}")
    public Location updateLocation(@RequestHeader("Authorization") String authorizationHeader,@PathVariable Long id, @RequestBody Location location) {
        return locationService.updateLocation(authorizationHeader,id, location);
    }

    // Gestion d'exception globale pour retourner un message d'erreur
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        return ex.getMessage();
    }
}