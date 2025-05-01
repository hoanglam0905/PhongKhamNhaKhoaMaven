package reponsitory.dao;

import Utils.JDBCUtil;
import model.Patient;

import java.io.IOException;
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
            String query = "SELECT \n" +
                    "    pr.id AS MaHoaDon,\n" +
                    "    p.name AS TenBenhNhan,\n" +
                    "    TIMESTAMPDIFF(YEAR, p.birthDate, CURDATE()) AS Tuoi,\n" +
                    "    p.phoneNumber AS SoDienThoai,\n" +
                    "    CASE \n" +
                    "        WHEN p.gender = 1 THEN 'Nam'\n" +
                    "        WHEN p.gender = 0 THEN 'Nữ'\n" +
                    "        ELSE 'Khác'\n" +
                    "    END AS GioiTinh,\n" +
                    "    pr.paymentStatus AS TrangThaiThanhToan,\n" +
                    "    -- Tổng tiền = tổng giá thuốc + tổng giá dịch vụ\n" +
                    "    IFNULL(SUM(d.price * pd.quantity), 0) + IFNULL(SUM(s.price), 0) AS TongTien,\n" +
                    "    YEAR(p.birthDate) AS NamSinh\n" +
                    "FROM \n" +
                    "    Patient p\n" +
                    "LEFT JOIN \n" +
                    "    Prescription pr ON p.id = pr.patient_id\n" +
                    "LEFT JOIN \n" +
                    "    PrescriptionDrugDetail pd ON pr.id = pd.prescription_id\n" +
                    "LEFT JOIN \n" +
                    "    Drug d ON pd.drug_id = d.id\n" +
                    "LEFT JOIN \n" +
                    "    PrescriptionServiceDetail psd ON pr.id = psd.prescription_id\n" +
                    "LEFT JOIN \n" +
                    "    Service s ON psd.service_id = s.id\n" +
                    "GROUP BY \n" +
                    "    p.id, pr.id;\n";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int invoiceId = rs.getInt("MaHoaDon");
                String name = rs.getString("TenBenhNhan");
                int age = rs.getInt("Tuoi");
                String phone = rs.getString("SoDienThoai");
                String genderText = rs.getString("GioiTinh");
                String paymentStatus = rs.getString("TrangThaiThanhToan");
                double totalAmount = rs.getDouble("TongTien");
                int birthYear = rs.getInt("NamSinh");

                list.add(new Object[]{
                        invoiceId,
                        name,
                        phone,
                        genderText,
                        age,
                        totalAmount,
                        paymentStatus,
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
    public static List<Object[]> getPatientsCharofDentist(String charFind, String id_doctor) {
        List<Object[]> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection()) {
            String query = "SELECT " +
                    "    p.name, p.phoneNumber, p.gender, p.birthDate, e.status " +
                    "FROM Examination e " +
                    "JOIN Patient p ON e.patient_id = p.id " +
                    "WHERE e.doctor_id = ? " +
                    "AND (" +
                    "      LOWER(p.name) LIKE ? " +
                    "   OR LOWER(p.phoneNumber) LIKE ?" +
                    ")";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(id_doctor));
            String keyword = "%" + charFind.toLowerCase() + "%";
            stmt.setString(2, keyword);
            stmt.setString(3, keyword);

            ResultSet rs = stmt.executeQuery();
            int stt = 1;

            while (rs.next()) {
                String name = rs.getString("name");
                String phone = rs.getString("phoneNumber");
                int genderInt = rs.getInt("gender");
                String gender = (genderInt == 1) ? "Nam" : "Nữ";

                int age = 0;
                Date birthDate = rs.getDate("birthDate");
                if (birthDate != null) {
                    age = java.time.Period.between(
                            birthDate.toLocalDate(),
                            java.time.LocalDate.now()
                    ).getYears();
                }

                String status = rs.getString("status");
                list.add(new Object[]{
                        stt++,
                        name,
                        phone,
                        gender,
                        age,
                        status,
                        null // Cột "Khám"
                });
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static int getIdPatient(String phoneNumber) {
        try {
            String sql = "SELECT id FROM Patient WHERE phoneNumber LIKE ?";
            Connection conn = JDBCUtil.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, phoneNumber);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1;
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Object[]> getPatientOfDentist(String id_dentist) {
        List<Object[]> list = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection()) {
            String query = "SELECT " +
                    "    p.name AS TenBenhNhan, " +
                    "    p.phoneNumber AS SoDienThoai, " +
                    "    CASE " +
                    "        WHEN p.gender = 1 THEN 'Nam' " +
                    "        WHEN p.gender = 0 THEN 'Nữ' " +
                    "        ELSE 'Khác' " +
                    "    END AS GioiTinh, " +
                    "    TIMESTAMPDIFF(YEAR, p.birthDate, CURDATE()) AS Tuoi, " +
                    "    e.status AS TrangThaiKham " +
                    "FROM Examination e " +
                    "JOIN Patient p ON e.patient_id = p.id " +
                    "WHERE e.doctor_id = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(id_dentist));
            ResultSet rs = stmt.executeQuery();

            int stt = 1;
            while (rs.next()) {
                String name = rs.getString("TenBenhNhan");
                String phone = rs.getString("SoDienThoai");
                String gender = rs.getString("GioiTinh");
                int age = rs.getInt("Tuoi");
                String status = rs.getString("TrangThaiKham");

                list.add(new Object[]{stt++, name, phone, gender, age, status});
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public static void main(String[] args) {
        PatientDAO patientDAO = new PatientDAO();
        System.out.println(patientDAO.getIdPatient("0987654321"));
    }
}
