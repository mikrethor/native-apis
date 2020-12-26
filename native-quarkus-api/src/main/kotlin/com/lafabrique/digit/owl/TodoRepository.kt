package com.lafabrique.digit.owl

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class TodoRepository : PanacheRepositoryBase<Todo, UUID> {

    fun findByTitle(title: String): List<Todo> {
        return find("title", title).list<Todo>()
    }

}
