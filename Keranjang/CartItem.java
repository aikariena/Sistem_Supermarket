public class CartItem {
    private String idBarang;
    private String nama;
    private int harga;
    private int jumlah;

    public CartItem(String idBarang, String nama, int harga, int jumlah) {
        this.idBarang = idBarang;
        this.nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public String getNama() { return nama; }
    public int getHarga() { return harga; }
    public int getJumlah() { return jumlah; }
    public String getIdBarang() { return idBarang; }

    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
}
