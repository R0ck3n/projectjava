import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Router {

    // Liste pour stocker toutes les routes définies
    private static List<String> routes = new ArrayList<>();

    public static void configure(HttpServer server) throws IOException {
        // Définir les routes
        routes.add("/");       // Page d'accueil
        routes.add("/hello");   // Route /hello

        // Associer les routes aux gestionnaires
        server.createContext("/", new StaticFileHandler());
        server.createContext("/hello", new HelloHandler());

        // Gestionnaire catch-all pour les routes non trouvées (404)
        configure404(server);
    }

    public static void configure404(HttpServer server) {
        // Route pour servir la page 404
        server.createContext("/404", new NotFoundHandler());

        // Gestionnaire pour toute autre requête non définie
        server.createContext("/", exchange -> {
            String path = exchange.getRequestURI().getPath();

            // Si la route demandée n'est pas dans la liste des routes définies
            if (!routes.contains(path)) {
                // Redirige vers la page 404
                exchange.getResponseHeaders().set("Location", "/404");
                exchange.sendResponseHeaders(302, -1); // Redirection temporaire (HTTP 302)
            } else {
                // Si la route existe, rien ne change
                exchange.sendResponseHeaders(404, -1);
            }
        });
    }
}
