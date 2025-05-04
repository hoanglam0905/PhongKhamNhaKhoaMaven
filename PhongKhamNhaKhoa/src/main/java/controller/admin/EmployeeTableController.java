package controller.admin;

import view.listPanelMain.MainFrame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EmployeeTableController implements MouseListener {
    private MainFrame view;

    public EmployeeTableController(MainFrame view) {
        this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable table = (JTable) e.getSource();

        // Kiểm tra double-click
        if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
            int row = table.getSelectedRow();
            // Gọi panel khám
            switchEmpInfro(
                    table.getValueAt(row, 0).toString() //id của nhân viên, dùng nó để truy xuất thông tin
            );
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
    public void switchEmpInfro(String id){
        //set lại text thông tin của nhân viên
        view.getAdminPanel().getCardLayout().show(view.getAdminPanel().getCenterPanel(),"adminEmployeeInfo");
    }
}
