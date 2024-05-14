/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transaksi;
import static gui.Transaksi.harga;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author Hadi Firmansyah
 */
public class formTransaksi extends javax.swing.JFrame {
    DefaultTableModel table = new DefaultTableModel();
    /**
     * Creates new form formTransaksi
     */
    
   public formTransaksi() {
    initComponents();
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
    
    
    // Mengatur model tabel ke tb_keranjang

  //   displayDateTime();
        startTimer(); 
    // Menampilkan data setelah menambahkan kolom
    tampilData();
    // clearTransaksi();
    // Menghitung total harga dan menampilkan tanggal
  totalnya();
    tanggal();
    setTanggal();
   no_transaksi();
   
}
   
   
   
     
  

private void setTanggal() {
    Date now = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String formattedDate = sdf.format(now);
    txt_tanggal.setText(formattedDate); // Ganti jLabel1 dengan nama variabel JLabel Anda
}

    
    
  
   
    //String reversedValue = sb.reverse().toString();
    
    //rupiah += reversedValue;

  //  return rupiah;
   
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

          //masukan semua data kedalam array
            Object[] data = { no_transaksi, kode, nama, harga, jumlah, total, kategori, catatan };
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
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String tanggal = dateFormat.format(tgl_transaksi.getDate());
    totalnya();

    Connection connect = koneksi.getKoneksi();
    String queryTambahKeranjang = "INSERT INTO tb_keranjang (no_transaksi, kode_barang, nama_barang, kategori, harga, jumlah, total_harga, catatan, tgl_transaksi) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
        
        // Ketika menambahkan data ke keranjang
        // Atur nilai nomorTransaksiKeranjang sesuai dengan keranjang saat ini

      
        
        // Memasukkan nomor transaksi ke dalam query INSERT
      //  psKeranjang.setString(9, nomorTransaksiFormatted);
        
        int rowsAffected = psKeranjang.executeUpdate();

        if (rowsAffected > 0) {
            // Kurangi stok barang
            PreparedStatement psStok = connect.prepareStatement(queryKurangiStok);
            psStok.setString(1, jumlah); // Kurangi stok sebanyak jumlah yang ditambahkan ke keranjang
            psStok.setString(2, kode);
            psStok.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data Masuk Ke Keranjang dan Disimpan ke Database");

            DefaultTableModel model = (DefaultTableModel) tb_keranjang.getModel();
            model.addRow(new Object[] { no_transaksi, kode, nama, harga, jumlah, total, kategori, catatan });

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

// ...


// Metode untuk menghapus semua data dari tabel keranjang
//private void hapusSemuaDataKeranjang() {
//    try {
//        Connection connect = koneksi.getKoneksi();
//        String queryHapusDataKeranjang = "DELETE FROM tb_keranjang";
//        PreparedStatement statement = connect.prepareStatement(queryHapusDataKeranjang);
//        statement.executeUpdate();
//        statement.close();
//        connect.close();
//    } catch (SQLException e) {
//        e.printStackTrace();
//        JOptionPane.showMessageDialog(null, "Terjadi kesalahan dalam menghapus data dari keranjang!");
//    }
//}


  


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
    





// Metode untuk menghitung kembalian
    
//private void hitungKembalian() {
//    String total = txt_totalharga2.getText();
//    String uang = txt_uang.getText();
//
//    if (!total.isEmpty() && !uang.isEmpty()) {
//        int totals = Integer.parseInt(total);
//        int uangs = Integer.parseInt(uang);
//
//        if (uangs < totals) {
//            txt_kembalian.setText("Jumlah uang yang diberikan kurang!");
//        } else {
//            int kembali = uangs - totals;
//            String fix = Integer.toString(kembali);
//            txt_kembalian.setText(fix);
//        }
//    } else {
//        txt_kembalian.setText("");
//    }
//}
    
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

//    private void hapusData() {
//    // Get the selected row index from the table
//    int selectedRow = tb_keranjang.getSelectedRow();
//
//    // Check if a row is selected
//    if (selectedRow == -1) {
//        // No row is selected, show an error message
//        JOptionPane.showMessageDialog(null, "Silakan pilih baris untuk dihapus!");
//    } else {
//        // Confirm the deletion
//        int confirm = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
//
//        if (confirm == JOptionPane.YES_OPTION) {
//            // Delete the selected row from the table model
//            DefaultTableModel model = (DefaultTableModel) tb_keranjang.getModel();
//            model.removeRow(selectedRow);
//
//            // Show a success message
//            JOptionPane.showMessageDialog(null, "Data berhasil dihapus dari keranjang!");
//        }
//    }
//}
//    
//private void kembalian(){
//    String total = txt_totalharga2.getText();
//    String uang = txt_uang.getText().trim(); // Trim to remove leading/trailing spaces
//
//    try {
//        // Check if input is empty
//        if (uang.isEmpty()) {
//            throw new NumberFormatException();
//        }
//
//        // Validasi apakah input hanya berupa angka
//        if (!uang.matches("\\d+")) {
//            throw new NumberFormatException();
//        }
//
//        int totals = Integer.parseInt(total);
//        int uangs = Integer.parseInt(uang);
//
//        if (uangs < totals) {
//            JOptionPane.showMessageDialog(null, "Jumlah uang yang diberikan kurang!");
//        } else {
//            int kembali = uangs - totals;
//            String fix = Integer.toString(kembali);
//            txt_kembalian.setText(fix);
//       
//            JOptionPane.showMessageDialog(null, "Transaksi Berhasil!");
//
//            // Simpan data transaksi ke dalam database
//         //   simpanTransaksi(totals, uangs, kembali);
//        }
//    } catch (NumberFormatException e) {
//    JOptionPane.showMessageDialog(null, "Masukkan jumlah uang dengan angka yang valid!");
//    txt_uang.setText(""); // Mengosongkan nilai input yang salah
//    txt_uang.requestFocus(); // Fokuskan kembali ke elemen input yang salah
//
//    }
//}
    
    
//    private void kembalian() {
//    String total = txt_totalharga2.getText();
//    String uang = txt_uang.getText().trim(); // Trim to remove leading/trailing spaces
//
//    try {
//        // Check if input is empty
//        if (uang.isEmpty()) {
//            throw new NumberFormatException();
//        }
//
//        // Validasi apakah input hanya berupa angka
//        if (!uang.matches("\\d+")) {
//            throw new NumberFormatException();
//        }
//
//        int totals = Integer.parseInt(total);
//        int uangs = Integer.parseInt(uang);
//
//        if (uangs < totals) {
//            JOptionPane.showMessageDialog(null, "Jumlah uang yang diberikan kurang!");
//        } else {
//            int kembali = uangs - totals;
//            String fix = Integer.toString(kembali);
//            txt_kembalian.setText(fix);
//
//            JOptionPane.showMessageDialog(null, "Transaksi Berhasil!");
//
//            // Perbarui nomor transaksi
//            nomorTransaksi++;
//
//            // Panggil metode no_transaksi untuk menampilkan nomor transaksi yang diperbarui
//            no_transaksi();
//
//            // Simpan data transaksi ke dalam database
//            // simpanTransaksi(totals, uangs, kembali);
//        }
//    } catch (NumberFormatException e) {
//        JOptionPane.showMessageDialog(null, "Masukkan jumlah uang dengan angka yang valid!");
//        txt_uang.setText(""); // Mengosongkan nilai input yang salah
//        txt_uang.requestFocus(); // Fokuskan kembali ke elemen input yang salah
//    }
//}
    
    
   

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
            String fix = Integer.toString(kembali);
            txt_kembalian.setText(fix);

            JOptionPane.showMessageDialog(null, "Transaksi Berhasil!");
          
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Masukkan jumlah uang dengan angka yang valid!");
        txt_uang.setText(""); // Mengosongkan nilai input yang salah
        txt_uang.requestFocus(); // Fokuskan kembali ke elemen input yang salah
    }
}

 
 private void pembayaran() {
    try {
        // Menghubungkan ke database
        Connection connect = koneksi.getKoneksi();

        // Membuat string query untuk memasukkan data dari tabel tb_keranjang ke dalam tabel transaksi
        String queryInsertDetail = "INSERT INTO transaksi (no_transaksi, tgl_transaksi, kode_barang, nama_barang, kategori, harga, jumlah_barang, total_harga, catatan)"
                + " SELECT no_transaksi, tgl_transaksi, kode_barang, nama_barang, kategori, harga, jumlah, total_harga, catatan FROM tb_keranjang";
         String deleteQuery = "DELETE FROM tb_keranjang";

         
        // Membuat objek PreparedStatement untuk mengeksekusi query dengan penggantian parameter (?)
        PreparedStatement statement = connect.prepareStatement(queryInsertDetail);
        
  //no_transaksi(); // Panggil metode no_transaksi tanpa menggunakan nomorTransaksiDihasilkany
        // Mengeksekusi pernyataan SQL
        statement.executeUpdate();

        // Menutup pernyataan
        statement.close();
//        nomorTransaksi++;
        // Menghapus semua data dari tabel keranjang setelah dimasukkan ke dalam transaksi
     //   hapusSemuaDataKeranjang();

        // Menutup koneksi
        connect.close();
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Terjadi kesalahan dalam memasukkan data dari keranjang ke transaksi!");
    }
}

//
// Variabel flag untuk menandakan apakah nomor transaksi sudah dihasilkan atau belum
//private boolean nomorTransaksiDihasilkan = false;
//private int nomorTransaksi = 0;
 
 
 
//punya xidane
//private void no_transaksi() {
//    String tanggalSekarang = new SimpleDateFormat("yyyyMMdd").format(new Date());
//    String nomorTransaksiFormatted = "T-" + tanggalSekarang + "-" + String.format("%03d", nomorTransaksi);
//    System.out.println(nomorTransaksiFormatted);
//    txt_kategori1.setText(nomorTransaksiFormatted);
//    nomorTransaksi++;
//}

//
//
//private int nomorTransaksi = 1; // Initial transaction number
//
//private void no_transaksi() {
//    // Get the current date
//    String tanggalSekarang = new SimpleDateFormat("yyyyMMdd").format(new Date());
//
//    // Generate the transaction number in the desired format, e.g.: T-20220507-001
//    String nomorTransaksiFormatted = "T-" + tanggalSekarang + "-" + String.format("%03d", nomorTransaksi);
//
//    // Display the transaction number in the JTextField
//    txt_kategori1.setText(nomorTransaksiFormatted);
//    if(txt_kategori1.getText().equals(nomorTransaksiFormatted)){
////        nomorTransaksi.("T-20220507-001");
//    } else {
//        JOptionPane.showMessageDialog(null, "ERROR BABIK", "Error", JOptionPane.ERROR_MESSAGE);
//    }
////    txt_kategori1.setText("T-20220507-001");
//    
//    // Perform any actions needed after generating the transaction number
//    
//    // Increment nomorTransaksi by 1 for the next transaction
//    
//    nomorTransaksi++;
//}

 
 
 
//private int nomorTransaksi = 1;  // Deklarasikan sebagai variabel instance di luar metode kembalian()
//private int nomorTransaksi = 1;  // Deklarasikan sebagai variabel instance di luar metode kembalian()/
//private int nomorTransaksiTerakhir = 1;  // Simpan nomor transaksi terakhir yang digunakan

//private void no_transaksi() {
//    // Mendapatkan tanggal saat ini
//    String tanggalSekarang = new SimpleDateFormat("yyyyMMdd").format(new Date());
//
//    // Menghasilkan nomor transaksi dengan format yang diinginkan, misalnya: T-20220507-001
//    String nomorTransaksiFormatted = "T-" + tanggalSekarang + "-" + String.format("%03d", nomorTransaksi);
//
//    // Menampilkan nomor transaksi ke dalam JTextField
//    txt_kategori1.setText(nomorTransaksiFormatted);
//
//    // Lakukan apa pun yang perlu dilakukan setelah nomor transaksi dihasilkan
//}




    private static int nomorTransaksi = 0;
    private static String tanggalTerakhir = "";
    





private void no_transaksi() {
    // Mendapatkan tanggal saat ini
    String tanggalSekarang = new SimpleDateFormat("yyyyMMdd").format(new Date());
    

    // Mendapatkan nilai nomor transaksi dan tanggal terakhir dari Preferences
   Preferences prefs = Preferences.userNodeForPackage(getClass());
    int nomorTransaksi = prefs.getInt("nomorTransaksi", 1);
    String tanggalTerakhir = prefs.get("tanggalTerakhir", "");

    // Mengecek apakah tanggal saat ini sama dengan tanggal terakhir
    if (tanggalSekarang.equals(tanggalTerakhir)) {
            // Jika tanggal sama, increment nomor transaksi
        //nomorTransaksi++;
    } else {
        // Jika tanggal berbeda, reset nomor transaksi menjadi 1
        nomorTransaksi = 1;
        tanggalTerakhir = tanggalSekarang;
    }

    // Menghasilkan nomor transaksi dengan format yang diinginkan, misalnya: T-20220507-001
    String nomorTransaksiFormatted = "T-" + tanggalSekarang + "-" + String.format("%03d", nomorTransaksi);

    // Menyimpan nilai nomor transaksi dan tanggal terakhir ke Preferences
   prefs.putInt("nomorTransaksi", nomorTransaksi);
    prefs.put("tanggalTerakhir", tanggalTerakhir);

    // Menampilkan nomor transaksi ke dalam JTextField
    txt_kategori1.setText(nomorTransaksiFormatted);

    // Lakukan apa pun yang perlu dilakukan setelah nomor transaksi dihasilkan
}
   
 private void startTimer() {
        // Buat objek Timer untuk memperbarui waktu setiap detik
        Timer timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        // Mulai timer
        timer.start();
    }
      
       private void updateTime() {
        // Format waktu ke dalam string
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
      Date now = new Date();
       String currentTime = dateFormat.format(now);
     //    Set teks waktu ke dalam JTextField
       T_date.setText(currentTime);
    }
       
       
       

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

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        T_date = new javax.swing.JLabel();
        txt_tanggal = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txt_jumlah2 = new javax.swing.JTextField();
        txt_kodebarang2 = new javax.swing.JTextField();
        txt_namabarang2 = new javax.swing.JTextField();
        txt_totalharga = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        txt_cari = new javax.swing.JButton();
        txt_harga2 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txt_uang = new javax.swing.JTextField();
        T_namabarang = new javax.swing.JLabel();
        T_harga = new javax.swing.JLabel();
        T_jumlah = new javax.swing.JLabel();
        T_total = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_kembalian = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        tgl_transaksi = new com.toedter.calendar.JDateChooser();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_keranjang = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        txt_totalharga2 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        t_total = new javax.swing.JLabel();
        txt_kategori = new javax.swing.JTextField();
        kategori = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_catatan = new javax.swing.JTextArea();
        kategori1 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txt_kategori1 = new javax.swing.JTextField();
        No_transaksi = new javax.swing.JLabel();
        catatan = new javax.swing.JLabel();
        txt_jumlah3 = new javax.swing.JTextField();
        T_jumlah1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();

        jMenuBar2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        T_date.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        T_date.setText("Timer");

        txt_tanggal.setText("tanggal");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1926, 1142));
        getContentPane().setLayout(null);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(1643, 1011));

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

        txt_kodebarang2.setEditable(false);
        txt_kodebarang2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_kodebarang2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_kodebarang2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kodebarang2ActionPerformed(evt);
            }
        });

        txt_namabarang2.setEditable(false);
        txt_namabarang2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_namabarang2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namabarang2ActionPerformed(evt);
            }
        });

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

        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/credit-card.png"))); // NOI18N
        jButton1.setText("  PAYMENT");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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

        txt_harga2.setEditable(false);
        txt_harga2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_harga2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_harga2ActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(153, 0, 0));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("TRANSAKSI");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 2131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(139, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 22, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

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

        T_namabarang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        T_namabarang.setText("Nama Barang");

        T_harga.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        T_harga.setText("Harga");

        T_jumlah.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        T_jumlah.setText("Nama Customer");

        T_total.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        T_total.setText("Total Harga");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("KEMBALIAN");

        txt_kembalian.setEditable(false);
        txt_kembalian.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txt_kembalian.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_kembalian.setEnabled(false);
        txt_kembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kembalianActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/chevron.png"))); // NOI18N
        jButton4.setText("  BACK");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        tgl_transaksi.setDateFormatString("dd-MM-yyyy");
        tgl_transaksi.setEnabled(false);
        tgl_transaksi.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jButton2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/print.png"))); // NOI18N
        jButton2.setText("  PRINT");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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

        jButton5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/che2ck.png"))); // NOI18N
        jButton5.setText("  ADD");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        txt_totalharga2.setEditable(false);
        txt_totalharga2.setBackground(new java.awt.Color(153, 153, 153));
        txt_totalharga2.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        txt_totalharga2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
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

        jButton7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/refresh.png"))); // NOI18N
        jButton7.setText("  RESET");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        t_total.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        t_total.setText("Grand Total");

        txt_kategori.setEditable(false);
        txt_kategori.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kategoriActionPerformed(evt);
            }
        });

        kategori.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        kategori.setText("kode barang");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("KERANJANG");

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
                .addGap(0, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        kategori1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        kategori1.setText("Kategori");

        jButton6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/trash-can (1).png"))); // NOI18N
        jButton6.setText("  DELETE");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("BAYAR");

        txt_kategori1.setEditable(false);
        txt_kategori1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_kategori1.setEnabled(false);
        txt_kategori1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kategori1ActionPerformed(evt);
            }
        });

        No_transaksi.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        No_transaksi.setText("No Transaksi");

        catatan.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        catatan.setText("Catatan");

        txt_jumlah3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_jumlah3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlah3ActionPerformed(evt);
            }
        });
        txt_jumlah3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_jumlah3KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_jumlah3KeyTyped(evt);
            }
        });

        T_jumlah1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        T_jumlah1.setText("Jumlah");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(432, 432, 432)
                .addComponent(kategori)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(545, 545, 545))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(101, 101, 101)
                                .addComponent(jButton4))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(101, 101, 101)
                                .addComponent(txt_cari)
                                .addGap(31, 31, 31)
                                .addComponent(txt_kodebarang2, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(tgl_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(No_transaksi)
                                    .addComponent(txt_kategori1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(T_jumlah)
                                            .addComponent(kategori1)
                                            .addComponent(catatan, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_kategori, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                                            .addComponent(txt_jumlah2)
                                            .addComponent(txt_jumlah3)
                                            .addComponent(T_jumlah1))
                                        .addGap(186, 186, 186)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(T_total)
                                            .addComponent(T_namabarang)
                                            .addComponent(T_harga)
                                            .addComponent(txt_namabarang2, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                                            .addComponent(txt_harga2)
                                            .addComponent(txt_totalharga)
                                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_uang, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(101, 101, 101)))
                        .addGap(470, 470, 470))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(t_total)
                                .addGap(646, 646, 646))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(txt_totalharga2, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(436, 436, 436)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(473, 473, 473)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jButton2)
                            .addGap(26, 26, 26)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(26, 26, 26)
                            .addComponent(jButton6)
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 889, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(403, 403, 403)))))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(1398, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(460, 460, 460)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(kategori)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(t_total)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_totalharga2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tgl_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_kodebarang2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_cari))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(No_transaksi)
                            .addComponent(T_harga))
                        .addGap(6, 6, 6)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_kategori1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_harga2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kategori1)
                            .addComponent(T_namabarang))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_namabarang2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(T_total)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(T_jumlah1)
                                .addGap(2, 2, 2)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_jumlah2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_totalharga, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addComponent(T_jumlah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_jumlah3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton5))
                        .addGap(40, 40, 40)
                        .addComponent(catatan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)
                        .addComponent(jButton4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(54, 54, 54)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(69, 69, 69)
                        .addComponent(txt_uang, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85))))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(856, Short.MAX_VALUE)
                    .addComponent(jLabel10)
                    .addGap(191, 191, 191)))
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, -20, 2300, 1070);

        jMenuBar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_harga2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_harga2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_harga2ActionPerformed

    private void txt_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cariActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_txt_cariActionPerformed

    private void txt_cariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_cariMouseClicked
        // TODO add your handling code here 
   new stok_barang().setVisible(true);
dispose();
    }//GEN-LAST:event_txt_cariMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        kembalian();
       pembayaran();
//        tambahData();
//        JOptionPane.showMessageDialog(null, "Transaksi Berhasil !");
//        new struk.struk().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_totalhargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalhargaActionPerformed
//total();        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalhargaActionPerformed

    private void txt_namabarang2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namabarang2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namabarang2ActionPerformed

    private void txt_kodebarang2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kodebarang2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kodebarang2ActionPerformed

    private void txt_jumlah2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlah2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlah2ActionPerformed

    private void txt_totalhargaMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_totalhargaMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalhargaMouseReleased

    private void txt_jumlah2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlah2KeyReleased
        // TODO add your handling code here:
        total();
    }//GEN-LAST:event_txt_jumlah2KeyReleased

    private void txt_uangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_uangKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_uangKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        new user.Menu_User().setVisible(true);
        dispose();
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txt_kembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kembalianActionPerformed

    private void txt_uangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_uangKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_uangKeyTyped

    private void txt_jumlah2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlah2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlah2KeyTyped

    private void txt_uangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_uangKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txt_uangKeyPressed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        keranjang();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txt_uangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_uangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_uangActionPerformed

    private void txt_totalharga2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_totalharga2MouseReleased
//totalnya();        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalharga2MouseReleased

    private void txt_totalharga2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalharga2ActionPerformed
//totalnya(); // hitungTotalKeranjang();
         // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalharga2ActionPerformed

    private void tb_keranjangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_keranjangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb_keranjangMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try{
             String file = "/strukNew/Struck_new.jasper";
            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            HashMap param = new HashMap();
            
            param.put("total",txt_totalharga2.getText());
            param.put("uang",txt_uang.getText());
            param.put("kembalian",txt_kembalian.getText());
            param.put("date",T_date.getText());
            param.put("tanggal",txt_tanggal.getText());
            
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream(file),param,koneksi.getKoneksi());
            JasperViewer.viewReport(print, false);
            
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException | JRException e){
            System.out.println(e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
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
            txt_totalharga2.setText(null);
        }
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void txt_kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kategoriActionPerformed
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kategoriActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        hapusData();
     
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txt_kategori1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kategori1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kategori1ActionPerformed

    private void txt_jumlah3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlah3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlah3ActionPerformed

    private void txt_jumlah3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlah3KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlah3KeyReleased

    private void txt_jumlah3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlah3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlah3KeyTyped

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
    private javax.swing.JLabel T_date;
    private javax.swing.JLabel T_harga;
    private javax.swing.JLabel T_jumlah;
    private javax.swing.JLabel T_jumlah1;
    private javax.swing.JLabel T_namabarang;
    private javax.swing.JLabel T_total;
    private javax.swing.JLabel catatan;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel kategori;
    private javax.swing.JLabel kategori1;
    private javax.swing.JLabel t_total;
    private javax.swing.JTable tb_keranjang;
    private com.toedter.calendar.JDateChooser tgl_transaksi;
    private javax.swing.JButton txt_cari;
    private javax.swing.JTextArea txt_catatan;
    public javax.swing.JTextField txt_harga2;
    public javax.swing.JTextField txt_jumlah2;
    public javax.swing.JTextField txt_jumlah3;
    public javax.swing.JTextField txt_kategori;
    public javax.swing.JTextField txt_kategori1;
    public static javax.swing.JTextField txt_kembalian;
    public javax.swing.JTextField txt_kodebarang2;
    public javax.swing.JTextField txt_namabarang2;
    private javax.swing.JLabel txt_tanggal;
    public javax.swing.JTextField txt_totalharga;
    public static javax.swing.JTextField txt_totalharga2;
    public static javax.swing.JTextField txt_uang;
    // End of variables declaration//GEN-END:variables

    static class dispose {

        public dispose() {
        }
    }

}