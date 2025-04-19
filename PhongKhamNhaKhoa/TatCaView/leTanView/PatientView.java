package leTanView;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//import com.lowagie.text.List;
import java.util.ArrayList;
import java.util.List;

import model.Patient;

//view/PatientView.java
public class PatientView extends JPanel {
 private JButton printButton = new JButton("In danh sách bệnh nhân");
 private JTable patientTable;
 private DefaultTableModel tableModel;

 public PatientView() {
     setLayout(new BorderLayout());
     tableModel = new DefaultTableModel(new Object[]{"ID", "Tên", "Ngày sinh", "SĐT"}, 0);
     patientTable = new JTable(tableModel);
     add(printButton, BorderLayout.NORTH);
     add(new JScrollPane(patientTable), BorderLayout.CENTER);
 }

 public JButton getPrintButton() {
     return printButton;
 }

 public void updatePatientTable(List<Patient> patients) {
     tableModel.setRowCount(0); // clear bảng cũ
     for (Patient p : patients) {
         tableModel.addRow(new Object[]{
             p.getId(), p.getName(), p.getBirthDate(), p.getPhoneNumber()
         });
     }
 }
}
