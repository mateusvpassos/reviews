package br.com.mateus.crud.endpoint.dto;

import br.com.mateus.crud.endpoint.domain.User;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private String id;
    private String name;
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
