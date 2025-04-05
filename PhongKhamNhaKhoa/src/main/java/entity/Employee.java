package entity;

import java.time.LocalDate;

public class Employee extends Person {
    private double mucLuong;
    private String chucVu;

    public Employee(String name, LocalDate birthDate, String address, int gender,
                    String phoneNumber, String cccd, double mucLuong, String chucVu) {
        super(name, birthDate, address, gender, phoneNumber, cccd, "BS"); 
        setSalary(mucLuong);
        setPosition(chucVu);
    }

    // Getter
    public double getSalary() {
        return mucLuong;
    }

    public String getPosition() {
        return chucVu;
    }

    // Setter
    public void setSalary(double mucLuong) {
        if (mucLuong >= 0) {
            this.mucLuong = mucLuong;
        } else {
            throw new IllegalArgumentException("Mức lương không được âm.");
        }
    }

    public void setPosition(String chucVu) {
        if (chucVu.equalsIgnoreCase("Lễ tân") ||
            chucVu.equalsIgnoreCase("Bác sĩ") ||
            chucVu.equalsIgnoreCase("Quầy thuốc")) {
            this.chucVu = chucVu;
        } else {
            throw new IllegalArgumentException("Chức vụ không hợp lệ.");
        }
    }

    @Override
    public void displayInfo() {
        System.out.println("===== Thông tin nhân viên =====");
        System.out.println("ID: " + getId());
        System.out.println("Tên: " + getName());
        System.out.println("Ngày sinh: " + getBirthDate());
        System.out.println("Địa chỉ: " + getAddress());
        System.out.println("Giới tính: " + (getGender() == 1 ? "Nam" : getGender() == 0 ? "Nữ" : "Khác"));
        System.out.println("SĐT: " + getPhoneNumber());
        System.out.println("CCCD: " + getCccd());
        System.out.println("Chức vụ: " + getPosition());
        System.out.println("Mức lương: " + getSalary());
    }


	public double tinhLuong() {
        return mucLuong;
    }
}

