package Login;

import java.util.Scanner;

public class MainMenu {
    private LoginSystem loginSystem;
    private Scanner input;

    public MainMenu() {
        this.loginSystem = new LoginSystem();
        this.input = new Scanner(System.in);
    }

    public static void main(String[] args) {
        MainMenu menu = new MainMenu();
        menu.showMainMenu();
    }

    public void showMainMenu() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║    SELAMAT DATANG DI SISTEM SUPERMARKET        ║");
        System.out.println("╚════════════════════════════════════════════════╝");

        int pilih;
        do {
            System.out.println("\n1. Login");
            System.out.println("2. Registrasi Pembeli");
            System.out.println("3. Keluar");
            System.out.print("Pilih: ");
            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1: login(); break;
                case 2: registrasi(); break;
                case 3: System.out.println("Bye bye!"); return;
                default: System.out.println("Input salah!");
            }
        } while (pilih != 3);
    }

    private void login() {
        System.out.println("\n--- PILIH TIPE LOGIN ---");
        System.out.println("1. ADMIN (Pakai Password)");
        System.out.println("2. KASIR (Pakai Password)");
        System.out.println("3. PEMBELI (Pakai No. HP)");
        System.out.print("Pilih: ");
        int tipe = input.nextInt();
        input.nextLine();

        System.out.print("Username: ");
        String username = input.nextLine();
        
        String credential = "";
        
        if (tipe == 1 || tipe == 2) {
            System.out.print("Password: ");
            credential = input.nextLine();
        } else if (tipe == 3) {
            System.out.print("Nomor Telepon: ");
            credential = input.nextLine();
        } else {
            System.out.println("Pilihan salah.");
            return;
        }

        if (loginSystem.login(username, credential)) {
            User user = loginSystem.getUserSekarang();
            
            // Validasi Role agar tidak salah kamar
            if ((tipe == 1 && !user.getRole().equals("admin")) ||
                (tipe == 2 && !user.getRole().equals("kasir")) ||
                (tipe == 3 && !user.getRole().equals("pengguna"))) {
                System.out.println("Role akun tidak sesuai!");
                return;
            }
            
            System.out.println("Login berhasil!");
            routeToMenu(user);
        } else {
            System.out.println("Gagal login. Cek data anda.");
        }
    }

    private void registrasi() {
        System.out.println("\n--- REGISTRASI PEMBELI ---");
        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Nomor Telepon: ");
        String noTelepon = input.nextLine();

        if (loginSystem.register(username, noTelepon)) {
            System.out.println("Registrasi berhasil! Silakan login.");
        } else {
            System.out.println("Username sudah terdaftar.");
        }
    }

    private void routeToMenu(User user) {
        if (user.getRole().equals("admin")) {
            new AdminMenu(loginSystem).showAdminMenu();
        } else if (user.getRole().equals("kasir")) {
            new CashierMenu(loginSystem).showCashierMenu();
        } else if (user.getRole().equals("pengguna")) {
            new UserMenu(loginSystem).showUserMenu();
        }
    }
}