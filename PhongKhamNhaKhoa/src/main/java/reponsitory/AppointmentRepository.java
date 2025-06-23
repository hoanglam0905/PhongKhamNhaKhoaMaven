package reponsitory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Appointment;
import Utils.JDBCUtil;

public class AppointmentRepository {

    public static List<Appointment> getAppointmentsByDoctorId(int doctorId, Date date) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointment WHERE doctor_id = ? AND ngay = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            stmt.setDate(2, date);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Appointment appt = new Appointment();
                appt.setId(rs.getInt("id"));
                appt.setDoctorId(rs.getInt("doctor_id"));
                appt.setPatientId(rs.getInt("patient_id"));
                appt.setServiceId(rs.getString("service"));
                appt.setDate(rs.getDate("ngay"));
                appt.setTime(rs.getTime("gio"));
                appt.setStatus(rs.getString("status"));
                appt.setDuration(rs.getInt("duKien"));
                appointments.add(appt);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }
    
    public static boolean insertAppointment(int doctorId, int patientId, String service,
            Date ngay, Time gio, int duKien, String status) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
    	String sql = "INSERT INTO Appointment (doctor_id, patient_id, service, ngay, gio, duKien, status) " +
    			"VALUES (?, ?, ?, ?, ?, ?, ?)";
    		try (Connection conn = JDBCUtil.getConnection();
    				PreparedStatement stmt = conn.prepareStatement(sql)) {
    			stmt.setInt(1, doctorId);
    			stmt.setInt(2, patientId);
    			stmt.setString(3, service);
    			stmt.setDate(4, ngay);
    			stmt.setTime(5, gio);
    			stmt.setInt(6, duKien);
    			stmt.setString(7, status);
    			return stmt.executeUpdate() > 0;
    			
    		} 
    			
    }
    public static int insertAppointmentAndReturnId(int doctorId, int patientId, String service,
            Date ngay, Time gio, int duKien, String status) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {

        String sql = "INSERT INTO Appointment (doctor_id, patient_id, service, ngay, gio, duKien, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, doctorId);
            stmt.setInt(2, patientId);
            stmt.setString(3, service);
            stmt.setDate(4, ngay);
            stmt.setTime(5, gio);
            stmt.setInt(6, duKien);
            stmt.setString(7, status);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Chèn lịch hẹn thất bại, không có hàng nào bị ảnh hưởng.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Trả về ID vừa chèn
                } else {
                    throw new SQLException("Chèn lịch hẹn thất bại, không lấy được ID.");
                }
            }
        }
    }

    
    public static Appointment getTodayUpcomingAppointment(int patientId) throws FileNotFoundException, ClassNotFoundException, IOException {
        String sql = """
            SELECT *
            FROM Appointment
            WHERE patient_id = ?
              AND status = 'Chưa đến'
              AND ngay = CURRENT_DATE
              AND TIMESTAMP(ngay, gio) >= CURRENT_TIMESTAMP
            ORDER BY gio ASC
            LIMIT 1
        """;
        Connection connection = JDBCUtil.getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setId(rs.getInt("id"));
                    appointment.setDoctorId(rs.getInt("doctor_id"));
                    appointment.setPatientId(rs.getInt("patient_id"));
                    appointment.setServiceId(rs.getString("service"));
                    appointment.setStatus(rs.getString("status"));
                    appointment.setDate(rs.getDate("ngay"));
                    appointment.setTime(rs.getTime("gio"));
                    appointment.setDuration(rs.getInt("duKien"));
                    return appointment;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Appointment notFound = new Appointment();
        return notFound;
    }
    
    public static Integer getDoctorIdByAppointmentId(int appointmentId) throws FileNotFoundException, ClassNotFoundException, IOException {
        String sql = "SELECT doctor_id FROM Appointment WHERE id = ?";
        
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, appointmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("doctor_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Trả về null nếu không tìm thấy hoặc lỗi
        return null;
    }

    public static boolean updateAppointmentStatusToArrived(int appointmentId) throws FileNotFoundException, ClassNotFoundException, IOException {
        String sql = "UPDATE Appointment SET status = 'Đã đến' WHERE id = ?";
        
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, appointmentId);
            int rowsUpdated = stmt.executeUpdate();
            
            return rowsUpdated > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static Appointment getAppointmentById(int appointmentId) throws FileNotFoundException, ClassNotFoundException, IOException {
        String sql = "SELECT * FROM Appointment WHERE id = ?";
        
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, appointmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setId(rs.getInt("id"));
                    appointment.setDoctorId(rs.getInt("doctor_id"));
                    appointment.setPatientId(rs.getInt("patient_id"));
                    appointment.setServiceId(rs.getString("service"));
                    appointment.setDate(rs.getDate("ngay"));
                    appointment.setTime(rs.getTime("gio"));
                    appointment.setStatus(rs.getString("status"));
                    appointment.setDuration(rs.getInt("duKien"));
                    return appointment;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Trả về null nếu không tìm thấy hoặc có lỗi
        return null;
    }


}
