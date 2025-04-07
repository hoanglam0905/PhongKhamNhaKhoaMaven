package controller;

import view.patient_View;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class patient_controller implements DocumentListener {
    private patient_View myApp;

    public patient_controller(patient_View myApp) {
        this.myApp = myApp;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        myApp.updateTable(myApp.getInputText());
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        myApp.updateTable(myApp.getInputText());
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        myApp.updateTable(myApp.getInputText());
    }
}
