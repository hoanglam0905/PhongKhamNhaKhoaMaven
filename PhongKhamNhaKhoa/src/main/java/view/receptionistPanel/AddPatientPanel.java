package view.receptionistPanel;

import javax.swing.*;
import java.awt.*;

public class AddPatientPanel extends JPanel {

    private JTextField txtName;
    private JTextField txtPhone;
    private JTextField txtAge;
    private JTextField txtGender;
    private JTextField txtAdress;
    private JTextField txtDoctor;
    private JButton btnAddPatient;

    public AddPatientPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(null); // Sử dụng layout tự do để căn chỉnh chính xác như hình
        setBackground(Color.WHITE);

        // Tiêu đề
        JLabel lblTitle = new JLabel("Thêm Bệnh Nhân");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBounds(10, 10, 200, 30);
        add(lblTitle);


        // Tên bệnh nhân
        JLabel lblName = new JLabel("Họ và tên:");
        lblName.setFont(new Font("Arial", Font.ITALIC, 16));
        lblName.setBounds(50, 50, 100, 20);
        txtName = new JTextField();
        txtName.setBounds(50, 80, 250, 30);
        txtName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblName);
        add(txtName);

        // Số điện thoại
        JLabel lblPhone = new JLabel("Số điện thoại:");
        lblPhone.setFont(new Font("Arial", Font.ITALIC, 16));
        lblPhone.setBounds(400, 50 , 100, 20);
        txtPhone = new JTextField();
        txtPhone.setBounds(400, 80 , 200, 30);
        txtPhone.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblPhone);
        add(txtPhone);

        // Tuổi
        JLabel lblAge = new JLabel("Tuổi:");
        lblAge.setFont(new Font("Arial", Font.ITALIC, 16));
        lblAge.setBounds(50, 130, 100, 20);
        txtAge = new JTextField();
        txtAge.setBounds(50, 160 , 250, 30);
        txtAge.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblAge);
        add(txtAge);

        // Giới tính
        JLabel lblGender = new JLabel("Giới tính:");
        lblGender.setFont(new Font("Arial", Font.ITALIC, 16));
        lblGender.setBounds(400, 130 , 100, 20);
        txtGender = new JTextField();
        txtGender.setBounds(400, 160, 200, 30);
        txtGender.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblGender);
        add(txtGender);

        // Chẩn đoán
        JLabel lblAdress = new JLabel("Địa chỉ:");
        lblAdress.setFont(new Font("Arial", Font.ITALIC, 16));
        lblAdress.setBounds(50, 210 , 100, 20);
        txtAdress = new JTextField();
        txtAdress.setBounds(50, 240, 550, 30);
        txtAdress.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblAdress);
        add(txtAdress);
        
        JLabel lblDoctor = new JLabel("Bác sĩ:");
        lblDoctor.setFont(new Font("Arial", Font.ITALIC, 16));
        lblDoctor.setBounds(50, 290 , 100, 20);
        txtDoctor = new JTextField();
        txtDoctor.setBounds(50, 320, 550, 30);
        txtDoctor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblDoctor);
        add(txtDoctor);

        // Nút "Thêm Bệnh Nhân và Lịch Hẹn"
        btnAddPatient = new JButton("Thêm Bệnh Nhân và Lịch Hẹn");
        btnAddPatient.setFont(new Font("Arial", Font.BOLD, 14));
        btnAddPatient.setBackground(new Color(0, 123, 255)); // Màu xanh dương
        btnAddPatient.setForeground(Color.WHITE);
        btnAddPatient.setBounds(355, 370, 250, 40);
        btnAddPatient.setFocusPainted(false);
        add(btnAddPatient);

        // Xử lý sự kiện nút "Thêm Bệnh Nhân và Lịch Hẹn"
        btnAddPatient.addActionListener(e -> {
            String name = txtName.getText().trim();
            String phone = txtPhone.getText().trim();
            String age = txtAge.getText().trim();
            String gender = txtGender.getText().trim();
            String adress = txtAdress.getText().trim();
            String doctor = txtDoctor.getText().trim();

            if (name.isEmpty() || phone.isEmpty() || age.isEmpty() || gender.isEmpty() || adress.isEmpty() || doctor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            // Thực hiện thêm bệnh nhân (có thể thêm logic lưu vào cơ sở dữ liệu ở đây)
            JOptionPane.showMessageDialog(this, "Thêm bệnh nhân thành công!");
            clearFields();
        });
    }

    private void clearFields() {
        txtName.setText("");
        txtPhone.setText("");
        txtAge.setText("");
        txtGender.setText("");
        txtAdress.setText("");
        txtDoctor.setText("");
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
    public JTextField getTxtAdress() { return txtAdress; }
    public void setTxtAdress(JTextField txtAdress) { this.txtAdress = txtAdress; }
    public JTextField getTxtDoctor() { return txtDoctor; }
    public void setTxtDoctor(JTextField txtDoctor) { this.txtDoctor = txtDoctor; }
    public JButton getBtnAddPatient() { return btnAddPatient; }
    public void setBtnAddPatient(JButton btnAddPatient) { this.btnAddPatient = btnAddPatient; }
}