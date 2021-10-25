package br.com.mateus.crud.endpoint.service;

import br.com.mateus.crud.endpoint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
}
