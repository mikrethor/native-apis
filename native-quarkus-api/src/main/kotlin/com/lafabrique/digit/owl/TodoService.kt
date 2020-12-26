package com.lafabrique.digit.owl

import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional

@Singleton
class TodoService(@Inject val repository: TodoRepository) {

    fun list(): MutableIterable<Todo> {

        return repository.findAll().list<Todo>()
    }

    fun list(title: String): List<Todo> {
        return repository.findByTitle(title)
    }

    @Transactional
    fun add(todo: Todo):Todo {
        repository.persist(todo)
        return todo
    }

    @Transactional
    fun modify(todo: Todo): Todo {
        repository.persist(todo)
        return todo
    }

    @Transactional
    fun delete(id: UUID): UUID {
        repository.deleteById(id)
        return id
    }

}
