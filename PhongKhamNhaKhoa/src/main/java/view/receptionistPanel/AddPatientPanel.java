package view.receptionistPanel;

import java.util.List;
import model.Doctor;
import reponsitory.DoctorRepository;
import javax.swing.*;

import controller.receptionist.AddPatientController;

import java.awt.*;
import java.awt.event.ActionListener;

public class AddPatientPanel extends JPanel {

    private JTextField txtName;
    private JTextField txtPhone;
    private JTextField txtBirthDate;
    private JComboBox<String> genderCombo;
    private JTextField txtIdCard;
    private JTextField txtAdress;
    private JComboBox<Doctor> doctorCombo;
    private JButton btnAdd, btnCancel;

    public AddPatientPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Thêm Bệnh Nhân");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBounds(10, 10, 200, 25);
        add(lblTitle);

        JLabel lblName = new JLabel("Họ và tên:");
        lblName.setFont(new Font("Arial", Font.ITALIC, 16));
        lblName.setBounds(30, 40, 100, 20);
        txtName = new JTextField();
        txtName.setBounds(30, 65, 220, 28);
        txtName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblName);
        add(txtName);

        JLabel lblPhone = new JLabel("Số điện thoại:");
        lblPhone.setFont(new Font("Arial", Font.ITALIC, 16));
        lblPhone.setBounds(300, 40, 100, 20);
        txtPhone = new JTextField();
        txtPhone.setBounds(300, 65, 200, 28);
        txtPhone.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblPhone);
        add(txtPhone);

        JLabel lblBirthDate = new JLabel("Ngày sinh (dd/MM/yyyy):");
        lblBirthDate.setFont(new Font("Arial", Font.ITALIC, 16));
        lblBirthDate.setBounds(30, 105, 220, 20);
        txtBirthDate = new JTextField();
        txtBirthDate.setBounds(30, 130, 220, 28);
        txtBirthDate.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblBirthDate);
        add(txtBirthDate);

        JLabel lblGender = new JLabel("Giới tính:");
        lblGender.setFont(new Font("Arial", Font.ITALIC, 16));
        lblGender.setBounds(300, 105, 100, 20);
        genderCombo = new JComboBox<>(new String[] { "Nữ", "Nam" });
        genderCombo.setBounds(300, 130, 200, 28);
        add(lblGender);
        add(genderCombo);

        JLabel lblIdCard = new JLabel("CCCD:");
        lblIdCard.setFont(new Font("Arial", Font.ITALIC, 16));
        lblIdCard.setBounds(30, 170, 100, 20);
        txtIdCard = new JTextField();
        txtIdCard.setBounds(30, 195, 470, 28);
        txtIdCard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblIdCard);
        add(txtIdCard);

        JLabel lblAdress = new JLabel("Địa chỉ:");
        lblAdress.setFont(new Font("Arial", Font.ITALIC, 16));
        lblAdress.setBounds(30, 235, 100, 20);
        txtAdress = new JTextField();
        txtAdress.setBounds(30, 260, 470, 28);
        txtAdress.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblAdress);
        add(txtAdress);

        JLabel lblDoctor = new JLabel("Bác sĩ:");
        lblDoctor.setFont(new Font("Arial", Font.ITALIC, 16));
        lblDoctor.setBounds(30, 300, 100, 20);
        doctorCombo = new JComboBox<>();
        doctorCombo.setBounds(30, 325, 470, 28);
        doctorCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblDoctor);
        add(doctorCombo);

        loadDoctors();

        btnAdd = new JButton("Thêm Bệnh Nhân và Lịch Hẹn");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setBackground(new Color(0, 123, 255));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBounds(270, 380, 230, 36);
        btnAdd.setFocusPainted(false);
        add(btnAdd);

        btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setBackground(new Color(0, 123, 255));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBounds(160, 380, 90, 36);
        btnCancel.setFocusPainted(false);
        add(btnCancel);

        setVisible(true);

        ActionListener addPatientController = new AddPatientController(this);
        btnAdd.addActionListener(addPatientController);
    }

    private void clearFields() {
        txtName.setText("");
        txtPhone.setText("");
        txtBirthDate.setText("");
        txtAdress.setText("");
        doctorCombo.setSelectedIndex(-1);
    }

    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtPhone() { return txtPhone; }
    public JTextField txtBirthDate() { return txtBirthDate; }
    public void txtBirthDate(JTextField txtBirthDate) { this.txtBirthDate = txtBirthDate; }
    public JComboBox<String> getGenderCombo() { return genderCombo; }
    public void setGenderCombo(JComboBox<String> genderCombo) { this.genderCombo = genderCombo; }
    public JTextField getTxtIdCard() { return txtIdCard; }
    public JTextField getTxtAdress() { return txtAdress; }
    public JComboBox<Doctor> getDoctorCombo() { return doctorCombo; }
    public JButton getBtnAddPatient() { return btnAdd; }

    public void addBtnAddListener(ActionListener listener) {
        btnAdd.addActionListener(listener);
    }

    public void addBtnCancelListener(ActionListener listener) {
        btnCancel.addActionListener(listener);
    }

    public void dispose() {
        // Placeholder if needed
    }

    private void loadDoctors() {
        try {
            List<Doctor> doctors = DoctorRepository.getAllDoctors();
            for (Doctor doctor : doctors) {
                doctorCombo.addItem(doctor);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không thể tải danh sách bác sĩ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Doctor getSelectedDoctor() {
        return (Doctor) doctorCombo.getSelectedItem();
    }

    public void setSelectedDoctor(Doctor doctor) {
        doctorCombo.setSelectedItem(doctor);
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Thêm bệnh nhân");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(550, 500); // Kích thước nhỏ hơn
//        frame.setLocationRelativeTo(null);
//        frame.add(new AddPatientPanel());
//        frame.setVisible(true);
//    }
}
