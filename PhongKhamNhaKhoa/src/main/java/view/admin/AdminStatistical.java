package view.admin;

import Utils.JDBCUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import service.ExportToEX;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminStatistical extends JPanel {
    JButton buttonEX;
    public AdminStatistical() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            Connection con= JDBCUtil.getConnection();
            String sql = "SELECT \n" +
                    "    DATE(p.preDate) AS Ngày,\n" +
                    "    SUM(\n" +
                    "        COALESCE(dr.quantity * d.price, 0)\n" +
                    "      + COALESCE(ps.quantity * s.price, 0)\n" +
                    "    ) AS DoanhThu\n" +
                    "FROM Prescription p\n" +
                    "LEFT JOIN PrescriptionDrugDetail dr \n" +
                    "  ON p.id = dr.prescription_id\n" +
                    "LEFT JOIN Drug d \n" +
                    "  ON dr.drug_id = d.id\n" +
                    "LEFT JOIN PrescriptionServiceDetail ps \n" +
                    "  ON p.id = ps.prescription_id\n" +
                    "LEFT JOIN Service s \n" +
                    "  ON ps.service_id = s.id\n" +
                    "WHERE p.paymentStatus = 'Đã thanh toán'\n" +
                    "GROUP BY DATE(p.preDate)\n" +
                    "ORDER BY DATE(p.preDate);\n";
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(sql);

            while(rs.next()){
                dataset.addValue(rs.getDouble(2), "Doanh thu", rs.getDate(1).toLocalDate());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Doanh thu theo ngày", // Tiêu đề
                "Ngày", // Nhãn trục X
                "Doanh thu (VND)", // Nhãn trục Y
                dataset // Dữ liệu
        );
        // Tạo panel để hiển thị biểu đồ
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setMouseWheelEnabled(true);

        // Thêm panel vào JFrame
        this.setLayout(new BorderLayout());
        this.add(chartPanel, BorderLayout.CENTER);
        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBottom.setBackground(Color.WHITE);

        buttonEX=new JButton("Xuất file excel");
        buttonEX.setBackground(Color.GREEN);
        buttonEX.setForeground(Color.WHITE);
        buttonEX.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn xuất file excel này không?",
                    "Xuất file excel",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (result == JOptionPane.YES_OPTION) {
                ExportToEX.exportServiceReport();
            }
        });
        panelBottom.add(buttonEX);
        this.add(panelBottom, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Biểu đồ doanh thu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new AdminStatistical());
            frame.pack();
            frame.setVisible(true);
        });
    }

    public JButton getButtonEX() {
        return buttonEX;
    }

    public void setButtonEX(JButton buttonEX) {
        this.buttonEX = buttonEX;
    }
}
