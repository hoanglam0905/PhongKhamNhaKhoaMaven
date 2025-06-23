package model;

import java.sql.Date;
import java.sql.Time;

public class Appointment {
    private int doctorId;
    private int patientId;
    private String serviceId;
    private String status;
    private Date date;
    private Time time;
    private int duration;

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
}
