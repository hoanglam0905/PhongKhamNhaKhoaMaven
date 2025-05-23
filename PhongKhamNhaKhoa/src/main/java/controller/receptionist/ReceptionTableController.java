package controller.receptionist;

import dao.PatientDAO;
import model.Patient;
import view.listPanelMain.MainFrame;
import view.receptionistPanel.ModifyPatient;
import view.receptionistPanel.ShowPatientsReceptionistPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class ReceptionTableController implements MouseListener {
    private MainFrame view;

    public ReceptionTableController(MainFrame view) {
        this.view = view;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable actionTable = view.getReceptionistPanel().getShowPatientsReceptionistPanel().getPatientActionTable();
        int row = actionTable.rowAtPoint(e.getPoint());
        int col = actionTable.columnAtPoint(e.getPoint());

        if (row >= 0) {
            ShowPatientsReceptionistPanel showPatientsPanel = view.getReceptionistPanel().getShowPatientsReceptionistPanel();
            JTable infoTable = showPatientsPanel.getPatientInfoTable();
            DefaultTableModel infoTableModel = showPatientsPanel.getInfoTableModel();
            DefaultTableModel actionTableModel = showPatientsPanel.getActionTableModel();

            if (col == 0) {
                // Chuyển sang panel Followup
                view.getReceptionistPanel().getCardLayout().show(view.getReceptionistPanel().getCenterPanel(), "Followup");
            } else if (col == 1) {
                // Chuyển sang panel NewAppointment
                view.getReceptionistPanel().getCardLayout().show(view.getReceptionistPanel().getCenterPanel(), "NewAppointment");
            } else if (col == 2) {
                // Xử lý nút Sửa
                String phoneNumber = (String) infoTableModel.getValueAt(row, 2); // Lấy số điện thoại từ cột SĐT
                int patientId = PatientDAO.getIdPatient(phoneNumber);

                if (patientId != -1) {
                    // Lấy thông tin bệnh nhân
                    List<Patient> patients = PatientDAO.getListPatients();
                    Patient selectedPatient = null;
                    for (Patient patient : patients) {
                        if (patient.getId() == patientId) {
                            selectedPatient = patient;
                            break;
                        }
                    }

                    if (selectedPatient != null) {
                        // Tạo và chuyển sang panel ModifyPatient
                        ModifyPatient modifyPatientPanel = new ModifyPatient(view.getReceptionistPanel(), selectedPatient);
                        view.getReceptionistPanel().getCenterPanel().add(modifyPatientPanel, "ModifyPatient");
                        view.getReceptionistPanel().getCardLayout().show(view.getReceptionistPanel().getCenterPanel(), "ModifyPatient");

                        // Đặt listener để làm mới bảng sau khi lưu
                        modifyPatientPanel.setOnSaveListener(v -> {
                            // Làm mới bảng
                            infoTableModel.setRowCount(0);
                            actionTableModel.setRowCount(0);
                            List<Object[]> updatedList = PatientDAO.getAllPatients();
                            for (Object[] updatedRow : updatedList) {
                                infoTableModel.addRow(updatedRow);
                                actionTableModel.addRow(new Object[]{"Tái Khám", "Lịch hẹn mới", "✎"});
                            }
                        });
                    } else {
                        JOptionPane.showMessageDialog(view, "Không tìm thấy thông tin bệnh nhân.");
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Không tìm thấy bệnh nhân với số điện thoại: " + phoneNumber);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}