package com.xavierbouclet.nativeapi;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
interface ActorMovieRepository extends R2dbcRepository<ActorMovie, Long> {

    Flux<ActorMovie> findAllByActorId(UUID actorId);

    Flux<ActorMovie> findAllByMovieId(UUID movieId);

    @Query("delete from actor_movie_mapping amm where amm.actor_id = :actorId and amm.movie_id = :movieId ")
    Mono<Void> deleteByMovieIdAndActorId(UUID movieId, UUID actorId);
}
