package view.receptionistPanel;

import dao.PatientDAO;
import model.Patient;
import view.listPanelMain.ReceptionistPanel;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.Consumer;

public class ModifyPatient extends JPanel {
    private JTextField txtName;
    private JTextField txtPhone;
    private JTextField txtAge;
    private JTextField txtGender;
    private JTextField txtAddress;
    private JButton btnModify;
    private ReceptionistPanel receptionistPanel;
    private Patient patient;
    private Consumer<Void> onSaveListener;

    public ModifyPatient(ReceptionistPanel receptionistPanel, Patient patient) {
        this.receptionistPanel = receptionistPanel;
        this.patient = patient;
        initComponents();
        populateFields();
    }

    private void initComponents() {
        setLayout(null); // Sử dụng layout tự do để căn chỉnh chính xác như hình
        setBackground(Color.WHITE);

        // Tiêu đề
        JLabel lblTitle = new JLabel("Chỉnh Sửa Bệnh Nhân");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBounds(10, 10, 200, 30);
        add(lblTitle);

        // Tên bệnh nhân
        JLabel lblName = new JLabel("Họ và tên:");
        lblName.setFont(new Font("Arial", Font.ITALIC, 18));
        lblName.setBounds(210, 140, 100, 20);
        txtName = new JTextField();
        txtName.setBounds(200, 170, 270, 40);
        txtName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblName);
        add(txtName);

        // Số điện thoại
        JLabel lblPhone = new JLabel("Số điện thoại:");
        lblPhone.setFont(new Font("Arial", Font.ITALIC, 18));
        lblPhone.setBounds(560, 140, 150, 20);
        txtPhone = new JTextField();
        txtPhone.setBounds(550, 170, 250, 40);
        txtPhone.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblPhone);
        add(txtPhone);

        // Tuổi
        JLabel lblAge = new JLabel("Tuổi:");
        lblAge.setFont(new Font("Arial", Font.ITALIC, 18));
        lblAge.setBounds(210, 230, 100, 20);
        txtAge = new JTextField();
        txtAge.setBounds(200, 260, 270, 40);
        txtAge.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblAge);
        add(txtAge);

        // Giới tính
        JLabel lblGender = new JLabel("Giới tính:");
        lblGender.setFont(new Font("Arial", Font.ITALIC, 18));
        lblGender.setBounds(560, 230, 100, 20);
        txtGender = new JTextField();
        txtGender.setBounds(550, 260, 250, 40);
        txtGender.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblGender);
        add(txtGender);

        // Địa chỉ
        JLabel lblAddress = new JLabel("Địa chỉ:");
        lblAddress.setFont(new Font("Arial", Font.ITALIC, 18));
        lblAddress.setBounds(210, 320, 100, 20);
        txtAddress = new JTextField();
        txtAddress.setBounds(200, 350, 600, 40);
        txtAddress.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblAddress);
        add(txtAddress);

        // Nút "Chỉnh sửa"
        btnModify = new JButton("Chỉnh sửa");
        btnModify.setFont(new Font("Arial", Font.BOLD, 16));
        btnModify.setBackground(new Color(0, 123, 255)); // Màu xanh dương
        btnModify.setForeground(Color.WHITE);
        btnModify.setBounds(500, 500, 300, 45);
        btnModify.setFocusPainted(false);
        add(btnModify);

        // Xử lý sự kiện nút "Chỉnh sửa"
        btnModify.addActionListener(e -> {
            String name = txtName.getText().trim();
            String phone = txtPhone.getText().trim();
            String ageStr = txtAge.getText().trim();
            String gender = txtGender.getText().trim();
            String address = txtAddress.getText().trim();

            if (name.isEmpty() || phone.isEmpty() || ageStr.isEmpty() || gender.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageStr);
                if (age < 0) {
                    JOptionPane.showMessageDialog(this, "Tuổi phải là số không âm.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Tuổi phải là số nguyên hợp lệ.");
                return;
            }

            int genderInt = gender.equalsIgnoreCase("Nam") ? 1 : (gender.equalsIgnoreCase("Nữ") ? 0 : -1);
            if (genderInt == -1) {
                JOptionPane.showMessageDialog(this, "Giới tính phải là 'Nam' hoặc 'Nữ'.");
                return;
            }

            // Hiển thị dialog xác nhận
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn chỉnh sửa thông tin bệnh nhân này?",
                    "Xác nhận chỉnh sửa",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

         // Trong btnModify.addActionListener
            if (confirm == JOptionPane.OK_OPTION) {
                Patient updatedPatient = new Patient(
                        patient.getId(),
                        name,
                        null, // birthDate sẽ được đặt trong updatePatient
                        address,
                        genderInt,
                        phone,
                        patient.getIdCard()
                );
                try {
                    PatientDAO.updatePatient(updatedPatient, age);
                    JOptionPane.showMessageDialog(this, "Chỉnh sửa thông tin thành công!");
                    clearFields();
                    if (onSaveListener != null) {
                        onSaveListener.accept(null);
                    }
                    receptionistPanel.getCardLayout().show(receptionistPanel.getCenterPanel(), "ShowPatientsReceptionist");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật thông tin: " + ex.getMessage());
                } catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
    }

    private void populateFields() {
        if (patient != null) {
            txtName.setText(patient.getName());
            txtPhone.setText(patient.getPhoneNumber());
            txtAddress.setText(patient.getAddress());
            txtGender.setText(patient.getGender() == 1 ? "Nam" : "Nữ");
            if (patient.getBirthDate() != null) {
                int age = java.time.Period.between(
                        patient.getBirthDate().toLocalDate(),
                        java.time.LocalDate.now()
                ).getYears();
                txtAge.setText(String.valueOf(age));
            } else {
                txtAge.setText("0");
            }
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtPhone.setText("");
        txtAge.setText("");
        txtGender.setText("");
        txtAddress.setText("");
    }

    public void setOnSaveListener(Consumer<Void> listener) {
        this.onSaveListener = listener;
    }

    // Getters và Setters
    public JTextField getTxtName() { return txtName; }
    public void setTxtName(JTextField txtName) { this.txtName = txtName; }
    public JTextField getTxtPhone() { return txtPhone; }
    public void setTxtPhone(JTextField txtPhone) { this.txtPhone = txtPhone; }
    public JTextField getTxtAge() { return txtAge; }
    public void setTxtAge(JTextField txtAge) { this.txtAge = txtAge; }
    public JTextField getTxtGender() { return txtGender; }
    public void setTxtGender(JTextField txtGender) { this.txtGender = txtGender; }
    public JTextField getTxtAddress() { return txtAddress; }
    public void setTxtAddress(JTextField txtAddress) { this.txtAddress = txtAddress; }
    public JButton getBtnModify() { return btnModify; }
    public void setBtnModify(JButton btnModify) { this.btnModify = btnModify; }
}