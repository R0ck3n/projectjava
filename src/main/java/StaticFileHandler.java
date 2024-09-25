import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StaticFileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String filePath = exchange.getRequestURI().getPath();
        if (filePath.equals("/")) {
            filePath = "/index.html"; // Page d'accueil par défaut
        }

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
