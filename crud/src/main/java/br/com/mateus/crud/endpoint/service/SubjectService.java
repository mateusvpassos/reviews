package br.com.mateus.crud.endpoint.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public SubjectDTO findSubjectByTitleIgnoreCase(String title) {
        StringValidator.validateIfStringIsNullOrEmpty(title, "Subject Title");
        verifyIfNotFoundByTitle(title);

        Optional<Subject> subject = subjectRepository.findByTitleIgnoreCase(title);
        return new SubjectDTO(subject.get());
    }

    @Transactional(readOnly = true)
    public List<SubjectDTO> findSubjectByTitleIgnoreCaseContaining(String title) {
        StringValidator.validateIfStringIsNullOrEmpty(title, "Subject Title");

        Optional<List<Subject>> optional = subjectRepository.findByTitleIgnoreCaseContaining(title);
        List<Subject> subjects = optional.orElseThrow(() -> new SubjectNotFoundException("Subject not found!"));

        return subjects.stream().map(subject -> new SubjectDTO(subject)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<SubjectDTO> findAllPaged(PageRequest pageRequest) {
        Page<Subject> subjects = subjectRepository.findAll(pageRequest);
        return subjects.map(subject -> new SubjectDTO(subject));
    }

    public SubjectDTO saveSubject(SubjectDTO subjectDto) {
        verifyIfAlreadyExistsByTitle(subjectDto.getTitle());
        return new SubjectDTO(subjectRepository.save(subjectDto.toSubjectEntity()));
    }

    public SubjectDTO mergeSubject(SubjectDTO subjectDto) {
        verifyIfNotFoundByTitle(subjectDto.getTitle());
        return new SubjectDTO(subjectRepository.save(subjectDto.toSubjectEntity()));
    }

    public SubjectDTO deleteSubject(String title) {
        verifyIfNotFoundByTitle(title);

        Optional<Subject> subject = subjectRepository.findByTitleIgnoreCase(title);
        subjectRepository.deleteById(subject.get().getId());
        return new SubjectDTO(subject.get());
    }

    private void verifyIfAlreadyExistsByTitle(String title) {
        if (subjectRepository.existsByTitleIgnoreCase(title)) {
            throw new SubjectAlreadyExistsException("Subject already exists!");
        }
    }

    private void verifyIfNotFoundByTitle(String title) {
        if (!subjectRepository.existsByTitleIgnoreCase(title)) {
            throw new SubjectNotFoundException("Subject not found!");
        }
    }
}
