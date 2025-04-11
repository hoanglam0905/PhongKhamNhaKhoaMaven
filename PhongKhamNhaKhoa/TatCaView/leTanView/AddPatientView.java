package leTanView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.PatientController;
import model.Patient;
import Utils.Utils;

public class AddPatientView extends JFrame {
    private JTextField txtName, txtBirthDate, txtAddress, txtPhoneNumber, txtIdCard;
    private JComboBox<String> genderCombo;
    private JButton btnAdd, btnCancel;
    

    public AddPatientView() {
        setTitle("Thêm bệnh nhân");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        Font labelFont = new Font("Arial", Font.PLAIN, 14);
        Font inputFont = new Font("Arial", Font.PLAIN, 14);

        JLabel lblName = new JLabel("Họ và tên:");
        lblName.setBounds(30, 30, 100, 25);
        lblName.setFont(labelFont);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(140, 30, 300, 25);
        txtName.setFont(inputFont);
        add(txtName);

        JLabel lblBirthDate = new JLabel("Ngày sinh (dd/MM/yyyy):");
        lblBirthDate.setBounds(30, 70, 160, 25);
        lblBirthDate.setFont(labelFont);
        add(lblBirthDate);

        txtBirthDate = new JTextField();
        txtBirthDate.setBounds(200, 70, 240, 25);
        txtBirthDate.setFont(inputFont);
        add(txtBirthDate);

        JLabel lblGender = new JLabel("Giới tính:");
        lblGender.setBounds(30, 110, 100, 25);
        lblGender.setFont(labelFont);
        add(lblGender);

        genderCombo = new JComboBox<>(new String[] { "Nam", "Nữ" });
        genderCombo.setBounds(140, 110, 300, 25);
        add(genderCombo);

        JLabel lblAddress = new JLabel("Địa chỉ:");
        lblAddress.setBounds(30, 150, 100, 25);
        lblAddress.setFont(labelFont);
        add(lblAddress);

        txtAddress = new JTextField();
        txtAddress.setBounds(140, 150, 300, 25);
        txtAddress.setFont(inputFont);
        add(txtAddress);

        JLabel lblPhoneNumber = new JLabel("Số điện thoại:");
        lblPhoneNumber.setBounds(30, 190, 100, 25);
        lblPhoneNumber.setFont(labelFont);
        add(lblPhoneNumber);

        txtPhoneNumber = new JTextField();
        txtPhoneNumber.setBounds(140, 190, 300, 25);
        txtPhoneNumber.setFont(inputFont);
        add(txtPhoneNumber);

        JLabel lblIdCard = new JLabel("CMND/CCCD:");
        lblIdCard.setBounds(30, 230, 100, 25);
        lblIdCard.setFont(labelFont);
        add(lblIdCard);

        txtIdCard = new JTextField();
        txtIdCard.setBounds(140, 230, 300, 25);
        txtIdCard.setFont(inputFont);
        add(txtIdCard);

        btnAdd = new JButton("Thêm");
        btnAdd.setBounds(140, 280, 100, 30);
        add(btnAdd);

        btnCancel = new JButton("Hủy");
        btnCancel.setBounds(260, 280, 100, 30);
        add(btnCancel);

//        PatientController controller = new PatientController();
//
//        btnAdd.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String name = txtName.getText();
//                String birth = txtBirthDate.getText();
//                String address = txtAddress.getText();
//                int gender = genderCombo.getSelectedIndex(); // 0: Nam, 1: Nữ
//                String phone = txtPhoneNumber.getText();
//                String idCard = txtIdCard.getText();
//
//                if (name.isEmpty() || birth.isEmpty() || address.isEmpty() || phone.isEmpty() || idCard.isEmpty()) {
//                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin.");
//                    return;
//                }
//
//                if (!Utils.validatePhoneNumber(phone)) {
//                    JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ.");
//                    return;
//                }
//
//                if (!Utils.validateIDCard(idCard)) {
//                    JOptionPane.showMessageDialog(null, "CMND/CCCD không hợp lệ.");
//                    return;
//                }
//
//                java.sql.Date sqlDate = Utils.parseDate(birth);
//                if (sqlDate == null) {
//                    JOptionPane.showMessageDialog(null, "Ngày sinh không đúng định dạng.");
//                    return;
//                }
//
//                Patient newPatient = new Patient(1, name, sqlDate, address, gender, phone, idCard);
//                boolean success = controller.addPatient(newPatient);
//                if (success) {
//                    JOptionPane.showMessageDialog(null, "Thêm bệnh nhân thành công.");
//                    dispose(); // Đóng cửa sổ
//                } else {
//                    JOptionPane.showMessageDialog(null, "Thêm thất bại, thử lại.");
//                }
//            }
//        });
//
//        btnCancel.addActionListener(e -> dispose());

        setVisible(true);
    }

    public JButton getBtnAdd() {
		return btnAdd;
	}

	public void setBtnAdd(JButton btnAdd) {
		this.btnAdd = btnAdd;
	}

	public JButton getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(JButton btnCancel) {
		this.btnCancel = btnCancel;
	}

	public JTextField getTxtName() {
		return txtName;
	}

	public void setTxtName(JTextField txtName) {
		this.txtName = txtName;
	}

	public JTextField getTxtBirthDate() {
		return txtBirthDate;
	}

	public void setTxtBirthDate(JTextField txtBirthDate) {
		this.txtBirthDate = txtBirthDate;
	}

	public JTextField getTxtAddress() {
		return txtAddress;
	}

	public void setTxtAddress(JTextField txtAddress) {
		this.txtAddress = txtAddress;
	}

	public JTextField getTxtPhoneNumber() {
		return txtPhoneNumber;
	}

	public void setTxtPhoneNumber(JTextField txtPhoneNumber) {
		this.txtPhoneNumber = txtPhoneNumber;
	}

	public JTextField getTxtIdCard() {
		return txtIdCard;
	}

	public void setTxtIdCard(JTextField txtIdCard) {
		this.txtIdCard = txtIdCard;
	}

	public JComboBox<String> getGenderCombo() {
		return genderCombo;
	}

	public void setGenderCombo(JComboBox<String> genderCombo) {
		this.genderCombo = genderCombo;
	}

	public static void main(String[] args) {
        new AddPatientView();
    }
	
	public void addBtnAddListener(ActionListener listener) {
        btnAdd.addActionListener(listener);
    }
	
	public void addBtnCancelListener(ActionListener listener) {
        btnCancel.addActionListener(listener);
    }
}
