package leTanView;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.Patient;
import reponsitory.PatientRepository;

public class MainFrame extends JFrame {
    private JButton btnAddPatient;
    private JButton btnViewAllPatients;

    public MainFrame() {
        setTitle("Quản lý bệnh nhân");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new FlowLayout());

        btnAddPatient = new JButton("Thêm Bệnh Nhân");
        btnViewAllPatients = new JButton("Xem Toàn Bộ Bệnh Nhân");

        add(btnAddPatient);
        add(btnViewAllPatients);
        
        this.setVisible(true);
    }

    // Cung cấp phương thức để đăng ký các listener từ Controller
    public void addAddPatientListener(ActionListener listener) {
        btnAddPatient.addActionListener(listener);
    }

    public void addViewAllPatientsListener(ActionListener listener) {
        btnViewAllPatients.addActionListener(listener);
    }
}
