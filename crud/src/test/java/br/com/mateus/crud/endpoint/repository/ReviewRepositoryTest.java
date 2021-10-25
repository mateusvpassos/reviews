package br.com.mateus.crud.endpoint.repository;

import br.com.mateus.crud.endpoint.domain.Review;
import br.com.mateus.crud.endpoint.domain.Subject;
import br.com.mateus.crud.endpoint.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void createShouldCreateData(){
        Review review = createObject();
        reviewRepository.save(review);

        assertThat(review.getId()).isNotNull();
        assertThat(review.getDescription()).isEqualTo("DescriptionReviewOne");
        assertThat(review.getRate()).isEqualTo((short) 5);
    }

    @Test
    public void deleteShouldRemoveData(){
        Review review = createObject();
        reviewRepository.save(review);
        reviewRepository.delete(review);

        assertThat(reviewRepository.findById(review.getId())).isEmpty();
    }

    @Test
    public void updateShouldChangeAndPersistData(){
        Review review = createObject();

        review = reviewRepository.save(review);
        review.setDescription("DescriptionReviewUpdated");
        reviewRepository.save(review);

        Optional<Review> result = reviewRepository.findById(review.getId());

        assertThat(result.map(Review::getDescription).orElse(null)).isEqualTo("DescriptionReviewUpdated");
    }

    @Test
    public void createWhenUserIsNullShouldThrowException(){
        Exception exception = assertThrows(
                DataIntegrityViolationException.class,
                () -> reviewRepository.save(createObjectNullUser()));

        assertEquals(exception.getClass(), DataIntegrityViolationException.class);
    }

    @Test
    public void createWhenSubjectIsNullShouldThrowException(){
        Exception exception = assertThrows(
                DataIntegrityViolationException.class,
                () -> reviewRepository.save(createObjectNullSubject()));

        assertEquals(exception.getClass(), DataIntegrityViolationException.class);
    }

    @Test
    public void createWhenDescriptionIsNullShouldThrowException(){
        Exception exception = assertThrows(
                DataIntegrityViolationException.class,
                () -> reviewRepository.save(createObjectNullDescription()));

        assertEquals(exception.getClass(), DataIntegrityViolationException.class);
    }

    @Test
    public void createWhenRateIsNullShouldThrowException(){
        Exception exception = assertThrows(
                DataIntegrityViolationException.class,
                () -> reviewRepository.save(createObjectNullRate()));

        assertEquals(exception.getClass(), DataIntegrityViolationException.class);
    }

    private User createUser(){
        return new User("00000000000", null, null, null);
    }

    private Subject createSubject(){
        return new Subject(1L, null, null);
    }

    private Review createObject(){
        return new Review(createUser(), createSubject(), "DescriptionReviewOne", (short) 5);
    }

    private Review createObjectNullUser(){
        return new Review(null, createSubject(), "descriptionreviewone", (short) 4);
    }

    private Review createObjectNullSubject(){
        return new Review(createUser(), null, "descriptionreviewone", (short) 4);
    }

    private Review createObjectNullDescription(){
        return new Review(createUser(), createSubject(), null, (short) 4);
    }

    private Review createObjectNullRate(){
        return new Review(createUser(), createSubject(), "descriptionreviewone", null);
    }

}
