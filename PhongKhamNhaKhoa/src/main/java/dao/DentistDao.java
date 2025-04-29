package dao;

import Utils.JDBCUtil;

import java.io.IOException;
import java.sql.*;

public class DentistDao {
    public static int getIdDentistLogin(String acc, String pass) {
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT id FROM Employee WHERE username = ? AND password = ? AND role = 'Bác sĩ'";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, acc);
            pst.setString(2, pass);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1; // Không tìm thấy tài khoản
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        System.out.println(DentistDao.getIdDentistLogin("bacsia","password"));
    }
}
