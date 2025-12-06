package Login;

import java.util.ArrayList;
import java.util.Scanner;
import Gudang.Gudang;

public class AdminMenu {
    private LoginSystem loginSystem;
    private Gudang gudang;
    private Scanner input;

    public AdminMenu(LoginSystem loginSystem) {
        this.loginSystem = loginSystem;
        this.gudang = new Gudang();
        this.input = new Scanner(System.in);
    }

    public void showAdminMenu() {
        User admin = loginSystem.getUserSekarang();
        
        int pilih;
        do {
            System.out.println("\n╔════════════════════════════════════════════════╗");
            System.out.println("║        MENU ADMIN - " + admin.getUsername());
            System.out.println("╚════════════════════════════════════════════════╝");
            System.out.println("1. Menu Gudang (Inventory)");
            System.out.println("2. Kelola Admin (CRUD)");
            System.out.println("3. Kelola Kasir (CRUD)");
            System.out.println("4. Kelola Member (CRUD)");
            System.out.println("5. Laporan Penjualan");
            System.out.println("6. Logout");
            System.out.print("Pilih Menu: ");
            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1:
                    menuGudang();
                    break;
                case 2:
                    kelolaAdmin();
                    break;
                case 3:
                    kelolaKasir();
                    break;
                case 4:
                    kelolaMember();
                    break;
                case 5:
                    lihatLaporan();
                    break;
                case 6:
                    System.out.println("Logout berhasil!");
                    loginSystem.logout();
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilih != 6);
    }

    private void menuGudang() {
        int pilih;
        do {
            System.out.println("\n╔════════════════════════════════════════════════╗");
            System.out.println("║        MENU GUDANG (INVENTORY)");
            System.out.println("╚════════════════════════════════════════════════╝");
            System.out.println("1. Tampilkan semua barang");
            System.out.println("2. Cari barang");
            System.out.println("3. Tambah barang");
            System.out.println("4. Edit barang");
            System.out.println("5. Hapus barang");
            System.out.println("6. Update stok");
            System.out.println("7. Kembali");
            System.out.print("Pilih Menu: ");
            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1:
                    gudang.tampilkanSemuaBarang();
                    break;
                case 2:
                    System.out.print("Cari (ID/Nama): ");
                    String keyword = input.nextLine();
                    gudang.cariBarang(keyword);
                    break;
                case 3:
                    System.out.print("ID Barang: ");
                    String id = input.nextLine();
                    System.out.print("Nama barang: ");
                    String nama = input.nextLine();
                    System.out.print("Harga: ");
                    double harga = input.nextDouble();
                    System.out.print("Stok: ");
                    int stok = input.nextInt();
                    input.nextLine();
                    gudang.tambahBarang(id, nama, harga, stok);
                    break;
                case 4:
                    System.out.print("ID Barang yang akan diedit: ");
                    String idEdit = input.nextLine();
                    System.out.print("Nama baru: ");
                    String namaBaru = input.nextLine();
                    System.out.print("Harga baru: ");
                    double hargaBaru = input.nextDouble();
                    input.nextLine();
                    gudang.editBarang(idEdit, namaBaru, hargaBaru);
                    break;
                case 5:
                    System.out.print("ID Barang yang akan dihapus: ");
                    String idHapus = input.nextLine();
                    gudang.hapusBarang(idHapus);
                    break;
                case 6:
                    System.out.print("ID Barang: ");
                    String idUpdate = input.nextLine();
                    System.out.print("Stok baru: ");
                    int stokBaru = input.nextInt();
                    input.nextLine();
                    gudang.updateStok(idUpdate, stokBaru);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilih != 7);
    }

    // ===== CRUD ADMIN =====
    private void kelolaAdmin() {
        int pilih;
        do {
            System.out.println("\n╔════════════════════════════════════════════════╗");
            System.out.println("║        KELOLA ADMIN");
            System.out.println("╚════════════════════════════════════════════════╝");
            System.out.println("1. Lihat semua admin");
            System.out.println("2. Tambah admin baru");
            System.out.println("3. Edit admin");
            System.out.println("4. Hapus admin");
            System.out.println("5. Kembali");
            System.out.print("Pilih Menu: ");
            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1:
                    lihatSemuaAdmin();
                    break;
                case 2:
                    tambahAdminBaru();
                    break;
                case 3:
                    editAdmin();
                    break;
                case 4:
                    hapusAdmin();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilih != 5);
    }

    private void lihatSemuaAdmin() {
        ArrayList<User> admins = loginSystem.getSemuaAdmin();
        if (admins.isEmpty()) {
            System.out.println("Tidak ada admin.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        DAFTAR ADMIN");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.printf("%-15s %-15s %-15s\n", "Username", "No. Telepon", "Status");
        System.out.println("─".repeat(50));

        for (User admin : admins) {
            String status = admin.isDefault() ? "Default" : "Custom";
            System.out.printf("%-15s %-15s %-15s\n", admin.getUsername(), admin.getNoTelepon(), status);
        }
    }

    private void tambahAdminBaru() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        TAMBAH ADMIN BARU");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.print("Username admin baru: ");
        String username = input.nextLine();
        System.out.print("Nomor telepon: ");
        String noTelepon = input.nextLine();

        if (loginSystem.tambahAdmin(username, noTelepon)) {
            System.out.println("✅ Admin baru berhasil ditambahkan!");
        } else {
            System.out.println("❌ Gagal menambah admin (username mungkin sudah ada)");
        }
    }

    private void editAdmin() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        EDIT ADMIN");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.print("Username admin yang akan diedit: ");
        String usernameOld = input.nextLine();

        User admin = loginSystem.cariAdminByUsername(usernameOld);
        if (admin == null) {
            System.out.println("❌ Admin tidak ditemukan!");
            return;
        }

        System.out.println("\nData saat ini: " + admin.getUsername() + " / " + admin.getNoTelepon());
        System.out.print("Username baru (Enter jika tidak diubah): ");
        String usernameNew = input.nextLine();
        if (usernameNew.isEmpty()) usernameNew = usernameOld;

        System.out.print("No. Telepon baru (Enter jika tidak diubah): ");
        String noTelepon = input.nextLine();
        if (noTelepon.isEmpty()) noTelepon = admin.getNoTelepon();

        if (loginSystem.editAdmin(usernameOld, usernameNew, noTelepon)) {
            System.out.println("✅ Admin berhasil diedit!");
        } else {
            System.out.println("❌ Gagal mengedit admin!");
        }
    }

    private void hapusAdmin() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        HAPUS ADMIN");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.print("Username admin yang akan dihapus: ");
        String username = input.nextLine();

        User admin = loginSystem.cariAdminByUsername(username);
        if (admin == null) {
            System.out.println("❌ Admin tidak ditemukan!");
            return;
        }

        if (admin.isDefault()) {
            System.out.println("❌ Tidak bisa menghapus admin default!");
            return;
        }

        System.out.print("Yakin ingin menghapus admin '" + username + "'? (y/n): ");
        String jawab = input.nextLine();

        if (jawab.equalsIgnoreCase("y")) {
            if (loginSystem.hapusAdmin(username)) {
                System.out.println("✅ Admin berhasil dihapus!");
            } else {
                System.out.println("❌ Gagal menghapus admin!");
            }
        } else {
            System.out.println("Pembatalan hapus admin.");
        }
    }

    // ===== CRUD KASIR =====
    private void kelolaKasir() {
        int pilih;
        do {
            System.out.println("\n╔════════════════════════════════════════════════╗");
            System.out.println("║        KELOLA KASIR");
            System.out.println("╚════════════════════════════════════════════════╝");
            System.out.println("1. Lihat semua kasir");
            System.out.println("2. Tambah kasir baru");
            System.out.println("3. Edit kasir");
            System.out.println("4. Hapus kasir");
            System.out.println("5. Kembali");
            System.out.print("Pilih Menu: ");
            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1:
                    lihatSemuaKasir();
                    break;
                case 2:
                    tambahKasirBaru();
                    break;
                case 3:
                    editKasir();
                    break;
                case 4:
                    hapusKasir();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilih != 5);
    }

    private void lihatSemuaKasir() {
        ArrayList<User> kasirs = loginSystem.getSemuaKasir();
        if (kasirs.isEmpty()) {
            System.out.println("Tidak ada kasir.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        DAFTAR KASIR");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.printf("%-15s %-15s\n", "Username", "No. Telepon");
        System.out.println("─".repeat(30));

        for (User kasir : kasirs) {
            System.out.printf("%-15s %-15s\n", kasir.getUsername(), kasir.getNoTelepon());
        }
    }

    private void tambahKasirBaru() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        TAMBAH KASIR BARU");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.print("Username kasir baru: ");
        String username = input.nextLine();
        System.out.print("Nomor telepon: ");
        String noTelepon = input.nextLine();

        if (loginSystem.tambahKasir(username, noTelepon)) {
            System.out.println("✅ Kasir baru berhasil ditambahkan!");
        } else {
            System.out.println("❌ Gagal menambah kasir (username mungkin sudah ada)");
        }
    }

    private void editKasir() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        EDIT KASIR");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.print("Username kasir yang akan diedit: ");
        String usernameOld = input.nextLine();

        User kasir = loginSystem.cariKasirByUsername(usernameOld);
        if (kasir == null) {
            System.out.println("❌ Kasir tidak ditemukan!");
            return;
        }

        System.out.println("\nData saat ini: " + kasir.getUsername() + " / " + kasir.getNoTelepon());
        System.out.print("Username baru (Enter jika tidak diubah): ");
        String usernameNew = input.nextLine();
        if (usernameNew.isEmpty()) usernameNew = usernameOld;

        System.out.print("No. Telepon baru (Enter jika tidak diubah): ");
        String noTelepon = input.nextLine();
        if (noTelepon.isEmpty()) noTelepon = kasir.getNoTelepon();

        if (loginSystem.editKasir(usernameOld, usernameNew, noTelepon)) {
            System.out.println("✅ Kasir berhasil diedit!");
        } else {
            System.out.println("❌ Gagal mengedit kasir!");
        }
    }

    private void hapusKasir() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        HAPUS KASIR");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.print("Username kasir yang akan dihapus: ");
        String username = input.nextLine();

        User kasir = loginSystem.cariKasirByUsername(username);
        if (kasir == null) {
            System.out.println("❌ Kasir tidak ditemukan!");
            return;
        }

        System.out.print("Yakin ingin menghapus kasir '" + username + "'? (y/n): ");
        String jawab = input.nextLine();

        if (jawab.equalsIgnoreCase("y")) {
            if (loginSystem.hapusKasir(username)) {
                System.out.println("✅ Kasir berhasil dihapus!");
            } else {
                System.out.println("❌ Gagal menghapus kasir!");
            }
        } else {
            System.out.println("Pembatalan hapus kasir.");
        }
    }

    // ===== CRUD MEMBER =====
    private void kelolaMember() {
        int pilih;
        do {
            System.out.println("\n╔════════════════════════════════════════════════╗");
            System.out.println("║        KELOLA MEMBER");
            System.out.println("╚════════════════════════════════════════════════╝");
            System.out.println("1. Lihat semua member");
            System.out.println("2. Tambah member baru");
            System.out.println("3. Edit member");
            System.out.println("4. Hapus member");
            System.out.println("5. Kembali");
            System.out.print("Pilih Menu: ");
            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1:
                    lihatSemuaMember();
                    break;
                case 2:
                    tambahMemberBaru();
                    break;
                case 3:
                    editMember();
                    break;
                case 4:
                    hapusMember();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilih != 5);
    }

    private void lihatSemuaMember() {
        ArrayList<User> members = loginSystem.getSemuaMember();
        if (members.isEmpty()) {
            System.out.println("Tidak ada member.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        DAFTAR MEMBER");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.printf("%-15s %-15s\n", "Username", "No. Telepon");
        System.out.println("─".repeat(30));

        for (User member : members) {
            System.out.printf("%-15s %-15s\n", member.getUsername(), member.getNoTelepon());
        }
    }

    private void tambahMemberBaru() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        TAMBAH MEMBER BARU");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.print("Username member baru: ");
        String username = input.nextLine();
        System.out.print("Nomor telepon: ");
        String noTelepon = input.nextLine();

        if (loginSystem.register(username, noTelepon)) {
            System.out.println("✅ Member baru berhasil ditambahkan!");
        } else {
            System.out.println("❌ Gagal menambah member (username mungkin sudah ada)");
        }
    }

    private void editMember() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        EDIT MEMBER");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.print("Username member yang akan diedit: ");
        String usernameOld = input.nextLine();

        User member = loginSystem.cariMemberByUsername(usernameOld);
        if (member == null) {
            System.out.println("❌ Member tidak ditemukan!");
            return;
        }

        System.out.println("\nData saat ini: " + member.getUsername() + " / " + member.getNoTelepon());
        System.out.print("Username baru (Enter jika tidak diubah): ");
        String usernameNew = input.nextLine();
        if (usernameNew.isEmpty()) usernameNew = usernameOld;

        System.out.print("No. Telepon baru (Enter jika tidak diubah): ");
        String noTelepon = input.nextLine();
        if (noTelepon.isEmpty()) noTelepon = member.getNoTelepon();

        if (loginSystem.editMember(usernameOld, usernameNew, noTelepon)) {
            System.out.println("✅ Member berhasil diedit!");
        } else {
            System.out.println("❌ Gagal mengedit member!");
        }
    }

    private void hapusMember() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        HAPUS MEMBER");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.print("Username member yang akan dihapus: ");
        String username = input.nextLine();

        User member = loginSystem.cariMemberByUsername(username);
        if (member == null) {
            System.out.println("❌ Member tidak ditemukan!");
            return;
        }

        System.out.print("Yakin ingin menghapus member '" + username + "'? (y/n): ");
        String jawab = input.nextLine();

        if (jawab.equalsIgnoreCase("y")) {
            if (loginSystem.hapusMember(username)) {
                System.out.println("✅ Member berhasil dihapus!");
            } else {
                System.out.println("❌ Gagal menghapus member!");
            }
        } else {
            System.out.println("Pembatalan hapus member.");
        }
    }

    private void lihatLaporan() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        LAPORAN PENJUALAN");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println("📊 Fitur laporan masih dalam pengembangan.");
        System.out.println("   Nantinya akan menampilkan:");
        System.out.println("   - Total penjualan");
        System.out.println("   - Barang terlaris");
        System.out.println("   - Laporan per periode");
    }
}

