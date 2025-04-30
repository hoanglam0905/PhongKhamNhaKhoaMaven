package dao;

import Utils.JDBCUtil;
import model.Drug;
import model.DrugDose;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DrugDao {
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
    public static List<Object[]> getListAllDrug() {
        List<Object[]> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection()) {
            String query = "SELECT * FROM Drug";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String price = rs.getString("price");
                int stock = rs.getInt("stockQuantity");

                list.add(new Object[]{
                        id,
                        name,
                        stock,
                        "Vỉ",
                        price
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
