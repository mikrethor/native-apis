package com.lafabrique.digit.owl

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ActorRepository : JpaRepository<Actor, UUID> {

    fun findByFullName(title: String): List<Actor>
}
