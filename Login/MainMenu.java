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
        System.out.println("║                                                ║");
        System.out.println("║    SELAMAT DATANG DI SISTEM SUPERMARKET        ║");
        System.out.println("║                                                ║");
        System.out.println("╚════════════════════════════════════════════════╝");

        int pilih;
        do {
            System.out.println("\n┌────────────────────────────────────────────────┐");
            System.out.println("│              MENU UTAMA                        │");
            System.out.println("├────────────────────────────────────────────────┤");
            System.out.println("│ 1. Login                                       │");
            System.out.println("│ 2. Registrasi (Pengguna Baru)                  │");
            System.out.println("│ 3. Keluar                                      │");
            System.out.println("└────────────────────────────────────────────────┘");
            System.out.print("Pilih Menu: ");
            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1:
                    login();
                    break;
                case 2:
                    registrasi();
                    break;
                case 3:
                    System.out.println("\n╔════════════════════════════════════════════════╗");
                    System.out.println("║     Terima kasih telah menggunakan sistem ini!║");
                    System.out.println("╚════════════════════════════════════════════════╝");
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilih != 3);
    }

    private void login() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║              LOGIN                              ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Nomor Telepon: ");
        String noTelepon = input.nextLine();

        if (loginSystem.login(username, noTelepon)) {
            System.out.println("✅ Login berhasil!");
            User user = loginSystem.getUserSekarang();
            routeToMenu(user);
        } else {
            System.out.println("❌ Login gagal! Username atau nomor telepon salah.");
        }
    }

    private void registrasi() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║      REGISTRASI PENGGUNA BARU                  ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.print("Username baru: ");
        String username = input.nextLine();
        System.out.print("Nomor Telepon: ");
        String noTelepon = input.nextLine();

        if (loginSystem.register(username, noTelepon)) {
            System.out.println("✅ Registrasi berhasil!");
            System.out.println("Silakan login dengan akun Anda.");
            
            // Auto login setelah registrasi
            if (loginSystem.login(username, noTelepon)) {
                System.out.println("✅ Auto-login berhasil!");
                User user = loginSystem.getUserSekarang();
                routeToMenu(user);
            }
        } else {
            System.out.println("❌ Registrasi gagal! Username mungkin sudah terdaftar.");
        }
    }

    private void routeToMenu(User user) {
        if (user.getRole().equals("admin")) {
            AdminMenu adminMenu = new AdminMenu(loginSystem);
            adminMenu.showAdminMenu();
        } else if (user.getRole().equals("kasir")) {
            CashierMenu cashierMenu = new CashierMenu(loginSystem);
            cashierMenu.showCashierMenu();
        } else if (user.getRole().equals("pengguna")) {
            UserMenu userMenu = new UserMenu(loginSystem);
            userMenu.showUserMenu();
        }
    }
}
