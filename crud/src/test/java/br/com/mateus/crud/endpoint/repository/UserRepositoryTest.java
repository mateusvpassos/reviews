package br.com.mateus.crud.endpoint.repository;

import br.com.mateus.crud.endpoint.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void createShouldCreateData(){
        User user = createObject();
        userRepository.save(user);

        assertThat(user.getId()).isNotNull();
        assertThat(user.getName()).isEqualTo("JpaTestOne");
        assertThat(user.getEmail()).isEqualTo("test@testone.com");
        assertThat(user.getPassword()).isEqualTo("pass");
    }

    @Test
    public void deleteShouldRemoveData(){
        User user = createObject();
        userRepository.save(user);
        userRepository.delete(user);

        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @Test
    public void updateShouldChangeAndPersistData(){
        User user = createObject();

        user = userRepository.save(user);
        user.setName("JpaTestTwo");
        user.setEmail("update@testone.com");
        user.setPassword("passUpdate");
        userRepository.save(user);

        Optional<User> result = userRepository.findById(user.getId());

        assertThat(result.map(User::getName).orElse(null)).isEqualTo("JpaTestTwo");
        assertThat(result.map(User::getEmail).orElse(null)).isEqualTo("update@testone.com");
        assertThat(result.map(User::getPassword).orElse(null)).isEqualTo("passUpdate");
    }

    @Test
    public void findByNameIgnoreCaseContainingShouldIgnoreCase(){
        User userOne = createObject();
        User userTwo = createObjectIgnoringCase();

        userRepository.save(userOne);
        userRepository.save(userTwo);

        List<User> users = userRepository.findByNameIgnoreCaseContaining("jpa");

        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    public void findByEmailIgnoreCaseShouldIgnoreCase(){
        User userOne = createObjectIgnoringCase();
        userRepository.save(userOne);
        User result = userRepository.findByEmailIgnoreCase("IGNORINGCASE@TEST.COM");

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("ignoringcase@test.com");
    }

    @Test
    public void createWhenIdIsNullShouldThrowException(){
        Exception exception = assertThrows(
                JpaSystemException.class,
                () -> userRepository.save(createObjectNullId()));

        assertEquals(exception.getClass(), JpaSystemException.class);
    }

    @Test
    public void createWhenNameIsNullShouldThrowException(){
        Exception exception = assertThrows(
                DataIntegrityViolationException.class,
                () -> userRepository.save(createObjectNullName()));

        assertEquals(exception.getClass(), DataIntegrityViolationException.class);
    }

    @Test
    public void createWhenEmailIsNullShouldThrowException(){
        Exception exception = assertThrows(
                DataIntegrityViolationException.class,
                () -> userRepository.save(createObjectNullEmail()));

        assertEquals(exception.getClass(), DataIntegrityViolationException.class);
    }

    @Test
    public void createWhenPasswordIsNullShouldThrowException(){
        Exception exception = assertThrows(
                DataIntegrityViolationException.class,
                () -> userRepository.save(createObjectNullPassword()));

        assertEquals(exception.getClass(), DataIntegrityViolationException.class);
    }

    private User createObject(){
        return new User("12345678901", "JpaTestOne", "test@testone.com", "pass");
    }

    private User createObjectUpdate(){ return new User("12345678901", "JpaTestTwo", "update@testone.com", "passUpdate");}

    private User createObjectIgnoringCase(){ return new User("78945612358", "jpatestone", "ignoringcase@test.com", "pass");}

    private User createObjectNullName(){
        return new User("78945612358", null, "ignoringcase@test.com", "pass");
    }

    private User createObjectNullEmail(){
        return new User("78945612358", "JpaTestOne", null, "pass");
    }

    private User createObjectNullPassword(){
        return new User("78945612358", "JpaTestOne", "test@testone.com", null);
    }

    private User createObjectNullId(){
        return new User(null, "JpaTestOne", "test@testone.com", "pass");
    }
}
