 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master;
import Notification.Notification;
import com.toedter.calendar.JDateChooser;
import koneksi.koneksi;
import java.awt.HeadlessException;
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
import java.sql.DriverManager;
import java.text.ParseException;
import javax.swing.JComboBox;
import javax.swing.JFrame;






//new
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import report.*;

/**
 *
 * @author Hadi Firmansyah
 */


public class formCRUDBarang extends javax.swing.JFrame {
    DefaultTableModel table = new DefaultTableModel(); 
    Connection con;

    /**
     * Creates new form formAddBarang
     */
    public formCRUDBarang() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        tanggal();
        
        koneksi conn = new koneksi();
        koneksi.getKoneksi();
        
        table_barang1.setModel(table);
        table.addColumn("Kode Barang");
        table.addColumn("Nama Barang");
        table.addColumn("Kategori");
        table.addColumn("Harga");
        table.addColumn("Stok");
        table.addColumn("Tanggal Masuk");
        
        
        
       tampilData();
        
    }
    
private void autoIn() {
    try {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_ujilevel", "root", "");

        String sqlquery = "SELECT MAX(CAST(SUBSTRING(kode_barang, 4) AS UNSIGNED)) AS no_auto FROM tb_databarang WHERE kode_barang LIKE ?";
        PreparedStatement st = con.prepareStatement(sqlquery);
        
        String selectedCategory = (String) kode_text.getSelectedItem();
        String prefix = ""; // Inisialisasi awalan kode barang

        if (selectedCategory != null) {
            switch (selectedCategory) {
                case "Makanan":
                    prefix = "MKN";
                    break;
                case "Minuman":
                    prefix = "MNM";
                    break;
                case "Topping":
                    prefix = "TPG";
                    break;
                // Tambahkan case untuk kategori lainnya jika diperlukan
                default:
                    prefix = ""; // Jika tidak ada kategori yang cocok, awalan menjadi kosong
                    break;
            }
        }

        st.setString(1, prefix + "%");
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            int no_auto = rs.getInt("no_auto");
            String newCode = prefix + String.format("%03d", no_auto + 1);
            txt_kodebarang.setText(newCode);
        }
    } catch (ClassNotFoundException | SQLException e) {
        txt_kodebarang.setText("MKN0001"); // Jika terjadi kesalahan, isi dengan kode default
        e.printStackTrace();
    }
}

//PreparedStatement st = null;
// ResultSet rs = null;

private void tampilData() {
    try {
        // Menghapus semua baris yang ada dalam tabel sebelum menampilkan data baru
        int row = table_barang1.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table.removeRow(0); 
        }
        
        // Mengambil data dari database
        String query = "SELECT * FROM `tb_databarang` ";
        Connection connect = koneksi.getKoneksi();
        Statement sttmnt = connect.createStatement();
        ResultSet rslt = sttmnt.executeQuery(query);
        
        while (rslt.next()) {
            // Mengambil data dari hasil query
            String kode = rslt.getString("kode_barang");
            String nama = rslt.getString("nama_barang");
            String kategori = rslt.getString("kategori");
            String harga = rslt.getString("harga");
            String stok = rslt.getString("stok");
            String tanggal = rslt.getString("tanggal");
                    
            // Memasukkan data ke dalam array
            String[] data = {kode, nama, kategori, harga, stok, tanggal};
            
            // Menambahkan baris baru ke dalam tabel
            table.addRow(data);
        }
        
        // Mengeset nilai tabel agar ditampilkan
        table_barang1.setModel(table);
    } catch(Exception e) {
        System.out.println(e);
    }


       
    }
     private void clear(){
        txt_kodebarang.setText(null);
        txt_namabarang.setText(null);
        txt_harga.setText(null);
        txt_stok.setText(null);
       // txt_tanggal.setDate(null);
        
    }
     
     

   
private void tambahData() {
    try {
        // Panggil fungsi autoIn untuk mendapatkan kode barang otomatis
        autoIn();
        
        // Ambil nilai kode barang yang dihasilkan dari autoIn()
        String kode = txt_kodebarang.getText(); 
        
        // Ambil nilai-nilai lain dari field input
        String nama = txt_namabarang.getText();
        String harga = txt_harga.getText();
        String stok = txt_stok.getText();
        String tanggal = new SimpleDateFormat("yyyy-MM-dd").format(txt_tanggal.getDate());
        
        // Ambil kategori yang dipilih dari combobox
        String selectedCategory = (String) kode_text.getSelectedItem();
        String prefix = ""; // Inisialisasi awalan kode barang
        
        if (selectedCategory != null) {
            switch (selectedCategory) {
                case "Makanan":
                    prefix = "MKN";
                    break;
                case "Minuman":
                    prefix = "MNM";
                    break;
                case "Topping":
                    prefix = "TPG";
                    break;
                // Tambahkan case untuk kategori lainnya jika diperlukan
                default:
                    prefix = "" ; // Jika tidak ada kategori yang cocok, awalan menjadi kosong
                    break;
            }
        }
        
        // Panggil koneksi
        Connection connect = koneksi.getKoneksi();
        // Query untuk memasukan data
        String query = "INSERT INTO `tb_databarang` (`kode_barang`, `nama_barang`, `harga`, `stok`, `tanggal`, `kategori`) "
                     + "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connect.prepareStatement(query);
        ps.setString(1, kode);
        ps.setString(2, nama);
        ps.setString(3, harga);
        ps.setString(4, stok);
        ps.setString(5, tanggal);
        ps.setString(6, selectedCategory); // Inserting the selected category
        ps.executeUpdate();
       // JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
         Notification panel = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Data berhasil ditambahkan");
        panel.showNotification();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        e.printStackTrace();
    }
}






   
   

    public void tanggal(){
        Date now = new Date();  
        txt_tanggal.setDate(now);    
    }
    private void hapusData(){
    try {
        // Ambil nilai kode barang dari baris yang dipilih dalam tabel
        int i = table_barang1.getSelectedRow();
        String kode = table.getValueAt(i, 0).toString();
        
        Connection connect = koneksi.getKoneksi();
        
        // Query untuk menghapus data berdasarkan kode barang
        String query = "DELETE FROM `tb_databarang` WHERE `kode_barang` = ?";
        
        PreparedStatement ps = connect.prepareStatement(query);
        ps.setString(1, kode);
        
        // Eksekusi query
        int rowsAffected = ps.executeUpdate();
        
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null , "Data Berhasil Dihapus");
             Notification panel = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Data berhasil diupdate");
        panel.showNotification();
        } else {
            JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
        }
    } catch(SQLException e){
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
    } finally {
        tampilData(); // Memuat ulang data setelah penghapusan
        clear(); // Membersihkan input fields setelah penghapusan
    }

    
        
    }
    
    private void editData() {
    try {
        // Mendapatkan nilai kode barang dari field input
        String kode = txt_kodebarang.getText();
        String nama = txt_namabarang.getText();
        String harga = txt_harga.getText();
        String stok = txt_stok.getText();
        String kategori = (String) kode_text.getSelectedItem();

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal = date.format(txt_tanggal.getDate());

        // Panggil koneksi
        Connection connect = koneksi.getKoneksi();
        // Query untuk melakukan update data
        String query = "UPDATE `tb_databarang` SET `nama_barang` = ?, `harga` = ?, `stok` = ?, `tanggal` = ?, `kategori` = ? "
                     + "WHERE `kode_barang` = ?";
        PreparedStatement ps = connect.prepareStatement(query);
        ps.setString(1, nama);
        ps.setString(2, harga);
        ps.setString(3, stok);
        ps.setString(4, tanggal);
        ps.setString(5, kategori);
        ps.setString(6, kode);
        ps.executeUpdate();
        
        Notification panel = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Data berhasil diupdate");
        panel.showNotification();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        e.printStackTrace();
    } finally {
        // Memuat ulang data setelah update
        tampilData();
        // Membersihkan input fields setelah update
        clear();
        
        // Memanggil autoIn() untuk memperbarui kode barang otomatis setelah proses edit data selesai
        autoIn();
        
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
        
        // Update nilai kode barang pada field input
    }
}
//private void editData() {
//    try {
//        // Mendapatkan nilai kode barang dari field input
//        String kode = txt_kodebarang.getText();
//        String nama = txt_namabarang.getText();
//        String harga = txt_harga.getText();
//        String stok = txt_stok.getText();
//        String kategori = txt_kategori.getText();
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
//        Notification panel = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Data berhasil diupdate");
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
//        // Memanggil autoIn() untuk memperbarui kode barang otomatis setelah proses edit data selesai
//        autoIn();
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
//        // Update nilai kode barang pada field input
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        button5 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txt_search = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        txt_kategori = new javax.swing.JLabel();
        txt_namabarang = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_harga = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txt_stok = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
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

        jPanel1.setBackground(new java.awt.Color(153, 0, 0));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DATA BARANG");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1920, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        button5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        button5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/reload.png"))); // NOI18N
        button5.setText("  REFRESH");
        button5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button5ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/chevron.png"))); // NOI18N
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
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/find.png"))); // NOI18N
        jButton7.setText("  CARI");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        txt_kategori.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
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

        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/plus.png"))); // NOI18N
        jButton1.setText("  ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/minus.png"))); // NOI18N
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

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel8.setText("TANGGAL MASUK");

        txt_tanggal.setEnabled(false);
        txt_tanggal.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jButton6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/edit.png"))); // NOI18N
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
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/trash-can (1).png"))); // NOI18N
        jButton2.setText("  DELETE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        tabel_print.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tabel_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/print.png"))); // NOI18N
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
        kode_text.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Makanan", "Minuman", "Topping" }));
        kode_text.setToolTipText("");
        kode_text.setName(""); // NOI18N
        kode_text.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kode_textActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel12.setText("KODE BARANG");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button5))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_kodebarang, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txt_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(kode_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(247, 247, 247)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_harga, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_stok))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                    .addComponent(jButton7))))
                        .addGap(66, 66, 66))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_namabarang, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addGap(50, 50, 50))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tabel_print)
                        .addGap(26, 26, 26)
                        .addComponent(jButton2)
                        .addGap(32, 32, 32))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kode_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_tanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(txt_kodebarang))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_namabarang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_stok, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(94, 94, 94)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button5)))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jButton3)
                        .addGap(42, 42, 42))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tabel_print)
                            .addComponent(jButton2))
                        .addGap(60, 60, 60))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 6, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        tambahData();
    }//GEN-LAST:event_jButton1ActionPerformed

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
        new Menu_Admin().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void button5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button5ActionPerformed
        // TODO add your handling code here:
   tampilData();
     //dispose();
   

    }//GEN-LAST:event_button5ActionPerformed

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
            String file = "/report/report_barang.jasper";
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
                autoIn(); // Panggil metode autoIn saat combobox dipilih
            }
        }); 
        
        
        // TODO add your handling code here:
    }//GEN-LAST:event_kode_textActionPerformed

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
    private javax.swing.JButton button5;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JComboBox<String> kode_text;
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

    private JDateChooser setDateFormatString(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
