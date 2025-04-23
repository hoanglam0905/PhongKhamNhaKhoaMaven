package dao;

import Utils.JDBCUtil;
import model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public static List<Object[]> getAllPatients() {
        List<Object[]> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection()) {
            String query = "SELECT * FROM Patient";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            int stt = 1;
            while (rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phoneNumber");
                int genderInt = rs.getInt("gender");
                String gender = (genderInt == 1) ? "Nam" : "Nữ";

                // Tính tuổi từ birthDate
                int age = 0;
                Date birthDate = rs.getDate("birthDate");
                if (birthDate != null) {
                    age = java.time.Period.between(
                            birthDate.toLocalDate(),
                            java.time.LocalDate.now()
                    ).getYears();
                }

                list.add(new Object[]{
                        stt++,
                        name,
                        phone,
                        gender,
                        age,
                        "Chưa khám",
                        null
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public static List<Object[]> getAllBillPatients() {
        List<Object[]> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection()) {
            String query = "SELECT * FROM Patient";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            int stt = 1;
            while (rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phoneNumber");
                int genderInt = rs.getInt("gender");
                String gender = (genderInt == 1) ? "Nam" : "Nữ";

                // Tính tuổi từ birthDate
                int age = 0;
                Date birthDate = rs.getDate("birthDate");
                if (birthDate != null) {
                    age = java.time.Period.between(
                            birthDate.toLocalDate(),
                            java.time.LocalDate.now()
                    ).getYears();
                }

                list.add(new Object[]{
                        stt++,
                        name,
                        phone,
                        gender,
                        age,
                        "300000",
                        "Chưa thanh toán",
                        null
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public static List<Patient> getListPatients() {
        List<Patient> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection()) {
            String query = "SELECT * FROM Patient";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            int stt = 1;
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Date birthDate = rs.getDate("birthDate");
                String address = rs.getString("address");
                int genderInt = rs.getInt("gender");
                String gender = (genderInt == 1) ? "Nam" : "Nữ";
                String phone = rs.getString("phoneNumber");
                String idCard = rs.getString("idCard");

                // Tính tuổi từ birthDate
                int age = 0;
                if (birthDate != null) {
                    age = java.time.Period.between(
                            birthDate.toLocalDate(),
                            java.time.LocalDate.now()
                    ).getYears();
                }
                Patient a=new Patient(id,name,birthDate,address,genderInt,phone,idCard);
                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public static List<Object[]> getPatientsChar(String charFind) {
        List<Object[]> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection()) {
            String query = "select * from Patient\n" +
                    "where phoneNumber like '" +
                    charFind +
                    "%' or name like '" +
                    charFind +
                    "%';";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            int stt = 1;
            while (rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phoneNumber");
                int genderInt = rs.getInt("gender");
                String gender = (genderInt == 1) ? "Nam" : "Nữ";

                // Tính tuổi từ birthDate
                int age = 0;
                Date birthDate = rs.getDate("birthDate");
                if (birthDate != null) {
                    age = java.time.Period.between(
                            birthDate.toLocalDate(),
                            java.time.LocalDate.now()
                    ).getYears();
                }

                list.add(new Object[]{
                        stt++,
                        name,
                        phone,
                        gender,
                        age,
                        "Chưa khám",
                        null
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
