package br.com.mateus.crud.endpoint.dto;

import br.com.mateus.crud.endpoint.domain.User;

import javax.validation.constraints.NotBlank;

public class InsertUserDTO extends UserDTO{

    @NotBlank
    private String password;

    public InsertUserDTO(String id, String name, String email) {
        super(id, name, email);
    }

    public InsertUserDTO(User user) {
        super(user);
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
