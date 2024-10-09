import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/java?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // Ton utilisateur MySQL
    private static final String PASSWORD = ""; // Ton mot de passe MySQL

    public static Connection getConnection() throws SQLException {
        try {
            // Forcer le chargement du driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
