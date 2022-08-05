package br.com.mateus.crud.endpoint.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.mateus.crud.endpoint.domain.Role;
import br.com.mateus.crud.endpoint.domain.User;
import br.com.mateus.crud.endpoint.dto.UserDTO;
import br.com.mateus.crud.endpoint.dto.UserSaveUpdateDTO;
import br.com.mateus.crud.endpoint.exception.exists.UserAlreadyExistsException;
import br.com.mateus.crud.endpoint.exception.notFound.UserNotFoundException;
import br.com.mateus.crud.endpoint.repository.UserRepository;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    private final static String EXISTING_EMAIL = "email@test.com";
    private final static String NON_EXISTING_EMAIL = "inexistent@test.com";
    private final static String UPDATED_NAME = "Mockito2";

    private User user = createObject();
    private User userUpdate = createObjectUpdate();

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    public void findAllPagedShouldListAll() {
        Mockito.when(userRepository.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(Page.empty());
        Page<UserDTO> users = userService.findAllPaged(PageRequest.of(1, 1));
        assertThat(users.getTotalElements()).isZero();
    }

    @Test
    public void findUserByEmailShouldReturnUser() {
        Mockito.when(userRepository.findByEmailIgnoreCase(EXISTING_EMAIL))
                .thenReturn(Optional.of(user));
        UserDTO userDTO = userService.findUserByEmail(EXISTING_EMAIL);
        assertThat(userDTO.getEmail()).isEqualTo(EXISTING_EMAIL);
    }

    @Test
    public void findUserByEmailShouldThrowUserNotFoundException() {
        Mockito.when(userRepository.findByEmailIgnoreCase(NON_EXISTING_EMAIL))
                .thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.findUserByEmail(NON_EXISTING_EMAIL));
    }

    @Test
    public void createShouldReturnUserDto() {
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class)))
                .thenReturn(user);
        UserSaveUpdateDTO userDto = new UserSaveUpdateDTO(user);
        UserDTO user = userService.saveUser(userDto);
        assertThat(user.getEmail()).isEqualTo(EXISTING_EMAIL);
    }

    @Test
    public void createShouldThrowUserAlreadyExistsException() {
        Mockito.when(userRepository.findByEmailIgnoreCase(EXISTING_EMAIL))
                .thenReturn(Optional.of(user));
        UserSaveUpdateDTO userDto = new UserSaveUpdateDTO(user);
        assertThrows(UserAlreadyExistsException.class, () -> userService.saveUser(userDto));
    }

    @Test
    public void deactivateShouldReturnUserDeactivated() {
        Mockito.when(userRepository.findByEmailIgnoreCase(EXISTING_EMAIL))
                .thenReturn(Optional.of(user));
        Mockito.doNothing().when(userRepository).delete(ArgumentMatchers.any(User.class));
        UserDTO userDto = userService.deactivateUser(user.getEmail());
        assertThat(userDto.isActive()).isFalse();
    }

    @Test
    public void deactivateShouldThrowUserNotFoundException() {
        Mockito.when(userRepository.findByEmailIgnoreCase(NON_EXISTING_EMAIL))
                .thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.deactivateUser(NON_EXISTING_EMAIL));
    }

    @Test
    public void updateShouldReturnUserDto() {
        Mockito.when(userRepository.findByEmailIgnoreCase(EXISTING_EMAIL))
                .thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class)))
                .thenReturn(userUpdate);
        UserSaveUpdateDTO userDto = new UserSaveUpdateDTO(userUpdate);
        UserDTO user = userService.mergeUser(userDto);
        assertThat(user.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void updateShouldThrowUserNotFoundException() {
        Mockito.when(userRepository.findByEmailIgnoreCase(NON_EXISTING_EMAIL))
                .thenReturn(Optional.empty());
        UserSaveUpdateDTO userDto = new UserSaveUpdateDTO(userUpdate);
        assertThrows(UserNotFoundException.class, () -> userService.mergeUser(userDto));
    }

    @Test
    public void activateShouldReturnUserActivated() {
        Mockito.when(userRepository.findByEmailIgnoreCase(EXISTING_EMAIL))
                .thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(ArgumentMatchers.any(User.class)))
                .thenReturn(user);
        UserDTO userDto = userService.activateUser(user.getEmail());
        assertThat(userDto.isActive()).isTrue();
    }

    @Test
    public void activateShouldThrowUserNotFoundException() {
        Mockito.when(userRepository.findByEmailIgnoreCase(NON_EXISTING_EMAIL))
                .thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.activateUser(NON_EXISTING_EMAIL));
    }

    private User createObject() {
        return new User.Builder()
                .id(1)
                .name("Mockito")
                .email(EXISTING_EMAIL)
                .password("creat")
                .role(Role.USER)
                .active(true)
                .build();
    }

    private User createObjectUpdate() {
        return new User.Builder()
                .id(1)
                .name("Mockito2")
                .email(EXISTING_EMAIL)
                .password("update")
                .role(Role.USER)
                .active(true)
                .build();
    }
}
