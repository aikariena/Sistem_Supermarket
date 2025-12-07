package Gudang;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Gudang {
    private LinkedList<Barang> daftarBarang = new LinkedList<>();
    private final String NAMA_FILE = "Gudang/database.txt";

    public Gudang() {
        bacaDataDariFile();
    }

    private void simpanDataKeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NAMA_FILE))) {
            for (Barang b : daftarBarang) {
                writer.write(b.toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saat menyimpan ke database: " + e.getMessage());
        }
    }

    private void bacaDataDariFile() {
        File file = new File(NAMA_FILE);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String baris = fileScanner.nextLine();
                String[] data = baris.split(";");

                if (data.length == 4) { 
                    String id = data[0];
                    String nama = data[1];
                    double harga = Double.parseDouble(data[2]);
                    int stok = Integer.parseInt(data[3]);
                    
                    daftarBarang.add(new Barang(id, nama, harga, stok));
                }
            }
        } catch (Exception e) {
            System.out.println("Error saat membaca database: " + e.getMessage());
        }
    }

    public void tambahBarang(String idBarang, String nama, double harga, int stok) {
        for (Barang b : daftarBarang) {
            if (b.getIdBarang().equalsIgnoreCase(idBarang)) {
                System.out.println("Gagal: ID Barang sudah ada!");
                return;
            }
        }
        daftarBarang.add(new Barang(idBarang, nama, harga, stok));
        simpanDataKeFile();
        System.out.println("Sukses: Barang ditambahkan dan disimpan.");
    }

    public void hapusBarang(String idBarang) {
        Iterator<Barang> iterator = daftarBarang.iterator();
        while (iterator.hasNext()) {
            Barang b = iterator.next();
            if (b.getIdBarang().equalsIgnoreCase(idBarang)) {
                iterator.remove();
                simpanDataKeFile();
                System.out.println("Sukses: Barang dihapus dari database.");
                return;
            }
        }
        System.out.println("Gagal: Barang tidak ditemukan.");
    }

    public void updateStok(String idBarang, int stokBaru) {
        for (Barang b : daftarBarang) {
            if (b.getIdBarang().equalsIgnoreCase(idBarang)) {
                b.setStok(stokBaru);
                simpanDataKeFile();
                System.out.println("Sukses: Stok diperbarui.");
                return;
            }
        }
        System.out.println("Gagal: ID Barang tidak ditemukan.");
    }

    public void editBarang(String idBarang, String namaBaru, double hargaBaru) {
        for (Barang b : daftarBarang) {
            if (b.getIdBarang().equalsIgnoreCase(idBarang)) {
                b.setNama(namaBaru);
                b.setHarga(hargaBaru);
                simpanDataKeFile();
                System.out.println("Sukses: Data barang diperbarui.");
                return;
            }
        }
        System.out.println("Gagal: ID Barang tidak ditemukan.");
    }

    public void tampilkanSemuaBarang() {
        if (daftarBarang.isEmpty()) {
            System.out.println("Gudang kosong (Database kosong).");
            return;
        }

        System.out.println("----------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-35s | %-20s | %-10s |\n", "ID Barang", "Nama Barang", "Harga (Rp)", "Stok");
        System.out.println("----------------------------------------------------------------------------------------");
        for (Barang b : daftarBarang) {
            System.out.printf("| %-10s | %-35s | Rp %-17.0f | %-10d |\n", 
                b.getIdBarang(), b.getNama(), b.getHarga(), b.getStok());
        }
        System.out.println("----------------------------------------------------------------------------------------");
    }

    public void cariBarang(String keyword) {
        boolean ditemukan = false;
        System.out.println("Hasil Pencarian:");
        for (Barang b : daftarBarang) {
            if (b.getIdBarang().equalsIgnoreCase(keyword) || b.getNama().toLowerCase().contains(keyword.toLowerCase())) {
                String status = (b.getStok() > 0) ? "Tersedia" : "Habis";
                System.out.println("- [" + b.getIdBarang() + "] " + b.getNama() + " | Stok: " + b.getStok() +" | Harga: " + b.getHarga()+ " (" + status + ")");

                ditemukan = true;
            }
        }
        if (!ditemukan) {
            System.out.println("Barang tidak ditemukan.");
        }
    }

    public ArrayList<Barang> getSemuaBarang() {
        return new ArrayList<>(daftarBarang);
    }

    public Barang getBarangById(String idBarang) {
        for (Barang b : daftarBarang) {
            if (b.getIdBarang().equalsIgnoreCase(idBarang)) {
                return b;
            }
        }
        return null; // jika tidak ditemukan
    }


}

