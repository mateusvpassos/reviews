package br.com.mateus.crud.endpoint.controller;

import java.net.URI;
import java.util.List;

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

import br.com.mateus.crud.endpoint.dto.SubjectDTO;
import br.com.mateus.crud.endpoint.service.SubjectService;

@RestController
@RequestMapping(value = "/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public ResponseEntity<Page<SubjectDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") final Integer page,
            @RequestParam(value = "size", defaultValue = "12") final Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") final String direction,
            @RequestParam(value = "sort", defaultValue = "id") final String sort) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sort);
        Page<SubjectDTO> list = subjectService.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{title}")
    public ResponseEntity<List<SubjectDTO>> findByTitle(@PathVariable final String title) {
        return ResponseEntity.ok().body(subjectService.findSubjectByTitleContaining(title));
    }

    @GetMapping("/{sourceId}")
    public ResponseEntity<SubjectDTO> findBySourceId(@PathVariable final String sourceId) {
        return ResponseEntity.ok().body(subjectService.findSubjectBySourceId(sourceId));
    }

    @PostMapping
    public ResponseEntity<SubjectDTO> insert(@RequestBody final SubjectDTO subjectDto) {
        SubjectDTO subject = subjectService.saveSubject(subjectDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(subject).toUri();
        return ResponseEntity.created(uri).body(subject);
    }

    @PutMapping
    public ResponseEntity<SubjectDTO> update(@RequestBody final SubjectDTO subjectDto) {
        SubjectDTO subject = subjectService.mergeSubject(subjectDto);
        return ResponseEntity.ok().body(subject);
    }

    @DeleteMapping("/{sourceId}")
    public ResponseEntity<SubjectDTO> delete(@PathVariable final String sourceId) {
        SubjectDTO subject = subjectService.deleteSubject(sourceId);
        return ResponseEntity.ok().body(subject);
    }

}