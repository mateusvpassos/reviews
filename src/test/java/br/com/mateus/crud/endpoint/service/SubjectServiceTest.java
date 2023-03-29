package br.com.mateus.crud.endpoint.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.mateus.crud.endpoint.domain.Subject;
import br.com.mateus.crud.endpoint.dto.SubjectDTO;
import br.com.mateus.crud.endpoint.repository.SubjectRepository;

@ExtendWith(SpringExtension.class)
public class SubjectServiceTest {

    private final static String UPDATED_TITLE = "Mockito2";
    private Subject subject = createObject();
    private Subject subjectUpdate = createObjectUpdate();

    @Mock
    private SubjectRepository subjectRepository;
    @InjectMocks
    private SubjectService subjectService;

    private Subject createObject() {
        return new Subject.Builder()
                .id(1)
                .title("Mockito")
                .description("MockitoTest")
                .build();
    }

    private Subject createObjectUpdate() {
        return new Subject.Builder()
                .id(1)
                .title(UPDATED_TITLE)
                .description("MockitoTest")
                .build();
    }

}
