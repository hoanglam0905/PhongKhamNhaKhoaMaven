package view.listPanelMain;

import controller.dentist.*;
import controller.login.LoginButtonController;
import controller.receptionist.ReceptionistLableController;
import controller.receptionist.ReceptionistSearch2Controller;
import controller.receptionist.ReceptionistSearchController;
import view.login.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class MainFrame extends JFrame {
    JPanel containerPanel; // Panel chính dùng CardLayout
    CardLayout cardLayout; // CardLayout điều khiển các panel
    LoginPanel loginPanel;
    DentistPanel mainPanel;
    ReceptionistPanel receptionistPanel;

    public MainFrame() {
        initComponents();
    }

    private void initComponents() {
        // Khởi tạo layout và panel
        loginPanel = new LoginPanel();
        mainPanel = new DentistPanel();
        receptionistPanel = new ReceptionistPanel();

        cardLayout = new CardLayout();
        containerPanel = new JPanel(cardLayout);

        // Thêm các "card" vào containerPanel
        containerPanel.add(loginPanel, "LoginPanel");
        containerPanel.add(mainPanel, "DentistPanel");
        containerPanel.add(receptionistPanel, "ReceptionistPanel");

        // Set layout chính cho MainFrame
        setLayout(new BorderLayout());
        add(containerPanel, BorderLayout.CENTER);

        // Ban đầu show login với kích thước cố định
        cardLayout.show(containerPanel, "LoginPanel");
        setSize(700, 400); // Kích thước cố định cho LoginPanel
        setLocationRelativeTo(null); // Căn giữa màn hình

        // Nếu muốn chuyển panel theo role lúc đăng nhập xong kiểm tra
        addListeners();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Phương thức để chuyển sang full-screen sau khi đăng nhập
    public void switchToFullScreen() {
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Chuyển sang full-screen
    }

    // Thêm sự kiện
    public void addListeners() {
        this.mainPanel.getDentistTaskbar().getLblDoctorName().setName("Login");
        this.mainPanel.getDentistMenuPanel().getLblHome().setName("Home");
        this.mainPanel.getDentistMenuPanel().getLblSchedule().setName("Schedule");
        this.mainPanel.getDentistMenuPanel().getLblPatients().setName("Patients");
        this.mainPanel.getDentistMenuPanel().getLblFeature1().setName("Feature 1");
        this.mainPanel.getDentistMenuPanel().getLblFeature2().setName("Feature 2");

        // Add xử lý cho menu
        MouseListener ml = new DentistManagerLableController(this);
        this.mainPanel.getDentistTaskbar().getLblDoctorName().addMouseListener(ml);
        this.mainPanel.getDentistMenuPanel().getLblHome().addMouseListener(ml);
        this.mainPanel.getDentistMenuPanel().getLblSchedule().addMouseListener(ml);
        this.mainPanel.getDentistMenuPanel().getLblPatients().addMouseListener(ml);
        this.mainPanel.getDentistMenuPanel().getLblFeature1().addMouseListener(ml);
        this.mainPanel.getDentistMenuPanel().getLblFeature2().addMouseListener(ml);

        // Add xử lý khi ấn vào bệnh nhân có trong lịch
        DentistManagerTableController ma = new DentistManagerTableController(this);
        this.mainPanel.getDentistListPatient().getTblPatients().addMouseListener(ma);
        // this.mainPanel.getDentistListPatient2().getTblPatients().addMouseListener(ma);

        // Add xử lý khi tìm kiếm
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

        // Set name cho các label menu của lễ tân
        this.receptionistPanel.getDentistTaskbar().getLblDoctorName().setName("Login");
        this.receptionistPanel.getReceptionistMenuPanel().getLblHome().setName("Home"); // intro
        this.receptionistPanel.getReceptionistMenuPanel().getLblSchedule().setName("Schedule"); // xem lịch khám
        this.receptionistPanel.getReceptionistMenuPanel().getLblPatients().setName("Patients"); // xem bệnh nhân
        this.receptionistPanel.getReceptionistMenuPanel().getLblFeature1().setName("addPatient"); // thêm bệnh nhân

        // Add xử lý khi ấn menu trong giao diện lễ tân
        MouseListener mlr = new ReceptionistLableController(this);
        this.receptionistPanel.getDentistTaskbar().getLblDoctorName().addMouseListener(mlr);
        this.receptionistPanel.getReceptionistMenuPanel().getLblHome().addMouseListener(mlr);
        this.receptionistPanel.getReceptionistMenuPanel().getLblSchedule().addMouseListener(mlr);
        this.receptionistPanel.getReceptionistMenuPanel().getLblPatients().addMouseListener(mlr);
        this.receptionistPanel.getReceptionistMenuPanel().getLblFeature1().addMouseListener(mlr);

        // Add xử lý khi button dentist bị ấn
        ActionListener ald = new DentistManagerButtonController(this);
        this.mainPanel.getDentistExaminationPanel().getBtnAddService().addActionListener(ald);
        this.mainPanel.getDentistExaminationPanel().getBtnCancel().addActionListener(ald);
        this.mainPanel.getServicePanel().getBtnAddDrugs().addActionListener(ald);
        this.mainPanel.getAddPrescriptionPanel().getBtnAddDrugs().addActionListener(ald);
        this.mainPanel.getAddPrescriptionPanel().getBtnConfirm().addActionListener(ald);

        // Add xử lý cho nút đăng nhập
        ActionListener all = new LoginButtonController(this);
        this.loginPanel.getLoginButton().addActionListener(all);
    }

    // Getters và Setters
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }
}