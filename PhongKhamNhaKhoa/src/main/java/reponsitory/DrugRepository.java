package reponsitory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Utils.JDBCUtil;
import model.Drug;

public class DrugRepository {
	private List<Drug> drugs = new ArrayList<>();

    public DrugRepository() {
        loadDrugsFromDatabase();
    }

    public List<Drug> getDrugs() {
		return drugs;
	}

	public void setDrugs(List<Drug> drugs) {
		this.drugs = drugs;
	}

	private void loadDrugsFromDatabase() {
        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM drug")) {
            while (rs.next()) {
                drugs.add(new Drug(rs.getInt("id"), rs.getString("name"), rs.getString("description"), rs.getDouble("price"), rs.getInt("stockQuantity")));
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	
	public Drug getDrugById(int id) {
        Drug drug = null;
        String query = "SELECT * FROM Drug WHERE id = ?";

        try (Connection conn = Utils.JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                drug = new Drug();
                drug.setId(rs.getInt("id"));
                drug.setName(rs.getString("name"));
                drug.setDescription(rs.getString("description"));
                drug.setPrice(rs.getDouble("price"));
                drug.setStockQuantity(rs.getInt("stockQuantity"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return drug;
    }
	
	public boolean updateDrug(int id, String name, String description, double price, int stock) {
	    String sql = "UPDATE Drug SET name = ?, description = ?, price = ?, stockQuantity = ? WHERE id = ?";
	    try (Connection conn = Utils.JDBCUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, name);
	        stmt.setString(2, description);
	        stmt.setDouble(3, price);
	        stmt.setInt(4, stock);
	        stmt.setInt(5, id);

	        int affectedRows = stmt.executeUpdate();
	        return affectedRows > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	public boolean insertDrug(String name, String description, double price, int stock) {
	    String sql = "INSERT INTO Drug(name, description, price, stockQuantity) VALUES (?, ?, ?, ?)";

	    try (Connection conn = Utils.JDBCUtil.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, name);
	        stmt.setString(2, description);
	        stmt.setDouble(3, price);
	        stmt.setInt(4, stock);

	        int affectedRows = stmt.executeUpdate();
	        return affectedRows > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

}
