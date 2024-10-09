import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, firstname, lastname, created_at FROM user";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            // Vérifier si la requête renvoie des résultats
            System.out.println("Exécution de la requête...");

            while (resultSet.next()) {
                System.out.println("Utilisateur trouvé dans la base de données");

                int id = resultSet.getInt("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String createdAt = resultSet.getString("created_at");

                users.add(new User(id, firstname, lastname, createdAt));
            }

            // Vérifier si des utilisateurs ont été ajoutés à la liste
            if (users.isEmpty()) {
                System.out.println("Aucun utilisateur trouvé.");
            } else {
                System.out.println(users.size() + " utilisateur(s) trouvé(s).");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
