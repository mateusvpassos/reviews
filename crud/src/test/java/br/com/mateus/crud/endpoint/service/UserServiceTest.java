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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;

import br.com.mateus.crud.endpoint.dto.UserDTO;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    private String existingId;

    @BeforeEach
    void setUp() throws Exception{
        existingId = "00000000000";
        Mockito.doNothing().when(userRepository).deleteById(existingId);
        Mockito.doReturn(userRepository.save(createObject())).when(userRepository).save(createObject());
    }

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    public void createShouldCreateData(){
        UserDTO user = new UserDTO(createObject());
        user = userService.saveUser(user);

        assertThat(user.getId()).isNotNull();
        assertThat(user.getName()).isEqualTo("MockitoTestOne");
        assertThat(user.getEmail()).isEqualTo("test@testone.com");

        Mockito.verify(userRepository).save(createObject());
    }

    @Test
    public void deleteShouldRemoveData(){
        userService.deleteUser(existingId);
        Exception exception = assertThrows(
                ResourceNotFoundException.class,
                () -> userService.findUser(existingId));
        assertEquals(exception.getClass(), ResourceNotFoundException.class);

        Mockito.verify(userRepository).deleteById(existingId);
    }

    @Test
    public void updateShouldChangeAndPersistData(){
        UserDTO user = new UserDTO(createObject());

        user = userService.saveUser(user);
        user.setName("MockitoTestTwo");
        user.setEmail("update@testone.com");
        userService.mergeUser(user);

        UserDTO result = userService.findUser(user.getId());

        assertThat(result.getName()).isEqualTo("MockitoTestTwo");
        assertThat(result.getEmail()).isEqualTo("update@testone.com");
    }

    @Test
    public void createWhenIdIsNullShouldThrowException(){
        Exception exception = assertThrows(
                JpaSystemException.class,
                () -> userService.mergeUser(new UserDTO(createObjectNullId())));

        assertEquals(exception.getClass(), JpaSystemException.class);
    }

    @Test
    public void createWhenNameIsNullShouldThrowException(){
        Exception exception = assertThrows(
                DataIntegrityViolationException.class,
                () -> userService.mergeUser(new UserDTO(createObjectNullName())));

        assertEquals(exception.getClass(), DataIntegrityViolationException.class);
    }

    @Test
    public void createWhenEmailIsNullShouldThrowException(){
        Exception exception = assertThrows(
                DataIntegrityViolationException.class,
                () -> userService.mergeUser(new UserDTO(createObjectNullEmail())));

        assertEquals(exception.getClass(), DataIntegrityViolationException.class);
    }

    private User createObject(){ return new User("12345678901", "MockitoTestOne", "test@testone.com", "pass");}
    private User createObjectNullName(){
        return new User("78945612358", null, "ignoringcase@test.com", "pass");
    }
    private User createObjectNullEmail(){
        return new User("78945612358", "MockitoTestOne", null, "pass");
    }
    private User createObjectNullId(){
        return new User(null, "MockitoTestOne", "test@testone.com", "pass");
    }
    private User createObjectNullPassword(){ return new User(null, "MockitoTestOne", "test@testone.com", null); }
}
