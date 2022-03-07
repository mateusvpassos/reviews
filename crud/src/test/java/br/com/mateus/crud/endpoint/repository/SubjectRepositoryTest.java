package br.com.mateus.crud.endpoint.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.mateus.crud.endpoint.domain.Subject;

@DataJpaTest
@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SubjectRepositoryTest {
    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    public void createShouldCreateData(){
        Subject subject = createObject();
        subjectRepository.save(subject);

        assertThat(subject.getId()).isNotNull();
        assertThat(subject.getTitle()).isEqualTo("SubjectOne");
        assertThat(subject.getDescription()).isEqualTo("DescriptionSubjectOne");
    }

    @Test
    public void deleteShouldRemoveData(){
        Subject subject = createObject();
        subjectRepository.save(subject);
        subjectRepository.delete(subject);

        assertThat(subjectRepository.findById(subject.getId())).isEmpty();
    }

    @Test
    public void updateShouldChangeAndPersistData(){
        Subject subject = createObject();

        subject = subjectRepository.save(subject);
        subject.setTitle("UpdatedTest");
        subject.setDescription("DescriptionSubjectUpdated");
        subjectRepository.save(subject);

        Optional<Subject> result = subjectRepository.findById(subject.getId());

        assertThat(result.map(Subject::getTitle).orElse(null)).isEqualTo("UpdatedTest");
        assertThat(result.map(Subject::getDescription).orElse(null)).isEqualTo("DescriptionSubjectUpdated");
    }

    @Test
    public void findByTitleIgnoreCaseContainingShouldIgnoreCase(){
        Subject subjectOne = createObject();
        Subject subjectTwo = createObjectIgnoringCase();

        subjectRepository.save(subjectOne);
        subjectRepository.save(subjectTwo);

        List<Subject> subjects = subjectRepository.findByTitleIgnoreCaseContaining("onet");

        assertThat(subjects.size()).isEqualTo(1);
    }

    @Test
    public void findByDescriptionIgnoreCaseContainingShouldIgnoreCase(){
        Subject subjectOne = createObjectIgnoringCase();
        subjectRepository.save(subjectOne);
        List<Subject> subjects = subjectRepository.findByDescriptionIgnoreCaseContaining("descriptionsubject");

        assertThat(subjects.size()).isEqualTo(1);
    }

    @Test
    public void createWhenTitleIsNullShouldThrowException(){
        Exception exception = assertThrows(
                DataIntegrityViolationException.class,
                () -> subjectRepository.save(createObjectNullTitle()));

        assertEquals(exception.getClass(), DataIntegrityViolationException.class);
    }

    @Test
    public void createWhenDescriptionIsNullShouldThrowException(){
        Exception exception = assertThrows(
                DataIntegrityViolationException.class,
                () -> subjectRepository.save(createObjectNullDescription()));

        assertEquals(exception.getClass(), DataIntegrityViolationException.class);
    }

    private Subject createObject(){
        return new Subject("SubjectOne","DescriptionSubjectOne");
    }

    private Subject createObjectIgnoringCase(){
        return new Subject("subjectonet","descriptionsubjectupdated");
    }

    private Subject createObjectNullTitle(){

        return new Subject(null,"DescriptionSubjectUpdated");
    }

    private Subject createObjectNullDescription(){

        return new Subject("SubjectOne",null);
    }

}
