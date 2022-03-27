package com.xavierbouclet.nativeapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory;

@Configuration(proxyBeanMethods = false)
public class AppConfig {

    @Bean
    ActorRepository actorRepository(R2dbcRepositoryFactory factory) {
        return factory.getRepository(ActorRepository.class);
    }

    @Bean
    MovieRepository movieRepository(R2dbcRepositoryFactory factory) {
        return factory.getRepository(MovieRepository.class);
    }

    @Bean
    ActorMovieRepository actorMovieRepository(R2dbcRepositoryFactory factory) {
        return factory.getRepository(ActorMovieRepository.class);
    }

    @Bean
    ActorService actorService(ActorRepository actorRepository, MovieRepository movieRepository, ActorMovieRepository actorMovieRepository) {
        return new ActorService(actorRepository, movieRepository, actorMovieRepository);
    }

    @Bean
    MovieService movieService(ActorRepository actorRepository, MovieRepository movieRepository, ActorMovieRepository actorMovieRepository) {
        return new MovieService(movieRepository, actorRepository, actorMovieRepository);
    }

    @Bean
    MovieHandler movieHandler(MovieService movieService) {
        return new MovieHandler(movieService);
    }

    @Bean
    ActorHandler actorHandler(ActorService actorService) {
        return new ActorHandler(actorService);
    }
}
