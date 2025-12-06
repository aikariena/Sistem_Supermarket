package Pembayaran;

import java.util.ArrayList;

public class Transaction {
    private String transactionId;
    private String username;
    private ArrayList<TransactionItem> items;
    private double subtotal;
    private double pajak;
    private double total;
    private String statusPembayaran; // "pending", "completed", "cancelled"
    private String tipePembayaran; // "tunai", "non-tunai"
    private long tanggalTransaksi;

    public Transaction(String transactionId, String username, ArrayList<TransactionItem> items, double pajak) {
        this.transactionId = transactionId;
        this.username = username;
        this.items = items;
        this.pajak = pajak;
        this.statusPembayaran = "pending";
        this.tanggalTransaksi = System.currentTimeMillis();
        hitungSubtotal();
    }

    private void hitungSubtotal() {
        this.subtotal = 0;
        for (TransactionItem item : items) {
            this.subtotal += item.getSubtotal();
        }
        this.total = this.subtotal + (this.subtotal * this.pajak / 100);
    }

    public String getTransactionId() { return transactionId; }
    public String getUsername() { return username; }
    public ArrayList<TransactionItem> getItems() { return items; }
    public double getSubtotal() { return subtotal; }
    public double getPajak() { return pajak; }
    public double getTotal() { return total; }
    public String getStatusPembayaran() { return statusPembayaran; }
    public String getTipePembayaran() { return tipePembayaran; }
    public long getTanggalTransaksi() { return tanggalTransaksi; }

    public void setStatusPembayaran(String status) { this.statusPembayaran = status; }
    public void setTipePembayaran(String tipe) { this.tipePembayaran = tipe; }
}
