import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import com.sun.net.httpserver.HttpServer;

public class Router {

    // Liste pour stocker toutes les routes définies
    private static List<String> routes = new ArrayList<>();

    public static void configure(HttpServer server) throws IOException {
        // Définir les routes

        // pour les requetes
        routes.add("/hello");       // Page d'accueil (home)

        // pages affichés
        routes.add("/");       // Page d'accueil (home)
        routes.add("/home");   // Route pour la page d'accueil
        routes.add("/page2");  // Route pour la page 2
        
        // Route pour /hello
        server.createContext("/hello", new HelloHandler());
        
        // Associer les routes aux gestionnaires
        server.createContext("/", exchange -> {
            String path = exchange.getRequestURI().getPath();

            // Gestion des fichiers statiques (globaux ou spécifiques aux pages)
            if (path.startsWith("/css") || path.startsWith("/js") || path.startsWith("/pages")) {
                new StaticFileHandler().handle(exchange);
                return;
            }

            // Si la route demandée n'est pas dans la liste des routes définies
            if (!routes.contains(path)) {
                // Redirige vers la page 404
                exchange.getResponseHeaders().set("Location", "/404");
                exchange.sendResponseHeaders(302, -1); // Redirection temporaire (HTTP 302)
            } else {
                // Si la route existe, traiter la page
                new StaticFileHandler().handle(exchange);
            }
        });

        // Route pour /home
        server.createContext("/home", exchange -> {
            exchange.getResponseHeaders().set("Location", "/pages/home/index.html");
            exchange.sendResponseHeaders(302, -1);
        });

        // Route pour /page2
        server.createContext("/page2", exchange -> {
            exchange.getResponseHeaders().set("Location", "/pages/page2/index.html");
            exchange.sendResponseHeaders(302, -1);
        });

        // Gestionnaire catch-all pour les routes non trouvées (404)
        configure404(server);
    }

    public static void configure404(HttpServer server) {
        // Route pour servir la page 404
        server.createContext("/404", new NotFoundHandler());
    }
}
