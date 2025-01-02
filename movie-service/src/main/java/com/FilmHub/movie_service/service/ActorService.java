package com.FilmHub.movie_service.service;
import com.FilmHub.movie_service.exceptions.ActorNotFoundException;
import com.FilmHub.movie_service.exceptions.DuplicateActorException;
import com.FilmHub.movie_service.exceptions.MovieNotFoundException;
import com.FilmHub.movie_service.mapper.ActorMapper;
import com.FilmHub.movie_service.models.Actor;
import com.FilmHub.movie_service.models.Movie;
import com.FilmHub.movie_service.payload.dto.ActorDTO;
import com.FilmHub.movie_service.payload.request.ActorRequest;
import com.FilmHub.movie_service.repository.ActorRepository;
import com.FilmHub.movie_service.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ActorService {

    private final ActorRepository actorRepository;
    private final MovieRepository movieRepository;
    private final ActorMapper actorMapper;

    public ActorService(ActorRepository actorRepository,ActorMapper actorMapper,MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository=movieRepository;
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
        // Verificar si el actor ya existe por nombre
        if (actorRepository.findByNameIgnoreCase(actorRequest.getName()).isPresent()) {
            throw new DuplicateActorException("El actor con el nombre '" + actorRequest.getName() + "' ya existe.");
        }
        // Convertir ActorRequest a entidad Actor
        Actor actor = actorMapper.toEntity(actorRequest);
        // Guardar actor en la base de datos
        Actor savedActor = actorRepository.save(actor);
        // Convertir la entidad Actor guardada a DTO y devolverla
        return actorMapper.toDTO(savedActor);
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

    //Asociar peliculas a actor
    @Transactional
    public ActorDTO addMovieToActor(Long actorId, Set<Long> movieIds) {
        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new ActorNotFoundException("Actor con ID " + actorId + " no encontrado"));

        Set<Movie> movies = new HashSet<>();
        for (Long movieId : movieIds) {
            Movie movie = movieRepository.findById(movieId)
                    .orElseThrow(() -> new MovieNotFoundException("Película con ID " + movieId + " no encontrada"));
            movies.add(movie);
        }
        actor.setMovies(movies);
        Actor savedActor = actorRepository.save(actor);
        return actorMapper.toDTO(savedActor);
    }

    // Eliminar actor
    public void deleteActor(Long id) {
        if (!actorRepository.existsById(id)) {
            throw new ActorNotFoundException("Actor con ID " + id + " no encontrado");
        }
        actorRepository.deleteById(id);
    }


}