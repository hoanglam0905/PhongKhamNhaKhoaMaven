package controller.login;

import view.listPanelMain.MainFrame;
import model.Employee;

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
        String cmd = e.getActionCommand();
        if (cmd.equals("Đăng nhập")) {
            Employee employee = view.getLoginPanel().getEmployee();
            if (employee == null) {
                JOptionPane.showMessageDialog(view, "Tên đăng nhập hoặc mật khẩu không đúng!", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
                view.getLoginPanel().resetUser();
                System.out.println("Đăng nhập thất bại: Employee là null");
            } else {
                employee.showInfo(); // Hiển thị thông tin nhân viên (để debug)
                swicthPanel(); // Chuyển panel dựa trên vai trò
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
                    view.getMainPanel().getDentistTaskbar().getLblDoctorName().setText(lastName);
                    break;
                case "Lễ tân":
                    view.getCardLayout().show(view.getContainerPanel(), "ReceptionistPanel");
                    view.getReceptionistPanel().getDentistTaskbar().getLblDoctorName().setText(lastName);
                    break;
                case "Nhân viên quầy thuốc":
                    // Thêm panel quầy thuốc vào tương tự (chưa được triển khai)
                    JOptionPane.showMessageDialog(view, "Chưa có giao diện cho Nhân viên quầy thuốc!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    view.getLoginPanel().resetUser();
                    return; // Không chuyển full-screen vì chưa có panel
                default:
                    JOptionPane.showMessageDialog(view, "Vai trò không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    view.getLoginPanel().resetUser();
                    return; // Không chuyển full-screen vì vai trò không hợp lệ
            }

            // Chuyển sang full-screen sau khi chuyển panel thành công
            view.switchToFullScreen();
        }
    }
}