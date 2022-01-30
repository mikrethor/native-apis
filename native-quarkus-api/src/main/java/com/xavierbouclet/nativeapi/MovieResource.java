package com.xavierbouclet.nativeapi;

import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.net.URI;
import java.util.*;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    private MovieService service;

    @Inject
    public MovieResource(MovieService service) {
        this.service = service;
    }

    @GET
    public Response list() {
        return Response.ok(service.list()).
                build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") UUID id) {
        return Response.ok(service.get(id)).

                build();
    }


    @POST
    public Response add(Movie movie) {
        var title = movie.title;

        if (("Kung Fury".equals(title) || "Kung Fury 2".equals(title))) {
            return Response.status(Status.CREATED.getStatusCode()).entity(service.add(movie)).build();
        }

        var problem = Problem.builder()
                .withType(URI.create("https://mymovieapp.com/probs/cant-add-other-movie-kung-fury"))
                .withTitle("Kung Fury movies are the only ones")
                .withDetail("How dare you add this movie ? Only Kung Fury movies are worthy.")
                .withStatus(Status.FORBIDDEN)
                .build();

        return Response.status(Status.FORBIDDEN.getStatusCode()).
                entity(problem).
                build();

    }


    @PUT
    public Response modify(Movie movie) {
        return Response.accepted(service.modify(movie)).build();
    }


    @DELETE
    @Path("/{id}")
    public Response delete(
            @PathParam("id") UUID id) {
        return Response.ok(service.delete(id)).build();
    }

    @PUT
    @Path("/{id}/actors/{actorId}")
    public Response addActorToMovie(@PathParam("id") UUID id, @PathParam("actorId") UUID actorId) {
        return Response.accepted(service.addActorToMovie(id, actorId)).build();

    }

    @DELETE
    @Path("/{id}/actors/{actorId}")
    public Response deleteActorFromMovie(@PathParam("id") UUID id, @PathParam("actorId") UUID actorId) {
        return Response.ok(service.removeActorFromMovie(id, actorId)).build();
    }
}

