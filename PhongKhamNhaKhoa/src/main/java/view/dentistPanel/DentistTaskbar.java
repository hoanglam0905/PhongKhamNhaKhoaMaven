package view.dentistPanel;

import javax.swing.*;
import java.awt.*;

public class DentistTaskbar extends JPanel {

    private JLabel lblDoctorName;
    private JLabel lblLanguage;

    public DentistTaskbar() {
        initComponents();
    }

    public DentistTaskbar(String employeeName, String profilePicturePath) {
        initComponents();
        updateDoctorInfo(employeeName, profilePicturePath);
    }

    private void initComponents() {
        // Màu nền thanh taskbar
        setBackground(new Color(51, 255, 255));

        // Label hiển thị tên bác sĩ
        lblDoctorName = new JLabel("Tên BS");
        lblDoctorName.setFont(new Font("Arial", Font.BOLD, 14));
        lblDoctorName.setForeground(Color.WHITE);

        // Label hiển thị ngôn ngữ
        lblLanguage = new JLabel("Tiếng việt");
        lblLanguage.setFont(new Font("Arial", Font.BOLD, 14));
        lblLanguage.setForeground(Color.WHITE);

        // Thêm icon mặc định cho lblDoctorName và lblLanguage
        setLabelIcon(lblDoctorName, "/img/doctor.png", 20, 20);
        setLabelIcon(lblLanguage, "/img/lang.png", 20, 20);

        // Cài đặt GroupLayout
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Horizontal Group
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(0, 0, Short.MAX_VALUE); // đẩy sang phải
        hGroup.addComponent(lblLanguage, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE);
        hGroup.addGap(20);
        hGroup.addComponent(lblDoctorName, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE); // Tăng kích thước để chứa tên dài
        hGroup.addGap(20);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(hGroup)
        );

        // Vertical Group
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(15);
        GroupLayout.ParallelGroup row = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        row.addComponent(lblDoctorName);
        row.addComponent(lblLanguage);
        vGroup.addGroup(row);
        vGroup.addGap(19);

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(vGroup)
        );
    }

    private void setLabelIcon(JLabel label, String iconPath, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            if (icon.getImage() == null) {
                throw new Exception("Icon not found: " + iconPath);
            }
            Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaled));
            label.setIconTextGap(10);
        } catch (Exception e) {
            System.err.println("Không load được icon: " + iconPath + ". Using default icon.");
            // Sử dụng icon mặc định nếu không load được
            try {
                ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/img/doctor.png"));
                Image scaled = defaultIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaled));
                label.setIconTextGap(10);
            } catch (Exception defaultEx) {
                System.err.println("Không load được icon mặc định: /img/doctor.png");
            }
        }
    }

    public void updateDoctorInfo(String employeeName, String profilePicturePath) {
        lblDoctorName.setText(employeeName != null ? employeeName : "Tên BS");
        if (profilePicturePath != null && !profilePicturePath.isEmpty()) {
            setLabelIcon(lblDoctorName, profilePicturePath, 20, 20);
        } else {
            setLabelIcon(lblDoctorName, "/img/doctor.png", 20, 20); // Mặc định nếu không có ảnh
        }
    }

    public JLabel getLblDoctorName() {
        return lblDoctorName;
    }

    public void setLblDoctorName(JLabel lblDoctorName) {
        this.lblDoctorName = lblDoctorName;
    }

    public JLabel getLblLanguage() {
        return lblLanguage;
    }

    public void setLblLanguage(JLabel lblLanguage) {
        this.lblLanguage = lblLanguage;
    }
}