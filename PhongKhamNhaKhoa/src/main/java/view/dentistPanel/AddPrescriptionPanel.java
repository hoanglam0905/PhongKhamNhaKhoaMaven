package view.dentistPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class AddPrescriptionPanel extends JPanel {

    private JLabel lblIntroTitle;
    private JLabel lblIntroContent;
    private JLabel lblPhone;
    private JLabel lblFacebook;
    private JLabel lblTikTok;
    private JLabel lblEmail;
    private JPanel contactInfoPanel;
    private JButton btnConfirm;
    private JButton btnAddDrugs;
//    private String nameDrug;
//    private int
    public AddPrescriptionPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- Tiêu đề ---
        lblIntroTitle = new JLabel(" Thêm đơn thuốc ");
        lblIntroTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblIntroTitle.setPreferredSize(new Dimension(200, 30));
        add(lblIntroTitle, BorderLayout.PAGE_START);

        // --- Panel chính chứa form và bảng ---
        JPanel servicePanel = new JPanel(new BorderLayout());

        // --- Form bên trái ---
        JPanel formPanel = new JPanel(null);  // dùng layout tự do để dễ căn chỉnh
        formPanel.setPreferredSize(new Dimension(230, 260));
        formPanel.setBackground(Color.WHITE);

        JLabel lblMedicinename = new JLabel("Tên thuốc:");
        lblMedicinename.setBounds(10, 25, 100, 25);
        lblMedicinename.setFont(new Font("Arial", Font.ITALIC, 16));

        //JCombo Box nhập tên thuốc
        String[] medicineList = {"Test1", "Test2", "Test3", "Test4"};
        JComboBox<String> comboMedicinename = new JComboBox<>(medicineList);
        comboMedicinename.setBounds(10, 50, 200, 35);
        comboMedicinename.setFont(new Font("Arial", Font.PLAIN, 14));
        comboMedicinename.setBackground(Color.WHITE);

        JTextField txtQuantity = new JTextField();
        
        JLabel lblMorning = new JLabel("Sáng:");
        lblMorning.setBounds(10, 170, 50, 25);
        lblMorning.setFont(new Font("Arial", Font.ITALIC, 14));
        JTextField txtMorning = new JTextField();
        txtMorning.setBounds(10, 195, 40, 35);
        txtMorning.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel lblNoon = new JLabel("Trưa:");
        lblNoon.setBounds(80, 170, 50, 25);
        lblNoon.setFont(new Font("Arial", Font.ITALIC, 14));
        JTextField txtNoon = new JTextField();
        txtNoon.setBounds(80, 195, 40, 35);
        txtNoon.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel lblAfternoon = new JLabel("Chiều:");
        lblAfternoon.setBounds(150, 170, 50, 25);
        lblAfternoon.setFont(new Font("Arial", Font.ITALIC, 14));
        JTextField txtAfternoon = new JTextField();
        txtAfternoon.setBounds(150, 195, 40, 35);
        txtAfternoon.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        formPanel.add(lblMorning);
        formPanel.add(txtMorning);
        formPanel.add(lblNoon);
        formPanel.add(txtNoon);
        formPanel.add(lblAfternoon);
        formPanel.add(txtAfternoon);




        JLabel lblQuantity = new JLabel("Liều lượng:");
        lblQuantity.setBounds(10, 100, 100, 25);
        lblQuantity.setFont(new Font("Arial", Font.ITALIC, 16));
        txtQuantity.setBounds(10, 125, 200, 35);
        txtQuantity.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        btnConfirm = new JButton("Xác nhận");
        btnConfirm.setBounds(10, 245, 100, 30);
        btnConfirm.setBackground(new Color(0, 123, 255));  // Màu xanh dương
        btnConfirm.setForeground(Color.WHITE);             // Màu chữ trắng

        formPanel.add(lblMedicinename);
        formPanel.add(comboMedicinename);
        formPanel.add(lblQuantity);
        formPanel.add(txtQuantity);
        formPanel.add(btnConfirm);

        // --- Bảng bên phải ---
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"STT", "Tên thuốc", "Số lượng", "Xóa"}, 0
        );
        JTable serviceTable = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        serviceTable.setRowHeight(20);
        serviceTable.setBackground(Color.WHITE);              // Nền bảng
        serviceTable.setForeground(Color.BLACK);              // Màu chữ
        serviceTable.setSelectionBackground(new Color(200, 230, 255)); // Nền khi chọn dòng
        serviceTable.setGridColor(Color.LIGHT_GRAY);          // Màu viền ô

        // Xóa viền ngoài của bảng
        serviceTable.setBorder(null);

        // Header
        JTableHeader header = serviceTable.getTableHeader();
        header.setBackground(new Color(0, 102, 204));
        header.setForeground(Color.white);
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        JScrollPane tableScroll = new JScrollPane(serviceTable);
        tableScroll.setPreferredSize(new Dimension(500, 100)); // <= Giới hạn chiều cao
        tableScroll.getViewport().setBackground(Color.WHITE);  // Nền vùng cuộn
     // Tạo renderer căn giữa
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Áp dụng renderer cho các cột: STT (0), Số lượng (2), Xóa (3)
        serviceTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        serviceTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        serviceTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        serviceTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); 
        serviceTable.getColumnModel().getColumn(0).setPreferredWidth(40); 
        serviceTable.getColumnModel().getColumn(1).setPreferredWidth(100); 
        serviceTable.getColumnModel().getColumn(2).setPreferredWidth(140);  
        serviceTable.getColumnModel().getColumn(3).setPreferredWidth(40);  
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);

        // --- Kết hợp form và bảng ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(formPanel, BorderLayout.WEST);
        bottomPanel.add(tableScroll, BorderLayout.CENTER);

        // --- Nút thêm danh sách thuốc ---
        btnAddDrugs = new JButton("Thêm");
        btnAddDrugs.setBackground(Color.GREEN);
        btnAddDrugs.setForeground(Color.WHITE);
        btnAddDrugs.setFocusPainted(false);
        btnAddDrugs.setFont(new Font("Arial", Font.BOLD, 12));

        JPanel drugPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        drugPanel.add(btnAddDrugs);

        // --- Gắn các phần vào servicePanel ---
        servicePanel.add(bottomPanel, BorderLayout.CENTER);
        servicePanel.add(drugPanel, BorderLayout.SOUTH);

        // --- Thêm vào giao diện chính ---
        add(servicePanel, BorderLayout.CENTER);
        
        servicePanel.setBackground(Color.WHITE);
        bottomPanel.setBackground(Color.WHITE);
        drugPanel.setBackground(Color.WHITE);
        
        serviceTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = serviceTable.rowAtPoint(e.getPoint());
                int col = serviceTable.columnAtPoint(e.getPoint());

                // Nếu click vào cột "Xóa"
                if (col == 3) {
                    int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Bạn có chắc muốn xóa dịch vụ này?",
                        "Xác nhận xóa",
                        JOptionPane.YES_NO_OPTION
                    );

                    if (confirm == JOptionPane.YES_OPTION) {
                        ((DefaultTableModel) serviceTable.getModel()).removeRow(row);

                        // Cập nhật lại STT
                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            tableModel.setValueAt(i + 1, i, 0);
                        }
                    }
                }
            }
        });



        // --- Xử lý nút xác nhận ---
        btnConfirm.addActionListener(e -> {
        	String serviceName = comboMedicinename.getSelectedItem().toString();
            String quantityStr = txtQuantity.getText().trim();

            // Kiểm tra nếu người dùng chưa nhập đầy đủ thông tin
            if (serviceName.isEmpty() || quantityStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            int stt = tableModel.getRowCount() + 1;

            // Thêm dòng vào bảng
            tableModel.addRow(new Object[]{stt, serviceName, quantityStr, "x"});

            // Xóa các trường nhập sau khi thêm
            comboMedicinename.setSelectedIndex(0);
            txtQuantity.setText("");
        });

    }


    private void setLabelIcon(JLabel label, String iconPath, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
            Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaled));
            label.setIconTextGap(8);
        } catch (Exception e) {
            System.err.println("Không tìm thấy icon: " + iconPath);
        }
    }

    // Getters và Setters
    public JLabel getLblIntroTitle() { return lblIntroTitle; }
    public void setLblIntroTitle(JLabel lblIntroTitle) { this.lblIntroTitle = lblIntroTitle; }
    public JLabel getLblIntroContent() { return lblIntroContent; }
    public void setLblIntroContent(JLabel lblIntroContent) { this.lblIntroContent = lblIntroContent; }
    public JLabel getLblPhone() { return lblPhone; }
    public void setLblPhone(JLabel lblPhone) { this.lblPhone = lblPhone; }
    public JLabel getLblFacebook() { return lblFacebook; }
    public void setLblFacebook(JLabel lblFacebook) { this.lblFacebook = lblFacebook; }
    public JLabel getLblTikTok() { return lblTikTok; }
    public void setLblTikTok(JLabel lblTikTok) { this.lblTikTok = lblTikTok; }
    public JLabel getLblEmail() { return lblEmail; }
    public void setLblEmail(JLabel lblEmail) { this.lblEmail = lblEmail; }
    public JPanel getContactInfoPanel() { return contactInfoPanel; }
    public void setContactInfoPanel(JPanel contactInfoPanel) { this.contactInfoPanel = contactInfoPanel; }

    public JButton getBtnConfirm() {
        return btnConfirm;
    }

    public void setBtnConfirm(JButton btnConfirm) {
        this.btnConfirm = btnConfirm;
    }

    public JButton getBtnAddDrugs() {
        return btnAddDrugs;
    }

    public void setBtnAddDrugs(JButton btnAddDrugs) {
        this.btnAddDrugs = btnAddDrugs;
    }
}
