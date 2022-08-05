package br.com.mateus.crud.endpoint.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mateus.crud.endpoint.domain.Review;
import br.com.mateus.crud.endpoint.domain.Subject;
import br.com.mateus.crud.endpoint.dto.ReviewDTO;
import br.com.mateus.crud.endpoint.exception.exists.ReviewAlreadyExistsException;
import br.com.mateus.crud.endpoint.exception.notFound.ReviewNotFoundException;
import br.com.mateus.crud.endpoint.repository.ReviewRepository;
import br.com.mateus.crud.endpoint.util.StringValidator;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public ReviewDTO findReviews(final String userEmail, final String subjectTitle) {
        StringValidator.validateIfStringIsNullOrEmpty(userEmail, "User Email");
        StringValidator.validateIfStringIsNullOrEmpty(subjectTitle, "Subject Title");

        Optional<Review> review = reviewRepository.findByUserEmailAndSubjectTitle(userEmail, subjectTitle);
        return review.map(ReviewDTO::new).orElseThrow(() -> new ReviewNotFoundException("Review not found!"));
    }

    @Transactional(readOnly = true)
    public ReviewDTO findReviewBySourceId(final String sourceId) {
        StringValidator.validateIfStringIsNullOrEmpty(sourceId, "Source Id");

        Optional<Review> review = reviewRepository.findBySourceIdIgnoreCase(sourceId);
        return review.map(ReviewDTO::new).orElseThrow(() -> new ReviewNotFoundException("Review not found!"));
    }

    @Transactional(readOnly = true)
    public Page<ReviewDTO> findAllPaged(final PageRequest pageRequest) {
        Page<Review> reviews = reviewRepository.findAll(pageRequest);
        return reviews.map(ReviewDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<List<Review>> findReviewBySubject(final Subject subject) {
        if (subject == null) {
            throw new ReviewNotFoundException("Reviews not found!");
        }
        return reviewRepository.findBySubject(subject);
    }

    public ReviewDTO saveReview(final ReviewDTO reviewDto) {
        Optional<Review> review = reviewRepository.findByUserEmailAndSubjectTitle(reviewDto.getUserEmail(),
                reviewDto.getSubjectTitle());

        if (review.isPresent()) {
            throw new ReviewAlreadyExistsException("Review already exists with userEmail: " + reviewDto.getUserEmail()
                    + " and subjectTitle: " + reviewDto.getSubjectTitle());
        }

        return new ReviewDTO();
    }

    public ReviewDTO mergeReview(final ReviewDTO reviewDto) {
        Optional<Review> review = reviewRepository.findByUserEmailAndSubjectTitle(reviewDto.getUserEmail(),
                reviewDto.getSubjectTitle());

        if (!review.isPresent()) {
            throw new ReviewNotFoundException("Review not found!");
        }

        return new ReviewDTO();
    }

    public ReviewDTO deleteReview(final String sourceId) {
        Optional<Review> review = reviewRepository.findBySourceIdIgnoreCase(sourceId);

        if (!review.isPresent()) {
            throw new ReviewNotFoundException("Review not found!");
        }

        reviewRepository.delete(review.get());
        return new ReviewDTO(review.get());
    }

}
