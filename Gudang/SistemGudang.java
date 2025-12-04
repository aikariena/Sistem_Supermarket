import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

class Barang {
    private String id;
    private String nama;
    private double harga;
    private int stok;

    public Barang(String id, String nama, double harga, int stok) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    public String getId() { return id; }
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public int getStok() { return stok; }
    
    public void setNama(String nama) { this.nama = nama; }
    public void setHarga(double harga) { this.harga = harga; }
    public void setStok(int stok) { this.stok = stok; }
}

class Gudang {
    private LinkedList<Barang> daftarBarang = new LinkedList<>();

    public void tambahBarang(String id, String nama, double harga, int stok) {
        for (Barang b : daftarBarang) {
            if (b.getId().equalsIgnoreCase(id)) {
                System.out.println("Gagal: ID Barang sudah ada!");
                return;
            }
        }
        Barang barangBaru = new Barang(id, nama, harga, stok);
        daftarBarang.add(barangBaru);
        System.out.println("Sukses: Barang berhasil ditambahkan.");
    }

    public void hapusBarang(String id) {
        Iterator<Barang> iterator = daftarBarang.iterator();
        while (iterator.hasNext()) {
            Barang b = iterator.next();
            if (b.getId().equalsIgnoreCase(id)) {
                iterator.remove();
                System.out.println("Sukses: Barang dengan ID " + id + " telah dihapus.");
                return;
            }
        }
        System.out.println("Gagal: Barang dengan ID tersebut tidak ditemukan.");
    }

    public void updateStok(String id, int stokBaru) {
        for (Barang b : daftarBarang) {
            if (b.getId().equalsIgnoreCase(id)) {
                b.setStok(stokBaru);
                System.out.println("Sukses: Stok barang '" + b.getNama() + "' menjadi " + stokBaru);
                return;
            }
        }
        System.out.println("Gagal: ID Barang tidak ditemukan.");
    }

    public void editBarang(String id, String namaBaru, double hargaBaru) {
        for (Barang b : daftarBarang) {
            if (b.getId().equalsIgnoreCase(id)) {
                b.setNama(namaBaru);
                b.setHarga(hargaBaru);
                System.out.println("Sukses: Data barang berhasil diperbarui.");
                return;
            }
        }
        System.out.println("Gagal: ID Barang tidak ditemukan.");
    }

    public void tampilkanSemuaBarang() {
        if (daftarBarang.isEmpty()) {
            System.out.println("Gudang kosong.");
            return;
        }

        System.out.println("-----------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-15s | %-8s |\n", "ID", "Nama Barang", "Harga (Rp)", "Stok");
        System.out.println("-----------------------------------------------------------------");
        for (Barang b : daftarBarang) {
            System.out.printf("| %-10s | %-20s | Rp %-12.0f | %-8d |\n", 
                b.getId(), b.getNama(), b.getHarga(), b.getStok());
        }
        System.out.println("-----------------------------------------------------------------");
    }

    public void cariBarang(String keyword) {
        boolean ditemukan = false;
        System.out.println("Hasil Pencarian:");
        for (Barang b : daftarBarang) {
            if (b.getId().equalsIgnoreCase(keyword) || b.getNama().toLowerCase().contains(keyword.toLowerCase())) {
                String status = (b.getStok() > 0) ? "Tersedia" : "Habis";
                System.out.println("- [" + b.getId() + "] " + b.getNama() + " | Stok: " + b.getStok() + " (" + status + ")");
                ditemukan = true;
            }
        }
        if (!ditemukan) {
            System.out.println("Barang tidak ditemukan.");
        }
    }
}

public class SistemGudang {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Gudang gudang = new Gudang();
        int pilihan = 0;

        do {
            System.out.println("\n=== MENU GUDANG SUPERMARKET ===");
            System.out.println("1. Tampilkan Daftar Barang");
            System.out.println("2. Tambah Barang Baru");
            System.out.println("3. Update Stok Barang");
            System.out.println("4. Edit Info Barang (Nama/Harga)");
            System.out.println("5. Hapus Barang");
            System.out.println("6. Cari / Cek Ketersediaan");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            
            try {
                pilihan = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Input harus angka!");
                scanner.nextLine();
                continue;
            }

            System.out.println();

            switch (pilihan) {
                case 1:
                    gudang.tampilkanSemuaBarang();
                    break;

                case 2:
                    System.out.print("Masukkan ID Barang: ");
                    String id = scanner.nextLine();
                    System.out.print("Masukkan Nama Barang: ");
                    String nama = scanner.nextLine();
                    System.out.print("Masukkan Harga: ");
                    double harga = scanner.nextDouble();
                    System.out.print("Masukkan Jumlah Stok: ");
                    int stok = scanner.nextInt();
                    gudang.tambahBarang(id, nama, harga, stok);
                    break;

                case 3:
                    System.out.print("Masukkan ID Barang yang akan diupdate: ");
                    String idStok = scanner.nextLine();
                    System.out.print("Masukkan Jumlah Stok Baru (Total Akhir): ");
                    int stokBaru = scanner.nextInt();
                    gudang.updateStok(idStok, stokBaru);
                    break;

                case 4:
                    System.out.print("Masukkan ID Barang yang diedit: ");
                    String idEdit = scanner.nextLine();
                    System.out.print("Masukkan Nama Baru: ");
                    String namaBaru = scanner.nextLine();
                    System.out.print("Masukkan Harga Baru: ");
                    double hargaBaru = scanner.nextDouble();
                    gudang.editBarang(idEdit, namaBaru, hargaBaru);
                    break;

                case 5:
                    System.out.print("Masukkan ID Barang yang akan dihapus: ");
                    String idHapus = scanner.nextLine();
                    gudang.hapusBarang(idHapus);
                    break;

                case 6:
                    System.out.print("Cari Nama atau ID Barang: ");
                    String keyword = scanner.nextLine();
                    gudang.cariBarang(keyword);
                    break;

                case 0:
                    System.out.println("Sistem dimatikan.");
                    break;

                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 0);

        scanner.close();
    }
}