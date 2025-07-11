package reponsitory;

import Utils.JDBCUtil;
import model.Service;

import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ServiceReponsitory {
    public static List<Service> getListService() {
        List<Service> listService=new ArrayList<>();
        try {
            Connection con= JDBCUtil.getConnection();
            Statement st= con.createStatement();
            ResultSet rs=st.executeQuery("select * from Service");
            while(rs.next()){
                int id=rs.getInt(1);
                String name=rs.getString(2);
                double price=rs.getDouble(3);
                listService.add(new Service(id,name,price));
            }
            return listService;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void addPrescriptionServiceDetail(int prescription_id, int service_id, int quantity) {
        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "INSERT INTO PrescriptionServiceDetail (prescription_id, service_id, quantity) VALUES (?, ?, ?)";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, prescription_id);
            pst.setInt(2, service_id);
            pst.setInt(3, quantity);

            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Insert chi tiết dịch vụ thành công!");
            }

            pst.close();
            con.close();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Object[]> getListServiceFromPre(String id_pre) {
        List<Object[]> list = new ArrayList<>();
        DecimalFormat formatter = new DecimalFormat("#,###");

        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT s.name AS TenDichVu, s.price AS DonGia, psd.quantity AS SoLuong " +
                    "FROM PrescriptionServiceDetail psd " +
                    "JOIN Service s ON psd.service_id = s.id " +
                    "WHERE psd.prescription_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(id_pre));
            ResultSet rs = pst.executeQuery();

            int stt = 1;
            while (rs.next()) {
                String name = rs.getString("TenDichVu");
                int quantity = rs.getInt("SoLuong");
                double price = rs.getDouble("DonGia");
                double total = price * quantity;

                String priceFormatted = formatter.format(price) + " VND";
                String totalFormatted = formatter.format(total) + " VND";

                list.add(new Object[]{stt++, name, quantity, priceFormatted, totalFormatted});
            }

            rs.close();
            pst.close();
            con.close();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static Service getServiceById(int id) {
        Service service = new Service();
        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "select * from Service where id = ?";

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int service_id = rs.getInt(1);
                String name = rs.getString(2);
                double price = rs.getDouble(3);
                service.setId(service_id);
                service.setName(name);
                service.setPrice(price);
            }

            pst.close();
            con.close();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return service;
    }
    public static void deleteService(String id){
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "DELETE FROM Service WHERE id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            System.out.println("Đã xóa service có id: " + id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void updateService(int id, String name, int price) {
        String sql = "UPDATE Service SET name = ?, price = ? WHERE id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void insertService(String name, double price) {
        String sql = "INSERT INTO Service (name, price) VALUES (?, ?)";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setDouble(2, price);

            stmt.executeUpdate();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
