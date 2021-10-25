package br.com.mateus.crud.endpoint.dto;

import br.com.mateus.crud.endpoint.domain.User;

public class UpdateUserDTO extends UserDTO{

    public UpdateUserDTO(String id, String name, String email) {
        super(id, name, email);
    }

    public UpdateUserDTO(User user) {
        super(user);
    }

}
