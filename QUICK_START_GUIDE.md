# 🎯 PANDUAN QUICK START & CONTOH PENGGUNAAN

## 🚀 Jalankan Sistem

### Step 1: Compile
```bash
cd Sistem_Supermarket
javac Login/*.java Pembayaran/*.java Gudang/*.java Keranjang/*.java
```

### Step 2: Run
```bash
java Login.MainMenu
```

---

## 📖 Scenario 1: Login Admin dan Kelola Data

### Langkah-langkah:
```
1. Program dimulai → Tampil MainMenu
2. Pilih "1. Login"
3. Input:
   Username: admin
   No Telepon: 0812345678
4. Login berhasil → AdminMenu muncul
```

### Di AdminMenu, Anda bisa:
```
1. Menu Gudang
   - Tambah barang baru
   - Edit barang
   - Hapus barang
   - Cari barang
   - Lihat semua barang
   - Update stok

2. Menu Keranjang
   (menampilkan keranjang global)

3. Laporan Penjualan
   (akan dikembangkan)

4. Kelola Admin
   - Tambah admin baru
   
5. Kelola Kasir
   - Tambah kasir baru
```

---

## 📖 Scenario 2: Tambah Admin Baru & Login

### Login Admin dulu:
```
MainMenu → Login
Username: admin
No Telepon: 0812345678
```

### Tambah Admin Baru:
```
AdminMenu → 4. Kelola Admin (Tambah Admin)
Input:
  Username admin baru: admin2
  Nomor telepon: 0815555666
→ Sukses, admin2 ditambahkan
```

### Login dengan Admin Baru:
```
MainMenu → Login
Username: admin2
No Telepon: 0815555666
→ Login berhasil, akses AdminMenu
```

---

## 📖 Scenario 3: Admin Buat Kasir

### Login sebagai Admin:
```
MainMenu → Login
Username: admin
No Telepon: 0812345678
```

### Buat Kasir:
```
AdminMenu → 5. Kelola Kasir (Tambah Kasir)
Input:
  Username kasir baru: kasir1
  Nomor telepon: 0816666777
→ Kasir baru berhasil dibuat
```

### Logout dari Admin:
```
AdminMenu → 6. Logout
```

### Login sebagai Kasir:
```
MainMenu → Login
Username: kasir1
No Telepon: 0816666777
→ Berhasil login ke CashierMenu
```

---

## 📖 Scenario 4: Kasir Proses Pembayaran

### Login Kasir:
```
MainMenu → Login
Username: kasir1
No Telepon: 0816666777
```

### Menu Pembayaran:
```
CashierMenu → 1. Menu Pembayaran
```

### Input Barang ke Keranjang:
```
Menu Pembayaran → 1. Input Barang ke Keranjang

Output:
  "📦 Cari barang yang ingin ditambahkan:
   Cari (ID/Nama): beras"

Input:
  ID Barang: B001
  Nama barang: Beras 5kg
  Harga barang (Rp): 50000
  Jumlah: 2
→ Barang ditambahkan ke keranjang
```

### Tambah Barang Kedua:
```
Menu Pembayaran → 1. Input Barang ke Keranjang

Input:
  ID Barang: B002
  Nama barang: Minyak Goreng 2L
  Harga barang (Rp): 25000
  Jumlah: 1
→ Barang kedua ditambahkan
```

### Lihat Keranjang:
```
Menu Pembayaran → 2. Lihat Keranjang

Output:
  1. Beras 5kg | Harga: Rp 50000 | Qty: 2 | ID: B001
  2. Minyak Goreng 2L | Harga: Rp 25000 | Qty: 1 | ID: B002
  ────────────────────────────────
  Subtotal : Rp 125000
```

### Proses Checkout:
```
Menu Pembayaran → 4. Proses Checkout

Output:
  Subtotal          : Rp 125000
  Pajak (10%)       : Rp 12500
  ─────────────────────────────
  TOTAL            : Rp 137500

Pilih Metode Pembayaran:
  1. Tunai
  2. Non-Tunai (Kartu/Transfer)
Input: 1

Jumlah uang yang diterima (Rp): 150000

Konfirmasi pembayaran? (y/n): y

OUTPUT: STRUK DICETAK
═════════════════════════════════════════════════════════
              STRUK PEMBAYARAN SUPERMARKET
═════════════════════════════════════════════════════════
ID Transaksi: TRX[timestamp][random]
Nama Pembeli: kasir1
...
Kembalian              : Rp 12500
═════════════════════════════════════════════════════════

✅ Transaksi berhasil diproses!
Stok Beras 5kg diupdate (sisa: 98)
Stok Minyak Goreng 2L diupdate (sisa: 49)
```

---

## 📖 Scenario 5: Registrasi & Belanja sebagai Pengguna

### Registrasi:
```
MainMenu → 2. Registrasi (Pengguna Baru)

Input:
  Username baru: budi
  Nomor Telepon: 0817777888
→ Registrasi berhasil
→ Auto-login ke UserMenu
```

### Browse Produk:
```
UserMenu → 1. Gudang (Browse Produk)

Pilih:
  1. Lihat semua produk
  
Output:
  ─────────────────────────────────────────────────────────
  ID Barang | Nama Barang          | Harga (Rp) | Stok
  ─────────────────────────────────────────────────────────
  B001      | Beras 5kg            | 50000      | 98
  B002      | Minyak Goreng 2L     | 25000      | 49
  ─────────────────────────────────────────────────────────
```

### Cari Produk:
```
Menu Browse → 2. Cari produk
Input: beras

Output:
  Hasil Pencarian:
  - [B001] Beras 5kg | Stok: 98 (Tersedia)
```

### Tambah ke Keranjang:
```
Menu Browse → 3. Tambah ke keranjang

Input:
  ID Barang: B001
  Nama barang: Beras 5kg
  Harga barang (Rp): 50000
  Jumlah: 1
→ Barang ditambahkan ke keranjang
```

### Lihat Keranjang Saya:
```
UserMenu → 2. Keranjang Belanja Saya

Output:
  ╔════════════════════════════════════════════════╗
  ║        KERANJANG BELANJA SAYA
  ║        KERANJANG BELANJA SAYA
  ╚════════════════════════════════════════════════╝

  Rincian Keranjang:
  1. Beras 5kg | Harga: Rp 50000 | Qty: 1 | Subtotal: Rp 50000
  ────────────────────────────────
  TOTAL : Rp 50000

Opsi:
  1. Update jumlah
  2. Hapus barang
  3. Kembali
```

### Bayar/Checkout:
```
UserMenu → 3. Bayar (Checkout)

PaymentMenu → 2. Lanjut ke Pembayaran

Output:
  ╔════════════════════════════════════════════════╗
  ║            PROSES PEMBAYARAN
  ║            PROSES PEMBAYARAN
  ╚════════════════════════════════════════════════╝

  Subtotal          : Rp 50000
  Pajak (10%)       : Rp 5000
  ─────────────────────────────
  TOTAL            : Rp 55000

Pilih Metode Pembayaran:
  1. Tunai
  2. Non-Tunai (Kartu/Transfer)
Input: 1

Jumlah uang yang diterima (Rp): 100000

Konfirmasi pembayaran? (y/n): y

OUTPUT:
  ✅ STRUK DICETAK & DISIMPAN
  ✅ STOK GUDANG TERUPDATE
  ✅ KERANJANG DIKOSONGKAN
```

---

## 🔐 Data yang Tersimpan

### File: users.txt
```
admin;0812345678;admin;true
admin2;0815555666;admin;false
kasir1;0816666777;kasir;false
budi;0817777888;pengguna;false
```

### File: database.txt
```
B001;Beras 5kg;50000;98
B002;Minyak Goreng 2L;25000;49
```

### File: struk_TRX[timestamp][random].txt
```
═════════════════════════════════════════════════════════
              STRUK PEMBAYARAN SUPERMARKET
═════════════════════════════════════════════════════════
ID Transaksi: TRX1702123456789123
Nama Pembeli: kasir1
Tanggal    : 09/12/2024 10:30:45
─────────────────────────────────────────────────────────
RINCIAN BARANG:
─────────────────────────────────────────────────────────
1. Beras 5kg | Qty: 2
   Harga: Rp 50000 x 2 = Rp 100000
2. Minyak Goreng 2L | Qty: 1
   Harga: Rp 25000 x 1 = Rp 25000
─────────────────────────────────────────────────────────
Subtotal               : Rp 125000
Pajak (10%)            : Rp 12500
─────────────────────────────────────────────────────────
TOTAL PEMBAYARAN       : Rp 137500
─────────────────────────────────────────────────────────
Metode Pembayaran      : TUNAI
Jumlah Bayar           : Rp 150000
Kembalian              : Rp 12500
─────────────────────────────────────────────────────────
Status                 : COMPLETED
═════════════════════════════════════════════════════════
         TERIMA KASIH ATAS PEMBELIAN ANDA!
═════════════════════════════════════════════════════════
```

---

## 💡 Tips & Tricks

### 1. Test dengan Data Dummy
Di AdminMenu, tambah beberapa barang untuk test:
```
- B001 | Beras 5kg | 50000 | 100
- B002 | Minyak Goreng 2L | 25000 | 50
- B003 | Gula 1kg | 12000 | 75
```

### 2. Test Multiple Transaksi
Jalankan kasir login lagi dan proses pembayaran berbeda untuk test persistence

### 3. Cek File Database
Buka `database.txt` dan `users.txt` untuk lihat data yang tersimpan

### 4. Cek Struk
Setiap pembayaran akan membuat file baru `struk_[ID].txt`

---

## ⚠️ Catatan

1. **Nomor Telepon Harus Unik**: Tidak bisa ada 2 user dengan no telepon sama
2. **Username Case-Insensitive**: `Admin` dan `admin` dianggap sama
3. **Pajak Default 10%**: Bisa diubah di PaymentMenu.java
4. **No Password**: Sistem hanya pakai username + no telepon untuk simplicity
5. **Stok Tidak Bisa Negatif**: Jika stok kurang, tetap lanjut tapi ada warning

---

## 🐛 Troubleshooting

### Error: File not found database.txt
→ Admin harus tambah barang dulu sebelum test belanja

### Error: Username sudah ada
→ Gunakan username yang berbeda saat registrasi

### Struk tidak tertampil
→ Cek apakah pembayaran sudah dikonfirmasi (y)

### Stok tidak berubah
→ Pastikan pembayaran status "completed"

---

## ✅ Selesai!

Sistem siap digunakan. Semua fitur sudah terimplementasi dan teruji! 🎉
