package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Employee_management implements IEmployee_management {
    private List<Employee> danhSachNhanVien;

    public Employee_management() {
        danhSachNhanVien = new ArrayList<>();
    }

    @Override
    public void showList() {
        if (danhSachNhanVien.isEmpty()) {
            System.out.println("Danh sách nhân viên trống.");
        } else {
            for (Employee nv : danhSachNhanVien) {
                nv.displayInfo();
                System.out.println("----------------------------");
            }
        }
    }

    @Override
    public void addEmployee(Employee nv) {
        danhSachNhanVien.add(nv);
        System.out.println("Thêm nhân viên thành công.");
    }

    @Override
    public void fixEmployee(String id, Employee nvMoi) {
        for (int i = 0; i < danhSachNhanVien.size(); i++) {
            if (danhSachNhanVien.get(i).getId().equalsIgnoreCase(id)) {
                danhSachNhanVien.set(i, nvMoi);
                System.out.println("Sửa nhân viên thành công.");
                return;
            }
        }
        System.out.println("Không tìm thấy nhân viên có ID: " + id);
    }

    @Override
    public void delEmployee(String id) {
        boolean removed = danhSachNhanVien.removeIf(nv -> nv.getId().equalsIgnoreCase(id));
        if (removed) {
            System.out.println("Xóa nhân viên thành công.");
        } else {
            System.out.println("Không tìm thấy nhân viên có ID: " + id);
        }
    }

    @Override
    public List<Employee> findByName(String ten) {
        List<Employee> ketQua = danhSachNhanVien.stream()
            .filter(nv -> nv.getName().toLowerCase().contains(ten.toLowerCase()))
            .collect(Collectors.toList());

        return ketQua;
    }

    @Override
    public List<Employee> sortBySpeciality(String chucVu) {
        return danhSachNhanVien.stream()
            .filter(nv -> nv.getPosition().equalsIgnoreCase(chucVu))
            .collect(Collectors.toList());
    }

}

