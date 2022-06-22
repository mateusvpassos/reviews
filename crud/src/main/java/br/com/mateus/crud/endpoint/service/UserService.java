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
    public UserDTO findUserByEmailIgnoreCase(String email) {
        StringValidator.validateIfStringIsNullOrEmpty(email, "User Email");
        verifyIfNotFoundByEmail(email);

        Optional<User> user = userRepository.findByEmailIgnoreCase(email);
        return new UserDTO(user.get());
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(PageRequest pageRequest) {
        Page<User> list = userRepository.findAll(pageRequest);
        return list.map(user -> new UserDTO(user));
    }

    public UserDTO saveUser(UserSaveUpdateDTO userDto) {
        verifyIfAlreadyExistsByEmail(userDto.getEmail());
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return new UserDTO(userRepository.save(userDto.toUserEntity()));
    }

    public UserDTO mergeUser(UserSaveUpdateDTO userDto) {
        verifyIfNotFoundByEmail(userDto.getEmail());
        return new UserDTO(userRepository.save(userDto.toUserEntity()));
    }

    public void deactivateUser(String email) {
        verifyIfNotFoundByEmail(email);

        User user = userRepository.findByEmailIgnoreCase(email).get();
        user.deactivate();
        userRepository.save(user);
    }

    public void activateUser(String email) {
        verifyIfNotFoundByEmail(email);

        User user = userRepository.findByEmailIgnoreCase(email).get();
        user.activate();
        userRepository.save(user);
    }

    private void verifyIfAlreadyExistsByEmail(String email) {
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new UserAlreadyExistsException("User already exists!");
        }
    }

    private void verifyIfNotFoundByEmail(String email) {
        if (!userRepository.existsByEmailIgnoreCase(email)) {
            throw new UserNotFoundException("User not found!");
        }
    }

}
