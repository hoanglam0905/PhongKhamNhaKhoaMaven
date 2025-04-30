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
            String content = "Thanh toán hóa đơn #";

            var qrPanel = view.getDrugStorePanel().getPaymentQRComponent();
            qrPanel.setAmountAndContent(2000, content);

            // Gán callback khi thanh toán thành công  chuyển sang BillConf
            qrPanel.setOnPaymentSuccess(() -> {
                view.getDrugStorePanel().getCardLayout().show(
                        view.getDrugStorePanel().getCenterPanel(), "BillConf"
                );
            });

            // Hiển thị panel QR
            view.getDrugStorePanel().getCardLayout().show(
                    view.getDrugStorePanel().getCenterPanel(), "QR"
            );
        }
    }
    public void switchPaySus(){
        view.getDrugStorePanel().getCardLayout().show(view.getDrugStorePanel().getCenterPanel(),"BillConf");
    }
}
