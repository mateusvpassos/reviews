package br.com.mateus.crud.endpoint.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false, name = "id")
    private long id;

    @Column(nullable = false, length = 200, name = "name")
    private String name;

    @Column(nullable = false, length = 100, unique = true, name = "email")
    private String email;

    @Column(nullable = false, length = 100, name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20, name = "role")
    private Role role;

    @Column(nullable = false, name = "active")
    private boolean active;

    public User(final String name, final String email, final String password, final Role role, final boolean active) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = active;
    }

    public User(final Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.role = builder.role;
        this.active = builder.active;
    }

    public User() {
    }

    public static class Builder {
        private long id;
        private String name;
        private String email;
        private String password;
        private Role role;
        private boolean active;

        public Builder id(final long id) {
            this.id = id;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        public Builder password(final String password) {
            this.password = password;
            return this;
        }

        public Builder role(final Role role) {
            this.role = role;
            return this;
        }

        public Builder active(final boolean active) {
            this.active = active;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("email", email)
                .add("password", password)
                .add("role", role)
                .add("active", active)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, email, password, role, active);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;

        return Objects.equal(id, other.id)
                && Objects.equal(name, other.name)
                && Objects.equal(email, other.email)
                && Objects.equal(password, other.password)
                && Objects.equal(role, other.role)
                && Objects.equal(active, other.active);
    }

}
