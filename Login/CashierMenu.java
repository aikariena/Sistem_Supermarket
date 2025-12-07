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
    private CartCode cartCode; // Ini objek penghubung ke file temanmu
    private Scanner input;

    public CashierMenu(LoginSystem loginSystem) {
        this.loginSystem = loginSystem;
        this.gudang = new Gudang();
        this.cartCode = new CartCode(); // Load data dari file temanmu
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
                    // Panggil lewat variabel cartCode (bukan Class CartCode)
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

        // Panggil lewat variabel cartCode
        ArrayList<CartItem> cart = cartCode.getKeranjangByKode(kodeKeranjang);
        if (cart == null) {
            System.out.println("Kode keranjang tidak ditemukan!");
            return;
        }

        // Tampilkan detail
        cartCode.tampilkanDetailKeranjang(kodeKeranjang);

        System.out.print("\nProses pembayaran? (y/n): ");
        String jawab = input.nextLine();

        if (jawab.equalsIgnoreCase("y")) {
            System.out.print("Username member: ");
            String usernameMember = input.nextLine();

            User kasir = loginSystem.getUserSekarang();
            // Masuk ke menu pembayaran Aika
            PaymentMenu paymentMenu = new PaymentMenu(cart, gudang, usernameMember, kasir.getUsername());
            paymentMenu.showPaymentMenu();

            // Hapus keranjang setelah lunas
            cartCode.hapusKeranjang(kodeKeranjang);
            System.out.println("Kode keranjang telah dihapus dari sistem.");
        } else {
            System.out.println("Pembayaran dibatalkan.");
        }
    }
}