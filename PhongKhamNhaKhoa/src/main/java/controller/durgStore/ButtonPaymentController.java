package controller.durgStore;

import Utils.PaymentQRComponent;
import reponsitory.BillReponsitory;
import reponsitory.DrugReponsitory;
import service.ExportToPDF;
import view.listPanelMain.MainFrame;

import javax.swing.table.DefaultTableModel;
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
            qrPanel.setAmountAndContent(BillReponsitory.getPriceInPre(id), content);

            qrPanel.setOnPaymentSuccess(() -> {
                // Cập nhật DB
                BillReponsitory.updatePaymentStatusToPaid(id);
                // Cập nhật giao diện
                view.getDrugStorePanel().getBillConfPanel().getStatus().setText("Đã thanh toán");
                // Xuất PDF
                ExportToPDF.billToPDF(id+"");
                //  Chuyển panel
                view.getDrugStorePanel().getCardLayout().show(
                        view.getDrugStorePanel().getCenterPanel(), "BillConf"
                );
                // cập nhật lại data thuốc
                DefaultTableModel model = (DefaultTableModel) view.getDrugStorePanel().getBillPanel().getTableMedicine().getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    String drugName = model.getValueAt(i, 1).toString();        // Tên thuốc
                    int quantity = Integer.parseInt(model.getValueAt(i, 2).toString());  // Số lượng
                    DrugReponsitory.updateDrugQuantity(drugName, quantity);
                }

            });
            view.getDrugStorePanel().getCardLayout().show(
                    view.getDrugStorePanel().getCenterPanel(), "QR"
            );
        }
    }
    public void switchPaySus() {
        String idStr = view.getDrugStorePanel().getBillPanel().getIdBill().getText();
        int id = Integer.parseInt(idStr);

        BillReponsitory.updatePaymentStatusToPaid(id);

        view.getDrugStorePanel().getBillConfPanel().getStatus().setText("Đã thanh toán");

        ExportToPDF.billToPDF(id+"");

        view.getDrugStorePanel().getCardLayout().show(view.getDrugStorePanel().getCenterPanel(), "BillConf");
        // Cập nhật số lượng thuốc
        DefaultTableModel model = (DefaultTableModel) view.getDrugStorePanel().getBillPanel().getTableMedicine().getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String drugName = model.getValueAt(i, 1).toString();        // Tên thuốc
            int quantity = Integer.parseInt(model.getValueAt(i, 2).toString());  // Số lượng
            DrugReponsitory.updateDrugQuantity(drugName, quantity);
        }
    }

}
