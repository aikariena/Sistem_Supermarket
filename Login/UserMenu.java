package Login;

import java.util.ArrayList;
import java.util.Scanner;

import Gudang.Barang;
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
            
            try {
                pilih = input.nextInt();
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Input harus angka!");
                input.nextLine();
                pilih = 0;
            }

            switch (pilih) {
                case 1: browseGudang(); break;
                case 2: lihatKeranjangSaya(); break;
                case 3: buatKodeKeranjang(); break;
                case 4: manajemenSaldo(); break;
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
            System.out.println("3. Tambah ke keranjang (Otomatis)");
            System.out.println("4. Kembali");
            System.out.print("Pilih: ");
            try {
                pilih = input.nextInt();
                input.nextLine();
            } catch (Exception e) {
                input.nextLine();
                pilih = 0;
            }

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
                    tambahKeKeranjangOtomatis();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilih != 4);
    }

    private void tambahKeKeranjangOtomatis() {
        System.out.print("Masukkan ID Barang: ");
        String idTarget = input.nextLine();

        Barang barangDitemukan = gudang.getBarangById(idTarget);

        if (barangDitemukan == null) {
            System.out.println("Barang dengan ID '" + idTarget + "' tidak ditemukan!");
            return;
        }

        System.out.println("Barang Ditemukan: " + barangDitemukan.getNama());
        System.out.printf("   Harga: Rp%.0f\n", barangDitemukan.getHarga());
        System.out.println("   Stok Tersedia: " + barangDitemukan.getStok());

        System.out.print("Masukkan Jumlah Beli: ");
        int jumlahBeli = 0;
        try {
            jumlahBeli = input.nextInt();
            input.nextLine();
        } catch (Exception e) {
            System.out.println("Input jumlah salah!");
            input.nextLine();
            return;
        }

        if (jumlahBeli <= 0) {
            System.out.println("Jumlah harus lebih dari 0.");
            return;
        }

        if (jumlahBeli > barangDitemukan.getStok()) {
            System.out.println("Stok tidak mencukupi!");
            return;
        }

        boolean isExist = false;
        for (CartItem item : cart) {
            if (item.getIdBarang().equalsIgnoreCase(barangDitemukan.getIdBarang())) {
                item.setJumlah(item.getJumlah() + jumlahBeli);
                isExist = true;
                System.out.println("Jumlah barang di keranjang diperbarui.");
                break;
            }
        }

        if (!isExist) {
            cart.add(new CartItem(
                barangDitemukan.getIdBarang(), 
                barangDitemukan.getNama(), 
                (int)barangDitemukan.getHarga(), 
                jumlahBeli
            ));
            System.out.println("Berhasil masuk keranjang!");
        }
    }

    private void lihatKeranjangSaya() {
        if (cart.isEmpty()) {
            System.out.println("\nKeranjang Anda kosong!");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        KERANJANG BELANJA SAYA");
        System.out.println("╚════════════════════════════════════════════════╝");

        double total = 0;
        // Format Header Tabel
        System.out.printf("%-4s %-10s %-30s %-15s %-8s %-15s\n",
            "No", "ID", "Nama", "Harga", "Qty", "Subtotal");
        System.out.println("─".repeat(85));

        int no = 1;
        for (CartItem item : cart) {
            // Format Baris Tabel: %.0f untuk menghilangkan desimal
            System.out.printf("%-4d %-10s %-30s Rp%-13.0f %-8d Rp%-13.0f\n",
                no++, 
                item.getIdBarang(), 
                item.getNama(),
                (double)item.getHarga(), // Casting ke double biar aman di %.0f
                item.getJumlah(), 
                item.getSubtotal());
            total += item.getSubtotal();
        }

        System.out.println("─".repeat(85));
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
                    System.out.println("Item dihapus!");
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
                    System.out.println("Jumlah item diubah!");
                }
                break;
        }
    }

    private void buatKodeKeranjang() {
        if (cart.isEmpty()) {
            System.out.println("\nKeranjang Anda kosong! Tambah barang terlebih dahulu.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        BUAT KODE KERANJANG");
        System.out.println("╚════════════════════════════════════════════════╝");

        double total = 0;
        // Format Header Tabel yang Rapi
        System.out.printf("%-4s %-10s %-30s %-15s %-8s %-15s\n",
            "No", "ID", "Nama", "Harga", "Qty", "Subtotal");
        System.out.println("─".repeat(85));

        int no = 1;
        for (CartItem item : cart) {
            // PERBAIKAN UTAMA DI SINI: Menggunakan %.0f
            System.out.printf("%-4d %-10s %-30s Rp%-13.0f %-8d Rp%-13.0f\n",
                no++, 
                item.getIdBarang(), 
                item.getNama(),
                (double)item.getHarga(), 
                item.getJumlah(), 
                item.getSubtotal());
            total += item.getSubtotal();
        }

        System.out.println("─".repeat(85));
        System.out.printf("TOTAL: Rp%.0f\n", total);

        System.out.print("\nBuat kode keranjang? (y/n): ");
        String jawab = input.nextLine();

        if (jawab.equalsIgnoreCase("y")) {
            String kode = cartCode.buatKodeKeranjang(new ArrayList<>(cart));
            System.out.println("\nKode keranjang berhasil dibuat!");
            System.out.println("Berikan kode ini kepada kasir untuk diproses.");
            System.out.println("Keranjang Anda akan dikosongkan.");

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
            System.out.printf("Saldo Anda: Rp%.0f\n\n", saldoSekarang);
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
