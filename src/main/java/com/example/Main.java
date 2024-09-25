package com.example;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Créer un gestionnaire pour les fichiers statiques (HTML, CSS, JS)
        server.createContext("/", new StaticFileHandler());

        // Créer un gestionnaire pour /hello
        server.createContext("/hello", new HelloHandler());

        server.setExecutor(null); // utilise le thread par défaut
        server.start();
        System.out.println("Serveur démarré sur le port 8080...");
    }

    // Gestionnaire pour servir les fichiers statiques
    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String filePath = exchange.getRequestURI().getPath();
            if (filePath.equals("/")) {
                filePath = "/index.html"; // Page d'accueil par défaut
            }

            // Charger les fichiers depuis le classpath (dossier resources)
            InputStream fileStream = getClass().getResourceAsStream("/static" + filePath);

            if (fileStream != null) {
                byte[] fileContent = fileStream.readAllBytes();
                exchange.sendResponseHeaders(200, fileContent.length);

                OutputStream os = exchange.getResponseBody();
                os.write(fileContent);
                os.close();
            } else {
                String response = "404 (Not Found)\n";
                exchange.sendResponseHeaders(404, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }

    // Gestionnaire pour répondre aux requêtes HTTP sur /hello
    static class HelloHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "Hello, from Java!";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
