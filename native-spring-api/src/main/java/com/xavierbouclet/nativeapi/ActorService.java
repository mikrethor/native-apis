package com.xavierbouclet.nativeapi;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActorService {

    private final ActorRepository repository;
    private final MovieRepository movieRepository;

    public ActorService(ActorRepository repository, MovieRepository movieRepository) {
        this.repository = repository;
        this.movieRepository = movieRepository;
    }

    public List<Actor> list() {

        return repository.findAll();
    }

//    public list(title: String): List<Actor> {
//        return repository.findByFullName(title)
//    }

    public Actor add(Actor actor) {
        return repository.save(actor);
    }

    public Actor modify(Actor actor) {
        return repository.save(actor);
    }

    public UUID delete(UUID id) {
        repository.deleteById(id);
        return id;
    }

    public Actor get(UUID id) {
        return repository.findById(id).get();
    }

    public Actor
    addMovieToActor(UUID id, UUID idMovie) {

        var movie = movieRepository.findById(idMovie).get();
        var actor = repository.findById(id).get();
        movie.getActors().add(actor);
        actor.getMovies().add(movie);
        return repository.save(actor);
    }

    public Actor removeMovieFromActor(UUID id, UUID idMovie) {
        var movie = movieRepository.findById(idMovie).get();
        var actor = repository.findById(id).get();
        movie.getActors().remove(actor);
        actor.getMovies().remove(movie);
        return repository.save(actor);
    }

}
