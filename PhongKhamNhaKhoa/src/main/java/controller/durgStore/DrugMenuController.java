package controller.durgStore;

import view.listPanelMain.MainFrame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DrugMenuController implements MouseListener {
    private MainFrame view;

    public DrugMenuController(MainFrame mainFrame) {
        this.view=mainFrame;
    }

    public void setMainFrame(MainFrame view) {
        this.view = view;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel clickedLabel = (JLabel) e.getComponent();
        String name = clickedLabel.getName();

        if (name == null) return;
        System.out.println("Đã click: " + name);

        switch (name) {
            case "Bills":
                switchBillsPanel();
                break;
            case "ListMedicine":
                switchListMedicinePanel();
                break;
        }
    }

    private void switchBillsPanel() {
        view.getDrugStorePanel().getCardLayout().show(view.getDrugStorePanel().getCenterPanel(), "Bills");
    }

    private void switchListMedicinePanel() {
        view.getDrugStorePanel().getCardLayout().show(view.getDrugStorePanel().getCenterPanel(), "Drugs");
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
