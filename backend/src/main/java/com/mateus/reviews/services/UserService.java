package com.mateus.reviews.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.mateus.reviews.domain.Role;
import com.mateus.reviews.domain.User;
import com.mateus.reviews.dtos.RoleDTO;
import com.mateus.reviews.dtos.UserDTO;
import com.mateus.reviews.dtos.UserInsertDTO;
import com.mateus.reviews.dtos.UserUpdateDTO;
import com.mateus.reviews.repositories.RoleRepository;
import com.mateus.reviews.repositories.UserRepository;
import com.mateus.reviews.services.exceptions.DatabaseException;
import com.mateus.reviews.services.exceptions.ResourceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        Optional<User> optional = userRepository.findById(id);
        User user = optional.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found!"));
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO userDto) {
        User user = new User();
        copyDtoToEntity(userDto, user);
        user = userRepository.save(user);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO userDto) {
        try {
            User user = userRepository.getById(id);
            copyDtoToEntity(userDto, user);
            user = userRepository.save(user);
            return new UserDTO(user);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("ID Not Found: " + id);
        }
    }

    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("ID Not Found: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity Violation");
        }

    }

    private void copyDtoToEntity(UserDTO dto, User user) {
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());

        user.getRoles().clear();

        for (RoleDTO roleDTO : dto.getRoles()) {
            Role role = roleRepository.getById(roleDTO.getId());
            user.getRoles().add(role);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            logger.error("User not found: " + username);
            throw new UsernameNotFoundException("User not found!");
        }
        logger.info("User found: " + username);
        return user;
    }
}
