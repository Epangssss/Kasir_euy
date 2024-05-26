/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package master;

import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import koneksi.koneksi;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.text.SimpleDateFormat;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.*;

/**
 *
 * @author edwar
 */
public class Laporan_Data_Barang extends javax.swing.JFrame {

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private Component[] savedComponents;

    public Laporan_Data_Barang() {
        initComponents();
        dateEnabled(false);
        koneksi.getKoneksi();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

    }

    private boolean isValidDate(String dateStr) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            df.setLenient(false);
            df.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void dateEnabled(boolean x) {
        t_datadari.setEnabled(x);
        t_datasampai.setEnabled(x);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser3 = new custom_date.DateChooser();
        dateChooser4 = new custom_date.DateChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        t_datasampai = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cbo_laporan = new javax.swing.JComboBox<>();
        t_datadari = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        t_print = new javax.swing.JButton();
        pnLaporan = new javax.swing.JPanel();
        panelGradiente1 = new swing.PanelGradiente();
        jLabel2 = new javax.swing.JLabel();
        T_user = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmdRegister1 = new javax.swing.JButton();
        panelGradiente2 = new swing.PanelGradiente();
        jLabel1 = new javax.swing.JLabel();
        T_date = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        cmdRegister = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        dateChooser3.setDateFormat("yyyy-MM-dd");
        dateChooser3.setName(""); // NOI18N
        dateChooser3.setTextRefernce(t_datadari);

        dateChooser4.setDateFormat("yyyy-MM-dd");
        dateChooser4.setTextRefernce(t_datasampai);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        t_datasampai.setText("...");
        t_datasampai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_datasampaiActionPerformed(evt);
            }
        });

        jButton5.setText("...");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Dari Tanggal");

        cbo_laporan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "pilih", "riwayat transaksi ", "laporan transaksi", " " }));
        cbo_laporan.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cbo_laporan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbo_laporanItemStateChanged(evt);
            }
        });
        cbo_laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_laporanActionPerformed(evt);
            }
        });
        cbo_laporan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cbo_laporanKeyPressed(evt);
            }
        });

        t_datadari.setText("...");
        t_datadari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_datadariActionPerformed(evt);
            }
        });

        jButton6.setText("...");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("PILIH JENIS LAPORAN");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Sampai Tanggal");

        t_print.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        t_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/print.png"))); // NOI18N
        t_print.setText("  PRINT");
        t_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_printActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(t_print, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                            .addComponent(t_datasampai, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(t_datadari, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton5))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbo_laporan, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(cbo_laporan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(t_datadari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(t_datasampai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addGap(125, 125, 125)
                .addComponent(t_print, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(231, Short.MAX_VALUE))
        );

        pnLaporan.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout pnLaporanLayout = new javax.swing.GroupLayout(pnLaporan);
        pnLaporan.setLayout(pnLaporanLayout);
        pnLaporanLayout.setHorizontalGroup(
            pnLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1293, Short.MAX_VALUE)
        );
        pnLaporanLayout.setVerticalGroup(
            pnLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

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

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8-businessman-80.png"))); // NOI18N
        panelGradiente1.add(jLabel6);
        jLabel6.setBounds(40, 30, 80, 80);

        cmdRegister1.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        cmdRegister1.setForeground(new java.awt.Color(255, 255, 255));
        cmdRegister1.setText("Laporan Data Barang");
        cmdRegister1.setContentAreaFilled(false);
        cmdRegister1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdRegister1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRegister1ActionPerformed(evt);
            }
        });
        panelGradiente1.add(cmdRegister1);
        cmdRegister1.setBounds(750, 30, 350, 110);

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
        jButton2.setBounds(30, 540, 171, 50);

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
        jButton1.setBounds(30, 650, 171, 50);

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
        jButton3.setBounds(30, 330, 171, 50);

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

        jButton8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/logout.png"))); // NOI18N
        jButton8.setText("  LOGOUT");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        panelGradiente2.add(jButton8);
        jButton8.setBounds(40, 920, 134, 50);

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
        cmdRegister.setBounds(20, 250, 190, 50);

        jButton7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/file.png"))); // NOI18N
        jButton7.setText("  DATA KARYAWAN");
        jButton7.setPreferredSize(new java.awt.Dimension(171, 40));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        panelGradiente2.add(jButton7);
        jButton7.setBounds(30, 430, 171, 50);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelGradiente2, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(pnLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(panelGradiente1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1859, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelGradiente1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(488, 488, 488))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnLaporan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addComponent(panelGradiente2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
     dateChooser3.showPopup();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void t_datasampaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_datasampaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_datasampaiActionPerformed

    private void t_datadariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_datadariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_datadariActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
dateChooser4.showPopup();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void t_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_printActionPerformed
String laporan = cbo_laporan.getSelectedItem().toString().trim();
switch (laporan) {
    case "Pilih":
        JOptionPane.showMessageDialog(null, "Terdapat inputan yang kosong.");
        break;

    case "riwayat transaksi":
        try {
            File file = new File("src/Report_New/transaksi.jasper");
            JasperPrint print = JasperFillManager.fillReport(file.getAbsolutePath(), null, koneksi.getKoneksi());
            JasperViewer viewer = new JasperViewer(print, false);
            
            // Hapus semua komponen dari panel laporan dan tambahkan viewer
            pnLaporan.removeAll();
            pnLaporan.setLayout(new BorderLayout()); // Menggunakan BorderLayout untuk tata letak yang lebih fleksibel
            pnLaporan.add(viewer.getContentPane(), BorderLayout.CENTER);
            pnLaporan.revalidate();
            pnLaporan.repaint();

            // Mengatur ukuran preferensi untuk panel laporan
            pnLaporan.setPreferredSize(new Dimension(1280, 720)); // Sesuaikan ukuran sesuai kebutuhan

            // Tambahkan pnLaporan ke frame dan refresh
            getContentPane().remove(pnLaporan);  // Hapus dulu agar diperbarui
            getContentPane().add(pnLaporan);
            getContentPane().revalidate();
            getContentPane().repaint();
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        break;

    case "laporan transaksi":
        try {
            HashMap<String, Object> hash = new HashMap<>();
            hash.put("dataAwal", t_datadari.getText());
            hash.put("dataAkhir", t_datasampai.getText());

            File file1 = new File("src/Report_new/laporan.jasper");

            JasperPrint jasperPrint = JasperFillManager.fillReport(file1.getAbsolutePath(), hash, koneksi.getKoneksi());
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            
            // Hapus semua komponen dari panel laporan dan tambahkan viewer
            pnLaporan.removeAll();
            pnLaporan.setLayout(new BorderLayout()); // Menggunakan BorderLayout untuk tata letak yang lebih fleksibel
            pnLaporan.add(viewer.getContentPane(), BorderLayout.CENTER);
            pnLaporan.revalidate();
            pnLaporan.repaint();

            // Mengatur ukuran preferensi untuk panel laporan
            pnLaporan.setPreferredSize(new Dimension(1280, 720)); // Sesuaikan ukuran sesuai kebutuhan

            // Tambahkan pnLaporan ke frame dan refresh
            getContentPane().remove(pnLaporan);  // Hapus dulu agar diperbarui
            getContentPane().add(pnLaporan);
            getContentPane().revalidate();
            getContentPane().repaint();
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
        break;
}



//        String laporan = cbo_laporan.getSelectedItem().toString().trim();
//        switch (laporan) {
//            case "Pilih":
//                JOptionPane.showMessageDialog(null, "Terdapat inputan yang kosong.");
//                break;
//
//            case "riwayat transaksi":
//            
//    try {
//      
//                File file = new File("src/Report_New/transaksi.jasper");
//                JasperPrint print = JasperFillManager.fillReport(file.getAbsolutePath(), null, koneksi.getKoneksi());
//                JasperViewer.viewReport(print, false);
//            } catch (JRException e) {
//                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
//            }
//            break;
//
////    case "riwayat transaksi":
////        try {
////            // Load the Jasper report design
////            InputStream report = getClass().getResourceAsStream("/Report_New/transaksi.jasper");;
////            JasperPrint Print = JasperFillManager.fillReport(report, null, koneksi.getKoneksi());
////            JasperViewer.viewReport(Print, false);
////            
////        } catch (JRException e) {
////            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
////        }
////        break;
////            case "riwayat transaksi":
////                try {
////                File file = new File("/Report_new/transaksi.jasper");
////                //    JasperDesign jasperDesign = JRXmlLoader.load(file);
////                //    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
////                JasperPrint jasperPrint = JasperFillManager.fillReport(file.getAbsolutePath(), null, koneksi.getKoneksi());
////                JasperViewer.viewReport(jasperPrint, false);
////
////            } catch (JRException e) {
////                JOptionPane.showMessageDialog(null, "Error " + e);
////            }
////            //this.hide();
////            break;
//            case "laporan transaksi":
//                try {
//                HashMap hash = new HashMap();
//                hash.put("dataAwal", t_datadari.getText());
//                hash.put("dataAkhir", t_datasampai.getText());
//
//                File file1 = new File("src/Report_new/laporan.jasper");
//
//                JasperPrint jasperPrint = JasperFillManager.fillReport(file1.getAbsolutePath(), hash, koneksi.getKoneksi());
//                JasperViewer.viewReport(jasperPrint, false);
//            } catch (JRException e) {
//                JOptionPane.showMessageDialog(null, "Error " + e);
//            }
//            //this.hide();
//            break;
//
//        }
//        
//        pnLaporan.removeAll();
//        
//        
//
////        try {
////            JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/Report_New/transaksi.jasper"), null, koneksi.getKoneksi());
////            JasperViewer.viewReport(print, false);
////
////        } catch (Exception e) {
////            JOptionPane.showMessageDialog(rootPane, e);
////        }

    }//GEN-LAST:event_t_printActionPerformed

    private void cbo_laporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_laporanActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_laporanActionPerformed

    private void cbo_laporanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbo_laporanKeyPressed

    }//GEN-LAST:event_cbo_laporanKeyPressed

    private void cbo_laporanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbo_laporanItemStateChanged

        String laporan = cbo_laporan.getSelectedItem().toString();
        if (laporan.equals("riwayat transaksi") || laporan.equals("Pilih")) {
            dateEnabled(false);
        } else {
            dateEnabled(true);
            t_datadari.requestFocus();
        }
    }//GEN-LAST:event_cbo_laporanItemStateChanged

    private void cmdRegister1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRegister1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdRegister1ActionPerformed

    private void T_dateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_T_dateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_T_dateActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        new master.akun_karyawan().setVisible(true);
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7ActionPerformed

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

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        //  new login.menu_login().setVisible(true);

        new login_new.sign_in().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void cmdRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRegisterActionPerformed
        new Menu_Admin("").setVisible(true);
        dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_cmdRegisterActionPerformed

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
            java.util.logging.Logger.getLogger(Laporan_Data_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Laporan_Data_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Laporan_Data_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Laporan_Data_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Laporan_Data_Barang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField T_date;
    private javax.swing.JLabel T_user;
    private javax.swing.JComboBox<String> cbo_laporan;
    private javax.swing.JButton cmdRegister;
    private javax.swing.JButton cmdRegister1;
    private custom_date.DateChooser dateChooser3;
    private custom_date.DateChooser dateChooser4;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private swing.PanelGradiente panelGradiente1;
    private swing.PanelGradiente panelGradiente2;
    private javax.swing.JPanel pnLaporan;
    private javax.swing.JTextField t_datadari;
    private javax.swing.JTextField t_datasampai;
    private javax.swing.JButton t_print;
    // End of variables declaration//GEN-END:variables

    private ResultSet getTransaksiDataForPeriod(Date startDate, Date endDate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
