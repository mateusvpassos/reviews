package br.com.mateus.crud.endpoint.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mateus.crud.endpoint.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<List<User>> findByNameIgnoreCaseContaining(final String name);

    Optional<User> findByEmailIgnoreCase(final String email);
}