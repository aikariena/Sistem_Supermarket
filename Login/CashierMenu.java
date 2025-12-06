package Login;

import java.util.ArrayList;
import java.util.Scanner;
import Gudang.Gudang;
import Keranjang.CartItem;
import Keranjang.CartCode;
import Pembayaran.PaymentMenu;

public class CashierMenu {
    private LoginSystem loginSystem;
    private Gudang gudang;
    private CartCode cartCode;
    private Scanner input;

    public CashierMenu(LoginSystem loginSystem) {
        this.loginSystem = loginSystem;
        this.gudang = new Gudang();
        this.cartCode = new CartCode();
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
            System.out.println("║        MENU PEMBAYARAN (KASIR)");
            System.out.println("╚════════════════════════════════════════════════╝");
            System.out.println("1. Input kode keranjang member");
            System.out.println("2. Lihat semua kode keranjang");
            System.out.println("3. Kembali");
            System.out.print("Pilih Menu: ");
            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1:
                    inputKodeKeranjang();
                    break;
                case 2:
                    cartCode.tampilkanSemuaKodeKeranjang();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilih != 3);
    }

    private void inputKodeKeranjang() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        INPUT KODE KERANJANG");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.print("Masukkan kode keranjang member: ");
        String kodeKeranjang = input.nextLine();

        ArrayList<CartItem> cart = cartCode.getKeranjangByKode(kodeKeranjang);
        if (cart == null) {
            System.out.println("❌ Kode keranjang tidak ditemukan!");
            return;
        }

        // Tampilkan detail keranjang
        cartCode.tampilkanDetailKeranjang(kodeKeranjang);

        // Proses pembayaran
        System.out.print("\nProses pembayaran? (y/n): ");
        String jawab = input.nextLine();

        if (jawab.equalsIgnoreCase("y")) {
            // Ambil username member dari input (untuk struk)
            System.out.print("Username member: ");
            String usernameMember = input.nextLine();

            PaymentMenu paymentMenu = new PaymentMenu(cart, gudang, usernameMember);
            paymentMenu.showPaymentMenu();

            // Hapus keranjang setelah pembayaran selesai
            cartCode.hapusKeranjang(kodeKeranjang);
            System.out.println("✅ Kode keranjang telah dihapus dari sistem.");
        } else {
            System.out.println("Pembayaran dibatalkan.");
        }
    }
}
