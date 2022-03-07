package br.com.mateus.crud.endpoint.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mateus.crud.endpoint.domain.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByTitleIgnoreCaseContaining(String title);
    List<Subject> findByDescriptionIgnoreCaseContaining(String description);
}