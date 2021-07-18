package com.mateus.reviews.resources;

import java.net.URI;

import com.mateus.reviews.dtos.SubjectDTO;
import com.mateus.reviews.services.SubjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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

@RestController
@RequestMapping(value = "/subjects")
public class SubjectResource {

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public ResponseEntity<Page<SubjectDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "sort", defaultValue = "name") String sort) {

        PageRequest pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), sort);
        Page<SubjectDTO> list = subjectService.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> findAll(@PathVariable Long id) {
        return ResponseEntity.ok().body(subjectService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SubjectDTO> insert(@RequestBody SubjectDTO subjectDto) {
        subjectDto = subjectService.insert(subjectDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(subjectDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(subjectDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectDTO> update(@PathVariable Long id, @RequestBody SubjectDTO subjectDto) {
        subjectDto = subjectService.update(id, subjectDto);
        return ResponseEntity.ok().body(subjectDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
