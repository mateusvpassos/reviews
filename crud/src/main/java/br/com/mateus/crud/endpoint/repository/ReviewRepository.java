package br.com.mateus.crud.endpoint.repository;

import br.com.mateus.crud.endpoint.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}