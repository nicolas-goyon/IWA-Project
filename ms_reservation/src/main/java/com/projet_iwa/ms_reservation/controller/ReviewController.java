package com.projet_iwa.ms_reservation.controller;

import com.projet_iwa.ms_reservation.model.Grade;
import com.projet_iwa.ms_reservation.model.Review;
import com.projet_iwa.ms_reservation.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        try {
            Review createdReview = reviewService.createReview(review);
            return ResponseEntity.ok(createdReview);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/location/{id}")
    public ResponseEntity<List<Review>> getAllReviewsByLocation(@PathVariable Long id) {
        List<Review> reviews = reviewService.getAllReviewsByLocation(id);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/grade/location/{id}")
    public ResponseEntity<Grade> getGradeByLocation(@PathVariable Long id) {
        Grade grade = reviewService.getGradeByLocation(id);
        return ResponseEntity.ok(grade);
    }
    @GetMapping("/reviewed/{id}")
    public ResponseEntity<List<Review>> getAllReviewsByReviewed(@PathVariable Long id) {
        List<Review> reviews = reviewService.getAllReviewsByReviewed(id);
        return ResponseEntity.ok(reviews);
    }
    @GetMapping("/grade/reviewed/{id}")
    public ResponseEntity<Grade> getGradeByReviewed(@PathVariable Long id) {
        Grade grade = reviewService.getGradeByReviewed(id);
        return ResponseEntity.ok(grade);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        try {
            Review review = reviewService.getReviewById(id);
            return ResponseEntity.ok(review);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        try {
            reviewService.deleteReview(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
