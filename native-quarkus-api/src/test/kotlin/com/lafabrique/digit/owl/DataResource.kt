package com.lafabrique.digit.owl

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import org.testcontainers.containers.PostgreSQLContainer
import java.util.*

class DataResource : QuarkusTestResourceLifecycleManager {

    internal class SpecifiedPostgresSQLContainer(val imageName: String) : PostgreSQLContainer<SpecifiedPostgresSQLContainer>(imageName)

    companion object {
        private val postgresSQLServer = SpecifiedPostgresSQLContainer("postgres:13.1")
                .withDatabaseName("db")
                .withUsername("sa")
                .withPassword("sa").
                withExposedPorts(5432)

    }

    override fun start(): Map<String, String> {
        postgresSQLServer.start()
        val propertiesMap: MutableMap<String, String> = HashMap()


        propertiesMap["quarkus.datasource.db-kind"]="postgresql"
        propertiesMap["quarkus.datasource.username"]=postgresSQLServer.username
        propertiesMap["quarkus.datasource.password"]=postgresSQLServer.password
        propertiesMap["quarkus.datasource.jdbc.url"]=postgresSQLServer.jdbcUrl
        propertiesMap["quarkus.datasource.jdbc.max-size"]="16"
        return propertiesMap
    }

    override fun stop() {
        postgresSQLServer.stop()
    }
}
