package entity;

import java.time.LocalDate;
import java.util.regex.Pattern;
import java.util.UUID;

public abstract class Person {
    protected String id;
    protected String name;
    protected LocalDate birthDate;
    protected String address;
    protected int gender; 
    protected String phoneNumber;
    protected String cccd;

    private static int count = 0;

    public Person(String name, LocalDate birthDate, String address, int gender,
            String phoneNumber, String cccd, String prefix) {
  this.id = generateId(prefix);
  this.name = name;
  this.birthDate = birthDate;
  this.address = address;
  setGender(gender);
  setPhoneNumber(phoneNumber);
  setCccd(cccd);
}


	private String generateId(String prefix) {
        return prefix + String.format("%04d", ++count);
    }

    public void setPhoneNumber(String phoneNumber) {
        if (Pattern.matches("0\\d{9}", phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ. Phải bắt đầu bằng 0 và đủ 10 chữ số.");
        }
    }

    public void setCccd(String cccd) {
        if (Pattern.matches("\\d{12}", cccd)) {
            this.cccd = cccd;
        } else {
            throw new IllegalArgumentException("CCCD không hợp lệ. Phải gồm đúng 12 chữ số.");
        }
    }

    public void setGender(int gender) {
        if (gender < 0 || gender > 2) {
            throw new IllegalArgumentException("Giới tính không hợp lệ. (0: Nữ, 1: Nam, 2: Khác)");
        }
        this.gender = gender;
    }

    // Getter
    public String getId() { return id; }
    public String getName() { return name; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getAddress() { return address; }
    public int getGender() { return gender; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getCccd() { return cccd; }

    // Abstract method
    public abstract void displayInfo();
}
