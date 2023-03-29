package br.com.mateus.crud.endpoint.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.google.common.base.MoreObjects;

import br.com.mateus.crud.endpoint.domain.User;

public class UserUpdatePasswordDTO implements Serializable {

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    public UserUpdatePasswordDTO(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public UserUpdatePasswordDTO(final User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public UserUpdatePasswordDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("email", email)
                .toString();
    }

}
