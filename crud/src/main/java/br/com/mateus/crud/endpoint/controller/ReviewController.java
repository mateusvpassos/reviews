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
    public ResponseEntity<Page<ReviewDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                 @RequestParam(value = "direction", defaultValue = "ASC") String direction,
                                                 @RequestParam(value = "sort", defaultValue = "id") String sort) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sort);
        Page<ReviewDTO> list = reviewService.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> findAll(@PathVariable Long id) {
        return ResponseEntity.ok().body(reviewService.findReview(id));
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> insert(@RequestBody ReviewDTO reviewDto) {
        ReviewDTO newDto = reviewService.saveReview(reviewDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping()
    public ResponseEntity<ReviewDTO> update(@RequestBody ReviewDTO reviewDto) {
        ReviewDTO newDto = reviewService.mergeReview(reviewDto);
        return ResponseEntity.ok().body(newDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

}