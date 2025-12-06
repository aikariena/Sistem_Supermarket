package Login;

import java.util.ArrayList;
import java.util.Scanner;
import Gudang.Gudang;
import Keranjang.CartItem;
import Keranjang.CartCode;

public class UserMenu {
    private LoginSystem loginSystem;
    private Gudang gudang;
    private CartCode cartCode;
    private MemberBalance memberBalance;
    private ArrayList<CartItem> cart;
    private Scanner input;

    public UserMenu(LoginSystem loginSystem) {
        this.loginSystem = loginSystem;
        this.gudang = new Gudang();
        this.cartCode = new CartCode();
        this.memberBalance = new MemberBalance();
        this.cart = new ArrayList<>();
        this.input = new Scanner(System.in);
    }

    public void showUserMenu() {
        User user = loginSystem.getUserSekarang();

        int pilih;
        do {
            System.out.println("\n╔════════════════════════════════════════════════╗");
            System.out.println("║        MENU PENGGUNA - " + user.getUsername());
            System.out.println("╚════════════════════════════════════════════════╝");
            System.out.println("1. Gudang (Browse Produk)");
            System.out.println("2. Keranjang Belanja Saya");
            System.out.println("3. Buat Kode Keranjang (untuk Kasir)");
            System.out.println("4. Manajemen Saldo");
            System.out.println("5. Logout");
            System.out.print("Pilih Menu: ");
            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1:
                    browseGudang();
                    break;
                case 2:
                    lihatKeranjangSaya();
                    break;
                case 3:
                    buatKodeKeranjang();
                    break;
                case 4:
                    manajemenSaldo();
                    break;
                case 5:
                    System.out.println("Logout berhasil!");
                    loginSystem.logout();
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilih != 5);
    }

    private void browseGudang() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        BROWSE PRODUK (PUBLIC CATALOG)");
        System.out.println("╚════════════════════════════════════════════════╝");

        int pilih;
        do {
            System.out.println("\n1. Lihat semua produk");
            System.out.println("2. Cari produk");
            System.out.println("3. Tambah ke keranjang");
            System.out.println("4. Kembali");
            System.out.print("Pilih: ");
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
                    String idBarang = input.nextLine();
                    System.out.print("Nama barang: ");
                    String nama = input.nextLine();
                    System.out.print("Harga barang (Rp): ");
                    int harga = input.nextInt();
                    System.out.print("Jumlah: ");
                    int jumlah = input.nextInt();
                    input.nextLine();

                    cart.add(new CartItem(idBarang, nama, harga, jumlah));
                    System.out.println("✅ Barang '" + nama + "' ditambahkan ke keranjang!");
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilih != 4);
    }

    private void lihatKeranjangSaya() {
        if (cart.isEmpty()) {
            System.out.println("\n⚠️  Keranjang Anda kosong!");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        KERANJANG BELANJA SAYA");
        System.out.println("╚════════════════════════════════════════════════╝");

        double total = 0;
        System.out.printf("%-5s %-10s %-30s %-10s %-10s %-12s\n",
            "No", "ID", "Nama", "Harga", "Jumlah", "Subtotal");
        System.out.println("─".repeat(82));

        int no = 1;
        for (CartItem item : cart) {
            System.out.printf("%-5d %-10s %-30s Rp%-8d %-10d Rp%.0f\n",
                no++, item.getIdBarang(), item.getNama(),
                item.getHarga(), item.getJumlah(), item.getSubtotal());
            total += item.getSubtotal();
        }

        System.out.println("─".repeat(82));
        System.out.printf("TOTAL: Rp%.0f\n", total);

        System.out.println("\n1. Hapus item");
        System.out.println("2. Ubah jumlah item");
        System.out.println("3. Kembali");
        System.out.print("Pilih: ");
        int pilih = input.nextInt();
        input.nextLine();

        switch (pilih) {
            case 1:
                System.out.print("Nomor item yang dihapus: ");
                int noHapus = input.nextInt();
                input.nextLine();
                if (noHapus > 0 && noHapus <= cart.size()) {
                    cart.remove(noHapus - 1);
                    System.out.println("✅ Item dihapus!");
                }
                break;
            case 2:
                System.out.print("Nomor item: ");
                int noItem = input.nextInt();
                System.out.print("Jumlah baru: ");
                int jumlahBaru = input.nextInt();
                input.nextLine();
                if (noItem > 0 && noItem <= cart.size()) {
                    cart.get(noItem - 1).setJumlah(jumlahBaru);
                    System.out.println("✅ Jumlah item diubah!");
                }
                break;
        }
    }

    private void buatKodeKeranjang() {
        if (cart.isEmpty()) {
            System.out.println("\n⚠️  Keranjang Anda kosong! Tambah barang terlebih dahulu.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        BUAT KODE KERANJANG");
        System.out.println("╚════════════════════════════════════════════════╝");

        // Tampilkan isi keranjang
        double total = 0;
        System.out.printf("%-5s %-10s %-30s %-10s %-10s %-12s\n",
            "No", "ID", "Nama", "Harga", "Jumlah", "Subtotal");
        System.out.println("─".repeat(82));

        int no = 1;
        for (CartItem item : cart) {
            System.out.printf("%-5d %-10s %-30s Rp%-8d %-10d Rp%.0f\n",
                no++, item.getIdBarang(), item.getNama(),
                item.getHarga(), item.getJumlah(), item.getSubtotal());
            total += item.getSubtotal();
        }

        System.out.println("─".repeat(82));
        System.out.printf("TOTAL: Rp%.0f\n", total);

        System.out.print("\nBuat kode keranjang? (y/n): ");
        String jawab = input.nextLine();

        if (jawab.equalsIgnoreCase("y")) {
            String kode = cartCode.buatKodeKeranjang(new ArrayList<>(cart));
            System.out.println("\n✅ Kode keranjang berhasil dibuat!");
            System.out.println("📝 Kode Anda: " + kode);
            System.out.println("   Berikan kode ini kepada kasir untuk diproses.");
            System.out.println("   Keranjang Anda akan dikosongkan.");

            cart.clear();
        } else {
            System.out.println("Pembatalan pembuatan kode keranjang.");
        }
    }

    private void manajemenSaldo() {
        User user = loginSystem.getUserSekarang();
        double saldoSekarang = memberBalance.lihatSaldo(user.getUsername());

        int pilih;
        do {
            System.out.println("\n╔════════════════════════════════════════════════╗");
            System.out.println("║        MANAJEMEN SALDO");
            System.out.println("╚════════════════════════════════════════════════╝");
            System.out.printf("💰 Saldo Anda: Rp%.0f\n\n", saldoSekarang);
            System.out.println("1. Setor Saldo");
            System.out.println("2. Tarik Saldo");
            System.out.println("3. Kembali");
            System.out.print("Pilih Menu: ");
            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1:
                    setorSaldo(user.getUsername());
                    saldoSekarang = memberBalance.lihatSaldo(user.getUsername());
                    break;
                case 2:
                    tarikSaldo(user.getUsername());
                    saldoSekarang = memberBalance.lihatSaldo(user.getUsername());
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilih != 3);
    }

    private void setorSaldo(String username) {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        SETOR SALDO");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.print("Jumlah setor (Rp): ");
        double jumlah = input.nextDouble();
        input.nextLine();

        memberBalance.setorSaldo(username, jumlah);
    }

    private void tarikSaldo(String username) {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        TARIK SALDO");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.print("Jumlah tarik (Rp): ");
        double jumlah = input.nextDouble();
        input.nextLine();

        memberBalance.tarikSaldo(username, jumlah);
    }
}
