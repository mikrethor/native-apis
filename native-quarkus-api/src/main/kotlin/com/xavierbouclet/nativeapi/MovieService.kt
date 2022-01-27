package com.xavierbouclet.nativeapi

import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieService(
    @Inject val repository: MovieRepository,
    @Inject val actorRepository: ActorRepository
) {

    fun list(): Iterable<Movie> {
        return repository.findAll().list()
    }

    fun add(movie: Movie): Movie {
        repository.persist(movie)
        return movie
    }

    fun modify(movie: Movie): Movie {
        repository.persist(movie)
        return movie

    }

    fun delete(id: UUID): UUID {
        repository.deleteById(id)
        return id
    }

    fun addActorToMovie(id: UUID, idActor: UUID): Movie {
        val movie = repository.findById(id)
        val actor = actorRepository.findById(idActor)
        movie.actors?.add(actor)
        actor.movies?.add(movie)
        repository.persist(movie)
        return movie
    }

    fun get(id: UUID): Movie? {
        return repository.findById(id)
    }

    fun removeActorFromMovie(id: UUID, idActor: UUID): Movie {
        val movie = repository.findById(id)
        val actor = actorRepository.findById(idActor)
        movie.actors?.remove(actor)
        actor.movies?.remove(movie)
        repository.persist(movie)
        return movie
    }

}
