package view.listPanelMain;

import Utils.PaymentQRComponent;
import view.dentistPanel.DentistIntroducePanel;
import view.dentistPanel.DentistTaskbar;
import view.durgStore.*;
import view.login.LoginPanel;

import javax.swing.*;
import java.awt.*;

public class DrugStorePanel extends JPanel {
    private JPanel centerPanel;
    private CardLayout cardLayout;
    private DentistTaskbar dentistTaskbar;
    private DrugStoreMenuPanel drugStoreMenuPanel;
    private ListDrugPanel listDrugPanel;
    private ListBillPanel listBillPanel;
    private DrugBillPanel billPanel;
    private DrugBillConfPanel billConfPanel;
    private PaymentQRComponent paymentQRComponent;
    private DentistIntroducePanel dentistIntroducePanel;

    public DrugStorePanel() {
        initComponents();
    }

    private void initComponents() {
        // Khởi tạo các thành phần
        drugStoreMenuPanel=new DrugStoreMenuPanel();
        dentistTaskbar = new DentistTaskbar();

        listBillPanel = new ListBillPanel();
        listDrugPanel=new ListDrugPanel();
        billPanel=new DrugBillPanel();
        billConfPanel  =new DrugBillConfPanel();
        dentistIntroducePanel=new DentistIntroducePanel();
        try {
            paymentQRComponent=new PaymentQRComponent(2000, "Thanh toán hóa đơn", () -> {
                JOptionPane.showMessageDialog(this, "Cảm ơn bạn đã thanh toán!");

            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // Layout chính
        setLayout(new BorderLayout());

        // Menu bên trái
        drugStoreMenuPanel.setPreferredSize(new Dimension(170, 450));
        drugStoreMenuPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(102, 102, 102)));
        add(drugStoreMenuPanel, BorderLayout.WEST);

        // Taskbar trên cùng
        dentistTaskbar.setPreferredSize(new Dimension(125, 40));
        add(dentistTaskbar, BorderLayout.NORTH);

        // Cài đặt centerPanel với CardLayout
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);
        centerPanel.setPreferredSize(new Dimension(700, 300));

        centerPanel.add(dentistIntroducePanel,"IntroducePanel");
        centerPanel.add(listBillPanel,"Bills");
        centerPanel.add(listDrugPanel,"Drugs");
        centerPanel.add(billPanel,"Bill");
        centerPanel.add(billConfPanel,"BillConf");
        centerPanel.add(paymentQRComponent,"QR");

        // sau này cần add thêm ExaminationPanel hoặc CalendarPanel thì add luôn ở đây

        add(centerPanel, BorderLayout.CENTER); //Chỉ add centerPanel
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public void setCenterPanel(JPanel centerPanel) {
        this.centerPanel = centerPanel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }

    public DentistTaskbar getDentistTaskbar() {
        return dentistTaskbar;
    }

    public void setDentistTaskbar(DentistTaskbar dentistTaskbar) {
        this.dentistTaskbar = dentistTaskbar;
    }

    public DrugStoreMenuPanel getDrugStoreMenuPanel() {
        return drugStoreMenuPanel;
    }

    public void setDrugStoreMenuPanel(DrugStoreMenuPanel drugStoreMenuPanel) {
        this.drugStoreMenuPanel = drugStoreMenuPanel;
    }

    public ListDrugPanel getListDrugPanel() {
        return listDrugPanel;
    }

    public void setListDrugPanel(ListDrugPanel listDrugPanel) {
        this.listDrugPanel = listDrugPanel;
    }

    public ListBillPanel getListBillPanel() {
        return listBillPanel;
    }

    public void setListBillPanel(ListBillPanel listBillPanel) {
        this.listBillPanel = listBillPanel;
    }

    public DrugBillConfPanel getBillConfPanel() {
        return billConfPanel;
    }

    public void setBillConfPanel(DrugBillConfPanel billConfPanel) {
        this.billConfPanel = billConfPanel;
    }

    public DrugBillPanel getBillPanel() {
        return billPanel;
    }

    public void setBillPanel(DrugBillPanel billPanel) {
        this.billPanel = billPanel;
    }

    public PaymentQRComponent getPaymentQRComponent() {
        return paymentQRComponent;
    }

    public void setPaymentQRComponent(PaymentQRComponent paymentQRComponent) {
        this.paymentQRComponent = paymentQRComponent;
    }
}
