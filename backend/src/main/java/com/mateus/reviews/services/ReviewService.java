package com.mateus.reviews.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.mateus.reviews.domain.Review;
import com.mateus.reviews.dtos.ReviewDTO;
import com.mateus.reviews.repositories.ReviewRepository;
import com.mateus.reviews.services.exceptions.DatabaseException;
import com.mateus.reviews.services.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

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

    @Transactional(readOnly = true)
    public ReviewDTO findById(Long id) {
        Optional<Review> optional = reviewRepository.findById(id);
        Review review = optional.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found!"));
        return new ReviewDTO(review);
    }

    @Transactional
    public ReviewDTO insert(ReviewDTO reviewDto) {
        Review review = new Review();
        review.setDescription(reviewDto.getDescription());
        review.setRate(reviewDto.getRate());
        review = reviewRepository.save(review);
        return new ReviewDTO(review);
    }

    @Transactional
    public ReviewDTO update(Long id, ReviewDTO reviewDto) {
        try {
            Review review = reviewRepository.getById(id);
            review.setDescription(reviewDto.getDescription());
            review.setRate(reviewDto.getRate());
            review = reviewRepository.save(review);
            return new ReviewDTO(review);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("ID Not Found: " + id);
        }
    }

    public void delete(Long id) {
        try {
            reviewRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("ID Not Found: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity Violation");
        }

    }
}
