/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Report_new;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;

//new
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;


import javax.swing.JDialog;
import java.awt.Dialog;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import login_new.sign_in;



public class riwayat extends javax.swing.JFrame {
    DefaultTableModel table = new DefaultTableModel();
    
  
    //private DefaultTableModel table = new DefaultTableModel();
   
    /**
     * Creates new form riwayat
     */
    public riwayat() {
        initComponents();
        
          T_user.setText(sign_in.getKasirName());
        
        koneksi con = new koneksi();
        koneksi.getKoneksi();
       //   setModalityType(ModalityType.APPLICATION_MODAL);
        tb_riwayat.setModel(table);
        table.addColumn("Tanggal Transaksi");
         table.addColumn("No_Transaksi");
      //  table.addColumn("No");
        table.addColumn("Kode Barang");
        table.addColumn("Nama Barang");
        table.addColumn("Harga");
        table.addColumn("Jumlah");
        table.addColumn("Total Harga");
            // Add the table to the panel
            
            

        // Set alignment for each column
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);

    DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
    rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

    TableColumnModel columnModel = tb_riwayat.getColumnModel();
    columnModel.getColumn(0).setCellRenderer(centerRenderer); // Tanggal Transaksi
    columnModel.getColumn(1).setCellRenderer(centerRenderer); // No_Transaksi
    columnModel.getColumn(2).setCellRenderer(centerRenderer); // Kode Barang
    columnModel.getColumn(3).setCellRenderer(centerRenderer); // Nama Barang
    columnModel.getColumn(4).setCellRenderer(rightRenderer); // Harga
    columnModel.getColumn(5).setCellRenderer(centerRenderer); // Jumlah
    columnModel.getColumn(6).setCellRenderer(rightRenderer); // Total Harga

    // Set table alignment
 
        
              


        
        tampilData();
        
    }

 private void tampilData() {
    // Untuk menghapus baris setelah input
    int row = tb_riwayat.getRowCount();
    for (int a = 0; a < row; a++) {
        table.removeRow(0);
    }

    String query = "SELECT * FROM `transaksi` ";

    try {
        Connection connect = koneksi.getKoneksi(); // memanggil koneksi
        Statement sttmnt = connect.createStatement(); // membuat statement
        ResultSet rslt = sttmnt.executeQuery(query); // menjalankan query

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        while (rslt.next()) {
            // Menampung data sementara
            String tanggal = rslt.getString("tgl_transaksi");
            String kode = rslt.getString("kode_barang");
            String nama = rslt.getString("nama_barang");
            String harga = rslt.getString("harga");
            String jumlah = rslt.getString("jumlah_barang");
            String total = rslt.getString("total_harga");
            String nomor_transaksi = rslt.getString("nomor_transaksi");

            // Format harga dan total harga ke dalam format Rupiah
            String formattedHarga = currencyFormatter.format(Double.parseDouble(harga));
            String formattedTotal = currencyFormatter.format(Double.parseDouble(total));

            // Masukkan semua data ke dalam array
            String[] data = {tanggal, nomor_transaksi, kode, nama, formattedHarga, jumlah, formattedTotal};
            // Menambahkan baris sesuai dengan data yang tersimpan di array
            table.addRow(data);
        }
        // Mengatur nilai yang ditampung agar muncul di tabel
        tb_riwayat.setModel(table);

    } catch (Exception e) {
        System.out.println(e);
    }

    }
 
 private void cari(){
        int row = tb_riwayat.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table.removeRow(0);
        }
        
        String cari = txt_search.getText();
        
        String query = "SELECT * FROM `transaksi` WHERE "
                + "`kode_barang`  LIKE '%"+cari+"%' OR "
                + "`tgl_transaksi` LIKE '%"+cari+"%' OR"
                + "`nomor` LIKE '%"+cari+"%' OR"
                + "`nama_barang` LIKE '%"+cari+"%' ";
                
       try{
           Connection connect = koneksi.getKoneksi();//memanggil koneksi
           Statement sttmnt = connect.createStatement();//membuat statement
           ResultSet rslt = sttmnt.executeQuery(query);//menjalanakn query
           
           while (rslt.next()){
                //menampung data sementara
                   
                    String tanggal = rslt.getString("tgl_transaksi");
                    String id = rslt.getString("nomor");
                    String kode = rslt.getString("kode_barang");
                    String nama = rslt.getString("nama_barang");
                    String harga = rslt.getString("harga");
                    String jumlah = rslt.getString("jumlah_barang");
                    String total = rslt.getString("total_harga");
                    
                //masukan semua data kedalam array
                String[] data = {tanggal,id,kode,nama,harga,jumlah,total};
                //menambahakan baris sesuai dengan data yang tersimpan diarray
                table.addRow(data);
            }
                //mengeset nilai yang ditampung agar muncul di table
                tb_riwayat.setModel(table);
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_riwayat = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        txt_search = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        panelGradiente1 = new swing.PanelGradiente();
        jLabel2 = new javax.swing.JLabel();
        T_user = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmdRegister1 = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tb_riwayat.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tb_riwayat.setModel(new javax.swing.table.DefaultTableModel(
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
        tb_riwayat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tb_riwayatMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tb_riwayat);

        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/find.png"))); // NOI18N
        jButton1.setText("  SEARCH");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txt_search.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_search.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jButton2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/chevron.png"))); // NOI18N
        jButton2.setText("  BACK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/reload.png"))); // NOI18N
        jButton5.setText("  REFRESH");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/print.png"))); // NOI18N
        jButton3.setText("  PRINT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

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
        cmdRegister1.setText("Riwayat Transaksi");
        cmdRegister1.setContentAreaFilled(false);
        cmdRegister1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdRegister1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRegister1ActionPerformed(evt);
            }
        });
        panelGradiente1.add(cmdRegister1);
        cmdRegister1.setBounds(460, 30, 350, 110);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelGradiente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 137, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 808, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jButton3)
                            .addGap(45, 45, 45)
                            .addComponent(jButton5))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 974, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 138, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelGradiente1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        new master.Menu_Admin("").setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        //        table_barang.setModel(table);
        tampilData();

        //        dispose();
        //        new stok_barang().setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        cari();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try{
            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/Report_New/transaksi.jasper"),null,koneksi.getKoneksi());
            JasperViewer.viewReport(print, false);
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tb_riwayatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tb_riwayatMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tb_riwayatMouseClicked

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
            java.util.logging.Logger.getLogger(riwayat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(riwayat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(riwayat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(riwayat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new riwayat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel T_user;
    private javax.swing.JButton cmdRegister1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private swing.PanelGradiente panelGradiente1;
    private javax.swing.JTable tb_riwayat;
    private javax.swing.JTextField txt_search;
    // End of variables declaration//GEN-END:variables

}
