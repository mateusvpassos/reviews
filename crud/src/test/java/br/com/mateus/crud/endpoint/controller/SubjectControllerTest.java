package br.com.mateus.crud.endpoint.controller;

import br.com.mateus.crud.config.JsonHelper;
import br.com.mateus.crud.endpoint.dto.SubjectDTO;
import br.com.mateus.crud.endpoint.exception.DatabaseException;
import br.com.mateus.crud.endpoint.exception.ResourceNotFoundException;
import br.com.mateus.crud.endpoint.service.SubjectService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.util.Arrays;

@WebMvcTest(controllers = SubjectController.class)
class SubjectControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private SubjectService subjectService;

    @Autowired
    public SubjectControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void saveShouldReturnCreated() throws Exception {
        URI uri = new URI("/subjects");
        SubjectDTO subjectDTO = new SubjectDTO(5L, "teste", "teste");
        Mockito.when(subjectService.saveSubject(Mockito.any())).thenReturn(5L);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(JsonHelper.toJson(subjectDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(201)));
    }

    @Test
    void findOneShouldReturnOk() throws Exception {
        URI uri = new URI("/subjects/1");
        SubjectDTO subjectDTO = new SubjectDTO(5L, "teste", "teste");
        Mockito.when(subjectService.findSubject(Mockito.any())).thenReturn(subjectDTO);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)));
    }

    @Test
    void updateShouldReturnOk() throws Exception {
        URI uri = new URI("/subjects/");
        SubjectDTO subjectDTO = new SubjectDTO(5L, "teste", "teste");
        Mockito.when(subjectService.mergeSubject(Mockito.any())).thenReturn(subjectDTO);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put(uri)
                        .content(JsonHelper.toJson(subjectDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)));
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        URI uri = new URI("/subjects/1");
        Mockito.doNothing().when(subjectService).deleteSubject(Mockito.any());

        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(204)));
    }

    @Test
    void findAllShouldReturnOk() throws Exception {
        URI uri = new URI("/subjects/");
        SubjectDTO subjectDTO = new SubjectDTO(5L, "teste", "teste");
        Mockito.when(subjectService.findAll()).thenReturn(Arrays.asList(subjectDTO));

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)));
    }

    @Test
    void findOneShouldThrowResourceNotFoundException() throws Exception {
        URI uri = new URI("/subjects/1");
        Mockito.doThrow(ResourceNotFoundException.class).when(subjectService).findSubject(1L);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(404)));
    }

    @Test
    void findOneShouldThrowDatabaseException() throws Exception {
        URI uri = new URI("/subjects/1");
        Mockito.doThrow(DatabaseException.class).when(subjectService).findSubject(1L);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(500)));
    }
}
