package br.com.mateus.crud.endpoint.dto;

import br.com.mateus.crud.endpoint.domain.Review;
import br.com.mateus.crud.endpoint.domain.Subject;
import br.com.mateus.crud.endpoint.domain.User;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ReviewDTO implements Serializable {
    @NotBlank
    private Long id;
    private User user;
    private Subject subject;
    @NotBlank
    private String description;
    @NotBlank
    private Short rate;

    public ReviewDTO(Long id, User user, Subject subject, String description, Short rate){
        this.id = id;
        this.user = user;
        this.subject = subject;
        this.description = description;
        this.rate = rate;
    }
    public ReviewDTO(Review review){
        this.id = review.getId();
        this.user = review.getUser();
        this.subject = review.getSubject();
        this.description = review.getDescription();
        this.rate = review.getRate();
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public Subject getSubject() { return subject; }
    public String getDescription() {
        return description;
    }
    public Short getRate() {
        return rate;
    }
}
