package com.FilmHub.movie_service.service;
import com.FilmHub.movie_service.mapper.ActorMapper;
import com.FilmHub.movie_service.mapper.MovieMapper;
import com.FilmHub.movie_service.models.Actor;
import com.FilmHub.movie_service.payload.dto.ActorDTO;
import com.FilmHub.movie_service.payload.request.ActorRequest;
import com.FilmHub.movie_service.repository.ActorRepository;
import org.springframework.stereotype.Service;
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
                .orElseThrow(() -> new RuntimeException("Actor con ID " + id + " no encontrado"));
        return actorMapper.toDTO(actor);
    }


    // Crear actor
    public ActorDTO createActor(ActorRequest actorRequest) {
        Actor actor = new Actor();
        actor.setName(actorRequest.getName());

        Actor savedActor = actorRepository.save(actor);

        return actorMapper.toDTO(savedActor);
    }

    // Actualizar actor
    public ActorDTO updateActor(Long id, ActorRequest actorRequest) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actor con ID " + id + " no encontrado"));

        actor.setName(actorRequest.getName());
        Actor updatedActor = actorRepository.save(actor);

        return actorMapper.toDTO(updatedActor);
    }

    // Eliminar actor
    public void deleteActor(Long id) {
        if (!actorRepository.existsById(id)) {
            throw new RuntimeException("Actor con ID " + id + " no encontrado");
        }
        actorRepository.deleteById(id);
    }


}