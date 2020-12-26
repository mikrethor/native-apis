package com.lafabrique.digit.owl

import org.springframework.stereotype.Component
import java.util.*

@Component
class TodoService(val repository: TodoRepository) {

    fun list(): MutableIterable<Todo> {

        return repository.findAll()
    }

    fun list(title: String): List<Todo> {
        return repository.findByTitle(title)
    }

    fun add(todo: Todo): Todo {
        return repository.save(todo)
    }

    fun modify(todo: Todo): Todo {
        return repository.save(todo)
    }

    fun delete(id: UUID): UUID {
        repository.deleteById(id)
        return id
    }

}
