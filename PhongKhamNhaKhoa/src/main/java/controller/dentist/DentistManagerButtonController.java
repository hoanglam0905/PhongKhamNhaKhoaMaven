package controller.dentist;

import Utils.JDBCUtil;
import dao.*;
import model.DrugDose;
import model.Service;
import service.ExportToPDF;
import view.listPanelMain.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DentistManagerButtonController implements ActionListener {
    private MainFrame view;
    private int id_pre;

    public DentistManagerButtonController(MainFrame view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println(command);
        if (command.equals("Thêm dịch vụ")) {
            switchDentistServicePanel();
        } else if (command.equals("Thêm danh sách thuốc")) {
            switchDentistAddPrescriptionPanelPanel();
        } else if (command.equals("Xuất đơn thuốc")){
            switchDentistPatient1Panel();
            int id_patient=PatientDAO.getIdPatient(view.getMainPanel().getDentistExaminationPanel().getSdtPatient());
            int id_doctor= DentistDao.getIdDentistLogin(view.getLoginPanel().getAcc(),view.getLoginPanel().getPass());
            ExamDao.updateExam(id_doctor+"",id_patient+"");
            ExportToPDF.prescriptionToPDF(id_pre+"");
        } else if (command.equals("Hủy")) {
            switchDentistPatient1Panel();
        }
    }
    public void switchDentistAddPrescriptionPanelPanel() {
        view.getMainPanel().getServicePanel().readServiceTableData();// doc thong tin cua dich vu
        view.getMainPanel().getServicePanel().showText();
        if(view.getMainPanel().getServicePanel().getTxtQuantity().getText().equals("")){
            JOptionPane.showMessageDialog(view, "Vui lòng điền đầy đủ thông tin !");
        } else
            view.getMainPanel().getCardLayout().show(view.getMainPanel().getCenterPanel(), "addPrescriptionPanel");
    }
    public void switchDentistServicePanel() {
        view.getMainPanel().getDentistExaminationPanel().setInfo();//set thong tin benh nhan
        if(view.getMainPanel().getDentistExaminationPanel().getTfSymptom().getText().equals("")||
                view.getMainPanel().getDentistExaminationPanel().getTreatment().equals("")||
                view.getMainPanel().getDentistExaminationPanel().getTfDiagnosis().getText().equals("")){

            JOptionPane.showMessageDialog(view, "Vui lòng điền đầy đủ thông tin !");
        } else {
            view.getMainPanel().getCardLayout().show(view.getMainPanel().getCenterPanel(), "servicePanel");
        }
    }
    public void switchDentistPatient1Panel() {
        //them vao co so du lieu
        int id_patient=PatientDAO.getIdPatient(view.getMainPanel().getDentistExaminationPanel().getSdtPatient());
        int id_doctor= DentistDao.getIdDentistLogin(view.getLoginPanel().getAcc(),view.getLoginPanel().getPass());
        String symptom=view.getMainPanel().getDentistExaminationPanel().getSymptom();
        String diagnosis=view.getMainPanel().getDentistExaminationPanel().getDiagnosis();
        String treatment=view.getMainPanel().getDentistExaminationPanel().getTreatment();

         id_pre = PrescriptionDAO.insertPrescription(id_doctor, id_patient, diagnosis, treatment, symptom, "Uống thuốc đầy đủ");

        List<DrugDose> listDrug = view.getMainPanel().getAddPrescriptionPanel().getListDrugDose();
        List<Integer> listID = new ArrayList<>();
        Connection con = null;

        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT id FROM Drug WHERE name = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            for (DrugDose drug : listDrug) {
                pst.setString(1, drug.getName());
                try (ResultSet res = pst.executeQuery()) { // Auto-close res sau mỗi vòng
                    if (res.next()) {
                        listID.add(res.getInt("id"));
                    }
                }
            }
            pst.close();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < listID.size(); i++) {
            System.out.println(listID.get(i));
        }
        for (int i = 0; i < listID.size(); i++) {
            PrescriptionDAO.insertPrescriptionDetail(id_pre,listID.get(i),(listDrug.get(i).getDoseMor()+listDrug.get(i).getDoseNoon()+listDrug.get(i).getDoseAfternoon())*7,listDrug.get(i).getDoseMor(),listDrug.get(i).getDoseNoon(),listDrug.get(i).getDoseAfternoon());
        }

        //them dich vu vao csdl
        Map<Service,Integer> listSer=view.getMainPanel().getServicePanel().getListSevice();
        List<Integer>listIdSer=new ArrayList<>();
        List<Integer>quatitySer=new ArrayList<>();
        for (Map.Entry<Service, Integer> entry : listSer.entrySet()) {
            quatitySer.add(entry.getValue());
        }
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT id FROM Service WHERE name = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            for (Map.Entry<Service, Integer> entry : listSer.entrySet()) {
                pst.setString(1, entry.getKey().getName());
                try (ResultSet res = pst.executeQuery()) { // Auto-close res sau mỗi vòng
                    if (res.next()) {
                        listIdSer.add(res.getInt("id"));
                    }
                }
            }
            pst.close();
            con.close();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < listIdSer.size(); i++) {
            ServiceDao.addPrescriptionServiceDetail(id_pre,listIdSer.get(i),listIdSer.get(i));
        }

        //xóa hết các text khi chèn thành công
        view.getMainPanel().getDentistExaminationPanel().resetInfo();
        view.getMainPanel().getAddPrescriptionPanel().resetInfor();
        view.getMainPanel().getServicePanel().resetInfor();

        view.getMainPanel().getAddPrescriptionPanel().showText();
        view.getMainPanel().getCardLayout().show(view.getMainPanel().getCenterPanel(), "Patient1");

    }
}
