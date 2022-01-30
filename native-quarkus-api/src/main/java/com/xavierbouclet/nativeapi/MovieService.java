package com.xavierbouclet.nativeapi;

import java.util.*;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

@Singleton
@Transactional
class MovieService {

    private MovieRepository repository;
    private ActorRepository actorRepository;

    @Inject
    public MovieService(MovieRepository repository,
            ActorRepository actorRepository) {
        this.repository = repository;
        this.actorRepository = actorRepository;
    }

    public Iterable<Movie> list() {
        return repository.findAll().list();
    }

    public Movie add(Movie movie) {
        repository.persist(movie);
        return movie;
    }

    public Movie modify(Movie movie) {
        repository.persist(movie);
        return movie;
    }

    public UUID delete(UUID id) {
        repository.deleteById(id);
        return id;
    }

    public Movie addActorToMovie(UUID id, UUID idActor) {
        var movie = repository.findById(id);
        var actor = actorRepository.findById(idActor);
        movie.actors.add(actor);
        actor.movies.add(movie);
        return modify(movie);
    }

    public Movie get(UUID id) {
        return repository.findById(id);
    }

    public Movie removeActorFromMovie(UUID id, UUID idActor) {
        var movie = repository.findById(id);
        var actor = actorRepository.findById(idActor);
        movie.actors.remove(actor);
        actor.movies.remove(movie);
        return modify(movie);
    }

}
