package br.com.mateus.crud.endpoint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mateus.crud.endpoint.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}