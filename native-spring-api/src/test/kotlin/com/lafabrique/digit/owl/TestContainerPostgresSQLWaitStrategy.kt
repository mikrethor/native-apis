package com.lafabrique.digit.owl

import org.rnorth.ducttape.unreliables.Unreliables
import org.testcontainers.containers.ContainerLaunchException
import org.testcontainers.containers.wait.strategy.AbstractWaitStrategy
import org.testcontainers.delegate.DatabaseDelegate
import java.util.concurrent.TimeUnit

class TestContainerPostgresSQLWaitStrategy : AbstractWaitStrategy() {
    override fun waitUntilReady() {
        // execute select version query until success or timeout
        try {
            Unreliables.retryUntilSuccess(startupTimeout.seconds.toInt(), TimeUnit.SECONDS) {
                rateLimiter.doWhenReady { databaseDelegate.use { databaseDelegate -> databaseDelegate.execute(SELECT_VERSION_QUERY, "", 1, false, false) } }
                true
            }
        } catch (e: Exception) {
            throw ContainerLaunchException(TIMEOUT_ERROR)
        }
    }

    private val databaseDelegate: DatabaseDelegate
        private get() = TestContainerPostgresSQLDelegate(waitStrategyTarget)

    companion object {
        private const val SELECT_VERSION_QUERY = "SELECT 666"
        private const val TIMEOUT_ERROR = "Timed out waiting for PostgresSQL to be accessible for query execution"
    }
}