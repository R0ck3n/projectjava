import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

public class StaticFileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String filePath = exchange.getRequestURI().getPath();

        // Si l'utilisateur demande la racine "/", charger index.html par défaut
        if (filePath.equals("/")) {
            filePath = "/views/home/index.html";
        }

        // Charger le fichier depuis le dossier "static" dans les ressources
        InputStream fileStream = getClass().getResourceAsStream("/static" + filePath);

        if (fileStream != null) {
            // Deviner le type MIME en fonction du fichier
            String contentType = URLConnection.guessContentTypeFromName(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";  // Type par défaut
            }

            // Lire le fichier et envoyer la réponse
            byte[] fileContent = fileStream.readAllBytes();
            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, fileContent.length);

            OutputStream os = exchange.getResponseBody();
            os.write(fileContent);
            os.close();
        } else {
            // Réponse 404 si le fichier n'est pas trouvé
            String response = "404 (Not Found)\n";
            exchange.sendResponseHeaders(404, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
