package br.com.mateus.crud.endpoint.service;

import br.com.mateus.crud.endpoint.dto.SubjectDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SubjectServiceTest {
    @Autowired
    private SubjectService subjectService;

    @Test
    public void createShouldCreateData(){
        SubjectDTO subject = createObject();
        subjectService.saveSubject(subject);

        assertThat(subject.getId()).isNotNull();
        assertThat(subject.getTitle()).isEqualTo("MockitoTestOne");
        assertThat(subject.getDescription()).isEqualTo("test@testone.com");
    }

    @Test
    public void deleteShouldRemoveData(){
        SubjectDTO subject = createObject();
        subjectService.saveSubject(subject);
        subjectService.deleteSubject(subject.getId());

        assertThat(subjectService.findSubject(subject.getId())).isNull();
    }

    @Test
    public void updateShouldChangeAndPersistData(){
        SubjectDTO subject = createObject();

        subject = subjectService.saveSubject(subject);
        subject.setTitle("MockitoTestTwo");
        subject.setDescription("Desc");
        subjectService.mergeSubject(subject);

        SubjectDTO result = subjectService.findSubject(subject.getId());

        assertThat(result.getTitle()).isEqualTo("MockitoTestTwo");
        assertThat(result.getDescription()).isEqualTo("Desc");
    }

    @Test
    public void createWhenIdIsNullShouldThrowException(){
        Exception exception = assertThrows(
                JpaSystemException.class,
                () -> subjectService.mergeSubject(createObjectNullId()));

        assertEquals(exception.getClass(), JpaSystemException.class);
    }

    @Test
    public void createWhenDescriptionIsNullShouldThrowException(){
        Exception exception = assertThrows(
                DataIntegrityViolationException.class,
                () -> subjectService.mergeSubject(createObjectNullDescription()));

        assertEquals(exception.getClass(), DataIntegrityViolationException.class);
    }


    private SubjectDTO createObject(){
        return new SubjectDTO(1005L, "MockitoTestOne", "Description");
    }

    private SubjectDTO createObjectNullTitle(){
        return new SubjectDTO(1005L, null, "Description");
    }

    private SubjectDTO createObjectNullDescription(){
        return new SubjectDTO(1005L, "MockitoTestOne", null);
    }

    private SubjectDTO createObjectNullId(){
        return new SubjectDTO(null, "MockitoTestOne", "Description");
    }
}
