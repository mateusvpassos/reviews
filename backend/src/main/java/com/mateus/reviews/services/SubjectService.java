package com.mateus.reviews.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import com.mateus.reviews.domain.Category;
import com.mateus.reviews.domain.Subject;
import com.mateus.reviews.dtos.CategoryDTO;
import com.mateus.reviews.dtos.SubjectDTO;
import com.mateus.reviews.repositories.CategoryRepository;
import com.mateus.reviews.repositories.SubjectRepository;
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
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<SubjectDTO> findAll() {
        List<Subject> list = subjectRepository.findAll();
        return list.stream().map(x -> new SubjectDTO(x, x.getCategories())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<SubjectDTO> findAllPaged(PageRequest pageRequest) {
        Page<Subject> list = subjectRepository.findAll(pageRequest);
        return list.map(x -> new SubjectDTO(x, x.getCategories()));
    }

    @Transactional(readOnly = true)
    public SubjectDTO findById(Long id) {
        Optional<Subject> optional = subjectRepository.findById(id);
        Subject subject = optional.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found!"));
        return new SubjectDTO(subject, subject.getCategories());
    }

    @Transactional
    public SubjectDTO insert(SubjectDTO subjectDto) {
        Subject subject = new Subject();
        copyDtoToEntity(subjectDto, subject);
        subject = subjectRepository.save(subject);
        return new SubjectDTO(subject, subject.getCategories());
    }

    @Transactional
    public SubjectDTO update(Long id, SubjectDTO subjectDto) {
        try {
            Subject subject = subjectRepository.getById(id);
            copyDtoToEntity(subjectDto, subject);
            subject = subjectRepository.save(subject);
            return new SubjectDTO(subject, subject.getCategories());
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("ID Not Found: " + id);
        }
    }

    public void delete(Long id) {
        try {
            subjectRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("ID Not Found: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity Violation");
        }

    }

    private void copyDtoToEntity(SubjectDTO dto, Subject subject) {
        subject.setName(dto.getName());
        subject.setDescription(dto.getDescription());
        subject.setImgUrl(dto.getImgUrl());
        subject.setMoment(dto.getMoment());

        subject.getCategories().clear();

        for (CategoryDTO catDTO : dto.getCategories()) {
            Category category = categoryRepository.getById(catDTO.getId());
            subject.getCategories().add(category);
        }
    }
}
