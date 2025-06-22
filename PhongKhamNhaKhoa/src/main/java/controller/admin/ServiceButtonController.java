package controller.admin;

import model.Service;
import reponsitory.DrugReponsitory;
import reponsitory.ServiceReponsitory;
import view.listPanelMain.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class ServiceButtonController implements ActionListener {
    private MainFrame view;

    public ServiceButtonController(MainFrame view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        System.out.println("Đã click: "+action);
        if (action.equals("Chỉnh sửa")) {
            switchEdit();
        } else if (action.equals("Xóa")) {
            int result = JOptionPane.showConfirmDialog(
                    null,
                    "Bạn có chắc chắn muốn xóa dịch vụ này không?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                String []sl=view.getAdminPanel().getAdminServiceInfo().getLblId().getText().split(" ");
                ServiceReponsitory.deleteService(sl[sl.length-1]+"");
                JOptionPane.showMessageDialog(null, "Đã xóa thành công!");
                switchService();
            }
        } else if(action.equals("Thêm")){
            switchAdd();
        } else if (action.equals("Xác nhận")){
            String []sl=view.getAdminPanel().getAdminServiceInfo().getLblId().getText().split(" ");
            ServiceReponsitory.updateService(Integer.parseInt(sl[sl.length-1]),view.getAdminPanel().getAdminServiceEdit().getTfName().getText(),Integer.parseInt(view.getAdminPanel().getAdminServiceEdit().getTfPrice().getText()));
            switchService();
        }else if(action.equals("Thêm dịch vụ")){
            if (view.getAdminPanel().getAdminServiceAdd().getTfName().getText().trim().isEmpty() || view.getAdminPanel().getAdminServiceAdd().getTfPrice().getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ tên, giá và số lượng!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                return;
            }
            ServiceReponsitory.insertService(view.getAdminPanel().getAdminServiceAdd().getTfName().getText(),Double.valueOf(view.getAdminPanel().getAdminServiceAdd().getTfPrice().getText()));
            JOptionPane.showMessageDialog(view,"Đã thêm dịch vụ thành công");
        }
    }
    public void switchEdit(){
        view.getAdminPanel().getAdminServiceEdit().loadData(view.getAdminPanel().getAdminServiceInfo().serviceId);
        view.getAdminPanel().getCardLayout().show(view.getAdminPanel().getCenterPanel(),"adminServiceEdit");
    }
    public void switchAdd(){
        view.getAdminPanel().getCardLayout().show(view.getAdminPanel().getCenterPanel(),"adminServiceAdd");
    }
    public void switchService() {
        view.getAdminPanel().getAdminService().loadServiceData();
        view.getAdminPanel().getCardLayout().show(view.getAdminPanel().getCenterPanel(), "adminService");
    }
}
