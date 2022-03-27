package com.xavierbouclet.nativeapi;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
interface MovieRepository extends R2dbcRepository<Movie, UUID> {

    @Query("select m.* " +
            "from movie m " +
            "join actor_movie_mapping amm on m.id = amm.movie_id " +
            "where amm.actor_id = :actorId " +
            "order by m.title")
    Flux<Movie> findMoviesByActorId(UUID actorId);
}
