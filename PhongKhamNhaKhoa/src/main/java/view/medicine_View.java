package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class medicine_View extends JFrame{
	public medicine_View() {
		 JFrame frame = new JFrame("Dental Clinic");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(1100, 700);
	        frame.setLocationRelativeTo(null);
	        
	        JPanel mainPanel = new JPanel(new BorderLayout());
	        
	        JPanel sidebar = new JPanel(null);
	        sidebar.setPreferredSize(new Dimension(240, frame.getHeight()));
	        sidebar.setBackground(new Color(220,220,220));
	        
	        JButton homeButton = new JButton("Trang Chủ");
	        homeButton.setBorder(null);
	        homeButton.setFocusPainted(false);
	        homeButton.setBackground(sidebar.getBackground());
	        homeButton.setBounds(70,110,80,20);
	        homeButton.setFont(new 
	        Font("Arial", Font.BOLD, 14));
	        homeButton.setHorizontalAlignment(SwingConstants.LEFT);
	        ImageIcon homeIcon = new ImageIcon(new ImageIcon("C:\\Users\\User\\OneDrive\\Pictures\\tham_khao_pknk\\ui\\src\\img\\home.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
	        JLabel imageHomeIcon = new JLabel(homeIcon);
	        imageHomeIcon.setBounds(14, 88, homeIcon.getIconWidth(), homeIcon.getIconHeight());
	        
	        JButton scheduleButton = new JButton("Lịch Làm Việc");
	        scheduleButton.setBorder(null);
	        scheduleButton.setFocusPainted(false);
	        scheduleButton.setBackground(sidebar.getBackground());
	        scheduleButton.
	        setBounds(70,165,105,20);
	        scheduleButton.setFont(new Font("Arial", Font.BOLD, 14));
	        scheduleButton.setHorizontalAlignment(SwingConstants.LEFT);
	        ImageIcon scheduleIcon = new ImageIcon(new ImageIcon("C:\\Users\\User\\OneDrive\\Pictures\\tham_khao_pknk\\ui\\src\\img\\schedule.png").getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	        JLabel imageSchedule = new JLabel(scheduleIcon);
	        imageSchedule.setBounds(15, 150, scheduleIcon.getIconWidth(), scheduleIcon.getIconHeight());
	        
	        JButton salaryButton = new JButton("Quản Lý Lương");
	        salaryButton.setBorder(null);
	        salaryButton.setFocusPainted(false);
	        salaryButton.setBackground(sidebar.getBackground());
	        salaryButton.setBounds(70,220,115,20);
	        salaryButton.setFont(new Font("Arial", Font.BOLD, 14));
	        salaryButton.setHorizontalAlignment(SwingConstants.LEFT);
	        ImageIcon salary = new ImageIcon(new ImageIcon("C:\\Users\\User\\OneDrive\\Pictures\\tham_khao_pknk\\ui\\src\\img\\coin.png").getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	        JLabel imageSalary = new JLabel(salary);
	        imageSalary.setBounds(15, 205, salary.getIconWidth(), salary.getIconHeight());
	        
	        JButton mngPatient = new JButton("Quản Lý Bệnh Nhân");
	        mngPatient.setBorder(null);
	        mngPatient.setFocusPainted(false);
	        mngPatient.setBackground(sidebar.getBackground());
	        mngPatient.setBounds(70,275,140,20);
	        mngPatient.setFont(new Font("Arial", Font.BOLD, 14));
	        mngPatient.setHorizontalAlignment(SwingConstants.LEFT);
	        ImageIcon noteBook = new ImageIcon(new ImageIcon("C:\\Users\\User\\OneDrive\\Pictures\\tham_khao_pknk\\ui\\src\\img\\noteBook.png").getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH));
	        JLabel imageNoteBook = new JLabel(noteBook);
	        imageNoteBook.setBounds(15, 260, noteBook.getIconWidth(), noteBook.getIconHeight());
	        
	        sidebar.add(homeButton);
	        sidebar.add(imageHomeIcon);
	        sidebar.add(scheduleButton);
	        sidebar.add(imageSchedule);
	        sidebar.add(salaryButton);
	        sidebar.add(imageSalary);
	        sidebar.add(mngPatient);
	        sidebar.add(imageNoteBook);
	        
	        // Header
	        JPanel header = new JPanel(null);
	        header.setBackground(new Color(65, 210, 210));
	        header.setPreferredSize(new Dimension(700, 70));
	        
	        JButton docName = new JButton("Phương");
	        docName.setBorder(null);
	        docName.setFocusPainted(false);
	        docName.setBackground(header.getBackground());
	        docName.setBounds(750, 30, 60,20);
	        docName.setFont(new Font("Arial", Font.BOLD, 13));
	        docName.setHorizontalAlignment(SwingConstants.LEFT);
	        docName.setForeground(Color.WHITE);
	        header.add(docName);
	        ImageIcon doctorIcon = new ImageIcon(new ImageIcon("C:\\Users\\User\\OneDrive\\Pictures\\tham_khao_pknk\\ui\\src\\img\\doctor.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	        JLabel imageDoctor = new JLabel(doctorIcon);
	        imageDoctor.setForeground(Color.WHITE);
	        imageDoctor.setBounds(700, 20, doctorIcon.getIconWidth(), doctorIcon.getIconHeight());
	        header.add(imageDoctor);
	        
	        JButton theme = new JButton("Chủ Đề");
	        theme.setBorder(null);
	        theme.setFocusPainted(false);
	        theme.setBackground(header.getBackground());
	        theme.setBounds(590, 30, 60,20);
	        theme.setFont(new Font("Arial", Font.BOLD, 13));
	        theme.setHorizontalAlignment(SwingConstants.LEFT);
	        theme.setForeground(Color.WHITE);
	        header.add(theme);
	        ImageIcon themeIcon = new ImageIcon(new ImageIcon("C:\\Users\\User\\OneDrive\\Pictures\\tham_khao_pknk\\ui\\src\\img\\theme.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	        JLabel imageTheme = new JLabel(themeIcon);
	        imageTheme.setForeground(Color.WHITE);
	        imageTheme.setBounds(540, 20, themeIcon.getIconWidth(), themeIcon.getIconHeight());
	        header.add(imageTheme);
	        
	        JButton language = new JButton("Ngôn ngữ");
	        language.setBorder(null);
	        language.setFocusPainted(false);
	        language.setBackground(header.getBackground());
	        language.setBounds(430, 30, 70,20);
	        language.setFont(new Font("Arial", Font.BOLD, 13));
	        language.setHorizontalAlignment(SwingConstants.LEFT);
	        language.setForeground(Color.WHITE);
	        header.add(language);
	        ImageIcon lge = new ImageIcon(new ImageIcon("C:\\Users\\User\\OneDrive\\Pictures\\tham_khao_pknk\\ui\\src\\img\\language.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	        JLabel imageLge = new JLabel(lge);
	        imageLge.setForeground(Color.WHITE);
	        imageLge.setBounds(380, 20, lge.getIconWidth(), lge.getIconHeight());
	        header.add(imageLge);
	        
	        
	        // Content
	        JPanel contentPanel = new JPanel(new BorderLayout());
	        
	        JPanel topPanel = new JPanel(new BorderLayout());
	        topPanel.add(header, BorderLayout.NORTH);
	        
	      
	        
	        contentPanel.add(topPanel, BorderLayout.NORTH); 
	     // --- Center content: Service & Medicine form ---
	        JPanel centerPanel = new JPanel(new BorderLayout());
	        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

	        JPanel formPanel = new JPanel(null);
	        formPanel.setPreferredSize(new Dimension(600, 600));
	        JComboBox<String> loaiThuocBox = new JComboBox<>(new String[]{"Paracetamol", "Dexamethasone", "Amoxicillin"});
	        JTextField soLuongThuocField = new JTextField();
	        JTextField morningField = new JTextField();
	        JTextField noonField = new JTextField();
	        JTextField eveningField = new JTextField();
	        
	        Font lblFont = new Font("Arial", Font.ITALIC, 15);
	        
	        JLabel lblLoaiThuoc = new JLabel("Loại thuốc:");
	        lblLoaiThuoc.setBounds(10, 10, 100, 25);
	        loaiThuocBox.setBounds(10, 40, 250, 30);
	        lblLoaiThuoc.setFont(lblFont);
	        formPanel.add(lblLoaiThuoc);
	        formPanel.add(loaiThuocBox);
	        
	        JLabel lblSoLuongTHuoc = new JLabel("Số lượng thuốc:");
	        lblSoLuongTHuoc.setBounds(10, 190, 150, 30);
	        soLuongThuocField.setBounds(10, 230, 250, 30);
	        lblSoLuongTHuoc.setFont(lblFont);
	        formPanel.add(lblSoLuongTHuoc);
	        formPanel.add(soLuongThuocField);
	        
	        JLabel dose = new JLabel("Liều lượng:");
	        dose.setBounds(10, 80, 100, 30);
	        dose.setFont(lblFont);
	        formPanel.add(dose);
	        
	        JLabel morning = new JLabel("Sáng:");
	        morning.setBounds(10, 120, 50, 30);
	        morningField.setBounds(10, 150, 50, 30);
	        morning.setFont(lblFont);
	        formPanel.add(morning);
	        formPanel.add(morningField);
	        
	        JLabel noon = new JLabel("Trưa:");
	        noon.setBounds(100, 120, 50, 30);
	        noonField.setBounds(100, 150, 50, 30);
	        noon.setFont(lblFont);
	        formPanel.add(noon);
	        formPanel.add(noonField);
	        
	        JLabel evening = new JLabel("Chiều:");
	        evening.setBounds(190, 120, 150, 30);
	        eveningField.setBounds(190, 150, 50, 30);
	        evening.setFont(lblFont);
	        formPanel.add(evening);
	        formPanel.add(eveningField);
	

	        JButton btnXacNhan = new JButton("Xác nhận");
	        btnXacNhan.setBounds(20, 280, 100, 30);
	        btnXacNhan.setForeground(Color.white);
	        btnXacNhan.setBackground(new Color(7, 231, 243));          
	        btnXacNhan.setForeground(Color.WHITE);         
	        btnXacNhan.setOpaque(true);             
	        btnXacNhan.setFocusPainted(false);
	        btnXacNhan.setBorderPainted(false); 
	        
	        JButton btnHuy = new JButton("Hủy");
	        btnHuy.setBounds(150, 280, 100, 30);
	        btnHuy.setForeground(Color.white);
	        btnHuy.setBackground(new Color(7, 231, 243));          
	        btnHuy.setForeground(Color.WHITE);         
	        btnHuy.setOpaque(true);             
	        btnHuy.setFocusPainted(false);
	        btnHuy.setBorderPainted(false);  
	        formPanel.add(btnXacNhan);
	        formPanel.add(btnHuy);

	        // Tạo bảng
	        String[] columnNames = {"Tên thuốc", "Số lượng thuốc", ""};
	        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
	        JTable table = new JTable(model);
	        JScrollPane scrollPane = new JScrollPane(table);
	        JPanel tableWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	        scrollPane.setPreferredSize(new Dimension(440, 450));
	        tableWrapper.add(scrollPane, BorderLayout.CENTER);
	        table.getTableHeader().setReorderingAllowed(false);
	        
	        JButton btnThem = new JButton("Xuất đơn thuốc");
	        btnThem.setPreferredSize(new Dimension(140, 35));
	        btnThem.setForeground(Color.white);
	        btnThem.setBackground(new Color(7, 231, 243));          
	        btnThem.setForeground(Color.WHITE);         
	        btnThem.setOpaque(true);             
	        btnThem.setFocusPainted(false);
	        btnThem.setBorderPainted(false);  
	        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	        buttonPanel.add(btnThem);
	        tableWrapper.add(buttonPanel, BorderLayout.SOUTH);

	        TableColumnModel columnModel = table.getColumnModel();
	        columnModel.getColumn(0).setPreferredWidth(160);  
	        columnModel.getColumn(1).setPreferredWidth(50); 
	        columnModel.getColumn(2).setPreferredWidth(50);

	        for (int i = 0; i < columnModel.getColumnCount(); i++) {
	            columnModel.getColumn(i).setResizable(false);
	        }
	        
	        JPanel leftPanel = new JPanel(new BorderLayout());
	        leftPanel.add(tableWrapper, BorderLayout.CENTER);
	        leftPanel.setPreferredSize(new Dimension(450, 0));

	        JPanel rightPanel = new JPanel(new BorderLayout());
	        rightPanel.add(formPanel, BorderLayout.NORTH);
	        rightPanel.setPreferredSize(new Dimension(400, 0));

	        centerPanel.add(leftPanel, BorderLayout.EAST );
	        centerPanel.add(rightPanel, BorderLayout.WEST);

	        contentPanel.add(centerPanel, BorderLayout.CENTER);
	        

	        class ButtonRenderer extends JButton implements TableCellRenderer {
	            public ButtonRenderer() {
	                setText("Xóa");
	            }
	            
	            public Component getTableCellRendererComponent(JTable table, Object value,
	                boolean isSelected, boolean hasFocus, int row, int column) {
	                return this;
	            }
	        }

	        // Editor cho nút "Xóa"
	        class ButtonEditor extends DefaultCellEditor {
	            private JButton button;
	            private int selectedRow;

	            public ButtonEditor(JCheckBox checkBox) {
	                super(checkBox);
	                button = new JButton("Xóa");
	                button.addActionListener(e -> {
	                    if (selectedRow >= 0) {
	                        model.removeRow(selectedRow);
	                    }
	                });
	            }

	            public Component getTableCellEditorComponent(JTable table, Object value,
	                boolean isSelected, int row, int column) {
	                selectedRow = row;
	                return button;
	            }

	            @Override
	            public Object getCellEditorValue() {
	                return "Xóa";
	            }
	        }

	        table.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
	        table.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor(new JCheckBox()));

	        btnXacNhan.addActionListener(e -> {
	            String loaiThuoc = (String) loaiThuocBox.getSelectedItem();
	            String soLuongThuoc = soLuongThuocField.getText().trim();

	            // Kiểm tra trống
	            if (soLuongThuoc.isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ số lượng thuốc!", "Thông báo", JOptionPane.WARNING_MESSAGE);
	                return;
	            }

	            // Nếu hợp lệ thì thêm dòng mới
	            model.addRow(new Object[]{ loaiThuoc,soLuongThuoc , "Xóa"});

	            // Xóa nội dung ô nhập
	            soLuongThuocField.setText("");
	        });



	        
	        
	        // Assemble
	        mainPanel.add(sidebar, BorderLayout.WEST);
	        mainPanel.add(contentPanel, BorderLayout.CENTER);
	        
	        frame.add(mainPanel);
	        frame.setVisible(true);
	    }
	public static void main(String[] args) {
		new medicine_View();
	}
	}
