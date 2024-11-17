package com.projet_iwa.ms_reservation.repository;

import com.projet_iwa.ms_reservation.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Récupère toutes les critiques associées à une location donnée.
     *
     * @param idLocation l'ID de la location concernée
     * @return une liste de critiques
     */
    List<Review> findByIdLocation(Long idLocation);

    /**
     * Récupère toutes les critiques laissées par un utilisateur spécifique.
     *
     * @param idUser l'ID de l'utilisateur
     * @return une liste de critiques
     */
    List<Review> findByIdReviewed(Long idUser);
}
