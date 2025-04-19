package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import model.Patient;
import service.PatientService;
import leTanView.AddPatientView;
import Utils.Utils;

public class AddPatientController {
    private PatientService patientService;
    private AddPatientView addPatientView;

    public AddPatientController() {
        this.patientService = new PatientService();
        this.addPatientView = new AddPatientView();
        
        // Đăng ký listener cho nút Thêm
        this.addPatientView.addBtnAddListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = addPatientView.getTxtName().getText();
                String birth = addPatientView.getTxtBirthDate().getText();
                String address = addPatientView.getTxtAddress().getText();
                int gender = addPatientView.getGenderCombo().getSelectedIndex(); // 0: Nam, 1: Nữ
                String phone = addPatientView.getTxtPhoneNumber().getText();
                String idCard = addPatientView.getTxtIdCard().getText();

                // Kiểm tra dữ liệu đầu vào
                if (name.isEmpty() || birth.isEmpty() || address.isEmpty() || phone.isEmpty() || idCard.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin.");
                    return;
                }

                // Kiểm tra số điện thoại
                if (!Utils.validatePhoneNumber(phone)) {
                    JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ.");
                    return;
                }

                // Kiểm tra CMND/CCCD
                if (!Utils.validateIDCard(idCard)) {
                    JOptionPane.showMessageDialog(null, "CMND/CCCD không hợp lệ.");
                    return;
                }

                // Chuyển đổi ngày sinh sang dạng java.sql.Date
                java.sql.Date sqlDate = Utils.parseDate(birth);
                if (sqlDate == null) {
                    JOptionPane.showMessageDialog(null, "Ngày sinh không đúng định dạng.");
                    return;
                }

                // Tạo đối tượng bệnh nhân mới
                Patient newPatient = new Patient(1, name, sqlDate, address, gender, phone, idCard);

                // Thêm bệnh nhân thông qua service
                boolean success = false;
				try {
					success = patientService.addPatient(newPatient);
				} catch (ClassNotFoundException | SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                if (success) {
                    JOptionPane.showMessageDialog(null, "Thêm bệnh nhân thành công.");
                    addPatientView.dispose(); // Đóng cửa sổ
                } else {
                    JOptionPane.showMessageDialog(null, "Thêm thất bại, thử lại.");
                }
            }
        });

        // Đăng ký listener cho nút Hủy
        this.addPatientView.addBtnCancelListener(e -> addPatientView.dispose());
    }

    public static void main(String[] args) {
        new AddPatientController(); // Khởi tạo controller
    }
}
