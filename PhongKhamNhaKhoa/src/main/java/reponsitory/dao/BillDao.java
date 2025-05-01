package reponsitory.dao;

import Utils.JDBCUtil;

import java.io.IOException;
import java.sql.*;

public class BillDao {
    public static void updatePaymentStatusToPaid(int prescriptionId) {
        try (Connection con = JDBCUtil.getConnection()) {
            String sql = "UPDATE Prescription SET paymentStatus = 'Đã thanh toán' WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, prescriptionId);
            pst.executeUpdate();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static int getPriceInPre(int id_pre){
        try {
            Connection con=JDBCUtil.getConnection();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT \n" +
                    "    p.id AS MaHoaDon,\n" +
                    "    IFNULL(SUM(d.price * pd.quantity), 0) AS TongTienThuoc,\n" +
                    "    IFNULL(SUM(s.price * psd.quantity), 0) AS TongTienDichVu,\n" +
                    "    (IFNULL(SUM(d.price * pd.quantity), 0) + IFNULL(SUM(s.price * psd.quantity), 0)) AS TongTien\n" +
                    "FROM Prescription p\n" +
                    "LEFT JOIN PrescriptionDrugDetail pd ON p.id = pd.prescription_id\n" +
                    "LEFT JOIN Drug d ON pd.drug_id = d.id\n" +
                    "LEFT JOIN PrescriptionServiceDetail psd ON p.id = psd.prescription_id\n" +
                    "LEFT JOIN Service s ON psd.service_id = s.id\n" +
                    "WHERE p.id = " + id_pre);
            rs.next();
            return rs.getInt("TongTien");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
