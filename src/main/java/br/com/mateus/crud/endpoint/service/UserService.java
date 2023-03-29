package br.com.mateus.crud.endpoint.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mateus.crud.endpoint.domain.User;
import br.com.mateus.crud.endpoint.dto.UserDTO;
import br.com.mateus.crud.endpoint.dto.UserSaveUpdateDTO;
import br.com.mateus.crud.endpoint.exception.exists.UserAlreadyExistsException;
import br.com.mateus.crud.endpoint.exception.notFound.UserNotFoundException;
import br.com.mateus.crud.endpoint.repository.UserRepository;
import br.com.mateus.crud.endpoint.util.StringValidator;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional(readOnly = true)
    public UserDTO findUserByEmail(final String email) {
        StringValidator.validateIfStringIsNullOrEmpty(email, "User Email");

        final Optional<User> user = userRepository.findByEmailIgnoreCase(email);
        if (user.isPresent()) {
            return new UserDTO(user.get());
        }
        throw new UserNotFoundException("User not found with email: " + email);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(final PageRequest pageRequest) {
        Page<User> users = userRepository.findAll(pageRequest);
        return users.map(UserDTO::new);
    }

    public UserDTO saveUser(final UserSaveUpdateDTO userDto) {
        Optional<User> user = userRepository.findByEmailIgnoreCase(userDto.getEmail());
        if (user.isPresent()) {
            throw new UserAlreadyExistsException("User already exists with email: " + userDto.getEmail());
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        final User userSaved = userRepository.save(userDto.toUserEntity());
        return new UserDTO(userSaved);
    }

    public UserDTO mergeUser(final UserSaveUpdateDTO userDto) {
        Optional<User> user = userRepository.findByEmailIgnoreCase(userDto.getEmail());
        if (user.isPresent()) {
            return new UserDTO(userRepository.save(userDto.toUserEntity()));
        }
        throw new UserNotFoundException("User not found with email: " + userDto.getEmail());
    }

    public UserDTO deactivateUser(final String email) {
        Optional<User> user = userRepository.findByEmailIgnoreCase(email);
        if (user.isPresent()) {
            user.get().deactivate();
            userRepository.save(user.get());
            return new UserDTO(user.get());
        }
        throw new UserNotFoundException("User not found with email: " + email);
    }

    public UserDTO activateUser(final String email) {
        Optional<User> user = userRepository.findByEmailIgnoreCase(email);
        if (user.isPresent()) {
            user.get().activate();
            userRepository.save(user.get());
            return new UserDTO(user.get());
        }
        throw new UserNotFoundException("User not found with email: " + email);
    }
}
