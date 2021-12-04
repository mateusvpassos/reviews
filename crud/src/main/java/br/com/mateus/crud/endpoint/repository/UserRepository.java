package br.com.mateus.crud.endpoint.repository;

import br.com.mateus.crud.endpoint.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findByNameIgnoreCaseContaining(String name);
    User findByEmailIgnoreCase(String email);
}