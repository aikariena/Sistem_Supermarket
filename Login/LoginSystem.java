import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LoginSystem {
    private ArrayList<User> daftarUser = new ArrayList<>();
    private final String NAMA_FILE = "users.txt";
    private User userLoginSekarang = null;

    public LoginSystem() {
        bacaDataDariFile();
        // Jika file kosong, buat admin default
        if (daftarUser.isEmpty()) {
            User adminDefault = new User("admin", "0812345678", "admin", true);
            daftarUser.add(adminDefault);
            simpanDataKeFile();
        }
    }

    private void bacaDataDariFile() {
        File file = new File(NAMA_FILE);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String baris = fileScanner.nextLine();
                String[] data = baris.split(";");

                if (data.length == 4) {
                    String username = data[0];
                    String noTelepon = data[1];
                    String role = data[2];
                    boolean isDefault = Boolean.parseBoolean(data[3]);
                    daftarUser.add(new User(username, noTelepon, role, isDefault));
                }
            }
        } catch (Exception e) {
            System.out.println("Error saat membaca file user: " + e.getMessage());
        }
    }

    private void simpanDataKeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NAMA_FILE))) {
            for (User u : daftarUser) {
                writer.write(u.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saat menyimpan user: " + e.getMessage());
        }
    }

    public boolean login(String username, String noTelepon) {
        for (User u : daftarUser) {
            if (u.getUsername().equalsIgnoreCase(username) && u.getNoTelepon().equals(noTelepon)) {
                userLoginSekarang = u;
                return true;
            }
        }
        return false;
    }

    public boolean register(String username, String noTelepon) {
        // Pengguna (user) bisa registrasi sendiri
        for (User u : daftarUser) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return false; // Username sudah ada
            }
        }
        User userBaru = new User(username, noTelepon, "pengguna");
        daftarUser.add(userBaru);
        simpanDataKeFile();
        return true;
    }

    public boolean tambahAdmin(String username, String noTelepon) {
        // Hanya admin yang bisa menambah admin baru
        if (userLoginSekarang == null || !userLoginSekarang.getRole().equals("admin")) {
            return false;
        }

        for (User u : daftarUser) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return false; // Username sudah ada
            }
        }

        User adminBaru = new User(username, noTelepon, "admin");
        daftarUser.add(adminBaru);
        simpanDataKeFile();
        return true;
    }

    public boolean tambahKasir(String username, String noTelepon) {
        // Hanya admin yang bisa menambah kasir
        if (userLoginSekarang == null || !userLoginSekarang.getRole().equals("admin")) {
            return false;
        }

        for (User u : daftarUser) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return false; // Username sudah ada
            }
        }

        User kasirBaru = new User(username, noTelepon, "kasir");
        daftarUser.add(kasirBaru);
        simpanDataKeFile();
        return true;
    }

    public void logout() {
        userLoginSekarang = null;
    }

    public User getUserSekarang() {
        return userLoginSekarang;
    }

    public boolean isLogin() {
        return userLoginSekarang != null;
    }
}
