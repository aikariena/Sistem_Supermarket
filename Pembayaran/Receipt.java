import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Receipt {
    private Transaction transaction;
    private double jumlahBayar;

    public Receipt(Transaction transaction, double jumlahBayar) {
        this.transaction = transaction;
        this.jumlahBayar = jumlahBayar;
    }

    public void cetakStruk() {
        System.out.println("\n");
        System.out.println("═════════════════════════════════════════════════════════");
        System.out.println("              STRUK PEMBAYARAN SUPERMARKET");
        System.out.println("═════════════════════════════════════════════════════════");
        System.out.println("ID Transaksi: " + transaction.getTransactionId());
        System.out.println("Nama Pembeli: " + transaction.getUsername());
        System.out.println("Tanggal    : " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(transaction.getTanggalTransaksi())));
        System.out.println("─────────────────────────────────────────────────────────");
        System.out.println("RINCIAN BARANG:");
        System.out.println("─────────────────────────────────────────────────────────");

        int noBarang = 1;
        for (TransactionItem item : transaction.getItems()) {
            System.out.printf("%d. %-30s | Qty: %d\n", noBarang, item.getNama(), item.getJumlah());
            System.out.printf("   Harga: Rp %.0f x %d = Rp %.0f\n", 
                item.getHarga(), item.getJumlah(), item.getSubtotal());
            noBarang++;
        }

        System.out.println("─────────────────────────────────────────────────────────");
        System.out.printf("Subtotal               : Rp %.0f\n", transaction.getSubtotal());
        System.out.printf("Pajak (%.1f%%)           : Rp %.0f\n", 
            transaction.getPajak(), (transaction.getSubtotal() * transaction.getPajak() / 100));
        System.out.println("─────────────────────────────────────────────────────────");
        System.out.printf("TOTAL PEMBAYARAN       : Rp %.0f\n", transaction.getTotal());
        System.out.println("─────────────────────────────────────────────────────────");
        System.out.printf("Metode Pembayaran      : %s\n", transaction.getTipePembayaran().toUpperCase());
        
        if (transaction.getTipePembayaran().equals("tunai")) {
            double kembalian = jumlahBayar - transaction.getTotal();
            System.out.printf("Jumlah Bayar           : Rp %.0f\n", jumlahBayar);
            System.out.printf("Kembalian              : Rp %.0f\n", kembalian);
        }
        
        System.out.println("─────────────────────────────────────────────────────────");
        System.out.println("Status                 : " + transaction.getStatusPembayaran().toUpperCase());
        System.out.println("═════════════════════════════════════════════════════════");
        System.out.println("         TERIMA KASIH ATAS PEMBELIAN ANDA!");
        System.out.println("═════════════════════════════════════════════════════════");
        System.out.println();
    }

    public void simpanKeFile() {
        String filename = "struk_" + transaction.getTransactionId() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("═════════════════════════════════════════════════════════\n");
            writer.write("              STRUK PEMBAYARAN SUPERMARKET\n");
            writer.write("═════════════════════════════════════════════════════════\n");
            writer.write("ID Transaksi: " + transaction.getTransactionId() + "\n");
            writer.write("Nama Pembeli: " + transaction.getUsername() + "\n");
            writer.write("Tanggal    : " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(transaction.getTanggalTransaksi())) + "\n");
            writer.write("─────────────────────────────────────────────────────────\n");
            writer.write("RINCIAN BARANG:\n");
            writer.write("─────────────────────────────────────────────────────────\n");

            int noBarang = 1;
            for (TransactionItem item : transaction.getItems()) {
                writer.write(noBarang + ". " + item.getNama() + " | Qty: " + item.getJumlah() + "\n");
                writer.write("   Harga: Rp " + String.format("%.0f", item.getHarga()) + " x " + item.getJumlah() + 
                    " = Rp " + String.format("%.0f", item.getSubtotal()) + "\n");
                noBarang++;
            }

            writer.write("─────────────────────────────────────────────────────────\n");
            writer.write(String.format("Subtotal               : Rp %.0f\n", transaction.getSubtotal()));
            writer.write(String.format("Pajak (%.1f%%)           : Rp %.0f\n", 
                transaction.getPajak(), (transaction.getSubtotal() * transaction.getPajak() / 100)));
            writer.write("─────────────────────────────────────────────────────────\n");
            writer.write(String.format("TOTAL PEMBAYARAN       : Rp %.0f\n", transaction.getTotal()));
            writer.write("─────────────────────────────────────────────────────────\n");
            writer.write("Metode Pembayaran      : " + transaction.getTipePembayaran().toUpperCase() + "\n");
            
            if (transaction.getTipePembayaran().equals("tunai")) {
                double kembalian = jumlahBayar - transaction.getTotal();
                writer.write(String.format("Jumlah Bayar           : Rp %.0f\n", jumlahBayar));
                writer.write(String.format("Kembalian              : Rp %.0f\n", kembalian));
            }
            
            writer.write("─────────────────────────────────────────────────────────\n");
            writer.write("Status                 : " + transaction.getStatusPembayaran().toUpperCase() + "\n");
            writer.write("═════════════════════════════════════════════════════════\n");
            writer.write("         TERIMA KASIH ATAS PEMBELIAN ANDA!\n");
            writer.write("═════════════════════════════════════════════════════════\n");
            
            System.out.println("Struk berhasil disimpan ke file: " + filename);
        } catch (IOException e) {
            System.out.println("Error saat menyimpan struk: " + e.getMessage());
        }
    }
}
