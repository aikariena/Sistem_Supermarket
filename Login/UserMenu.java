package Login;

import java.util.ArrayList;
import java.util.Scanner;
import Gudang.Gudang;
import Keranjang.CartItem;
import Pembayaran.PaymentMenu;

public class UserMenu {
    private LoginSystem loginSystem;
    private Gudang gudang;
    private ArrayList<CartItem> cart;
    private Scanner input;

    public UserMenu(LoginSystem loginSystem) {
        this.loginSystem = loginSystem;
        this.gudang = new Gudang();
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
            System.out.println("3. Bayar (Checkout)");
            System.out.println("4. Logout");
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
                    if (!cart.isEmpty()) {
                        PaymentMenu paymentMenu = new PaymentMenu(cart, gudang, user.getUsername());
                        paymentMenu.showPaymentMenu();
                    } else {
                        System.out.println("⚠️  Keranjang Anda kosong!");
                    }
                    break;
                case 4:
                    System.out.println("Logout berhasil!");
                    loginSystem.logout();
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilih != 4);
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
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        KERANJANG BELANJA SAYA");
        System.out.println("╚════════════════════════════════════════════════╝");

        if (cart.isEmpty()) {
            System.out.println("Keranjang kosong.");
            return;
        }

        System.out.println("\nRincian Keranjang:");
        int no = 1;
        double total = 0;
        for (CartItem item : cart) {
            double subtotal = item.getHarga() * item.getJumlah();
            total += subtotal;
            System.out.println(no + ". " + item.getNama() +
                    " | Harga: Rp " + item.getHarga() +
                    " | Qty: " + item.getJumlah() +
                    " | Subtotal: Rp " + String.format("%.0f", subtotal));
            no++;
        }

        System.out.println("────────────────────────────────────");
        System.out.printf("TOTAL : Rp %.0f\n", total);

        System.out.println("\nOpsi:");
        System.out.println("1. Update jumlah");
        System.out.println("2. Hapus barang");
        System.out.println("3. Kembali");
        System.out.print("Pilih: ");
        int pilih = input.nextInt();
        input.nextLine();

        if (pilih == 1) {
            System.out.print("Pilih nomor barang: ");
            int idx = input.nextInt() - 1;
            input.nextLine();
            if (idx >= 0 && idx < cart.size()) {
                System.out.print("Jumlah baru: ");
                int jumlahBaru = input.nextInt();
                input.nextLine();
                cart.get(idx).setJumlah(jumlahBaru);
                System.out.println("✅ Jumlah diperbarui!");
            }
        } else if (pilih == 2) {
            System.out.print("Pilih nomor barang: ");
            int idx = input.nextInt() - 1;
            input.nextLine();
            if (idx >= 0 && idx < cart.size()) {
                cart.remove(idx);
                System.out.println("✅ Barang dihapus!");
            }
        }
    }
}
