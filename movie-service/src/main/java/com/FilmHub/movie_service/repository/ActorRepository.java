package com.FilmHub.movie_service.repository;
import com.FilmHub.movie_service.models.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface ActorRepository extends JpaRepository<Actor, Long> {

    Set<Actor> findAllById(Set<Long> ids);
    Optional<Object> findByNameIgnoreCase(String name);
}
