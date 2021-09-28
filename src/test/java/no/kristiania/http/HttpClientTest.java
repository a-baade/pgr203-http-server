package no.kristiania.http;

import no.kristiania.http.HttpClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class HttpClientTest {


    @Test
    void shouldReturnStatusCode() throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80,"/html");
        assertEquals(200, client.getStatusCode());
    }

    @Test
    void shouldReturn404StatusCode() throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80,"/this-page-does-not-exist");
        assertEquals(404, client.getStatusCode());
    }

    @Test
    void shouldReturnHeaderField() throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80,"/html");
        assertEquals("text/html; charset=utf-8", client.getHeader("Content-Type"));
    }

    @Test
    void shouldReadMessageBody() throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80,"/html");
        assertTrue(
                client.getMessageBody().startsWith("<!DOCTYPE html>\n<html"),
                "should be html: " + client.getMessageBody());
    }
}