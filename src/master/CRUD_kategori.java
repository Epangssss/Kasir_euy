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
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
        
        table_barang1.setModel(table);
        table = (DefaultTableModel) table_barang1.getModel();
        table.addColumn("Kategori");
        table.addColumn("Kode Barang");
        
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


     
     
    
    
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_kategori = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_kodebarang = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_barang1 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("TAMBAH KATEGORI");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(222, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(210, 210, 210))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        txt_kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kategoriActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Serif", 0, 20)); // NOI18N
        jLabel3.setText("Tambah Kategori ");

        txt_kodebarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kodebarangActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Serif", 0, 20)); // NOI18N
        jLabel4.setText("Kode barang");

        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/plus.png"))); // NOI18N
        jButton1.setText("  ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                    .addGap(96, 96, 96)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel4)
                        .addComponent(txt_kodebarang, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addComponent(txt_kategori))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(35, 35, 35)
                            .addComponent(jButton2))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_kodebarang, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kategoriActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
tambah_kategori();        // TODO add your handling code here:
     //   tambahData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        new Menu_Admin().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void table_barang1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_barang1MouseClicked
        // TODO add your handling code here:
   
    }//GEN-LAST:event_table_barang1MouseClicked

    private void txt_kodebarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kodebarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kodebarangActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
editkategori();        // TODO add your handling code here:
       // editData();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
hapuskategori();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable table_barang1;
    private javax.swing.JTextField txt_kategori;
    private javax.swing.JTextField txt_kodebarang;
    // End of variables declaration//GEN-END:variables
}
