package master;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.Timer;
import koneksi.koneksi;
import transaksi.formTransaksi;
import master.akun_karyawan;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import login_new.sign_in;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class Menu_Admin extends javax.swing.JFrame {

    DefaultTableModel table = new DefaultTableModel();

    // public javax.swing.JTextField T_user;
    // private JLabel User;
//       private String userName;
//
//    public void setUserNames(String userName) {
//        this.userName = userName;
//        T_user.setText(" Manager: " + userName);
    /**
     * Creates new form option_menu
     */
        public Menu_Admin(String username) {
            initComponents();

            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            displayDateTime();
            tampilJumlahKaryawan();
            tampilData();
            pendapatan();
            kerugian();
            T_user.setText(sign_in.getKasirName());
            startTimer();

            tb_riwayat.setModel(table);
            table.addColumn("Tanggal Transaksi");
            table.addColumn("No_Transaksi");
            table.addColumn("Kode Barang");
            table.addColumn("Nama Barang");
            table.addColumn("Harga");
            table.addColumn("Jumlah");
            table.addColumn("Total Harga");
            table.addColumn("Nama Pembeli");
            //    CenteredTableCellRenderer centerRenderer = new CenteredTableCellRenderer();

            // Buat instance dari RightAlignedTableCellRenderer
            RightAlignedTableCellRenderer rightRenderer = new RightAlignedTableCellRenderer();

    // Buat instance dari CenteredTableHeaderRenderer
            CenteredTableHeaderRenderer centerRenderer = new CenteredTableHeaderRenderer();

    // Atur renderer untuk header kolom
            tb_riwayat.getTableHeader().setDefaultRenderer(centerRenderer);

            
            
    // Atur renderer untuk kolom Harga, Jumlah, dan Total Harga
            tb_riwayat.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
            tb_riwayat.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);

            
            
            tb_riwayat.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
            tb_riwayat.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            tb_riwayat.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
            tb_riwayat.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
            tb_riwayat.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
            tb_riwayat.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
            
            
            
            
            

            tampildatatransaksi();
        }

    private void tampildatatransaksi() {
        // Untuk menghapus baris setelah input
        int row = tb_riwayat.getRowCount();
        for (int a = 0; a < row; a++) {
            table.removeRow(0);
        }

        String query = "SELECT * FROM `transaksi`";

        try {
            Connection connect = koneksi.getKoneksi(); // memanggil koneksi
            Statement sttmnt = connect.createStatement(); // membuat statement
            ResultSet rslt = sttmnt.executeQuery(query); // menjalankan query

            // Membuat formatter untuk mata uang Rupiah
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

            while (rslt.next()) {
                // menampung data sementara
                String tanggal = rslt.getString("tgl_transaksi");
                String kode = rslt.getString("kode_barang");
                String nama = rslt.getString("nama_barang");
                String harga = rslt.getString("harga");
                String jumlah = rslt.getString("jumlah_barang");
                String total = rslt.getString("total_harga");
                String nomor_transaksi = rslt.getString("nomor_transaksi");
                String namacs = rslt.getString("namacs");

                // Format harga dan total harga ke dalam format Rupiah
                String formattedHarga = currencyFormatter.format(Double.parseDouble(harga));
                String formattedTotal = currencyFormatter.format(Double.parseDouble(total));

                // Masukkan semua data ke dalam array
                String[] data = {tanggal, nomor_transaksi, kode, nama, formattedHarga, jumlah, formattedTotal, namacs};
                // Menambahkan baris sesuai dengan data yang tersimpan di array
                table.addRow(data);
            }
            // Mengatur nilai yang ditampung agar muncul di tabel
            tb_riwayat.setModel(table);

        } catch (Exception e) {
            System.out.println(e);
        }
    }




  
    // ...

    // Metode lainnya
 // Asumsikan bahwa Anda sudah membuat JTextField dengan nama "karyawan"
 // Asumsikan Anda sudah membuat JTextField dengan nama "karyawan"
// Asumsikan Anda sudah membuat JTextField dengan nama "T_karyawan"

private void tampilJumlahKaryawan() {
        try {
            Connection connect = koneksi.getKoneksi(); // Panggil koneksi database
            String query = "SELECT COUNT(*) AS jumlah_karyawan FROM tb_datakaryawan";
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            int jumlahKaryawan = 0;
            if (rs.next()) {
                jumlahKaryawan = rs.getInt("jumlah_karyawan");
                System.out.println("Jumlah Karyawan: " + jumlahKaryawan);
            }

            // Tampilkan jumlah karyawan ke label T_karyawan
            T_karyawan1.setText(String.valueOf(jumlahKaryawan));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Terjadi kesalahan dalam mengambil data karyawan: " + e.getMessage());
        }
    }

private void kerugian() {
    try {
        // Establish database connection
        Connection connect = koneksi.getKoneksi();

        // Query to get total loss
        String query = "SELECT SUM(harga * stok) AS total_kerugian FROM tb_databarang";
        Statement stmt = connect.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        if (rs.next()) {
            double totalKerugian = rs.getDouble("total_kerugian");
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
            String formattedKerugian = formatter.format(totalKerugian);
            T_kerugian.setText(formattedKerugian);
        } else {
            T_kerugian.setText("Rp0,00");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        e.printStackTrace();
    }
}

    private void tampilData() {
        try {
            // Mengambil data dari database
            String query = "SELECT COUNT(*) AS total_data FROM `tb_databarang`";
            Connection connect = koneksi.getKoneksi();
            Statement sttmnt = connect.createStatement();
            ResultSet rslt = sttmnt.executeQuery(query);

            if (rslt.next()) {
                int totalData = rslt.getInt("total_data");
                T_data.setText(String.valueOf(totalData));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void pendapatan() {
        double totalRevenue = 0;
        try {
            // Assuming you have a database connection named "conn"
            Connection connect = koneksi.getKoneksi();
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT SUM(total_harga) AS total_revenue FROM transaksi");
            if (rs.next()) {
                totalRevenue = rs.getDouble("total_revenue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String formattedTotalRevenue = formatRupiah(totalRevenue);
        t_pendapatan1.setText(formattedTotalRevenue);
    }

    private String formatRupiah(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatter.format(value);
    }
//private void tampilDataKaryawan() {
//    int jumlahKaryawan = 0;
//
//    try {
//        Connection connect = koneksi.getKoneksi(); // Panggil koneksi database
//        Statement stmt = connect.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT COUNT(id_karyawan) AS jumlah_karyawan FROM tb_karyawan");
//
//        if (rs.next()) {
//            jumlahKaryawan = rs.getInt("jumlah_karyawan");
//        }
//
//        karyawan.setText("Jumlah Karyawan: " + jumlahKaryawan);
//
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//}

    private void displayDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("EEEE dd MMMM yyyy");
        Date date = new Date();
        String dateStr = format.format(date);
        T_date.setText(dateStr);
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

    public class CenteredTableCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(CENTER);
            return this;
        }
    }

    public class CenteredTableHeaderRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JTableHeader header = table.getTableHeader();
            setHorizontalAlignment(CENTER);
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    public class RightAlignedTableCellRenderer extends DefaultTableCellRenderer {

        @Override
        public void setValue(Object value) {
            setHorizontalAlignment(RIGHT);
            super.setValue(value);
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
        jPanel5 = new javax.swing.JPanel();
        panelGradiente1 = new swing.PanelGradiente();
        jLabel2 = new javax.swing.JLabel();
        T_user = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmdRegister1 = new javax.swing.JButton();
        panelGradiente2 = new swing.PanelGradiente();
        jLabel1 = new javax.swing.JLabel();
        T_date = new javax.swing.JTextField();
        cmdRegister = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_riwayat = new javax.swing.JTable();
        panelGradiente3 = new swing.PanelGradiente();
        t_pendapatan1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        panelGradiente4 = new swing.PanelGradiente();
        T_kerugian = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        panelGradiente5 = new swing.PanelGradiente();
        T_data = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        panelGradiente6 = new swing.PanelGradiente();
        T_karyawan1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(153, 0, 0));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);

        panelGradiente1.setColorPrimario(new java.awt.Color(255, 204, 102));
        panelGradiente1.setColorSecundario(new java.awt.Color(153, 153, 153));

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
        cmdRegister1.setText("Menu Admin");
        cmdRegister1.setContentAreaFilled(false);
        cmdRegister1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdRegister1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRegister1ActionPerformed(evt);
            }
        });
        panelGradiente1.add(cmdRegister1);
        cmdRegister1.setBounds(800, 20, 190, 110);

        panelGradiente2.setColorPrimario(new java.awt.Color(204, 102, 0));
        panelGradiente2.setColorSecundario(new java.awt.Color(204, 204, 204));

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

        jButton6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/file.png"))); // NOI18N
        jButton6.setText("  DATA KARYAWAN");
        jButton6.setPreferredSize(new java.awt.Dimension(171, 40));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        panelGradiente2.add(jButton6);
        jButton6.setBounds(30, 360, 171, 50);

        jButton2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/bar-chart.png"))); // NOI18N
        jButton2.setText("  KATEGORI BARANG");
        jButton2.setPreferredSize(new java.awt.Dimension(171, 40));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        panelGradiente2.add(jButton2);
        jButton2.setBounds(30, 460, 171, 50);

        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/bar-chart.png"))); // NOI18N
        jButton1.setText("DATA BARANG");
        jButton1.setPreferredSize(new java.awt.Dimension(171, 40));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panelGradiente2.add(jButton1);
        jButton1.setBounds(30, 560, 171, 50);

        jButton3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/bar-chart.png"))); // NOI18N
        jButton3.setText("LAPORAN");
        jButton3.setPreferredSize(new java.awt.Dimension(171, 40));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        panelGradiente2.add(jButton3);
        jButton3.setBounds(30, 660, 171, 50);

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
        jButton4.setBounds(30, 760, 171, 50);

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1504, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        panelGradiente3.setColorPrimario(new java.awt.Color(102, 51, 0));
        panelGradiente3.setColorSecundario(new java.awt.Color(204, 204, 204));

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
        jLabel4.setText("Total Pendapatan");
        panelGradiente3.add(jLabel4);
        jLabel4.setBounds(80, 30, 190, 30);

        panelGradiente4.setColorPrimario(new java.awt.Color(102, 51, 0));
        panelGradiente4.setColorSecundario(new java.awt.Color(204, 204, 204));

        T_kerugian.setEditable(false);
        T_kerugian.setBackground(new java.awt.Color(255, 255, 255));
        T_kerugian.setFont(new java.awt.Font("Segoe UI", 0, 25)); // NOI18N
        T_kerugian.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        T_kerugian.setText("0");
        T_kerugian.setBorder(null);
        T_kerugian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                T_kerugianActionPerformed(evt);
            }
        });
        panelGradiente4.add(T_kerugian);
        T_kerugian.setBounds(-10, 80, 340, 120);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 2, 22)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Total Pengeluaran");
        panelGradiente4.add(jLabel7);
        jLabel7.setBounds(70, 30, 190, 30);

        panelGradiente5.setColorPrimario(new java.awt.Color(102, 51, 0));
        panelGradiente5.setColorSecundario(new java.awt.Color(204, 204, 204));

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

        panelGradiente6.setColorPrimario(new java.awt.Color(102, 51, 0));
        panelGradiente6.setColorSecundario(new java.awt.Color(204, 204, 204));

        T_karyawan1.setEditable(false);
        T_karyawan1.setBackground(new java.awt.Color(255, 255, 255));
        T_karyawan1.setFont(new java.awt.Font("Segoe UI", 0, 25)); // NOI18N
        T_karyawan1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        T_karyawan1.setText("0");
        T_karyawan1.setBorder(null);
        T_karyawan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                T_karyawan1ActionPerformed(evt);
            }
        });
        panelGradiente6.add(T_karyawan1);
        T_karyawan1.setBounds(-10, 80, 370, 140);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 2, 22)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Jumlah Karyawan");
        panelGradiente6.add(jLabel8);
        jLabel8.setBounds(90, 30, 180, 30);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelGradiente2, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1777, 1777, 1777)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelGradiente1, javax.swing.GroupLayout.PREFERRED_SIZE, 1790, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(89, 89, 89)
                            .addComponent(panelGradiente3, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(78, 78, 78)
                            .addComponent(panelGradiente5, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(81, 81, 81)
                            .addComponent(panelGradiente6, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(79, 79, 79)
                            .addComponent(panelGradiente4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(66, 66, 66)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelGradiente2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelGradiente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(159, 159, 159)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(panelGradiente3, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelGradiente4, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelGradiente6, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelGradiente5, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(88, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1980, 1120));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void T_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_T_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_T_dateActionPerformed

    private void cmdRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRegisterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdRegisterActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        new master.akun_karyawan().setVisible(true);
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new master.CRUD_kategori().setVisible(true);
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new formCRUDBarang().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        new master.Laporan_Data_Barang().setVisible(true);
        dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        new master.riwayat().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        //  new login.menu_login().setVisible(true);

        new login_new.sign_in().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void T_dataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_T_dataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_T_dataActionPerformed

    private void T_karyawan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_T_karyawan1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_T_karyawan1ActionPerformed

    private void cmdRegister1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRegister1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdRegister1ActionPerformed

    private void T_kerugianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_T_kerugianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_T_kerugianActionPerformed

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
            java.util.logging.Logger.getLogger(Menu_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu_Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Menu_Admin("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField T_data;
    private javax.swing.JTextField T_date;
    private javax.swing.JTextField T_karyawan1;
    private javax.swing.JTextField T_kerugian;
    private javax.swing.JLabel T_user;
    private javax.swing.JButton cmdRegister;
    private javax.swing.JButton cmdRegister1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private swing.PanelGradiente panelGradiente1;
    private swing.PanelGradiente panelGradiente2;
    private swing.PanelGradiente panelGradiente3;
    private swing.PanelGradiente panelGradiente4;
    private swing.PanelGradiente panelGradiente5;
    private swing.PanelGradiente panelGradiente6;
    private javax.swing.JTextField t_pendapatan;
    private javax.swing.JTextField t_pendapatan1;
    private javax.swing.JTable tb_riwayat;
    // End of variables declaration//GEN-END:variables
}
