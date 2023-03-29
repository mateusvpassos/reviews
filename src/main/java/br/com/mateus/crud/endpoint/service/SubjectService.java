package br.com.mateus.crud.endpoint.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mateus.crud.endpoint.domain.Review;
import br.com.mateus.crud.endpoint.domain.Subject;
import br.com.mateus.crud.endpoint.dto.SubjectDTO;
import br.com.mateus.crud.endpoint.exception.exists.SubjectAlreadyExistsException;
import br.com.mateus.crud.endpoint.exception.notFound.SubjectNotFoundException;
import br.com.mateus.crud.endpoint.repository.SubjectRepository;
import br.com.mateus.crud.endpoint.util.StringValidator;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ReviewService reviewService;

    @Transactional(readOnly = true)
    public List<SubjectDTO> findSubjectByTitleContaining(final String title) {
        StringValidator.validateIfStringIsNullOrEmpty(title, "Subject Title");

        Optional<List<Subject>> optional = subjectRepository.findByTitleIgnoreCaseContaining(title);
        List<Subject> subjects = optional.orElseThrow(() -> new SubjectNotFoundException("Subject not found!"));

        return subjects.stream().map(SubjectDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubjectDTO findSubjectBySourceId(final String sourceId) {
        StringValidator.validateIfStringIsNullOrEmpty(sourceId, "Source Id");

        Optional<Subject> optional = subjectRepository.findBySourceIdIgnoreCase(sourceId);
        Subject subject = optional.orElseThrow(() -> new SubjectNotFoundException("Subject not found!"));

        return new SubjectDTO(subject);
    }

    @Transactional(readOnly = true)
    public Page<SubjectDTO> findAllPaged(final PageRequest pageRequest) {
        Page<Subject> subjects = subjectRepository.findAll(pageRequest);
        return subjects.map(SubjectDTO::new);
    }

    public SubjectDTO saveSubject(final SubjectDTO subjectDto) {
        Optional<Subject> subject = subjectRepository.findByTitleIgnoreCase(subjectDto.getTitle());
        if (subject.isPresent()) {
            throw new SubjectAlreadyExistsException("Subject already exists with title: " + subjectDto.getTitle());
        }
        return new SubjectDTO(subjectRepository.save(subjectDto.toSubjectEntity()));
    }

    public SubjectDTO mergeSubject(final SubjectDTO subjectDto) {
        Optional<Subject> subject = subjectRepository.findByTitleIgnoreCase(subjectDto.getTitle());
        if (subject.isPresent()) {
            return new SubjectDTO(subjectRepository.save(subjectDto.toSubjectEntity()));
        }
        throw new SubjectNotFoundException("Subject not found with title: " + subjectDto.getTitle());
    }

    public SubjectDTO deleteSubject(final String sourceId) {
        StringValidator.validateIfStringIsNullOrEmpty(sourceId, "Subject Source Id");
        Optional<Subject> subject = subjectRepository.findBySourceIdIgnoreCase(sourceId);
        if (subject.isPresent()) {
            Optional<List<Review>> reviews = reviewService.findReviewBySubject(subject.get());
            if (reviews.isPresent() && !reviews.get().isEmpty()) {
                throw new IllegalStateException("Subject has reviews associated");
            }

            subjectRepository.delete(subject.get());
            return new SubjectDTO(subject.get());
        }
        throw new SubjectNotFoundException("Subject not found with source id: " + sourceId);
    }
}
