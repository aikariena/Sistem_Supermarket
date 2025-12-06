# Dokumentasi Sistem Supermarket - Fitur Pembayaran & Manajemen Role

## 📋 Ringkasan Perubahan

Sistem telah diupdate dengan fitur pembayaran lengkap dan sistem login dengan role-based access untuk Admin, Kasir, dan Pengguna.

---

## 🔧 File yang Dibuat (BARU)

### Login System
1. **`Login/User.java`** - Model data untuk user dengan atribut: username, noTelepon, role, isDefault
2. **`Login/LoginSystem.java`** - Sistem autentikasi dan manajemen user (login, registrasi, tambah admin/kasir)
3. **`Login/MainMenu.java`** - Entry point aplikasi dengan menu utama
4. **`Login/AdminMenu.java`** - Menu admin dengan akses ke gudang, keranjang, laporan, dan manajemen pengguna
5. **`Login/CashierMenu.java`** - Menu kasir dengan akses ke menu pembayaran
6. **`Login/UserMenu.java`** - Menu pengguna dengan browse produk, keranjang, dan checkout

### Pembayaran System
1. **`Pembayaran/Transaction.java`** - Model transaksi dengan perhitungan pajak otomatis
2. **`Pembayaran/TransactionItem.java`** - Model item dalam transaksi
3. **`Pembayaran/Receipt.java`** - Pembuatan dan pencetakan struk pembayaran (juga disimpan ke file)
4. **`Pembayaran/PaymentMenu.java`** - Menu pembayaran lengkap (checkout, konfirmasi, update stok)

---

## ✏️ File yang Dimodifikasi (MINIMAL)

### `Gudang/Gudang.java`
**Perubahan:**
- Tambah import: `import java.util.ArrayList;`
- Tambah method public:
  ```java
  public ArrayList<Barang> getSemuaBarang() {
      return new ArrayList<>(daftarBarang);
  }
  ```
**Alasan:** PaymentMenu memerlukan akses ke semua barang untuk update stok saat checkout

---

## 🎯 Fitur Utama

### 1. **Sistem Login dengan 3 Role**
- **Admin**
  - Akun default: `username=admin, noTelepon=0812345678`
  - Dapat login tanpa verifikasi
  - Dapat menambah admin baru dan kasir baru
  - Akses ke: Gudang, Keranjang, Laporan Penjualan

- **Kasir**
  - Hanya bisa dibuat oleh admin
  - Akses ke: Menu Pembayaran (Keranjang + Checkout)
  - Input barang, lihat keranjang, hapus barang, proses pembayaran

- **Pengguna**
  - Bisa registrasi sendiri
  - Akses ke: Browse Produk, Keranjang Saya, Checkout
  - Self-service shopping

### 2. **Fitur Pembayaran Lengkap**
- ✅ Ambil data barang dari keranjang
- ✅ Hitung subtotal + pajak (default 10%)
- ✅ Pilih metode pembayaran (tunai/non-tunai)
- ✅ Konfirmasi pembayaran manual
- ✅ Cetak struk ke console
- ✅ Simpan struk ke file (`struk_[ID_TRANSAKSI].txt`)
- ✅ Update stok barang di gudang otomatis
- ✅ Generate ID transaksi unik

### 3. **Alur Pembayaran**
```
1. Pilih barang → Tambah ke keranjang
2. Review keranjang → Opsi edit/hapus
3. Lanjut checkout → Pilih metode pembayaran
4. Input jumlah uang (jika tunai) → Konfirmasi pembayaran
5. Sistem cetak struk → Update stok → Kosongkan keranjang
```

---

## 🗂️ Struktur Project Baru

```
Sistem_Supermarket/
├── Login/
│   ├── User.java (BARU)
│   ├── LoginSystem.java (BARU)
│   ├── MainMenu.java (BARU)
│   ├── AdminMenu.java (BARU)
│   ├── CashierMenu.java (BARU)
│   └── UserMenu.java (BARU)
├── Pembayaran/
│   ├── Transaction.java (BARU)
│   ├── TransactionItem.java (BARU)
│   ├── Receipt.java (BARU)
│   └── PaymentMenu.java (BARU)
├── Gudang/
│   ├── Barang.java (TIDAK BERUBAH)
│   ├── Gudang.java (DIMODIFIKASI: +1 method)
│   ├── SistemGudang.java (TIDAK BERUBAH)
│   └── database.txt
├── Keranjang/
│   ├── CartItem.java (TIDAK BERUBAH)
│   └── CartMenu.java (TIDAK BERUBAH)
└── README.md
```

---

## 🚀 Cara Menjalankan

### Entry Point
```
java Login.MainMenu
```

### User Credentials

#### Admin Default (Sudah Ada)
- Username: `admin`
- No. Telepon: `0812345678`

#### Membuat Kasir Baru
1. Login sebagai Admin
2. Pilih menu "5. Kelola Kasir (Tambah Kasir)"
3. Input username dan nomor telepon

#### Registrasi Pengguna Baru
1. Di MainMenu pilih "2. Registrasi (Pengguna Baru)"
2. Input username dan nomor telepon
3. Auto-login ke UserMenu

---

## 📝 Catatan Penting

1. **File Database**
   - `Gudang/database.txt` - Menyimpan data barang
   - `Login/users.txt` - Menyimpan data user (dibuat otomatis)
   - `struk_[ID].txt` - Menyimpan setiap struk pembayaran

2. **Pajak**
   - Default pajak: 10%
   - Dapat diubah di `PaymentMenu.java` (variable `PAJAK`)

3. **Keamanan**
   - Password tidak diimplementasi, hanya username + nomor telepon
   - Admin default hanya bisa login sekali (karena no telepon unik)

4. **Update Stok**
   - Stok diupdate otomatis setelah pembayaran berhasil
   - Jika stok tidak cukup, akan menampilkan warning tapi tetap lanjut

---

## 💡 Fitur Tambahan yang Bisa Dikembangkan

- [ ] Implementasi password
- [ ] Laporan penjualan dengan analitik
- [ ] Riwayat transaksi per user
- [ ] Diskon dan promo
- [ ] Integrasi pembayaran real-time
- [ ] Backup database otomatis
- [ ] Export laporan ke Excel

