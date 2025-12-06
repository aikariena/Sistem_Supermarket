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

    public String getIdBarang() { return idBarang; }
    public String getNama() { return nama; }
    public int getHarga() { return harga; }
    public int getJumlah() { return jumlah; }

    public void setJumlah(int jumlah) { this.jumlah = jumlah; }

    @Override
    public String toString() {
        return idBarang + "|" + nama + "|" + harga + "|" + jumlah;
    }

    public static CartItem fromString(String line){
        try {
        String[] parts = line.split("\\|");
        if (parts.length != 4) return null;

        String id = parts[0].trim();
        String nama = parts[1].trim();
        int harga = Integer.parseInt(parts[2].trim());
        int jumlah = Integer.parseInt(parts[3].trim());

        return new CartItem(id, nama, harga, jumlah);

    } catch (Exception e) {
        return null; // kalau error langsung skip
    }
    }
}
