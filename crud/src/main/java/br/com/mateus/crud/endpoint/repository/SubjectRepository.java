package br.com.mateus.crud.endpoint.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mateus.crud.endpoint.domain.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Optional<Subject> findBySourceIdIgnoreCase(final String sourceId);

    Optional<List<Subject>> findByTitleIgnoreCaseContaining(final String title);

    Optional<Subject> findByTitleIgnoreCase(final String title);

    Optional<List<Subject>> findByDescriptionIgnoreCaseContaining(final String description);
}