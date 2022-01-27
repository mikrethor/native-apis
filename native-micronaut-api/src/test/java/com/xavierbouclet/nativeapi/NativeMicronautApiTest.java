package com.xavierbouclet.nativeapi;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


@MicronautTest
class NativeMicronautApiTest {

    @Inject
    EmbeddedApplication application;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }


    private static EmbeddedServer server;
    private static HttpClient client;

    @BeforeAll
    static void setupServer() {
        System.out.println("Starting server");
        server = ApplicationContext.run(EmbeddedServer.class);
        server.start();
        client = server
                .getApplicationContext()
                .createBean(HttpClient.class, server.getURL());
    }

    @AfterAll
    static void stopServer() {
        System.out.println("Stop server");
        if (server != null) {
            server.stop();
        }
        if (client != null) {
            client.stop();
        }
    }


    @Test
    void testResponse() {
        HttpRequest request = HttpRequest.GET("/actors");
        System.out.println("client: $client");
        var body = client.toBlocking().retrieve(request);
        Assertions.assertNotNull(body);
        Assertions.assertEquals("[]", body);
    }
}
