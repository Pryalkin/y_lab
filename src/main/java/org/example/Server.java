package org.example;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private int port;

    public Server(int port) {
        this.port = port;
    }

    void start() {
        try (var server = new ServerSocket(this.port)) {
            System.out.println("Сервер запущен на порту 8080");
            while (true) {
                var socket = server.accept();
                var thread = new Handler(socket);
                thread.start();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        var port = Integer.parseInt(args[0]);
        new Server(port).start();
    }
}
