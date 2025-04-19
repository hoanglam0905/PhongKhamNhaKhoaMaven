package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import leTanView.AddPatientView;
import leTanView.MainFrame;
import reponsitory.PatientRepository;

public class MainController {
    private MainFrame mainFrame;
    private PatientRepository patientRepository;

    public MainController() {
        this.mainFrame = new MainFrame();
        this.patientRepository = new PatientRepository();

        // Đăng ký các listener
        this.mainFrame.addAddPatientListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.out.println("heloo");
            	AddPatientController x= new AddPatientController();
            }

//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
        });

        this.mainFrame.addViewAllPatientsListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hiển thị danh sách bệnh nhân
            }
        });
        
        
    }

    // Mở cửa sổ thêm bệnh nhân
    
//    public void showView() {
//        mainFrame.setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        MainController controller = new MainController();
//        controller.showView();
//    }
}
