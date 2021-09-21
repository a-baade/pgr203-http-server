package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private final ServerSocket serverSocket;

    public HttpServer(int serverPort) throws IOException {
        serverSocket = new ServerSocket(serverPort);

    new Thread(this::handleClients).start();
    }

    private void handleClients() {
        try {
            Socket clientSocket = serverSocket.accept();
            String response = "HTTP/1.1 404 Not found\r\nContent-Length: 0\r\n\r\n";
            clientSocket.getOutputStream().write(response.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);

        Socket clientSocket = serverSocket.accept();

        String requestLine = HttpClient.readLine(clientSocket);

        System.out.println(requestLine);

        String headerLine;
        while (!(headerLine = HttpClient.readLine(clientSocket)).isBlank()) {
            System.out.println(headerLine);
        }

        String responseMessage = "<h1> Hello World <h1>";
        String contentType = "text/html";

        String response =  "Http/1.1 200 OK\r\n" +
                "Content-Length: " + responseMessage.getBytes().length + "r\n" +
                "Content-Type: " + contentType + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                responseMessage;

        clientSocket.getOutputStream().write((response).getBytes());
    }
}
