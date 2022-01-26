package com.xavierbouclet.nativeapi

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import java.util.UUID

@Repository
interface ActorRepository : CrudRepository<Actor, UUID> {

//    fun findByFullName(title: String): List<Actor>
}
