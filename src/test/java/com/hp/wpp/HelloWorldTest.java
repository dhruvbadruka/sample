package com.hp.wpp;

import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

public class HelloWorldTest {

    private TJWSEmbeddedJaxrsServer server;

    @Before
    public void setup() {
        server = new TJWSEmbeddedJaxrsServer();
        server.setPort(9000);
        server.start();
        server.getDeployment().getRegistry().addPerRequestResource(HelloWorld.class);

    }

    @After
    public void tearDown() {
        server.stop();
    }


    @Test
    public void helloWorld() throws Exception {
        get("http://localhost:9000/hell1o").
                then().
                assertThat().statusCode(200).and().body(equalTo("Hello World"));
    }

    @Test
    public void echoAPI() throws Exception {
        UUID uuid = UUID.randomUUID();
        get("http://localhost:9000/hello/echo/" + uuid.toString()).
                then().
                assertThat().statusCode(200).body(equalTo(uuid.toString()));
    }
}