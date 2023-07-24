package com.puneeth.project;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.LinkedList;

public class PatientRecordManager {

	LinkedList<Patient> patientList;
	
	
	public PatientRecordManager() {
		patientList = new LinkedList<>();
	}
	
	public void addPatient(int id , String name, int age, String gender) {
		Patient patient = new Patient(id, name, age, gender);
		
		patientList.add(patient);
		
	}

    public void displayDataInTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        //System.out.println(patientList);

        for (Patient patient : patientList) {
            Object[] rowData = {patient.getName(), patient.getId(), patient.getAge(), patient.getGender()};
            model.addRow(rowData);
        }
       
        
    }
    public LinkedList<Patient> getPatientList() {
    	//System.out.println(patientList);
        return patientList;
    }
   
}
