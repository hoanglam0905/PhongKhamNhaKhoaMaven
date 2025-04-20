package controller.dentist;

import view.listPanelMain.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DentistManagerButtonController implements ActionListener {
    private MainFrame view;
    public DentistManagerButtonController(MainFrame view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println(command);
        if (command.equals("Thêm dịch vụ")) {
            switchDentistAddPrescriptionPanelPanel();
        } else if (command.equals("Thêm danh sách thuốc")) {
            switchDentistServicePanel();
        } else if (command.equals("Thêm")){
            System.out.println("Làm gì đó đi...");
            switchDentistPatient1Panel();
        } else if (command.equals("Hủy")) {
            switchDentistPatient1Panel();
        }
    }
    public void switchDentistAddPrescriptionPanelPanel() {
        view.getMainPanel().getCardLayout().show(view.getMainPanel().getCenterPanel(), "addPrescriptionPanel");
    }
    public void switchDentistServicePanel() {
        view.getMainPanel().getCardLayout().show(view.getMainPanel().getCenterPanel(), "servicePanel");
    }
    public void switchDentistPatient1Panel() {
        view.getMainPanel().getCardLayout().show(view.getMainPanel().getCenterPanel(), "Patient1");
    }
}
