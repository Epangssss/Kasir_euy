/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class koneksi {
    Connection connect;
    Statement sttmnt;
    ResultSet rslt;
    private static Connection koneksi;  
    public static Connection getKoneksi(){
        try{
          
            String url = "jdbc:mysql://localhost:3306/db_ujilevel";
            String user = "root";
            String password = "";
            
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            koneksi = DriverManager.getConnection(url,user,password);
            System.out.println("Koneksi berhasil");
            
        }catch(Exception e){
            System.out.println("Koneksi Gagal");
        }return koneksi;
    
    }}
 
//    
// public static ResultSet ambilData(String query, String... params) {
//    Connection conn = getKoneksi();
//    ResultSet rs = null;
//    try {
//        PreparedStatement stmt = conn.prepareStatement(query);
//        for (int i = 0; i < params.length; i++) {
//            stmt.setString(i + 1, params[i]);
//        }
//        rs = stmt.executeQuery();
//    } catch (SQLException e) {
//        System.out.println("Terjadi kesalahan saat mengambil data: " + e.getMessage());
//        throw new RuntimeException("Terjadi kesalahan saat mengambil data dari database: " + e.getMessage(), e);
//    }
//    return rs;
//}}
//   public static ResultSet ambilData(String query, String text) {
//        Connection conn = getKoneksi();
//        ResultSet rs = null;
//        try {
//            PreparedStatement stmt = conn.prepareStatement(query);
//            rs = stmt.executeQuery();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return rs;
//    }
//}


//TUTUP CLASS KONEKSI

   