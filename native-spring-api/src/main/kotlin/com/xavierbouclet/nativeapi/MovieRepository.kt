package com.xavierbouclet.nativeapi

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface MovieRepository : JpaRepository<Movie, UUID> {

//    fun findByTitle(title: String): List<Movie>
}
