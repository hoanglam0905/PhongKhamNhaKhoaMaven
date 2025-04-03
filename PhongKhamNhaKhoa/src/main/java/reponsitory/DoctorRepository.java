package reponsitory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Utils.JDBCUtil;
import model.Doctor;
import model.Employee;

public class DoctorRepository {
    private List<Doctor> doctors = new ArrayList<>();

    public DoctorRepository() {
        loadDoctorsFromDatabase();
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    private void loadDoctorsFromDatabase() {
        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT e.id, e.name, e.birthDate, e.address, e.gender, e.phoneNumber, e.idCard, e.username, e.password, e.salary,e.experienceYears, e.role, d.specialty FROM Employee e INNER JOIN Doctor d ON e.id = d.id")) {
            while (rs.next()) {
                doctors.add(new Doctor(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDate("birthDate"),
                    rs.getString("address"),
                    rs.getInt("gender"),
                    rs.getString("phoneNumber"),
                    rs.getString("idCard"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getDouble("salary"),
                    rs.getInt("experienceYears"),
                    rs.getString("specialty")
                ));
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
