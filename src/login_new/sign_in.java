
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
    koneksi koneksi = new koneksi(); // baru
    Menu_Admin Menu_Admin = new Menu_Admin(""); // baru

    private static String kasirName;
    private boolean isPasswordVisible = false;

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

    private void togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible;
        if (isPasswordVisible) {
            txt_password.setEchoChar((char) 0);
        } else {
            txt_password.setEchoChar('*');
        }
    }
    
private void loginAdmin() {
    try {
        Connection connect = koneksi.getKoneksi();
        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
        PreparedStatement pstmt = connect.prepareStatement(query);
        pstmt.setString(1, txt_username.getText());
        pstmt.setString(2, txt_password.getText());

        ResultSet go = pstmt.executeQuery();

        if (go.next()) {
            String username = go.getString("username");
            String adminCode = go.getString("admin_code");
            int firstLogin = go.getInt("first_login");

            if (firstLogin == 1) {
                // Hapus data terkait admin ini dari tabel admin_data
                String deleteQuery = "DELETE FROM admin_data WHERE admin_code = ?";
                PreparedStatement deleteStmt = connect.prepareStatement(deleteQuery);
                deleteStmt.setString(1, adminCode);
                deleteStmt.executeUpdate();
                deleteStmt.close();

                // Update kolom first_login menjadi 0
                String updateQuery = "UPDATE admin SET first_login = 0 WHERE admin_code = ?";
                PreparedStatement updateStmt = connect.prepareStatement(updateQuery);
                updateStmt.setString(1, adminCode);
                updateStmt.executeUpdate();
                updateStmt.close();
            }

            JOptionPane.showMessageDialog(null, "Login Success");

            sign_in.setKasirName(username);

            // Pass the admin code to the next screen   
            Menu_Admin menuAdmin = new Menu_Admin(adminCode);
            menuAdmin.setLocationRelativeTo(null);
            menuAdmin.setVisible(true);

            // Kontrol akses berdasarkan kode unik admin
            if (adminCode.equals("ADM002")) {
                // Izinkan akses ke semua data
            } else {
                // Batasi akses hanya untuk data yang sesuai dengan kode unik admin
            }

            this.dispose(); // Tutup frame login
        } else {
            JOptionPane.showMessageDialog(null, "Username atau Password Salah");
            txt_username.setText(null);
            txt_password.setText(null);
        }

        pstmt.close();
        connect.close();
    } catch (SQLException e) {
        System.out.println(e);
    }
}



//private void loginAdmin() {
//    try {
//        Connection connect = koneksi.getKoneksi();
//        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
//        PreparedStatement pstmt = connect.prepareStatement(query);
//        pstmt.setString(1, txt_username.getText());
//        pstmt.setString(2, txt_password.getText());
//
//        ResultSet go = pstmt.executeQuery();
//
//        if (go.next()) {
//            String username = go.getString("username");
//            String adminCode = go.getString("admin_code");
//
//            JOptionPane.showMessageDialog(null, "Login Success");
//
//            sign_in.setKasirName(username);
//
//            // Pass the admin code to the next screen
//            Menu_Admin menuAdmin = new Menu_Admin(adminCode);
//            menuAdmin.setLocationRelativeTo(null);
//            menuAdmin.setVisible(true);
//
//            this.dispose(); // Tutup frame login
//        } else {
//            JOptionPane.showMessageDialog(null, "Username atau Password Salah");
//            txt_username.setText(null);
//            txt_password.setText(null);
//        }
//
//        pstmt.close();
//        connect.close();
//    } catch (SQLException e) {
//        System.out.println(e);
//    }
//}

//    private void loginAdmin() {
//        try {
//            Connection connect = koneksi.getKoneksi();
//            Statement sttmnt = connect.createStatement();
//            String query = "SELECT * FROM `admin` WHERE `username` = '" + txt_username.getText() + "' && `password`= '"
//                    + txt_password.getText() + "' ";
//
//            ResultSet go = sttmnt.executeQuery(query);
//
//            if (go.next()) {
//                String username = go.getString("username"); // Ambil nama pengguna dari data yang dipilih
//                JOptionPane.showMessageDialog(null, "Login Success");
//
//                sign_in.setKasirName(txt_username.getText());
//
//                Menu_Admin Menu_Admin = new Menu_Admin(""); // Buat instance Menu_Admin
//                Menu_Admin.setLocationRelativeTo(null); // Set nama pengguna
//                Menu_Admin.setVisible(true); // Tampilkan Menu_Admin
//
//                // Menu_Admin menuAdmin = new Menu_Admin(); // Buat instance Menu_Admin
//                // menuAdmin.setLocationRelativeTo(null); // Set nama pengguna
//                // menuAdmin.setVisible(true); // Tampilkan Menu_Admin
//                this.dispose(); // Tutup frame login
//            } else {
//                JOptionPane.showMessageDialog(null, "Username atau Password Salah");
//                txt_username.setText(null);
//                txt_password.setText(null);
//            }
//
//            sttmnt.close();
//            connect.close();
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//    }

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
                // formTransaksi FormTransaksi = new formTransaksi();
                Menu_User menuUser = new Menu_User();
                menuUser.setLocationRelativeTo(null);
                menuUser.setVisible(true);
                // FormTransaksi.setVisible(true);

                this.dispose();

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
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelGradiente1 = new swing.PanelGradiente();
        jComboBox1 = new javax.swing.JComboBox<>();
        txt_password = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        sign_up = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        t_box = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelGradiente1.setColorPrimario(new java.awt.Color(236, 177, 118));
        panelGradiente1.setColorSecundario(new java.awt.Color(204, 102, 0));

        jComboBox1.setFont(new java.awt.Font("SansSerif", 2, 16)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Karyawan", " " }));
        panelGradiente1.add(jComboBox1);
        jComboBox1.setBounds(370, 360, 120, 29);

        txt_password.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_password.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passwordActionPerformed(evt);
            }
        });
        panelGradiente1.add(txt_password);
        txt_password.setBounds(240, 270, 250, 40);

        jLabel1.setText("Username");
        panelGradiente1.add(jLabel1);
        jLabel1.setBounds(270, 140, 66, 21);

        jLabel2.setText("Password");
        panelGradiente1.add(jLabel2);
        jLabel2.setBounds(270, 231, 62, 30);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        jLabel3.setText("Belum punya akun?");
        panelGradiente1.add(jLabel3);
        jLabel3.setBounds(250, 520, 150, 30);

        sign_up.setBackground(new java.awt.Color(204, 204, 204));
        sign_up.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
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
        panelGradiente1.add(sign_up);
        sign_up.setBounds(400, 520, 70, 30);

        txt_username.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_username.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usernameActionPerformed(evt);
            }
        });
        panelGradiente1.add(txt_username);
        txt_username.setBounds(240, 170, 250, 40);

        jButton1.setBackground(new java.awt.Color(51, 204, 0));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/password.png"))); // NOI18N
        jButton1.setText("  LOGIN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panelGradiente1.add(jButton1);
        jButton1.setBounds(270, 440, 180, 50);

        t_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_boxActionPerformed(evt);
            }
        });
        panelGradiente1.add(t_box);
        t_box.setBounds(360, 320, 25, 25);

        jLabel5.setText("Show Password");
        panelGradiente1.add(jLabel5);
        jLabel5.setBounds(390, 320, 120, 21);

        jLabel6.setFont(new java.awt.Font("Yu Gothic", 3, 24)); // NOI18N
        jLabel6.setText("Wellcome Admin Kasir Kuy !");
        panelGradiente1.add(jLabel6);
        jLabel6.setBounds(210, 50, 350, 40);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-username-24.png"))); // NOI18N
        panelGradiente1.add(jLabel7);
        jLabel7.setBounds(240, 140, 30, 21);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-password-24.png"))); // NOI18N
        panelGradiente1.add(jLabel8);
        jLabel8.setBounds(240, 236, 24, 24);

        jPanel1.add(panelGradiente1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-60, 0, 660, 600));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo_kasi_kuygg.png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 120, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1057, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void t_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_boxActionPerformed
if (t_box.isSelected()){
    txt_password.setEchoChar((char)0);
}else{
    txt_password.setEchoChar('*');
}  
        
// TODO add your handling code here:
    }//GEN-LAST:event_t_boxActionPerformed

    private void sign_upMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_sign_upMouseClicked

        sign_up.setForeground(Color.BLUE);
        new sign_admin().setVisible(true);
        JOptionPane.showMessageDialog(null, "Selamat Datang Owner Baru Silahkan registrasi Untuk Membuat Akun");
        dispose();
        // TODO add your handling code here:
    }// GEN-LAST:event_sign_upMouseClicked

    private void txt_passwordActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txt_passwordActionPerformed
        jButton1.requestFocus(); // TODO add your handling code here:
    }// GEN-LAST:event_txt_passwordActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        String selectedOption = (String) jComboBox1.getSelectedItem();
        if (selectedOption.equals("Admin")) {
            loginAdmin();
        } else if (selectedOption.equals("Karyawan")) {
            loginKaryawan();
        } else {
            JOptionPane.showMessageDialog(null, "Pilihan tidak valid");
        }

    }// GEN-LAST:event_jButton1ActionPerformed

    private void sign_upMouseEntered(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_sign_upMouseEntered
        sign_up.setCursor(new Cursor(Cursor.HAND_CURSOR)); // TODO add your handling code here:
    }// GEN-LAST:event_sign_upMouseEntered

    private void sign_upMouseReleased(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_sign_upMouseReleased
        // TODO add your handling code here:
    }// GEN-LAST:event_sign_upMouseReleased

    private void txt_usernameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txt_usernameActionPerformed

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
                        int asn = JOptionPane.showConfirmDialog(null,
                                "Apakah Anda Akan Login sebagai admin: '" + rs.getString("username") + "'?");
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
        // try {
        // String username = txt_username.getText();
        // if (username == null || username.trim().isEmpty()) {
        // txt_username.requestFocus();
        // return;
        // }
        //
        // try (Connection conn = getKoneksi();
        // PreparedStatement stmt = conn.prepareStatement("SELECT username FROM admin
        // WHERE username = ?")) {
        // stmt.setString(1, username);
        // try (ResultSet rs = stmt.executeQuery()) {
        // if (rs.next()) {
        // txt_username.setText("");
        // int asn = JOptionPane.showConfirmDialog(null, "Apakah Anda Akan Login sebagai
        // admin: '" + rs.getString("username") + "'?");
        // if (asn == JOptionPane.YES_OPTION) {
        // Menu_Admin menu_admin = new Menu_Admin(rs.getString("username"));
        // menu_admin.setVisible(true);
        // this.dispose();
        // }
        // } else {
        // txt_username.setText("");
        // }
        // txt_password.requestFocus();
        // }
        // }
        // } catch (SQLException e) {
        // e.printStackTrace();
        // } catch (RuntimeException e) {
        // e.printStackTrace();
        // }
        // ResultSet rs = koneksi.ambilData("select * from admin where username = '" +
        // txt_username.getText() + "'");
        // try {
        // if (rs.next()) {
        // txt_username.setText("");
        // int asn = JOptionPane.showConfirmDialog(null, "Apakah Anda Akan Login sebagai
        // admin: '" + rs.getString("Username") + "'deangsn role: '" +
        // rs.getString("admin") + "'?");
        // if (asn == JOptionPane.YES_OPTION) {
        // Menu_Admin Menu_Admin = new Menu_Admin(rs.getString("Username"));
        // Menu_Admin.setVisible(true);
        // this.dispose();
        // }
        // }
        // txt_username.setText("");
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // txt_password.requestFocus();
    }// GEN-LAST:event_txt_usernameActionPerformed

    private void eyeMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_eyeMouseClicked
        togglePasswordVisibility();
    }// GEN-LAST:event_eyeMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
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
        // </editor-fold>
        // </editor-fold>

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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private swing.PanelGradiente panelGradiente1;
    private javax.swing.JLabel sign_up;
    private javax.swing.JCheckBox t_box;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
