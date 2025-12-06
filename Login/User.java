package Login;

public class User {
    private String username;
    private String noTelepon;
    private String role; // "admin", "kasir", "pengguna"
    private boolean isDefault; // untuk admin default

    public User(String username, String noTelepon, String role) {
        this.username = username;
        this.noTelepon = noTelepon;
        this.role = role;
        this.isDefault = false;
    }

    public User(String username, String noTelepon, String role, boolean isDefault) {
        this.username = username;
        this.noTelepon = noTelepon;
        this.role = role;
        this.isDefault = isDefault;
    }

    public String getUsername() { return username; }
    public String getNoTelepon() { return noTelepon; }
    public String getRole() { return role; }
    public boolean isDefault() { return isDefault; }

    public String toCSV() {
        return username + ";" + noTelepon + ";" + role + ";" + isDefault;
    }
}
