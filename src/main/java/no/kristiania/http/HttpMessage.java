package no.kristiania.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;

public class HttpMessage {
    public String startLine;
    public final HashMap<String, String> headerFields = new HashMap<>();
    public String messageBody;

    public HttpMessage(Socket socket) throws IOException {
        startLine = HttpMessage.readLine(socket);
        readHeaders(socket);
        messageBody = HttpMessage.readBytes(socket, getContentLength());
    }

    public int getContentLength() {
        return Integer.parseInt(getHeader("Content-Length"));
    }

    public String getHeader(String headerName) {
        return headerFields.get(headerName);
    }

    static String readBytes(Socket socket, int contentLength) throws IOException {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < contentLength; i++) {
            result.append((char)socket.getInputStream().read());
        }

        return result.toString();
    }

    private void readHeaders(Socket socket) throws IOException {
        String headerLine;
        while (!(headerLine = HttpMessage.readLine(socket)).isBlank()) {
            int colonPos = headerLine.indexOf(':');
            String headerField = headerLine.substring(0, colonPos);
            String HeaderValue = headerLine.substring(colonPos+1).trim();
            headerFields.put(headerField,HeaderValue);
        }
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


}
