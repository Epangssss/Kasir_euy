/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package master;

import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import login_new.sign_in;

/**
 *
 * @author edwar
 */
public class CRUD_kategori extends javax.swing.JFrame {
   DefaultTableModel table = new DefaultTableModel(); 
   Connection con;
   
     private Connection connection;
   
     
     
     
     
    /**
     * Creates new form CRUD_kategori
     */
     
     
  
     
       public CRUD_kategori() {
        initComponents();
       
        koneksi conn = new koneksi();
        connection = conn.getKoneksi();
        
           T_user.setText(sign_in.getKasirName());
        
        table_barang1.setModel(table);
        table = (DefaultTableModel) table_barang1.getModel();
        table.addColumn("Kategori");
        table.addColumn("Kode Barang");
        
               DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table_barang1.getColumnCount(); i++) {
            table_barang1.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        // Center align table content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table_barang1.getColumnCount(); i++) {
            table_barang1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        addTableListener();
          Tampil_Data();
    }

       
       
       
          private void Tampil_Data() {
        try {
            // Bersihkan data tabel sebelumnya
            table.setRowCount(0);

            // Query untuk mengambil data dari tabel tb_kategori
            String selectQuery = "SELECT * FROM tb_kategori";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = selectStatement.executeQuery();

            // Isi data tabel
            while (resultSet.next()) {
                String namaKategori = resultSet.getString("kategori");
                String kodeBarang = resultSet.getString("kode_barang");
                table.addRow(new Object[]{namaKategori, kodeBarang});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    public CRUD_kategori() {
//        initComponents();
//         
//        koneksi conn = new koneksi();
//        koneksi.getKoneksi();
//        
//        table_barang1.setModel(table);
//        table.addColumn("Kategori");
//        table.addColumn("Kode Barang");
//
//  
//     
//    }
    
          
    
     public boolean tambah_kategori() {
        try {
            String namaKategori = txt_kategori.getText();
            String kodeBarang = txt_kodebarang.getText();

            // Periksa apakah kategori sudah ada
            String checkQuery = "SELECT * FROM tb_kategori WHERE kategori = ? AND kode_barang = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, namaKategori);
            checkStatement.setString(2, kodeBarang);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                // Kategori sudah ada, kembalikan false
                return false;
            }

            // Tambahkan kategori baru
            String insertQuery = "INSERT INTO tb_kategori (kategori, kode_barang) VALUES (?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1, namaKategori);
            insertStatement.setString(2, kodeBarang);
            insertStatement.executeUpdate();

            // Tambahkan data baru ke tabel
            table.addRow(new Object[]{namaKategori, kodeBarang});

              txt_kategori.setText("");
            txt_kodebarang.setText("");
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

     private void addTableListener() {
    table_barang1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table_barang1.getSelectedRow();
                if (selectedRow != -1) {
                    String namaKategori = (String) table_barang1.getValueAt(selectedRow, 0);
                    String kodeBarang = (String) table_barang1.getValueAt(selectedRow, 1);
                    txt_kategori.setText(namaKategori);
                    txt_kodebarang.setText(kodeBarang);
                }
            }
        }
    });
}
     
     private void editkategori() {
    String namaKategori = txt_kategori.getText();
    String kodeBarang = txt_kodebarang.getText();

    int selectedRow = table_barang1.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Silakan pilih baris yang ingin diedit.");
        return;
    }

    // Perbarui data di tabel
    table_barang1.setValueAt(namaKategori, selectedRow, 0);
    table_barang1.setValueAt(kodeBarang, selectedRow, 1);

    // Perbarui data di database
    editkategori(selectedRow, namaKategori, kodeBarang);
}

private void editkategori(int row, String namaKategori, String kodeBarang) {
    try {
        String updateQuery = "UPDATE tb_kategori SET kategori = ?, kode_barang = ? WHERE kategori = ? AND kode_barang = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
        updateStatement.setString(1, namaKategori);
        updateStatement.setString(2, kodeBarang);
        updateStatement.setString(3, (String) table_barang1.getValueAt(row, 0));
        updateStatement.setString(4, (String) table_barang1.getValueAt(row, 1));
        updateStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
//     
//private void editkategori() {
//    int selectedRow = table_barang1.getSelectedRow();
//    if (selectedRow == -1) {
//        JOptionPane.showMessageDialog(this, "Silakan pilih baris yang ingin diedit.");
//        return;
//    }
//
//    String namaKategori = (String) table_barang1.getValueAt(selectedRow, 0);
//    String kodeBarang = (String) table_barang1.getValueAt(selectedRow, 1);
//
//    // Meminta input dari pengguna
//    String newNamaKategori = JOptionPane.showInputDialog(this, "Masukkan nama kategori baru:", namaKategori);
//    if (newNamaKategori == null) {
//        // Pengguna membatalkan input
//        return;
//    }
//
//    String newKodeBarang = JOptionPane.showInputDialog(this, "Masukkan kode barang baru:", kodeBarang);
//    if (newKodeBarang == null) {
//        // Pengguna membatalkan input
//        return;
//    }
//
//    // Perbarui data di tabel
//    table_barang1.setValueAt(newNamaKategori, selectedRow, 0);
//    table_barang1.setValueAt(newKodeBarang, selectedRow, 1);
//
//    // Perbarui data di database
//    editkategori(selectedRow, newNamaKategori, newKodeBarang);
//}
private void hapuskategori() {
    int selectedRow = table_barang1.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Silakan pilih baris yang ingin dihapus.");
        return;
    }

    // Dapatkan nilai kategori dan kode barang dari baris yang dipilih
    String namaKategori = (String) table_barang1.getValueAt(selectedRow, 0);
    String kodeBarang = (String) table_barang1.getValueAt(selectedRow, 1);

    // Konfirmasi penghapusan
    int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus kategori " + namaKategori + " dengan kode barang " + kodeBarang + "?", "Konfirmasi Penghapusan", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        // Hapus data dari database
        hapuskategori(selectedRow, namaKategori, kodeBarang);

        // Dapatkan model tabel
        DefaultTableModel model = (DefaultTableModel) table_barang1.getModel();

        // Hapus baris dari model tabel
        model.removeRow(selectedRow);
    }
}

private void hapuskategori(int row, String namaKategori, String kodeBarang) {
    try {
        String deleteQuery = "DELETE FROM tb_kategori WHERE kategori = ? AND kode_barang = ?";
        PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
        deleteStatement.setString(1, namaKategori);
        deleteStatement.setString(2, kodeBarang);
        deleteStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

//
//private void hapuskategori() {
//    int selectedRow = table_barang1.getSelectedRow();
//    if (selectedRow == -1) {
//        JOptionPane.showMessageDialog(this, "Silakan pilih baris yang ingin dihapus.");
//        return;
//    }
//
//    String namaKategori = (String) table_barang1.getValueAt(selectedRow, 0);
//    String kodeBarang = (String) table_barang1.getValueAt(selectedRow, 1);
//
//    
//     int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus kategori " + namaKategori + " dengan kode barang " + kodeBarang + "?", "Konfirmasi Penghapusan", JOptionPane.YES_NO_OPTION);
//    if (confirm == JOptionPane.YES_OPTION) {
//        hapuskategori(selectedRow, namaKategori, kodeBarang);
//
//        // Dapatkan model tabel
//        DefaultTableModel model = (DefaultTableModel) table_barang1.getModel();
//
//        // Hapus baris dari model tabel
//        model.removeRow(selectedRow);
//        
//    }
//}
//    int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus kategori " + namaKategori + " dengan kode barang " + kodeBarang + "?", "Konfirmasi Penghapusan", JOptionPane.YES_NO_OPTION);
//    if (confirm == JOptionPane.YES_OPTION) {
//        hapuskategori(selectedRow, namaKategori, kodeBarang);
//       table_barang1.removeRow( selectedRow);
//    }
//}

//private void editkategori(int row, String namaKategori, String kodeBarang) {
//    try {
//        String updateQuery = "UPDATE tb_kategori SET kategori = ?, kode_barang = ? WHERE kategori = ? AND kode_barang = ?";
//        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
//        updateStatement.setString(1, namaKategori);
//        updateStatement.setString(2, kodeBarang);
//        updateStatement.setString(3, (String) table_barang1.getValueAt(row, 0));
//        updateStatement.setString(4, (String) table_barang1.getValueAt(row, 1));
//        updateStatement.executeUpdate();
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//}

//private void hapuskategori(int row, String namaKategori, String kodeBarang) {
//    try {
//        String deleteQuery = "DELETE FROM tb_kategori WHERE kategori = ? AND kode_barang = ?";
//        PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
//        deleteStatement.setString(1, namaKategori);
//        deleteStatement.setString(2, kodeBarang);
//        deleteStatement.executeUpdate();
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//}
//     public boolean addCategory() {
//        try {
//            String namaKategori = txt_kategori.getText();
//            String kodeBarang = txt_kodebarang.getText();
//
//            // Periksa apakah kategori sudah ada
//            String checkQuery = "SELECT * FROM tb_kategori WHERE nama_kategori = ? AND kode_barang = ?";
//            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
//            checkStatement.setString(1, namaKategori);
//            checkStatement.setString(2, kodeBarang);
//            ResultSet resultSet = checkStatement.executeQuery();
//
//            if (resultSet.next()) {
//                // Kategori sudah ada, kembalikan false
//                return false;
//            }
//
//            // Tambahkan kategori baru
//            String insertQuery = "INSERT INTO tb_kategori (nama_kategori, kode_barang) VALUES (?, ?)";
//            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
//            insertStatement.setString(1, namaKategori);
//            insertStatement.setString(2, kodeBarang);
//            insertStatement.executeUpdate();
//
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

//    private void showInfoDialog() {
//        // Membuat instance J_Informasi
//        infoDialog = new J_Informasi(this, true);
//        
//        // Menampilkan dialog
//        infoDialog.setVisible(true);
//    }

     
     
    
    
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelGradiente1 = new swing.PanelGradiente();
        jLabel2 = new javax.swing.JLabel();
        T_user = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmdRegister1 = new javax.swing.JButton();
        J_petunjuk = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_barang1 = new javax.swing.JTable();
        txt_kodebarang = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_kategori = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cmdRegister = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelGradiente1.setColorPrimario(new java.awt.Color(204, 102, 0));
        panelGradiente1.setColorSecundario(new java.awt.Color(236, 177, 118));

        jLabel2.setFont(new java.awt.Font("Serif", 2, 24)); // NOI18N
        jLabel2.setText("Admin : ");
        panelGradiente1.add(jLabel2);
        jLabel2.setBounds(10, 120, 82, 32);

        T_user.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        T_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        T_user.setText("...");
        panelGradiente1.add(T_user);
        T_user.setBounds(90, 120, 50, 32);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-businessman-80.png"))); // NOI18N
        panelGradiente1.add(jLabel5);
        jLabel5.setBounds(40, 30, 80, 80);

        cmdRegister1.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        cmdRegister1.setForeground(new java.awt.Color(255, 255, 255));
        cmdRegister1.setText("Data Kategori");
        cmdRegister1.setContentAreaFilled(false);
        cmdRegister1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdRegister1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRegister1ActionPerformed(evt);
            }
        });
        panelGradiente1.add(cmdRegister1);
        cmdRegister1.setBounds(430, 20, 310, 110);

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
        J_petunjuk.setBounds(930, 100, 160, 50);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jButton6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/edit.png"))); // NOI18N
        jButton6.setText("  EDIT");
        jButton6.setPreferredSize(new java.awt.Dimension(117, 40));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/trash-can (1).png"))); // NOI18N
        jButton2.setText("  DELETE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
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

        table_barang1.setFont(new java.awt.Font("Times New Roman", 0, 15)); // NOI18N
        table_barang1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        table_barang1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_barang1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(table_barang1);

        txt_kodebarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kodebarangActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Serif", 0, 20)); // NOI18N
        jLabel6.setText("Kode barang");

        txt_kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kategoriActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Serif", 0, 20)); // NOI18N
        jLabel3.setText("Tambah Kategori ");

        jLabel7.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel7.setText("Back To Dashboard");

        cmdRegister.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        cmdRegister.setForeground(new java.awt.Color(153, 51, 0));
        cmdRegister.setText("Dashboard");
        cmdRegister.setContentAreaFilled(false);
        cmdRegister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRegisterActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel4.setText("Back To Crud barang");

        jButton3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/chevron.png"))); // NOI18N
        jButton3.setText("  BACK");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(142, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_kodebarang, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3)
                            .addComponent(txt_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(44, 44, 44))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmdRegister)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cmdRegister)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_kodebarang, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txt_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 74, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
            .addComponent(panelGradiente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelGradiente1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(483, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmdRegister1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRegister1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdRegister1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        editkategori();        // TODO add your handling code here:
        // editData();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        hapuskategori();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        tambah_kategori();        // TODO add your handling code here:
        //   tambahData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void table_barang1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_barang1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_table_barang1MouseClicked

    private void txt_kodebarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kodebarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kodebarangActionPerformed

    private void txt_kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kategoriActionPerformed

    private void cmdRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRegisterActionPerformed
        new Menu_Admin("").setVisible(true);
        dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_cmdRegisterActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        new formCRUDBarang().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void J_petunjukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_J_petunjukActionPerformed
//J_Informasi1 ();
    //    j_dialog();
  //showInfoDialog();

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
            java.util.logging.Logger.getLogger(CRUD_kategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CRUD_kategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CRUD_kategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CRUD_kategori.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CRUD_kategori().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton J_petunjuk;
    private javax.swing.JLabel T_user;
    private javax.swing.JButton cmdRegister;
    private javax.swing.JButton cmdRegister1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private swing.PanelGradiente panelGradiente1;
    private javax.swing.JTable table_barang1;
    private javax.swing.JTextField txt_kategori;
    private javax.swing.JTextField txt_kodebarang;
    // End of variables declaration//GEN-END:variables
}
