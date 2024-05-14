import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import koneksi.koneksi;
import user.Menu_User;
import javax.swing.JLabel;

public class Utama extends koneksi {
    
     private JLabel transaksi;
    public Utama() {
       // super.setKoneksi();
       
    }

    public void tampilTransaksi() {
        try {
            String sql = "SELECT COUNT(kode_barang) FROM transaksi";
            Connection connect = koneksi.getKoneksi();
            Statement sttmnt = connect.createStatement();
            ResultSet rslt = sttmnt.executeQuery(sql);

                if (rslt.next()) {
           transaksi.setText(rslt.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Menu_User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
