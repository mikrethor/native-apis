package com.lafabrique.digit.owl

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MovieRepository : JpaRepository<Movie, UUID> {

    fun findByTitle(title: String): List<Movie>
}
