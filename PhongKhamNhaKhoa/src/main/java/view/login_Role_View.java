package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class login_Role_View extends JFrame {
    public login_Role_View() {
        setTitle("Đăng Nhập Hệ Thống");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Chọn chức vụ để đăng nhập", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 60, 20, 60));

        JButton doctorBtn = new JButton("Bác sĩ");
        doctorBtn.setOpaque(true);             
        doctorBtn.setFocusPainted(false);
        doctorBtn.setBorderPainted(false); 
        JButton receptionistBtn = new JButton("Nhân viên lễ tân");
        receptionistBtn.setOpaque(true);             
        receptionistBtn.setFocusPainted(false);
        receptionistBtn.setBorderPainted(false); 
        JButton patientBtn = new JButton("Bệnh nhân");
        patientBtn.setOpaque(true);             
        patientBtn.setFocusPainted(false);
        patientBtn.setBorderPainted(false); 

        doctorBtn.addActionListener(e -> openLoginForm("Bác sĩ"));
        receptionistBtn.addActionListener(e -> openLoginForm("Nhân viên lễ tân"));
        patientBtn.addActionListener(e -> openLoginForm("Bệnh nhân"));

        buttonPanel.add(doctorBtn);
        buttonPanel.add(receptionistBtn);
        buttonPanel.add(patientBtn);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void openLoginForm(String role) {
        this.dispose();

        new login_View(role);
    }

    public static void main(String[] args) {
        new login_Role_View();
    }
}
