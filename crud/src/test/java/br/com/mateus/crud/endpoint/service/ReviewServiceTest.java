package br.com.mateus.crud.endpoint.service;

import br.com.mateus.crud.endpoint.domain.Review;
import br.com.mateus.crud.endpoint.dto.ReviewDTO;
import br.com.mateus.crud.endpoint.repository.ReviewRepository;
import br.com.mateus.crud.endpoint.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReviewServiceTest {

    private Long existingId;
    private Long nonExistingId;
    private Review review = createObject();
    private Review reviewUpdate = createObjectUpdate();

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 0L;

        Mockito.when(reviewRepository.save(ArgumentMatchers.any())).thenReturn(review);
        Mockito.when(reviewRepository.findById(existingId)).thenReturn(Optional.of(createObject()));
        Mockito.when(reviewRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        Mockito.when(reviewRepository.findAll()).thenReturn(List.of(createObject()));

        Mockito.doNothing().when(reviewRepository).deleteById(existingId);
    }

    @Mock
    private ReviewRepository reviewRepository;
    @InjectMocks
    private ReviewService reviewService;

    @Test
    public void createShouldCreateData(){
        ReviewDTO reviewDto = new ReviewDTO(review);
        reviewDto = reviewService.saveReview(reviewDto);

        assertThat(reviewDto.getDescription()).isEqualTo("MockitoTestOne");
        assertThat(reviewDto.getRate()).isEqualTo(5);

    }

    @Test
    public void deleteShouldRemoveData(){
        reviewService.deleteReview(existingId);
        Exception exception = assertThrows(
                ResourceNotFoundException.class,
                () -> reviewService.findReview(nonExistingId));
        assertEquals(exception.getClass(), ResourceNotFoundException.class);
    }

    @Test
    public void findAllShouldListAll(){
        List<ReviewDTO> reviews = reviewService.findAll();
        assertThat(reviews.size()).isEqualTo(1);
    }

    @Test
    public void updateShouldChangeAndPersistData(){
        Mockito.when(reviewRepository.save(ArgumentMatchers.any())).thenReturn(reviewUpdate);
        Mockito.when(reviewRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(createObjectUpdate()));

        ReviewDTO reviewDTO = new ReviewDTO(createObjectUpdate());

        reviewDTO = reviewService.saveReview(reviewDTO);
        reviewDTO.setDescription("MockitoTestTwo");
        reviewService.mergeReview(reviewDTO);

        ReviewDTO result = reviewService.findReview(reviewDTO.getId());

        assertThat(result.getDescription()).isEqualTo("MockitoTestTwo");
    }

    private Review createObject(){ return new Review(null, null, "MockitoTestOne", Short.valueOf("5"));}
    private Review createObjectUpdate(){ return new Review(null, null, "MockitoTestTwo", Short.valueOf("5"));}

}
