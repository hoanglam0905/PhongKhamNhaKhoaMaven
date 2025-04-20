package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;

import Utils.JDBCUtil;
import controller.MainController;
import controller.PatientController;
import leTanView.MainFrame;
import view.PatientView;

import javax.swing.*;

public class main {
    public static void main(String[] args) {
    	System.out.print("kaka");
    }
}




































//// Thiết lập giao diện UI trong luồng sự kiện
//SwingUtilities.invokeLater(() -> {
//  JFrame frame = new JFrame("Quản lý bệnh nhân");
//  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//  frame.setSize(800, 600);
//
//  // Tạo view và controller
//  PatientView patientView = new PatientView();
//  new PatientController(patientView); // Controller gắn logic vào view
//
//  // Đưa view vào frame
//  frame.setContentPane(patientView);
//  frame.setLocationRelativeTo(null); // Center window
//  frame.setVisible(true);
//});