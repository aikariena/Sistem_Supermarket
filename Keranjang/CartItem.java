package Keranjang;

public class CartItem {
    private String idBarang;
    private String nama;
    private double harga;
    private int jumlah;

    public CartItem(String idBarang, String nama, double harga, int jumlah) {
        this.idBarang = idBarang;
        this.nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
    }

    public double getSubtotal() {
    // Menggunakan double untuk total agar sesuai dengan tipe data yang digunakan di CartCode (Rp%.0f)
    return (double) this.harga * this.jumlah; 
}

    // Getters
    public String getIdBarang() {
        return idBarang;
    }

    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    // Setter untuk jumlah
    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    // Konversi ke String untuk penyimpanan (ID|Nama|Harga|Jumlah)
    @Override
    public String toString() {
        return idBarang + ";" + nama + ";" + harga + ";" + jumlah;
    }

    // Metode statis untuk membuat objek dari String yang dimuat
    public static CartItem fromString(String line) {
        // Karena data gudang mungkin tidak konsisten, kita hanya mengandalkan 4 bagian
        String[] parts = line.split(";");
        if (parts.length == 4) {
            try {
                String id = parts[0];
                String nama = parts[1];
                int harga = (int) Double.parseDouble(parts[2]);
                int jumlah = Integer.parseInt(parts[3]);
                return new CartItem(id, nama, harga, jumlah);
            } catch (NumberFormatException e) {
                // System.err.println("Error parsing data keranjang: " + line);
                return null;
            }
        }
        return null;
    }
}