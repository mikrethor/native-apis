package com.xavierbouclet.nativeapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration(proxyBeanMethods = false)
public class RoutesConfig {

    @Bean
    RouterFunction<ServerResponse> routes(ActorHandler actorHandler, MovieHandler movieHandler) {
        return route()
                .path("/actors", builder -> builder
                        .GET("/{id}", actorHandler::get)
                        .GET("", actorHandler::list)
                        .DELETE("/{id}", actorHandler::delete)
                        .DELETE("/{id}/movies/{movieId}", actorHandler::deleteActorFromMovie)
                        .PUT("", actorHandler::modify)
                        .PUT("/{id}/movies/{movieId}", actorHandler::addActorToMovie)
                        .POST("", actorHandler::add)
                )
                .path("/movies", builder -> builder
                        .GET("/{id}", movieHandler::get)
                        .GET("", movieHandler::list)
                        .DELETE("/{id}", movieHandler::delete)
                        .DELETE("/{id}/actors/{actorId}", movieHandler::deleteActorFromMovie)
                        .PUT("", movieHandler::modify)
                        .PUT("/{id}/actors/{actorId}", movieHandler::addActorToMovie)
                        .POST("", movieHandler::add)
                )
                .build();
    }
}
