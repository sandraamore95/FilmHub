package com.FilmHub.movie_service.mapper;
import com.FilmHub.movie_service.models.Actor;
import com.FilmHub.movie_service.payload.dto.ActorDTO;
import com.FilmHub.movie_service.payload.request.ActorRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActorMapper {

    // Convertir Actor a ActorDTO
    ActorDTO toDTO(Actor actor);

    // Convertir ActorRequest a Actor
    Actor toEntity(ActorRequest actorRequest);
}

