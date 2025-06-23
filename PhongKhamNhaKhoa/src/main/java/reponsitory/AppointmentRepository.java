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
}
