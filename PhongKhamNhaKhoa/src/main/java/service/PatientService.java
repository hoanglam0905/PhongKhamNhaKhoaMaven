package service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Patient;
import reponsitory.PatientRepository;

//service/PatientService.java
public class PatientService {
 private PatientRepository patientRepository = new PatientRepository();

 public List<Patient> getAllPatients() throws FileNotFoundException, ClassNotFoundException, IOException {
     try {
         return patientRepository.getAllPatients();
     } catch (SQLException e) {
         e.printStackTrace(); // hoặc log lỗi
         return new ArrayList<>();
     }
 }
 public boolean addPatient(Patient patient) throws SQLException, IOException, ClassNotFoundException {
     return patientRepository.addPatient(patient);
 }

 public int addPatientAndReturnId(Patient patient) throws SQLException, ClassNotFoundException, IOException {
	    return patientRepository.addPatientAndReturnId(patient);
	}
}

