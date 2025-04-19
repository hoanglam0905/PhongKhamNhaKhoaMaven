package reponsitory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Utils.JDBCUtil;
import model.Service;

public class ServiceRepository {
	private List<Service> services = new ArrayList<>();

    public ServiceRepository() {
        loadServicesFromDatabase();
    }

    private void loadServicesFromDatabase() {
        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM services")) {
            while (rs.next()) {
                services.add(new Service(rs.getInt("id"), rs.getString("name"), rs.getDouble("price")));
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
