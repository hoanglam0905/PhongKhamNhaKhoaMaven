package controller.receptionist;

import view.listPanelMain.MainFrame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ReceptionTableController implements MouseListener{
    private MainFrame view;
    public ReceptionTableController(MainFrame view) {
        this.view = view;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = view.getReceptionistPanel().getShowPatientsReceptionistPanel().getPatientActionTable().rowAtPoint(e.getPoint());
                int col = view.getReceptionistPanel().getShowPatientsReceptionistPanel().getPatientActionTable().columnAtPoint(e.getPoint());

                if (row >= 0) {
                    String patientName = (String) view.getReceptionistPanel().getShowPatientsReceptionistPanel().getPatientInfoTable().getValueAt(row, 1);
                    if (col == 0) {
                        view.getReceptionistPanel().getCardLayout().show(view.getReceptionistPanel().getCenterPanel(), "Followup");
                    } else if (col == 1) {
                        view.getReceptionistPanel().getCardLayout().show(view.getReceptionistPanel().getCenterPanel(), "NewAppointment");
                    } else if (col == 2) {
                        JOptionPane.showMessageDialog(null, "Sửa thông tin bệnh nhân: " + patientName);
                    }
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
}
