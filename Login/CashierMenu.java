import java.util.ArrayList;
import java.util.Scanner;

public class CashierMenu {
    private LoginSystem loginSystem;
    private Gudang gudang;
    private ArrayList<CartItem> cart;
    private Scanner input;

    public CashierMenu(LoginSystem loginSystem) {
        this.loginSystem = loginSystem;
        this.gudang = new Gudang();
        this.cart = new ArrayList<>();
        this.input = new Scanner(System.in);
    }

    public void showCashierMenu() {
        User kasir = loginSystem.getUserSekarang();

        int pilih;
        do {
            System.out.println("\n╔════════════════════════════════════════════════╗");
            System.out.println("║        MENU KASIR - " + kasir.getUsername());
            System.out.println("╚════════════════════════════════════════════════╝");
            System.out.println("1. Menu Pembayaran");
            System.out.println("2. Logout");
            System.out.print("Pilih Menu: ");
            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1:
                    menuPembayaran();
                    break;
                case 2:
                    System.out.println("Logout berhasil!");
                    loginSystem.logout();
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilih != 2);
    }

    private void menuPembayaran() {
        int pilih;
        do {
            System.out.println("\n╔════════════════════════════════════════════════╗");
            System.out.println("║        MENU PEMBAYARAN");
            System.out.println("╚════════════════════════════════════════════════╝");
            System.out.println("1. Input Barang ke Keranjang");
            System.out.println("2. Lihat Keranjang");
            System.out.println("3. Hapus Barang dari Keranjang");
            System.out.println("4. Proses Checkout");
            System.out.println("5. Kembali");
            System.out.print("Pilih Menu: ");
            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1:
                    inputBarangKeKeranjang();
                    break;
                case 2:
                    lihatKeranjang();
                    break;
                case 3:
                    hapusBarangKeranjang();
                    break;
                case 4:
                    if (!cart.isEmpty()) {
                        PaymentMenu paymentMenu = new PaymentMenu(cart, gudang, loginSystem.getUserSekarang().getUsername());
                        paymentMenu.showPaymentMenu();
                    } else {
                        System.out.println("⚠️  Keranjang kosong!");
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilih != 5);
    }

    private void inputBarangKeKeranjang() {
        System.out.println("\n📦 Cari barang yang ingin ditambahkan:");
        System.out.print("Cari (ID/Nama): ");
        String keyword = input.nextLine();
        
        gudang.cariBarang(keyword);
        
        System.out.print("\nTambahkan ke keranjang? (y/n): ");
        String jawab = input.nextLine();
        
        if (jawab.equalsIgnoreCase("y")) {
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
        }
    }

    private void lihatKeranjang() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        ISI KERANJANG");
        System.out.println("╚════════════════════════════════════════════════╝");
        
        if (cart.isEmpty()) {
            System.out.println("Keranjang kosong.");
            return;
        }

        for (int i = 0; i < cart.size(); i++) {
            CartItem item = cart.get(i);
            System.out.println((i + 1) + ". " + item.getNama() +
                    " | Harga: Rp " + item.getHarga() +
                    " | Qty: " + item.getJumlah() +
                    " | ID: " + item.getIdBarang());
        }

        double total = 0;
        for (CartItem item : cart) {
            total += item.getHarga() * item.getJumlah();
        }
        System.out.println("────────────────────────────────────");
        System.out.printf("Subtotal : Rp %.0f\n", total);
    }

    private void hapusBarangKeranjang() {
        if (cart.isEmpty()) {
            System.out.println("Keranjang kosong.");
            return;
        }

        lihatKeranjang();
        System.out.print("\nPilih nomor barang untuk dihapus: ");
        int idx = input.nextInt() - 1;
        input.nextLine();

        if (idx >= 0 && idx < cart.size()) {
            cart.remove(idx);
            System.out.println("✅ Barang dihapus dari keranjang!");
        } else {
            System.out.println("Nomor tidak valid!");
        }
    }
}
