package view.receptionistPanel;

import dao.PatientDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ShowPatientsReceptionistPanel extends JPanel {

    private JTextField txtSearch;
    private JTable patientInfoTable; // Bảng 1: Thông tin bệnh nhân
    private JTable patientActionTable; // Bảng 2: Hành động
    private DefaultTableModel infoTableModel;
    private DefaultTableModel actionTableModel;

    public ShowPatientsReceptionistPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

     // Tạo panel chứa cả tiêu đề và ô tìm kiếm
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        // Tiêu đề bên trái
        JLabel lblTitle = new JLabel("Danh Sách Bệnh Nhân");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0)); // padding trái
        topPanel.add(lblTitle, BorderLayout.WEST);

        // Search Bar bên phải
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(Color.WHITE);

        txtSearch = new JTextField("");
        txtSearch.setPreferredSize(new Dimension(200, 30));
        txtSearch.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        searchPanel.add(txtSearch);

        topPanel.add(searchPanel, BorderLayout.EAST);

        // Thêm topPanel vào phía trên của frame/panel chính
        add(topPanel, BorderLayout.NORTH);


        // --- Panel chứa hai bảng ---
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // --- Bảng 1: Thông tin bệnh nhân ---
        infoTableModel = new DefaultTableModel(
                new Object[]{"Mã BN", "Tên Bệnh Nhân", "SĐT", "Giới tính", "Tuổi"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa
            }
        };

        patientInfoTable = new JTable(infoTableModel);
        patientInfoTable.setRowHeight(30);
        patientInfoTable.setBackground(Color.WHITE);
        patientInfoTable.setForeground(Color.BLACK);
        patientInfoTable.setSelectionBackground(new Color(0, 123, 255));
        patientInfoTable.setGridColor(Color.LIGHT_GRAY);

        // Tùy chỉnh header bảng 1
        JTableHeader infoHeader = patientInfoTable.getTableHeader();
        infoHeader.setPreferredSize(new Dimension(0, 30));
        infoHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString());
                label.setOpaque(true);
                label.setBackground(new Color(0, 123, 255)); // DodgerBlue
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Arial", Font.BOLD, 13));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 3, Color.WHITE)); // viền trắng giữa các cột
                return label;
            }
        });


        // Căn giữa các cột bảng 1
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < patientInfoTable.getColumnCount(); i++) {
            patientInfoTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Điều chỉnh độ rộng cột bảng 1 (rộng hơn)
        patientInfoTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // Mã BN
        patientInfoTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Tên Bệnh Nhân
        patientInfoTable.getColumnModel().getColumn(2).setPreferredWidth(150); // SĐT
        patientInfoTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Giới tính
        patientInfoTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Tuổi

        infoHeader.setReorderingAllowed(false);
        infoHeader.setResizingAllowed(false);

        // --- Bảng 2: Hành động ---
        actionTableModel = new DefaultTableModel(
                new Object[]{"Tái Khám", "Lịch hẹn mới", "Sửa"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; // Cho phép tương tác với các nút
            }
        };

        patientActionTable = new JTable(actionTableModel);
        patientActionTable.setRowHeight(30);
        patientActionTable.setBackground(Color.WHITE);
        patientActionTable.setForeground(Color.BLACK);
        patientActionTable.setSelectionBackground(new Color(200, 230, 255));
        patientActionTable.setGridColor(Color.LIGHT_GRAY);

        // Tùy chỉnh header bảng 2
        JTableHeader actionHeader = patientActionTable.getTableHeader();
        actionHeader.setPreferredSize(new Dimension(0, 30)); // Tăng chiều cao header

        actionHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString());
                label.setOpaque(true);
                label.setBackground(new Color(0, 123, 255)); // Xanh dương
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Arial", Font.BOLD, 13));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 3, Color.WHITE)); // viền trắng chia cột
                return label;
            }
        });


        // Căn giữa các cột bảng 2
        for (int i = 0; i < patientActionTable.getColumnCount(); i++) {
            patientActionTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Điều chỉnh độ rộng cột bảng 2 (nhỏ hơn)
        patientActionTable.getColumnModel().getColumn(0).setPreferredWidth(55);  // Tái Khám
        patientActionTable.getColumnModel().getColumn(1).setPreferredWidth(75);  // Lịch hẹn mới
        patientActionTable.getColumnModel().getColumn(2).setPreferredWidth(35);  // Sửa

        actionHeader.setReorderingAllowed(false);
        actionHeader.setResizingAllowed(false);

        // Thêm dữ liệu mẫu
        PatientDAO dao=new PatientDAO();
        List<Object[]> list = dao.getAllPatients();
        for (Object[] row : list) {
            infoTableModel.addRow(row);
            actionTableModel.addRow(new Object[]{"Tái Khám", "Lịch hẹn mới", "✎"});
        }

        // Tùy chỉnh renderer cho các cột nút trong bảng 2
        patientActionTable.getColumnModel().getColumn(0).setCellRenderer(new ButtonRenderer("Tái Khám", new Color(0, 153, 51)));
        patientActionTable.getColumnModel().getColumn(1).setCellRenderer(new ButtonRenderer("Lịch hẹn mới", new Color(0, 153, 51)));
        patientActionTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer("✎", new Color(0, 153, 51)));

        // Xử lý sự kiện click vào bảng hành động
        patientActionTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = patientActionTable.rowAtPoint(e.getPoint());
                int col = patientActionTable.columnAtPoint(e.getPoint());

                if (row >= 0) {
                    String patientName = (String) infoTableModel.getValueAt(row, 1);
                    if (col == 0) {
                        JOptionPane.showMessageDialog(null, "Lên lịch tái khám cho bệnh nhân: " + patientName);
                    } else if (col == 1) {
                        JOptionPane.showMessageDialog(null, "Tạo lịch hẹn mới cho bệnh nhân: " + patientName);
                    } else if (col == 2) {
                        JOptionPane.showMessageDialog(null, "Sửa thông tin bệnh nhân: " + patientName);
                    }
                }
            }
        });

        // Đặt hai bảng trong các JScrollPane
        JScrollPane infoTableScroll = new JScrollPane(patientInfoTable);
        infoTableScroll.setBorder(null); // Xóa viền tổng
        infoTableScroll.getViewport().setBackground(Color.WHITE);
        JScrollPane actionTableScroll = new JScrollPane(patientActionTable);
        actionTableScroll.setBorder(null); // Xóa viền tổng
        actionTableScroll.getViewport().setBackground(Color.WHITE);

        // Điều chỉnh kích thước bảng
        infoTableScroll.setPreferredSize(new Dimension(380, 400)); // Bảng bên trái rộng hơn
        actionTableScroll.setPreferredSize(new Dimension(300, 400)); // Bảng bên phải nhỏ hơn


        // Sắp xếp hai bảng cạnh nhau
        JPanel combinedTablePanel = new JPanel(new BorderLayout());
        JPanel nth = new JPanel(new BorderLayout());
        nth.setBackground(Color.WHITE);
        combinedTablePanel.add(infoTableScroll, BorderLayout.WEST);
        combinedTablePanel.add(actionTableScroll, BorderLayout.EAST);
        combinedTablePanel.add(nth, BorderLayout.CENTER);

        tablePanel.add(combinedTablePanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);
    }

    // Class để render nút trong bảng
    private class ButtonRenderer extends DefaultTableCellRenderer {
        private String buttonText;
        private Color backgroundColor;

        public ButtonRenderer(String buttonText, Color backgroundColor) {
            this.buttonText = buttonText;
            this.backgroundColor = backgroundColor;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JButton button = new JButton(buttonText);
            button.setBackground(backgroundColor);
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            if (buttonText.equals("✎")) {
                button.setFont(new Font("Arial", Font.PLAIN, 16));
            }
            return button;
        }
    }

    // Getters and Setters
    public JTextField getTxtSearch() { return txtSearch; }
    public void setTxtSearch(JTextField txtSearch) { this.txtSearch = txtSearch; }
    public JTable getPatientInfoTable() { return patientInfoTable; }
    public void setPatientInfoTable(JTable patientInfoTable) { this.patientInfoTable = patientInfoTable; }
    public JTable getPatientActionTable() { return patientActionTable; }
    public void setPatientActionTable(JTable patientActionTable) { this.patientActionTable = patientActionTable; }
    public DefaultTableModel getInfoTableModel() { return infoTableModel; }
    public void setInfoTableModel(DefaultTableModel infoTableModel) { this.infoTableModel = infoTableModel; }
    public DefaultTableModel getActionTableModel() { return actionTableModel; }
    public void setActionTableModel(DefaultTableModel actionTableModel) { this.actionTableModel = actionTableModel; }
}