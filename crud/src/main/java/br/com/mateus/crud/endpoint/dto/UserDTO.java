package br.com.mateus.crud.endpoint.dto;

import br.com.mateus.crud.endpoint.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class UserDTO implements Serializable {
    @NotBlank
    private String id;
    @NotBlank
    private String name;
    @Email
    private String email;

    public UserDTO(String id, String name, String email){
        super();
        this.id = id;
        this.name = name;
        this.email = email;
    }
    public UserDTO(User user){
        super();
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public String getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getName() {
        return name;
    }
}
