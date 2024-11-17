package com.projet_iwa.ms_reservation.dto;


import com.projet_iwa.ms_reservation.dto.UserDTO;
import com.projet_iwa.ms_reservation.dto.Category;

public class LocationDTO {

    private Long id;
    private String title;
    private String description;
    private String address;
    private Boolean active;
    private String imageUrl;
    private UserDTO user;
    private Category category;


    public LocationDTO() {}

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocationDTO(Long id, String title, String description, String address, Boolean active, String imageUrl, UserDTO user, Category category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.address = address;
        this.active = active;
        this.user = user;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
