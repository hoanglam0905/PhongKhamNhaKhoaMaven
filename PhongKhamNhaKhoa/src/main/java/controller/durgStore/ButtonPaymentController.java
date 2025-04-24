package controller.durgStore;

import view.listPanelMain.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonPaymentController implements ActionListener {
    private MainFrame view;
    public ButtonPaymentController(MainFrame mainFrame) {
        this.view = mainFrame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("Tiền mặt")) {
            switchPaySus();
        } else if (actionCommand.equals("Quét mã")) {
            System.out.println("Tính năng đang được thêm!");
        }
    }
    public void switchPaySus(){
        view.getDrugStorePanel().getCardLayout().show(view.getDrugStorePanel().getCenterPanel(),"BillConf");
    }
}
