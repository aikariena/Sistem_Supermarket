public class Barang {
    private String idBarang;
    private String nama;
    private double harga;
    private int stok;

    public Barang(String idBarang, String nama, double harga, int stok) {
        this.idBarang = idBarang;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    public String getIdBarang() { return idBarang; }
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public int getStok() { return stok; }
    
    public void setNama(String nama) { this.nama = nama; }
    public void setHarga(double harga) { this.harga = harga; }
    public void setStok(int stok) { this.stok = stok; }

    public String toCSV() {
        return idBarang + ";" + nama + ";" + harga + ";" + stok;
    }

}
