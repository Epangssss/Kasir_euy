/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaksi;
//import static gui.Transaksi.harga;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
//NEW

import java.awt.event.KeyEvent;

import transaksi.stok_barang;
import java.util.prefs.Preferences;

import java.util.Locale;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import login_new.sign_in;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import user.Menu_User;

public class formTransaksi extends javax.swing.JFrame {
    DefaultTableModel table = new DefaultTableModel();
    
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    
    
   public formTransaksi() {
    initComponents();
    
    
    
     txt_karyawan.setText(sign_in.getKasirName());
    setLocationRelativeTo(null);
    koneksi.getKoneksi();
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    setResizable(false);
    
    // Menambahkan kolom ke tabel dengan model yang sama
        tb_keranjang.setModel(table);
        
     table.addColumn("no_transaksi");
   table.addColumn("Kode Barang");
    table.addColumn("Nama Barang");
    table.addColumn("Harga");
    table.addColumn("Jumlah");
    table.addColumn("Total Harga");
    table.addColumn("Kategori");
    table.addColumn("catatan");
    table.addColumn("Nama");
    
    
    // Mengatur model tabel ke tb_keranjang

  //   displayDateTime();
    //    startTimer(); 
    // Menampilkan data setelah menambahkan kolom
    tampilData();
    // clearTransaksi();
    // Menghitung total harga dan menampilkan tanggal
  totalnya();
    tanggal();
  //  setTanggal();
   no_transaksi();
}
   
   
   
     
  



    public String getKodeBarang2() {
        return this.txt_kodebarang2.getText();
    }

    public void setKodeBarang2(String kode) {
        this.txt_kodebarang2.setText(kode);
    }
    
    
     public String getKategori() {
        return this.txt_kategori.getText();
    }

    public void setKategori(String kategori) {
        this.txt_kategori.setText(kategori);
    }

    public String getNamaBarang() {
        return this.txt_namabarang2.getText();
    }

    public void setNamaBarang(String namabarang) {
        this.txt_namabarang2.setText(namabarang);
    }

    public String getHarga() {
        return this.txt_harga2.getText();
    }

    public void setHarga(String harga) {
        this.txt_harga2.setText(harga);
    }

      
     
     
     
    

   
    public void tanggal(){
        Date now = new Date();  
        tgl_transaksi.setDate(now);    
    }
    

private void tampilData() {
        int row = tb_keranjang.getRowCount();
        for (int a = 0; a < row; a++) {
            table.removeRow(0);
          
        }
        try {
            // Menghapus semua baris dalam tabel
            table.setRowCount(0);

                 String query = "SELECT * FROM tb_keranjang ";
                String procedures = "CALL total_harga_transaksi()";
            Connection connect = koneksi.getKoneksi();
            Statement sttmnt = connect.createStatement();
            ResultSet rslt = sttmnt.executeQuery(query);

            while (rslt.next()) {
                // Mendapatkan data dari ResultSet
                String no_transaksi = rslt.getString("no_transaksi");
                String kode = rslt.getString("kode_barang");
                String nama = rslt.getString("nama_barang");
                int harga = rslt.getInt("harga"); // Ubah menjadi tipe data int jika harga adalah angka
                int jumlah = rslt.getInt("jumlah"); // Ubah menjadi tipe data int jika jumlah adalah angka

                int total = rslt.getInt("total_harga"); // Ubah menjadi tipe data int jika total_harga adalah angka
                String kategori = rslt.getString("kategori");
                String catatan = rslt.getString("catatan");
                 String namacs = rslt.getString("namacs");

          //masukan semua data kedalam array
            Object[] data = { no_transaksi, kode, nama, harga, jumlah, total, kategori, catatan, namacs };
                //menambahakan baris sesuai dengan data yang tersimpan diarray
                table.addRow(data);
            }
                //mengeset nilai yang ditampung agar muncul di table
                tb_keranjang.setModel(table);
                
            
        }catch(Exception e){
            System.out.println(e);
        }
       
    }

  
private void clear(){
    txt_kodebarang2.setText(null);
    txt_namabarang2.setText(null);
    txt_harga2.setText(null);
    txt_jumlah2.setText(null);
    txt_totalharga.setText(null);
    txt_catatan.setText(null);
}


private void keranjang() {
    String kode = txt_kodebarang2.getText();
    String nama = txt_namabarang2.getText();
    String harga = txt_harga2.getText();
    String jumlah = txt_jumlah2.getText();
    String total = txt_totalharga.getText();
    String kategori = txt_kategori.getText();
    String catatan = txt_catatan.getText();
    String no_transaksi = txt_kategori1.getText();
    String namacs = txt_namacs.getText();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String tanggal = dateFormat.format(tgl_transaksi.getDate());
    totalnya();

    Connection connect = koneksi.getKoneksi();
  String queryTambahKeranjang = "INSERT INTO tb_keranjang (no_transaksi, kode_barang, nama_barang, kategori, harga, jumlah, total_harga, catatan, tgl_transaksi, namacs) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    String queryKurangiStok = "UPDATE tb_databarang SET stok = stok - ? WHERE kode_barang = ?";
    try {
  
        // Tambahkan item ke keranjang
        PreparedStatement psKeranjang = connect.prepareStatement(queryTambahKeranjang);
        psKeranjang.setString(1, no_transaksi);
        psKeranjang.setString(2, kode);
        psKeranjang.setString(3, nama);
        psKeranjang.setString(4, kategori);
        psKeranjang.setString(5, harga);
        psKeranjang.setString(6, jumlah);
        psKeranjang.setString(7, total);
        psKeranjang.setString(8, catatan);
        psKeranjang.setString(9, tanggal);
         psKeranjang.setString(10, namacs);
        
      
        
        int rowsAffected = psKeranjang.executeUpdate();

        if (rowsAffected > 0) {
            // Kurangi stok barang
            PreparedStatement psStok = connect.prepareStatement(queryKurangiStok);
            psStok.setString(1, jumlah); // Kurangi stok sebanyak jumlah yang ditambahkan ke keranjang
            psStok.setString(2, kode);
            psStok.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data Masuk Ke Keranjang dan Disimpan ke Database");

            DefaultTableModel model = (DefaultTableModel) tb_keranjang.getModel();
            model.addRow(new Object[] { no_transaksi, kode, nama, harga, jumlah, total, kategori, catatan, namacs });

            // Perbarui total harga keseluruhan di txt_totalharga2
            int totalHarga = Integer.parseInt(txt_totalharga2.getText());
            totalHarga += Integer.parseInt(total);
            txt_totalharga2.setText(Integer.toString(totalHarga));
            
             kurangiStokBarang(kode, jumlah);
            
       
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data ke keranjang");
        }
    } catch (SQLException ex) {
        Logger.getLogger(formTransaksi.class.getName()).log(Level.SEVERE, null, ex);
    }
}

private void kurangiStokBarang(String itemCode, String quantity) {
    try {
        Connection connection = koneksi.getKoneksi();
        Statement statement = connection.createStatement();

        String query = "UPDATE tb_databarang SET stok = stok - " + quantity + " WHERE kode_barang = '" + itemCode + "'";
        statement.executeUpdate(query);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



private int grandTotal = 0; // Deklarasikan variabel grandTotal di luar metode totalnya()
 private void totalnya() {
    String querySelect = "SELECT COALESCE(SUM(total_harga), 0) FROM tb_keranjang";

    try {
        Connection connect = koneksi.getKoneksi();
        Statement sttmnt = connect.createStatement();

        // Menghitung total harga dari tabel keranjang
        ResultSet rslt = sttmnt.executeQuery(querySelect);

        if (rslt.next()) {
            int totalHarga = rslt.getInt(1);
            grandTotal = totalHarga; // Simpan nilai totalHarga ke dalam grandTotal
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
            String formattedTotalHarga = numberFormat.format(totalHarga);
            String grandTotalWithoutComma = formattedTotalHarga.replaceAll(",", "");
            txt_totalharga2.setText(grandTotalWithoutComma);
        }

        // Menutup hasil, pernyataan, dan koneksi
        rslt.close();
        sttmnt.close();
        connect.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


private void total() {
    String harga = txt_harga2.getText();
    String jumlah = txt_jumlah2.getText();

    try {
        int hargaa = Integer.parseInt(harga);
        int jumlahh = Integer.parseInt(jumlah);
        int total = hargaa * jumlahh;
        txt_totalharga.setText(Integer.toString(total));
    } catch (NumberFormatException e) {
        txt_totalharga.setText("");
        JOptionPane.showMessageDialog(null, "Only Number");
        txt_jumlah2.setText(null);
    }
}
    
    private void reset(){
        txt_uang.setText(null);
        
    }   
    


    
private void hapusData() {
    // Get the selected row index from the table
    int selectedRow = tb_keranjang.getSelectedRow();

    // Check if a row is selected
    if (selectedRow == -1) {
        // No row is selected, show an error message
        JOptionPane.showMessageDialog(null, "Silakan pilih baris untuk dihapus!");
    } else {
        // Confirm the deletion
        int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Retrieve the deleted item's code from the table
            String deletedItemCode = tb_keranjang.getValueAt(selectedRow, 1).toString();
            int deletedQuantity = Integer.parseInt(tb_keranjang.getValueAt(selectedRow, 4).toString());

            // Delete the selected row from the table model
            DefaultTableModel model = (DefaultTableModel) tb_keranjang.getModel();
            model.removeRow(selectedRow);

            // Show a success message
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus dari keranjang!");

            // Delete the item from the database
            hapusItemDariDatabase(deletedItemCode);
            
            // Restore the item's stock in the product table
            restoreItemStock(deletedItemCode, deletedQuantity);
        }
    }
}

private void hapusItemDariDatabase(String itemCode) {
    try {
        Connection connection = koneksi.getKoneksi();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM tb_keranjang WHERE kode_barang = ?");
        statement.setString(1, itemCode);
        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void restoreItemStock(String itemCode, int quantity) {
    try {
        Connection connection = koneksi.getKoneksi();
        Statement statement = connection.createStatement();

        // Retrieve the current stock of the item
        String query = "SELECT stok FROM tb_databarang WHERE kode_barang = '" + itemCode + "'";
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            int currentStock = resultSet.getInt("stok");

            // Increment the stock by the deleted quantity
            int updatedStock = currentStock + quantity;

            // Update the stock in the product table
            String updateQuery = "UPDATE tb_databarang SET stok = " + updatedStock + " WHERE kode_barang = '" + itemCode + "'";
            statement.executeUpdate(updateQuery);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


   

private void editData() {
    // Get the selected row index from the table
    int selectedRow = tb_keranjang.getSelectedRow();

    // Check if a row is selected
    if (selectedRow == -1) {
        // No row is selected, show an error message
        JOptionPane.showMessageDialog(null, "Silakan pilih baris untuk diedit!");
    } else {
        // Get the values from the selected row
        String kode = tb_keranjang.getValueAt(selectedRow, 0).toString();
        String nama = tb_keranjang.getValueAt(selectedRow, 1).toString();
        String harga = tb_keranjang.getValueAt(selectedRow, 2).toString();
        String stok = tb_keranjang.getValueAt(selectedRow, 3).toString();
        String kategori = tb_keranjang.getValueAt(selectedRow, 4).toString();
        
        // Prompt the user to enter the updated data
        String newNama = JOptionPane.showInputDialog(null, "Masukkan nama baru:", nama);
        String newHarga = JOptionPane.showInputDialog(null, "Masukkan harga baru:", harga);
        String newStok = JOptionPane.showInputDialog(null, "Masukkan stok baru:", stok);
        String newKategori = JOptionPane.showInputDialog(null, "Masukkan kategori baru:", kategori);

        // Update the values in the table model
        tb_keranjang.setValueAt(newNama, selectedRow, 1);
        tb_keranjang.setValueAt(newHarga, selectedRow, 2);
        tb_keranjang.setValueAt(newStok, selectedRow, 3);
        tb_keranjang.setValueAt(newKategori, selectedRow, 4);

        // Show a success message
        JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
    }
}
      
private void kembalian() {
    String total = txt_totalharga2.getText();
    String uang = txt_uang.getText().trim(); // Trim to remove leading/trailing spaces

    try {
        // Check if input is empty
        if (uang.isEmpty()) {
            throw new NumberFormatException();
        }

        // Validasi apakah input hanya berupa angka
        if (!uang.matches("\\d+")) {
            throw new NumberFormatException();
        }

        int totals = Integer.parseInt(total);
        int uangs = Integer.parseInt(uang);

        if (uangs < totals) {
            JOptionPane.showMessageDialog(null, "Jumlah uang yang diberikan kurang!");
        } else {
            int kembali = uangs - totals;
            String fix = formatRupiah(kembali);
            txt_kembalian.setText(fix);

            JOptionPane.showMessageDialog(null, "Transaksi Berhasil!");
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Masukkan jumlah uang dengan angka yang valid!");
        txt_uang.setText(""); // Mengosongkan nilai input yang salah
        txt_uang.requestFocus(); // Fokuskan kembali ke elemen input yang salah
    }
}

private String formatRupiah(int value) {
    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    return formatter.format(value);
}


private void pembayaran() {
    String total = txt_totalharga2.getText();
    String uang = txt_uang.getText().trim(); // Trim to remove leading/trailing spaces

    try {
        // Check if input is empty
        if (uang.isEmpty()) {
            throw new NumberFormatException();
        }

        // Validasi apakah input hanya berupa angka
        if (!uang.matches("\\d+")) {
            throw new NumberFormatException();
        }

        int totals = Integer.parseInt(total);
        int uangs = Integer.parseInt(uang);

        if (uangs < totals) {
            JOptionPane.showMessageDialog(null, "Jumlah uang yang diberikan kurang!");
            // Jangan lanjutkan proses pembayaran, langsung keluar dari method
            return;
        } else {
            try {
                Connection conn = koneksi.getKoneksi();
                
                // Buat query untuk insert data dari tb_keranjang ke transaksi
                String queryInsertTransaksi = "INSERT INTO transaksi " +
                                             "(nomor_transaksi, tgl_transaksi, kode_barang, nama_barang, kategori, harga, jumlah_barang, total_harga, catatan, namacs) " +
                                             "SELECT no_transaksi, tgl_transaksi, kode_barang, nama_barang, kategori, harga, jumlah, total_harga, catatan, namacs " +
                                             "FROM tb_keranjang";
                PreparedStatement psInsertTransaksi = conn.prepareStatement(queryInsertTransaksi);
                
                // Eksekusi query
                int rowsAffected = psInsertTransaksi.executeUpdate();
                
                if (rowsAffected > 0) {
                    // Tampilkan pesan sukses
                    JOptionPane.showMessageDialog(null, "Pembayaran berhasil! Silahkan Reset Keranjang ");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal melakukan pembayaran.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(formTransaksi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Masukkan jumlah uang dengan angka yang valid!");
        txt_uang.setText(""); // Mengosongkan nilai input yang salah
        txt_uang.requestFocus(); // Fokuskan kembali ke elemen input yang salah
    }
}
//fix bisa
//private void pembayaran() {
//    try {
//        Connection conn = koneksi.getKoneksi();
//        
//        // Buat query untuk insert data dari tb_keranjang ke transaksi
//        String queryInsertTransaksi = "INSERT INTO transaksi " +
//                                     "(nomor_transaksi, tgl_transaksi, kode_barang, nama_barang, kategori, harga, jumlah_barang, total_harga, catatan, namacs) " +
//                                     "SELECT no_transaksi, tgl_transaksi, kode_barang, nama_barang, kategori, harga, jumlah, total_harga, catatan, namacs " +
//                                     "FROM tb_keranjang";
//        PreparedStatement psInsertTransaksi = conn.prepareStatement(queryInsertTransaksi);
//        
//        // Eksekusi query
//        int rowsAffected = psInsertTransaksi.executeUpdate();
//        
//        if (rowsAffected > 0) {
//            // Tampilkan pesan sukses
//            JOptionPane.showMessageDialog(null, "Pembayaran berhasil!");
//        } else {
//            JOptionPane.showMessageDialog(null, "Gagal melakukan pembayaran.");
//        }
//    } catch (SQLException ex) {
//        Logger.getLogger(formTransaksi.class.getName()).log(Level.SEVERE, null, ex);
//    }
//}

//private void pembayaran() {
//    try {
//        Connection conn = koneksi.getKoneksi();
//        
//        // Buat query untuk insert data dari tb_keranjang ke tb_transaksi
//     String queryInsertTransaksi = "INSERT INTO transaksi " +
//                             "(nomor_transaksi, tgl_transaksi, kode_barang, nama_barang, kategori, harga, jumlah_barang, total_harga, catatan, namacs) " +
//                             "SELECT no_transaksi, tgl_transaksi, kode_barang, nama_barang, kategori, harga, jumlah, total_harga, catatan, namacs " +
//                             "FROM tb_keranjang";
//        PreparedStatement psInsertTransaksi = conn.prepareStatement(queryInsertTransaksi);
//        
//        // Eksekusi query
//        int rowsAffected = psInsertTransaksi.executeUpdate();
//        
//        if (rowsAffected > 0) {
//            // Hapus data dari tabel tb_keranjang
//            String queryDeleteKeranjang = "DELETE FROM tb_keranjang";
//            PreparedStatement psDeleteKeranjang = conn.prepareStatement(queryDeleteKeranjang);
//            psDeleteKeranjang.executeUpdate();
//            
//            // Tampilkan pesan sukses
//            JOptionPane.showMessageDialog(null, "Pembayaran berhasil!");
//        } else {
//            JOptionPane.showMessageDialog(null, "Gagal melakukan pembayaran.");
//        }
//    } catch (SQLException ex) {
//        Logger.getLogger(formTransaksi.class.getName()).log(Level.SEVERE, null, ex);
//    }
//}





private int getLastTransactionNumber() {
    int lastTransactionNumber = 0;
    try {
        // Buat koneksi ke database
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_ujilevel", "root", "");

        // Buat statement dan jalankan query
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT MAX(CAST(SUBSTRING(nomor_transaksi, LOCATE('-', nomor_transaksi) + 1) AS SIGNED INTEGER)) AS last_transaction_number FROM transaksi");

        // Dapatkan nomor transaksi terbesar
        if (rs.next()) {
            lastTransactionNumber = rs.getInt("last_transaction_number");
        }

        // Tutup koneksi
        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lastTransactionNumber;
}

private void no_transaksi() {
    String tanggalSekarang = new SimpleDateFormat("yyyyMMdd").format(new Date());

    int lastTransactionNumber = getLastTransactionNumber();
    if (lastTransactionNumber == 0) {
        // Jika tidak ada transaksi di database, set lastTransactionNumber menjadi 1
        lastTransactionNumber = 1;
    } else {
        // Jika ada transaksi di database, increment lastTransactionNumber
        lastTransactionNumber++;
    }

    // Menghasilkan nomor transaksi dengan format yang diinginkan, misalnya: 20220507-002
    String nomorTransaksiFormatted = tanggalSekarang + "-" + String.format("%03d", lastTransactionNumber);

    // Menampilkan nomor transaksi ke dalam JTextField
    txt_kategori1.setText(nomorTransaksiFormatted);

}

private void btnTambahTransaksi_ActionPerformed(ActionEvent evt) {
    no_transaksi();
}
//private int getLastTransactionNumber() {
//    int lastTransactionNumber = 0;
//    try {
//        // Buat koneksi ke database
//        Connection conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/db_ujilevel","root","");
//        
//        // Buat statement dan jalankan query
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT MAX(nomor_transaksi) AS last_transaction_number FROM transaksi");
//        
//        // Dapatkan nomor transaksi terbesar
//        if (rs.next()) {
//            lastTransactionNumber = rs.getInt("last_transaction_number");
//        }
//        
//        // Tutup koneksi
//        rs.close();
//        stmt.close();
//        conn.close();
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return lastTransactionNumber;
//}
//private static int nomorTransaksi = 0;
//private static String tanggalTerakhir = "";
//private boolean perluPerbarui = false;
//
//private void no_transaksi() {
//    String tanggalSekarang = new SimpleDateFormat("yyyyMMdd").format(new Date());
//
//    int lastTransactionNumber = getLastTransactionNumber();
//    if (lastTransactionNumber == 0) {
//        // If there are no transactions in the database, set the lastTransactionNumber to 1
//        lastTransactionNumber = 1;
//    }
//
//    // Mengecek apakah tanggal saat ini sama dengan tanggal terakhir
//    if (tanggalSekarang.equals(tanggalTerakhir)) {
//        if (perluPerbarui) { // Jika tanggal sama, increment nomor transaksi
//            lastTransactionNumber++;
//        }
//    } else {
//        // Jika tanggal berbeda, simpan nomor transaksi dan tanggal terakhir ke Preferences
//        Preferences prefs = Preferences.userNodeForPackage(getClass());
//        prefs.putInt("nomorTransaksi", lastTransactionNumber);
//        prefs.put("tanggalTerakhir", tanggalTerakhir);
//        lastTransactionNumber = 1; // Reset nomor transaksi menjadi 1
//        tanggalTerakhir = tanggalSekarang;
//    }
//
//    // Menghasilkan nomor transaksi dengan format yang diinginkan, misalnya: T-20220507-001
//    String nomorTransaksiFormatted = "T-" + tanggalSekarang + "-" + String.format("%03d", lastTransactionNumber);
//
//    // Menampilkan nomor transaksi ke dalam JTextField
//    txt_kategori1.setText(nomorTransaksiFormatted);
//}
//
//
//private void btnTambahTransaksi_ActionPerformed(ActionEvent evt) {
//    perluPerbarui = true;
//    no_transaksi();
//}



//yang sekarang
//    private static int nomorTransaksi = 0;
//    private static String tanggalTerakhir = "";
//    
//
//private void no_transaksi() {
//    // Mendapatkan tanggal saat ini
//    String tanggalSekarang = new SimpleDateFormat("yyyyMMdd").format(new Date());
//    
//
//    // Mendapatkan nilai nomor transaksi dan tanggal terakhir dari Preferences
//   Preferences prefs = Preferences.userNodeForPackage(getClass());
//    int nomorTransaksi = prefs.getInt("nomorTransaksi", 1);
//    String tanggalTerakhir = prefs.get("tanggalTerakhir", "");
//
//    // Mengecek apakah tanggal saat ini sama dengan tanggal terakhir
//    if (tanggalSekarang.equals(tanggalTerakhir)) {
//            // Jika tanggal sama, increment nomor transaksi
//        //nomorTransaksi++;
//    } else {
//        // Jika tanggal berbeda, reset nomor transaksi menjadi 1
//        nomorTransaksi = 1;
//        tanggalTerakhir = tanggalSekarang;
//    }
//
//    // Menghasilkan nomor transaksi dengan format yang diinginkan, misalnya: T-20220507-001
//    String nomorTransaksiFormatted = "T-" + tanggalSekarang + "-" + String.format("%03d", nomorTransaksi);
//
//    // Menyimpan nilai nomor transaksi dan tanggal terakhir ke Preferences
//   prefs.putInt("nomorTransaksi", nomorTransaksi);
//    prefs.put("tanggalTerakhir", tanggalTerakhir);
//
//    // Menampilkan nomor transaksi ke dalam JTextField
//    txt_kategori1.setText(nomorTransaksiFormatted);
//
//    // Lakukan apa pun yang perlu dilakukan setelah nomor transaksi dihasilkan
//}
   
 
       
       
   private void getDataBarang(String kodeBarang) {
    try {
        // Panggil koneksi
        Connection connect = koneksi.getKoneksi();
        
        // Query untuk mendapatkan data barang dari database berdasarkan kode barang
        String query = "SELECT nama_barang, harga, kategori FROM tb_databarang WHERE kode_barang = ?";
        PreparedStatement ps = connect.prepareStatement(query);
        ps.setString(1, kodeBarang);
        ResultSet resultSet = ps.executeQuery();
        
        // Mengisi data ke komponen yang sesuai
        if (resultSet.next()) {
            String namaBarang = resultSet.getString("nama_barang");
            String harga = resultSet.getString("harga");
            String kategori = resultSet.getString("kategori");
            
            txt_namabarang2.setText(namaBarang);
            txt_harga2.setText(harga);
            txt_kategori.setText(kategori);
        }
        
        resultSet.close();
        ps.close();
        connect.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        e.printStackTrace();
    }
}

private String getKodeBarangFromDatabase(String inputKodeBarang) {
    String kodeBarang = "";
    try {
        // Panggil koneksi
        Connection connect = koneksi.getKoneksi();
        
        // Query untuk mendapatkan data barang berdasarkan kode barang yang diinput
        String query = "SELECT * FROM tb_databarang WHERE kode_barang = ?";
        PreparedStatement statement = connect.prepareStatement(query);
        statement.setString(1, inputKodeBarang);
        ResultSet resultSet = statement.executeQuery();
        
        // Cek apakah ada data yang ditemukan
        if (resultSet.next()) {
            // Ambil data barang dari database
            kodeBarang = resultSet.getString("kode_barang");
            // Lakukan pengambilan data lainnya yang diperlukan
            // ...
        } else {
            // Jika tidak ada data yang ditemukan, kembalikan pesan error
            kodeBarang = "Kode barang tidak ditemukan";
        }
        
        resultSet.close();
        statement.close();
        connect.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        e.printStackTrace();
    }
    
    return kodeBarang;
}
//       
//   private String kasirName;
//
//public void setKasirName(String kasirName) {
//    this.kasirName = kasirName;
//    txt_karyawan.setText(kasirName);
//}
//    private void simpanTransaksi(int total, int uang, int kembali) {
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        try {
//            // Mendapatkan koneksi ke database
//            conn = koneksi.getKoneksi();
//
//            // Query untuk menyimpan data transaksi
//            String sql = "INSERT INTO transaksi (tgl_transaksi, kode_barang, nama_barang, kategori, harga, jumlah_barang, total_harga, catatan, totals, uang, kembali) VALUES (CURDATE(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//            stmt = conn.prepareStatement(sql);
//            stmt.setString(1, txt_kodebarang2.getText());
//            stmt.setString(2, txt_namabarang2.getText());
//            stmt.setString(3, txt_kategori.getText());
//            stmt.setInt(4, Integer.parseInt(txt_harga2.getText()));
//            stmt.setInt(5, Integer.parseInt(txt_jumlah2.getText()));
//            stmt.setInt(6, total);
//            stmt.setString(7, txt_catatan.getText());
//            stmt.setInt(8, total);
//            stmt.setInt(9, uang);
//            stmt.setInt(10, kembali);
//
//            // Jalankan pernyataan SQL
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace(); // Ganti dengan penanganan kesalahan yang sesuai
//        } finally {
//            // Tutup koneksi dan pernyataan
//            try {
//                if (stmt != null) {
//                    stmt.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace(); // Ganti dengan penanganan kesalahan yang sesuai
//            }
//        }
//    }

    
//    private void tambahData(){
//        String kode = txt_kodebarang2.getText();
//        String nama = txt_namabarang2.getText();
//        String harga = txt_harga2.getText();
//        String jumlah = txt_jumlah2.getText();
//        String total = txt_totalharga.getText();
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        String tanggal = String.valueOf(date.format(tgl_transaksi.getDate()));
//        
//        //panggil koneksi
//        Connection connect = koneksi.getKoneksi();
//        //query untuk memasukan data
//        String query = "INSERT INTO `transaksi` (`tgl_transaksi`, `id_transaksi`, `kode_barang`, `nama_barang`, `harga`, `jumlah_barang`, `total_harga`) "
//                     + "VALUES ( '"+tanggal+"', NULL, '"+kode+"', '"+nama+"', '"+harga+"', '"+jumlah+"', '"+total+"')";
//        
//        try{
//            //menyiapkan statement untuk di eksekusi
//            PreparedStatement ps = (PreparedStatement) connect.prepareStatement(query);
//            ps.executeUpdate(query);
//            
//            
//        }catch(SQLException | HeadlessException e){
//            System.out.println(e);
//            
//            
//        }finally{
//            
//            
//            
//        }
//    }
       
       

  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_lembaga = new javax.swing.JPanel();
        panelGradiente1 = new swing.PanelGradiente();
        cmdRegister1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_username = new javax.swing.JLabel();
        tgl_transaksi = new com.toedter.calendar.JDateChooser();
        panelGradiente2 = new swing.PanelGradiente();
        jLabel1 = new javax.swing.JLabel();
        T_date = new javax.swing.JTextField();
        cmdRegister = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        kategori = new javax.swing.JLabel();
        txt_kodebarang2 = new javax.swing.JTextField();
        txt_cari = new javax.swing.JButton();
        txt_kategori = new javax.swing.JTextField();
        txt_namabarang2 = new javax.swing.JTextField();
        txt_jumlah2 = new javax.swing.JTextField();
        T_jumlah2 = new javax.swing.JLabel();
        txt_karyawan = new javax.swing.JTextField();
        T_jumlah1 = new javax.swing.JLabel();
        txt_namacs = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_catatan = new javax.swing.JTextArea();
        catatan = new javax.swing.JLabel();
        No_transaksi = new javax.swing.JLabel();
        txt_kategori1 = new javax.swing.JTextField();
        T_jumlah = new javax.swing.JLabel();
        T_harga = new javax.swing.JLabel();
        T_namabarang = new javax.swing.JLabel();
        txt_harga2 = new javax.swing.JTextField();
        kategori1 = new javax.swing.JLabel();
        txt_totalharga = new javax.swing.JTextField();
        T_total = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        t_total = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        txt_totalharga2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_keranjang = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        txt_kembalian = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_uang = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1926, 1142));
        getContentPane().setLayout(null);

        panel_lembaga.setBackground(new java.awt.Color(255, 255, 255));

        panelGradiente1.setColorPrimario(new java.awt.Color(204, 102, 0));
        panelGradiente1.setColorSecundario(new java.awt.Color(236, 177, 118));

        cmdRegister1.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        cmdRegister1.setForeground(new java.awt.Color(255, 255, 255));
        cmdRegister1.setText("Form Transaksi");
        cmdRegister1.setContentAreaFilled(false);
        cmdRegister1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdRegister1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRegister1ActionPerformed(evt);
            }
        });
        panelGradiente1.add(cmdRegister1);
        cmdRegister1.setBounds(770, 20, 270, 110);

        jLabel2.setFont(new java.awt.Font("Serif", 2, 20)); // NOI18N
        jLabel2.setText("Karyawan :");
        panelGradiente1.add(jLabel2);
        jLabel2.setBounds(10, 110, 95, 27);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-businessman-80.png"))); // NOI18N
        panelGradiente1.add(jLabel5);
        jLabel5.setBounds(50, 30, 80, 80);

        txt_username.setFont(new java.awt.Font("Serif", 2, 20)); // NOI18N
        txt_username.setText(".....");
        panelGradiente1.add(txt_username);
        txt_username.setBounds(120, 110, 25, 27);

        tgl_transaksi.setDateFormatString("dd-MM-yyyy");
        tgl_transaksi.setEnabled(false);
        tgl_transaksi.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        panelGradiente1.add(tgl_transaksi);
        tgl_transaksi.setBounds(1480, 110, 201, 29);

        panelGradiente2.setColorPrimario(new java.awt.Color(204, 102, 0));
        panelGradiente2.setColorSecundario(new java.awt.Color(236, 177, 118));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/135.png"))); // NOI18N
        panelGradiente2.add(jLabel1);
        jLabel1.setBounds(50, 110, 135, 125);

        T_date.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        T_date.setBorder(null);
        T_date.setEnabled(false);
        T_date.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                T_dateActionPerformed(evt);
            }
        });
        panelGradiente2.add(T_date);
        T_date.setBounds(40, 40, 156, 48);

        cmdRegister.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        cmdRegister.setForeground(new java.awt.Color(255, 255, 255));
        cmdRegister.setText("Dashboard");
        cmdRegister.setContentAreaFilled(false);
        cmdRegister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRegisterActionPerformed(evt);
            }
        });
        panelGradiente2.add(cmdRegister);
        cmdRegister.setBounds(20, 250, 190, 50);

        jButton5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/logout.png"))); // NOI18N
        jButton5.setText("  LOGOUT");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        panelGradiente2.add(jButton5);
        jButton5.setBounds(40, 952, 134, 50);

        jButton4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/attachment.png"))); // NOI18N
        jButton4.setText("  RIWAYAT");
        jButton4.setPreferredSize(new java.awt.Dimension(171, 40));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        panelGradiente2.add(jButton4);
        jButton4.setBounds(20, 590, 185, 48);

        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/file.png"))); // NOI18N
        jButton1.setText("  TRANSAKSI");
        jButton1.setPreferredSize(new java.awt.Dimension(171, 40));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panelGradiente2.add(jButton1);
        jButton1.setBounds(20, 350, 185, 48);

        jButton2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/folder.png"))); // NOI18N
        jButton2.setText("  DAFTAR MENU");
        jButton2.setPreferredSize(new java.awt.Dimension(171, 40));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        panelGradiente2.add(jButton2);
        jButton2.setBounds(20, 470, 185, 48);

        kategori.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        kategori.setText("kode barang");

        txt_kodebarang2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_kodebarang2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_kodebarang2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kodebarang2ActionPerformed(evt);
            }
        });
        txt_kodebarang2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_kodebarang2KeyPressed(evt);
            }
        });

        txt_cari.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_cari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/find.png"))); // NOI18N
        txt_cari.setText("  SEARCH DATA");
        txt_cari.setToolTipText("");
        txt_cari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_cariMouseClicked(evt);
            }
        });
        txt_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cariActionPerformed(evt);
            }
        });

        txt_kategori.setEditable(false);
        txt_kategori.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kategoriActionPerformed(evt);
            }
        });

        txt_namabarang2.setEditable(false);
        txt_namabarang2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_namabarang2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namabarang2ActionPerformed(evt);
            }
        });

        txt_jumlah2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_jumlah2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlah2ActionPerformed(evt);
            }
        });
        txt_jumlah2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_jumlah2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_jumlah2KeyTyped(evt);
            }
        });

        T_jumlah2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        T_jumlah2.setText("Kasir");

        txt_karyawan.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_karyawan.setEnabled(false);
        txt_karyawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_karyawanActionPerformed(evt);
            }
        });
        txt_karyawan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_karyawanKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_karyawanKeyTyped(evt);
            }
        });

        T_jumlah1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        T_jumlah1.setText("Jumlah");

        txt_namacs.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_namacs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namacsActionPerformed(evt);
            }
        });
        txt_namacs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_namacsKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_namacsKeyTyped(evt);
            }
        });

        txt_catatan.setColumns(20);
        txt_catatan.setRows(5);
        jScrollPane2.setViewportView(txt_catatan);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        catatan.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        catatan.setText("Catatan");

        No_transaksi.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        No_transaksi.setText("No Transaksi");

        txt_kategori1.setEditable(false);
        txt_kategori1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_kategori1.setEnabled(false);
        txt_kategori1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kategori1ActionPerformed(evt);
            }
        });

        T_jumlah.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        T_jumlah.setText("Nama Customer");

        T_harga.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        T_harga.setText("Harga");

        T_namabarang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        T_namabarang.setText("Nama Barang");

        txt_harga2.setEditable(false);
        txt_harga2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_harga2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_harga2ActionPerformed(evt);
            }
        });

        kategori1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        kategori1.setText("Kategori");

        txt_totalharga.setEditable(false);
        txt_totalharga.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_totalharga.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txt_totalhargaMouseReleased(evt);
            }
        });
        txt_totalharga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalhargaActionPerformed(evt);
            }
        });

        T_total.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        T_total.setText("Total Harga");

        jButton6.setBackground(new java.awt.Color(51, 255, 51));
        jButton6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/che2ck.png"))); // NOI18N
        jButton6.setText("  ADD");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/trash-can (1).png"))); // NOI18N
        jButton7.setText("  DELETE");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("KERANJANG");

        t_total.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        t_total.setText("Grand Total");

        jButton8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/refresh.png"))); // NOI18N
        jButton8.setText("  RESET");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        txt_totalharga2.setEditable(false);
        txt_totalharga2.setBackground(new java.awt.Color(153, 153, 153));
        txt_totalharga2.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        txt_totalharga2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_totalharga2.setText("Rp.0,0");
        txt_totalharga2.setEnabled(false);
        txt_totalharga2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txt_totalharga2MouseReleased(evt);
            }
        });
        txt_totalharga2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalharga2ActionPerformed(evt);
            }
        });

        tb_keranjang.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tb_keranjang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "ttitle 5", "ttitle 6"
            }
        ));
        tb_keranjang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_keranjangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_keranjang);

        jButton3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/print.png"))); // NOI18N
        jButton3.setText("  PRINT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txt_kembalian.setEditable(false);
        txt_kembalian.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txt_kembalian.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_kembalian.setEnabled(false);
        txt_kembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kembalianActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("KEMBALIAN");

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("BAYAR");

        txt_uang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_uang.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_uang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_uangActionPerformed(evt);
            }
        });
        txt_uang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_uangKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_uangKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_uangKeyTyped(evt);
            }
        });

        jButton9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/credit-card.png"))); // NOI18N
        jButton9.setText("  PAYMENT");
        jButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton9MouseClicked(evt);
            }
        });
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_lembagaLayout = new javax.swing.GroupLayout(panel_lembaga);
        panel_lembaga.setLayout(panel_lembagaLayout);
        panel_lembagaLayout.setHorizontalGroup(
            panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_lembagaLayout.createSequentialGroup()
                .addGap(0, 4, Short.MAX_VALUE)
                .addComponent(panelGradiente2, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelGradiente1, javax.swing.GroupLayout.PREFERRED_SIZE, 1689, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel_lembagaLayout.createSequentialGroup()
                        .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_lembagaLayout.createSequentialGroup()
                                .addGap(129, 129, 129)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_lembagaLayout.createSequentialGroup()
                                    .addGap(513, 513, 513)
                                    .addComponent(kategori)
                                    .addGap(300, 300, 300))
                                .addGroup(panel_lembagaLayout.createSequentialGroup()
                                    .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_lembagaLayout.createSequentialGroup()
                                            .addGap(82, 82, 82)
                                            .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txt_karyawan, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txt_kategori1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(No_transaksi)
                                                    .addComponent(kategori1)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txt_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(txt_jumlah2)
                                                            .addComponent(txt_namacs, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(T_jumlah1)
                                                            .addComponent(T_jumlah))))
                                                .addComponent(T_jumlah2))
                                            .addGap(93, 93, 93))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_lembagaLayout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txt_cari)
                                            .addGap(61, 61, 61)))
                                    .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(T_total)
                                            .addComponent(txt_namabarang2, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                                            .addComponent(T_namabarang)
                                            .addComponent(txt_harga2, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                                            .addComponent(T_harga)
                                            .addComponent(txt_totalharga, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                                            .addComponent(txt_kodebarang2))
                                        .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_lembagaLayout.createSequentialGroup()
                                                .addGap(129, 129, 129)
                                                .addComponent(catatan, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(151, 151, 151))
                                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(131, 131, 131))))
                        .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_lembagaLayout.createSequentialGroup()
                                .addComponent(t_total, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(380, 380, 380))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_lembagaLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 743, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_lembagaLayout.createSequentialGroup()
                                .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_uang, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(panel_lembagaLayout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton3)))
                                .addGap(171, 171, 171))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_lembagaLayout.createSequentialGroup()
                                .addComponent(txt_totalharga2, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(69, 69, 69))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_lembagaLayout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(177, 177, 177))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_lembagaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(277, 277, 277))))
        );
        panel_lembagaLayout.setVerticalGroup(
            panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_lembagaLayout.createSequentialGroup()
                .addComponent(panelGradiente1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_lembagaLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(t_total)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_totalharga2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panel_lembagaLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(kategori)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_kodebarang2)
                            .addComponent(txt_cari))
                        .addGap(18, 18, 18)
                        .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(T_jumlah2)
                            .addComponent(T_harga))
                        .addGap(6, 6, 6)
                        .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_harga2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_karyawan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(T_namabarang)
                            .addComponent(No_transaksi))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_namabarang2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_kategori1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kategori1)
                            .addComponent(T_total))
                        .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_lembagaLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(txt_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_lembagaLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_totalharga, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12)
                        .addComponent(T_jumlah1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_lembagaLayout.createSequentialGroup()
                                .addComponent(txt_jumlah2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(65, 65, 65)
                                .addComponent(T_jumlah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_namacs, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panel_lembagaLayout.createSequentialGroup()
                                .addComponent(catatan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(panel_lembagaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_lembagaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addGap(12, 12, 12)
                        .addComponent(txt_kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_uang, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_lembagaLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
            .addComponent(panelGradiente2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(panel_lembaga);
        panel_lembaga.setBounds(0, 0, 1920, 1080);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        kembalian();

        // perluPerbarui = true;
        no_transaksi();
        pembayaran();
        //        tambahData();
        //        JOptionPane.showMessageDialog(null, "Transaksi Berhasil !");
        //        new struk.struk().setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9MouseClicked

    private void txt_uangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_uangKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_uangKeyTyped

    private void txt_uangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_uangKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_uangKeyReleased

    private void txt_uangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_uangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_uangKeyPressed

    private void txt_uangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_uangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_uangActionPerformed

    private void txt_kembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kembalianActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try{
            String file = "/Struk_baru/Nota.jasper";

            Class.forName("com.mysql.jdbc.Driver").newInstance();
            HashMap param = new HashMap();

            param.put("total",txt_totalharga2.getText());
            param.put("uang",txt_uang.getText());
            param.put("kembalian",txt_kembalian.getText());
            param.put("namacs",txt_namacs.getText());
            param.put("kasir",txt_karyawan.getText());

            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream(file),param,koneksi.getKoneksi());
            JasperViewer.viewReport(print, false);

        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException | JRException e){
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tb_keranjangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_keranjangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb_keranjangMouseClicked

    private void txt_totalharga2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalharga2ActionPerformed
        //totalnya(); // hitungTotalKeranjang();
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalharga2ActionPerformed

    private void txt_totalharga2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_totalharga2MouseReleased
        //totalnya();        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalharga2MouseReleased

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:

        try{
            String clear = "TRUNCATE `tb_keranjang`";
            Connection connect = koneksi.getKoneksi();
            PreparedStatement ps = (PreparedStatement) connect.prepareStatement(clear);
            ps.execute();
            //   keranjang();

        }catch(Exception e){
            System.out.println(e);
        }finally{
            tampilData();
            totalnya();
            txt_uang.setText(null);
            txt_kembalian.setText(null);
            txt_kodebarang2.setText(null);
            txt_harga2.setText(null);
            txt_namabarang2.setText(null);
            txt_kategori.setText(null);
            txt_jumlah2.setText(null);
            txt_totalharga.setText(null);

        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        hapusData();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        keranjang();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txt_totalhargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalhargaActionPerformed
        //total();        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalhargaActionPerformed

    private void txt_totalhargaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_totalhargaMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalhargaMouseReleased

    private void txt_harga2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_harga2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_harga2ActionPerformed

    private void txt_kategori1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kategori1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kategori1ActionPerformed

    private void txt_namacsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_namacsKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namacsKeyTyped

    private void txt_namacsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_namacsKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namacsKeyReleased

    private void txt_namacsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namacsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namacsActionPerformed

    private void txt_karyawanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_karyawanKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_karyawanKeyTyped

    private void txt_karyawanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_karyawanKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_karyawanKeyReleased

    private void txt_karyawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_karyawanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_karyawanActionPerformed

    private void txt_jumlah2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlah2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlah2KeyTyped

    private void txt_jumlah2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlah2KeyReleased
        // TODO add your handling code here:
        total();
    }//GEN-LAST:event_txt_jumlah2KeyReleased

    private void txt_jumlah2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlah2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlah2ActionPerformed

    private void txt_namabarang2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namabarang2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namabarang2ActionPerformed

    private void txt_kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kategoriActionPerformed
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kategoriActionPerformed

    private void txt_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cariActionPerformed

    private void txt_cariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_cariMouseClicked
        // TODO add your handling code here
        new stok_barang().setVisible(true);
        dispose();
    }//GEN-LAST:event_txt_cariMouseClicked

    private void txt_kodebarang2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_kodebarang2KeyPressed
        // TODO add your handling code here:

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            // Dapatkan kode barang yang diinputkan oleh pengguna
            String inputKodeBarang = txt_kodebarang2.getText();

            // Panggil fungsi untuk mendapatkan kode barang dari database
            String kodeBarang = getKodeBarangFromDatabase(inputKodeBarang);

            // Cek apakah kode barang ditemukan
            if (!kodeBarang.equals("Kode barang tidak ditemukan")) {
                // Set nilai kode barang ke txt_kodebarang2
                txt_kodebarang2.setText(kodeBarang);

                // Memanggil fungsi untuk mendapatkan data lainnya berdasarkan kode barang
                getDataBarang(kodeBarang);
            } else {
                // Jika kode barang tidak ditemukan, tampilkan pesan error
                JOptionPane.showMessageDialog(null, kodeBarang);
            }
        }
    }//GEN-LAST:event_txt_kodebarang2KeyPressed

    private void txt_kodebarang2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kodebarang2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kodebarang2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        new transaksi.daftar_menu().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new formTransaksi().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        //new ().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        //  new login.menu_login().setVisible(true);

        new login_new.sign_in().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void cmdRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRegisterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdRegisterActionPerformed

    private void T_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_T_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_T_dateActionPerformed

    private void cmdRegister1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRegister1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdRegister1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(formTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new formTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel No_transaksi;
    private javax.swing.JTextField T_date;
    private javax.swing.JLabel T_harga;
    private javax.swing.JLabel T_jumlah;
    private javax.swing.JLabel T_jumlah1;
    private javax.swing.JLabel T_jumlah2;
    private javax.swing.JLabel T_namabarang;
    private javax.swing.JLabel T_total;
    private javax.swing.JLabel catatan;
    private javax.swing.JButton cmdRegister;
    private javax.swing.JButton cmdRegister1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel kategori;
    private javax.swing.JLabel kategori1;
    private swing.PanelGradiente panelGradiente1;
    private swing.PanelGradiente panelGradiente2;
    private javax.swing.JPanel panel_lembaga;
    private javax.swing.JLabel t_total;
    private javax.swing.JTable tb_keranjang;
    private com.toedter.calendar.JDateChooser tgl_transaksi;
    private javax.swing.JButton txt_cari;
    private javax.swing.JTextArea txt_catatan;
    private javax.swing.JTextField txt_harga2;
    private javax.swing.JTextField txt_jumlah2;
    private javax.swing.JTextField txt_karyawan;
    private javax.swing.JTextField txt_kategori;
    private javax.swing.JTextField txt_kategori1;
    private javax.swing.JTextField txt_kembalian;
    private javax.swing.JTextField txt_kodebarang2;
    private javax.swing.JTextField txt_namabarang2;
    private javax.swing.JTextField txt_namacs;
    private javax.swing.JTextField txt_totalharga;
    private javax.swing.JTextField txt_totalharga2;
    private javax.swing.JTextField txt_uang;
    private javax.swing.JLabel txt_username;
    // End of variables declaration//GEN-END:variables

    static class dispose {

        public dispose() {
        }
    }

}