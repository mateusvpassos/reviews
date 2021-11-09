package br.com.mateus.crud.endpoint.service;

import br.com.mateus.crud.endpoint.domain.Subject;
import br.com.mateus.crud.endpoint.dto.SubjectDTO;
import br.com.mateus.crud.endpoint.repository.SubjectRepository;
import br.com.mateus.crud.endpoint.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        Mockito.doNothing().when(subjectRepository).deleteById(existingId);
    }

    @Mock
    private SubjectRepository subjectRepository;
    @InjectMocks
    private SubjectService subjectService;

    @Test
    public void createShouldCreateData(){
        SubjectDTO subjectDto = new SubjectDTO(subject);
        subjectDto = subjectService.saveSubject(subjectDto);

        //assertThat(subjectDto.getId()).isNotNull();
        assertThat(subjectDto.getTitle()).isEqualTo("MockitoTestOne");
        assertThat(subjectDto.getDescription()).isEqualTo("Description");

    }

    @Test
    public void deleteShouldRemoveData(){
        subjectService.deleteSubject(existingId);
        Exception exception = assertThrows(
                ResourceNotFoundException.class,
                () -> subjectService.findSubject(nonExistingId));
        assertEquals(exception.getClass(), ResourceNotFoundException.class);
    }

    @Test
    public void findAllShouldListAll(){
        List<SubjectDTO> subjects = subjectService.findAll();
        assertThat(subjects.size()).isEqualTo(1);
    }

    @Test
    public void updateShouldChangeAndPersistData(){
        Mockito.when(subjectRepository.save(ArgumentMatchers.any())).thenReturn(subjectUpdate);
        Mockito.when(subjectRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(createObjectUpdate()));

        SubjectDTO subjectDTO = new SubjectDTO(createObjectUpdate());

        subjectDTO = subjectService.saveSubject(subjectDTO);
        subjectDTO.setTitle("MockitoTestTwo");
        subjectDTO.setDescription("Update Description");
        subjectService.mergeSubject(subjectDTO);

        SubjectDTO result = subjectService.findSubject(subjectDTO.getId());

        assertThat(result.getTitle()).isEqualTo("MockitoTestTwo");
        assertThat(result.getDescription()).isEqualTo("Update Description");
    }

    private Subject createObject(){ return new Subject(4L, "MockitoTestOne", "Description");}
    private Subject createObjectUpdate(){ return new Subject(4L, "MockitoTestTwo", "Update Description");}

}
