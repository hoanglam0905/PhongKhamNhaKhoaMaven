package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;

public class pateint_Management_View extends JFrame{
	public pateint_Management_View() {
		 JFrame frame = new JFrame("Dental Clinic");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(940, 600);
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
	        header.setBackground(new Color(30,144,255));
	        header.setPreferredSize(new Dimension(frame.getWidth(), 70));
	        
	        JButton docName = new JButton("Phương");
	        docName.setBorder(null);
	        docName.setFocusPainted(false);
	        docName.setBackground(header.getBackground());
	        docName.setBounds(610, 30, 60,20);
	        docName.setFont(new Font("Arial", Font.BOLD, 13));
	        docName.setHorizontalAlignment(SwingConstants.LEFT);
	        docName.setForeground(Color.WHITE);
	        header.add(docName);
	        ImageIcon doctorIcon = new ImageIcon(new ImageIcon("C:\\Users\\User\\OneDrive\\Pictures\\tham_khao_pknk\\ui\\src\\img\\doctor.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	        JLabel imageDoctor = new JLabel(doctorIcon);
	        imageDoctor.setForeground(Color.WHITE);
	        imageDoctor.setBounds(560, 20, doctorIcon.getIconWidth(), doctorIcon.getIconHeight());
	        header.add(imageDoctor);
	        
	        JButton theme = new JButton("Chủ Đề");
	        theme.setBorder(null);
	        theme.setFocusPainted(false);
	        theme.setBackground(header.getBackground());
	        theme.setBounds(450, 30, 60,20);
	        theme.setFont(new Font("Arial", Font.BOLD, 13));
	        theme.setHorizontalAlignment(SwingConstants.LEFT);
	        theme.setForeground(Color.WHITE);
	        header.add(theme);
	        ImageIcon themeIcon = new ImageIcon(new ImageIcon("C:\\Users\\User\\OneDrive\\Pictures\\tham_khao_pknk\\ui\\src\\img\\theme.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	        JLabel imageTheme = new JLabel(themeIcon);
	        imageTheme.setForeground(Color.WHITE);
	        imageTheme.setBounds(400, 20, themeIcon.getIconWidth(), themeIcon.getIconHeight());
	        header.add(imageTheme);
	        
	        JButton language = new JButton("Ngôn ngữ");
	        language.setBorder(null);
	        language.setFocusPainted(false);
	        language.setBackground(header.getBackground());
	        language.setBounds(290, 30, 70,20);
	        language.setFont(new Font("Arial", Font.BOLD, 13));
	        language.setHorizontalAlignment(SwingConstants.LEFT);
	        language.setForeground(Color.WHITE);
	        header.add(language);
	        ImageIcon lge = new ImageIcon(new ImageIcon("C:\\Users\\User\\OneDrive\\Pictures\\tham_khao_pknk\\ui\\src\\img\\language.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	        JLabel imageLge = new JLabel(lge);
	        imageLge.setForeground(Color.WHITE);
	        imageLge.setBounds(240, 20, lge.getIconWidth(), lge.getIconHeight());
	        header.add(imageLge);
	        
	        
	        // Content
	        JPanel contentPanel = new JPanel(new BorderLayout());
	        
	        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	        JTextField searchField = new JTextField(45);
	        searchField.setPreferredSize(new Dimension(200, 30));
	        searchPanel.add(searchField);
	        
	        JPanel topPanel = new JPanel(new BorderLayout());
	        topPanel.add(header, BorderLayout.NORTH);
	        topPanel.add(searchPanel, BorderLayout.SOUTH);
	        
	        String[] columnNames = {"STT", "Tên Bệnh Nhân", "Số Điện Thoại", "Giới Tính", "Tuổi", "Trạng Thái", " "};
	        Object[][] data = {};
	        JTable table = new JTable(data, columnNames);
	        TableColumnModel columnModel = table.getColumnModel();
	        table.getTableHeader().setReorderingAllowed(false);
	        columnModel.getColumn(0).setPreferredWidth(50);  
	        columnModel.getColumn(1).setPreferredWidth(150); 
	        columnModel.getColumn(2).setPreferredWidth(120);
	        columnModel.getColumn(3).setPreferredWidth(50);
	        columnModel.getColumn(4).setPreferredWidth(40);
	        columnModel.getColumn(5).setPreferredWidth(140);
	        columnModel.getColumn(6).setPreferredWidth(30);
	        
	        columnModel.getColumn(0).setResizable(false);
	        columnModel.getColumn(1).setResizable(false);
	        columnModel.getColumn(2).setResizable(false);
	        columnModel.getColumn(3).setResizable(false);
	        columnModel.getColumn(4).setResizable(false);
	        columnModel.getColumn(5).setResizable(false);
	        columnModel.getColumn(6).setResizable(false);
	        
	        JScrollPane scrollPane = new JScrollPane(table);
	        
	        contentPanel.add(topPanel, BorderLayout.NORTH); 
	        contentPanel.add(scrollPane, BorderLayout.CENTER);
	        
	        // Assemble
	        mainPanel.add(sidebar, BorderLayout.WEST);
	        mainPanel.add(contentPanel, BorderLayout.CENTER);
	        
	        frame.add(mainPanel);
	        frame.setVisible(true);
	    }
	public static void main(String[] args) {
		new pateint_Management_View();
	}

	}
