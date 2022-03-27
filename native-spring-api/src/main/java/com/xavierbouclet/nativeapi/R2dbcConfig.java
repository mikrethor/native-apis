package com.xavierbouclet.nativeapi;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;


@Configuration(proxyBeanMethods = false)
class R2dbcConfig {

    @Bean
    public ConnectionFactory connectionFactory(R2dbcProperties properties) {
        if (properties.url() == null && properties.username() == null && properties.password() == null) {
            properties = new R2dbcProperties("r2dbc:postgresql://localhost:5432/movie-postgres", "movie-postgres", "movie-postgres");
        }

        return ConnectionFactoryBuilder.withUrl(properties.url()).username(properties.username()).password(properties.password()).build();
    }

    @Bean
    DatabaseClient r2dbcDatabaseClient(ConnectionFactory connectionFactory) {
        return DatabaseClient.builder().connectionFactory(connectionFactory).build();
    }

    @Bean
    R2dbcRepositoryFactory repositoryFactory(DatabaseClient client) {
        return new R2dbcRepositoryFactory(client, new DefaultReactiveDataAccessStrategy(new PostgresDialect()));
    }

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        var initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("install-uuid-ossp.sql"), new ClassPathResource("schema.sql"), new ClassPathResource("data.sql")));
        return initializer;
    }
}