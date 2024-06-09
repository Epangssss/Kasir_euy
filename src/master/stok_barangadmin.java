    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master;
//import static gui.FormUtama.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import transaksi.formTransaksi;
import javax.swing.JDialog;
import java.awt.Dialog;


public class stok_barangadmin extends javax.swing.JDialog {
    DefaultTableModel table = new DefaultTableModel();
    

    
  public stok_barangadmin() {
    initComponents();
    
    
    
    setModalityType(ModalityType.APPLICATION_MODAL);
    koneksi conn = new koneksi();
    koneksi.getKoneksi();
    
    table_barang.setModel(table);
    table.addColumn("Kode Barang");
    table.addColumn("Nama Barang");
    table.addColumn("Harga");
    table.addColumn("Stok");
    table.addColumn("Kategori"); // Menambahkan kolom "Kategori"
    
    tampilData();
}

  
  
  private void tampilData() {
    DefaultTableModel model = (DefaultTableModel) table_barang.getModel();
    model.setRowCount(0);

    String query = "SELECT * FROM tb_databarang";

    try {
        Connection connection = koneksi.getKoneksi();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            String kode = resultSet.getString("kode_barang");
            String nama = resultSet.getString("nama_barang");
            String harga = resultSet.getString("harga");
            int stok = resultSet.getInt("stok");
            String kategori = resultSet.getString("Kategori");

            // Menentukan tampilan stok
            String tampilanStok;
            if (stok == 0) {
                tampilanStok = "Stok Habis";
            } else if (stok > 0) {
                tampilanStok = String.valueOf(stok);
            } else {
                // Stok negatif, tampilkan pesan kesalahan dan lewati penambahan ke tabel
                JOptionPane.showMessageDialog(null, "Data stok tidak valid untuk barang: " + nama + " dengan kode: " + kode);
                continue;
            }

            model.addRow(new Object[]{kode, nama, harga, tampilanStok, kategori});
        }

        table_barang.setModel(model);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

  
//private void tampilData() {
//    DefaultTableModel model = (DefaultTableModel) table_barang.getModel();
//    model.setRowCount(0);
//
//    String query = "SELECT * FROM tb_databarang";
//
//    try {
//        Connection connection = koneksi.getKoneksi();
//        Statement statement = connection.createStatement();
//        ResultSet resultSet = statement.executeQuery(query);
//
//        while (resultSet.next()) {
//            String kode = resultSet.getString("kode_barang");
//            String nama = resultSet.getString("nama_barang");
//            String harga = resultSet.getString("harga");
//            int stok = resultSet.getInt("stok");
//            String kategori = resultSet.getString("Kategori");
//
//            // Mengubah tampilan stok menjadi "0" jika stok habis
//            String tampilanStok = (stok > 0) ? String.valueOf(stok) : "0";
//
//            // Menambahkan pesan stok habis saat stok = 0
//            if (stok == 0) {
//                tampilanStok = "Stok Habis";
//            }
//
//            model.addRow(new Object[]{kode, nama, harga, tampilanStok, kategori});
//        }
//
//        table_barang.setModel(model);
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//}



//private void tampilData(){
//    // Untuk menghapus baris setelah input
//    int row = table_barang.getRowCount();
//    for(int a = 0 ; a < row ; a++){
//        table.removeRow(0);
//    }
//    
//    String query = "SELECT * FROM `tb_databarang` ";
//    
//    try{
//        Connection connect = koneksi.getKoneksi(); // Memanggil koneksi
//        Statement sttmnt = connect.createStatement(); // Membuat statement
//        ResultSet rslt = sttmnt.executeQuery(query); // Menjalankan query
//        
//        while (rslt.next()){
//            // Menampung data sementara
//            String kode = rslt.getString("kode_barang");
//            String nama = rslt.getString("nama_barang");
//            String harga = rslt.getString("harga");
//            String stok = rslt.getString("stok");
//            String kategori = rslt.getString("Kategori"); // Menggunakan "Kategori" sesuai dengan nama kolom di database
//            
//            // Masukkan semua data ke dalam array
//            String[] data = {kode, nama, harga, stok, kategori};
//            // Menambahkan baris sesuai dengan data yang tersimpan di array
//            table.addRow(data);
//        }
//        
//        // Mengeset nilai yang ditampung agar muncul di table
//        table_barang.setModel(table);
//        
//    } catch(Exception e){
//        System.out.println(e);
//    }
//}

private void cari(){
    int row = table_barang.getRowCount();
    for(int a = 0 ; a < row ; a++){
        table.removeRow(0);
    }
    
    String cari = txt_cari.getText();
    
    String query = "SELECT * FROM `tb_databarang` WHERE `kode_barang`  LIKE '%"+cari+"%' OR `nama_barang` LIKE '%"+cari+"%' ";
            
   try{
       Connection connect = koneksi.getKoneksi(); // Memanggil koneksi
       Statement sttmnt = connect.createStatement(); // Membuat statement
       ResultSet rslt = sttmnt.executeQuery(query); // Menjalankan query
       
       while (rslt.next()){
            // Menampung data sementara
            String kode = rslt.getString("kode_barang");
            String nama = rslt.getString("nama_barang");
            String harga = rslt.getString("harga");
            String stok = rslt.getString("stok");
            String kategori = rslt.getString("Kategori"); // Menggunakan "Kategori" sesuai dengan nama kolom di database
            
            // Masukkan semua data ke dalam array
            String[] data = {kode, nama, harga, stok, kategori};
            // Menambahkan baris sesuai dengan data yang tersimpan di array
            table.addRow(data);
        }
        
        // Mengeset nilai yang ditampung agar muncul di table
        table_barang.setModel(table);
       
   }catch(Exception e){
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

        jPanel2 = new javax.swing.JPanel();
        txt_cari = new javax.swing.JTextField();
        search = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_barang = new javax.swing.JTable();
        refresh = new javax.swing.JButton();
        back = new javax.swing.JButton();
        panelGradiente1 = new swing.PanelGradiente();
        cmdRegister1 = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(565, 695));
        getContentPane().setLayout(null);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        txt_cari.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_cari.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cariActionPerformed(evt);
            }
        });

        search.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/find.png"))); // NOI18N
        search.setText("  SEARCH");
        search.setToolTipText("");
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        table_barang.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        table_barang.setModel(new javax.swing.table.DefaultTableModel(
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
        table_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_barangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_barang);

        refresh.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/reload.png"))); // NOI18N
        refresh.setText("  REFRESH");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });

        back.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/chevron.png"))); // NOI18N
        back.setText("  BACK");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        panelGradiente1.setColorPrimario(new java.awt.Color(204, 102, 0));
        panelGradiente1.setColorSecundario(new java.awt.Color(236, 177, 118));

        cmdRegister1.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        cmdRegister1.setForeground(new java.awt.Color(255, 255, 255));
        cmdRegister1.setText("Stok Barang");
        cmdRegister1.setContentAreaFilled(false);
        cmdRegister1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdRegister1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRegister1ActionPerformed(evt);
            }
        });
        panelGradiente1.add(cmdRegister1);
        cmdRegister1.setBounds(130, 10, 310, 110);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(panelGradiente1, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(back)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(search)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(50, 50, 50))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(panelGradiente1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_cari))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(back)
                    .addComponent(refresh))
                .addContainerGap(58, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 0, 572, 680);

        getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cariActionPerformed

    private void table_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_barangMouseClicked
        // TODO add your handling code here:
//        int row = table_barang.getSelectedRow();
//        formTransaksi menu = new formTransaksi();
//        
//        
//        String kode = table.getValueAt(row, 0).toString();
//        menu.txt_kodebarang2.setText(kode);
//        
//       
//        String kategori = table.getValueAt(row, 4).toString();
//        menu.txt_kategori.setText(kategori);
//        
//        String nama = table.getValueAt(row, 1).toString();
//        menu.txt_namabarang2.setText(nama);
//        
//       
//        String harga = table.getValueAt(row, 2).toString();
//        menu.txt_harga2.setText(harga);
//        
//        menu.setVisible(true);
//        menu.pack();
//        menu.setDefaultCloseOperation(formTransaksi.DISPOSE_ON_CLOSE);
//        dispose();


int row = table_barang.getSelectedRow();
formTransaksiadmin menu = new formTransaksiadmin();

String kode = (String) table_barang.getValueAt(row, 0);
menu.setKodeBarang2(kode);

String kategori = (String) table_barang.getValueAt(row, 4);
menu.setKategori(kategori);

String nama = (String) table_barang.getValueAt(row, 1);
menu.setNamaBarang(nama);

String harga = (String) table_barang.getValueAt(row, 2);
menu.setHarga(harga);

// Memeriksa stok sebelum menampilkan formTransaksi
int stok = Integer.parseInt((String) table_barang.getValueAt(row, 3));
if (stok > 0) {
    menu.setVisible(true);
  //  menu.pack();
   //menu.setDefaultCloseOperation(formTransaksi.DISPOSE_ON_CLOSE);
   dispose();
} else {
    JOptionPane.showMessageDialog(this, "Stok habis. Data tidak dapat diambil.");
}



//int row = table_barang.getSelectedRow();
//formTransaksi menu = new formTransaksi();
//
//String kode = table.getValueAt(row, 0).toString();
//menu.txt_kodebarang2.setText(kode);
//
//
//String kategori = table.getValueAt(row, 4).toString();
//menu.txt_kategori.setText(kategori);
//
//
//String nama = table.getValueAt(row, 1).toString();
//menu.txt_namabarang2.setText(nama);
//
//
//String harga = table.getValueAt(row, 2).toString();
//menu.txt_harga2.setText(harga);
//
//
//
//
//
//// Memeriksa stok sebelum menampilkan formTransaksi
//int stok = Integer.parseInt(table.getValueAt(row, 3).toString());
//if (stok > 0) {
//    menu.setVisible(true);
//    menu.pack();
//    menu.setDefaultCloseOperation(formTransaksi.DISPOSE_ON_CLOSE);
//    dispose();
//} else {
//    JOptionPane.showMessageDialog(this, "Stok habis. Data tidak dapat diambil.");
//}
    



        
        
        
        
    }//GEN-LAST:event_table_barangMouseClicked

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:
        cari();
    }//GEN-LAST:event_searchActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        // TODO add your handling code here:
        //        table_barang.setModel(table);
        tampilData();

        //        dispose();
        //        new stok_barangadmin().setVisible(true);
    }//GEN-LAST:event_refreshActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        // TODO add your handling code here:
//     formTransaksi n = new formTransaksi();
new formTransaksi().setVisible(true);
dispose();
  
//formTransaksi.setVisible(true);
    //    dispose();
        
        // Mengatur visibilitas frame lama menjadi false
//this.setVisible(false);

// Membuat objek frame baru
  // FormTransaksi1 formTransaksi = new FormTransaksi1();
//formTransaksi.setVisible(true);

// Menghapus frame lama dari memori jika diperlukan
//this.dispose();
    }//GEN-LAST:event_backActionPerformed

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
            java.util.logging.Logger.getLogger(stok_barangadmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(stok_barangadmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(stok_barangadmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(stok_barangadmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new stok_barangadmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JButton cmdRegister1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private swing.PanelGradiente panelGradiente1;
    private javax.swing.JButton refresh;
    private javax.swing.JButton search;
    public javax.swing.JTable table_barang;
    private javax.swing.JTextField txt_cari;
    // End of variables declaration//GEN-END:variables
}
