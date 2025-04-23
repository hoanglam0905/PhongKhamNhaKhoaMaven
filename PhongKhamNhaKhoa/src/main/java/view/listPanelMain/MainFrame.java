package view.listPanelMain;

import controller.dentist.*;
import controller.durgStore.ListBillRadioButtonController;
import controller.login.LoginButtonController;
import controller.receptionist.ReceptionTableController;
import controller.receptionist.ReceptionistLableController;
import controller.receptionist.ReceptionistSearch2Controller;
import controller.receptionist.ReceptionistSearchController;
import view.durgStore.ListBillPanel;
import view.durgStore.ListDrugPanel;
import view.login.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class MainFrame extends JFrame {
    private JPanel containerPanel; // Panel chính dùng CardLayout
    private CardLayout cardLayout; // CardLayout điều khiển các panel
    private LoginPanel loginPanel;
    private DentistPanel mainPanel;
    private ReceptionistPanel receptionistPanel;
    private DrugStorePanel drugStorePanel;

    public MainFrame() {
        initComponents();
    }

    private void initComponents() {
        // Khởi tạo layout và panel
        loginPanel = new LoginPanel();
        mainPanel = new DentistPanel();
        receptionistPanel = new ReceptionistPanel();
        drugStorePanel =new DrugStorePanel();


        cardLayout = new CardLayout();
        containerPanel = new JPanel(cardLayout);

        // Thêm các "card" vào containerPanel
        containerPanel.add(loginPanel, "LoginPanel");
        containerPanel.add(mainPanel, "DentistPanel");
        containerPanel.add(receptionistPanel, "ReceptionistPanel");
        containerPanel.add(drugStorePanel,"drugstore");
        // Set layout chính cho MainFrame
        setLayout(new BorderLayout());
        add(containerPanel, BorderLayout.CENTER);

        // Ban đầu show login
        cardLayout.show(containerPanel, "LoginPanel");

        // Nếu muốn chuyển panel theo role lúc đăng nhập xong kiểm tra
        addListeners();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    //Thêm sự kiện
    public void addListeners() {
        this.mainPanel.getDentistTaskbar().getLblDoctorName().setName("Login");
        this.mainPanel.getDentistMenuPanel().getLblHome().setName("Home");
        this.mainPanel.getDentistMenuPanel().getLblSchedule().setName("Schedule");
        this.mainPanel.getDentistMenuPanel().getLblPatients().setName("Patients");
        this.mainPanel.getDentistMenuPanel().getLblFeature1().setName("Feature 1");
        this.mainPanel.getDentistMenuPanel().getLblFeature2().setName("Feature 2");

        //add xử lí cho menu
        MouseListener ml = new DentistManagerLableController(this);
        this.mainPanel.getDentistTaskbar().getLblDoctorName().addMouseListener(ml);
        this.mainPanel.getDentistMenuPanel().getLblHome().addMouseListener(ml);
        this.mainPanel.getDentistMenuPanel().getLblSchedule().addMouseListener(ml);
        this.mainPanel.getDentistMenuPanel().getLblPatients().addMouseListener(ml);
        this.mainPanel.getDentistMenuPanel().getLblFeature1().addMouseListener(ml);
        this.mainPanel.getDentistMenuPanel().getLblFeature2().addMouseListener(ml);

        //add xử lí khi ấn vào bệnh nhân có trong lịch
        DentistManagerTableController ma=new DentistManagerTableController(this);
        this.mainPanel.getDentistListPatient().getTblPatients().addMouseListener(ma);
//        this.mainPanel.getDentistListPatient2().getTblPatients().addMouseListener(ma);
        //add xử lí khi tìm kiếm
        this.mainPanel.getDentistListPatient().getTfSearch().getDocument().addDocumentListener(
                new DentistPatientSearchController(this.mainPanel.getDentistListPatient())
        );
        this.mainPanel.getDentistListPatient2().getTfSearch().getDocument().addDocumentListener(
                new DentistPatient2SearchController(this.mainPanel.getDentistListPatient2())
        );
        this.receptionistPanel.getShowPatientsReceptionistPanel().getTxtSearch().getDocument().addDocumentListener(
                new ReceptionistSearchController(this.receptionistPanel.getShowPatientsReceptionistPanel())
        );
        this.receptionistPanel.getReceptionistCalendarPanel().getTfSearch().getDocument().addDocumentListener(
                new ReceptionistSearch2Controller(this.receptionistPanel.getReceptionistCalendarPanel())
        );
        //set name cho các label menu của lễ tân
        this.receptionistPanel.getDentistTaskbar().getLblDoctorName().setName("Login");
        this.receptionistPanel.getReceptionistMenuPanel().getLblHome().setName("Home");//intro
        this.receptionistPanel.getReceptionistMenuPanel().getLblSchedule().setName("Schedule");// xem lịch khám
        this.receptionistPanel.getReceptionistMenuPanel().getLblPatients().setName("Patients");//xem bệnh nhân
        this.receptionistPanel.getReceptionistMenuPanel().getLblFeature1().setName("addPatient");//thêm bệnh nhân
        //add xử lí khi ấn menu trong giao diện lễ tân
        MouseListener mlr=new ReceptionistLableController(this);
        this.receptionistPanel.getDentistTaskbar().getLblDoctorName().addMouseListener(mlr);
        this.receptionistPanel.getReceptionistMenuPanel().getLblHome().addMouseListener(mlr);
        this.receptionistPanel.getReceptionistMenuPanel().getLblSchedule().addMouseListener(mlr);
        this.receptionistPanel.getReceptionistMenuPanel().getLblPatients().addMouseListener(mlr);
        this.receptionistPanel.getReceptionistMenuPanel().getLblFeature1().addMouseListener(mlr);
        //add xử lí khi button dentist bị ấn
        ActionListener ald=new DentistManagerButtonController(this);
        this.mainPanel.getDentistExaminationPanel().getBtnAddService().addActionListener(ald);
        this.mainPanel.getDentistExaminationPanel().getBtnCancel().addActionListener(ald);
        this.mainPanel.getServicePanel().getBtnAddDrugs().addActionListener(ald);
        //chưa thấy nút hủy cua dich vu
        this.mainPanel.getAddPrescriptionPanel().getBtnAddDrugs().addActionListener(ald);
        this.mainPanel.getAddPrescriptionPanel().getBtnConfirm().addActionListener(ald);
        //
        ActionListener all=new LoginButtonController(this);
        this.loginPanel.getLoginButton().addActionListener(all);

        ActionListener allb=new ListBillRadioButtonController(this);
        this.drugStorePanel.getListBillPanel().getRbHT().addActionListener(allb);
        this.drugStorePanel.getListBillPanel().getRbChuaHT().addActionListener(allb);
        //thêm sự kiện khi click vào sửa, thêm lịch khám và tái khám
        MouseListener mlrep=new ReceptionTableController(this);
        this.receptionistPanel.getShowPatientsReceptionistPanel().getPatientActionTable().addMouseListener(mlrep);
    }

    public DentistPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(DentistPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public ReceptionistPanel getReceptionistPanel() {
        return receptionistPanel;
    }

    public void setReceptionistPanel(ReceptionistPanel receptionistPanel) {
        this.receptionistPanel = receptionistPanel;
    }

    public LoginPanel getLoginPanel() {
        return loginPanel;
    }

    public void setLoginPanel(LoginPanel loginPanel) {
        this.loginPanel = loginPanel;
    }

    public JPanel getContainerPanel() {
        return containerPanel;
    }

    public void setContainerPanel(JPanel containerPanel) {
        this.containerPanel = containerPanel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }

    public DrugStorePanel getDrugStorePanel() {
        return drugStorePanel;
    }

    public void setDrugStorePanel(DrugStorePanel drugStorePanel) {
        this.drugStorePanel = drugStorePanel;
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
