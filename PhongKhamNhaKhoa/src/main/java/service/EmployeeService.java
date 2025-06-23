package service;

import java.sql.Date;

import Utils.ValidationUtils;
import reponsitory.EmployeeRepository;

public class EmployeeService {
    public static boolean updateEmployee(int id, String name, Date birthDate, String address,
            int gender, String phone, String idCard,
            String username, String password,
            double salary, String role, String profilePicture) {
        // Kiểm tra số điện thoại và CCCD
        if (!ValidationUtils.isPhoneValid(phone)) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ.");
        }
        if (!ValidationUtils.isCCCDValid(idCard)) {
            throw new IllegalArgumentException("CCCD không hợp lệ.");
        }

        // Lấy thông tin nhân viên hiện tại để so sánh
        Object[] currentEmployee = EmployeeRepository.findById(id);
        if (currentEmployee != null) {
            String currentPhone = (String) currentEmployee[2];
            String currentIdCard = (String) currentEmployee[6];
            String currentUsername = (String) currentEmployee[9];

            // Chỉ kiểm tra sự tồn tại nếu giá trị thay đổi
            if (!phone.equals(currentPhone) && EmployeeRepository.isPhoneExists(phone)) {
                throw new IllegalArgumentException("Số điện thoại đã tồn tại.");
            }
            if (!idCard.equals(currentIdCard) && EmployeeRepository.isCCCDExists(idCard)) {
                throw new IllegalArgumentException("CCCD đã tồn tại.");
            }
            if (!username.equals(currentUsername) && EmployeeRepository.isUsernameExists(username)) {
                throw new IllegalArgumentException("Tên đăng nhập đã tồn tại.");
            }
        }

        return EmployeeRepository.updateEmployeeById(id, name, birthDate, address, gender, phone, idCard, username, password, salary, role, profilePicture);
    }

    public static boolean insertEmployee(String name, Date birthDate, String address, int gender,
            String phone, String cccd, String username, String password,
            double salary, int year, String role) {
        // Kiểm tra các giá trị nhập vào
        if (!ValidationUtils.isPhoneValid(phone)) {
            throw new IllegalArgumentException("Số điện thoại không hợp lệ.");
        }
        if (!ValidationUtils.isCCCDValid(cccd)) {
            throw new IllegalArgumentException("CCCD không hợp lệ.");
        }
        if (EmployeeRepository.isPhoneExists(phone)) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại.");
        }
        if (EmployeeRepository.isCCCDExists(cccd)) {
            throw new IllegalArgumentException("CCCD đã tồn tại.");
        }
        if (EmployeeRepository.isUsernameExists(username)) {
            throw new IllegalArgumentException("Tên đăng nhập đã tồn tại.");
        }

        return EmployeeRepository.insertEmployee(name, birthDate, address, gender, phone, cccd, username, password, salary, year, role);
    }
}