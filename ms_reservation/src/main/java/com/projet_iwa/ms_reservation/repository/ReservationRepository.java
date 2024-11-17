package com.projet_iwa.ms_reservation.repository;


import com.projet_iwa.ms_reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByIdLocation(Long idLocation);
    List<Reservation> findByIdTraveler(Long idTraveler);


    List<Reservation> findByDateStartLessThanEqualAndDateEndGreaterThanEqual(LocalDate dateEnd, LocalDate dateStart);
}
