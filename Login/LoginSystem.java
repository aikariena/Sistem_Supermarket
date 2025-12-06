package Login;

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
        
        // Inisialisasi saldo untuk member baru
        MemberBalance mb = new MemberBalance();
        mb.initSaldoMember(username);
        
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

    // ===== CRUD ADMIN =====
    public ArrayList<User> getSemuaAdmin() {
        ArrayList<User> admins = new ArrayList<>();
        for (User u : daftarUser) {
            if (u.getRole().equals("admin")) {
                admins.add(u);
            }
        }
        return admins;
    }

    public User cariAdminByUsername(String username) {
        for (User u : daftarUser) {
            if (u.getRole().equals("admin") && u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    public boolean editAdmin(String usernameOld, String usernameNew, String noTelepon) {
        if (userLoginSekarang == null || !userLoginSekarang.getRole().equals("admin")) {
            return false;
        }

        User admin = cariAdminByUsername(usernameOld);
        if (admin == null) return false;

        // Cek username baru sudah ada atau belum (jika berbeda)
        if (!usernameNew.equalsIgnoreCase(usernameOld)) {
            for (User u : daftarUser) {
                if (u.getUsername().equalsIgnoreCase(usernameNew)) {
                    return false;
                }
            }
        }

        admin.setUsername(usernameNew);
        admin.setNoTelepon(noTelepon);
        simpanDataKeFile();
        return true;
    }

    public boolean hapusAdmin(String username) {
        if (userLoginSekarang == null || !userLoginSekarang.getRole().equals("admin")) {
            return false;
        }

        // Jangan hapus admin default
        User admin = cariAdminByUsername(username);
        if (admin != null && admin.isDefault()) {
            return false;
        }

        return daftarUser.removeIf(u -> u.getRole().equals("admin") && u.getUsername().equalsIgnoreCase(username));
    }

    // ===== CRUD KASIR =====
    public ArrayList<User> getSemuaKasir() {
        ArrayList<User> kasirs = new ArrayList<>();
        for (User u : daftarUser) {
            if (u.getRole().equals("kasir")) {
                kasirs.add(u);
            }
        }
        return kasirs;
    }

    public User cariKasirByUsername(String username) {
        for (User u : daftarUser) {
            if (u.getRole().equals("kasir") && u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    public boolean editKasir(String usernameOld, String usernameNew, String noTelepon) {
        if (userLoginSekarang == null || !userLoginSekarang.getRole().equals("admin")) {
            return false;
        }

        User kasir = cariKasirByUsername(usernameOld);
        if (kasir == null) return false;

        // Cek username baru sudah ada atau belum
        if (!usernameNew.equalsIgnoreCase(usernameOld)) {
            for (User u : daftarUser) {
                if (u.getUsername().equalsIgnoreCase(usernameNew)) {
                    return false;
                }
            }
        }

        kasir.setUsername(usernameNew);
        kasir.setNoTelepon(noTelepon);
        simpanDataKeFile();
        return true;
    }

    public boolean hapusKasir(String username) {
        if (userLoginSekarang == null || !userLoginSekarang.getRole().equals("admin")) {
            return false;
        }

        return daftarUser.removeIf(u -> u.getRole().equals("kasir") && u.getUsername().equalsIgnoreCase(username));
    }

    // ===== CRUD MEMBER =====
    public ArrayList<User> getSemuaMember() {
        ArrayList<User> members = new ArrayList<>();
        for (User u : daftarUser) {
            if (u.getRole().equals("pengguna")) {
                members.add(u);
            }
        }
        return members;
    }

    public User cariMemberByUsername(String username) {
        for (User u : daftarUser) {
            if (u.getRole().equals("pengguna") && u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    public boolean editMember(String usernameOld, String usernameNew, String noTelepon) {
        if (userLoginSekarang == null || !userLoginSekarang.getRole().equals("admin")) {
            return false;
        }

        User member = cariMemberByUsername(usernameOld);
        if (member == null) return false;

        // Cek username baru sudah ada atau belum
        if (!usernameNew.equalsIgnoreCase(usernameOld)) {
            for (User u : daftarUser) {
                if (u.getUsername().equalsIgnoreCase(usernameNew)) {
                    return false;
                }
            }
        }

        member.setUsername(usernameNew);
        member.setNoTelepon(noTelepon);
        simpanDataKeFile();
        return true;
    }

    public boolean hapusMember(String username) {
        if (userLoginSekarang == null || !userLoginSekarang.getRole().equals("admin")) {
            return false;
        }

        return daftarUser.removeIf(u -> u.getRole().equals("pengguna") && u.getUsername().equalsIgnoreCase(username));
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
