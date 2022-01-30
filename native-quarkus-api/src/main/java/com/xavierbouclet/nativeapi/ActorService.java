package com.xavierbouclet.nativeapi;

import java.util.*;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
@Transactional
class ActorService {

    private ActorRepository repository;
    private MovieRepository movieRepository;

    @Inject
    public ActorService(
            ActorRepository repository,
            MovieRepository movieRepository) {
        this.repository = repository;
        this.movieRepository = movieRepository;
    }

    public List<Actor> list() {
        return repository.findAll().list();
    }


    public Actor add(Actor actor) {
        repository.persist(actor);
        return actor;
    }

    public Actor modify(Actor actor) {
        repository.persist(actor);
        return actor;
    }

    public UUID delete(UUID id) {
        repository.deleteById(id);
        return id;
    }

    public Actor get(UUID id) {
        return repository.findById(id);
    }

    public Actor addMovieToActor(UUID id, UUID idMovie) {
        var movie = movieRepository.findById(idMovie);
        var actor = repository.findById(id);
        movie.actors.add(actor);
        actor.movies.add(movie);
        return modify(actor);
    }

    public Actor removeMovieFromActor(UUID id, UUID idMovie) {
        var movie = movieRepository.findById(idMovie);
        var actor = repository.findById(id);
        movie.actors.remove(actor);
        actor.movies.remove(movie);
        return modify(actor);
    }

}
