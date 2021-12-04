package br.com.mateus.crud.endpoint.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.mateus.crud.endpoint.domain.User;
import br.com.mateus.crud.endpoint.exception.DatabaseException;
import br.com.mateus.crud.endpoint.repository.UserRepository;
import br.com.mateus.crud.endpoint.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.mateus.crud.endpoint.dto.UserDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    private String existingId;
    private String nonExistingId;
    private User user = createObject();
    private User userUpdate = createObjectUpdate();

    @BeforeEach
    void setUp() {
        existingId = "00000000000";
        nonExistingId = "154156745134";

        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
        Mockito.when(userRepository.findById(existingId)).thenReturn(Optional.of(createObject()));
        Mockito.when(userRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        Mockito.when(userRepository.findAll()).thenReturn(List.of(createObject()));
        Mockito.when(userRepository.findAll(PageRequest.of(1, 1))).thenReturn(Page.empty());
    }

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    public void createShouldCreateData(){
        UserDTO userDto = new UserDTO(user);
        String id = userService.saveUser(userDto);

        assertThat(id).isNotNull();
    }

    @Test
    public void deleteShouldRemoveData(){
        Mockito.doNothing().when(userRepository).deleteById(existingId);
        userService.deleteUser(existingId);
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessException(){
        EmptyResultDataAccessException exc = new EmptyResultDataAccessException("ID Not Found: " + nonExistingId, 1);
        Mockito.doThrow(exc).when(userRepository).deleteById(nonExistingId);
        Exception exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.deleteUser(nonExistingId));
        assertEquals(exception.getMessage(), "ID Not Found: " + nonExistingId);
    }

    @Test
    public void deleteShouldThrowDatabaseException(){
        DataIntegrityViolationException exc = new DataIntegrityViolationException("Integrity Violation");
        Mockito.doThrow(exc).when(userRepository).deleteById(existingId);
        Exception exception = assertThrows(
                DatabaseException.class,
                () -> userService.deleteUser(existingId));
        assertEquals(exception.getMessage(), "Integrity Violation");
    }

    @Test
    public void findAllShouldListAll(){
        List<UserDTO> users = userService.findAll();
        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void findAllPagedShouldListAll(){
        Page<UserDTO> users = userService.findAllPaged(PageRequest.of(1, 1));
        assertThat(users.getTotalElements()).isEqualTo(0);
    }

    @Test
    public void updateShouldChangeAndPersistData(){
        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(userUpdate);
        Mockito.when(userRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(createObjectUpdate()));

        UserDTO userDTO = new UserDTO(createObjectUpdate());

        String id = userService.saveUser(userDTO);
        userDTO.setName("MockitoTestTwo");
        userDTO.setEmail("update@testone.com");
        userService.mergeUser(userDTO);

        UserDTO result = userService.findUser(id);

        assertThat(result.getName()).isEqualTo("MockitoTestTwo");
        assertThat(result.getEmail()).isEqualTo("update@testone.com");
    }

    private User createObject(){ return new User("12345678901", "MockitoTestOne", "test@testone.com", "pass");}
    private User createObjectUpdate(){ return new User("12345678901", "MockitoTestTwo", "update@testone.com", "pass");}
}
