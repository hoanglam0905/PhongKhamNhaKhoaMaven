package controller.durgStore;

import view.listPanelMain.MainFrame;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ListBillTableController extends MouseAdapter {
    private MainFrame view;
    public ListBillTableController(MainFrame view) {
        this.view = view;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        JTable table = (JTable) e.getSource();

        // Kiểm tra double-click
        if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
            int row = table.getSelectedRow();
            String tenBN = table.getValueAt(row, 1).toString();
            System.out.println("Đã double-click bệnh nhân: " + tenBN);

            // hàm này đổi giá trị truyền vào
//            switchDrugBillPanel(table.getValueAt(row, 1).toString(),
//                    table.getValueAt(row, 2).toString(),
//                    table.getValueAt(row, 3).toString(),
//                    table.getValueAt(row, 4).toString(),
//                    table.getValueAt(row, 5).toString());
            switchDrugBillPanel("001","Hoàng Lam","Hoàng Minh","300 000 VND","Chưa thanh toán");

        }
    }
    public MainFrame getView() {
        return view;
    }

    public void setView(MainFrame view) {
        this.view = view;
    }
    public void switchDrugBillPanel(String id, String namePat, String nameDen, String price, String status) {
        view.getDrugStorePanel().getBillPanel().getIdBill().setText(id);
        view.getDrugStorePanel().getBillPanel().getNamePat().setText(namePat);
        view.getDrugStorePanel().getBillPanel().getNameDen().setText(nameDen);
        view.getDrugStorePanel().getBillPanel().getPrice().setText(String.valueOf(price));
        view.getDrugStorePanel().getBillPanel().getStatus().setText(status);
        view.getDrugStorePanel().getCardLayout().show(view.getDrugStorePanel().getCenterPanel(),"Bill");
    }
//    public void switchDrugConfBillPanel(String id, String namePat, String nameDen, long price, String status) {
//        view.getDrugStorePanel().getBillPanel().getIdBill().setText(id);
//        view.getDrugStorePanel().getBillPanel().getNamePat().setText(namePat);
//        view.getDrugStorePanel().getBillPanel().getNameDen().setText(nameDen);
//        view.getDrugStorePanel().getBillPanel().getPrice().setText(String.valueOf(price));
//        view.getDrugStorePanel().getBillPanel().getStatus().setText(status);
//        view.getDrugStorePanel().getCardLayout().show(view.getDrugStorePanel().getCenterPanel(),"BillConf");
////        view.getDrugStorePanel().getCardLayout().show(view.getMainPanel().getCenterPanel(), "Examination");
//    }
}
