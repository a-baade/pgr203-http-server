package no.kristiania.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);

        Socket clientSocket = serverSocket.accept();
        String requestLine = HttpClient.readLine(clientSocket);

        System.out.println(requestLine);
    }
}
