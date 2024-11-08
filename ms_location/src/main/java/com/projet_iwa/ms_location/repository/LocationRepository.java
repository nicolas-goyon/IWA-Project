package com.projet_iwa.ms_location.repository;

import com.projet_iwa.ms_location.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByIdUser(Long idUser);
}
