package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {
 private MedicalRecord medicalRecord;

 public Patient(int id, String name, Date birthDate, String address, int gender, String phoneNumber, String idCard) {
     super(id, name, birthDate, address, gender, phoneNumber, idCard);
     this.medicalRecord = new MedicalRecord(id, name);  // Mỗi bệnh nhân sẽ có một bệnh án
 }

 public MedicalRecord getMedicalRecord() {
     return medicalRecord;
 }

 public void setMedicalRecord(MedicalRecord medicalRecord) {
     this.medicalRecord = medicalRecord;
 }

 @Override
 public void showInfo() {
     super.showInfo();
     System.out.println("Medical Record ID: " + medicalRecord.getId());
 }
}

