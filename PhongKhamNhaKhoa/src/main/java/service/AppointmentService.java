package service;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import model.Appointment;
import model.Examination;
import reponsitory.AppointmentRepository;
import reponsitory.DoctorRepository;

public class AppointmentService {
	
	private AppointmentRepository appointmentrRepository;

    public AppointmentService() {
        this.appointmentrRepository = new AppointmentRepository();
    }

    public List<Appointment> getAppointmentsForDoctorOnDate(int doctorId, Date date) {
        return AppointmentRepository.getAppointmentsByDoctorId(doctorId, date);
    }
    
    public boolean addAppoint(int doctorId, int patientId, String service,
            Date ngay, Time gio, int duKien, String status) throws SQLException, IOException, ClassNotFoundException {
        return appointmentrRepository.insertAppointment(doctorId, patientId, service, ngay, gio, duKien, status);
    }
}
