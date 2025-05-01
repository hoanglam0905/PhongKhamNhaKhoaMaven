package reponsitory.dao;

import Utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

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

}
