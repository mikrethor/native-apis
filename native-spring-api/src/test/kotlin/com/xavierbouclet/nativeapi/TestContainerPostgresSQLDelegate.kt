package com.xavierbouclet.nativeapi

import org.hibernate.query.sqm.tree.SqmNode.log
import org.slf4j.LoggerFactory
import org.testcontainers.containers.ContainerState
import org.testcontainers.delegate.AbstractDatabaseDelegate
import org.testcontainers.exception.ConnectionCreationException
import org.testcontainers.ext.ScriptUtils
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.util.*

fun <T> loggerFor(clazz: Class<T>) = LoggerFactory.getLogger(clazz)

class TestContainerPostgresSQLDelegate(val container: ContainerState) : AbstractDatabaseDelegate<Connection?>() {

    private val LOG = loggerFor(javaClass)
    override fun createNewConnection(): Connection {
        return try {
            val connectionProps = Properties()
            connectionProps["user"] = "sa"
            connectionProps["password"] = "sa"
            DriverManager.getConnection(
                "jdbc:postgresql://localhost:${container.firstMappedPort}/integration",
                connectionProps
            )
        } catch (e: Exception) {
            LOG.error("Could not obtain PostgresSQL connection")
            throw ConnectionCreationException("Could not obtain PostgresSQL connection", e)
        }
    }

    override fun execute(statement: String, scriptPath: String, lineNumber: Int, continueOnError: Boolean, ignoreFailedDrops: Boolean) {
        try {
            val result: ResultSet = connection!!.prepareStatement(statement).executeQuery()
            result.next()
            if (result.getObject<Integer>(1, Integer::class.java).equals(Integer.valueOf(666))) {
                LOG.debug("Statement {} was applied", statement)
            } else {
                throw ScriptUtils.ScriptStatementFailedException(statement, lineNumber, scriptPath)
            }
        } catch (e: Exception) {
            throw ScriptUtils.ScriptStatementFailedException(statement, lineNumber, scriptPath, e)
        }
    }

    override fun closeConnectionQuietly(connection: Connection?) {
        try {
            connection!!.close()
        } catch (e: Exception) {
            LOG.error("Could not close PostgresSQL connection", e)
        }
    }
}