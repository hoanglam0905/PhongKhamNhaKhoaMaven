package service;

import java.sql.Date;

import reponsitory.EmployeeRepository;

public class EmployeeService {
	public static boolean updateEmployee(int id, String name, Date birthDate, String address,
            int gender, String phone, String idCard,
            String username, String password,
            double salary, String role) {
		// (Có thể thêm kiểm tra dữ liệu đầu vào tại đây)

		return EmployeeRepository.updateEmployeeById(
				id, name, birthDate, address, gender, phone, idCard, username, password, salary, role
			);
	}

	public static boolean insertEmployee(String name, Date birthDate, String address, int gender,
            String phone, String cccd, String username, String password,
            double salary, int year, String role) {
		return EmployeeRepository.insertEmployee(name, birthDate, address, gender,
                    phone, cccd, username, password, salary, year, role);
}
}
