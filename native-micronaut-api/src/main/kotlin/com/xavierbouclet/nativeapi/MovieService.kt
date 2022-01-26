package com.xavierbouclet.nativeapi

import jakarta.inject.Inject
import jakarta.inject.Singleton
import java.util.*

@Singleton
class MovieService(
    @Inject val repository: MovieRepository,
    @Inject val actorRepository: ActorRepository
) {

    fun list(): MutableIterable<Movie> {
        return repository.findAll()
    }

//    fun list(title: String): List<Movie> {
//        return repository.findByTitle(title)
//    }

    fun add(movie: Movie): Movie {
        return repository.save(movie)
    }

    fun modify(movie: Movie): Movie {
        return repository.save(movie)
    }

    fun delete(id: UUID): UUID {
        repository.deleteById(id)
        return id
    }

    fun addActorToMovie(id: UUID, idActor: UUID): Movie {
        val movie = repository.findById(id).get()
        val actor = actorRepository.findById(idActor).get()
        movie.actors?.add(actor)
        actor.movies?.add(movie)
        return repository.save(movie)
    }

    fun get(id: UUID): Movie? {
        return repository.findById(id).orElseGet(null)
    }

    fun removeActorFromMovie(id: UUID, idActor: UUID): Movie {
        val movie = repository.findById(id).get()
        val actor = actorRepository.findById(idActor).get()
        movie.actors?.remove(actor)
        actor.movies?.remove(movie)
        return repository.save(movie)
    }

}
