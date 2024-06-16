/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package login_new;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import koneksi.koneksi;
/**
 *
 * @author edwar
 */
public class sign_admin extends javax.swing.JFrame {

    /**
     * Creates new form sign_admin
     */
    public sign_admin() {
        initComponents();
    }

    Connection con;
Statement stat;
ResultSet rs;
String sql;


private String generateAdminCode() {
    String lastAdminCode = getLastAdminCode();
    if (lastAdminCode == null) {
        return "ADM001";
    }
    
    int number = Integer.parseInt(lastAdminCode.substring(3)) + 1;
    return String.format("ADM%03d", number);
}

private String getLastAdminCode() {
    String lastAdminCode = null;
    try {
        Connection connect = koneksi.getKoneksi();
        String query = "SELECT admin_code FROM admin ORDER BY admin_code DESC LIMIT 1";
        PreparedStatement statement = connect.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            lastAdminCode = resultSet.getString("admin_code");
        }
        
        resultSet.close();
        statement.close();
        connect.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return lastAdminCode;
}

//private String generateAdminCode() {
//    // Generate a unique admin code. This is just an example.
//    // You might want to implement a more sophisticated code generation logic.
//    return "ADM" + System.currentTimeMillis();
//}

private boolean saveAdminAccount(String username, String password, String email, String no_telp, String alamat, String adminCode) {
    try {
        Connection connect = koneksi.getKoneksi();

        String query = "INSERT INTO admin (username, password, email, no_telp, alamat, admin_code) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connect.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        statement.setString(3, email);
        statement.setString(4, no_telp);
        statement.setString(5, alamat);
        statement.setString(6, adminCode); // Set admin code

        int rowsInserted = statement.executeUpdate();

        statement.close();
        connect.close();

        return rowsInserted > 0;
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    }
}


private boolean saveAdminData(String adminCode, String data, String username) {
    try {
        Connection connect = koneksi.getKoneksi();

        String query = "INSERT INTO admin_data (admin_code, data, username) VALUES (?, ?, ?)";
        PreparedStatement statement = connect.prepareStatement(query);
        statement.setString(1, adminCode); // Set admin code
        statement.setString(2, data);
        statement.setString(3, username);

        int rowsInserted = statement.executeUpdate();

        statement.close();
        connect.close();

        return rowsInserted > 0;
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    }
}





//private boolean saveAdminAccount(String username, String password, String email, String no_telp, String alamat) {
//    try {
//        Connection connect = koneksi.getKoneksi();
//        String query = "INSERT INTO `admin` (`username`, `password`, `email`, `no_telp`, `alamat`) VALUES (?, ?, ?, ?, ?)";
//        PreparedStatement statement = connect.prepareStatement(query);
//        statement.setString(1, username);
//        statement.setString(2, password);
//        statement.setString(3, email);
//        statement.setString(4, no_telp);
//        statement.setString(5, alamat);
//
//        int rowsInserted = statement.executeUpdate();
//
//        statement.close();
//        connect.close();
//
//        return rowsInserted > 0;
//    } catch (SQLException ex) {
//        ex.printStackTrace();
//        return false;
//    }
//}

private void clearSignUpForm() {
    txt_username.setText("");
    txt_password.setText("");
    txt_email.setText("");
    txt_phoneNumber.setText("");
    txt_address.setText("");
}
    
//    private boolean saveAdminAccount(String username, String password) {
//    try {
//        Connection connect = koneksi.getKoneksi();
//        String query = "INSERT INTO `admin` (`username`, `password`) VALUES (?, ?)";
//        PreparedStatement statement = connect.prepareStatement(query);
//        statement.setString(1, username);
//        statement.setString(2, password);
//
//        int rowsInserted = statement.executeUpdate();
//
//        statement.close();
//        connect.close();
//
//        return rowsInserted > 0;
//    } catch (SQLException ex) {
//        ex.printStackTrace();
//        return false;
//    }
//}
//    
//    private void clearSignUpForm() {
//    txt_username.setText("");
//    txt_password.setText("");
//}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        panelGradiente1 = new swing.PanelGradiente();
        jLabel1 = new javax.swing.JLabel();
        panelGradiente2 = new swing.PanelGradiente();
        jLabel6 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_password = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_phoneNumber = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_address = new javax.swing.JTextField();
        btn_signup = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        t_box = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelGradiente1.setColorPrimario(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        jLabel1.setText("Silahkan Registrasi Akun Anda ");
        panelGradiente1.add(jLabel1);
        jLabel1.setBounds(340, 30, 310, 50);

        panelGradiente2.setColorPrimario(new java.awt.Color(204, 102, 0));
        panelGradiente2.setColorSecundario(new java.awt.Color(236, 177, 118));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("USERNAME");
        panelGradiente2.add(jLabel6);
        jLabel6.setBounds(140, 60, 122, 32);

        txt_username.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_username.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        panelGradiente2.add(txt_username);
        txt_username.setBounds(140, 100, 250, 40);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("PASSWORD");
        panelGradiente2.add(jLabel7);
        jLabel7.setBounds(140, 160, 125, 32);

        txt_password.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_password.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passwordActionPerformed(evt);
            }
        });
        panelGradiente2.add(txt_password);
        txt_password.setBounds(140, 200, 250, 40);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("EMAIL");
        panelGradiente2.add(jLabel8);
        jLabel8.setBounds(570, 60, 66, 32);

        txt_email.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_email.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        panelGradiente2.add(txt_email);
        txt_email.setBounds(570, 100, 290, 40);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("NO TELP");
        panelGradiente2.add(jLabel9);
        jLabel9.setBounds(570, 160, 92, 32);

        txt_phoneNumber.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_phoneNumber.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        panelGradiente2.add(txt_phoneNumber);
        txt_phoneNumber.setBounds(570, 200, 290, 40);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("ALAMAT");
        panelGradiente2.add(jLabel10);
        jLabel10.setBounds(140, 260, 91, 32);

        txt_address.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_address.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        panelGradiente2.add(txt_address);
        txt_address.setBounds(140, 310, 260, 60);

        btn_signup.setFont(new java.awt.Font("Rockwell", 1, 24)); // NOI18N
        btn_signup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-sign-up-24.png"))); // NOI18N
        btn_signup.setText("SIGN UP");
        btn_signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_signupActionPerformed(evt);
            }
        });
        panelGradiente2.add(btn_signup);
        btn_signup.setBounds(570, 300, 200, 50);

        jButton3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-go-back-24.png"))); // NOI18N
        jButton3.setText("  BACK");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        panelGradiente2.add(jButton3);
        jButton3.setBounds(110, 430, 114, 32);

        t_box.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_boxActionPerformed(evt);
            }
        });
        panelGradiente2.add(t_box);
        t_box.setBounds(250, 240, 25, 25);

        jLabel5.setText("Show Password");
        panelGradiente2.add(jLabel5);
        jLabel5.setBounds(280, 240, 110, 21);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelGradiente2, javax.swing.GroupLayout.PREFERRED_SIZE, 1007, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(panelGradiente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelGradiente1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelGradiente2, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_passwordActionPerformed

    private void btn_signupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_signupActionPerformed
 String username = txt_username.getText();
    String password = String.valueOf(txt_password.getPassword());
    String email = txt_email.getText();
    String no_telp = txt_phoneNumber.getText();
    String alamat = txt_address.getText();
    String adminCode = generateAdminCode(); // Generate the admin code here
  
    // Melakukan validasi input
    if (username.isEmpty() || password.isEmpty() || email.isEmpty() || no_telp.isEmpty() || alamat.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Harap lengkapi semua kolom");
        return;
    }

    // Menyimpan informasi akun admin ke database atau penyimpanan yang sesuai
    if (saveAdminAccount(username, password, email, no_telp, alamat, adminCode)) {
        // Memanggil metode saveAdminData untuk menyimpan data admin spesifik
        if (saveAdminData(adminCode, "initial data", username)) { // Pass initial data and username
            JOptionPane.showMessageDialog(null, "Akun admin berhasil dibuat");
            dispose();
            new sign_in().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data admin. Silakan coba lagi.");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Gagal membuat akun admin. Silakan coba lagi.");
    }


//  String username = txt_username.getText();
//    String password = String.valueOf(txt_password.getPassword());
//    String email = txt_email.getText();
//    String no_telp = txt_phoneNumber.getText();
//    String alamat = txt_address.getText();
//
//    // Melakukan validasi input
//    if (username.isEmpty() || password.isEmpty() || email.isEmpty() || no_telp.isEmpty() || alamat.isEmpty()) {
//        JOptionPane.showMessageDialog(null, "Harap lengkapi semua kolom");
//        return;
//    }
//
//    // Menyimpan informasi akun admin ke database atau penyimpanan yang sesuai
//    if (saveAdminAccount(username, password, email, no_telp, alamat)) {
//        JOptionPane.showMessageDialog(null, "Akun admin berhasil dibuat");
//
//        dispose();
//        new sign_in().setVisible(true);
//    } else {
//        JOptionPane.showMessageDialog(null, "Gagal membuat akun admin. Silakan coba lagi.");
//    }




//        String username = txt_username.getText();
//        String password = String.valueOf(txt_password.getPassword());
//        String email = txt_email.getText();
//        String no_telp = txt_phoneNumber.getText();
//        String alamat = txt_address.getText();
//
//        // Melakukan validasi input
//        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || no_telp.isEmpty() || alamat.isEmpty()) {
//            JOptionPane.showMessageDialog(null, "Harap lengkapi semua kolom");
//            return;
//        }
//
//        // Menyimpan informasi akun admin ke database atau penyimpanan yang sesuai
//        if (saveAdminAccount(username, password, email, no_telp, alamat)) {
//            JOptionPane.showMessageDialog(null, "Akun admin berhasil dibuat");
//
//            dispose();
//            new sign_in().setVisible(true);
//            // clearSignUpForm(); // Mengosongkan formulir sign up
//
//        } else {
//            JOptionPane.showMessageDialog(null, "Gagal membuat akun admin. Silakan coba lagi.");
//        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_signupActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        new sign_in().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void t_boxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_boxActionPerformed
        if (t_box.isSelected()){
            txt_password.setEchoChar((char)0);
        }else{
            txt_password.setEchoChar('*');
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_t_boxActionPerformed

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
            java.util.logging.Logger.getLogger(sign_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(sign_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(sign_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(sign_admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new sign_admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_signup;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private swing.PanelGradiente panelGradiente1;
    private swing.PanelGradiente panelGradiente2;
    private javax.swing.JCheckBox t_box;
    private javax.swing.JTextField txt_address;
    private javax.swing.JTextField txt_email;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_phoneNumber;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
