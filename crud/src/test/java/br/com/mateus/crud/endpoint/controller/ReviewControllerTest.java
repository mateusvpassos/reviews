package br.com.mateus.crud.endpoint.controller;

import java.net.URI;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.mateus.crud.config.JsonHelper;
import br.com.mateus.crud.endpoint.dto.ReviewDTO;
import br.com.mateus.crud.endpoint.exception.DatabaseException;
import br.com.mateus.crud.endpoint.exception.ResourceNotFoundException;
import br.com.mateus.crud.endpoint.service.ReviewService;

@WebMvcTest(controllers = ReviewController.class)
class ReviewControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    public ReviewControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void saveShouldReturnCreated() throws Exception {
        URI uri = new URI("/reviews");
        ReviewDTO reviewDTO = new ReviewDTO(5L, null, null, "teste", Short.valueOf("5"));
        Mockito.when(reviewService.saveReview(Mockito.any())).thenReturn(5L);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(JsonHelper.toJson(reviewDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(201)));
    }

    @Test
    void findOneShouldReturnOk() throws Exception {
        URI uri = new URI("/reviews/1");
        ReviewDTO reviewDTO = new ReviewDTO(5L, null, null, "teste", Short.valueOf("5"));
        Mockito.when(reviewService.findReview(Mockito.any())).thenReturn(reviewDTO);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)));
    }

    @Test
    void updateShouldReturnOk() throws Exception {
        URI uri = new URI("/reviews/");
        ReviewDTO reviewDTO = new ReviewDTO(5L, null, null, "teste", Short.valueOf("5"));
        Mockito.when(reviewService.mergeReview(Mockito.any())).thenReturn(reviewDTO);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .put(uri)
                        .content(JsonHelper.toJson(reviewDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)));
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        URI uri = new URI("/reviews/1");
        Mockito.doNothing().when(reviewService).deleteReview(Mockito.any());

        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(204)));
    }

    @Test
    void findAllShouldReturnOk() throws Exception {
        URI uri = new URI("/reviews/");
        ReviewDTO reviewDTO = new ReviewDTO(5L, null, null, "teste", Short.valueOf("5"));
        Mockito.when(reviewService.findAll()).thenReturn(Arrays.asList(reviewDTO));

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .content(JsonHelper.toJson(reviewDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)));
    }

    @Test
    void findOneShouldThrowResourceNotFoundException() throws Exception {
        URI uri = new URI("/reviews/1");
        Mockito.doThrow(ResourceNotFoundException.class).when(reviewService).findReview(1L);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(404)));
    }

    @Test
    void findOneShouldThrowDatabaseException() throws Exception {
        URI uri = new URI("/reviews/1");
        Mockito.doThrow(DatabaseException.class).when(reviewService).findReview(1L);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(500)));
    }
}
