package view.listPanelMain;

import view.dentistPanel.DentistTaskbar;
import view.durgStore.DrugStoreMenuPanel;

import javax.swing.*;
import java.awt.*;

public class DrugStorePanel extends JPanel {
    private JPanel centerPanel;
    private CardLayout cardLayout;
    private DentistTaskbar dentistTaskbar;
    private DrugStoreMenuPanel drugStoreMenuPanel;

    public DrugStorePanel() {
        initComponents();
    }

    private void initComponents() {
        // Khởi tạo các thành phần
        drugStoreMenuPanel=new DrugStoreMenuPanel();
        dentistTaskbar = new DentistTaskbar();

//        receptionistCalendarPanel = new ReceptionistCalendarPanel();
//        addPatientPanel= new AddPatientPanel();
//        showPatientsReceptionistPanel = new ShowPatientsReceptionistPanel();
//        followupPanel = new FollowupPanel();
//        newAppointmentPanel = new NewAppointmentPanel();
//        dentistIntroducePanel = new DentistIntroducePanel();

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

//        centerPanel.add(receptionistCalendarPanel,"Calendar");
//        centerPanel.add(addPatientPanel,"AddPatient");
//        centerPanel.add(showPatientsReceptionistPanel,"ShowPatientsReceptionist");
//        centerPanel.add(followupPanel,"Followup");
//        centerPanel.add(newAppointmentPanel,"NewAppointment");
//        centerPanel.add(dentistIntroducePanel,"DentistIntroduce");
        // sau này cần add thêm ExaminationPanel hoặc CalendarPanel thì add luôn ở đây

        add(centerPanel, BorderLayout.CENTER); //Chỉ add centerPanel
    }
}
