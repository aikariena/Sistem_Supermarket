package Pembayaran;

public class TransactionItem {
    private String idBarang;
    private String nama;
    private double harga;
    private int jumlah;

    public TransactionItem(String idBarang, String nama, double harga, int jumlah) {
        this.idBarang = idBarang;
        this.nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public String getIdBarang() { return idBarang; }
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public int getJumlah() { return jumlah; }

    public double getSubtotal() {
        return harga * jumlah;
    }
}
