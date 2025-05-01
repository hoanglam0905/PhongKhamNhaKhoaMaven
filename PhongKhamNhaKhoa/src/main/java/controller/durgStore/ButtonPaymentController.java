package controller.durgStore;

import Utils.PaymentQRComponent;
import reponsitory.dao.BillDao;
import service.ExportToPDF;
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
            String idStr = view.getDrugStorePanel().getBillPanel().getIdBill().getText();
            int id = Integer.parseInt(idStr);
            String content = "Thanh toán hóa đơn #" + id;

            PaymentQRComponent qrPanel = view.getDrugStorePanel().getPaymentQRComponent();
            qrPanel.setAmountAndContent(BillDao.getPriceInPre(id), content);

            qrPanel.setOnPaymentSuccess(() -> {
                // 1. Cập nhật DB
                BillDao.updatePaymentStatusToPaid(id);

                // 2. Cập nhật giao diện
                view.getDrugStorePanel().getBillConfPanel().getStatus().setText("Đã thanh toán");

                // 3. Xuất PDF
                ExportToPDF.billToPDF(id+"");

                // 4. Chuyển panel
                view.getDrugStorePanel().getCardLayout().show(
                        view.getDrugStorePanel().getCenterPanel(), "BillConf"
                );
            });

            view.getDrugStorePanel().getCardLayout().show(
                    view.getDrugStorePanel().getCenterPanel(), "QR"
            );
        }
    }
    public void switchPaySus() {
        String idStr = view.getDrugStorePanel().getBillPanel().getIdBill().getText();
        int id = Integer.parseInt(idStr);

        BillDao.updatePaymentStatusToPaid(id);

        view.getDrugStorePanel().getBillConfPanel().getStatus().setText("Đã thanh toán");

        ExportToPDF.billToPDF(id+"");

        view.getDrugStorePanel().getCardLayout().show(view.getDrugStorePanel().getCenterPanel(), "BillConf");
    }

}
