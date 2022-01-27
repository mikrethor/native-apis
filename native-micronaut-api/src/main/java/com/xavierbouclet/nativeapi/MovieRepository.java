package com.xavierbouclet.nativeapi;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.UUID;

@Repository
interface MovieRepository extends CrudRepository<Movie, UUID> {

//    fun findByTitle(title: String): List<Movie>
}
