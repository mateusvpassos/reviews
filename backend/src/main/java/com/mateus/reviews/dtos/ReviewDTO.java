package com.mateus.reviews.dtos;

import java.io.Serializable;

import com.mateus.reviews.domain.Review;

public class ReviewDTO implements Serializable {
    private Long id;
    private String description;
    private Short rate;

    public ReviewDTO() {
    }

    public ReviewDTO(Long id, String description, Short rate) {
        super();
        this.id = id;
        this.description = description;
        this.rate = rate;
    }

    public ReviewDTO(Review entity) {
        id = entity.getId();
        description = entity.getDescription();
        rate = entity.getRate();
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRate(Short rate) {
        this.rate = rate;
    }
}
