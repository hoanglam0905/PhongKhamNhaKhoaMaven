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
    
 // 6. Lấy bệnh nhân mới nhất
    public Patient getLatestPatient() throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
        String query = "SELECT * FROM Patient ORDER BY id DESC LIMIT 1";  // Sắp xếp theo id giảm dần
        try (PreparedStatement ps = JDBCUtil.getConnection().prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return extractPatientFromResultSet(rs);
            }
        }
        return null;  // Nếu không có bệnh nhân nào
    }

 // 3.1. Thêm mới bệnh nhân và trả về ID mới
    public int addPatientAndReturnId(Patient patient) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException {
        String query = "INSERT INTO Patient (name, birthDate, address, gender, phoneNumber, idCard) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, patient.getName());
            ps.setDate(2, patient.getBirthDate());
            ps.setString(3, patient.getAddress());
            ps.setInt(4, patient.getGender());
            ps.setString(5, patient.getPhoneNumber());
            ps.setString(6, patient.getIdCard());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Thêm bệnh nhân thất bại, không có dòng nào bị ảnh hưởng.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Trả về id mới được sinh ra
                } else {
                    throw new SQLException("Không lấy được ID bệnh nhân mới.");
                }
            }
        }
    }
    
    public static Patient getPatientById(int id) {
        Patient patient = null;

        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "SELECT * FROM Patient WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                patient = new Patient();
                patient.setId(rs.getInt("id"));
                patient.setName(rs.getString("name"));
                patient.setPhone(rs.getString("phoneNumber"));
                patient.setBirthDate(rs.getDate("birthDate"));
                patient.setGender(rs.getInt("gender"));
                patient.setIdCard(rs.getString("idCard"));
                patient.setAddress(rs.getString("address"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return patient;
    }
}
