package com.lafabrique.digit.owl

import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer


class PostgresSQLExtension : BeforeAllCallback, AfterAllCallback {

    internal class SpecifiedPostgresSQLContainer(imageName: String) : PostgreSQLContainer<SpecifiedPostgresSQLContainer>(imageName)

    companion object {
        private val postgresSQLServer = SpecifiedPostgresSQLContainer("postgres:15.1")
            .withDatabaseName("integration")
            .withUsername("sa")
            .withPassword("sa")
            .waitingFor(
                TestContainerPostgresSQLWaitStrategy()
            )

    }

    override fun beforeAll(p0: ExtensionContext?) {
        postgresSQLServer.start()
        println("Postgres has started")
    }

    override fun afterAll(p0: ExtensionContext?) {
        postgresSQLServer.stop()
    }

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
            TestPropertyValues.of(
                "spring.datasource.url=${postgresSQLServer.jdbcUrl}",
                "spring.datasource.username=${postgresSQLServer.username}",
                "spring.datasource.password=${postgresSQLServer.password}",
                "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect",
                "spring.jpa.hibernate.ddl-auto=update"
            ).applyTo(configurableApplicationContext.environment)
        }
    }
}
