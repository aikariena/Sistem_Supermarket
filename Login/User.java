package Login;

public class User {
    private String username;
    private String password; // NEW: Ini saja tambahan barunya
    private String noTelepon;
    private String role; 
    private boolean isDefault; 

    // Constructor Update: Ada passwordnya
    public User(String username, String password, String noTelepon, String role, boolean isDefault) {
        this.username = username;
        this.password = password;
        this.noTelepon = noTelepon;
        this.role = role;
        this.isDefault = isDefault;
    }

    // Constructor Lama (Overloading) - Supaya file lain yang pake cara lama GAK ERROR
    public User(String username, String noTelepon, String role) {
        this.username = username;
        this.password = "-"; // Default password strip
        this.noTelepon = noTelepon;
        this.role = role;
        this.isDefault = false;
    }

    // Method Getter (NAMA TETAP SAMA)
    public String getUsername() { return username; }
    public String getPassword() { return password; } // Baru
    public String getNoTelepon() { return noTelepon; }
    public String getRole() { return role; }
    public boolean isDefault() { return isDefault; }

    // Method Setter (NAMA TETAP SAMA)
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; } // Baru
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }

    // CSV format jadi 5 kolom (Tambah password)
    public String toCSV() {
        return username + ";" + password + ";" + noTelepon + ";" + role + ";" + isDefault;
    }
}