import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class UserHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Vérifie que la méthode HTTP est bien GET
        if (!"GET".equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(405, -1); // Méthode non autorisée
            return;
        }

        try {
            // Récupérer la liste des utilisateurs depuis la base de données
            UserDAO userDAO = new UserDAO();
            List<User> users = userDAO.getAllUsers();

            // Configurer l'ObjectMapper avec le module JavaTimeModule
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule()); // Permet de gérer LocalDateTime

            // Convertir la liste des utilisateurs en JSON
            String jsonResponse = objectMapper.writeValueAsString(users);

            // Préparer la réponse JSON
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);

            // Envoyer la réponse JSON
            OutputStream os = exchange.getResponseBody();
            os.write(jsonResponse.getBytes());
            os.close();

        } catch (Exception e) {
            // En cas d'erreur, on affiche l'erreur sur la console
            e.printStackTrace();

            // Préparer une réponse d'erreur pour le client
            String response = "Erreur interne du serveur.";
            exchange.sendResponseHeaders(500, response.getBytes().length);

            // Envoyer la réponse d'erreur au client
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
