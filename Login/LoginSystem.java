package Login;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class LoginSystem {
    // Pakai HashMap biar Cepat (Optimasi)
    private HashMap<String, User> userMap = new HashMap<>();
    private final String NAMA_FILE = "users.txt";
    private User userLoginSekarang = null;

    public LoginSystem() {
        bacaDataDariFile();
        // Buat Default Admin jika file kosong
        if (userMap.isEmpty()) {
            // Password admin default: admin123
            User adminDefault = new User("admin", "admin123", "0812345678", "admin", true);
            userMap.put("admin", adminDefault);
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

                // Kita baca 5 kolom sekarang
                if (data.length == 5) {
                    String u = data[0];
                    String p = data[1]; // password
                    String t = data[2]; // telp
                    String r = data[3];
                    boolean d = Boolean.parseBoolean(data[4]);
                    
                    User user = new User(u, p, t, r, d);
                    userMap.put(u.toLowerCase(), user);
                }
            }
        } catch (Exception e) {
            System.out.println("Error baca file: " + e.getMessage());
        }
    }

    private void simpanDataKeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NAMA_FILE))) {
            for (User u : userMap.values()) {
                writer.write(u.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error simpan file: " + e.getMessage());
        }
    }

    // === LOGIC LOGIN YANG DIMINTA ===
    // Parameter kedua namanya 'credential' karena bisa berisi Password atau NoTelp
    public boolean login(String username, String credential) {
        String key = username.toLowerCase();
        
        if (userMap.containsKey(key)) {
            User u = userMap.get(key);
            
            // ADMIN & KASIR -> Cek Password
            if (u.getRole().equals("admin") || u.getRole().equals("kasir")) {
                if (u.getPassword().equals(credential)) {
                    userLoginSekarang = u;
                    return true;
                }
            } 
            // USER PEMBELI -> Cek No Telepon
            else if (u.getRole().equals("pengguna")) {
                if (u.getNoTelepon().equals(credential)) {
                    userLoginSekarang = u;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean register(String username, String noTelepon) {
        if (userMap.containsKey(username.toLowerCase())) return false;

        // User passwordnya "-"
        User userBaru = new User(username, "-", noTelepon, "pengguna", false);
        userMap.put(username.toLowerCase(), userBaru);
        simpanDataKeFile();
        
        MemberBalance mb = new MemberBalance();
        mb.initSaldoMember(username);
        return true;
    }

    // Tambah Admin (Wajib Password)
    public boolean tambahAdmin(String username, String password, String noTelepon) {
        if (userLoginSekarang == null || !userLoginSekarang.getRole().equals("admin")) return false;
        if (userMap.containsKey(username.toLowerCase())) return false;
        
        User adminBaru = new User(username, password, noTelepon, "admin", false);
        userMap.put(username.toLowerCase(), adminBaru);
        simpanDataKeFile();
        return true;
    }

    // Tambah Kasir (Wajib Password)
    public boolean tambahKasir(String username, String password, String noTelepon) {
        if (userLoginSekarang == null || !userLoginSekarang.getRole().equals("admin")) return false;
        if (userMap.containsKey(username.toLowerCase())) return false;

        User kasirBaru = new User(username, password, noTelepon, "kasir", false);
        userMap.put(username.toLowerCase(), kasirBaru);
        simpanDataKeFile();
        return true;
    }

    // --- Method Helper untuk AdminMenu (NAMA VARIABLE TETAP SAMA) ---
    public ArrayList<User> getSemuaAdmin() {
        ArrayList<User> list = new ArrayList<>();
        for(User u : userMap.values()) if(u.getRole().equals("admin")) list.add(u);
        return list;
    }
    public ArrayList<User> getSemuaKasir() {
        ArrayList<User> list = new ArrayList<>();
        for(User u : userMap.values()) if(u.getRole().equals("kasir")) list.add(u);
        return list;
    }
    public ArrayList<User> getSemuaMember() {
        ArrayList<User> list = new ArrayList<>();
        for(User u : userMap.values()) if(u.getRole().equals("pengguna")) list.add(u);
        return list;
    }

    public User cariAdminByUsername(String u) { return userMap.get(u.toLowerCase()); }
    public User cariKasirByUsername(String u) { return userMap.get(u.toLowerCase()); }
    public User cariMemberByUsername(String u) { return userMap.get(u.toLowerCase()); }

    // Edit dengan support Password baru
    public boolean editAdmin(String usernameOld, String usernameNew, String passwordNew, String noTelepon) {
        if (userLoginSekarang == null || !userLoginSekarang.getRole().equals("admin")) return false;
        String oldKey = usernameOld.toLowerCase();
        String newKey = usernameNew.toLowerCase();
        
        if (!userMap.containsKey(oldKey)) return false;
        User u = userMap.get(oldKey);

        if (!oldKey.equals(newKey)) {
            if (userMap.containsKey(newKey)) return false;
            userMap.remove(oldKey);
            u.setUsername(usernameNew);
            userMap.put(newKey, u);
        }
        
        // Update data
        u.setPassword(passwordNew);
        u.setNoTelepon(noTelepon);
        simpanDataKeFile();
        return true;
    }

    public boolean editKasir(String usernameOld, String usernameNew, String passwordNew, String noTelepon) {
        // Sama dengan admin logic
        return editAdmin(usernameOld, usernameNew, passwordNew, noTelepon);
    }
    
    // Edit Member (Tidak ubah password)
    public boolean editMember(String usernameOld, String usernameNew, String noTelepon) {
        if (userLoginSekarang == null || !userLoginSekarang.getRole().equals("admin")) return false;
        String oldKey = usernameOld.toLowerCase();
        String newKey = usernameNew.toLowerCase();
        
        if (!userMap.containsKey(oldKey)) return false;
        User u = userMap.get(oldKey);

        if (!oldKey.equals(newKey)) {
            if (userMap.containsKey(newKey)) return false;
            userMap.remove(oldKey);
            u.setUsername(usernameNew);
            userMap.put(newKey, u);
        }
        u.setNoTelepon(noTelepon);
        simpanDataKeFile();
        return true;
    }

    public boolean hapusUser(String username) {
        if (userLoginSekarang == null || !userLoginSekarang.getRole().equals("admin")) return false;
        String key = username.toLowerCase();
        User u = userMap.get(key);
        
        if(u != null && !u.isDefault()) {
            userMap.remove(key);
            simpanDataKeFile();
            return true;
        }
        return false;
    }
    // Wrapper method agar AdminMenu tidak error
    public boolean hapusAdmin(String u) { return hapusUser(u); }
    public boolean hapusKasir(String u) { return hapusUser(u); }
    public boolean hapusMember(String u) { return hapusUser(u); }

    public void logout() { userLoginSekarang = null; }
    public User getUserSekarang() { return userLoginSekarang; }
    public boolean isLogin() { return userLoginSekarang != null; }
}