package Keranjang;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CartCode {
    private HashMap<String, ArrayList<CartItem>> cartDatabase = new HashMap<>();
    private final String NAMA_FILE = "Keranjang/cart_codes.txt";

    public CartCode() {
        bacaDataDariFile();
    }

    private void bacaDataDariFile() {
        File file = new File(NAMA_FILE);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String baris = fileScanner.nextLine();
                String[] data = baris.split("\\|");
                
                if (data.length >= 1) {
                    String kodeKeranjang = data[0];
                    ArrayList<CartItem> items = new ArrayList<>();
                    
                    // Parse items dari kode keranjang
                    for (int i = 1; i < data.length; i++) {
                        String[] itemData = data[i].split(";");
                        if (itemData.length == 4) {
                            String idBarang = itemData[0];
                            String nama = itemData[1];
                            int harga = Integer.parseInt(itemData[2]);
                            int jumlah = Integer.parseInt(itemData[3]);
                            
                            items.add(new CartItem(idBarang, nama, harga, jumlah));
                        }
                    }
                    
                    if (!items.isEmpty()) {
                        cartDatabase.put(kodeKeranjang, items);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error saat membaca cart codes: " + e.getMessage());
        }
    }

    private void simpanDataKeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NAMA_FILE))) {
            for (String kode : cartDatabase.keySet()) {
                StringBuilder sb = new StringBuilder(kode);
                ArrayList<CartItem> items = cartDatabase.get(kode);
                
                for (CartItem item : items) {
                    sb.append("|").append(item.getIdBarang()).append(";")
                      .append(item.getNama()).append(";")
                      .append(item.getHarga()).append(";")
                      .append(item.getJumlah());
                }
                
                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saat menyimpan cart codes: " + e.getMessage());
        }
    }

    // Buat kode keranjang baru dengan item
    public String buatKodeKeranjang(ArrayList<CartItem> items) {
        String kode = "CART-" + System.currentTimeMillis();
        cartDatabase.put(kode, items);
        simpanDataKeFile();
        return kode;
    }

    // Ambil keranjang berdasarkan kode
    public ArrayList<CartItem> getKeranjangByKode(String kodeKeranjang) {
        return cartDatabase.getOrDefault(kodeKeranjang, null);
    }

    // Hapus keranjang setelah pembayaran
    public void hapusKeranjang(String kodeKeranjang) {
        if (cartDatabase.containsKey(kodeKeranjang)) {
            cartDatabase.remove(kodeKeranjang);
            simpanDataKeFile();
        }
    }

    // Tampilkan semua kode keranjang
    public void tampilkanSemuaKodeKeranjang() {
        if (cartDatabase.isEmpty()) {
            System.out.println("Tidak ada keranjang terdaftar.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║         DAFTAR KODE KERANJANG");
        System.out.println("╚════════════════════════════════════════════════╝");
        
        int no = 1;
        for (String kode : cartDatabase.keySet()) {
            ArrayList<CartItem> items = cartDatabase.get(kode);
            double total = 0;
            for (CartItem item : items) {
                total += item.getSubtotal();
            }
            System.out.printf("%d. %s - %d item (Total: Rp%.0f)\n", no++, kode, items.size(), total);
        }
    }

    // Tampilkan detail keranjang
    public void tampilkanDetailKeranjang(String kodeKeranjang) {
        ArrayList<CartItem> items = cartDatabase.get(kodeKeranjang);
        if (items == null) {
            System.out.println("❌ Kode keranjang tidak ditemukan!");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║ DETAIL KERANJANG: " + kodeKeranjang);
        System.out.println("╚════════════════════════════════════════════════╝");

        double total = 0;
        System.out.printf("%-5s %-10s %-30s %-10s %-10s %-12s\n", 
            "No", "ID", "Nama", "Harga", "Jumlah", "Subtotal");
        System.out.println("─".repeat(82));

        int no = 1;
        for (CartItem item : items) {
            System.out.printf("%-5d %-10s %-30s Rp%-8.0f %-10d Rp%.0f\n",
                no++, item.getIdBarang(), item.getNama(),
                item.getHarga(), item.getJumlah(), item.getSubtotal());
            total += item.getSubtotal();
        }

        System.out.println("─".repeat(82));
        System.out.printf("TOTAL: Rp%.0f\n", total);
    }
}
