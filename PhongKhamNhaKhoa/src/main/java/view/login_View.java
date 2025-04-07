package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class login_View extends JFrame {
    public login_View(String role) {
        setTitle("Đăng nhập - " + role);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel roleLabel = new JLabel("Đăng nhập với tư cách: " + role);
        roleLabel.setBounds(70, 20, 300, 30);
        roleLabel.setFont(new Font("Arial", Font.BOLD, 15));

        JLabel usernameLabel = new JLabel("Tài khoản:");
        usernameLabel.setBounds(50, 70, 100, 25);
        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 70, 180, 25);

        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordLabel.setBounds(50, 110, 100, 25);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 110, 180, 25);

        JButton loginButton = new JButton("Đăng nhập");
        loginButton.setBounds(150, 160, 100, 30);

        loginButton.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công với vai trò: " + role);
            }
        });

        add(roleLabel);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);

        setVisible(true);
    }
}
