package com.projet_iwa.ms_reservation.service;


import com.projet_iwa.ms_reservation.model.Grade;
import com.projet_iwa.ms_reservation.model.Review;
import com.projet_iwa.ms_reservation.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import com.projet_iwa.ms_reservation.Util;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * Crée une nouvelle critique.
     *
     * @param review la critique à créer
     * @return la critique créée
     */
    public Review createReview(Review review) {
        if (review.getGrade() < 1 || review.getGrade() > 5) {
            throw new IllegalArgumentException("La note doit être comprise entre 1 et 5.");
        }
        return reviewRepository.save(review);
    }

    /**
     * Récupère toutes les critiques à une location.
     *
     * @return une liste de toutes les critiques
     */
    public List<Review> getAllReviewsByLocation(Long id) {
        return reviewRepository.findByIdLocation(id);
    }
    /**
     * Récupère une note via les critiques à une location.
     *
     * @return une note
     */
    public Grade getGradeByLocation(Long id) {
        List<Review> reviews = reviewRepository.findByIdLocation(id);
        return Util.calculateGrade(reviews);
    }


    /**
     * Récupère toutes les critiques à un traveler
     *
     * @return une liste de toutes les critiques
     */
    public List<Review> getAllReviewsByReviewed(Long id) {
        return reviewRepository.findByIdReviewed(id);
    }
    /**
     * Récupère une note via les critiques à une location.
     *
     * @return une note
     */
    public Grade getGradeByReviewed(Long id) {
        List<Review> reviews = reviewRepository.findByIdReviewed(id);
        return Util.calculateGrade(reviews);
    }

    /**
     * Récupère une critique par son ID.
     *
     * @param id l'ID de la critique
     * @return la critique correspondante
     * @throws IllegalArgumentException si la critique n'est pas trouvée
     */
    public Review getReviewById(Long id) {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (optionalReview.isEmpty()) {
            throw new IllegalArgumentException("La critique avec l'ID " + id + " n'existe pas.");
        }
        return optionalReview.get();
    }

    /**
     * Supprime une critique par son ID.
     *
     * @param id l'ID de la critique à supprimer
     * @throws IllegalArgumentException si la critique n'est pas trouvée
     */
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new IllegalArgumentException("La critique avec l'ID " + id + " n'existe pas.");
        }
        reviewRepository.deleteById(id);
    }
}

