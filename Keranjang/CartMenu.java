import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CartMenu {

    private ArrayList<CartItem> cart = new ArrayList<>();
    private Scanner input = new Scanner(System.in);

    private final String FILE_KERANJANG = "keranjang.txt";

    public static void main(String[] args) {
        CartMenu menu = new CartMenu();
        menu.loadKeranjang();
        menu.ShowMenu();
        menu.saveKeranjang();
    }

    public void ShowMenu(){
        int pilihMenu;

        do{
            System.out.println("\n === KERANJANG SUPERMARKET === ");
            System.out.println("1. Tambah barang ke keranjang");
            System.out.println("2. Update jumlah barang");
            System.out.println("3. Hapus barang dari keranjang");
            System.out.println("4. Lihat isi keranjang");
            System.out.println("5. Hitung subtotal");
            System.out.println("6. Kembali");
            System.out.print("Pilih Menu : ");
            pilihMenu = input.nextInt();
            input.nextLine();

            switch (pilihMenu){
                case 1 : tambahKeranjang(); break;
                case 2 : updateJumlah(); break;
                case 3 : hapusKeranjang(); break;
                case 4 : tampilKeranjang(); break;
                case 5 : hitungSubtotal(); break;
            }

        } while (pilihMenu != 6);

        saveKeranjang();
    }

    private void tambahKeranjang() {
        System.out.print("ID Barang : ");
        String idBarang = input.nextLine();

        System.out.print("Nama barang : ");
        String nama = input.nextLine();

        System.out.print("Harga barang : ");
        int harga = input.nextInt();

        System.out.print("Jumlah : ");
        int jumlah = input.nextInt();

        cart.add(new CartItem(idBarang, nama, harga, jumlah));
        System.out.println("Barang '" + nama + "' ditambahkan ke keranjang!");
        saveKeranjang();
    }

    private void updateJumlah() {
        if(cart.isEmpty()){
            System.out.println("Keranjang masih kosong.");
            return;
        }

        tampilKeranjang();

        System.out.print("Pilih nomor barang : ");
        int idx = input.nextInt() - 1;

        if (idx >= 0 && idx < cart.size()) {
            System.out.print("Jumlah baru : ");
            int jumlah = input.nextInt();
            cart.get(idx).setJumlah(jumlah);
            System.out.println("Jumlah berhasil diperbarui!");
        } else {
            System.out.println("Nomor tidak valid!");
        }
    }

    private void hapusKeranjang() {
        if(cart.isEmpty()){
            System.out.println("Keranjang masih kosong.");
            return;
        }
        
        tampilKeranjang();

        System.out.print("Pilih nomor barang untuk dihapus : ");
        int idx = input.nextInt() - 1;

        if (idx >= 0 && idx < cart.size()) {
            cart.remove(idx);
            System.out.println("Barang dihapus dari keranjang!");
        } else {
            System.out.println("Nomor tidak valid!");
        }
    }

    private void tampilKeranjang() {
        System.out.println("\n=== ISI KERANJANG ===");
        if (cart.isEmpty()) {
            System.out.println("Keranjang kosong.");
            return;
        }

        for (int i = 0; i < cart.size(); i++) {
            CartItem item = cart.get(i);
            System.out.println((i + 1) + ". " + item.getNama() +
                    " | Harga : " + item.getHarga() +
                    " | Jumlah : " + item.getJumlah() +
                    " | ID barang : " + item.getIdBarang());
        }
    }

    private void hitungSubtotal() {
        int total = 0;

        for (CartItem item : cart) {
            total += item.getHarga() * item.getJumlah();
        }

        System.out.println("Subtotal : " + total);
    }
    private void saveKeranjang() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("keranjang.txt"))) {
        for (CartItem item : cart) {
            bw.write(item.toString());
            bw.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    private void loadKeranjang() {
        try (BufferedReader br = new BufferedReader(new FileReader("keranjang.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            CartItem item = CartItem.fromString(line);
            if (item != null) cart.add(item);
        }
    } catch (IOException e) {
        System.out.println("keranjang.txt belum ada.");
    }
    }
}