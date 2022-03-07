package br.com.mateus.crud.endpoint.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mateus.crud.endpoint.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByNameIgnoreCaseContaining(String name);
    User findByEmailIgnoreCase(String email);
}