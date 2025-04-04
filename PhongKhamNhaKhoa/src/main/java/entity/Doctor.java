package entity;

import java.time.LocalDate;

public class Doctor extends Employee {
    private String chuyenNganh;

    // Constructor
    public Doctor(String name, LocalDate birthDate, String address, int gender,
                 String phoneNumber, String cccd, double mucLuong, String chucVu, String chuyenNganh) {
        super(name, birthDate, address, gender, phoneNumber, cccd, mucLuong, chucVu);
        this.chuyenNganh = chuyenNganh;
    }

    // Getter
    public String getSpeciality() {
        return chuyenNganh;
    }

    // Setter
    public void setSpeciality (String chuyenNganh) {
        this.chuyenNganh = chuyenNganh;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Chuyên ngành: " + chuyenNganh);
    }
}
