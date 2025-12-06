import java.util.Scanner;

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
                    String idBarang = scanner.nextLine();
                    System.out.print("Masukkan Nama Barang: ");
                    String nama = scanner.nextLine();
                    System.out.print("Masukkan Harga: ");
                    double harga = scanner.nextDouble();
                    System.out.print("Masukkan Jumlah Stok: ");
                    int stok = scanner.nextInt();
                    gudang.tambahBarang(idBarang, nama, harga, stok);
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
