package br.com.mateus.crud.endpoint.repository;

import br.com.mateus.crud.endpoint.domain.Subject;
import br.com.mateus.crud.endpoint.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

}