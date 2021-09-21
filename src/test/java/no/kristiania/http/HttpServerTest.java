package no.kristiania.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpServerTest {

    @Test
    void shouldReturn404ForUnknownRequestTarget() throws IOException {
        HttpServer server = new HttpServer(1001);
        HttpClient client = new HttpClient("localHost",1001,"/non-existing");
        assertEquals(404,client.getStatusCode());
    }

}
