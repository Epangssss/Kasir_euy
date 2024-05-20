
package login_new;


//admin
import java.awt.HeadlessException;
import master.Menu_Admin;
import transaksi.*;
import master.*;
import login_new.sign_admin;
//karyawan
import user.Menu_User;
import java.awt.HeadlessException;
import koneksi.*;
import transaksi.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static koneksi.koneksi.getKoneksi;

/**
 *
 * @author edwar
 */
public class sign_in extends javax.swing.JFrame {
      public javax.swing.JTextField T_user;
   koneksi koneksi = new koneksi (); // baru
   Menu_Admin Menu_Admin = new Menu_Admin (""); //baru
   
   
    private static String kasirName;
          
Connection con;
Statement stat;
ResultSet rs;
String sql;

    /**
     * Creates new form signIn
     */
public sign_in() {
    initComponents();
    koneksi.getKoneksi();
    
    
   
}

  public static String getKasirName() {
        return kasirName;
    }
  
   public static void setKasirName(String name) {
        kasirName = name;
    }


private void loginAdmin() {
    try {
        Connection connect = koneksi.getKoneksi();
        Statement sttmnt = connect.createStatement();
        String query = "SELECT * FROM `admin` WHERE `username` = '"+txt_username.getText()+"' && `password`= '"+txt_password.getText()+"' ";

        ResultSet go = sttmnt.executeQuery(query);

        if(go.next()){
            String username = go.getString("username"); // Ambil nama pengguna dari data yang dipilih
            JOptionPane.showMessageDialog(null, "Login Success");
            
            
            sign_in.setKasirName(txt_username.getText());

            Menu_Admin Menu_Admin = new Menu_Admin(""); // Buat instance Menu_Admin
            Menu_Admin.setLocationRelativeTo(null); // Set nama pengguna
            Menu_Admin.setVisible(true); // Tampilkan Menu_Admin
            
            
//            Menu_Admin menuAdmin = new Menu_Admin(); // Buat instance Menu_Admin
//            menuAdmin.setLocationRelativeTo(null); // Set nama pengguna
//            menuAdmin.setVisible(true); // Tampilkan Menu_Admin
            this.dispose(); // Tutup frame login
        } else {
            JOptionPane.showMessageDialog(null, "Username atau Password Salah");
            txt_username.setText(null);
            txt_password.setText(null);
        }

        sttmnt.close();
        connect.close();
    } catch(SQLException e) {
        System.out.println(e);
    }
}



   public ResultSet ambilData(String sql, String... params) {
    try {
        PreparedStatement ps = koneksi.getKoneksi().prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            ps.setString(i + 1, params[i]);
        }
        return ps.executeQuery();
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}



private void loginKaryawan() {
    try {
        Connection connect = koneksi.getKoneksi();
        String query = "SELECT * FROM `tb_datakaryawan` WHERE `username` = ? AND `password`= ?";
        PreparedStatement statement = connect.prepareStatement(query);
        statement.setString(1, txt_username.getText());
        statement.setString(2, txt_password.getText());

        ResultSet resultSet = statement.executeQuery();
if (resultSet.next()) {
    JOptionPane.showMessageDialog(null, "Login Success");
    
    // Simpan nama kasir di SessionManager
   sign_in.setKasirName(txt_username.getText());
    
    // Buat dan tampilkan instance FormTransaksi
   //formTransaksi FormTransaksi = new formTransaksi();
   Menu_User menuUser = new Menu_User();
   menuUser.setLocationRelativeTo(null);
   menuUser.setVisible(true);
     //FormTransaksi.setVisible(true);
    
    this.dispose();

        
//       if (resultSet.next()) {
//    JOptionPane.showMessageDialog(null, "Login Success");
//    Menu_User menuUser = new Menu_User();
//    menuUser.setUserName(txt_username.getText());
//     menuUser.setVisible(true);
//    
    
    //    formTransaksi FormTransaksi = new formTransaksi();
    //    FormTransaksi.setKasirName (txt_username.getText());
    //   FormTransaksi.setVisible(true);
    //    
    
    this.dispose();

            
        } else {
            JOptionPane.showMessageDialog(null, "Username atau Password Salah");
            txt_username.setText(null);
            txt_password.setText(null);
        }

        statement.close();
        connect.close();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat melakukan login. Silakan coba lagi.");
        ex.printStackTrace();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        txt_password = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        sign_up = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 5, 33));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/seblak (1).png"))); // NOI18N
        jLabel5.setText("jLabel5");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 340, 420));

        jComboBox1.setFont(new java.awt.Font("SansSerif", 2, 16)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Karyawan", " " }));
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 220, 120, -1));

        txt_password.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_password.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passwordActionPerformed(evt);
            }
        });
        jPanel1.add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, 220, 40));

        jLabel1.setText("Username");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, -1, -1));

        jLabel2.setText("Password");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 120, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel3.setText("Belum punya akun?");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 270, -1, 30));

        sign_up.setBackground(new java.awt.Color(204, 204, 204));
        sign_up.setFont(new java.awt.Font("Segoe UI", 2, 15)); // NOI18N
        sign_up.setForeground(new java.awt.Color(0, 204, 0));
        sign_up.setText("SIgn Up");
        sign_up.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sign_upMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sign_upMouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                sign_upMouseReleased(evt);
            }
        });
        jPanel1.add(sign_up, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 270, -1, 30));

        txt_username.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_username.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usernameActionPerformed(evt);
            }
        });
        jPanel1.add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 70, 220, 40));

        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/password.png"))); // NOI18N
        jButton1.setText("  LOGIN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 340, 180, 50));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void sign_upMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sign_upMouseClicked
 
 sign_up.setForeground(Color.BLUE);
    new sign_admin().setVisible(true);
     JOptionPane.showMessageDialog(null, "Selamat Datang Owner Baru Silahkan registrasi Untuk Membuat Akun"); 
        dispose();
 // TODO add your handling code here:
    }//GEN-LAST:event_sign_upMouseClicked

    private void txt_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passwordActionPerformed
 jButton1.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txt_passwordActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     String selectedOption = (String) jComboBox1.getSelectedItem();
    if (selectedOption.equals("Admin")) {
        loginAdmin();
    } else if (selectedOption.equals("Karyawan")) {
        loginKaryawan();
    } else {
        JOptionPane.showMessageDialog(null, "Pilihan tidak valid");
    }


    

    }//GEN-LAST:event_jButton1ActionPerformed

    private void sign_upMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sign_upMouseEntered
 sign_up.setCursor(new Cursor(Cursor.HAND_CURSOR));        // TODO add your handling code here:
    }//GEN-LAST:event_sign_upMouseEntered

    private void sign_upMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sign_upMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_sign_upMouseReleased

    private void txt_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usernameActionPerformed

try {
    String username = txt_username.getText();
    if (username == null || username.trim().isEmpty()) {
        txt_username.requestFocus();
        return;
    }

    try (Connection conn = getKoneksi();
         PreparedStatement stmt = conn.prepareStatement("SELECT username FROM admin WHERE username = ?")) {
        stmt.setString(1, username);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                txt_username.setText("");
                int asn = JOptionPane.showConfirmDialog(null, "Apakah Anda Akan Login sebagai admin: '" + rs.getString("username") + "'?");
                if (asn == JOptionPane.YES_OPTION) {
                    Menu_Admin menu_admin = new Menu_Admin(rs.getString("username"));
                    menu_admin.setVisible(true);
                    this.dispose();
                }
            } else {
                txt_username.setText("");
            }
            txt_password.requestFocus();
        }
    }
} catch (SQLException e) {
    e.printStackTrace();
} catch (RuntimeException e) {
    e.printStackTrace();
}
//        ResultSet rs = koneksi.ambilData("select * from admin where username = '" + txt_username.getText() + "'");
//try {
//    if (rs.next()) {
//        txt_username.setText("");
//        int asn = JOptionPane.showConfirmDialog(null, "Apakah Anda Akan Login sebagai admin: '" + rs.getString("Username") + "'deangsn role: '" + rs.getString("admin") + "'?");
//        if (asn == JOptionPane.YES_OPTION) {
//           Menu_Admin Menu_Admin = new Menu_Admin(rs.getString("Username"));
//          Menu_Admin.setVisible(true);
//            this.dispose();
//        }
//    }
//    txt_username.setText("");
//} catch (Exception e) {
//    e.printStackTrace();
//}
//txt_password.requestFocus();
    }//GEN-LAST:event_txt_usernameActionPerformed

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
            java.util.logging.Logger.getLogger(sign_in.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(sign_in.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(sign_in.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(sign_in.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new sign_in().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel sign_up;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
