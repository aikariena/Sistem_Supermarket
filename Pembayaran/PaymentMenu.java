import java.util.ArrayList;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentMenu {
    private ArrayList<CartItem> cart;
    private Gudang gudang;
    private String username;
    private Scanner input;
    private double PAJAK = 10.0; // 10% pajak

    public PaymentMenu(ArrayList<CartItem> cartItems, Gudang gudang, String username) {
        this.cart = cartItems;
        this.gudang = gudang;
        this.username = username;
        this.input = new Scanner(System.in);
    }

    public void showPaymentMenu() {
        int pilih;
        
        do {
            System.out.println("\n╔════════════════════════════════════════════════╗");
            System.out.println("║      MENU PEMBAYARAN - " + username);
            System.out.println("╚════════════════════════════════════════════════╝");
            System.out.println("1. Lihat Keranjang");
            System.out.println("2. Lanjut ke Pembayaran");
            System.out.println("3. Kembali");
            System.out.print("Pilih Menu: ");
            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1:
                    lihatKeranjang();
                    break;
                case 2:
                    if (!cart.isEmpty()) {
                        prosesCheckout();
                    } else {
                        System.out.println("⚠️  Keranjang Anda kosong!");
                    }
                    break;
                case 3:
                    System.out.println("Kembali ke menu utama...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        } while (pilih != 3);
    }

    private void lihatKeranjang() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║          ISI KERANJANG BELANJA ANDA");
        System.out.println("╚════════════════════════════════════════════════╝");
        
        if (cart.isEmpty()) {
            System.out.println("Keranjang kosong.");
            return;
        }

        System.out.println("┌────┬─────────────────────────┬───────┬────────┐");
        System.out.println("│No. │ Nama Barang             │ Qty   │ Harga  │");
        System.out.println("├────┼─────────────────────────┼───────┼────────┤");
        
        for (int i = 0; i < cart.size(); i++) {
            CartItem item = cart.get(i);
            System.out.printf("│ %-2d │ %-23s │ %-5d │ %6.0f │\n", 
                i + 1, item.getNama(), item.getJumlah(), (double)item.getHarga());
        }
        
        System.out.println("└────┴─────────────────────────┴───────┴────────┘");
        
        double subtotal = hitungSubtotal();
        double pajakRp = subtotal * PAJAK / 100;
        double total = subtotal + pajakRp;
        
        System.out.printf("\nSubtotal          : Rp %.0f\n", subtotal);
        System.out.printf("Pajak (%.1f%%)       : Rp %.0f\n", PAJAK, pajakRp);
        System.out.printf("─────────────────────────────\n");
        System.out.printf("TOTAL            : Rp %.0f\n", total);
    }

    private void prosesCheckout() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║            PROSES PEMBAYARAN");
        System.out.println("╚════════════════════════════════════════════════╝");

        // Tampilkan ringkasan
        double subtotal = hitungSubtotal();
        double pajakRp = subtotal * PAJAK / 100;
        double total = subtotal + pajakRp;

        System.out.printf("\nSubtotal          : Rp %.0f\n", subtotal);
        System.out.printf("Pajak (%.1f%%)       : Rp %.0f\n", PAJAK, pajakRp);
        System.out.printf("─────────────────────────────\n");
        System.out.printf("TOTAL            : Rp %.0f\n\n", total);

        // Pilih metode pembayaran
        System.out.println("Pilih Metode Pembayaran:");
        System.out.println("1. Tunai");
        System.out.println("2. Non-Tunai (Kartu/Transfer)");
        System.out.print("Pilih: ");
        int metode = input.nextInt();
        input.nextLine();

        String tipePembayaran = (metode == 1) ? "tunai" : "non-tunai";
        double jumlahBayar = total;

        if (metode == 1) {
            System.out.print("\nJumlah uang yang diterima (Rp): ");
            jumlahBayar = input.nextDouble();
            input.nextLine();

            if (jumlahBayar < total) {
                System.out.println("❌ Jumlah pembayaran kurang!");
                return;
            }
        } else if (metode == 2) {
            System.out.println("Memproses pembayaran non-tunai...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Pilihan tidak valid!");
            return;
        }

        // Konfirmasi pembayaran
        System.out.print("\nKonfirmasi pembayaran? (y/n): ");
        String konfirmasi = input.nextLine().toLowerCase();

        if (!konfirmasi.equals("y")) {
            System.out.println("❌ Pembayaran dibatalkan!");
            return;
        }

        // Buat transaksi
        String transactionId = generateTransactionId();
        ArrayList<TransactionItem> transactionItems = new ArrayList<>();
        
        for (CartItem item : cart) {
            transactionItems.add(new TransactionItem(
                item.getIdBarang(),
                item.getNama(),
                item.getHarga(),
                item.getJumlah()
            ));
        }

        Transaction transaction = new Transaction(transactionId, username, transactionItems, PAJAK);
        transaction.setStatusPembayaran("completed");
        transaction.setTipePembayaran(tipePembayaran);

        // Cetak struk
        Receipt receipt = new Receipt(transaction, jumlahBayar);
        receipt.cetakStruk();
        receipt.simpanKeFile();

        // Update stok di gudang
        updateStokGudang();

        System.out.println("✅ Transaksi berhasil diproses!");
        System.out.println("Keranjang dikosongkan...");
        cart.clear();
    }

    private double hitungSubtotal() {
        double total = 0;
        for (CartItem item : cart) {
            total += item.getHarga() * item.getJumlah();
        }
        return total;
    }

    private void updateStokGudang() {
        System.out.println("\n📦 Mengupdate stok barang di gudang...");
        for (CartItem item : cart) {
            // Ambil barang dari gudang
            ArrayList<Barang> semuaBarang = gudang.getSemuaBarang();
            for (Barang barang : semuaBarang) {
                if (barang.getIdBarang().equalsIgnoreCase(item.getIdBarang())) {
                    int stokBaru = barang.getStok() - item.getJumlah();
                    if (stokBaru < 0) {
                        System.out.println("⚠️  Stok " + barang.getNama() + " tidak cukup untuk diupdate!");
                    } else {
                        gudang.updateStok(item.getIdBarang(), stokBaru);
                        System.out.println("✓ Stok " + barang.getNama() + " diupdate (sisa: " + stokBaru + ")");
                    }
                    break;
                }
            }
        }
    }

    private String generateTransactionId() {
        long timestamp = System.currentTimeMillis();
        return "TRX" + timestamp + (int)(Math.random() * 1000);
    }
}
