package br.com.mateus.crud.endpoint.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.google.common.base.MoreObjects;

import br.com.mateus.crud.endpoint.domain.Role;
import br.com.mateus.crud.endpoint.domain.User;

public class UserSaveUpdateDTO implements Serializable {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Role is required")
    private Role role;

    @NotNull(message = "Active is required")
    private boolean active;

    public UserSaveUpdateDTO(String name, String email, Role role, boolean active, String password) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.active = active;
        this.password = password;
    }

    public UserSaveUpdateDTO(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.active = user.isActive();
        this.password = user.getPassword();
    }

    public UserSaveUpdateDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
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

    public User toUserEntity() {
        return new User.Builder()
                .name(name)
                .email(email)
                .role(role)
                .active(active)
                .password(password)
                .build();
    }

}
