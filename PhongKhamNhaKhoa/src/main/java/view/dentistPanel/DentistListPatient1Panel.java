package view.dentistPanel;

import reponsitory.dao.DentistDao;
import reponsitory.dao.PatientDAO;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class DentistListPatient1Panel extends JPanel {

    private JPanel headerPanel;
    private JLabel lblTitle, lblSearch;
    private JTextField tfSearch;
    private JTable tblPatients;
    private JScrollPane scrollPane;
    Object[][] data;
    private String id_doctor;

    public String getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(String id_doctor) {
        this.id_doctor = id_doctor;
    }

    public DentistListPatient1Panel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        //Header Panel
        headerPanel = new JPanel();
        headerPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        //Dòng này không quan trọng do nó sẽ được "kéo" dựa trên JFrame
        headerPanel.setPreferredSize(new Dimension(641, 40));

        lblTitle = new JLabel("Xem lịch của bác sĩ");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitle.setForeground(new Color(51, 51, 255));

        lblSearch = new JLabel("Tìm kiếm");
        lblSearch.setFont(new Font("Arial", Font.PLAIN, 15));
        lblSearch.setForeground(Color.BLACK);
        tfSearch = new JTextField();
        tfSearch.setPreferredSize(new Dimension(200, 25));

        GroupLayout headerLayout = new GroupLayout(headerPanel);
        headerPanel.setLayout(headerLayout);
        headerLayout.setAutoCreateGaps(true);
        headerLayout.setAutoCreateContainerGaps(true);

        headerPanel.setBackground(Color.WHITE);

        GroupLayout.SequentialGroup hGroup = headerLayout.createSequentialGroup();
        GroupLayout.ParallelGroup vGroup = headerLayout.createParallelGroup(GroupLayout.Alignment.CENTER);

        // Horizontal Group
        hGroup.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE);
        hGroup.addGap(50, 50, Short.MAX_VALUE);
        hGroup.addComponent(lblSearch);
        hGroup.addGap(10);
        hGroup.addComponent(tfSearch, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE);

        //Vertical Group
        vGroup.addComponent(lblTitle);
        vGroup.addComponent(lblSearch);
        vGroup.addComponent(tfSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);

        //Set layout
        headerLayout.setHorizontalGroup(hGroup);
        headerLayout.setVerticalGroup(vGroup);

        add(headerPanel, BorderLayout.PAGE_START);

        String[] columnNames = {"STT", "Tên bệnh nhân", "Số điện thoại", "Giới tính", "Tuổi", "Trạng thái", "Khám"};

        List<Object[]> list = PatientDAO.getAllPatients();

        // Tạo icon "see"
        ImageIcon seeIcon;
        try {
            seeIcon = new ImageIcon(getClass().getResource("/img/see.png"));
            Image scaled = seeIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            seeIcon = new ImageIcon(scaled);
        } catch (Exception e) {
            System.err.println("Không tìm thấy ảnh see.png");
            seeIcon = null;
        }

        // Gán icon vào từng dòng
        for (Object[] row : list) {
            if (row.length >= 7) {
                row[6] = seeIcon; // gán icon khám
            }
        }

        data = list.toArray(new Object[0][]);


        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            final boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false
            };

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 6) return Icon.class;
                return String.class;
            }
        };

        tblPatients = new JTable(model);
        tblPatients.setRowHeight(28);
        tblPatients.setFillsViewportHeight(true);
        tblPatients.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Tô màu cho tiêu đề cột
        JTableHeader tableHeader = tblPatients.getTableHeader();
        tableHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JLabel lbl = new JLabel(value.toString());
                lbl.setOpaque(true);
                lbl.setBackground(new Color(0, 102, 204));
                lbl.setForeground(Color.WHITE);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setFont(new Font("Arial", Font.BOLD, 13));
                lbl.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0, 102, 204)));
                return lbl;
            }
        });

        scrollPane = new JScrollPane(tblPatients);
        add(scrollPane, BorderLayout.CENTER);

        // Renderer trắng cho ô dữ liệu
        DefaultTableCellRenderer whiteCenterRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                lbl.setOpaque(true);

                if (isSelected) {
                    lbl.setBackground(table.getSelectionBackground());
                    lbl.setForeground(table.getSelectionForeground());
                } else {
                    lbl.setBackground(Color.WHITE);
                    lbl.setForeground(Color.BLACK);
                }

                lbl.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                return lbl;
            }
        };


        DefaultTableCellRenderer iconRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JLabel lbl = new JLabel();
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                if (value instanceof Icon) {
                    lbl.setIcon((Icon) value);
                }
                lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                return lbl;
            }
        };

        for (int i = 0; i < tblPatients.getColumnCount(); i++) {
            if (i == 6) {
                tblPatients.getColumnModel().getColumn(i).setCellRenderer(iconRenderer);
            } else {
                tblPatients.getColumnModel().getColumn(i).setCellRenderer(whiteCenterRenderer);
            }
        }

        // Kích thước từng cột
        tblPatients.getColumnModel().getColumn(0).setPreferredWidth(40);   // STT
        tblPatients.getColumnModel().getColumn(1).setPreferredWidth(150);  // Tên
        tblPatients.getColumnModel().getColumn(2).setPreferredWidth(120);  // SĐT
        tblPatients.getColumnModel().getColumn(3).setPreferredWidth(60);   // Giới tính
        tblPatients.getColumnModel().getColumn(4).setPreferredWidth(50);   // Tuổi
        tblPatients.getColumnModel().getColumn(5).setPreferredWidth(100);  // Trạng thái
        tblPatients.getColumnModel().getColumn(6).setPreferredWidth(60);   // Khám
    }

    public JPanel getHeaderPanel() {
        return headerPanel;
    }

    public void setHeaderPanel(JPanel headerPanel) {
        this.headerPanel = headerPanel;
    }

    public JLabel getLblTitle() {
        return lblTitle;
    }

    public void setLblTitle(JLabel lblTitle) {
        this.lblTitle = lblTitle;
    }

    public JLabel getLblSearch() {
        return lblSearch;
    }

    public void setLblSearch(JLabel lblSearch) {
        this.lblSearch = lblSearch;
    }

    public JTextField getTfSearch() {
        return tfSearch;
    }

    public void setTfSearch(JTextField tfSearch) {
        this.tfSearch = tfSearch;
    }

    public JTable getTblPatients() {
        return tblPatients;
    }

    public void setTblPatients(JTable tblPatients) {
        this.tblPatients = tblPatients;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }
    public void reloadTableData(String acc, String pass) {
        int dentistId = DentistDao.getIdDentistLogin(acc, pass);
        List<Object[]> list = PatientDAO.getPatientOfDentist(String.valueOf(dentistId));

        // Gán lại icon xem
        ImageIcon seeIcon;
        try {
            seeIcon = new ImageIcon(getClass().getResource("/img/see.png"));
            Image scaled = seeIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            seeIcon = new ImageIcon(scaled);
        } catch (Exception e) {
            System.err.println("Không tìm thấy ảnh see.png");
            seeIcon = null;
        }

        for (Object[] row : list) {
            if (row.length >= 7) {
                row[6] = seeIcon;
            }
        }

        data = list.toArray(new Object[0][]);
        DefaultTableModel model = (DefaultTableModel) tblPatients.getModel();
        model.setDataVector(data, new String[]{
                "STT", "Tên bệnh nhân", "Số điện thoại", "Giới tính", "Tuổi", "Trạng thái", "Khám"
        });

        // Căn giữa toàn bộ nội dung bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tblPatients.getColumnCount(); i++) {
            tblPatients.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        //Căn giữa tiêu đề cột
        JTableHeader header = tblPatients.getTableHeader();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    }


}
