package view.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminStatistical extends JPanel {
    private JTable table;

    public AdminStatistical() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Tiêu đề
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(400, 40));
        headerPanel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Doanh thu theo tháng");
        lblTitle.setForeground(new Color(0, 51, 255));
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

        headerPanel.add(lblTitle, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // Bảng dữ liệu
        table = new JTable(new DefaultTableModel(
                new Object[][] {
                        {"Tháng 1", "12,000,000 VND"},
                        {"Tháng 2", "15,500,000 VND"},
                        {"Tháng 3", "9,300,000 VND"}
                },
                new String[] { "Tháng", "Doanh thu" }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);
        table.setRowHeight(26);

        // Header: màu xanh, chữ trắng, in đậm
        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JLabel label = new JLabel(value.toString());
                label.setOpaque(true);
                label.setBackground(new Color(0, 102, 204));
                label.setForeground(Color.WHITE);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setFont(new Font("Arial", Font.BOLD, 13));
                label.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0, 102, 204)));
                return label;
            }
        });

        // Canh giữa nội dung trong bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);

        // Panel chân (có thể thêm tổng doanh thu hoặc nút chức năng)
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setPreferredSize(new Dimension(400, 40));
        footerPanel.setBackground(Color.WHITE);
//        add(footerPanel, BorderLayout.SOUTH);
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý nhân viên");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new AdminStatistical());
            frame.setVisible(true);
        });
    }
}
