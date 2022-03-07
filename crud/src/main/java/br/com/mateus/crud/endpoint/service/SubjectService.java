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

import br.com.mateus.crud.endpoint.domain.Subject;
import br.com.mateus.crud.endpoint.dto.SubjectDTO;
import br.com.mateus.crud.endpoint.exception.DatabaseException;
import br.com.mateus.crud.endpoint.exception.ResourceNotFoundException;
import br.com.mateus.crud.endpoint.repository.SubjectRepository;

@Service
public class SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    @Transactional(readOnly = true)
    public SubjectDTO findSubject(Long id) {
        Optional<Subject> optional = subjectRepository.findById(id);
        Subject subject = optional.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found!"));
        return new SubjectDTO(subject);
    }

    @Transactional(readOnly = true)
    public List<SubjectDTO> findAll() {
        List<Subject> list = subjectRepository.findAll();
        return list.stream().map(x -> new SubjectDTO(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<SubjectDTO> findAllPaged(PageRequest pageRequest) {
        Page<Subject> list = subjectRepository.findAll(pageRequest);
        return list.map(x -> new SubjectDTO(x));
    }

    @Transactional
    public Long saveSubject(SubjectDTO subjectDto) {
        Subject subject = new Subject();
        subject = copyDtoToEntity(subjectDto, subject);
        subject = subjectRepository.save(subject);
        return subject.getId();
    }

    @Transactional
    public SubjectDTO mergeSubject(SubjectDTO subjectDto) {
        Subject subject = new Subject();
        subject = copyDtoToEntity(subjectDto, subject);
        subject = subjectRepository.save(subject);
        return new SubjectDTO(subject);
    }

    public void deleteSubject(Long id) {
        try {
            subjectRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("ID Not Found: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity Violation");
        }

    }

    private Subject copyDtoToEntity(SubjectDTO dto, Subject subject) {
        subject.setTitle(dto.getTitle());
        subject.setDescription(dto.getDescription());
        return subject;
    }

}
