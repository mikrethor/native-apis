package com.xavierbouclet.nativeapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
interface MovieRepository extends JpaRepository<Movie, UUID> {

//    public findByTitle(title: String): List<Movie>
}
