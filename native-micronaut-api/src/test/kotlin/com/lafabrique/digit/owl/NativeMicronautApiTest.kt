package com.lafabrique.digit.owl

import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class NativeMicronautApiTest {

    @Inject
    lateinit var application: EmbeddedApplication<*>

    @Test
    fun testItWorks() {
        Assertions.assertTrue(application.isRunning)
    }


    companion object {

        var server: EmbeddedServer? = null
        var client: HttpClient? = null

        @BeforeAll
        @JvmStatic
       internal fun setupServer() {
            println("Starting server")
            server = ApplicationContext.run(EmbeddedServer::class.java)
            server?.start()
            client = server
                    ?.applicationContext
                    ?.createBean(HttpClient::class.java, server!!.url)
        }

        @AfterAll
        internal fun stopServer() {
            println("Stop server")
            if (server != null) {
                server!!.stop()
            }
            if (client != null) {
                client!!.stop()
            }
        }

    }

    @Test
    fun testResponse() {
        val request: HttpRequest<Any> = HttpRequest.GET("/todos")
        println("client: $client")
        val body = client?.toBlocking()?.retrieve(request)
        Assertions.assertNotNull(body)
        Assertions.assertEquals("[]", body)
    }
}
