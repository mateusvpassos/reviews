package br.com.mateus.crud.endpoint.repository;

import br.com.mateus.crud.endpoint.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByTitleIgnoreCaseContaining(String title);
    List<Subject> findByDescriptionIgnoreCaseContaining(String description);
}