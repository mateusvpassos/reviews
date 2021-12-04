package br.com.mateus.crud.endpoint.repository;

import br.com.mateus.crud.endpoint.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByTitleIgnoreCaseContaining(String title);
    List<Subject> findByDescriptionIgnoreCaseContaining(String description);
}