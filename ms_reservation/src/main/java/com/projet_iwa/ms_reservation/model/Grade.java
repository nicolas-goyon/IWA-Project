package com.projet_iwa.ms_reservation.model;

public class Grade {

    private long count;
    private float grade;


    public Grade(long count, float grade) {
        this.count = count;
        this.grade = grade;
    }

    // Getters et Setters
    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }
}
