package com.xavierbouclet.nativeapi;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresSQLExtension implements AfterAllCallback {

    private static PostgreSQLContainer<?> postgresSQLServer = new PostgreSQLContainer<>("postgres:14.1")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");


    static {
        postgresSQLServer.start();
    }

    public void afterAll(ExtensionContext p0) {
        postgresSQLServer.stop();
    }



    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public Initializer() {
        }

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.r2dbc.url=" + postgresSQLServer.getJdbcUrl().replace("jdbc", "r2dbc"),
                    "spring.r2dbc.username=" + postgresSQLServer.getUsername(),
                    "spring.r2dbc.password=" + postgresSQLServer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }


    }
}
