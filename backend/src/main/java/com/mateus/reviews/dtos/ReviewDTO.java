package com.mateus.reviews.dtos;

import java.io.Serializable;

import com.mateus.reviews.domain.Review;
import com.mateus.reviews.domain.User;

public class ReviewDTO implements Serializable {
    private Long id;
    private String description;
    private Short rate;
    private User user;

    public ReviewDTO() {
    }

    public ReviewDTO(Long id, String description, Short rate, User user) {
        super();
        this.id = id;
        this.description = description;
        this.rate = rate;
        this.user = user;
    }

    public ReviewDTO(Review entity) {
        id = entity.getId();
        description = entity.getDescription();
        rate = entity.getRate();
        user = entity.getUser();
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Short getRate() {
        return rate;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRate(Short rate) {
        this.rate = rate;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
