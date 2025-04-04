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

public class prescription_Management_View extends JFrame{
	public prescription_Management_View() {
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
	        
	        JPanel prescription_info = new JPanel(null);
	        prescription_info.setBounds(10, 10, 460, 250);
	        Font labelFont = new Font("Arial", Font.ITALIC, 17);
	        Font txtFont = new Font("Arial", Font.BOLD, 20);
	        
	        JLabel lblName = new JLabel("Họ và tên:");
	        lblName.setBounds(70, 15, 100, 25);
	        lblName.setFont(labelFont);
	        JTextField txtName = new JTextField();
	        txtName.setBounds(50, 40, 220, 35);
	        txtName.setFont(txtFont);

	        JLabel lblPhone = new JLabel("Số Điện Thoại:");
	        lblPhone.setBounds(320, 15, 120, 25);
	        lblPhone.setFont(labelFont);
	        JTextField txtPhone = new JTextField();
	        txtPhone.setBounds(300, 40, 190, 30);
	        txtPhone.setFont(txtFont);

	        JLabel lblSymptoms = new JLabel("Triệu chứng:");
	        lblSymptoms.setBounds(70, 85, 100, 25);
	        lblSymptoms.setFont(labelFont);
	        JTextField txtSymptoms = new JTextField();
	        txtSymptoms.setBounds(50, 110, 600, 35);
	        txtSymptoms.setFont(txtFont);

	        JLabel lblDiagnosis = new JLabel("Chẩn đoán:");
	        lblDiagnosis.setBounds(70, 155, 100, 25);
	        lblDiagnosis.setFont(labelFont);
	        JTextField txtDiagnosis = new JTextField();
	        txtDiagnosis.setBounds(50, 180, 600, 35);
	        txtDiagnosis.setFont(txtFont);

	        JLabel lblTreatment = new JLabel("Phương pháp điều trị:");
	        lblTreatment.setBounds(70, 225, 180, 25);
	        lblTreatment.setFont(labelFont);
	        JTextField txtTreatment = new JTextField();
	        txtTreatment.setBounds(50, 250, 600, 35);
	        txtTreatment.setFont(txtFont);

	        prescription_info.add(lblName);
	        prescription_info.add(txtName);
	        prescription_info.add(lblPhone);
	        prescription_info.add(txtPhone);
	        prescription_info.add(lblSymptoms);
	        prescription_info.add(txtSymptoms);
	        prescription_info.add(lblDiagnosis);
	        prescription_info.add(txtDiagnosis);
	        prescription_info.add(lblTreatment);
	        prescription_info.add(txtTreatment);

	        
	        contentPanel.add(topPanel, BorderLayout.NORTH);
	        contentPanel.add(prescription_info, BorderLayout.CENTER);
	        
	        // Assemble
	        mainPanel.add(sidebar, BorderLayout.WEST);
	        mainPanel.add(contentPanel, BorderLayout.CENTER);
	        
	        frame.add(mainPanel);
	        frame.setVisible(true);
	    }
	public static void main(String[] args) {
		new prescription_Management_View();
	}
	}
