package reponsitory;

import Utils.JDBCUtil;
import model.Drug;

import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DrugReponsitory {
    public static List<Drug> getListDrug(){
        List<Drug> listDrug=new ArrayList<>();
        try {
            Connection con=JDBCUtil.getConnection();
            Statement st= con.createStatement();
            ResultSet rs=st.executeQuery("select * from Drug");
            while(rs.next()){
                int id=rs.getInt(1);
                String name=rs.getString(2);
                String description=rs.getString(3);
                double price=rs.getDouble(4);
                int stockQuantity=rs.getInt(5);
                listDrug.add(new Drug(id,name,description,price,stockQuantity));
            }
            return listDrug;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Drug> getListAllDrug() {
        List<Drug> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection()) {
            String query = "SELECT * FROM Drug";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stockQuantity");

                Drug drug = new Drug(id, name, description, price, stock);
                list.add(drug);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public static List<Object[]> getListDrugFromPre(String id_pre) {
        List<Object[]> list = new ArrayList<>();
        DecimalFormat formatter = new DecimalFormat("#,###");

        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT d.name AS TenThuoc, pd.quantity AS SoLuong, d.price AS DonGia, (d.price * pd.quantity) AS ThanhTien " +
                    "FROM PrescriptionDrugDetail pd " +
                    "JOIN Drug d ON pd.drug_id = d.id " +
                    "WHERE pd.prescription_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(id_pre));
            ResultSet rs = pst.executeQuery();

            int stt = 1;
            while (rs.next()) {
                String name = rs.getString("TenThuoc");
                int quantity = rs.getInt("SoLuong");
                double price = rs.getDouble("DonGia");
                double total = rs.getDouble("ThanhTien");

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
    public static void updateDrugQuantity(String drugName, int quantitySold) {
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "UPDATE Drug SET stockQuantity = stockQuantity - ? WHERE name = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, quantitySold);
            pst.setString(2, drugName);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
