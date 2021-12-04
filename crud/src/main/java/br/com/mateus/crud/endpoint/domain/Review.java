package br.com.mateus.crud.endpoint.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Subject subject;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(length = 1)
    private Short rate;

    public Review(User user, Subject subject, String description, Short rate){
        this.user = user;
        this.subject = subject;
        this.description = description;
        this.rate = rate;
    }

    public Review(Long id, User user, Subject subject, String description, Short rate){
        this.id = id;
        this.user = user;
        this.subject = subject;
        this.description = description;
        this.rate = rate;
    }

    public Review() {}

    public Long getId() {
        return id;
    }
    public User getUser() {
        return user;
    }
    public Subject getSubject() { return subject; }
    public Short getRate() {
        return rate;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setRate(Short rate) {
        this.rate = rate;
    }
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
