package br.com.mateus.crud.endpoint.controller;

import br.com.mateus.crud.config.JsonHelper;
import br.com.mateus.crud.endpoint.dto.UserDTO;
import br.com.mateus.crud.endpoint.exception.DatabaseException;
import br.com.mateus.crud.endpoint.exception.ResourceNotFoundException;
import br.com.mateus.crud.endpoint.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.util.Arrays;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    public UserControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void saveShouldReturnCreated() throws Exception {
        URI uri = new URI("/users");
        UserDTO userDTO = new UserDTO("1", "Teste", "teste", "teste");
        Mockito.when(userService.saveUser(Mockito.any())).thenReturn("1");

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(JsonHelper.toJson(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(201)));
    }

    @Test
    void findOneShouldReturnOk() throws Exception {
        URI uri = new URI("/users/11111111111");
        UserDTO userDTO = new UserDTO("11111111111", "teste", "teste", "teste");
        Mockito.when(userService.findUser(Mockito.any())).thenReturn(userDTO);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)));
    }

    @Test
    void updateShouldReturnOk() throws Exception {
        URI uri = new URI("/users/");
        UserDTO userDTO = new UserDTO("11111111111", "teste", "teste", "teste");
        Mockito.when(userService.mergeUser(Mockito.any())).thenReturn(userDTO);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put(uri)
                        .content(JsonHelper.toJson(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)));
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        URI uri = new URI("/users/11111111111");
        Mockito.doNothing().when(userService).deleteUser(Mockito.any());

        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(204)));
    }

    @Test
    void findAllShouldReturnOk() throws Exception {
        URI uri = new URI("/users/");
        UserDTO userDTO = new UserDTO("11111111111", "teste", "teste", "teste");
        Mockito.when(userService.findAll()).thenReturn(Arrays.asList(userDTO));

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)));
    }

    @Test
    void findOneShouldThrowResourceNotFoundException() throws Exception {
        URI uri = new URI("/users/11111111112");
        Mockito.doThrow(ResourceNotFoundException.class).when(userService).findUser("11111111112");

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(404)));
    }

    @Test
    void findOneShouldThrowDatabaseException() throws Exception {
        URI uri = new URI("/users/11111111112");
        Mockito.doThrow(DatabaseException.class).when(userService).findUser("11111111112");

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(500)));
    }
}
