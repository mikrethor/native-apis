package com.lafabrique.digit.owl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*

@Component
class MovieService(val repository: MovieRepository, val actorRepository: ActorRepository) {

    fun list(): MutableIterable<Movie> {

        return repository.findAll()
    }

    fun list(title: String): List<Movie> {
        return repository.findByTitle(title)
    }

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
        return repository.findByIdOrNull(id)
    }

}
