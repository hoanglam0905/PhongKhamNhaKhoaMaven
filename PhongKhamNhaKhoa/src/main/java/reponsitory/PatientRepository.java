package reponsitory;

import model.Patient;
import model.Person;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Utils.JDBCUtil;
// 1. Tìm kiếm bệnh nhân theo đầu số điện thoại
public class PatientRepository {
//    private final Connection connection;
//
//    public PatientRepository(Connection connection) {
//        this.connection = connection;
//    }
	
    public PatientRepository() {
		// TODO Auto-generated constructor stub
	}


	public List<Patient> findByPhonePrefix(String phonePrefix) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM Patient WHERE phoneNumber LIKE ?";
        try (PreparedStatement ps = JDBCUtil.getConnection().prepareStatement(query)) {
            ps.setString(1, phonePrefix + "%"); // Chỉ tìm những số bắt đầu bằng prefix
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                patients.add(extractPatientFromResultSet(rs));
            }
        }
        return patients;
    }


    // 2. Lấy toàn bộ danh sách bệnh nhân
    public List<Patient> getAllPatients() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM Patient";
        try (Statement stmt = JDBCUtil.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                patients.add(extractPatientFromResultSet(rs));
            }
        }
        return patients;
    }

    // 3. Thêm mới bệnh nhân
    public boolean addPatient(Patient patient) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
        String query = "INSERT INTO Patient (name, birthDate, address, gender, phoneNumber, idCard) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = JDBCUtil.getConnection().prepareStatement(query)) {
            ps.setString(1, patient.getName());
            ps.setDate(2, patient.getBirthDate());
            ps.setString(3, patient.getAddress());
            ps.setInt(4, patient.getGender());
            ps.setString(5, patient.getPhoneNumber());
            ps.setString(6, patient.getIdCard());
            return ps.executeUpdate() > 0;
        }
    }

    // 4. Cập nhật thông tin bệnh nhân
    public boolean updatePatient(Patient patient) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
        String query = "UPDATE Patient SET name = ?, birthDate = ?, address = ?, gender = ?, phoneNumber = ?, idCard = ? WHERE id = ?";
        try (PreparedStatement ps = JDBCUtil.getConnection().prepareStatement(query)) {
            ps.setString(1, patient.getName());
            ps.setDate(2, patient.getBirthDate());
            ps.setString(3, patient.getAddress());
            ps.setInt(4, patient.getGender());
            ps.setString(5, patient.getPhoneNumber());
            ps.setString(6, patient.getIdCard());
            ps.setInt(7, patient.getId());
            return ps.executeUpdate() > 0;
        }
    }

    // 5. Xoá bệnh nhân theo ID
    public boolean deletePatient(int id) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
        String query = "DELETE FROM Patient WHERE id = ?";
        try (PreparedStatement ps = JDBCUtil.getConnection().prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // Hàm hỗ trợ để convert từ ResultSet sang Patient
    private Patient extractPatientFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        Date birthDate = rs.getDate("birthDate");
        String address = rs.getString("address");
        int gender = rs.getInt("gender");
        String phoneNumber = rs.getString("phoneNumber");
        String idCard = rs.getString("idCard");

        return new Patient(id, name, birthDate, address, gender, phoneNumber, idCard);
    }
    
}
