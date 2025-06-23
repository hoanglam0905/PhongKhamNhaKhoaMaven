package model;

import java.sql.Date;
import java.sql.Time;

public class Appointment {
	private int id;
    private int doctorId;
    private int patientId;
    private String serviceId;
    private String status;
    private Date date;
    private Time time;
    private int duration;
    
    public Appointment() {
    	this.id = -1;
    }
    
    public Appointment(int id, int doctorId, int patientId, String serviceId, String status, Date date, Time time,
			int duration) {
		super();
		this.id = id;
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.serviceId = serviceId;
		this.status = status;
		this.date = date;
		this.time = time;
		this.duration = duration;
	}
	// Getters and setters
    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public Time getTime() { return time; }
    public void setTime(Time time) { this.time = time; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
    
}
