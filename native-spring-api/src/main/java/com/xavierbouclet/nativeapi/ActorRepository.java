package com.xavierbouclet.nativeapi;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
interface ActorRepository extends R2dbcRepository<Actor, UUID> {

    @Query("select a.* " +
            "from actor a " +
            "join actor_movie_mapping amm on a.id = amm.actor_id " +
            "where amm.movie_id = :movieId " +
            "order by a.full_name")
    Flux<Actor> findActorsByMovieId(UUID movieId);
}
