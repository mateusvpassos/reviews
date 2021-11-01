package br.com.mateus.crud.endpoint.service;

import br.com.mateus.crud.endpoint.domain.User;
import br.com.mateus.crud.endpoint.dto.UserDTO;
import br.com.mateus.crud.endpoint.repository.UserRepository;
import br.com.mateus.crud.endpoint.service.exception.DatabaseException;
import br.com.mateus.crud.endpoint.service.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserDTO findUser(String id) {
        Optional<User> optional = userRepository.findById(id);
        User user = optional.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found!"));
        return new UserDTO(user);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> list = userRepository.findAll();
        return list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(PageRequest pageRequest) {
        Page<User> list = userRepository.findAll(pageRequest);
        return list.map(x -> new UserDTO(x));
    }

    @Transactional
    public UserDTO saveUser(UserDTO userDto) {
        User user = new User();
        user = copyDtoToEntity(userDto, user);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user = userRepository.save(user);
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO mergeUser(UserDTO userDto) {
        User user = new User();
        user = copyDtoToEntity(userDto, user);
        user = userRepository.save(user);
        return new UserDTO(user);
    }

    @Transactional
    public void deleteUser(String id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("ID Not Found: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity Violation");
        }

    }

    private User copyDtoToEntity(UserDTO dto, User user) {
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return user;
    }

}