package controller.admin;

import reponsitory.EmployeeRepository;
import view.admin.EditEmployeeInfoDialog;
import view.listPanelMain.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LableController implements MouseListener {
    private final MainFrame view;

    public LableController(MainFrame view) {
        this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel clickedLabel = (JLabel) e.getComponent();
        String name = clickedLabel.getName();

        if (name == null) return;
        System.out.println("Đã click: " + name);

        switch (name) {
            case "ListPatient":
                switchPatient();
                break;
            case "ListService":
                switchService();
                break;
            case "ListDrug":
                switchDrug();
                break;
            case "ListEmployee":
                switchEmployee();
                break;
            case "TK":
                switchTK();
                break;
            case "Login":
                showOptionsDialog();
                break;
        }
    }

    private void showOptionsDialog() {
        JDialog optionsDialog = new JDialog((Frame) null, "Tùy chọn", true);
        optionsDialog.setLayout(new FlowLayout());
        optionsDialog.setSize(300, 150);
        optionsDialog.setLocationRelativeTo(null);

        JButton logoutButton = new JButton("Đăng xuất");
        JButton editInfoButton = new JButton("Sửa thông tin");
        JButton timekeepingButton = new JButton("Chấm công");

        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Bạn có muốn đăng xuất không?",
                    "Xác nhận đăng xuất",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                switchReceptionistLoginPanel();
                optionsDialog.dispose();
            }
        });

        editInfoButton.addActionListener(e -> {
            String acc = view.getLoginPanel().getAcc();
            String pass = view.getLoginPanel().getPass();
            EmployeeRepository.EmployeeInfo info = EmployeeRepository.getEmployeeInfo(acc, pass);
            if (info != null) {
                new EditEmployeeInfoDialog(view, info.id, info.username, info.password, info.profilePicture).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(optionsDialog, "Không thể lấy thông tin nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            optionsDialog.dispose();
        });

        timekeepingButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Chức năng chấm công đang được phát triển!");
            optionsDialog.dispose();
        });

        optionsDialog.add(logoutButton);
        optionsDialog.add(editInfoButton);
        optionsDialog.add(timekeepingButton);

        optionsDialog.setVisible(true);
    }

    public void switchReceptionistLoginPanel() {
        view.getLoginPanel().resetUser();
        view.getCardLayout().show(view.getContainerPanel(), "LoginPanel");
    }

    public void switchPatient() {
        view.getAdminPanel().getAdminPatient().loadPatientData();
        view.getAdminPanel().getCardLayout().show(view.getAdminPanel().getCenterPanel(), "adminPatient");
    }

    public void switchService() {
        view.getAdminPanel().getAdminService().loadServiceData();
        view.getAdminPanel().getCardLayout().show(view.getAdminPanel().getCenterPanel(), "adminService");
    }

    public void switchDrug() {
        view.getAdminPanel().getAdminDrug().loadDrugData();
        view.getAdminPanel().getCardLayout().show(view.getAdminPanel().getCenterPanel(), "adminDrug");
    }

    public void switchEmployee() {
        view.getAdminPanel().getAdminEmployee().loadEmployeeData();
        view.getAdminPanel().getCardLayout().show(view.getAdminPanel().getCenterPanel(), "adminEmployee");
    }

    public void switchTK() {
        view.getAdminPanel().getCardLayout().show(view.getAdminPanel().getCenterPanel(), "adminStatistical");
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    private JLabel selectedLabel = null;

    public void setLabelEvent(JLabel... labels) {
        for (JLabel label : labels) {
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (selectedLabel != null) {
                        selectedLabel.setBackground(Color.WHITE);
                        selectedLabel.setOpaque(false);
                        selectedLabel.setBorder(null);
                        selectedLabel.repaint();
                    }
                    if ("Home".equals(label.getName())) {
                        selectedLabel = null;
                    } else {
                        selectedLabel = label;
                        selectedLabel.setBackground(Color.LIGHT_GRAY);
                        selectedLabel.setOpaque(true);
                        selectedLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
                        selectedLabel.repaint();
                    }
                }
            });
        }
    }
}