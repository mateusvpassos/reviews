package br.com.mateus.crud.endpoint.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@Entity
@Table(name = "review")
public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "subject_id")
    private Subject subject;

    @Column(nullable = false, length = 500, name = "description")
    private String description;

    @Column(length = 1, nullable = false, name = "rate")
    private short rate;

    public Review(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.subject = builder.subject;
        this.description = builder.description;
        this.rate = builder.rate;
    }

    public Review() {
    }

    public static class Builder {
        private long id;
        private User user;
        private Subject subject;
        private String description;
        private short rate;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder subject(Subject subject) {
            this.subject = subject;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder rate(short rate) {
            this.rate = rate;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Subject getSubject() {
        return subject;
    }

    public short getRate() {
        return rate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("user", user)
                .add("subject", subject)
                .add("description", description)
                .add("rate", rate)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, user, subject, description, rate);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Review other = (Review) obj;
        return Objects.equal(this.id, other.id)
                && Objects.equal(this.user, other.user)
                && Objects.equal(this.subject, other.subject)
                && Objects.equal(this.description, other.description)
                && Objects.equal(this.rate, other.rate);
    }
}
