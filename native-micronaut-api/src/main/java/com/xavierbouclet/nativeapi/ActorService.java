package com.xavierbouclet.nativeapi;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.*;

@Singleton
record ActorService(
    @Inject ActorRepository  repository,
    @Inject MovieRepository movieRepository) {

    public Iterable<Actor> list() {
        return repository.findAll();
    }

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
