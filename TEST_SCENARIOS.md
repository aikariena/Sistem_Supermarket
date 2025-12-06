# Test Scenarios - Sistem Pembayaran Supermarket

## Scenario 1: Admin Login & Tambah Barang
```
Input Sequence:
1. MainMenu → Pilih: 1 (Login)
2. Username: admin
3. No Telepon: 0812345678
4. AdminMenu → Pilih: 1 (Menu Gudang)
5. Pilih: 3 (Tambah barang)
6. ID Barang: B001
7. Nama barang: Beras 5kg
8. Harga: 50000
9. Stok: 100
10. Kembali sampai MainMenu

Expected Output:
✓ Login berhasil
✓ AdminMenu ditampilkan
✓ Barang berhasil ditambahkan dan disimpan
✓ Message: "Sukses: Barang ditambahkan dan disimpan."
```

## Scenario 2: Tambah Kasir
```
Input Sequence:
1. Login sebagai admin (seperti Scenario 1)
2. AdminMenu → Pilih: 5 (Kelola Kasir)
3. Username kasir baru: kasir1
4. Nomor telepon: 0816666777
5. Logout

Expected Output:
✓ Kasir baru berhasil ditambahkan
✓ Message: "✅ Kasir baru berhasil ditambahkan!"
✓ users.txt memiliki entry: kasir1;0816666777;kasir;false
```

## Scenario 3: Kasir Login & Proses Pembayaran
```
Input Sequence:
1. MainMenu → Pilih: 1 (Login)
2. Username: kasir1
3. No Telepon: 0816666777
4. CashierMenu → Pilih: 1 (Menu Pembayaran)
5. Pilih: 1 (Input Barang ke Keranjang)
6. Cari (ID/Nama): B001
7. Tambahkan ke keranjang? y
8. ID Barang: B001
9. Nama barang: Beras 5kg
10. Harga barang: 50000
11. Jumlah: 2
12. Menu Pembayaran → Pilih: 2 (Lihat Keranjang)
13. Menu Pembayaran → Pilih: 4 (Proses Checkout)
14. Pilih Metode Pembayaran: 1 (Tunai)
15. Jumlah uang yang diterima: 150000
16. Konfirmasi pembayaran? y

Expected Output:
✓ Barang ditambahkan ke keranjang
✓ Keranjang menampilkan:
  - 1. Beras 5kg | Harga: Rp 50000 | Qty: 2
  - Subtotal : Rp 100000
✓ Checkout menampilkan:
  - Subtotal: Rp 100000
  - Pajak (10%): Rp 10000
  - TOTAL: Rp 110000
✓ Struk dicetak dengan format rapi
✓ Struk disimpan ke file struk_[ID].txt
✓ Stok Beras berkurang (100 → 98)
✓ Keranjang dikosongkan
✓ Message: "✅ Transaksi berhasil diproses!"
```

## Scenario 4: Registrasi & Shopping sebagai Pengguna
```
Input Sequence:
1. MainMenu → Pilih: 2 (Registrasi)
2. Username baru: budi
3. Nomor Telepon: 0817777888
4. UserMenu → Pilih: 1 (Gudang - Browse Produk)
5. Pilih: 1 (Lihat semua produk)
6. Kembali, Pilih: 3 (Tambah ke keranjang)
7. ID Barang: B001
8. Nama barang: Beras 5kg
9. Harga barang: 50000
10. Jumlah: 1
11. UserMenu → Pilih: 2 (Keranjang Belanja Saya)
12. Pilih: 3 (Kembali)
13. UserMenu → Pilih: 3 (Bayar - Checkout)
14. Pilih Menu: 2 (Lanjut ke Pembayaran)
15. Pilih Metode Pembayaran: 2 (Non-Tunai)
16. Konfirmasi pembayaran? y

Expected Output:
✓ Registrasi berhasil
✓ Auto-login ke UserMenu
✓ Produk ditampilkan dengan benar
✓ Barang ditambahkan ke keranjang
✓ Keranjang menampilkan item dengan benar
✓ Subtotal: Rp 50000
✓ Pajak (10%): Rp 5000
✓ TOTAL: Rp 55000
✓ Struk dicetak untuk pembayaran non-tunai
✓ Stok berkurang
✓ users.txt memiliki entry: budi;0817777888;pengguna;false
```

## Scenario 5: Admin Lihat & Edit Barang
```
Input Sequence:
1. Login sebagai admin
2. AdminMenu → Pilih: 1 (Menu Gudang)
3. Pilih: 1 (Tampilkan semua barang)
4. Pilih: 4 (Edit barang)
5. ID Barang yang akan diedit: B001
6. Nama baru: Beras Premium 5kg
7. Harga baru: 55000
8. Kembali

Expected Output:
✓ Semua barang ditampilkan dengan format table
✓ Barang berhasil diedit
✓ Message: "Sukses: Data barang diperbarui."
✓ database.txt diupdate dengan data baru
```

---

## Validation Checklist

### Data Persistence
- [ ] users.txt dibuat otomatis saat pertama run
- [ ] database.txt bisa dibaca & ditulis dengan baik
- [ ] struk_[ID].txt dibuat untuk setiap transaksi
- [ ] Format CSV dengan semicolon separator

### User Management
- [ ] Admin login dengan default credential
- [ ] Admin bisa tambah admin baru
- [ ] Admin bisa tambah kasir baru
- [ ] Pengguna bisa registrasi mandiri
- [ ] Username tidak boleh duplikat
- [ ] No telepon menjadi identifier unik

### Inventory Management
- [ ] Admin bisa lihat semua barang
- [ ] Admin bisa cari barang (by ID/nama)
- [ ] Admin bisa tambah barang
- [ ] Admin bisa edit barang
- [ ] Admin bisa hapus barang
- [ ] Admin bisa update stok manual

### Payment Flow
- [ ] Kasir bisa input barang ke keranjang
- [ ] Kasir bisa lihat keranjang
- [ ] Kasir bisa edit qty
- [ ] Kasir bisa hapus barang
- [ ] Kasir bisa proses checkout
- [ ] Pajak otomatis 10%
- [ ] Support tunai & non-tunai
- [ ] Hitung kembalian untuk tunai
- [ ] Cetak struk console
- [ ] Simpan struk ke file

### Stock Management
- [ ] Stok terbaca dengan benar
- [ ] Stok berkurang setelah pembayaran
- [ ] Update stok tersimpan di database.txt
- [ ] Warning jika stok tidak cukup

### User Experience
- [ ] Menu navigasi jelas
- [ ] Error message informatif
- [ ] Format output rapi
- [ ] Emoji & box drawing tampil dengan baik

---

## Known Good Input Sequences

### Quick Test (5 menit)
1. java Login.MainMenu
2. Login: admin / 0812345678
3. Admin → Menu Gudang → Lihat semua (empty OK)
4. Kembali sampai MainMenu
5. Logout

### Full Test (30 menit)
1. Jalankan Scenario 1-5 berurutan
2. Cek file yang dibuat
3. Restart app dan verify data persist

### Edge Cases
- Registrasi dengan username sama → error
- Login dengan data salah → error
- Input uang kurang dari total → error
- Keranjang kosong → warning
- Stok habis → warning tapi tetap lanjut
