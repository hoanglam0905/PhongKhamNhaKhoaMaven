package reponsitory;

import Utils.JDBCUtil;
import model.Employee;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeReponsitory {
    public static List<Employee> getEmployees() {
        try {
            Connection con= JDBCUtil.getConnection();
            Statement statement= con.createStatement();
            ResultSet rs= statement.executeQuery("select * from Employee");

            List<Employee> employees= new ArrayList<Employee>();
            while (rs.next()) {
                int id= rs.getInt("id");
                String name= rs.getString("name");
                Date birthday= rs.getDate("birthDate");
                String address= rs.getString("address");
                int gender= rs.getInt("gender");
                String phoneNumber= rs.getString("phoneNumber");
                String idCard= rs.getString("idCard");
                String username= rs.getString("username");
                String password= rs.getString("password");
                double salary= rs.getDouble("salary");
                int experience= rs.getInt("experienceYears");
                String role= rs.getString("role");

                employees.add(new Employee(id,name,birthday,address,gender,phoneNumber,idCard,username,password,salary,experience,role));
            }
            rs.close();
            return employees;
        } catch (IOException e){
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}