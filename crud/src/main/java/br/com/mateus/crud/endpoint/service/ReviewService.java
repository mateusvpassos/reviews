package br.com.mateus.crud.endpoint.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mateus.crud.endpoint.domain.Review;
import br.com.mateus.crud.endpoint.dto.ReviewDTO;
import br.com.mateus.crud.endpoint.exception.DatabaseException;
import br.com.mateus.crud.endpoint.exception.ResourceNotFoundException;
import br.com.mateus.crud.endpoint.repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public ReviewDTO findReview(Long id) {
        Optional<Review> optional = reviewRepository.findById(id);
        Review review = optional.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found!"));
        return new ReviewDTO(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> findAll() {
        List<Review> list = reviewRepository.findAll();
        return list.stream().map(x -> new ReviewDTO(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ReviewDTO> findAllPaged(PageRequest pageRequest) {
        Page<Review> list = reviewRepository.findAll(pageRequest);
        return list.map(x -> new ReviewDTO(x));
    }

    @Transactional
    public Long saveReview(ReviewDTO reviewDto) {
        Review review = new Review();
        review = copyDtoToEntity(reviewDto, review);
        review = reviewRepository.save(review);
        return review.getId();
    }

    @Transactional
    public ReviewDTO mergeReview(ReviewDTO reviewDto) {
        Review review = new Review();
        review = copyDtoToEntity(reviewDto, review);
        review = reviewRepository.save(review);
        return new ReviewDTO(review);
    }

    public void deleteReview(Long id) {
        try {
            reviewRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("ID Not Found: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity Violation");
        }

    }

    private Review copyDtoToEntity(ReviewDTO dto, Review review) {
        review.setDescription(dto.getDescription());
        review.setRate(dto.getRate());
        review.setSubject(dto.getSubject());
        review.setUser(dto.getUser());
        return review;
    }

}
