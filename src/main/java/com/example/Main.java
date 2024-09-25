package com.example;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws IOException {
        // Créer un serveur HTTP écoutant sur le port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Créer un point d'entrée /hello
        server.createContext("/", new HelloHandler());

        // Démarrer le serveur
        server.setExecutor(null); // utilise le thread par défaut
        server.start();
        System.out.println("Serveur démarré sur le port 8080...");
    }

    // Gestionnaire pour répondre aux requêtes HTTP sur /hello
    static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Hello, World!";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}

