import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CartMenu {
    private ArrayList<CartItem> cart = new ArrayList<>();
    private Scanner input = new Scanner(System.in);

    private final String FILE_KERANJANG = "keranjang.txt";
    private final String FILE_GUDANG = "../Gudang/database.txt"; // berisi data ID|Nama|Harga|?|Stok

    // key=id, value=[nama, harga]
    private HashMap<String, String[]> gudangData = new HashMap<>(); 

    public static void main(String[] args) {
        CartMenu menu = new CartMenu();
        // 1. Memuat data gudang
        menu.loadGudang(); 
        // 2. Memuat keranjang dari sesi sebelumnya
        menu.loadKeranjang(); 
        // 3. Menampilkan menu utama
        menu.ShowMenu(); 
        // 4. Menyimpan keranjang saat keluar
        menu.saveKeranjang();
    }

    // Perbaikan: Load data gudang dan simpan ke gudangData
    private void loadGudang() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_GUDANG))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";"); // ID|Nama|Harga|?|Stok
                
                // Cek apakah minimal memiliki 3 bagian (ID, Nama, Harga)
                if (parts.length >= 3) {
                    String id = parts[0];
                    String nama = parts[1];
                    // Ambil harga (parts[2]) sebagai String
                    String hargaStr = parts[2]; 
                    
                    // Simpan data ID -> [Nama, Harga]
                    gudangData.put(id, new String[]{nama, hargaStr});
                }
            }
            System.out.println("\n✅ Data gudang berhasil dimuat: " + gudangData.size() + " item.");
        } catch (IOException e) {
            System.err.println("\n❌ ERROR: File gudang tidak ditemukan! Pastikan jalur : " + FILE_GUDANG);
        }
    }

    // Menu utama
    public void ShowMenu() {
        int pilihMenu = 0;
        do {
            System.out.println("\n=== KERANJANG SUPERMARKET ===");
            System.out.println("1. Tambah barang ke keranjang");
            System.out.println("2. Update jumlah barang");
            System.out.println("3. Hapus barang dari keranjang");
            System.out.println("4. Lihat isi keranjang");
            System.out.println("5. Hitung subtotal");
            System.out.println("6. Keluar");
            System.out.print("Pilih Menu : ");
            
            // Handle input non-integer
            if (input.hasNextInt()) {
                pilihMenu = input.nextInt();
                input.nextLine();
            } else {
                System.out.println("Input tidak valid. Masukkan angka.");
                input.nextLine(); // Kosongkan buffer
                continue;
            }


            switch (pilihMenu) {
                case 1: tambahKeranjang(); break;
                case 2: updateJumlah(); break;
                case 3: hapusKeranjang(); break;
                case 4: tampilKeranjang(); break;
                case 5: hitungSubtotal(); break;
                case 6: System.out.println("Terima kasih! Keranjang disimpan."); break;
                default: System.out.println("Pilihan tidak ada.");
            }
        } while (pilihMenu != 6);
    }

    // Tambah barang berdasarkan ID dari gudang
    private void tambahKeranjang() {
        System.out.print("Masukkan ID Barang: ");
        String id = input.nextLine().toUpperCase(); // Pastikan ID huruf besar/kecilnya sama

        if (!gudangData.containsKey(id)) {
            System.out.println("❌ ID tidak ditemukan di gudang!");
            return;
        }

        String[] data = gudangData.get(id);
        String nama = data[0];
        // Konversi harga (yang disimpan sebagai String) menjadi int
        int harga = (int) Double.parseDouble(data[1]); 

        System.out.print("Jumlah: ");
        int jumlah;
        if (input.hasNextInt()) {
            jumlah = input.nextInt();
            input.nextLine();
        } else {
            System.out.println("❌ Input jumlah tidak valid.");
            input.nextLine();
            return;
        }

        if (jumlah <= 0) {
            System.out.println("❌ Jumlah harus lebih dari nol.");
            return;
        }

        // cek kalau sudah ada di keranjang
        boolean found = false;
        for (CartItem item : cart) {
            if (item.getIdBarang().equals(id)) {
                item.setJumlah(item.getJumlah() + jumlah);
                found = true;
                break;
            }
        }
        if (!found) {
            cart.add(new CartItem(id, nama, harga, jumlah));
        }

        System.out.println("Barang '" + nama + "' ditambahkan ke keranjang!");
        saveKeranjang();
    }

    private void updateJumlah() {
        if(cart.isEmpty()) {
            System.out.println("Keranjang kosong.");
            return;
        }
        tampilKeranjang();
        
        System.out.print("Pilih nomor barang untuk update: ");
        int idx = -1;
        if (input.hasNextInt()) {
            idx = input.nextInt() - 1;
            input.nextLine();
        } else {
            System.out.println("❌ Input nomor tidak valid.");
            input.nextLine();
            return;
        }

        if(idx >=0 && idx < cart.size()) {
            System.out.print("Jumlah baru: ");
            int jumlah;
            if (input.hasNextInt()) {
                jumlah = input.nextInt();
                input.nextLine();
            } else {
                System.out.println("❌ Input jumlah tidak valid.");
                input.nextLine();
                return;
            }

            if (jumlah > 0) {
                cart.get(idx).setJumlah(jumlah);
                System.out.println("Jumlah diperbarui!");
                saveKeranjang();
            } else if (jumlah == 0) {
                // Langsung hapus jika jumlah di set 0
                String namaHapus = cart.get(idx).getNama();
                cart.remove(idx);
                System.out.println("Barang '" + namaHapus + "' dihapus dari keranjang (karena jumlah = 0).");
                saveKeranjang();
            } else {
                System.out.println("❌ Jumlah tidak boleh negatif.");
            }
        } else {
            System.out.println("❌ Nomor tidak valid!");
        }
    }

    private void hapusKeranjang() {
        if(cart.isEmpty()) {
            System.out.println("Keranjang kosong.");
            return;
        }
        tampilKeranjang();
        System.out.print("Pilih nomor barang untuk hapus: ");
        int idx = -1;
        if (input.hasNextInt()) {
            idx = input.nextInt() - 1;
            input.nextLine();
        } else {
            System.out.println("❌ Input nomor tidak valid.");
            input.nextLine();
            return;
        }

        if(idx >=0 && idx < cart.size()) {
            String namaHapus = cart.get(idx).getNama();
            cart.remove(idx);
            System.out.println("Barang '" + namaHapus + "' berhasil dihapus!");
            saveKeranjang();
        } else {
            System.out.println("❌ Nomor tidak valid!");
        }
    }

    private void tampilKeranjang() {
        System.out.println("\n=== ISI KERANJANG ===");
        if(cart.isEmpty()) {
            System.out.println("Keranjang kosong.");
            return;
        }
        
        System.out.printf("%-5s %-15s %-25s %-10s %-10s\n", "No.", "ID", "Nama Barang", "Harga", "Jumlah");
        System.out.println("-----------------------------------------------------------------");
        
        int no=1;
        for(CartItem item : cart) {
            String hargaFormatted = String.format("Rp%,d", item.getHarga());
            System.out.printf("%-5d %-15s %-25s %-10s %-10d\n", 
                no++, 
                item.getIdBarang(), 
                item.getNama(), 
                hargaFormatted, 
                item.getJumlah());
        }
        System.out.println("-----------------------------------------------------------------");
    }

    private void hitungSubtotal() {
        long total=0; // Gunakan long untuk menghindari overflow jika total sangat besar
        for(CartItem item : cart) {
            // Konversi harga dan jumlah ke long sebelum dikalikan
            total += (long)item.getHarga() * item.getJumlah(); 
        }
        String totalFormatted = String.format("Rp%,d", total);
        System.out.println("Total Subtotal: " + totalFormatted);
    }

    // Save/load keranjang
    private void saveKeranjang() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_KERANJANG))) {
            for(CartItem item : cart) {
                bw.write(item.toString());
                bw.newLine();
            }
        } catch(IOException e) {
            System.err.println("❌ Error menyimpan keranjang: "+e.getMessage());
        }
    }

    private void loadKeranjang() {
        try(BufferedReader br = new BufferedReader(new FileReader(FILE_KERANJANG))) {
            String line;
            int loadedCount = 0;
            while((line=br.readLine())!=null) {
                CartItem item = CartItem.fromString(line);
                if(item != null) {
                    cart.add(item);
                    loadedCount++;
                }
            }
            if (loadedCount > 0) {
                System.out.println("✅ " + loadedCount + " item keranjang berhasil dimuat dari sesi sebelumnya.");
            }
        } catch(IOException e) {
            // Ini normal jika file belum ada
            System.out.println("Keranjang kosong / file belum ada.");
        }
    }
}