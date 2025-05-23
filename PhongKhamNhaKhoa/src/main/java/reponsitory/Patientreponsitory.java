package reponsitory;

import Utils.JDBCUtil;
import model.Patient;

import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Patientreponsitory {

    public static List<Object[]> getAllPatients() {
        List<Object[]> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection()) {
<<<<<<< HEAD:PhongKhamNhaKhoa/src/main/java/reponsitory/Patientreponsitory.java
            String query =
                    "SELECT p.*, e.status AS examStatus " +
                            "FROM Patient p " +
                            "LEFT JOIN Examination e ON p.id = e.patient_id";

=======
            String query = "SELECT p.id, p.name, p.phoneNumber, p.gender, p.birthDate, e.status " +
                          "FROM Patient p " +
                          "LEFT JOIN Examination e ON p.id = e.patient_id";
>>>>>>> minh2:PhongKhamNhaKhoa/src/main/java/dao/PatientDAO.java
            PreparedStatement stmt = conn.prepareStatement(query);
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

<<<<<<< HEAD:PhongKhamNhaKhoa/src/main/java/reponsitory/Patientreponsitory.java
                String status = rs.getString("examStatus");
                if (status == null) {
                    status = "Chưa khám";  // mặc định nếu chưa có cuộc khám nào
=======
                String status = rs.getString("status");
                if (status == null) {
                    status = "Chưa khám";
>>>>>>> minh2:PhongKhamNhaKhoa/src/main/java/dao/PatientDAO.java
                }

                list.add(new Object[]{
                        stt++,
                        name,
                        phone,
                        gender,
                        age,
<<<<<<< HEAD:PhongKhamNhaKhoa/src/main/java/reponsitory/Patientreponsitory.java
                        status,
                        null
=======
                        status
>>>>>>> minh2:PhongKhamNhaKhoa/src/main/java/dao/PatientDAO.java
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
<<<<<<< HEAD:PhongKhamNhaKhoa/src/main/java/reponsitory/Patientreponsitory.java
                    "    (\n" +
                    "        (SELECT COALESCE(SUM(s.price * psd.quantity), 0) \n" +
                    "         FROM PrescriptionServiceDetail psd \n" +
                    "         JOIN Service s ON psd.service_id = s.id \n" +
                    "         WHERE psd.prescription_id = pr.id) \n" +
                    "      + \n" +
                    "        (SELECT COALESCE(SUM(d.price * pd.quantity), 0) \n" +
                    "         FROM PrescriptionDrugDetail pd \n" +
                    "         JOIN Drug d ON pd.drug_id = d.id \n" +
                    "         WHERE pd.prescription_id = pr.id)\n" +
                    "    ) AS TongTien,\n" +
=======
                    "    IFNULL(SUM(d.price * pd.quantity), 0) + IFNULL(SUM(s.price), 0) AS TongTien,\n" +
>>>>>>> minh2:PhongKhamNhaKhoa/src/main/java/dao/PatientDAO.java
                    "    YEAR(p.birthDate) AS NamSinh\n" +
                    "FROM \n" +
                    "    Patient p\n" +
                    "LEFT JOIN \n" +
                    "    Prescription pr ON p.id = pr.patient_id\n" +
                    "GROUP BY \n" +
<<<<<<< HEAD:PhongKhamNhaKhoa/src/main/java/reponsitory/Patientreponsitory.java
                    "    pr.id, p.name, p.birthDate, p.phoneNumber, p.gender, pr.paymentStatus\n" +
                    "ORDER BY \n" +
                    "    pr.id ASC;";


=======
                    "    p.id, pr.id;";
>>>>>>> minh2:PhongKhamNhaKhoa/src/main/java/dao/PatientDAO.java
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            DecimalFormat formatter = new DecimalFormat("#,###");

            while (rs.next()) {
                String paymentStatus = rs.getString("TrangThaiThanhToan");
                if (paymentStatus != null &&
                        (paymentStatus.equals("Chưa thanh toán") || paymentStatus.equals("Đã thanh toán"))) {

                    int invoiceId = rs.getInt("MaHoaDon");
                    String name = rs.getString("TenBenhNhan");
                    int age = rs.getInt("Tuoi");
                    String phone = rs.getString("SoDienThoai");
                    String genderText = rs.getString("GioiTinh");
                    double totalAmountRaw = rs.getDouble("TongTien");
                    String totalAmountFormatted = formatter.format(totalAmountRaw);
                    int birthYear = rs.getInt("NamSinh");

                    list.add(new Object[]{
                            invoiceId,
                            name,
                            phone,
                            genderText,
                            age,
                            totalAmountFormatted + " VND",
                            paymentStatus,
                            null
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

<<<<<<< HEAD:PhongKhamNhaKhoa/src/main/java/reponsitory/Patientreponsitory.java
=======
    public static void updatePatient(Patient patient, int age) throws SQLException {
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "UPDATE Patient SET name = ?, birthDate = ?, address = ?, gender = ?, phoneNumber = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, patient.getName());
            stmt.setDate(2, new Date(java.time.LocalDate.now().minusYears(age).toEpochDay() * 1000 * 60 * 60 * 24));
            stmt.setString(3, patient.getAddress());
            stmt.setInt(4, patient.getGender());
            stmt.setString(5, patient.getPhoneNumber());
            stmt.setInt(6, patient.getId());
            stmt.executeUpdate();
        } catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

>>>>>>> minh2:PhongKhamNhaKhoa/src/main/java/dao/PatientDAO.java
    public static List<Patient> getListPatients() {
        List<Patient> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection()) {
            String query = "SELECT * FROM Patient";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Date birthDate = rs.getDate("birthDate");
                String address = rs.getString("address");
                int genderInt = rs.getInt("gender");
                String phone = rs.getString("phoneNumber");
                String idCard = rs.getString("idCard");

                Patient patient = new Patient(id, name, birthDate, address, genderInt, phone, idCard);
                list.add(patient);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<Object[]> getPatientsChar(String charFind) {
        List<Object[]> list = new ArrayList<>();

        try (Connection conn = JDBCUtil.getConnection()) {
<<<<<<< HEAD:PhongKhamNhaKhoa/src/main/java/reponsitory/Patientreponsitory.java
            String query =
                    "SELECT p.*, e.status AS examStatus " +
                            "FROM Patient p " +
                            "LEFT JOIN Examination e ON p.id = e.patient_id " +
                            "WHERE p.phoneNumber LIKE ? OR p.name LIKE ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, charFind + "%");
            stmt.setString(2, charFind + "%");

=======
            String query = "SELECT p.id, p.name, p.phoneNumber, p.gender, p.birthDate, e.status " +
                          "FROM Patient p " +
                          "LEFT JOIN Examination e ON p.id = e.patient_id " +
                          "WHERE phoneNumber LIKE ? OR name LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            String keyword = charFind + "%";
            stmt.setString(1, keyword);
            stmt.setString(2, keyword);
>>>>>>> minh2:PhongKhamNhaKhoa/src/main/java/dao/PatientDAO.java
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

<<<<<<< HEAD:PhongKhamNhaKhoa/src/main/java/reponsitory/Patientreponsitory.java
                String status = rs.getString("examStatus");
=======
                String status = rs.getString("status");
>>>>>>> minh2:PhongKhamNhaKhoa/src/main/java/dao/PatientDAO.java
                if (status == null) {
                    status = "Chưa khám";
                }

                list.add(new Object[]{
                        stt++,
                        name,
                        phone,
                        gender,
                        age,
<<<<<<< HEAD:PhongKhamNhaKhoa/src/main/java/reponsitory/Patientreponsitory.java
                        status,
                        null
=======
                        status
>>>>>>> minh2:PhongKhamNhaKhoa/src/main/java/dao/PatientDAO.java
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
                        status
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
        } catch (Exception e) {
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
        Patientreponsitory patientDAO = new Patientreponsitory();
        System.out.println(patientDAO.getIdPatient("0987654321"));
    }
}