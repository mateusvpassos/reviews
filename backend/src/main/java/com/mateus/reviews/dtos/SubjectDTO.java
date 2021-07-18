package com.mateus.reviews.dtos;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mateus.reviews.domain.Category;
import com.mateus.reviews.domain.Subject;

public class SubjectDTO implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String imgUrl;
    private Instant moment;
    private List<CategoryDTO> categories = new ArrayList<>();

    public SubjectDTO() {
    }

    public SubjectDTO(Long id, String name, String description, String imgUrl, Instant moment) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.moment = moment;
    }

    public SubjectDTO(Subject subject) {
        id = subject.getId();
        name = subject.getName();
        description = subject.getDescription();
        imgUrl = subject.getImgUrl();
        moment = subject.getMoment();
    }

    public SubjectDTO(Subject subject, Set<Category> categories) {
        this(subject);
        categories.forEach(x -> this.categories.add(new CategoryDTO(x)));
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public String getDescription() {
        return description;
    }

    public Long getId() {
        return id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Instant getMoment() {
        return moment;
    }

    public String getName() {
        return name;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public void setName(String name) {
        this.name = name;
    }
}
