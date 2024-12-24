package com.FilmHub.movie_service.service;
import com.FilmHub.movie_service.exceptions.ActorNotFoundException;
import com.FilmHub.movie_service.exceptions.DuplicateActorException;
import com.FilmHub.movie_service.mapper.ActorMapper;
import com.FilmHub.movie_service.models.Actor;
import com.FilmHub.movie_service.payload.dto.ActorDTO;
import com.FilmHub.movie_service.payload.request.ActorRequest;
import com.FilmHub.movie_service.repository.ActorRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorService {

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    public ActorService(ActorRepository actorRepository,ActorMapper actorMapper) {
        this.actorRepository = actorRepository;
        this.actorMapper=actorMapper;
    }

    // Obtener todos los actores
    public List<ActorDTO> getAllActors() {
        return actorRepository.findAll()
                .stream()
                .map(actorMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Obtener actor por ID
    public ActorDTO getActorById(Long id) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ActorNotFoundException("Actor con ID " + id + " no encontrado"));
        return actorMapper.toDTO(actor);
    }


    // Crear actor
    public ActorDTO createActor(ActorRequest actorRequest) {
        try {
            Actor actor = actorMapper.toEntity(actorRequest);// Convertir ActorRequest a entidad Movie
            Actor savedActor = actorRepository.save(actor); //guarda en BD
            return actorMapper.toDTO(savedActor);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateActorException("El actor  '" + actorRequest.getName() + "' ya existe.");
        }
    }

    // Actualizar actor
    public ActorDTO updateActor(Long id, ActorRequest actorRequest) {

        // Verificar que existe el actor
        Actor existingActor = actorRepository.findById(id)
                .orElseThrow(() -> new ActorNotFoundException("Actor con ID " + id + " no encontrado"));

        // Actualizar solo los campos que se proporcionan en el DTO
        if (StringUtils.hasText(actorRequest.getName())) {
            existingActor.setName(actorRequest.getName());
        }
        // Guardar el actor actualizado en el repositorio
        Actor updatedActor = actorRepository.save(existingActor);
        return actorMapper.toDTO(updatedActor);
    }

    // Eliminar actor
    public void deleteActor(Long id) {
        if (!actorRepository.existsById(id)) {
            throw new ActorNotFoundException("Actor con ID " + id + " no encontrado");
        }
        actorRepository.deleteById(id);
    }


}