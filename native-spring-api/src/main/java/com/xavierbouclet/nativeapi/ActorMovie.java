package com.xavierbouclet.nativeapi;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("ACTOR_MOVIE_MAPPING")
public class ActorMovie {
    @Id
    private Long id;

    private UUID actorId;

    private UUID movieId;

    public ActorMovie(UUID actorId, UUID movieId) {
        this.actorId = actorId;
        this.movieId = movieId;
    }

    public UUID getActorId() {
        return actorId;
    }

    public UUID getMovieId() {
        return movieId;
    }
}
