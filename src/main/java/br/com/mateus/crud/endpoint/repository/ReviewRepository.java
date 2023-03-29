package br.com.mateus.crud.endpoint.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mateus.crud.endpoint.domain.Review;
import br.com.mateus.crud.endpoint.domain.Subject;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findBySourceIdIgnoreCase(final String sourceId);

    Optional<Review> findByUserEmailAndSubjectTitle(final String userEmail, final String subjectTitle);

    Optional<List<Review>> findBySubject(final Subject subject);

}