package br.com.mateus.crud.endpoint.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.google.common.base.MoreObjects;

import br.com.mateus.crud.endpoint.domain.Role;
import br.com.mateus.crud.endpoint.domain.User;

public class UserDTO implements Serializable {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Role is required")
    private Role role;

    @NotNull(message = "Active is required")
    private boolean active;

    public UserDTO(final String name, final String email, final Role role, final boolean active) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.active = active;
    }

    public UserDTO(final User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.active = user.isActive();
    }

    public UserDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(final Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("email", email)
                .add("role", role)
                .add("active", active)
                .toString();
    }

}
