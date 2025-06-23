package view.receptionistPanel;

import javax.swing.*;

import com.toedter.calendar.JDateChooser;

import controller.receptionist.AddPatientController;
import controller.receptionist.FollowupController;
import model.Doctor;
import model.Patient;
import reponsitory.DoctorRepository;
import reponsitory.Patientreponsitory;
import view.dentistPanel.AppointmentTimeline;

import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AddPatientPanel extends JPanel {
    private int patientId;
    private JTextField txtName, txtPhone, txtIdCard, txtAdress;
    private JComboBox<String> genderCombo;
    private JComboBox<String> doctorCombo;
    private JButton btnAdd, btnCancel, btnDexuat;
    private JComboBox<String> timeCombo;
    private JComboBox<Integer> durationCombo;
    private JDateChooser dateChooser, birthDate;
    private JComboBox<String> serviceCombo;
    private JSpinner timeSpinner; 
    private JComboBox<Integer> hourCombo;
    private JComboBox<Integer> minuteCombo;
    private AppointmentTimeline appointmentTimelinePanel;
    private JTextField txtDoctorAppointmentName;
 // Thêm vào phần khai báo biến
    private JLabel lblGuardianName, lblGuardianPhone, lblGuardianIdCard, lblRelationship;
    private JTextField txtGuardianName, txtGuardianPhone, txtGuardianIdCard, txtRelationship;


    public AddPatientPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Thêm Lịch Hẹn");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBounds(10, 10, 300, 30);
        add(lblTitle);

        JLabel lblName = new JLabel("Họ và tên:");
        lblName.setFont(new Font("Arial", Font.ITALIC, 14));
        lblName.setBounds(30, 50, 100, 20);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(30, 70, 220, 30);
        txtName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(txtName);

        txtDoctorAppointmentName = new JTextField();
        txtDoctorAppointmentName.setBounds(550, 250, 220, 30);
        txtDoctorAppointmentName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(txtDoctorAppointmentName);
        
        JLabel lblPhone = new JLabel("Số điện thoại:");
        lblPhone.setFont(new Font("Arial", Font.ITALIC, 14));
        lblPhone.setBounds(280, 50, 100, 20);
        add(lblPhone);

        txtPhone = new JTextField();
        txtPhone.setBounds(280, 70, 220, 30);
        txtPhone.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(txtPhone);

        JLabel lblBirthDate = new JLabel("Ngày sinh (dd/MM/yyyy):");
        lblBirthDate.setFont(new Font("Arial", Font.ITALIC, 14));
        lblBirthDate.setBounds(30, 110, 200, 20);
        add(lblBirthDate);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setDate(Calendar.getInstance().getTime()); // ngày hiện tại
        dateChooser.setMinSelectableDate(new java.util.Date()); // Chỉ chọn từ ngày hiện tại trở đi
        dateChooser.setBounds(30, 430, 220, 30);
        dateChooser.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(dateChooser);

        birthDate = new JDateChooser();
        birthDate.setDateFormatString("dd/MM/yyyy");
        
        Calendar today = Calendar.getInstance();

        Calendar min = (Calendar) today.clone();
        min.add(Calendar.YEAR, -60);
//        birthDate.setMinSelectableDate(min.getTime());
        birthDate.setMaxSelectableDate(new java.util.Date());


        birthDate.setBounds(30, 130, 220, 30);
        birthDate.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(birthDate);

        JLabel lblGender = new JLabel("Giới tính:");
        lblGender.setFont(new Font("Arial", Font.ITALIC, 14));
        lblGender.setBounds(280, 110, 100, 20);
        add(lblGender);

        genderCombo = new JComboBox<>(new String[]{"Nam", "Nữ"});
        genderCombo.setBounds(280, 130, 220, 30);
        add(genderCombo);

        JLabel lblIdCard = new JLabel("CCCD:");
        lblIdCard.setFont(new Font("Arial", Font.ITALIC, 14));
        lblIdCard.setBounds(30, 170, 100, 20);
        add(lblIdCard);

        txtIdCard = new JTextField();
        txtIdCard.setBounds(30, 190, 470, 30);
        txtIdCard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(txtIdCard);

        JLabel lblAdress = new JLabel("Địa chỉ:");
        lblAdress.setFont(new Font("Arial", Font.ITALIC, 14));
        lblAdress.setBounds(30, 230, 100, 20);
        add(lblAdress);

        txtAdress = new JTextField();
        txtAdress.setBounds(30, 250, 470, 30);
        txtAdress.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(txtAdress);

        JLabel lblDoctor = new JLabel("Bác sĩ:");
        lblDoctor.setFont(new Font("Arial", Font.ITALIC, 14));
        lblDoctor.setBounds(30, 290, 100, 20);
        add(lblDoctor);

        doctorCombo = new JComboBox<>(new String[]{"Null"});
        doctorCombo.setBounds(30, 310, 470, 30);
        doctorCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(doctorCombo);

        loadDoctors();
        
     // Dịch vụ quan tâm
        JLabel lblService = new JLabel("Dịch vụ quan tâm:");
        lblService.setFont(new Font("Arial", Font.ITALIC, 14));
        lblService.setBounds(30, 350, 150, 20);
        add(lblService);

        serviceCombo = new JComboBox<>(new String[]{"Khám tổng quát", "Nha khoa", "Phục hình", "Chỉnh nha", "Tẩy trắng", "Cạo vôi"});
        serviceCombo.setBounds(30, 370, 470, 30);
        serviceCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(serviceCombo);

        // Ngày khám
        JLabel lblDate = new JLabel("Ngày khám:");
        lblDate.setFont(new Font("Arial", Font.ITALIC, 14));
        lblDate.setBounds(30, 410, 100, 20);
        add(lblDate);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setDate(Calendar.getInstance().getTime()); // ngày hiện tại
        dateChooser.setMinSelectableDate(new java.util.Date()); // Chỉ chọn từ ngày hiện tại trở đi
        dateChooser.setBounds(30, 430, 220, 30);
        dateChooser.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(dateChooser);

     // Giờ khám
        
        JLabel lblTime = new JLabel("Giờ khám:");
        lblTime.setFont(new Font("Arial", Font.ITALIC, 14));
        lblTime.setBounds(280, 410, 100, 20);
        add(lblTime);

        // ComboBox chọn giờ từ 8 đến 17
//        Integer[] hours = new Integer[10];
//        for (int i = 0; i < 10; i++) {
//            hours[i] = i + 8;  // từ 8 đến 17
//        }
        Integer[] hours = {0, 8, 9, 10, 11, 12, 13,14, 15, 16};
        hourCombo = new JComboBox<>(hours);
        hourCombo.setBounds(280, 430, 100, 30);
        add(hourCombo);

        // ComboBox chọn phút từ 0 đến 59
        Integer[] minutes = new Integer[60];
        for (int i = 0; i < 60; i++) {
            minutes[i] = i;
        }
        minuteCombo = new JComboBox<>(minutes);
        minuteCombo.setBounds(400, 430, 100, 30);
        add(minuteCombo);



        // Thời gian dự kiến
        JLabel lblDuration = new JLabel("Thời gian dự kiến (phút):");
        lblDuration.setFont(new Font("Arial", Font.ITALIC, 14));
        lblDuration.setBounds(30, 470, 200, 20);
        add(lblDuration);

        Integer[] durations = {0,10, 30, 45, 60, 75, 90, 105, 120};
        durationCombo = new JComboBox<>(durations);
        durationCombo.setBounds(30, 490, 470, 30);
        durationCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(durationCombo);

        btnDexuat = new JButton("Chọn Đề Xuất");
        btnDexuat.setFont(new Font("Arial", Font.BOLD, 14));
        btnDexuat.setBackground(new Color(0, 255, 0));
        btnDexuat.setForeground(Color.WHITE);
        btnDexuat.setBounds(20, 660, 100, 35);
        add(btnDexuat);

        btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancel.setBackground(new Color(0, 123, 255));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBounds(150, 660, 90, 35);
        add(btnCancel);

        btnAdd = new JButton("Thêm Bệnh Nhân và Lịch Hẹn");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setBackground(new Color(0, 123, 255));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBounds(260, 660, 240, 35);
        add(btnAdd);

        appointmentTimelinePanel = new AppointmentTimeline();
        appointmentTimelinePanel.setBounds(550, 300, 750, 80); // ví dụ đặt bên phải
        add(appointmentTimelinePanel);
        
     // Người giám hộ (mặc định ẩn)
        lblGuardianName = new JLabel("Tên người giám hộ:");
        lblGuardianName.setFont(new Font("Arial", Font.ITALIC, 14));
        lblGuardianName.setBounds(30, 530, 150, 20);
        lblGuardianName.setVisible(false);
        add(lblGuardianName);

        txtGuardianName = new JTextField();
        txtGuardianName.setBounds(30, 550, 220, 30);
        txtGuardianName.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtGuardianName.setVisible(false);
        add(txtGuardianName);

        lblGuardianPhone = new JLabel("Số điện thoại người giám hộ:");
        lblGuardianPhone.setFont(new Font("Arial", Font.ITALIC, 14));
        lblGuardianPhone.setBounds(280, 530, 220, 20);
        lblGuardianPhone.setVisible(false);
        add(lblGuardianPhone);

        txtGuardianPhone = new JTextField();
        txtGuardianPhone.setBounds(280, 550, 220, 30);
        txtGuardianPhone.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtGuardianPhone.setVisible(false);
        add(txtGuardianPhone);

        lblGuardianIdCard = new JLabel("CCCD người giám hộ:");
        lblGuardianIdCard.setFont(new Font("Arial", Font.ITALIC, 14));
        lblGuardianIdCard.setBounds(30, 590, 150, 20);
        lblGuardianIdCard.setVisible(false);
        add(lblGuardianIdCard);

        txtGuardianIdCard = new JTextField();
        txtGuardianIdCard.setBounds(30, 610, 220, 30);
        txtGuardianIdCard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtGuardianIdCard.setVisible(false);
        add(txtGuardianIdCard);

        lblRelationship = new JLabel("Mối quan hệ:");
        lblRelationship.setFont(new Font("Arial", Font.ITALIC, 14));
        lblRelationship.setBounds(280, 590, 150, 20);
        lblRelationship.setVisible(false);
        add(lblRelationship);

        txtRelationship = new JTextField();
        txtRelationship.setBounds(280, 610, 220, 30);
        txtRelationship.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtRelationship.setVisible(false);
        add(txtRelationship);
        
        birthDate.getDateEditor().addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                java.util.Date selectedDate = birthDate.getDate();
                if (selectedDate != null) {
                    int age = calculateAge(selectedDate);
                    boolean isUnder16 = age < 16;
                    // Hiện/ẩn các trường người giám hộ
                    lblGuardianName.setVisible(isUnder16);
                    txtGuardianName.setVisible(isUnder16);
                    lblGuardianPhone.setVisible(isUnder16);
                    txtGuardianPhone.setVisible(isUnder16);
                    lblGuardianIdCard.setVisible(isUnder16);
                    txtGuardianIdCard.setVisible(isUnder16);
                    lblRelationship.setVisible(isUnder16);
                    txtRelationship.setVisible(isUnder16);
                }
            }
        });
        ActionListener addPatientController = new AddPatientController(this);
        btnDexuat.addActionListener(addPatientController);
        btnAdd.addActionListener(addPatientController);

        
        
    }

    public void clearFields() {
        txtName.setText("");
        txtPhone.setText("");
//        txtBirthDate.setText("");
        txtAdress.setText("");
        doctorCombo.setSelectedIndex(0);
    }
    
    public void clear() {
    	doctorCombo.setSelectedIndex(0);
    	durationCombo.setSelectedIndex(0);
    	hourCombo.setSelectedIndex(0);
    	minuteCombo.setSelectedIndex(0);
    	dateChooser.setDate(new java.util.Date()); 
        
    }
    
    

    // Getters
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtPhone() { return txtPhone; }
    public JDateChooser getBirthDate() { return birthDate; }
    public JComboBox<String> getGenderCombo() { return genderCombo; }
    public JTextField getTxtIdCard() { return txtIdCard; }
    public JTextField getTxtAdress() { return txtAdress; }
    public JComboBox<String> getDoctorCombo() { return doctorCombo; }
    public JButton getBtnAddPatient() { return btnAdd; }
    public int getPatientId() { return patientId; }
    public JComboBox<Integer> getDurationCombo() {
		return durationCombo;
	}
    
	public JTextField getTxtDoctorAppointmentName() {
		return txtDoctorAppointmentName;
	}

	public void setTxtDoctorAppointmentName(JTextField txtDoctorAppointmentName) {
		this.txtDoctorAppointmentName = txtDoctorAppointmentName;
	}

	public void setDurationCombo(JComboBox<Integer> durationCombo) {
		this.durationCombo = durationCombo;
	}
	
	
	public JComboBox<Integer> getHourCombo() {
		return hourCombo;
	}

	public void setHourCombo(JComboBox<Integer> hourCombo) {
		this.hourCombo = hourCombo;
	}

	public JComboBox<Integer> getMinuteCombo() {
		return minuteCombo;
	}

	public void setMinuteCombo(JComboBox<Integer> minuteCombo) {
		this.minuteCombo = minuteCombo;
	}
	
	public JButton getBtnDexuat() {
		return btnDexuat;
	}

	public void setBtnDexuat(JButton btnDexuat) {
		this.btnDexuat = btnDexuat;
	}

	public AppointmentTimeline getAppointmentTimelinePanel() {return appointmentTimelinePanel;}
//    public AppointmentTimeline getAppointmentTimelinePanel1() {return appointmentTimelinePanel1;}
//    public AppointmentTimeline getAppointmentTimelinePanel2() {return appointmentTimelinePanel2;}

    // Setters
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public void addBtnAddListener(ActionListener listener) {
        btnAdd.addActionListener(listener);
    }

    public void addBtnCancelListener(ActionListener listener) {
        btnCancel.addActionListener(listener);
    }

    public void dispose() {
        SwingUtilities.getWindowAncestor(this).dispose();
    }

    private void loadDoctors() {
        try {
            List<Doctor> doctors = DoctorRepository.getAllDoctors();
            for (Doctor doctor : doctors) {
                doctorCombo.addItem(doctor.getName());
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
    public JComboBox<String> getServiceCombo() { return serviceCombo; }

    public JDateChooser getDateChooser() { return dateChooser; }

//    Date selectedTime = (Date) timeSpinner.getValue();
//    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//    String timeString = sdf.format(selectedTime); // eg: "08:30"
//
//    public JTextField getTxtDuration() { return txtDuration; }

    public void loadPatientInfo(int patientId) {
        this.setPatientId(patientId);
        try {
            Patient patient = Patientreponsitory.getPatientById(patientId);
            if (patient != null) {
                txtName.setText(patient.getName());
                txtPhone.setText(patient.getPhoneNumber());

                Date birthDate = patient.getBirthDate();
                if (birthDate != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                    txtBirthDate.set(sdf.format(birthDate));
                }

                txtIdCard.setText(patient.getIdCard());
                txtAdress.setText(patient.getAddress());

                genderCombo.setSelectedItem(patient.getGender() == 1 ? "Nam" : "Nữ");
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin bệnh nhân với ID: " + patientId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải thông tin bệnh nhân.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    private int calculateAge(java.util.Date birthDate) {
        Calendar birthCal = Calendar.getInstance();
        birthCal.setTime(birthDate);
        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);

        // Nếu chưa tới sinh nhật năm nay thì trừ 1 tuổi
        if (today.get(Calendar.MONTH) < birthCal.get(Calendar.MONTH) ||
            (today.get(Calendar.MONTH) == birthCal.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < birthCal.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }

        return age;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("AddPatient Panel Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700); // đủ rộng để chứa panel và timeline nếu bạn có timeline bên phải
            
            AddPatientPanel addPatientPanel = new AddPatientPanel();
            frame.add(addPatientPanel);
            
            frame.setLocationRelativeTo(null); // hiện cửa sổ ở giữa màn hình
            frame.setVisible(true);
        });
    }
}
