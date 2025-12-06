import java.util.Scanner;

public class AdminMenu {
    private LoginSystem loginSystem;
    private Gudang gudang;
    private CartMenu cartMenu;
    private Scanner input;

    public AdminMenu(LoginSystem loginSystem) {
        this.loginSystem = loginSystem;
        this.gudang = new Gudang();
        this.cartMenu = new CartMenu();
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
            System.out.println("2. Menu Keranjang");
            System.out.println("3. Laporan Penjualan");
            System.out.println("4. Kelola Admin (Tambah Admin)");
            System.out.println("5. Kelola Kasir (Tambah Kasir)");
            System.out.println("6. Logout");
            System.out.print("Pilih Menu: ");
            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1:
                    menuGudang();
                    break;
                case 2:
                    cartMenu.ShowMenu();
                    break;
                case 3:
                    lihatLaporan();
                    break;
                case 4:
                    tambahAdminBaru();
                    break;
                case 5:
                    tambahKasirBaru();
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
            System.out.println("   Username: " + username);
            System.out.println("   No. Telepon: " + noTelepon);
        } else {
            System.out.println("❌ Gagal menambah admin (username mungkin sudah ada)");
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
            System.out.println("   Username: " + username);
            System.out.println("   No. Telepon: " + noTelepon);
        } else {
            System.out.println("❌ Gagal menambah kasir (username mungkin sudah ada)");
        }
    }
}
