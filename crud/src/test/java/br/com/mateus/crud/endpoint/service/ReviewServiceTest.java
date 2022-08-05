// package br.com.mateus.crud.endpoint.service;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;

// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.ArgumentMatchers;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.springframework.dao.DataIntegrityViolationException;
// import org.springframework.dao.EmptyResultDataAccessException;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

// import br.com.mateus.crud.endpoint.domain.Review;
// import br.com.mateus.crud.endpoint.dto.ReviewDTO;
// import br.com.mateus.crud.endpoint.exception.DatabaseException;
// import br.com.mateus.crud.endpoint.exception.ResourceNotFoundException;
// import br.com.mateus.crud.endpoint.repository.ReviewRepository;

// @ExtendWith(SpringExtension.class)
// public class ReviewServiceTest {

// @Mock
// private ReviewRepository reviewRepository;
// @InjectMocks
// private ReviewService reviewService;

// private Long existingId;
// private Long nonExistingId;
// private Review review = createObject();
// private Review reviewUpdate = createObjectUpdate();

// @BeforeEach
// void setUp() {
// existingId = 1L;
// nonExistingId = 0L;

// Mockito.when(reviewRepository.save(ArgumentMatchers.any())).thenReturn(review);
// Mockito.when(reviewRepository.findById(existingId)).thenReturn(Optional.of(createObject()));
// Mockito.when(reviewRepository.findById(nonExistingId)).thenReturn(Optional.empty());
// Mockito.when(reviewRepository.findAll()).thenReturn(List.of(createObject()));
// Mockito.when(reviewRepository.findAll(PageRequest.of(1,
// 1))).thenReturn(Page.empty());
// }

// @Test
// public void createShouldCreateData(){
// ReviewDTO reviewDto = new ReviewDTO(review);
// Long id = reviewService.saveReview(reviewDto);

// assertThat(id).isNotNull();
// }

// @Test
// public void deleteShouldRemoveData(){
// Mockito.doNothing().when(reviewRepository).deleteById(existingId);
// reviewService.deleteReview(existingId);
// }

// @Test
// public void deleteShouldThrowEmptyResultDataAccessException(){
// EmptyResultDataAccessException exc = new EmptyResultDataAccessException("ID
// Not Found: " + nonExistingId, 1);
// Mockito.doThrow(exc).when(reviewRepository).deleteById(nonExistingId);
// Exception exception = assertThrows(
// ResourceNotFoundException.class,
// () -> reviewService.deleteReview(nonExistingId));
// assertEquals(exception.getMessage(), "ID Not Found: " + nonExistingId);
// }

// @Test
// public void deleteShouldThrowDataIntegrityViolationException(){
// DataIntegrityViolationException exc = new
// DataIntegrityViolationException("Integrity Violation");
// Mockito.doThrow(exc).when(reviewRepository).deleteById(existingId);
// Exception exception = assertThrows(
// DatabaseException.class,
// () -> reviewService.deleteReview(existingId));
// assertEquals(exception.getMessage(), "Integrity Violation");
// }

// @Test
// public void findAllShouldListAll(){
// List<ReviewDTO> reviews = reviewService.findAll();
// assertThat(reviews.size()).isEqualTo(1);
// }

// @Test
// public void findAllPagedShouldListAll(){
// Page<ReviewDTO> reviews = reviewService.findAllPaged(PageRequest.of(1, 1));
// assertThat(reviews.getTotalElements()).isEqualTo(0);
// }

// @Test
// public void updateShouldChangeAndPersistData(){
// Mockito.when(reviewRepository.save(ArgumentMatchers.any())).thenReturn(reviewUpdate);
// Mockito.when(reviewRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(createObjectUpdate()));

// ReviewDTO reviewDTO = new ReviewDTO(createObjectUpdate());

// reviewDTO.setDescription("MockitoTestTwo");
// reviewService.mergeReview(reviewDTO);

// ReviewDTO result = reviewService.findReview(reviewDTO.getId());

// assertThat(result.getDescription()).isEqualTo("MockitoTestTwo");
// }

// private Review createObject(){ return new Review(6L, null, null,
// "MockitoTestOne", Short.valueOf("5"));}
// private Review createObjectUpdate(){ return new Review(6L, null, null,
// "MockitoTestTwo", Short.valueOf("5"));}

// }
