package Login;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class MemberBalance {
    private HashMap<String, Double> saldoMember = new HashMap<>();
    private final String NAMA_FILE = "Login/saldo_member.txt";

    public MemberBalance() {
        bacaDataDariFile();
    }

    private void bacaDataDariFile() {
        File file = new File(NAMA_FILE);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String baris = fileScanner.nextLine();
                String[] data = baris.split(";");
                
                if (data.length == 2) {
                    String username = data[0];
                    double saldo = Double.parseDouble(data[1]);
                    saldoMember.put(username, saldo);
                }
            }
        } catch (Exception e) {
            System.out.println("Error saat membaca saldo member: " + e.getMessage());
        }
    }

    private void simpanDataKeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NAMA_FILE))) {
            for (String username : saldoMember.keySet()) {
                double saldo = saldoMember.get(username);
                writer.write(username + ";" + saldo);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saat menyimpan saldo member: " + e.getMessage());
        }
    }

    // Inisialisasi saldo member baru
    public void initSaldoMember(String username) {
        if (!saldoMember.containsKey(username)) {
            saldoMember.put(username, 0.0);
            simpanDataKeFile();
        }
    }

    // Lihat saldo
    public double lihatSaldo(String username) {
        return saldoMember.getOrDefault(username, 0.0);
    }

    // Setor saldo
    public boolean setorSaldo(String username, double jumlah) {
        if (jumlah <= 0) {
            System.out.println("❌ Jumlah setor harus lebih dari 0!");
            return false;
        }

        double saldoSekarang = saldoMember.getOrDefault(username, 0.0);
        saldoMember.put(username, saldoSekarang + jumlah);
        simpanDataKeFile();
        
        System.out.println("✅ Setor saldo berhasil!");
        System.out.printf("   Jumlah: Rp%.0f\n", jumlah);
        System.out.printf("   Saldo baru: Rp%.0f\n", saldoMember.get(username));
        return true;
    }

    // Tarik saldo
    public boolean tarikSaldo(String username, double jumlah) {
        if (jumlah <= 0) {
            System.out.println("❌ Jumlah tarik harus lebih dari 0!");
            return false;
        }

        double saldoSekarang = saldoMember.getOrDefault(username, 0.0);
        
        if (saldoSekarang < jumlah) {
            System.out.println("❌ Saldo tidak cukup!");
            System.out.printf("   Saldo Anda: Rp%.0f\n", saldoSekarang);
            return false;
        }

        saldoMember.put(username, saldoSekarang - jumlah);
        simpanDataKeFile();
        
        System.out.println("✅ Tarik saldo berhasil!");
        System.out.printf("   Jumlah: Rp%.0f\n", jumlah);
        System.out.printf("   Saldo baru: Rp%.0f\n", saldoMember.get(username));
        return true;
    }

    // Pembayaran (kurangi saldo)
    public boolean bayarDariSaldo(String username, double jumlah) {
        if (jumlah <= 0) {
            return false;
        }

        double saldoSekarang = saldoMember.getOrDefault(username, 0.0);
        
        if (saldoSekarang < jumlah) {
            return false;
        }

        saldoMember.put(username, saldoSekarang - jumlah);
        simpanDataKeFile();
        return true;
    }

    // Update saldo member (untuk admin)
    public void updateSaldo(String username, double saldoBaru) {
        saldoMember.put(username, saldoBaru);
        simpanDataKeFile();
    }

    // Hapus saldo member (saat member dihapus)
    public void hapusSaldomember(String username) {
        if (saldoMember.containsKey(username)) {
            saldoMember.remove(username);
            simpanDataKeFile();
        }
    }
}
