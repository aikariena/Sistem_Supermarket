package Pembayaran;

import Gudang.Barang;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LaporanPenjualan {
    private final String GUDANG_FILE = "Gudang/database.txt";
    private final String STRUK_PREFIX = "struk_TRX";
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("id", "ID"));
    private final SimpleDateFormat DATE_ONLY_FORMAT = new SimpleDateFormat("dd/MM/yyyy", new Locale("id", "ID"));

    public LaporanPenjualan() {
        // Constructor is empty for now
    }

    private String formatRupiah(double angka) {
        // Menggunakan Locale.US untuk memastikan format yang benar, kemudian mengganti separator jika perlu
        return String.format("Rp %,.0f", angka).replace(',', '.');
    }

    // Helper untuk membaca stok saat ini (Stok Akhir) dari database gudang
    private Map<String, Barang> getStockAkhir() {
        Map<String, Barang> stockAkhir = new HashMap<>();
        File file = new File(GUDANG_FILE);
        // Coba dengan path lain jika Gudang/ tidak ditemukan
        if (!file.exists()) {
            file = new File("database.txt");
            if (!file.exists()) {
                System.out.println("Peringatan: File database gudang tidak ditemukan.");
                return stockAkhir;
            }
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String baris = fileScanner.nextLine();
                // Asumsi: Baris data barang dimulai dengan Bxxx
                if (baris.toLowerCase().startsWith("b0")) {
                    String[] data = baris.split(";");

                    if (data.length == 4) {
                        String id = data[0];
                        String nama = data[1];
                        try {
                            double harga = Double.parseDouble(data[2]);
                            int stok = Integer.parseInt(data[3]);
                            stockAkhir.put(id, new Barang(id, nama, harga, stok));
                        } catch (NumberFormatException ignored) {
                            // Abaikan baris yang tidak valid
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error saat membaca database gudang: " + e.getMessage());
        }
        return stockAkhir;
    }

    public void generateLaporanHarian(String tanggal) {
        // Format tanggal input ke format per hari (dd/MM/yyyy)
        Date tglLaporan;
        try {
            tglLaporan = DATE_ONLY_FORMAT.parse(tanggal);
        } catch (ParseException e) {
            System.out.println("Format tanggal tidak valid. Gunakan format DD/MM/YYYY (contoh: 06/12/2025)");
            return;
        }
        String tanggalStr = DATE_ONLY_FORMAT.format(tglLaporan);
        
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        LAPORAN PENJUALAN HARIAN");
        System.out.println("║        Tanggal: " + tanggalStr);
        System.out.println("╚════════════════════════════════════════════════╝");

        double totalPendapatanKotor = 0;
        int totalBarangTerjual = 0;
        
        Map<String, Integer> barangTerjualHarian = new HashMap<>();
        
        // 1. Dapatkan daftar file struk.
        File folder = new File("."); 
        // Menggunakan filter agar hanya memproses file struk TRX*.txt
        File[] listOfFiles = folder.listFiles((dir, name) -> name.startsWith(STRUK_PREFIX) && name.endsWith(".txt"));

        if (listOfFiles == null) {
             System.out.println("Peringatan: Gagal mengakses daftar file. Tidak ada laporan yang dapat diproses.");
             return;
        }
        
        // 2. Proses semua file struk
        int transaksiDiproses = 0;
        for (File file : listOfFiles) {
            try (Scanner scanner = new Scanner(file)) {
                String line;
                boolean isTargetDate = false;
                double totalPembayaran = 0;
                
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    
                    // Cek Tanggal
                    if (line.contains("Tanggal    : ")) {
                        String tglTransaksiStr = line.split(": ")[1].trim();
                        // Ambil bagian tanggal saja
                        String tglTransaksiPart = tglTransaksiStr.split(" ")[0]; 
                        
                        if (tglTransaksiPart.equals(tanggalStr)) {
                            isTargetDate = true;
                            transaksiDiproses++;
                        } else {
                            // Skip file jika tanggal tidak cocok
                            break; 
                        }
                    }

                    // Cek Total Pembayaran (Perbaikan Bug Total Pendapatan)
                    if (line.contains("TOTAL PEMBAYARAN")) {
                        // Pola diperbaiki agar fleksibel dengan spasi
                        // (.*?) akan menangkap semua yang ada setelah Rp hingga akhir baris
                        Pattern totalPattern = Pattern.compile("TOTAL PEMBAYARAN\\s*:\\s*Rp\\s*(.*)");
                        Matcher totalMatcher = totalPattern.matcher(line);
                        
                        if (totalMatcher.find()) {
                            String rawTotalStr = totalMatcher.group(1).trim();
                            
                            // Hapus semua karakter non-digit, non-titik, dan non-koma
                            // Kemudian hapus semua pemisah ribuan (titik dan koma)
                            String cleanedTotalStr = rawTotalStr.replaceAll("[^\\d\\.,]", "")
                                .replace(".", "")
                                .replace(",", "");
                            
                            try {
                                totalPembayaran = Double.parseDouble(cleanedTotalStr);
                                totalPendapatanKotor += totalPembayaran;
                            } catch (NumberFormatException nfe) {
                                // Memberi peringatan jika parsing jumlah gagal
                                System.out.println("Peringatan: Gagal parsing jumlah pembayaran '" + rawTotalStr + "' di file " + file.getName());
                            }
                        }
                    }
                    
                    // Cek Rincian Barang
                    // Pola: 1. Nama Barang | Qty: X
                    Pattern itemPattern = Pattern.compile("(\\d+)\\. (.*) \\|\\s*Qty: (\\d+)");
                    Matcher itemMatcher = itemPattern.matcher(line);
                    
                    if (itemMatcher.find()) {
                        String namaBarang = itemMatcher.group(2).trim();
                        int qty = Integer.parseInt(itemMatcher.group(3));
                        totalBarangTerjual += qty;
                        
                        // Gunakan Nama Barang sebagai kunci untuk melacak barang terjual
                        barangTerjualHarian.put(namaBarang, 
                            barangTerjualHarian.getOrDefault(namaBarang, 0) + qty);
                    }
                }
            } catch (Exception e) { // Menangani ParseException, IOException, dll.
                // Solusi untuk error "Unreachable catch block for ParseException"
                String errorMsg = "Error saat memproses struk " + file.getName() + ": " + e.getMessage();
                if (e instanceof ParseException) {
                    errorMsg = "Error saat parsing format di struk: " + file.getName();
                } else if (e instanceof FileNotFoundException) {
                    errorMsg = "Error: File struk tidak ditemukan: " + file.getName();
                }
                System.out.println(errorMsg);
            }
        }
        
        // 3. Output Ringkasan
        if (transaksiDiproses == 0) {
            System.out.println("\nTidak ada transaksi ditemukan untuk tanggal " + tanggalStr + ".");
            return;
        }

        System.out.println("\n┌────────────────────────────────────────────────┐");
        System.out.println("│              RINGKASAN PENJUALAN               │");
        System.out.println("├────────────────────────────────────────────────┤");
        System.out.printf("│ Total Transaksi Diproses     : %-23d         \n", transaksiDiproses);
        System.out.printf("│ Total Pendapatan Kotor       : %-23s \n", formatRupiah(totalPendapatanKotor));
        System.out.printf("│ Total Barang Terjual         : %-23d     \n", totalBarangTerjual);
        System.out.println("└────────────────────────────────────────────────┘");

        // 4. Laporan Stock Awal/Akhir (Verifikasi Stok)
        verifikasiStok(tanggalStr, barangTerjualHarian);
    }

    // Method verifikasiStok tetap menggunakan logic tanpa mencetak barang yang tidak terjual
    private void verifikasiStok(String tanggalStr, Map<String, Integer> barangTerjualHarian) {
        Map<String, Barang> stockAkhir = getStockAkhir();
        
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║        VERIFIKASI STOK DAN BARANG TERJUAL");
        System.out.println("║        (Stok Awal dihitung secara Teoritis)");
        System.out.println("╚════════════════════════════════════════════════╝");
        
        // Header
        System.out.println("┌───────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf("| %-30s | %-20s | %-15s | %-15s | %-10s |\n", 
            "Nama Barang", "ID Barang (DB)", "Stok Awal     ", "Terjual Hari Ini", "Stok Akhir  ");
        System.out.println("├───────────────────────────────────────────────────────────────────────────────────────────────────────────┤");
        
        boolean foundSale = false;
        
        // Iterasi berdasarkan barang yang terjual hari ini
        for (Map.Entry<String, Integer> entry : barangTerjualHarian.entrySet()) {
            String namaBarangTerjual = entry.getKey().trim();
            int terjual = entry.getValue();
            
            // Cari ID Barang di Stock Akhir (menggunakan nama)
            Barang barangFinal = null;
            String idBarangDB = "Unknown";
            
            for (Barang b : stockAkhir.values()) {
                // Mencari nama barang di DB yang mengandung nama dari struk
                if (b.getNama().toLowerCase().contains(namaBarangTerjual.toLowerCase())) {
                    barangFinal = b;
                    idBarangDB = b.getIdBarang();
                    break;
                }
            }
            
            if (barangFinal != null) {
                foundSale = true;
                int stokAkhir = barangFinal.getStok();
                int stokAwalTeoritis = stokAkhir + terjual;

                System.out.printf("| %-30s | %-20s | %-15d | %-15d | %-10d    |\n", 
                    barangFinal.getNama(), idBarangDB, stokAwalTeoritis, terjual, stokAkhir);
                
                // Hapus barang yang sudah diproses dari stockAkhir karena sudah dilaporkan
                stockAkhir.remove(barangFinal.getIdBarang());
                
            } else {
                // Barang terjual, tapi tidak ditemukan di database saat ini 
                System.out.printf("| %-30s | %-20s | %-15s | %-15d | %-10s    |\n", 
                    namaBarangTerjual, idBarangDB, "N/A", terjual, "N/A");
            }
        }
        
        if (!foundSale) {
            System.out.println("| Tidak ada barang terjual yang tercatat pada tanggal ini.                                                 |");
        }
        
        // Akhir laporan (tanpa mencetak barang yang tidak terjual)
        System.out.println("└───────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
    }
}