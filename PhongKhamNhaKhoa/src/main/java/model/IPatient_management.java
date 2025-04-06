package model;

import java.util.List;

public interface IPatient_management {
    void addPatient(Patient bn);
    void fixPatient(String id, Patient bnMoi);
    void delPatient(String id);
    boolean checkIfPatientHasVisited(String sdtOrCCCD);
}