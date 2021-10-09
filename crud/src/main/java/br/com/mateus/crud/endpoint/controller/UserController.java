package br.com.mateus.crud.endpoint.controller;

import br.com.mateus.crud.endpoint.repository.UserRepository;
import br.com.mateus.crud.endpoint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;
}
