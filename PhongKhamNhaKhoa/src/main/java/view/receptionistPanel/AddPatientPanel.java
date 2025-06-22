package view.receptionistPanel;

import java.util.Calendar;
import java.util.List;
import model.Doctor;
import reponsitory.DoctorRepository;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import controller.receptionist.AddPatientController;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class AddPatientPanel extends JPanel {

    private JTextField txtName;
    private JTextField txtPhone;
    private JComboBox<String> genderCombo;
    private JTextField txtIdCard;
    private JTextField txtAdress;
    private JComboBox<Doctor> doctorCombo;
    private JDateChooser dateChooser;
    private JButton btnAdd, btnCancel;
    private JPanel contentPanel;
    private JLabel lblGuardianName, lblGuardianPhone, lblGuardianIdCard, lblRelationship;
    private JTextField txtGuardianName, txtGuardianPhone, txtGuardianIdCard, txtRelationship;
    private JScrollPane scrollPane;

    // Kích thước mặc định ban đầu
    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 450;

    public AddPatientPanel() {
        initComponents();
    }

    private void initComponents() {
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout()); // Sử dụng GridBagLayout để căn giữa scrollPane

        // Panel chứa nội dung với GroupLayout
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        GroupLayout layout = new GroupLayout(contentPanel);
        contentPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel lblTitle = new JLabel("Thêm Bệnh Nhân");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblName = new JLabel("Họ và tên:");
        lblName.setFont(new Font("Arial", Font.ITALIC, 14));
        txtName = new JTextField();
        txtName.setPreferredSize(new Dimension(180, 35));

        JLabel lblPhone = new JLabel("Số điện thoại:");
        lblPhone.setFont(new Font("Arial", Font.ITALIC, 14));
        txtPhone = new JTextField();
        txtPhone.setPreferredSize(new Dimension(180, 35));

        JLabel lblBirthDate = new JLabel("Ngày sinh:");
        lblBirthDate.setFont(new Font("Arial", Font.ITALIC, 14));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd-MM-yyyy");
        Calendar today = Calendar.getInstance();

        // Tính ngày nhỏ nhất: hôm nay - 60 năm
        Calendar min = (Calendar) today.clone();
        min.add(Calendar.YEAR, -60);

        // Tính ngày lớn nhất: hôm nay
        Calendar max = (Calendar) today.clone();
        dateChooser.setMinSelectableDate(min.getTime());
        dateChooser.setMaxSelectableDate(max.getTime());

        JLabel lblGender = new JLabel("Giới tính:");
        lblGender.setFont(new Font("Arial", Font.ITALIC, 14));
        genderCombo = new JComboBox<>(new String[]{"Nữ", "Nam"});
        genderCombo.setPreferredSize(new Dimension(180, 35));

        JLabel lblIdCard = new JLabel("CCCD:");
        lblIdCard.setFont(new Font("Arial", Font.ITALIC, 14));
        txtIdCard = new JTextField();
        txtIdCard.setPreferredSize(new Dimension(0, 35));

        JLabel lblAdress = new JLabel("Địa chỉ:");
        lblAdress.setFont(new Font("Arial", Font.ITALIC, 14));
        txtAdress = new JTextField();
        txtAdress.setPreferredSize(new Dimension(0, 35));

        JLabel lblDoctor = new JLabel("Bác sĩ:");
        lblDoctor.setFont(new Font("Arial", Font.ITALIC, 14));
        doctorCombo = new JComboBox<>();
        doctorCombo.setPreferredSize(new Dimension(0, 35));

        // Các trường thông tin người giám hộ
        lblGuardianName = new JLabel("Tên người giám hộ:");
        lblGuardianName.setFont(new Font("Arial", Font.ITALIC, 14));
        txtGuardianName = new JTextField();
        txtGuardianName.setPreferredSize(new Dimension(0, 35));

        lblGuardianPhone = new JLabel("Số điện thoại người giám hộ:");
        lblGuardianPhone.setFont(new Font("Arial", Font.ITALIC, 14));
        txtGuardianPhone = new JTextField();
        txtGuardianPhone.setPreferredSize(new Dimension(0, 35));

        lblGuardianIdCard = new JLabel("CCCD người giám hộ:");
        lblGuardianIdCard.setFont(new Font("Arial", Font.ITALIC, 14));
        txtGuardianIdCard = new JTextField();
        txtGuardianIdCard.setPreferredSize(new Dimension(0, 35));

        lblRelationship = new JLabel("Mối quan hệ với bệnh nhân:");
        lblRelationship.setFont(new Font("Arial", Font.ITALIC, 14));
        txtRelationship = new JTextField();
        txtRelationship.setPreferredSize(new Dimension(0, 35));

        // Ẩn các trường người giám hộ ban đầu
        lblGuardianName.setVisible(false);
        txtGuardianName.setVisible(false);
        lblGuardianPhone.setVisible(false);
        txtGuardianPhone.setVisible(false);
        lblGuardianIdCard.setVisible(false);
        txtGuardianIdCard.setVisible(false);
        lblRelationship.setVisible(false);
        txtRelationship.setVisible(false);

        btnAdd = new JButton("Thêm Bệnh Nhân và Lịch Hẹn");
        btnAdd.setBackground(new Color(0, 123, 255));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setPreferredSize(new Dimension(200, 35));

        btnCancel = new JButton("Hủy");
        btnCancel.setBackground(new Color(0, 123, 255));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        btnCancel.setPreferredSize(new Dimension(80, 35));

        // Load danh sách bác sĩ
        loadDoctors();

        // Thiết lập layout ngang & dọc cho contentPanel
        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addComponent(lblTitle)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup()
                        .addComponent(lblName)
                        .addComponent(txtName, 180, 180, 180)
                        .addComponent(lblBirthDate)
                        .addComponent(dateChooser, 180, 180, 180))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(lblPhone)
                        .addComponent(txtPhone, 180, 180, 180)
                        .addComponent(lblGender)
                        .addComponent(genderCombo, 180, 180, 180)))
                .addComponent(lblIdCard)
                .addComponent(txtIdCard, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblAdress)
                .addComponent(txtAdress, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblDoctor)
                .addComponent(doctorCombo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblGuardianName)
                .addComponent(txtGuardianName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblGuardianPhone)
                .addComponent(txtGuardianPhone, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblGuardianIdCard)
                .addComponent(txtGuardianIdCard, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblRelationship)
                .addComponent(txtRelationship, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(lblTitle)
                .addGap(8)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(lblPhone))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPhone, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBirthDate)
                    .addComponent(lblGender))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                    .addComponent(genderCombo, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                .addComponent(lblIdCard)
                .addComponent(txtIdCard, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblAdress)
                .addComponent(txtAdress, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblDoctor)
                .addComponent(doctorCombo, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblGuardianName)
                .addComponent(txtGuardianName, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblGuardianPhone)
                .addComponent(txtGuardianPhone, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblGuardianIdCard)
                .addComponent(txtGuardianIdCard, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblRelationship)
                .addComponent(txtRelationship, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addGap(15)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
        );

        // Thêm contentPanel vào JScrollPane
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        scrollPane.setMaximumSize(new Dimension(DEFAULT_WIDTH, Integer.MAX_VALUE)); // Chiều rộng cố định, chiều cao linh hoạt

        // Tùy chỉnh thanh cuộn
        customizeScrollBar(scrollPane.getVerticalScrollBar());
        customizeScrollBar(scrollPane.getHorizontalScrollBar());

        // Thêm scrollPane vào panel chính với GridBagConstraints để căn giữa
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.VERTICAL; // Cho phép scrollPane mở rộng theo chiều dọc
        gbc.weighty = 1.0; // Chiếm toàn bộ không gian dọc
        add(scrollPane, gbc);

        // Lắng nghe sự kiện thay đổi kích thước cửa sổ
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustLayout();
            }
        });

        // Lắng nghe sự kiện thay đổi ngày sinh
        dateChooser.getDateEditor().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                updateGuardianFieldsVisibility();
            }
        });

        ActionListener addPatientController = new AddPatientController(this);
        btnAdd.addActionListener(addPatientController);
    }

    private void customizeScrollBar(JScrollBar scrollBar) {
        scrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(135, 206, 250); // Màu xanh dương nhạt
                this.trackColor = Color.WHITE;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }

            @Override
            protected Dimension getMinimumThumbSize() {
                return new Dimension(8, 8);
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                g.setColor(trackColor);
                g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                g.setColor(thumbColor);
                g.fillRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height);
            }
        });

        scrollBar.setPreferredSize(new Dimension(8, 8));
    }

    private void updateGuardianFieldsVisibility() {
        if (dateChooser.getDate() != null) {
            Calendar today = Calendar.getInstance();
            Calendar birthDate = Calendar.getInstance();
            birthDate.setTime(dateChooser.getDate());
            int age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
            if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }
            boolean isVisible = age < 16;
            lblGuardianName.setVisible(isVisible);
            txtGuardianName.setVisible(isVisible);
            lblGuardianPhone.setVisible(isVisible);
            txtGuardianPhone.setVisible(isVisible);
            lblGuardianIdCard.setVisible(isVisible);
            txtGuardianIdCard.setVisible(isVisible);
            lblRelationship.setVisible(isVisible);
            txtRelationship.setVisible(isVisible);
        } else {
            lblGuardianName.setVisible(false);
            txtGuardianName.setVisible(false);
            lblGuardianPhone.setVisible(false);
            txtGuardianPhone.setVisible(false);
            lblGuardianIdCard.setVisible(false);
            txtGuardianIdCard.setVisible(false);
            lblRelationship.setVisible(false);
            txtRelationship.setVisible(false);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
        adjustLayout(); // Gọi adjustLayout để đảm bảo kích thước đúng sau khi thay đổi hiển thị
    }

    private void adjustLayout() {
        Dimension parentSize = getSize();
        int contentHeight = parentSize.height > DEFAULT_HEIGHT ? parentSize.height - 20 : DEFAULT_HEIGHT;
        scrollPane.setPreferredSize(new Dimension(DEFAULT_WIDTH, contentHeight));
        scrollPane.setMaximumSize(new Dimension(DEFAULT_WIDTH, Integer.MAX_VALUE)); // Giữ chiều rộng cố định
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    private void clearFields() {
        txtName.setText("");
        txtPhone.setText("");
        txtAdress.setText("");
        txtIdCard.setText("");
        txtGuardianName.setText("");
        txtGuardianPhone.setText("");
        txtGuardianIdCard.setText("");
        txtRelationship.setText("");
        doctorCombo.setSelectedIndex(-1);
        dateChooser.setDate(null);
        lblGuardianName.setVisible(false);
        txtGuardianName.setVisible(false);
        lblGuardianPhone.setVisible(false);
        txtGuardianPhone.setVisible(false);
        lblGuardianIdCard.setVisible(false);
        txtGuardianIdCard.setVisible(false);
        lblRelationship.setVisible(false);
        txtRelationship.setVisible(false);
    }

    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtPhone() { return txtPhone; }
    public JDateChooser getDateChooser() { return dateChooser; }
    public void setDateChooser(JDateChooser dateChooser) { this.dateChooser = dateChooser; }
    public JComboBox<String> getGenderCombo() { return genderCombo; }
    public void setGenderCombo(JComboBox<String> genderCombo) { this.genderCombo = genderCombo; }
    public JTextField getTxtIdCard() { return txtIdCard; }
    public JTextField getTxtAdress() { return txtAdress; }
    public JComboBox<Doctor> getDoctorCombo() { return doctorCombo; }
    public JButton getBtnAddPatient() { return btnAdd; }
    public JTextField getTxtGuardianName() { return txtGuardianName; }
    public JTextField getTxtGuardianPhone() { return txtGuardianPhone; }
    public JTextField getTxtGuardianIdCard() { return txtGuardianIdCard; }
    public JTextField getTxtRelationship() { return txtRelationship; }

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
}