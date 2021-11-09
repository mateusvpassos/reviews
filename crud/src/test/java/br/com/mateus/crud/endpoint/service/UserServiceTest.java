package br.com.mateus.crud.endpoint.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.mateus.crud.endpoint.domain.User;
import br.com.mateus.crud.endpoint.repository.UserRepository;
import br.com.mateus.crud.endpoint.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.mateus.crud.endpoint.dto.UserDTO;
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
        nonExistingId = "00";

        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(user);
        Mockito.when(userRepository.findById(existingId)).thenReturn(Optional.of(createObject()));
        Mockito.when(userRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        Mockito.when(userRepository.findAll()).thenReturn(List.of(createObject()));

        Mockito.doNothing().when(userRepository).deleteById(existingId);
    }

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    public void createShouldCreateData(){
        UserDTO userDto = new UserDTO(user);
        userDto = userService.saveUser(userDto);

        assertThat(userDto.getId()).isNotNull();
        assertThat(userDto.getName()).isEqualTo("MockitoTestOne");
        assertThat(userDto.getEmail()).isEqualTo("test@testone.com");

    }

    @Test
    public void deleteShouldRemoveData(){
        userService.deleteUser(existingId);
        Exception exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.findUser(nonExistingId));
        assertEquals(exception.getClass(), ResourceNotFoundException.class);
    }

    @Test
    public void findAllShouldListAll(){
        List<UserDTO> users = userService.findAll();
        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void updateShouldChangeAndPersistData(){
        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenReturn(userUpdate);
        Mockito.when(userRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(createObjectUpdate()));

        UserDTO userDTO = new UserDTO(createObjectUpdate());

        userDTO = userService.saveUser(userDTO);
        userDTO.setName("MockitoTestTwo");
        userDTO.setEmail("update@testone.com");
        userService.mergeUser(userDTO);

        UserDTO result = userService.findUser(userDTO.getId());

        assertThat(result.getName()).isEqualTo("MockitoTestTwo");
        assertThat(result.getEmail()).isEqualTo("update@testone.com");
    }

    private User createObject(){ return new User("12345678901", "MockitoTestOne", "test@testone.com", "pass");}
    private User createObjectUpdate(){ return new User("12345678901", "MockitoTestTwo", "update@testone.com", "pass");}
}
