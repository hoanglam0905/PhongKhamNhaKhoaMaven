package entity;

import java.util.List;

public interface IEmployee_management {
    void showList();
    void addEmployee(Employee nv);
    void fixEmployee(String id, Employee nvMoi);
    void delEmployee(String id);
    List<Employee> findByName(String ten);
    List<Employee> sortBySpeciality(String chucVu);
}

