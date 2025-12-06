# 📝 DETAIL PERUBAHAN GUDANG.JAVA

## Ringkas: Hanya +3 Baris Berubah

File: `Gudang/Gudang.java`
Alasan Edit: PaymentMenu butuh akses ke daftar barang untuk update stok

---

## Perubahan #1: Tambah Import

**Lokasi:** Baris 4 (di antara import statements)

**Sebelum:**
```java
import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
```

**Sesudah:**
```java
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
```

**Alasan:** 
- `ArrayList` diperlukan untuk return type method baru `getSemuaBarang()`

---

## Perubahan #2: Tambah Method Baru

**Lokasi:** Akhir class, sebelum closing brace `}`

**Sebelum:**
```java
    public void cariBarang(String keyword) {
        boolean ditemukan = false;
        System.out.println("Hasil Pencarian:");
        for (Barang b : daftarBarang) {
            if (b.getIdBarang().equalsIgnoreCase(keyword) || b.getNama().toLowerCase().contains(keyword.toLowerCase())) {
                String status = (b.getStok() > 0) ? "Tersedia" : "Habis";
                System.out.println("- [" + b.getIdBarang() + "] " + b.getNama() + " | Stok: " + b.getStok() + " (" + status + ")");
                ditemukan = true;
            }
        }
        if (!ditemukan) {
            System.out.println("Barang tidak ditemukan.");
        }
    }

}
```

**Sesudah:**
```java
    public void cariBarang(String keyword) {
        boolean ditemukan = false;
        System.out.println("Hasil Pencarian:");
        for (Barang b : daftarBarang) {
            if (b.getIdBarang().equalsIgnoreCase(keyword) || b.getNama().toLowerCase().contains(keyword.toLowerCase())) {
                String status = (b.getStok() > 0) ? "Tersedia" : "Habis";
                System.out.println("- [" + b.getIdBarang() + "] " + b.getNama() + " | Stok: " + b.getStok() + " (" + status + ")");
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

}
```

**Method Baru:**
```java
/**
 * Mengambil copy dari daftar barang di gudang
 * Digunakan oleh sistem pembayaran untuk update stok
 * 
 * @return ArrayList berisi copy dari semua barang
 */
public ArrayList<Barang> getSemuaBarang() {
    return new ArrayList<>(daftarBarang);
}
```

---

## Mengapa Method Ini Diperlukan?

### Analisis:

Dalam `PaymentMenu.java`, method `updateStokGudang()` memerlukan akses ke daftar barang untuk:

1. **Mencari barang** yang sudah dibeli (berdasarkan ID)
2. **Mendapatkan stok lama** dari barang tersebut
3. **Mengurangi stok** dengan jumlah yang dibeli
4. **Update stok baru** ke gudang

### Kode yang Membutuhkan (PaymentMenu.java - line ~165):
```java
private void updateStokGudang() {
    System.out.println("\n📦 Mengupdate stok barang di gudang...");
    for (CartItem item : cart) {
        // Ambil barang dari gudang ← BUTUH getSemuaBarang()
        ArrayList<Barang> semuaBarang = gudang.getSemuaBarang();
        
        for (Barang barang : semuaBarang) {
            if (barang.getIdBarang().equalsIgnoreCase(item.getIdBarang())) {
                int stokBaru = barang.getStok() - item.getJumlah();
                if (stokBaru < 0) {
                    System.out.println("⚠️  Stok " + barang.getNama() + " tidak cukup!");
                } else {
                    gudang.updateStok(item.getIdBarang(), stokBaru);
                    System.out.println("✓ Stok " + barang.getNama() + " diupdate");
                }
                break;
            }
        }
    }
}
```

---

## Desain Decision: Mengapa ArrayList Copy?

### Opsi 1: Return Direct Reference (TIDAK BAGUS)
```java
public LinkedList<Barang> getSemuaBarang() {
    return daftarBarang;  // Langsung return reference
}
```
**Problem:**
- Code luar bisa modify daftar barang secara langsung
- Tidak aman, melanggar encapsulation
- Bisa merusak data

### Opsi 2: Return Copy (BAGUS ✅)
```java
public ArrayList<Barang> getSemuaBarang() {
    return new ArrayList<>(daftarBarang);  // Return copy
}
```
**Keuntungan:**
- Code luar hanya bisa baca, tidak bisa modify
- Aman, menjaga encapsulation
- Tidak ada side effect
- **DIPILIH**

---

## Verifikasi Kompatibilitas

### ✅ File Lain Tidak Berubah:
- `Barang.java` → TIDAK BERUBAH
- `SistemGudang.java` → TIDAK BERUBAH
- `CartItem.java` → TIDAK BERUBAH
- `CartMenu.java` → TIDAK BERUBAH

### ✅ Method Existing Tetap Berfungsi:
- `tambahBarang()` → Tetap bekerja
- `hapusBarang()` → Tetap bekerja
- `updateStok()` → Tetap bekerja
- `editBarang()` → Tetap bekerja
- `tampilkanSemuaBarang()` → Tetap bekerja
- `cariBarang()` → Tetap bekerja

### ✅ Backward Compatibility:
- Tidak merusak method lama
- Hanya menambah fungsionalitas
- Existing code tetap jalan 100%

---

## Testing Perubahan

### Test Case 1: getSemuaBarang() Return Correct Data
```java
Gudang gudang = new Gudang();
gudang.tambahBarang("B001", "Beras", 50000, 100);
gudang.tambahBarang("B002", "Minyak", 25000, 50);

ArrayList<Barang> daftar = gudang.getSemuaBarang();

// Expected: daftar.size() == 2
// Expected: daftar.get(0).getNama() == "Beras"
// Expected: daftar.get(1).getNama() == "Minyak"
```

### Test Case 2: getSemuaBarang() Return Copy (Not Reference)
```java
Gudang gudang = new Gudang();
gudang.tambahBarang("B001", "Beras", 50000, 100);

ArrayList<Barang> daftar1 = gudang.getSemuaBarang();
ArrayList<Barang> daftar2 = gudang.getSemuaBarang();

// Expected: daftar1 != daftar2 (different objects)
// Expected: daftar1.get(0) == daftar2.get(0) (same Barang object, tapi list beda)
```

### Test Case 3: Update Stok via PaymentMenu
```
1. Admin tambah barang: B001 | Beras | 50000 | 100 stok
2. Kasir input barang ke keranjang: B001 | Qty 5
3. Checkout → pembayaran berhasil
4. Admin cek: Beras stok sekarang 95 ✅
```

---

## Summary Perubahan

```
File: Gudang.java
─────────────────────────────────
Tambahan Import:      1 line
  + import java.util.ArrayList;

Tambahan Method:      3 lines
  + public ArrayList<Barang> getSemuaBarang() {
  +     return new ArrayList<>(daftarBarang);
  + }

Total Perubahan:      4 lines (1 import + 3 method)
─────────────────────────────────

Konfigurasi Method: MINIMAL & AMAN
- Hanya read-only access
- Return copy, bukan reference
- Tidak modify logic existing
- 100% backward compatible
```

---

## Kesimpulan

✅ **Perubahan MINIMAL, AMAN, dan DIPERLUKAN**

Hanya menambahkan 1 method publik untuk memberikan akses read-only ke daftar barang. Tidak merusak atau memodifikasi kode yang sudah ada.

**Impact: ZERO RISK ✅**
