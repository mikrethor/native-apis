package com.xavierbouclet.nativeapi

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class ActorService(
    @Autowired val repository: ActorRepository,
    @Autowired val movieRepository: MovieRepository) {

    fun list(): MutableIterable<Actor> {

        return repository.findAll()
    }

//    fun list(title: String): List<Actor> {
//        return repository.findByFullName(title)
//    }

    fun add(actor: Actor): Actor {
        return repository.save(actor)
    }

    fun modify(actor: Actor): Actor {
        return repository.save(actor)
    }

    fun delete(id: UUID): UUID {
        repository.deleteById(id)
        return id
    }

    fun get(id: UUID): Actor? {
        return repository.findByIdOrNull(id)
    }

    fun addMovieToActor(id: UUID, idMovie: UUID): Actor {
        val movie = movieRepository.findById(idMovie).get()
        val actor = repository.findById(id).get()
        movie.actors?.add(actor)
        actor.movies?.add(movie)
        return repository.save(actor)
    }

    fun removeMovieFromActor(id: UUID, idMovie: UUID): Actor {
        val movie = movieRepository.findById(idMovie).get()
        val actor = repository.findById(id).get()
        movie.actors?.remove(actor)
        actor.movies?.remove(movie)
        return repository.save(actor)
    }

}
