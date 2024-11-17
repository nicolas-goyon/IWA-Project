package com.projet_iwa.ms_reservation.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_reservation", nullable = false)
    private Long idReservation;

    @Column(name = "id_reviewer", nullable = false)
    private Long idReviewer;

    @Column(name = "id_reviewed")
    private Long idReviewed;

    @Column(name = "id_location")
    private Long idLocation;

    @Column(nullable = false)
    private Integer grade;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(Long idReservation) {
        this.idReservation = idReservation;
    }

    public Long getIdReviewer() {
        return idReviewer;
    }

    public void setIdReviewer(Long idReviewer) {
        this.idReviewer = idReviewer;
    }

    public Long getIdReviewed() {
        return idReviewed;
    }

    public void setIdReviewed(Long idReviewed) {
        this.idReviewed = idReviewed;
    }

    public Long getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Long idLocation) {
        this.idLocation = idLocation;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        if (grade < 0 || grade > 5) {
            throw new IllegalArgumentException("Grade must be between 0 and 5.");
        }
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
