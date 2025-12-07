package Login;

import java.util.ArrayList;
import java.util.Scanner;
import Gudang.Gudang;
import Pembayaran.LaporanPenjualan;

public class AdminMenu {
    private LoginSystem loginSystem;
    private Gudang gudang;
    private Scanner input;

    public AdminMenu(LoginSystem loginSystem) {
        this.loginSystem = loginSystem;
        this.gudang = new Gudang();
        this.input = new Scanner(System.in);
    }

    public void showAdminMenu() {
        User admin = loginSystem.getUserSekarang();
        int pilih;
        do {
            System.out.println("\n--- MENU ADMIN (" + admin.getUsername() + ") ---");
            System.out.println("1. Menu Gudang");
            System.out.println("2. Kelola Admin");
            System.out.println("3. Kelola Kasir");
            System.out.println("4. Kelola Member");
            System.out.println("5. Laporan Penjualan");
            System.out.println("6. Logout");
            System.out.print("Pilih: ");
            pilih = input.nextInt();
            input.nextLine();

            switch (pilih) {
                case 1: menuGudang(); break;
                case 2: kelolaAdmin(); break;
                case 3: kelolaKasir(); break;
                case 4: kelolaMember(); break;
                case 5: lihatLaporan(); break;
                case 6: System.out.println("Logout!"); loginSystem.logout(); break;
                default: System.out.println("Invalid.");
            }
        } while (pilih != 6);
    }

    private void menuGudang() {
        int pilih;
        do {
            System.out.println("\n[MENU GUDANG]");
            System.out.println("1. Tampilkan Semua | 2. Cari | 3. Tambah | 4. Edit | 5. Hapus | 6. Stok | 7. Back");
            System.out.print("Pilih: ");
            pilih = input.nextInt(); input.nextLine();
            
            switch (pilih) {
                case 1: gudang.tampilkanSemuaBarang(); break;
                case 2: System.out.print("Keyword: "); gudang.cariBarang(input.nextLine()); break;
                case 3: 
                    System.out.print("ID: "); String id = input.nextLine();
                    System.out.print("Nama: "); String nm = input.nextLine();
                    System.out.print("Harga: "); double hg = input.nextDouble();
                    System.out.print("Stok: "); int st = input.nextInt(); input.nextLine();
                    gudang.tambahBarang(id, nm, hg, st); break;
                case 4:
                    System.out.print("ID Edit: "); String ide = input.nextLine();
                    System.out.print("Nama Baru: "); String nmb = input.nextLine();
                    System.out.print("Harga Baru: "); double hgb = input.nextDouble(); input.nextLine();
                    gudang.editBarang(ide, nmb, hgb); break;
                case 5: System.out.print("ID Hapus: "); gudang.hapusBarang(input.nextLine()); break;
                case 6: 
                    System.out.print("ID: "); String ids = input.nextLine();
                    System.out.print("Stok Baru: "); int stb = input.nextInt(); input.nextLine();
                    gudang.updateStok(ids, stb); break;
                case 7: return;
            }
        } while (pilih != 7);
    }

    private void kelolaAdmin() {
        System.out.println("\n1. Lihat | 2. Tambah | 3. Edit | 4. Hapus | 5. Back");
        int p = input.nextInt(); input.nextLine();
        if(p==1) lihatSemuaAdmin();
        else if(p==2) tambahAdminBaru();
        else if(p==3) editAdmin();
        else if(p==4) hapusAdmin();
    }

    private void lihatSemuaAdmin() {
        for(User u : loginSystem.getSemuaAdmin()) 
            System.out.println("- " + u.getUsername() + " (Telp: " + u.getNoTelepon() + ")");
    }

    private void tambahAdminBaru() {
        System.out.println("\n[TAMBAH ADMIN]");
        System.out.print("Username: "); String u = input.nextLine();
        System.out.print("Password: "); String pw = input.nextLine();
        System.out.print("No Telp: "); String t = input.nextLine();
        if(loginSystem.tambahAdmin(u, pw, t)) System.out.println("Sukses");
        else System.out.println("Gagal");
    }

    private void editAdmin() {
        System.out.print("Username Target: "); String old = input.nextLine();
        User u = loginSystem.cariAdminByUsername(old);
        if(u==null) { System.out.println("Not Found"); return; }
        
        System.out.print("Username Baru (Enter skip): "); String un = input.nextLine();
        if(un.isEmpty()) un = old;
        System.out.print("Password Baru (Enter skip): "); String pw = input.nextLine();
        if(pw.isEmpty()) pw = u.getPassword();
        System.out.print("No Telp Baru (Enter skip): "); String t = input.nextLine();
        if(t.isEmpty()) t = u.getNoTelepon();
        
        loginSystem.editAdmin(old, un, pw, t);
        System.out.println("Update Sukses");
    }

    private void hapusAdmin() {
        System.out.print("Username Hapus: ");
        if(loginSystem.hapusAdmin(input.nextLine())) System.out.println("Terhapus");
        else System.out.println("Gagal");
    }

    private void kelolaKasir() {
        System.out.println("\n1. Lihat | 2. Tambah | 3. Edit | 4. Hapus | 5. Back");
        int p = input.nextInt(); input.nextLine();
        if(p==1) lihatSemuaKasir();
        else if(p==2) tambahKasirBaru();
        else if(p==3) editKasir();
        else if(p==4) hapusKasir();
    }

    private void lihatSemuaKasir() {
        for(User u : loginSystem.getSemuaKasir()) 
            System.out.println("- " + u.getUsername() + " (Telp: " + u.getNoTelepon() + ")");
    }

    private void tambahKasirBaru() {
        System.out.println("\n[TAMBAH KASIR]");
        System.out.print("Username: "); String u = input.nextLine();
        System.out.print("Password: "); String pw = input.nextLine();
        System.out.print("No Telp: "); String t = input.nextLine();
        if(loginSystem.tambahKasir(u, pw, t)) System.out.println("Sukses");
        else System.out.println("Gagal");
    }

    private void editKasir() {
        System.out.print("Username Target: "); String old = input.nextLine();
        User u = loginSystem.cariKasirByUsername(old);
        if(u==null) { System.out.println("Not Found"); return; }
        
        System.out.print("Username Baru (Enter skip): "); String un = input.nextLine();
        if(un.isEmpty()) un = old;
        System.out.print("Password Baru (Enter skip): "); String pw = input.nextLine();
        if(pw.isEmpty()) pw = u.getPassword();
        System.out.print("No Telp Baru (Enter skip): "); String t = input.nextLine();
        if(t.isEmpty()) t = u.getNoTelepon();
        
        loginSystem.editKasir(old, un, pw, t);
        System.out.println("Update Sukses");
    }

    private void hapusKasir() {
        System.out.print("Username Hapus: ");
        if(loginSystem.hapusKasir(input.nextLine())) System.out.println("Terhapus");
        else System.out.println("Gagal");
    }

    private void kelolaMember() {
        System.out.println("\n1. Lihat | 2. Tambah | 3. Edit | 4. Hapus | 5. Back");
        int p = input.nextInt(); input.nextLine();
        if(p==1) lihatSemuaMember();
        else if(p==2) tambahMemberBaru();
        else if(p==3) editMember();
        else if(p==4) hapusMember();
    }

    private void lihatSemuaMember() {
        for(User u : loginSystem.getSemuaMember()) 
            System.out.println("- " + u.getUsername() + " (Telp: " + u.getNoTelepon() + ")");
    }

    private void tambahMemberBaru() {
        System.out.print("Username: "); String u = input.nextLine();
        System.out.print("No Telp: "); String t = input.nextLine();
        if(loginSystem.register(u, t)) System.out.println("Sukses");
        else System.out.println("Gagal");
    }

    private void editMember() {
        System.out.print("Username Target: "); String old = input.nextLine();
        System.out.print("Username Baru: "); String un = input.nextLine();
        if(un.isEmpty()) un = old;
        System.out.print("No Telp Baru: "); String t = input.nextLine();
        
        loginSystem.editMember(old, un, t);
        System.out.println("Update Sukses");
    }

    private void hapusMember() {
        System.out.print("Username Hapus: ");
        if(loginSystem.hapusMember(input.nextLine())) System.out.println("Terhapus");
        else System.out.println("Gagal");
    }

    private void lihatLaporan() {
        System.out.println("Fitur Laporan...");
        System.out.print("Masukkan Tanggal: ");
        new LaporanPenjualan().generateLaporanHarian(input.nextLine());
    }
}
