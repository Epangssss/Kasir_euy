/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package master;
import master.*;
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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import login_new.sign_in;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;


public class akun_karyawan extends javax.swing.JFrame {
    DefaultTableModel table = new DefaultTableModel();
  
     // private String getusername;
      
      


  

    
    public akun_karyawan() {
        initComponents();
         this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        Date now = new Date();  
        tgl_daftar.setDate(now); 
           T_user.setText(sign_in.getKasirName());
        
        koneksi conn = new koneksi();
        koneksi.getKoneksi();
        
        table_user.setModel(table);
        table.addColumn("ID");
        table.addColumn("Nama Karyawan");
        table.addColumn("Email");
        table.addColumn("Alamat");
        table.addColumn("Username");
        table.addColumn("Password");
        table.addColumn("Tanggal Pendaftaran");
        
        tanggal();
        tampilData();
        
    }
    
    
    private void tampilData(){
        //untuk mengahapus baris setelah input
        int row = table_user.getRowCount();
        for(int a = 0 ; a < row ; a++){
            table.removeRow(0);
        }
        
        String query = "SELECT * FROM `tb_datakaryawan` ";
        
        try{
            Connection connect = koneksi.getKoneksi();//memanggil koneksi
            Statement sttmnt = connect.createStatement();//membuat statement
            ResultSet rslt = sttmnt.executeQuery(query);//menjalanakn query
            
            while (rslt.next()){
                //menampung data sementara
                   
                    String id= rslt.getString("id_karyawan");
                    String nama = rslt.getString("nama_karyawan");
                    String email = rslt.getString("email");
                    String alamat = rslt.getString("alamat");
                    String username = rslt.getString("username");
                    String password = rslt.getString("password");
                    String tanggal = rslt.getString("tanggal_pendaftaran");
                    
                //masukan semua data kedalam array
                String[] data = {id,nama,email,alamat,username,password,tanggal};
                //menambahakan baris sesuai dengan data yang tersimpan diarray
                table.addRow(data);
            }
                //mengeset nilai yang ditampung agar muncul di table
                table_user.setModel(table);
            
        }catch(Exception e){
            System.out.println(e);
        }
       
    }
    private void clear(){
//        txt_kodebarang.setText(null);
        txt_nama.setText(null);
        txt_email.setText(null);
        txt_alamat.setText(null);
        txt_username.setText(null);
        txt_password.setText(null);
        tgl_daftar.setDate(null);
        
    }
    
    private void tambahData() {
    String nama = txt_nama.getText();
    String email = txt_email.getText();
    String alamat = txt_alamat.getText();
    String username = txt_username.getText();
    String password = txt_password.getText();

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal = date.format(tgl_daftar.getDate());

    // panggil koneksi
    Connection connect = koneksi.getKoneksi();

    // Query untuk mendapatkan ID terbesar saat ini
    String getMaxIdQuery = "SELECT MAX(id_karyawan) AS max_id FROM tb_datakaryawan";

    try {
        Statement sttmnt = connect.createStatement();
        ResultSet rslt = sttmnt.executeQuery(getMaxIdQuery);

        int nextId = 1; // Default ID pertama jika tabel kosong
        if (rslt.next()) {
            int maxId = rslt.getInt("max_id");
            nextId = maxId + 1;
        }

        // Query untuk memasukkan data, dengan ID yang sudah ditentukan
        String query = "INSERT INTO `tb_datakaryawan` (`id_karyawan`, `nama_karyawan`, `email`, `alamat`, `username`, `password`, `tanggal_pendaftaran`) "
                + "VALUES (" + nextId + ", '" + nama + "', '" + email + "', '" + alamat + "', '" + username + "', '" + password + "', '" + tanggal + "')";

        // Menyiapkan statement untuk dieksekusi
        PreparedStatement ps = connect.prepareStatement(query);
        ps.executeUpdate(query);
        JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");

    } catch (SQLException | HeadlessException e) {
        System.out.println(e);
        JOptionPane.showMessageDialog(null, "Data Gagal Disimpan");

    } finally {
        tampilData();
        clear();
        tanggal();
    }
}
    
    
    private void hapusData() {
    // ambil data no pendaftaran
    int i = table_user.getSelectedRow();

    if (i == -1) {
        JOptionPane.showMessageDialog(null, "Pilih data yang ingin dihapus");
        return;
    }

    String id = table.getValueAt(i, 0).toString();

    Connection connect = koneksi.getKoneksi();

    String query = "DELETE FROM `tb_datakaryawan` WHERE `id_karyawan` = " + id;
    try {
        PreparedStatement ps = connect.prepareStatement(query);
        ps.execute();
        JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");

        // Update ID setelah penghapusan
        updateIDs();

    } catch (SQLException | HeadlessException e) {
        System.out.println(e);
        JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
    } finally {
        tampilData();
        clear();
        tanggal();
    }
}

private void updateIDs() {
    Connection connect = koneksi.getKoneksi();
    String selectQuery = "SELECT * FROM `tb_datakaryawan` ORDER BY `id_karyawan`";
    String updateQuery = "UPDATE `tb_datakaryawan` SET `id_karyawan` = ? WHERE `id_karyawan` = ?";

    try {
        Statement selectStmt = connect.createStatement();
        ResultSet rs = selectStmt.executeQuery(selectQuery);

        int newId = 1;
        while (rs.next()) {
            int oldId = rs.getInt("id_karyawan");

            if (newId != oldId) {
                PreparedStatement updateStmt = connect.prepareStatement(updateQuery);
                updateStmt.setInt(1, newId);
                updateStmt.setInt(2, oldId);
                updateStmt.executeUpdate();
            }
            newId++;
        }

    } catch (SQLException e) {
        System.out.println(e);
    }
}


//    private void tambahData(){
//   //  String kode = txt_kodebarang.getText();
//        String nama = txt_nama.getText();
//        String email = txt_email.getText();
//        String alamat = txt_alamat.getText();
//        String username = txt_username.getText();
//        String password = txt_password.getText();
//        
//        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
//        String tanggal = date.format(tgl_daftar.getDate());
//        
//        //panggil koneksi
//        Connection connect = koneksi.getKoneksi();
//        //query untuk memasukan data
//        String query = "INSERT INTO `tb_datakaryawan` (`id_karyawan`, `nama_karyawan`, `email`,`alamat`, `username`, `password`, `tanggal_pendaftaran`) "
//                     + "VALUES (NULL, '"+nama+"', '"+email+"','"+alamat+"', '"+username+"', '"+password+"','"+tanggal+"')";
//        
//        try{
//            //menyiapkan statement untuk di eksekusi
//            PreparedStatement ps = (PreparedStatement) connect.prepareStatement(query);
//            ps.executeUpdate(query);
//            JOptionPane.showMessageDialog(null,"Data Berhasil Disimpan");
//            
//        }catch(SQLException | HeadlessException e){
//            System.out.println(e);
//            JOptionPane.showMessageDialog(null,"Data Gagal Disimpan");
//            
//        }finally{
//            tampilData();
//            clear();
//            tanggal();
//            
//        }
//        
//    }
//
//    private void hapusData(){
//        //ambill data no pendaftaran
//        int i = table_user.getSelectedRow();
//        
//        String id = table.getValueAt(i, 0).toString();
//        
//        Connection connect = koneksi.getKoneksi();
//        
//        String query = "DELETE FROM `tb_datakaryawan` WHERE `tb_datakaryawan`.`id_karyawan` = "+id+" ";
//        try{
//            PreparedStatement ps = (PreparedStatement) connect.prepareStatement(query);
//            ps.execute();
//            JOptionPane.showMessageDialog(null , "Data Berhasil Dihapus");
//        }catch(SQLException | HeadlessException e){
//            System.out.println(e);
//            JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
//        }finally{
//            tampilData();
//            clear();
//        }
//        
//    }
        public void tanggal(){
        Date now = new Date();  
        tgl_daftar.setDate(now);    
    }
//    
    private void editData(){
        int i = table_user.getSelectedRow();
        
        String id = table.getValueAt(i, 0).toString();
        String nama = txt_nama.getText();
        String email = txt_email.getText();
        String alamat = txt_alamat.getText();
        String username = txt_username.getText();
        String password = txt_password.getText();
        
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        String tanggal = String.valueOf(date.format(tgl_daftar.getDate()));
        
        
        Connection connect = koneksi.getKoneksi();
        
        String query = "UPDATE `tb_datakaryawan` SET `nama_karyawan` = '"+nama+"', `email` = '"+email+"', `alamat` = '"+alamat+"', `tanggal_pendaftaran` = '"+tanggal+"', "
                + "`username` = '"+username+"', `password` = '"+password+"' "
                + "WHERE `tb_datakaryawan`.`id_karyawan` = '"+id+"';";

        try{
            PreparedStatement ps = (PreparedStatement) connect.prepareStatement(query);
            ps.executeUpdate(query);
            JOptionPane.showMessageDialog(null , "Data Update");
        }catch(SQLException | HeadlessException e){
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Gagal Update");
        }finally{
            tampilData();
            clear();
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
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        txt_nama = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_password = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_user = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        txt_alamat = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        tgl_daftar = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        panelGradiente2 = new swing.PanelGradiente();
        jLabel3 = new javax.swing.JLabel();
        cmdRegister = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        panelGradiente1 = new swing.PanelGradiente();
        jLabel2 = new javax.swing.JLabel();
        T_user = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmdRegister1 = new javax.swing.JButton();

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
        setMinimumSize(new java.awt.Dimension(950, 650));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMinimumSize(new java.awt.Dimension(1000, 611));
        jPanel2.setPreferredSize(new java.awt.Dimension(1000, 611));

        jButton5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-edit-24.png"))); // NOI18N
        jButton5.setText("  EDIT");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-delete-24.png"))); // NOI18N
        jButton4.setText("  DELETE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setText("Nama");

        txt_nama.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_nama.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel11.setText("Email");

        txt_email.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_email.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txt_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emailActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel12.setText("Username");

        txt_username.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txt_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usernameActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setText("Password");

        txt_password.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_password.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        txt_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passwordActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-add-24.png"))); // NOI18N
        jButton1.setText("  ADD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-broom-24.png"))); // NOI18N
        jButton7.setText("  CLEAR");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        table_user.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        table_user.setModel(new javax.swing.table.DefaultTableModel(
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
        table_user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_userMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(table_user);

        txt_alamat.setColumns(20);
        txt_alamat.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txt_alamat.setRows(5);
        txt_alamat.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jScrollPane4.setViewportView(txt_alamat);

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel13.setText("Alamat");

        tgl_daftar.setEnabled(false);
        tgl_daftar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setText("Tanggal Pendaftaran");

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelGradiente2.setColorPrimario(new java.awt.Color(204, 102, 0));
        panelGradiente2.setColorSecundario(new java.awt.Color(236, 177, 118));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/135.png"))); // NOI18N
        panelGradiente2.add(jLabel3);
        jLabel3.setBounds(50, 110, 135, 125);

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
        panelGradiente2.add(cmdRegister);
        cmdRegister.setBounds(40, 250, 170, 50);

        jButton2.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-data-migration-24.png"))); // NOI18N
        jButton2.setText("  KATEGORI BARANG");
        jButton2.setPreferredSize(new java.awt.Dimension(171, 40));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        panelGradiente2.add(jButton2);
        jButton2.setBounds(40, 350, 171, 50);

        jButton14.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-data-quality-24.png"))); // NOI18N
        jButton14.setText("DATA BARANG");
        jButton14.setPreferredSize(new java.awt.Dimension(171, 40));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        panelGradiente2.add(jButton14);
        jButton14.setBounds(40, 460, 171, 50);

        jButton15.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-report-24.png"))); // NOI18N
        jButton15.setText("LAPORAN");
        jButton15.setPreferredSize(new java.awt.Dimension(171, 40));
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        panelGradiente2.add(jButton15);
        jButton15.setBounds(40, 570, 171, 50);

        jButton16.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-history-24.png"))); // NOI18N
        jButton16.setText("  RIWAYAT");
        jButton16.setPreferredSize(new java.awt.Dimension(171, 40));
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        panelGradiente2.add(jButton16);
        jButton16.setBounds(40, 690, 171, 50);

        jButton17.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-go-back-24.png"))); // NOI18N
        jButton17.setText("  LOGOUT");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        panelGradiente2.add(jButton17);
        jButton17.setBounds(60, 930, 134, 50);

        jButton13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img_new/icons8-transaction-24.png"))); // NOI18N
        jButton13.setText("  TRANSAKSI");
        jButton13.setPreferredSize(new java.awt.Dimension(171, 40));
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        panelGradiente2.add(jButton13);
        jButton13.setBounds(40, 810, 170, 50);

        jLabel10.setFont(new java.awt.Font("Serif", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Back To Dashboard");
        panelGradiente2.add(jLabel10);
        jLabel10.setBounds(80, 250, 96, 17);

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
        T_user.setBounds(90, 120, 70, 32);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-businessman-80.png"))); // NOI18N
        panelGradiente1.add(jLabel5);
        jLabel5.setBounds(40, 30, 80, 80);

        cmdRegister1.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        cmdRegister1.setForeground(new java.awt.Color(255, 255, 255));
        cmdRegister1.setText("Data Karyawan");
        cmdRegister1.setContentAreaFilled(false);
        cmdRegister1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdRegister1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRegister1ActionPerformed(evt);
            }
        });
        panelGradiente1.add(cmdRegister1);
        cmdRegister1.setBounds(710, 20, 310, 110);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelGradiente2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(247, 247, 247)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tgl_daftar, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_email, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(940, 940, 940))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(220, 220, 220)
                                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(46, 46, 46)
                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(44, 44, 44)
                                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(234, 234, 234)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_password, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(449, 449, 449))))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1318, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelGradiente1, javax.swing.GroupLayout.PREFERRED_SIZE, 1798, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(panelGradiente1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addGap(9, 9, 9)
                        .addComponent(tgl_daftar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(193, 193, 193))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(panelGradiente2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 10, 1950, 1170));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         tambahData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       // TODO add your handling code here:
       hapusData();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void table_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_userMouseClicked
        int baris = table_user.getSelectedRow();
        
        String nama = table.getValueAt(baris,1).toString();
        txt_nama.setText(nama);
        
        String email = table.getValueAt(baris, 2).toString();
        txt_email.setText(email);
        
        String alamat = table.getValueAt(baris, 3).toString();
        txt_alamat.setText(alamat);
        
        String username = table.getValueAt(baris, 4).toString();
        txt_username.setText(username);
        
        String password = table.getValueAt(baris, 5).toString();
        txt_password.setText(password);
        
        String tanggal = table.getValueAt(baris, 6).toString();

        Date convert = null;
        try{
            convert = new SimpleDateFormat("yyyy-MM-dd").parse(tanggal);
        }catch(Exception e){
            System.out.println(e);
        }
        tgl_daftar.setDate(convert);
             
    }//GEN-LAST:event_table_userMouseClicked

    private void txt_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_passwordActionPerformed

    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        editData();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void txt_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_usernameActionPerformed

    private void cmdRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRegisterActionPerformed
new Menu_Admin("").setVisible(true);
        dispose();       
        
// TODO add your handling code here:
    }//GEN-LAST:event_cmdRegisterActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new master.CRUD_kategori().setVisible(true);
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        new formCRUDBarang().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        new master.Laporan_Data_Barang().setVisible(true);
        dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        // TODO add your handling code here:
        new Report_new.riwayat().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        //  new login.menu_login().setVisible(true);

        new login_new.sign_in().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton17ActionPerformed

    private void cmdRegister1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRegister1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdRegister1ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        new formTransaksiadmin().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton13ActionPerformed

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
            java.util.logging.Logger.getLogger(akun_karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(akun_karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(akun_karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(akun_karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new akun_karyawan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel T_user;
    private javax.swing.JButton cmdRegister;
    private javax.swing.JButton cmdRegister1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private swing.PanelGradiente panelGradiente1;
    private swing.PanelGradiente panelGradiente2;
    private javax.swing.JTable table_user;
    private com.toedter.calendar.JDateChooser tgl_daftar;
    private javax.swing.JTextArea txt_alamat;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables

    private JDateChooser setDateFormatString(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
