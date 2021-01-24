package com.lafabrique.digit.owl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.util.*

@Component
class ActorService(val repository: ActorRepository) {

    fun list(): MutableIterable<Actor> {

        return repository.findAll()
    }

    fun list(title: String): List<Actor> {
        return repository.findByFullName(title)
    }

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

}
