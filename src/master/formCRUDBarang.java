     /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master;
import Notification.Notification;
//import com.barcodelib.barcode.Linear;
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.WriterException;
//import com.google.zxing.common.BitMatrix;


import com.toedter.calendar.JDateChooser;
import koneksi.koneksi;
import java.awt.HeadlessException;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;






//new
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import login_new.sign_in;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import transaksi.formTransaksi;




public class formCRUDBarang extends javax.swing.JFrame {
    DefaultTableModel table = new DefaultTableModel(); 
    Connection con;
private J_dialog2 infoDialog;
    /**
     * Creates new form formAddBarang
     */
    public formCRUDBarang() {
        
    //   T_user.setText(sign_in.getKasirName());
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
      notifstokhabis();
//        ViewBarcode();
        tanggal();
        
        
//        koneksi conn = new koneksi();
//        koneksi.getKoneksi();

con = koneksi.getKoneksi();
        
        table_barang1.setModel(table);
        table.addColumn("Kode Barang");
        table.addColumn("Nama Barang");
        table.addColumn("Kategori");
        table.addColumn("Harga");
        table.addColumn("Stok");
        table.addColumn("Tanggal Masuk");
      //  ViewBarcode();
       // showBarcode();
     //   tampilkode("");
       tampilkan();
       tampilData();
     
       
    }
    
private void barang(String kodeBarang, int jumlahDikurangi) {
    try {
        // Ambil koneksi ke database
        Connection connect = koneksi.getKoneksi();

        // Query untuk mendapatkan stok barang
        String queryGetStok = "SELECT stok FROM tb_databarang WHERE kode_barang = ?";
        PreparedStatement psGetStok = connect.prepareStatement(queryGetStok);
        psGetStok.setString(1, kodeBarang);
        ResultSet rs = psGetStok.executeQuery();

        if (rs.next()) {
            int stok = rs.getInt("stok");

            // Cek jika stok kurang dari 0
            if (stok < 0) {
                JOptionPane.showMessageDialog(null, "Data stok tidak valid untuk barang dengan kode: " + kodeBarang);
            } else if (stok == 0) {
                JOptionPane.showMessageDialog(null, "Stok barang habis! Stok saat ini: " + stok);
            } else if (jumlahDikurangi > stok) {
                JOptionPane.showMessageDialog(null, "Jumlah yang diminta melebihi stok yang tersedia! Stok saat ini: " + stok);
            } else {
                // Kurangi stok barang
                String queryKurangiStok = "UPDATE tb_databarang SET stok = stok - ? WHERE kode_barang = ?";
                PreparedStatement psKurangiStok = connect.prepareStatement(queryKurangiStok);
                psKurangiStok.setInt(1, jumlahDikurangi);
                psKurangiStok.setString(2, kodeBarang);
                psKurangiStok.executeUpdate();

                JOptionPane.showMessageDialog(null, "Stok barang berhasil dikurangi. Stok baru: " + (stok - jumlahDikurangi));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Barang dengan kode " + kodeBarang + " tidak ditemukan.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
}

  private void showInfoDialog() {
        // Membuat instance J_Informasi
        infoDialog = new J_dialog2(this, true);
        
        // Menampilkan dialog
        infoDialog.setVisible(true);
    }
    
//    private void barang(String kodeBarang, int jumlahDikurangi) {
//    try {
//        // Ambil koneksi ke database
//        Connection connect = koneksi.getKoneksi();
//
//        // Query untuk mendapatkan stok barang
//        String queryGetStok = "SELECT stok FROM tb_databarang WHERE kode_barang = ?";
//        PreparedStatement psGetStok = connect.prepareStatement(queryGetStok);
//        psGetStok.setString(1, kodeBarang);
//        ResultSet rs = psGetStok.executeQuery();
//
//        if (rs.next()) {
//            int stok = rs.getInt("stok");
//
//            // Cek jika jumlah dikurangi melebihi stok
//            if (jumlahDikurangi > stok) {
//                JOptionPane.showMessageDialog(null, "Jumlah yang dikurangi melebihi stok yang tersedia! Stok saat ini: " + stok);
//            } else if (stok <= 0) {
//                JOptionPane.showMessageDialog(null, "Stok barang habis! Tidak dapat mengurangi lebih lanjut.");
//            } else {
//                // Kurangi stok barang
//                String queryKurangiStok = "UPDATE tb_databarang SET stok = stok - ? WHERE kode_barang = ?";
//                PreparedStatement psKurangiStok = connect.prepareStatement(queryKurangiStok);
//                psKurangiStok.setInt(1, jumlahDikurangi);
//                psKurangiStok.setString(2, kodeBarang);
//                psKurangiStok.executeUpdate();
//
//                JOptionPane.showMessageDialog(null, "Stok barang berhasil dikurangi. Stok baru: " + (stok - jumlahDikurangi));
//            }
//        } else {
//            JOptionPane.showMessageDialog(null, "Barang dengan kode " + kodeBarang + " tidak ditemukan.");
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
//    }
//}


private void notifstokhabis() {
    try {
        // Retrieve data from the database
        String query = "SELECT kode_barang, nama_barang, stok FROM tb_databarang WHERE stok <= 0";
        Connection connect = koneksi.getKoneksi();
        Statement statement = connect.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        // Iterate through the results and display notifications
        while (resultSet.next()) {
            String kodeBarang = resultSet.getString("kode_barang");
            String namaBarang = resultSet.getString("nama_barang");
            int stok = resultSet.getInt("stok");

            // Display a notification for low stock items
            showLowStockNotification(kodeBarang, namaBarang, stok);
        }
    } catch (Exception e) {
        System.out.println(e);
    }
}

private void showLowStockNotification(String kodeBarang, String namaBarang, int stok) {
    // You can use a library like JOptionPane or Swing's JDialog to display the notification
    JOptionPane.showMessageDialog(
        null,
        "Barang yang habis:\nCode: " + kodeBarang + "\nName: " + namaBarang + "\nStock: " + stok,
        "Stok Habis!!",
        JOptionPane.WARNING_MESSAGE
    );
}


private void tampilData() {
    try {
        // Menghapus semua baris yang ada dalam tabel sebelum menampilkan data baru
        int row = table_barang1.getRowCount();
        for(int a = 0; a < row; a++){
            table.removeRow(0); 
        }
        
        // Mengambil data dari database
        String query = "SELECT * FROM `tb_databarang`";
        Connection connect = koneksi.getKoneksi();
        Statement sttmnt = connect.createStatement();
        ResultSet rslt = sttmnt.executeQuery(query);
        
        while (rslt.next()) {
            // Mengambil data dari hasil query
            String kode = rslt.getString("kode_barang");
            String nama = rslt.getString("nama_barang");
            String kategori = rslt.getString("kategori");
            String harga = rslt.getString("harga");
            int stok = rslt.getInt("stok");
            String tanggal = rslt.getString("tanggal");
            
            // Mengatur stok menjadi 0 jika habis
            if (stok < 0) {
                stok = 0;
            }

            // Memasukkan data ke dalam array
            String[] data = {kode, nama, kategori, harga, String.valueOf(stok), tanggal};
            
            // Menambahkan baris baru ke dalam tabel
            table.addRow(data);
        }
        
        // Mengeset nilai tabel agar ditampilkan
        table_barang1.setModel(table);
        
        // Mengatur agar stok dan nama tabel berada di tengah
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        // Mengatur renderer untuk kolom stok
         table_barang1.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
         table_barang1.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
         table_barang1.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table_barang1.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
         table_barang1.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

        // Mengatur renderer untuk header tabel agar berada di tengah
        JTableHeader tableHeader = table_barang1.getTableHeader();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) tableHeader.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        
             DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        table_barang1.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

    } catch(Exception e) {
        System.out.println(e);
    }  
}



  
  private void tambahData() {
    try {
        // Ambil nilai kode barang yang diinput
        String kode = txt_kodebarang.getText(); 
        
        // Ambil nilai-nilai lain dari field input
        String nama = txt_namabarang.getText();
        String harga = txt_harga.getText();
        String stok = txt_stok.getText();
        String tanggal = new SimpleDateFormat("yyyy-MM-dd").format(txt_tanggal.getDate());
        
        // Ambil kategori yang dipilih dari combobox
        String selectedCategory = (String) kode_text.getSelectedItem();
        
        // Panggil koneksi
        Connection connect = koneksi.getKoneksi();
        
        // Query untuk memasukkan data
        String query = "INSERT INTO `tb_databarang` (`kode_barang`, `nama_barang`, `harga`, `stok`, `tanggal`, `kategori`) "
                     + "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connect.prepareStatement(query);
        ps.setString(1, kode);
        ps.setString(2, nama);
        ps.setString(3, harga);
        ps.setString(4, stok);
        ps.setString(5, tanggal);
        ps.setString(6, selectedCategory); // Memasukkan kategori yang dipilih
        ps.executeUpdate();
        
        
        
        tampilData();
        // Menampilkan notifikasi bahwa data berhasil ditambahkan
        Notification panel = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Data berhasil ditambahkan");
        panel.showNotification();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        e.printStackTrace();
    }
}
   








     private void clear(){
        txt_kodebarang.setText(null);
        txt_namabarang.setText(null);
        txt_harga.setText(null);
        txt_stok.setText(null);
       // txt_tanggal.setDate(null);
        
    }
     
     
    



     private void tampilkan() {
    try {
        // Bersihkan data pada JComboBox
        kode_text.removeAllItems();

        // Query untuk mengambil data dari tabel tb_kategori
        String selectQuery = "SELECT * FROM tb_kategori";
        PreparedStatement selectStatement = con.prepareStatement(selectQuery);
        ResultSet resultSet = selectStatement.executeQuery();

        // Isi data pada JComboBox
        while (resultSet.next()) {
            String namaKategori = resultSet.getString("kategori");
            kode_text.addItem(namaKategori);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
     }
    
     
     private void tampilkode(String kategori) {
    try {
        // Bersihkan data pada JTextField
        txt_kodebarang.setText("");

        // Query untuk mengambil kode prefiks dari tabel tb_kategori
        String selectKategoriQuery = "SELECT LEFT(kode_barang, 3) AS prefix, kategori FROM tb_kategori WHERE kategori = ? LIMIT 1";
        PreparedStatement selectKategoriStatement = con.prepareStatement(selectKategoriQuery);
        selectKategoriStatement.setString(1, kategori);
        ResultSet kategoriResultSet = selectKategoriStatement.executeQuery();

        String prefix = "";
        if (kategoriResultSet.next()) {
            prefix = kategoriResultSet.getString("prefix");
        } else {
            // Jika kategori tidak ditemukan di tb_kategori, tampilkan pesan error
            JOptionPane.showMessageDialog(null, "Kategori tidak terdaftar di dalam sistem.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Query untuk mengambil data dari tabel tb_databarang yang sesuai dengan prefix
        String selectDatabarangQuery = "SELECT SUBSTRING(kode_barang, ?) AS number FROM tb_databarang WHERE SUBSTRING(kode_barang, 1, ?) = ?";
        PreparedStatement selectDatabarangStatement = con.prepareStatement(selectDatabarangQuery);
        selectDatabarangStatement.setInt(1, prefix.length() + 1);
        selectDatabarangStatement.setInt(2, prefix.length());
        selectDatabarangStatement.setString(3, prefix);
        ResultSet databarangResultSet = selectDatabarangStatement.executeQuery();

        // Mencari celah dalam nomor yang ada
        Set<Integer> existingNumbers = new HashSet<>();
        while (databarangResultSet.next()) {
            existingNumbers.add(Integer.parseInt(databarangResultSet.getString("number")));
        }

        int newNumber = 1;
        while (existingNumbers.contains(newNumber)) {
            newNumber++;
        }

        // Buat kode barang baru
        String newKodeBarang = prefix + String.format("%03d", newNumber);
        txt_kodebarang.setText(newKodeBarang);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
     
// private void tampilkode(String kategori) {
//    try {
//        // Bersihkan data pada JTextField
//        txt_kodebarang.setText("");
//
//        // Query untuk mengambil kode prefiks dari tabel tb_kategori
//        String selectKategoriQuery = "SELECT LEFT(kode_barang, 3) AS prefix, kategori FROM tb_kategori WHERE kategori = ? LIMIT 1";
//        PreparedStatement selectKategoriStatement = con.prepareStatement(selectKategoriQuery);
//        selectKategoriStatement.setString(1, kategori);
//        ResultSet kategoriResultSet = selectKategoriStatement.executeQuery();
//
//        String prefix = "";
//        String kategoriDB = "";
//        if (kategoriResultSet.next()) {
//            prefix = kategoriResultSet.getString("prefix");
//            kategoriDB = kategoriResultSet.getString("kategori");
//        } else {
//            // Jika kategori tidak ditemukan di tb_kategori, tampilkan pesan error
//            JOptionPane.showMessageDialog(null, "Kategori tidak terdaftar di dalam sistem.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Periksa apakah kategori yang diinput sesuai dengan kategori di db
//        if (!kategori.equalsIgnoreCase(kategoriDB)) {
//            JOptionPane.showMessageDialog(null, "Kategori yang diinput tidak sesuai dengan kategori yang terdaftar.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Query untuk mengambil data dari tabel tb_databarang yang sesuai dengan prefix
//        String selectDatabarangQuery = "SELECT MAX(CAST(SUBSTRING(kode_barang, " + (prefix.length() + 1) + ") AS SIGNED)) AS max_number " +
//                                      "FROM tb_databarang " +
//                                      "WHERE SUBSTRING(kode_barang, 1, ?) = ?";
//        PreparedStatement selectDatabarangStatement = con.prepareStatement(selectDatabarangQuery);
//        selectDatabarangStatement.setInt(1, prefix.length());
//        selectDatabarangStatement.setString(2, prefix);
//        ResultSet databarangResultSet = selectDatabarangStatement.executeQuery();
//
//        int maxNumber = 0;
//        if (databarangResultSet.next()) {
//            maxNumber = databarangResultSet.getInt("max_number");
//        }
//
//        // Buat kode barang baru
//        String newKodeBarang = prefix + String.format("%03d", maxNumber + 1);
//        txt_kodebarang.setText(newKodeBarang);
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//}
     
// yang bisa
//     private void tampilkode(String kategori) {
//    try {
//        // Bersihkan data pada JTextField
//        txt_kodebarang.setText("");
//
//        // Query untuk mengambil kode prefiks dari tabel tb_kategori
//        String selectKategoriQuery = "SELECT LEFT(kode_barang, 3) AS prefix FROM tb_kategori WHERE kategori = ? LIMIT 1";
//        PreparedStatement selectKategoriStatement = con.prepareStatement(selectKategoriQuery);
//        selectKategoriStatement.setString(1, kategori);
//        ResultSet kategoriResultSet = selectKategoriStatement.executeQuery();
//
//        String prefix = "";
//        if (kategoriResultSet.next()) {
//            prefix = kategoriResultSet.getString("prefix");
//        } else {
//            // Jika kategori tidak ditemukan di tb_kategori, gunakan 3 karakter pertama dari kategori
//            prefix = kategori.substring(0, Math.min(3, kategori.length())).toUpperCase();
//        }
//
//        // Query untuk mengambil data dari tabel tb_databarang yang sesuai dengan prefix
//        String selectDatabarangQuery = "SELECT MAX(CAST(SUBSTRING(kode_barang, " + (prefix.length() + 1) + ") AS SIGNED)) AS max_number " +
//                                      "FROM tb_databarang " +
//                                      "WHERE SUBSTRING(kode_barang, 1, ?) = ?";
//        PreparedStatement selectDatabarangStatement = con.prepareStatement(selectDatabarangQuery);
//        selectDatabarangStatement.setInt(1, prefix.length());
//        selectDatabarangStatement.setString(2, prefix);
//        ResultSet databarangResultSet = selectDatabarangStatement.executeQuery();
//
//        int maxNumber = 0;
//        if (databarangResultSet.next()) {
//            maxNumber = databarangResultSet.getInt("max_number");
//        }
//
//        // Buat kode barang baru
//        String newKodeBarang = prefix + String.format("%03d", maxNumber + 1);
//        txt_kodebarang.setText(newKodeBarang);
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//}




  
  
  
//private void tambahData() {
//    try {
//        // Panggil fungsi autoIn untuk mendapatkan kode barang otomatis
//       // autoIn();
//        
//        // Ambil nilai kode barang yang dihasilkan dari autoIn()
//        String kode = txt_kodebarang.getText(); 
//        
//        // Ambil nilai-nilai lain dari field input
//        String nama = txt_namabarang.getText();
//        String harga = txt_harga.getText();
//        String stok = txt_stok.getText();
//        String tanggal = new SimpleDateFormat("yyyy-MM-dd").format(txt_tanggal.getDate());
//        
//        // Ambil kategori yang dipilih dari combobox
//        String selectedCategory = (String) kode_text.getSelectedItem();
//        String prefix = ""; // Inisialisasi awalan kode barang
//        
//        if (selectedCategory != null) {
//            switch (selectedCategory) {
//                case "Makanan":
//                    prefix = "MKN";
//                    break;
//                case "Minuman":
//                    prefix = "MNM";
//                    break;
//                case "Topping":
//                    prefix = "TPG";
//                    break;
//                // Tambahkan case untuk kategori lainnya jika diperlukan
//                default:
//                    prefix = "" ; // Jika tidak ada kategori yang cocok, awalan menjadi kosong
//                    break;
//            }
//        }
//        
//        // Panggil koneksi
//        Connection connect = koneksi.getKoneksi();
//        // Query untuk memasukan data
//        String query = "INSERT INTO `tb_databarang` (`kode_barang`, `nama_barang`, `harga`, `stok`, `tanggal`, `kategori`) "
//                     + "VALUES (?, ?, ?, ?, ?, ?)";
//        PreparedStatement ps = connect.prepareStatement(query);
//        ps.setString(1, kode);
//        ps.setString(2, nama);
//        ps.setString(3, harga);
//        ps.setString(4, stok);
//        ps.setString(5, tanggal);
//        ps.setString(6, selectedCategory); // Inserting the selected category
//        ps.executeUpdate();
//       // JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
//         Notification panel = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Data berhasil ditambahkan");
//        panel.showNotification();
//    } catch (Exception e) {
//        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
//        e.printStackTrace();
//    }
//}



   

    public void tanggal(){
        Date now = new Date();  
        txt_tanggal.setDate(now);    
    }
    
    
    private void hapusData() {
    try {
        // Ambil nilai kode barang dari baris yang dipilih dalam tabel
        int i = table_barang1.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(null, "Pilih baris yang ingin dihapus.");
            return;
        }
        String kode = table_barang1.getValueAt(i, 0).toString();
        
        // Menghubungkan ke database
        try (Connection connect = koneksi.getKoneksi();
             PreparedStatement ps = connect.prepareStatement("DELETE FROM `tb_databarang` WHERE `kode_barang` = ?")) {
             
            ps.setString(1, kode);
            
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                Notification panel = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Data berhasil dihapus");
                panel.showNotification();
            } else {
                JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
    } finally {
        tampilData(); // Memuat ulang data setelah penghapusan
        // clear(); // Tidak membersihkan input fields setelah penghapusan
    }
}
    
    
//    
//    private void hapusData(){
//    try {
//        // Ambil nilai kode barang dari baris yang dipilih dalam tabel
//        int i = table_barang1.getSelectedRow();
//        String kode = table.getValueAt(i, 0).toString();
//        
//        Connection connect = koneksi.getKoneksi();
//        
//        // Query untuk menghapus data berdasarkan kode barang
//        String query = "DELETE FROM `tb_databarang` WHERE `kode_barang` = ?";
//        
//        PreparedStatement ps = connect.prepareStatement(query);
//        ps.setString(1, kode);
//        
//        // Eksekusi query
//        int rowsAffected = ps.executeUpdate();
//        
//        if (rowsAffected > 0) {
//            JOptionPane.showMessageDialog(null , "Data Berhasil Dihapus");
//             Notification panel = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Data berhasil diupdate");
//        panel.showNotification();
//        } else {
//            JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
//        }
//    } catch(SQLException e){
//        e.printStackTrace();
//        JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
//    } finally {
//        tampilData(); // Memuat ulang data setelah penghapusan
//        clear(); // Membersihkan input fields setelah penghapusan
//    }   
//    }

    
    
    
    private void editData() {
    try {
        // Mendapatkan nilai dari field input
        String kode = txt_kodebarang.getText().trim();
        String nama = txt_namabarang.getText().trim();
        String harga = txt_harga.getText().trim();
        String stok = txt_stok.getText().trim();

        // Validasi input data
        if (kode.isEmpty() || nama.isEmpty() || harga.isEmpty() || stok.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi.");
            return;
        }

        // Validasi harga dan stok
        double hargaValue = Double.parseDouble(harga);
        int stokValue = Integer.parseInt(stok);

        if (hargaValue <= 0 || stokValue < 0) {
            JOptionPane.showMessageDialog(null, "Harga harus lebih besar dari 0 dan stok harus lebih besar atau sama dengan 0.");
            return;
        }

        // Panggil koneksi
        Connection connect = koneksi.getKoneksi();

        // Ambil kategori yang ada di database berdasarkan kode_barang
        String kategoriQuery = "SELECT kategori FROM tb_databarang WHERE kode_barang = ?";
        PreparedStatement psKategori = connect.prepareStatement(kategoriQuery);
        psKategori.setString(1, kode);
        ResultSet rsKategori = psKategori.executeQuery();

        if (!rsKategori.next()) {
            // Jika kode_barang tidak ditemukan, tampilkan pesan kesalahan
            JOptionPane.showMessageDialog(null, "Kode barang tidak ditemukan.");
            return;
        }

        // Dapatkan kategori dari hasil query
        String kategori = rsKategori.getString("kategori");

        // Format tanggal
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal = date.format(txt_tanggal.getDate());

        // Query untuk melakukan update data
        String query = "UPDATE tb_databarang SET nama_barang = ?, harga = ?, stok = ?, tanggal = ?, kategori = ? "
                     + "WHERE kode_barang = ?";
        PreparedStatement psUpdate = connect.prepareStatement(query);
        psUpdate.setString(1, nama);
        psUpdate.setString(2, harga);
        psUpdate.setString(3, stok);
        psUpdate.setString(4, tanggal);
        psUpdate.setString(5, kategori);
        psUpdate.setString(6, kode);

        // Log untuk debugging
        System.out.println("Query: " + query);
        System.out.println("Parameters: [" + nama + ", " + harga + ", " + stok + ", " + tanggal + ", " + kategori + ", " + kode + "]");

        psUpdate.executeUpdate();

        Notification panel = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Data berhasil diupdate");
        panel.showNotification();
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Harga harus berupa angka positif dan stok harus berupa angka non-negatif.");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        e.printStackTrace();
    } finally {
        // Memuat ulang data setelah update
        tampilData();
        // Membersihkan input fields setelah update
        clear();

        // Mengatur nilai tanggal pada JDateChooser setelah edit data
        String tanggalString = "";
        Date tanggalBaru = null;

        if (txt_tanggal.getDate() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            tanggalString = dateFormat.format(txt_tanggal.getDate());

            try {
                tanggalBaru = dateFormat.parse(tanggalString);
            } catch (ParseException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        txt_tanggal.setDate(tanggalBaru);
    }
}

    
//private void editData() {
//    try {
//        // Mendapatkan nilai dari field input
//        String kode = txt_kodebarang.getText().trim();
//        String nama = txt_namabarang.getText().trim();
//        String harga = txt_harga.getText().trim();
//        String stok = txt_stok.getText().trim();
//
//        // Validasi input data
//        if (kode.isEmpty() || nama.isEmpty() || harga.isEmpty() || stok.isEmpty()) {
//            JOptionPane.showMessageDialog(null, "Semua field harus diisi.");
//            return;
//        }
//
//        // Panggil koneksi
//        Connection connect = koneksi.getKoneksi();
//
//        // Ambil kategori yang ada di database berdasarkan kode_barang
//        String kategoriQuery = "SELECT kategori FROM tb_databarang WHERE kode_barang = ?";
//        PreparedStatement psKategori = connect.prepareStatement(kategoriQuery);
//        psKategori.setString(1, kode);
//        ResultSet rsKategori = psKategori.executeQuery();
//
//        if (!rsKategori.next()) {
//            // Jika kode_barang tidak ditemukan, tampilkan pesan kesalahan
//            JOptionPane.showMessageDialog(null, "Kode barang tidak ditemukan.");
//            return;
//        }
//
//        // Dapatkan kategori dari hasil query
//        String kategori = rsKategori.getString("kategori");
//
//        // Format tanggal
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        String tanggal = date.format(txt_tanggal.getDate());
//
//        // Query untuk melakukan update data
//        String query = "UPDATE tb_databarang SET nama_barang = ?, harga = ?, stok = ?, tanggal = ?, kategori = ? "
//                     + "WHERE kode_barang = ?";
//        PreparedStatement psUpdate = connect.prepareStatement(query);
//        psUpdate.setString(1, nama);
//        psUpdate.setString(2, harga);
//        psUpdate.setString(3, stok);
//        psUpdate.setString(4, tanggal);
//        psUpdate.setString(5, kategori);
//        psUpdate.setString(6, kode);
//
//        // Log untuk debugging
//        System.out.println("Query: " + query);
//        System.out.println("Parameters: [" + nama + ", " + harga + ", " + stok + ", " + tanggal + ", " + kategori + ", " + kode + "]");
//
//        psUpdate.executeUpdate();
//
//        Notification panel = new Notification(this, Notification.Type.SUCCESS, Notification.Location.CENTER, "Data berhasil diupdate");
//        panel.showNotification();
//    } catch (Exception e) {
//        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
//        e.printStackTrace();
//    } finally {
//        // Memuat ulang data setelah update
//        tampilData();
//        // Membersihkan input fields setelah update
//        clear();
//
//        // Mengatur nilai tanggal pada JDateChooser setelah edit data
//        String tanggalString = "";
//        Date tanggalBaru = null;
//
//        if (txt_tanggal.getDate() != null) {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            tanggalString = dateFormat.format(txt_tanggal.getDate());
//
//            try {
//                tanggalBaru = dateFormat.parse(tanggalString);
//            } catch (ParseException ex) {
//                ex.printStackTrace();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//
//        txt_tanggal.setDate(tanggalBaru);
//    }
//}



    
    
//    
//    private void editData() {
//    try {
//        // Mendapatkan nilai kode barang dari field input
//        String kode = txt_kodebarang.getText();
//        String nama = txt_namabarang.getText();
//        String harga = txt_harga.getText();
//        String stok = txt_stok.getText();
//        String kategori = (String) kode_text.getSelectedItem();
//
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        String tanggal = date.format(txt_tanggal.getDate());
//
//        // Panggil koneksi
//        Connection connect = koneksi.getKoneksi();
//        // Query untuk melakukan update data
//        String query = "UPDATE `tb_databarang` SET `nama_barang` = ?, `harga` = ?, `stok` = ?, `tanggal` = ?, `kategori` = ? "
//                     + "WHERE `kode_barang` = ?";
//        PreparedStatement ps = connect.prepareStatement(query);
//        ps.setString(1, nama);
//        ps.setString(2, harga);
//        ps.setString(3, stok);
//        ps.setString(4, tanggal);
//        ps.setString(5, kategori);
//        ps.setString(6, kode);
//        ps.executeUpdate();
//        
//        Notification panel = new Notification(this, Notification.Type.SUCCESS, Notification.Location.CENTER, "Data berhasil diupdate");
//        panel.showNotification();
//    } catch (Exception e) {
//        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
//        e.printStackTrace();
//    } finally {
//        // Memuat ulang data setelah update
//        tampilData();
//        // Membersihkan input fields setelah update
//        clear();
//        
//       
//        
//        // Mengatur nilai tanggal pada JDateChooser setelah edit data
//        String tanggalString = "";
//        Date tanggalBaru = null;
//
//        if (txt_tanggal.getDate() != null) {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            tanggalString = dateFormat.format(txt_tanggal.getDate());
//            
//            try {
//                tanggalBaru = dateFormat.parse(tanggalString);
//            } catch (ParseException ex) {
//                ex.printStackTrace();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//
//        txt_tanggal.setDate(tanggalBaru);
//        
//       
//    }
//}



   private void cari() {
    try {
        // Menghapus semua baris yang ada dalam tabel sebelum menampilkan data baru
        int row = table_barang1.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table.removeRow(0);
        }
        
        // Mendapatkan kata kunci pencarian dari field txt_search
        String cari = txt_search.getText();
        
        // Query untuk mencari data berdasarkan kode barang atau nama barang yang cocok dengan kata kunci pencarian
        String query = "SELECT * FROM `tb_databarang` WHERE `kode_barang` LIKE ? OR `nama_barang` LIKE ?";
        
        Connection connect = koneksi.getKoneksi();
        PreparedStatement ps = connect.prepareStatement(query);
        
        // Menetapkan nilai parameter pada query untuk kata kunci pencarian
        ps.setString(1, "%" + cari + "%");
        ps.setString(2, "%" + cari + "%");
        
        ResultSet rslt = ps.executeQuery();

        while (rslt.next()) {
            // Mengambil data dari hasil query
            String kode = rslt.getString("kode_barang");
            String nama = rslt.getString("nama_barang");
            String harga = rslt.getString("harga");
            String stok = rslt.getString("stok");
            String tanggal = rslt.getString("tanggal");
                    
            // Memasukkan data ke dalam array
            String[] data = {kode, nama, harga, stok, tanggal};
            
            // Menambahkan baris baru ke dalam tabel
            table.addRow(data);
        }
        
        // Mengeset nilai tabel agar ditampilkan
        table_barang1.setModel(table);
    } catch (Exception e) {
        System.out.println(e);
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        txt_search = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        txt_kategori = new javax.swing.JLabel();
        txt_namabarang = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_harga = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txt_stok = new javax.swing.JTextField();
        txt_tanggal = new com.toedter.calendar.JDateChooser();
        jButton6 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_barang1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        tabel_print = new javax.swing.JButton();
        txt_kodebarang = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        kode_text = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        cmdRegister = new javax.swing.JButton();
        panelGradiente1 = new swing.PanelGradiente();
        jLabel5 = new javax.swing.JLabel();
        cmdRegister1 = new javax.swing.JButton();
        J_petunjuk = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(950, 650));
        setSize(new java.awt.Dimension(1920, 1080));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jButton3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-go-back-24.png"))); // NOI18N
        jButton3.setText("  BACK");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txt_search.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_searchActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-search-24.png"))); // NOI18N
        jButton7.setText("  CARI");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        txt_kategori.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txt_kategori.setText("Kategori");

        txt_namabarang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_namabarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namabarangActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel10.setText("HARGA");

        txt_harga.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_harga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hargaActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-broom-24.png"))); // NOI18N
        jButton4.setText("CLEAR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setText("STOK");

        txt_stok.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_stok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_stokActionPerformed(evt);
            }
        });

        txt_tanggal.setEnabled(false);
        txt_tanggal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jButton6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-edit-24.png"))); // NOI18N
        jButton6.setText("  EDIT");
        jButton6.setPreferredSize(new java.awt.Dimension(117, 40));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        table_barang1.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        table_barang1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        table_barang1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_barang1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(table_barang1);

        jButton2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-delete-24.png"))); // NOI18N
        jButton2.setText("  DELETE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        tabel_print.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tabel_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-print-24.png"))); // NOI18N
        tabel_print.setText("  PRINT");
        tabel_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabel_printActionPerformed(evt);
            }
        });

        txt_kodebarang.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_kodebarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kodebarangActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel11.setText("NAMA BARANG");

        kode_text.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        kode_text.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "..." }));
        kode_text.setToolTipText("");
        kode_text.setName(""); // NOI18N
        kode_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kode_textActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel12.setText("KODE BARANG");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setText("TANGGAL MASUK");

        jButton5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-add-24.png"))); // NOI18N
        jButton5.setText("  ADD");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        cmdRegister.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        cmdRegister.setForeground(new java.awt.Color(51, 153, 0));
        cmdRegister.setText("Tambah Kategori");
        cmdRegister.setContentAreaFilled(false);
        cmdRegister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRegisterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabel_print)
                .addGap(186, 186, 186))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jButton2)
                        .addGap(42, 42, 42)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txt_namabarang, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_kodebarang, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(247, 247, 247)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_harga, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_stok)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kode_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmdRegister)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 286, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                            .addComponent(jButton7)))
                    .addComponent(jLabel9))
                .addGap(66, 66, 66))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kode_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9))
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_kodebarang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_namabarang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_stok, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(94, 94, 94)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jButton3)
                        .addGap(42, 42, 42))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(tabel_print)
                        .addGap(60, 60, 60))))
        );

        panelGradiente1.setColorPrimario(new java.awt.Color(204, 102, 0));
        panelGradiente1.setColorSecundario(new java.awt.Color(236, 177, 118));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-businessman-80.png"))); // NOI18N
        panelGradiente1.add(jLabel5);
        jLabel5.setBounds(30, 40, 90, 90);

        cmdRegister1.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        cmdRegister1.setForeground(new java.awt.Color(255, 255, 255));
        cmdRegister1.setText("Data Barang");
        cmdRegister1.setContentAreaFilled(false);
        cmdRegister1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdRegister1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRegister1ActionPerformed(evt);
            }
        });
        panelGradiente1.add(cmdRegister1);
        cmdRegister1.setBounds(800, 20, 310, 110);

        J_petunjuk.setFont(new java.awt.Font("sansserif", 1, 16)); // NOI18N
        J_petunjuk.setForeground(new java.awt.Color(255, 255, 255));
        J_petunjuk.setText("Petunjuk");
        J_petunjuk.setContentAreaFilled(false);
        J_petunjuk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        J_petunjuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                J_petunjukActionPerformed(evt);
            }
        });
        panelGradiente1.add(J_petunjuk);
        J_petunjuk.setBounds(1760, 100, 160, 50);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
            .addComponent(panelGradiente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelGradiente1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_searchActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        hapusData();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_hargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hargaActionPerformed

    private void txt_namabarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namabarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namabarangActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        clear();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void table_barang1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_barang1MouseClicked
        // TODO add your handling code here:
   int baris = table_barang1.getSelectedRow();
    
   String kode = table.getValueAt(baris, 0).toString();
txt_kodebarang.setText(kode);
   
String nama = table.getValueAt(baris, 1).toString();
txt_namabarang.setText(nama);

String harga = table.getValueAt(baris, 3).toString();
txt_harga.setText(harga);

String stok = table.getValueAt(baris, 4).toString();
txt_stok.setText(stok);

String tanggal = table.getValueAt(baris, 5).toString();

// Periksa format tanggal sebelum parsing
if (tanggal.matches("\\d{4}-\\d{2}-\\d{2}")) {
    Date convert = null;
    try {
        convert = new SimpleDateFormat("yyyy-MM-dd").parse(tanggal);
        txt_tanggal.setDate(convert);
    } catch (ParseException e) {
        e.printStackTrace();
    }
} else {
    System.err.println("Format tanggal tidak valid: " + tanggal);
}

    }//GEN-LAST:event_table_barang1MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        editData();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txt_stokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_stokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_stokActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        new Menu_Admin("").setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        cari();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void tabel_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabel_printActionPerformed
        // TODO add your handling code here:
//        try{
//            String file = "src/report/report_barang.jasper";
//            JasperPrint print = JasperFillManager.fillReport(file,null,koneksi.getKoneksi());
//            JasperViewer view = new JasperViewer(print,false);
//            view.setVisible(true);
////            JasperViewer.viewReport(print, false);
//            
//        }catch(Exception e){
//            JOptionPane.showMessageDialog(rootPane, e);
//        }
        try{
            String file = "/Report_new/Databarang.jasper";
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream(file),null,koneksi.getKoneksi());
            JasperViewer.viewReport(print, false);
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_tabel_printActionPerformed

    private void txt_kodebarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kodebarangActionPerformed

             // TODO add your handling code here:
    }//GEN-LAST:event_txt_kodebarangActionPerformed

    private void kode_textActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kode_textActionPerformed
     kode_text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
     //           autoIn();
     
       String selectedKategori = (String) kode_text.getSelectedItem();
    if (selectedKategori != null) {
        tampilkode(selectedKategori);
    }
//        String kategori = (String) kode_text.getSelectedItem();
//        tampilkode(kategori);


//new CRUD_kategori().setVisible(true);// Panggil metode autoIn saat combobox dipilih
            }
        }); 
       
        
        // TODO add your handling code here:
    }//GEN-LAST:event_kode_textActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
tambahData();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void cmdRegister1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRegister1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdRegister1ActionPerformed

    private void cmdRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRegisterActionPerformed
new CRUD_kategori().setVisible(true);
        dispose();// TODO add your handling code here:
    }//GEN-LAST:event_cmdRegisterActionPerformed

    private void J_petunjukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J_petunjukActionPerformed
        //J_Informasi1 ();
        //     j_dialog();
        showInfoDialog();

        // Menampilkan dialog
        // TODO add your handling code here:
    }//GEN-LAST:event_J_petunjukActionPerformed

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
            java.util.logging.Logger.getLogger(formCRUDBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formCRUDBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formCRUDBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formCRUDBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new formCRUDBarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton J_petunjuk;
    private javax.swing.JButton cmdRegister;
    private javax.swing.JButton cmdRegister1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JComboBox<String> kode_text;
    private swing.PanelGradiente panelGradiente1;
    private javax.swing.JButton tabel_print;
    private javax.swing.JTable table_barang1;
    private javax.swing.JTextField txt_harga;
    private javax.swing.JLabel txt_kategori;
    private javax.swing.JTextField txt_kodebarang;
    private javax.swing.JTextField txt_namabarang;
    private javax.swing.JTextField txt_search;
    private javax.swing.JTextField txt_stok;
    private com.toedter.calendar.JDateChooser txt_tanggal;
    // End of variables declaration//GEN-END:variables

   

}
