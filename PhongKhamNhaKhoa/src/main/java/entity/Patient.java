package entity;

import java.time.LocalDate;

public class Patient extends Person {
    private String hoSoBenhAn;

    // Constructor
    public Patient(String name, LocalDate birthDate, String address, int gender,
                    String phoneNumber, String cccd, String hoSoBenhAn) {
        super(name, birthDate, address, gender, phoneNumber, cccd, "BN");
        this.hoSoBenhAn = hoSoBenhAn;
    }

    // Getter
    public String getMedicalRecord() {
        return hoSoBenhAn;
    }

    // Setter
    public void setMedicalRecord(String hoSoBenhAn) {
        this.hoSoBenhAn = hoSoBenhAn;
    }

    @Override
    public void displayInfo() {
        System.out.println("===== Thông tin bệnh nhân =====");
        System.out.println("ID: " + getId());
        System.out.println("Tên: " + getName());
        System.out.println("Ngày sinh: " + getBirthDate());
        System.out.println("Địa chỉ: " + getAddress());
        System.out.println("Giới tính: " + (getGender() == 1 ? "Nam" : getGender() == 0 ? "Nữ" : "Khác"));
        System.out.println("SĐT: " + getPhoneNumber());
        System.out.println("CCCD: " + getCccd());
        System.out.println("Hồ sơ bệnh án: " + hoSoBenhAn);
    }
}

