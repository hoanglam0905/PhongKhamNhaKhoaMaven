package view.receptionistPanel;

import javax.swing.*;

import model.Appointment;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import reponsitory.AppointmentRepository;
import reponsitory.DoctorRepository;
import reponsitory.ExamReponsitory;

public class NewAppointmentPanel extends JPanel {

    private JTextField txtName;
    private JTextField txtPhone;
    private JTextField txtAge;
    private JTextField txtGender;
    private JTextField txtAdress;
    private JComboBox<String> cmbDoctor;
    private JButton btnAddPatient;
    private JLabel lblDoctorName,lblStartTime;
    private JTextField txtDoctorName, txtStartTime;
    private int appointmentId;
    public NewAppointmentPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Lịch khám mới");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBounds(10, 10, 200, 30);
        add(lblTitle);

        JLabel lblName = new JLabel("Họ và tên:");
        lblName.setFont(new Font("Arial", Font.ITALIC, 16));
        lblName.setBounds(50, 50, 100, 20);
        txtName = new JTextField();
        txtName.setBounds(50, 80, 250, 30);
        txtName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblName);
        add(txtName);

        JLabel lblPhone = new JLabel("Số điện thoại:");
        lblPhone.setFont(new Font("Arial", Font.ITALIC, 16));
        lblPhone.setBounds(370, 50, 100, 20);
        txtPhone = new JTextField();
        txtPhone.setBounds(370, 80, 230, 30);
        txtPhone.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblPhone);
        add(txtPhone);

        JLabel lblAge = new JLabel("Tuổi:");
        lblAge.setFont(new Font("Arial", Font.ITALIC, 16));
        lblAge.setBounds(50, 130, 100, 20);
        txtAge =	new JTextField();
        txtAge.setBounds(50, 160, 250, 30);
        txtAge.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblAge);
        add(txtAge);

        JLabel lblGender = new JLabel("Giới tính:");
        lblGender.setFont(new Font("Arial", Font.ITALIC, 16));
        lblGender.setBounds(370, 130, 100, 20);
        txtGender = new JTextField();
        txtGender.setBounds(370, 160, 230, 30);
        txtGender.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblGender);
        add(txtGender);

        JLabel lblAdress = new JLabel("Địa chỉ:");
        lblAdress.setFont(new Font("Arial", Font.ITALIC, 16));
        lblAdress.setBounds(50, 210, 100, 20);
        txtAdress = new JTextField();
        txtAdress.setBounds(50, 240, 550, 30);
        txtAdress.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblAdress);
        add(txtAdress);
        

//        JLabel lblDoctor = new JLabel("Bác sĩ:");
//        lblDoctor.setFont(new Font("Arial", Font.ITALIC, 16));
//        lblDoctor.setBounds(50, 290, 100, 20);
//
//        // Lấy danh sách tên bác sĩ từ DoctorRepository
//        DoctorRepository doctorRepository = new DoctorRepository();
//        List<String> doctorNames;
//        try {
//            doctorNames = doctorRepository.getDoctorNames();
//            doctorNames.add(0, "-- Chọn bác sĩ --"); // Thêm tùy chọn mặc định
//        } catch (Exception e) {
//            e.printStackTrace();
//            doctorNames = List.of("-- Chọn bác sĩ --"); // Danh sách mặc định nếu có lỗi
//            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách bác sĩ: " + e.getMessage());
//        }
//
//        cmbDoctor = new JComboBox<>(doctorNames.toArray(new String[0]));
//        cmbDoctor.setBounds(50, 320, 550, 30);
//        cmbDoctor.setBackground(Color.WHITE);
//        cmbDoctor.setFont(new Font("Arial", Font.PLAIN, 14));
//        cmbDoctor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//        add(lblDoctor);
//        add(cmbDoctor);
        JLabel lblDoctorName = new JLabel("Tên bác sĩ:");
        lblDoctorName.setFont(new Font("Arial", Font.ITALIC, 16));
        lblDoctorName.setBounds(50, 290, 100, 20);
        txtDoctorName = new JTextField();
        txtDoctorName.setBounds(50, 320, 250, 30);
        txtDoctorName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblDoctorName);
        add(txtDoctorName);

        JLabel lblStartTime = new JLabel("Giờ bắt đầu:");
        lblStartTime.setFont(new Font("Arial", Font.ITALIC, 16));
        lblStartTime.setBounds(370, 290, 100, 20);
        txtStartTime = new JTextField();
        txtStartTime.setBounds(370, 320, 230, 30);
        txtStartTime.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lblStartTime);
        add(txtStartTime);

        btnAddPatient = new JButton("Đã đến");
        btnAddPatient.setFont(new Font("Arial", Font.BOLD, 14));
        btnAddPatient.setBackground(new Color(0, 123, 255));
        btnAddPatient.setForeground(Color.WHITE);
        btnAddPatient.setBounds(450, 370, 150, 40);
        btnAddPatient.setFocusPainted(false);
        add(btnAddPatient);

        btnAddPatient.addActionListener(e -> {
            String name = txtName.getText().trim();
            String phone = txtPhone.getText().trim();
            String age = txtAge.getText().trim();
            String gender = txtGender.getText().trim();
            String adress = txtAdress.getText().trim();
            String doctor = txtDoctorName.getText().trim();
            try {
				Appointment x=AppointmentRepository.getAppointmentById(appointmentId);
				boolean setStatus=false;
	            if (doctor.isEmpty()) {
	                JOptionPane.showMessageDialog(this, "Không có lịch hẹn trước");
	                return;
	            }else {
	            	try {
	    				setStatus=AppointmentRepository.updateAppointmentStatusToArrived(this.appointmentId);
	    				
	    			} catch (ClassNotFoundException | IOException e1) {
	    				// TODO Auto-generated catch block
	    				e1.printStackTrace();
	    			}
	                if (setStatus) {
	                	JOptionPane.showMessageDialog(this, "Đã chuyển sang đã đến");
	                	//chuyển qua cho bác sĩ
	                	boolean insertExam=false;
//	                	insertExam=ExamReponsitory.insertExamination(appointmentId, x.getDoctorId(), x.getPatientId(), "Chưa khám");

	             	
	                }else {
	                	JOptionPane.showMessageDialog(this, "Chưa chuyển trạng thái được");
	                }                
	            }           
	            clearFields();
	       
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });   
    }

    private void clearFields() {
        txtName.setText("");
        txtPhone.setText("");
        txtAge.setText("");
        txtGender.setText("");
        txtAdress.setText("");
        cmbDoctor.setSelectedIndex(0);
    }

    // Getters và Setters
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtPhone() { return txtPhone; }
    public JTextField getTxtAge() { return txtAge; }
    public JTextField getTxtGender() { return txtGender; }
    public JTextField getTxtAdress() { return txtAdress; }
    public JComboBox<String> getCmbDoctor() { return cmbDoctor; }
    public JButton getBtnAddPatient() { return btnAddPatient; }
    
    public JLabel getLblDoctorName() {
		return lblDoctorName;
	}

	public void setLblDoctorName(JLabel lblDoctorName) {
		this.lblDoctorName = lblDoctorName;
	}

	public JLabel getLblStartTime() {
		return lblStartTime;
	}

	public void setLblStartTime(JLabel lblStartTime) {
		this.lblStartTime = lblStartTime;
	}

	public JTextField getTxtDoctorName() {
		return txtDoctorName;
	}

	public void setTxtDoctorName(JTextField txtDoctorName) {
		this.txtDoctorName = txtDoctorName;
	}

	public JTextField getTxtStartTime() {
		return txtStartTime;
	}

	public void setTxtStartTime(JTextField txtStartTime) {
		this.txtStartTime = txtStartTime;
	}

	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	
//	public void reload(int id) {
//		this.appointmentId=id;
//		this.txtDoctorName.setText(DoctorRepository.getDoctorNameById(AppointmentRepository.getDoctorIdByAppointmentId(id)));
//		this.txtStartTime.
//	}

	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý lịch hẹn");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 500);
            frame.setLocationRelativeTo(null); // Center screen
            frame.setResizable(false);

            NewAppointmentPanel panel = new NewAppointmentPanel();
            frame.setContentPane(panel);
            frame.setVisible(true);
        });
    }
}