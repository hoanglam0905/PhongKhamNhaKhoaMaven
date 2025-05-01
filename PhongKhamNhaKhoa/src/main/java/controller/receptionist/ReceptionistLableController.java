package controller.receptionist;

import view.listPanelMain.MainFrame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ReceptionistLableController implements MouseListener {
    private MainFrame view;
    public ReceptionistLableController(MainFrame view) {
        this.view = view;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel clickedLabel = (JLabel) e.getComponent();
        String name = clickedLabel.getName();

        if (name == null) return;
        System.out.println("Đã click: " + name);

        switch (name) {
            case "Patients":
                switchReceptionistPatientsPanel();
                break;
            case "addPatient":
                switchReceptionistaddPatientPanel();
                break;
            case "Schedule":
                switchReceptionistSchedulePanel();
                break;
            case "Home":
                switchReceptionistIntroducePanel();
                break;
            case "Login":
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Bạn có muốn đăng xuất không?",
                        "Xác nhận đăng xuất",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    switchReceptionistLoginPanel();
                }
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    public void switchReceptionistIntroducePanel() {
        view.getReceptionistPanel().getCardLayout().show(view.getReceptionistPanel().getCenterPanel(), "DentistIntroduce");
    }
    public void switchReceptionistPatientsPanel(){
        view.getReceptionistPanel().getShowPatientsReceptionistPanel().reloadPatientList();
        view.getReceptionistPanel().getCardLayout().show(view.getReceptionistPanel().getCenterPanel(), "ShowPatientsReceptionist");
    }
    public void switchReceptionistaddPatientPanel(){
        view.getReceptionistPanel().getCardLayout().show(view.getReceptionistPanel().getCenterPanel(), "AddPatient");
    }
    public void switchReceptionistSchedulePanel(){
        view.getReceptionistPanel().getCardLayout().show(view.getReceptionistPanel().getCenterPanel(), "Calendar");
    }
    public void switchReceptionistLoginPanel(){
        view.getLoginPanel().resetUser();
        view.getCardLayout().show(view.getContainerPanel(), "LoginPanel");
    }
}
