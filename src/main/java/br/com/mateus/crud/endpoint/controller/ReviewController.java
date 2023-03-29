package br.com.mateus.crud.endpoint.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.mateus.crud.endpoint.dto.ReviewDTO;
import br.com.mateus.crud.endpoint.service.ReviewService;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<Page<ReviewDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") final Integer page,
            @RequestParam(value = "size", defaultValue = "12") final Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
            @RequestParam(value = "sort", defaultValue = "id") final String sort) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sort);
        Page<ReviewDTO> list = reviewService.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{sourceId}")
    public ResponseEntity<ReviewDTO> findBySourceId(@PathVariable final String sourceId) {
        return ResponseEntity.ok().body(reviewService.findReviewBySourceId(sourceId));
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> insert(@RequestBody final ReviewDTO reviewDto) {
        ReviewDTO review = reviewService.saveReview(reviewDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(review).toUri();
        return ResponseEntity.created(uri).body(review);
    }

    @PutMapping
    public ResponseEntity<ReviewDTO> update(@RequestBody final ReviewDTO reviewDto) {
        ReviewDTO review = reviewService.mergeReview(reviewDto);
        return ResponseEntity.ok().body(review);
    }

    @DeleteMapping("/{sourceId}")
    public ResponseEntity<ReviewDTO> delete(@PathVariable final String sourceId) {
        ReviewDTO review = reviewService.deleteReview(sourceId);
        return ResponseEntity.ok().body(review);
    }

}