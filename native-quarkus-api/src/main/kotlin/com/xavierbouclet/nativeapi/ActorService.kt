package com.xavierbouclet.nativeapi

import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActorService(
    @Inject val repository: ActorRepository,
    @Inject val movieRepository: MovieRepository
) {

    fun list(): Iterable<Actor> {

        return repository.findAll().list()
    }

    fun add(actor: Actor): Actor {
        repository.persist(actor)
        return actor
    }

    fun modify(actor: Actor): Actor {
        repository.persist(actor)
        return actor
    }

    fun delete(id: UUID): UUID {
        repository.deleteById(id)
        return id
    }

    fun get(id: UUID): Actor? {
        return repository.findById(id)
    }

    fun addMovieToActor(id: UUID, idMovie: UUID): Actor {
        val movie = movieRepository.findById(idMovie)
        val actor = repository.findById(id)
        movie.actors?.add(actor)
        actor.movies?.add(movie)
        repository.persist(actor)
        return actor
    }

    fun removeMovieFromActor(id: UUID, idMovie: UUID): Actor {
        val movie = movieRepository.findById(idMovie)
        val actor = repository.findById(id)
        movie.actors?.remove(actor)
        actor.movies?.remove(movie)
        repository.persist(actor)
        return actor
    }

}
