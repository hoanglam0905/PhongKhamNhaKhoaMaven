package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import model.Patient;
import service.PatientService;
import view.PatientView;

//controller/PatientController.java
public class PatientController {
 private PatientService patientService = new PatientService();
 private PatientView view;

 public PatientController(PatientView view) {
     this.view = view;
     initListeners();
 }

 public PatientController() {
	// TODO Auto-generated constructor stub
}

private void initListeners() {
     view.getPrintButton().addActionListener(e -> {
		try {
			onPrintPatients();
		} catch (ClassNotFoundException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	});
 }

 private void onPrintPatients() throws FileNotFoundException, ClassNotFoundException, IOException {
     List<Patient> patients = patientService.getAllPatients();
     view.updatePatientTable(patients);
 }
 
 public boolean addPatient(Patient patient) {
     try {
         return patientService.addPatient(patient);
     } catch (SQLException | IOException | ClassNotFoundException e) {
         e.printStackTrace();
         return false;
     }
 }
}

