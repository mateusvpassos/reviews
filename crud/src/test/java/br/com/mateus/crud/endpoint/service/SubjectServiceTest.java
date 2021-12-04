package br.com.mateus.crud.endpoint.service;

import br.com.mateus.crud.endpoint.domain.Subject;
import br.com.mateus.crud.endpoint.dto.SubjectDTO;
import br.com.mateus.crud.endpoint.dto.UserDTO;
import br.com.mateus.crud.endpoint.exception.DatabaseException;
import br.com.mateus.crud.endpoint.repository.SubjectRepository;
import br.com.mateus.crud.endpoint.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
public class SubjectServiceTest {

    private Long existingId;
    private Long nonExistingId;
    private Subject subject = createObject();
    private Subject subjectUpdate = createObjectUpdate();

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 0L;

        Mockito.when(subjectRepository.save(ArgumentMatchers.any())).thenReturn(subject);
        Mockito.when(subjectRepository.findById(existingId)).thenReturn(Optional.of(createObject()));
        Mockito.when(subjectRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        Mockito.when(subjectRepository.findAll()).thenReturn(List.of(createObject()));
        Mockito.when(subjectRepository.findAll(PageRequest.of(1, 1))).thenReturn(Page.empty());
    }

    @Mock
    private SubjectRepository subjectRepository;
    @InjectMocks
    private SubjectService subjectService;

    @Test
    public void createShouldCreateData(){
        SubjectDTO subjectDto = new SubjectDTO(subject);
        Long id = subjectService.saveSubject(subjectDto);

        assertThat(id).isNotNull();
    }

    @Test
    public void deleteShouldRemoveData(){
        Mockito.doNothing().when(subjectRepository).deleteById(existingId);
        subjectService.deleteSubject(existingId);
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessException(){
        EmptyResultDataAccessException exc = new EmptyResultDataAccessException("ID Not Found: " + nonExistingId, 1);
        Mockito.doThrow(exc).when(subjectRepository).deleteById(nonExistingId);
        Exception exception = assertThrows(
                ResourceNotFoundException.class,
                () -> subjectService.deleteSubject(nonExistingId));
        assertEquals(exception.getMessage(), "ID Not Found: " + nonExistingId);
    }

    @Test
    public void deleteShouldThrowDataIntegrityViolationException(){
        DataIntegrityViolationException exc = new DataIntegrityViolationException("Integrity Violation");
        Mockito.doThrow(exc).when(subjectRepository).deleteById(existingId);
        Exception exception = assertThrows(
                DatabaseException.class,
                () -> subjectService.deleteSubject(existingId));
        assertEquals(exception.getMessage(), "Integrity Violation");
    }

    @Test
    public void findAllShouldListAll(){
        List<SubjectDTO> subjects = subjectService.findAll();
        assertThat(subjects.size()).isEqualTo(1);
    }

    @Test
    public void findAllPagedShouldListAll(){
        Page<SubjectDTO> subjects = subjectService.findAllPaged(PageRequest.of(1, 1));
        assertThat(subjects.getTotalElements()).isEqualTo(0);
    }

    @Test
    public void updateShouldChangeAndPersistData(){
        Mockito.when(subjectRepository.save(ArgumentMatchers.any())).thenReturn(subjectUpdate);
        Mockito.when(subjectRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(createObjectUpdate()));

        SubjectDTO subjectDTO = new SubjectDTO(createObjectUpdate());

        Long id = subjectService.saveSubject(subjectDTO);
        subjectDTO.setTitle("MockitoTestTwo");
        subjectDTO.setDescription("Update Description");
        subjectService.mergeSubject(subjectDTO);

        SubjectDTO result = subjectService.findSubject(id);

        assertThat(result.getTitle()).isEqualTo("MockitoTestTwo");
        assertThat(result.getDescription()).isEqualTo("Update Description");
    }

    private Subject createObject(){ return new Subject(4L, "MockitoTestOne", "Description");}
    private Subject createObjectUpdate(){ return new Subject(4L, "MockitoTestTwo", "Update Description");}

}
