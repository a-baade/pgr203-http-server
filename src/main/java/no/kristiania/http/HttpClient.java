package no.kristiania.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;

public class HttpClient {

    private final int statusCode;
    private final HashMap<String, String> headerFields = new HashMap<>();
    private String messageBody;

    public HttpClient(String host, int port, String requestTarget) throws IOException {
        Socket socket = new Socket(host, port);

        String request = "GET " + requestTarget + " HTTP/1.1\r\n" +
                "Host: " + host + "\r\n" +
                "Connection: close\r\n" +
                "\r\n";
        socket.getOutputStream().write(request.getBytes());

        String[] statusLine = readLine(socket).split(" ");
        this.statusCode = Integer.parseInt(statusLine[1]);

        String headerLine;
        while (!(headerLine = readLine(socket)).isBlank()) {
            int colonPos = headerLine.indexOf(':');
            String headerField = headerLine.substring(0, colonPos);
            String HeaderValue = headerLine.substring(colonPos+1).trim();
            headerFields.put(headerField,HeaderValue);
        }

        this.messageBody = readBytes(socket, getContentLength());
    }

    private String readBytes(Socket socket, int contentLength) throws IOException {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < contentLength; i++) {
            result.append((char)socket.getInputStream().read());
        }

        return result.toString();
    }

    static String readLine(Socket socket) throws IOException {
        StringBuilder result = new StringBuilder();
        InputStream in = socket.getInputStream();

        int c;
        while ((c = in.read()) != -1 && c != '\r') {
            result.append((char) c);
        }
        //noinspection ResultOfMethodCallIgnored
        in.read();
        return result.toString();
    }

    public int getStatusCode () {
        return statusCode;
    }

    public String getHeader(String headerName) {
        return headerFields.get(headerName);
    }

    public int getContentLength() {
        return Integer.parseInt(getHeader("Content-Length"));
    }

    public String getMessageBody() {
        return messageBody;
    }

    public static void main(String[] args) throws IOException {
        HttpClient client = new HttpClient("httpbin.org", 80, "/html");
        System.out.println(client.getMessageBody());
    }

}