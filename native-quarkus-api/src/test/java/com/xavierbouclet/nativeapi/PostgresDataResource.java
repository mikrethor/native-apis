package com.xavierbouclet.nativeapi;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.*;

public class PostgresDataResource implements QuarkusTestResourceLifecycleManager {

    static PostgreSQLContainer postgresSQLServer = new PostgreSQLContainer<>("postgres:14.1")
            .withDatabaseName("db")
            .withUsername("sa")
            .withPassword("sa")
            .withExposedPorts(5432);

    @Override
    public Map<String, String> start() {
        postgresSQLServer.start();
        return Map.of(
                "quarkus.datasource.db-kind", "postgresql",
                "quarkus.datasource.username", postgresSQLServer.getUsername(),
                "quarkus.datasource.password", postgresSQLServer.getPassword(),
                "quarkus.datasource.jdbc.url", postgresSQLServer.getJdbcUrl(),
                "quarkus.datasource.jdbc.max-size", "16"
        );
    }

    @Override
    public void stop() {
        postgresSQLServer.stop();
    }
}
