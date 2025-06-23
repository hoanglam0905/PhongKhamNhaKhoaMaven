package view.admin;

import reponsitory.EmployeeRepository;
import service.EmployeeService;
import view.listPanelMain.MainFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;

public class EditEmployeeInfoDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField profilePictureField;
    private JButton choosePictureButton;
    private JButton saveButton;
    private JButton cancelButton;
    private MainFrame mainFrame;
    private int employeeId;
    private String originalUsername;
    private Object[] employeeData;

    public EditEmployeeInfoDialog(MainFrame mainFrame, int employeeId, String currentUsername, String currentPassword, String currentProfilePicture) {
        super((Frame) null, "Sửa thông tin", true);
        this.mainFrame = mainFrame;
        this.employeeId = employeeId;
        this.originalUsername = currentUsername;
        this.employeeData = EmployeeRepository.findById(employeeId);

        // Áp dụng Nimbus Look and Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    SwingUtilities.updateComponentTreeUI(this);
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Không thể áp dụng Nimbus: " + e.getMessage());
        }

        initComponents(currentUsername, currentPassword, currentProfilePicture);
        setLocationRelativeTo(null);
    }

    private void initComponents(String currentUsername, String currentPassword, String currentProfilePicture) {
        setLayout(new GridLayout(4, 1, 10, 10)); // 4 hàng: 3 cho input, 1 cho nút
        setSize(400, 250); // Điều chỉnh kích thước cho phù hợp

        // Tùy chỉnh giao diện
        getContentPane().setBackground(new Color(240, 240, 245)); // Màu nền nhạt

        // Username
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernamePanel.setBackground(new Color(240, 240, 245));
        JLabel usernameLabel = new JLabel("Tên đăng nhập:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField = new JTextField(currentUsername);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(250, 25));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        add(usernamePanel);

        // Password
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.setBackground(new Color(240, 240, 245));
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField = new JPasswordField(currentPassword);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(250, 25));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        add(passwordPanel);

        // Profile Picture
        JPanel picturePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        picturePanel.setBackground(new Color(240, 240, 245));
        JLabel pictureLabel = new JLabel("Ảnh đại diện:");
        pictureLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        profilePictureField = new JTextField(currentProfilePicture != null ? currentProfilePicture : "");
        profilePictureField.setEditable(false);
        profilePictureField.setFont(new Font("Arial", Font.PLAIN, 14));
        profilePictureField.setPreferredSize(new Dimension(200, 25));
        picturePanel.add(pictureLabel);
        picturePanel.add(profilePictureField);
        add(picturePanel);

        // Nút và nhãn hướng dẫn
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(240, 240, 245));
        choosePictureButton = new JButton("Chọn ảnh");
        choosePictureButton.setFont(new Font("Arial", Font.PLAIN, 14));
        choosePictureButton.setBackground(new Color(0, 120, 215)); // Màu xanh đẹp mắt
        choosePictureButton.setForeground(Color.WHITE);
        choosePictureButton.addActionListener(e -> chooseProfilePicture());
        saveButton = new JButton("Lưu");
        saveButton.setFont(new Font("Arial", Font.PLAIN, 14));
        saveButton.setBackground(new Color(0, 120, 215));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> saveChanges());
        cancelButton = new JButton("Hủy");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setBackground(new Color(220, 53, 69)); // Màu đỏ nhạt
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(choosePictureButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel);
    }

    private void chooseProfilePicture() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "png"));
        fileChooser.setDialogTitle("Chọn ảnh đại diện");
        fileChooser.setApproveButtonText("Xác nhận");
        fileChooser.setPreferredSize(new Dimension(600, 400)); // Kích thước lớn hơn
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Tùy chỉnh giao diện JFileChooser
        UIManager.put("FileChooser.cancelButtonText", "Hủy");
        UIManager.put("FileChooser.lookAndFeelDecorated", true);
        UIManager.put("Button.background", new Color(0, 120, 215)); // Màu nút xanh
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("FileChooser.background", new Color(240, 240, 245)); // Màu nền nhạt

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            profilePictureField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void saveChanges() {
        String newUsername = usernameField.getText().trim();
        String newPassword = new String(passwordField.getPassword()).trim();
        String selectedPicturePath = profilePictureField.getText().trim();

        if (newUsername.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Handle profile picture
        String newProfilePicturePath = (String) (employeeData[11] != null ? employeeData[11] : "");
        if (!selectedPicturePath.isEmpty() && !selectedPicturePath.equals(newProfilePicturePath)) {
            try {
                Path sourcePath = Paths.get(selectedPicturePath);
                String fileName = employeeId + "_" + sourcePath.getFileName().toString();
                Path targetPath = Paths.get("src/main/resources/img/profiles/", fileName);
                Files.createDirectories(targetPath.getParent());
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                newProfilePicturePath = "/img/profiles/" + fileName;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu ảnh đại diện: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Update employee data
        try {
            boolean updated = EmployeeService.updateEmployee(
                employeeId,
                (String) employeeData[1], // name
                employeeData[3] != null && !employeeData[3].toString().isEmpty() ? Date.valueOf((String) employeeData[3]) : null, // birthDate
                (String) employeeData[5], // address
                "Nam".equals(employeeData[4]) ? 1 : 0, // gender
                (String) employeeData[2], // phone
                (String) employeeData[6], // idCard
                newUsername,
                newPassword,
                (Double) employeeData[7], // salary
                (String) employeeData[8], // role
                newProfilePicturePath
            );
            if (updated) {
                // Update LoginPanel credentials
                mainFrame.getLoginPanel().setAcc(newUsername);
                mainFrame.getLoginPanel().setPass(newPassword);
                // Update DentistTaskbar for all panels
                mainFrame.getMainPanel().getDentistTaskbar().updateDoctorInfo((String) employeeData[1], newProfilePicturePath);
                mainFrame.getReceptionistPanel().getDentistTaskbar().updateDoctorInfo((String) employeeData[1], newProfilePicturePath);
                mainFrame.getDrugStorePanel().getDentistTaskbar().updateDoctorInfo((String) employeeData[1], newProfilePicturePath);
                mainFrame.getAdminPanel().getDentistTaskbar().updateDoctorInfo((String) employeeData[1], newProfilePicturePath);
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể cập nhật thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi bất ngờ: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}