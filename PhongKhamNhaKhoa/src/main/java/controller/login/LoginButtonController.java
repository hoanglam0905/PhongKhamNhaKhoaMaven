package controller.login;

import dao.DentistDao;
import view.listPanelMain.MainFrame;
import model.Employee;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class LoginButtonController implements ActionListener {
    private MainFrame view;

    public LoginButtonController(MainFrame view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String cmd=e.getActionCommand();
        if (cmd.equals("Đăng nhập")){
            if (view.getLoginPanel().getEmployee()==null){
                JOptionPane.showMessageDialog(
                        view,
                        "Tên đăng nhập hoặc mật khẩu không đúng!",
                        "Đăng nhập thất bại",
                        JOptionPane.ERROR_MESSAGE
                );
            }else {
                view.getLoginPanel().getEmployee().showInfo();
                swicthPanel();
                view.getMainPanel().getDentistListPatient().setId_doctor(DentistDao.getIdDentistLogin(view.getLoginPanel().getAcc(),view.getLoginPanel().getPass())+"");
            }
        }
    }

    public void swicthPanel() {
        Employee employee = view.getLoginPanel().getEmployee();
        if (employee != null) {
            String[] nameD = employee.getName().split(" ");
            String lastName = nameD[nameD.length - 1]; // Lấy tên cuối cùng

            // Chuyển panel dựa trên vai trò
            switch (employee.getRole()) {
                case "Bác sĩ":
                    view.getCardLayout().show(view.getContainerPanel(), "DentistPanel");

                    switchDentistIntroducePanel();
                    view.getMainPanel().getDentistTaskbar().getLblDoctorName().setText(nameD[nameD.length - 1]);

                    break;
                case "Lễ tân":
                    view.getCardLayout().show(view.getContainerPanel(), "ReceptionistPanel");
                    view.getReceptionistPanel().getDentistTaskbar().getLblDoctorName().setText(lastName);
                    break;
                case "Nhân viên quầy thuốc":

                    view.getCardLayout().show(view.getContainerPanel(), "drugstore");
                    view.getDrugStorePanel().getDentistTaskbar().getLblDoctorName().setText(nameD[nameD.length - 1]);
                    break;

            }

            // Chuyển sang full-screen sau khi chuyển panel thành công
            view.switchToFullScreen();
        }
    }

    public void switchDentistIntroducePanel() {
        view.getMainPanel().getCardLayout().show(view.getMainPanel().getCenterPanel(), "Introduce");
    }
}

