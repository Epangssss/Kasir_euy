/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

//import gui.FormUtama;
import java.awt.List;
import master.*;
import koneksi.koneksi;
import transaksi.formTransaksi;
import transaksi.stok_barang;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import login_new.sign_in;

public class Menu_User extends javax.swing.JFrame {

    //private javax.swing.JLabel User;
    //     private javax.swing.JLabel transaksi;
    //   private JLabel transaksi;
    public Menu_User() {
        initComponents();
        txt_username.setText(sign_in.getKasirName());
        
          this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        startTimer();

//        displayDateTime();
//        startTimer(); 
//          //txt_user.setText(userName);
        //displayUserInfo();
        //  setusername("Default User");
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

    // Metode lain dan komponen GUI lainnya di sini...
    // dd-MM-yyyy
//     public void tanggal(){
//   
//        SimpleDateFormat format = new SimpleDateFormat("EEEE dd MMMM yyyy");
//  
//        Date date = new Date();
//     
//        txt_date.setText(format.format(date));
//     
//    }
// private void displayDateTime() {
//    SimpleDateFormat format = new SimpleDateFormat("EEEE dd MMMM yyyy");
//    Date date = new Date();
//    String dateStr = format.format(date);
//    txt_date.setText(dateStr);
//}
//      private void startTimer() {
//        // Buat objek Timer untuk memperbarui waktu setiap detik
//        Timer timer = new Timer(1000, new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                updateTime();
//            }
//        });
//        // Mulai timer
//        timer.start();
//    }
//      
//       private void updateTime() {
//        // Format waktu ke dalam string
//        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//      Date now = new Date();
//       String currentTime = dateFormat.format(now);
//     //    Set teks waktu ke dalam JTextField
//       T_date.setText(currentTime);
//    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        panelGradiente2 = new swing.PanelGradiente();
        jLabel1 = new javax.swing.JLabel();
        T_date = new javax.swing.JTextField();
        cmdRegister = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        panelGradiente1 = new swing.PanelGradiente();
        cmdRegister1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_username = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_riwayat = new javax.swing.JTable();
        panelGradiente5 = new swing.PanelGradiente();
        T_data = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        panelGradiente3 = new swing.PanelGradiente();
        t_pendapatan1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(jPanel6, java.awt.BorderLayout.CENTER);

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

        panelGradiente1.setColorPrimario(new java.awt.Color(204, 102, 0));
        panelGradiente1.setColorSecundario(new java.awt.Color(236, 177, 118));

        cmdRegister1.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        cmdRegister1.setForeground(new java.awt.Color(255, 255, 255));
        cmdRegister1.setText("Menu Karyawan");
        cmdRegister1.setContentAreaFilled(false);
        cmdRegister1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdRegister1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRegister1ActionPerformed(evt);
            }
        });
        panelGradiente1.add(cmdRegister1);
        cmdRegister1.setBounds(740, 20, 270, 110);

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

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

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
        tb_riwayat.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(tb_riwayat);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(104, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1504, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        panelGradiente5.setColorPrimario(new java.awt.Color(102, 51, 0));
        panelGradiente5.setColorSecundario(new java.awt.Color(102, 51, 0));

        T_data.setEditable(false);
        T_data.setBackground(new java.awt.Color(255, 255, 255));
        T_data.setFont(new java.awt.Font("Segoe UI", 0, 25)); // NOI18N
        T_data.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        T_data.setText("0");
        T_data.setBorder(null);
        T_data.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                T_dataActionPerformed(evt);
            }
        });
        panelGradiente5.add(T_data);
        T_data.setBounds(-7, 80, 350, 110);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 2, 22)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Jumlah Data Barang");
        panelGradiente5.add(jLabel6);
        jLabel6.setBounds(70, 30, 200, 30);

        panelGradiente3.setColorPrimario(new java.awt.Color(102, 51, 0));
        panelGradiente3.setColorSecundario(new java.awt.Color(102, 51, 0));

        t_pendapatan1.setEditable(false);
        t_pendapatan1.setBackground(new java.awt.Color(255, 255, 255));
        t_pendapatan1.setFont(new java.awt.Font("Segoe UI", 0, 25)); // NOI18N
        t_pendapatan1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t_pendapatan1.setText("0");
        t_pendapatan1.setBorder(null);
        t_pendapatan1.setCaretColor(new java.awt.Color(255, 255, 255));
        t_pendapatan1.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        t_pendapatan1.setSelectionColor(new java.awt.Color(255, 255, 255));
        panelGradiente3.add(t_pendapatan1);
        t_pendapatan1.setBounds(-10, 70, 360, 120);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 2, 22)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Total Transaksi");
        panelGradiente3.add(jLabel4);
        jLabel4.setBounds(90, 20, 190, 30);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelGradiente2, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelGradiente1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1692, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(367, 367, 367)
                        .addComponent(panelGradiente3, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(325, 325, 325)
                        .addComponent(panelGradiente5, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelGradiente1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(457, 457, 457)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelGradiente5, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelGradiente3, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 92, Short.MAX_VALUE))
            .addComponent(panelGradiente2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1960, 1130));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void T_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_T_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_T_dateActionPerformed

    private void cmdRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRegisterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdRegisterActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        //  new login.menu_login().setVisible(true);

        new login_new.sign_in().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        new riwayat_transaksi().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new formTransaksi().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        new transaksi.daftar_menu().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cmdRegister1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRegister1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdRegister1ActionPerformed

    private void T_dataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_T_dataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_T_dataActionPerformed

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
            java.util.logging.Logger.getLogger(Menu_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Menu_User().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField T_data;
    private javax.swing.JTextField T_date;
    private javax.swing.JButton cmdRegister;
    private javax.swing.JButton cmdRegister1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private swing.PanelGradiente panelGradiente1;
    private swing.PanelGradiente panelGradiente2;
    private swing.PanelGradiente panelGradiente3;
    private swing.PanelGradiente panelGradiente5;
    private javax.swing.JTextField t_pendapatan1;
    private javax.swing.JTable tb_riwayat;
    private javax.swing.JLabel txt_username;
    // End of variables declaration//GEN-END:variables
}
