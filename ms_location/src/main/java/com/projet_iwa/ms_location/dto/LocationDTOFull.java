package com.projet_iwa.ms_location.dto;

import com.projet_iwa.ms_location.dto.UserDTO;
import com.projet_iwa.ms_location.model.Category;
import java.util.List;
import java.util.Date;

public class LocationDTOFull {

    private Long id;
    private String title;
    private String description;
    private String address;
    private Boolean active;
    private UserDTO user;
    private Category category;
    private String imageUrl;
    private List<Date> blockedDates;

    public LocationDTOFull() {}

    public LocationDTOFull(Long id, String title, String description, String address, Boolean active, UserDTO user,
                           Category category, String imageUrl, List<Date> blockedDates) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.address = address;
        this.active = active;
        this.user = user;
        this.category = category;
        this.imageUrl = imageUrl;
        this.blockedDates = blockedDates;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Date> getBlockedDates() {
        return blockedDates;
    }

    public void setBlockedDates(List<Date> blockedDates) {
        this.blockedDates = blockedDates;
    }
}
