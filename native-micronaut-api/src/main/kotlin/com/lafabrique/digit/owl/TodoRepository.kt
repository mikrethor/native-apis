package com.lafabrique.digit.owl

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.*

@Repository
interface TodoRepository : CrudRepository<Todo, UUID> {

    fun findByTitle(title: String): List<Todo>
}
