public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String createdAt;

    public User(int id, String firstname, String lastname, String createdAt) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.createdAt = createdAt;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
