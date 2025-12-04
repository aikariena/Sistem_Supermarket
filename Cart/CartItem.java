public class CartItem {
    private String nama;
    private int harga;
    private int jumlah;

    public CartItem(String nama, int harga, int jumlah) {
        this.nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public String getNama() { return nama; }
    public int getHarga() { return harga; }
    public int getJumlah() { return jumlah; }

    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
}
