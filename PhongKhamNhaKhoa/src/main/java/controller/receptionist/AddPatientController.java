package controller.receptionist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Patient;
import model.Doctor;
import model.Examination;
import service.PatientService;
import service.DoctorService;
import view.receptionistPanel.AddPatientPanel;
import Utils.Utils;

public class AddPatientController implements ActionListener{
    private PatientService patientService;
    private DoctorService doctorService;
    private AddPatientPanel addPatientView;

    public AddPatientController(AddPatientPanel addPatientView) {
        this.patientService = new PatientService();
        this.doctorService = new DoctorService();
        this.addPatientView = addPatientView;
        

//        // Đăng ký listener cho nút Hủy
//        this.addPatientView.addBtnCancelListener(e -> addPatientView.dispose());
    }
    @Override
	public void actionPerformed(ActionEvent e) {
		String src =e.getActionCommand();
		System.out.println("bạn nhấn nút "+src);
		if (src.equals("Thêm Bệnh Nhân và Lịch Hẹn")) {
          String name = addPatientView.getTxtName().getText();
          String birth = addPatientView.txtBirthDate().getText();
          String idCard=addPatientView.getTxtIdCard().getText();
          String address = addPatientView.getTxtAdress().getText();
          int gender = addPatientView.getGenderCombo().getSelectedIndex(); // 0: Nam, 1: Nữ
          String phone = addPatientView.getTxtPhone().getText();

          if (name.isEmpty() || birth.isEmpty() || address.isEmpty() || phone.isEmpty()) {
              JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin.");
              return;
          }

          if (!Utils.validatePhoneNumber(phone)) {
              JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ.");
              return;
          }

          java.sql.Date sqlDate = Utils.parseDate(birth);
          if (sqlDate == null) {
              JOptionPane.showMessageDialog(null, "Ngày sinh không đúng định dạng.");
              return;
          }
          if (!Utils.validateIDCard(idCard)) {
              JOptionPane.showMessageDialog(null, "CCCD không hợp lệ.");
              return;
          } 
          // Lấy bác sĩ được chọn
          Doctor selectedDoctor = addPatientView.getSelectedDoctor();
          if (selectedDoctor == null) {
              JOptionPane.showMessageDialog(null, "Vui lòng chọn bác sĩ.");
              return;
          }

          // Tạo bệnh nhân mới
          Patient newPatient = new Patient(1, name, sqlDate, address, gender, phone, idCard);

          try {
              // Thêm bệnh nhân vào DB và lấy id bệnh nhân mới
              int newPatientId = patientService.addPatientAndReturnId(newPatient);
              if (newPatientId > 0) {
                  System.out.println("Thêm bệnh nhân thành công với ID: " + newPatientId);

                  // Thêm lịch khám (Examination)
                  Examination examination = new Examination();
                  examination.setPatientId(newPatientId);
                  examination.setDoctorId(selectedDoctor.getId());
                  examination.setStatus("Chưa khám");

                  boolean addExamSuccess = doctorService.addExamination(examination);
                  if (addExamSuccess) {
                      JOptionPane.showMessageDialog(null, "Thêm bệnh nhân và lịch hẹn thành công!");
                  } else {
                      JOptionPane.showMessageDialog(null, "Thêm bệnh nhân thành công nhưng thêm lịch hẹn thất bại!");
                  }

                  addPatientView.dispose(); // Đóng cửa sổ
              } else {
                  JOptionPane.showMessageDialog(null, "Thêm bệnh nhân thất bại, thử lại.");
              }
          } catch (ClassNotFoundException | SQLException | IOException ex) {
              ex.printStackTrace();
              JOptionPane.showMessageDialog(null, "Có lỗi xảy ra khi thêm bệnh nhân.");
          }
      }
	}

//    public static void main(String[] args) {
//        new AddPatientController(); // Khởi tạo controller
//    }
}
