package controller.admin;

import view.admin.AdminEmployeeAdd;
import view.admin.AdminEmployeeEdit;
import view.listPanelMain.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JOptionPane;

import service.EmployeeService;

public class EmployeeButtonController implements ActionListener {
    private MainFrame view;

    public EmployeeButtonController(MainFrame view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        System.out.println("Đã click: "+action);
        if (action.equals("Chỉnh sửa")) {
            try {
				switchEdit();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } else if (action.equals("Xóa")) {
            
        } else if(action.equals("Thêm")){
            switchAdd();
        } else if (action.equals("Xác nhận")){
        	updateValue();

        }else if(action.equals("Thêm nhân viên")){
        	insertEmployee();
        }
    }
    
    private void insertEmployee() {
    	AdminEmployeeAdd addPanel = view.getAdminPanel().getAdminEmployeeAdd();

        String name = addPanel.getTfName().getText().trim();
        String phone = addPanel.getTfPhone().getText().trim();
        java.util.Date birthUtilDate = addPanel.getDateChooser().getDate();
        if (birthUtilDate == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn ngày sinh hợp lệ.");
            return;
        }
        java.sql.Date birthDate = new java.sql.Date(birthUtilDate.getTime());

        int gender = addPanel.getCbGender().getSelectedItem().equals("Nam") ? 1 : 0;
        String address = addPanel.getTfAddress().getText().trim();
        String cccd = addPanel.getTfCCCD().getText().trim();
        double salary = Double.parseDouble(addPanel.getTfSalary().getText().trim());
        String role = (String) addPanel.getCbRole().getSelectedItem();
        String username = addPanel.getTfUsername().getText().trim();
        String password = addPanel.getTfPassword().getText().trim();
        int year = Integer.parseInt(addPanel.getTfYear().getText().trim());

        boolean success = EmployeeService.insertEmployee(name, birthDate, address, gender, phone, cccd, username, password, salary, year, role);

        if (success) {
            JOptionPane.showMessageDialog(view, "Thêm nhân viên thành công!");
            // Optionally reset form or chuyển về danh sách
        } else {
            JOptionPane.showMessageDialog(view, "Thêm thất bại! Kiểm tra lại thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
		
	}

	public void updateValue() {
    	AdminEmployeeEdit editPanel = view.getAdminPanel().getAdminEmployeeEdit();
        int id = Integer.parseInt(editPanel.getLblId().getText().replace("Mã số: ", ""));

        String name = editPanel.getTfName().getText().trim();
        String phone = editPanel.getTfPhone().getText().trim();
        java.util.Date birthUtilDate = editPanel.getDateChooser().getDate();
        java.sql.Date birthDate = new java.sql.Date(birthUtilDate.getTime());
        int gender = editPanel.getCbGender().getSelectedItem().equals("Nam") ? 1 : 0;
        String address = editPanel.getTfAddress().getText().trim();
        String cccd = editPanel.getTfCCCD().getText().trim();
        double salary = Double.parseDouble(editPanel.getTfSalary().getText().trim());
        String role = (String) editPanel.getCbRole().getSelectedItem();
        String username = editPanel.getTfUsername().getText().trim();
        String password = editPanel.getTfPassword().getText().trim();

        // Tạo object
        
        // Gọi Service cập nhật
        boolean success = EmployeeService.updateEmployee(id, name, birthDate, address, gender, phone, cccd, username, password, salary, role);
        if (success) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
		
	}

	public void switchEdit() throws ParseException{
    	view.getAdminPanel().getAdminEmployeeInfo().getEmployeeId();
    	view.getAdminPanel().getAdminEmployeeEdit().loadData(view.getAdminPanel().getAdminEmployeeInfo().getEmployeeId());
        view.getAdminPanel().getCardLayout().show(view.getAdminPanel().getCenterPanel(),"adminEmployeeEdit");
    }
    public void switchAdd(){
    	
        view.getAdminPanel().getCardLayout().show(view.getAdminPanel().getCenterPanel(),"adminEmployeeAdd");
    }
}
