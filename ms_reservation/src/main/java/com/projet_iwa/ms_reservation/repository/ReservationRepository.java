package com.projet_iwa.ms_reservation.repository;


import com.projet_iwa.ms_reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByIdLocation(Long idLocation);
    List<Reservation> findByIdTraveler(Long idTraveler);

    // Requête pour mettre à jour les statuts des réservations expirées
    @Transactional
    @Modifying
    @Query("UPDATE Reservation r SET r.status = 'EXPIRED' WHERE r.dateEnd < CURRENT_DATE AND r.status <> 'EXPIRED'")
    void updateExpiredReservations();


    List<Reservation> findByDateStartLessThanEqualAndDateEndGreaterThanEqual(LocalDate dateEnd, LocalDate dateStart);
}
