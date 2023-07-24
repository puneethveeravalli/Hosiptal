package com.puneeth.project;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class PatientRecordUI<Jbutton> extends JFrame {
	private LinkedList<Patient> patientList;
	private PatientRecordManager recordManager;

	private JTextField idField;
	private JTextField nameField;
	private JTextField ageField;
	private JTextField genderField;
	private JTextArea displayArea;
	private JTable table;
	private JTextField searchField;
	private DefaultTableModel model;

	protected int id;

	public PatientRecordUI() {
		patientList = new LinkedList<>();
		recordManager = new PatientRecordManager();
		model = new DefaultTableModel();
		model.addColumn("Name");
		model.addColumn("Id");
		model.addColumn("Age");
		model.addColumn("Gender");
		table = new JTable(model);

		idField = new JTextField(10);
		nameField = new JTextField(20);
		ageField = new JTextField(3);
		genderField = new JTextField(10);
		displayArea = new JTextArea(10, 40);

		displayArea.setEditable(false);

		JButton admitButton = new JButton("Admit Patient");
		admitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(idField.getText().trim());
				String name = nameField.getText().trim();
				int age = Integer.parseInt(ageField.getText().trim());
				String gender = genderField.getText().trim();

				recordManager.addPatient(id, name, age, gender);
				Patient patient = new Patient(id, name, age, gender);
				patientList.add(patient);

				displayArea.append("Patient Admitted: " + patient.toString() + "\n");
				JOptionPane.showMessageDialog(null, "Patient Details Added SuccessFully", "Alert",
						JOptionPane.INFORMATION_MESSAGE);
				clearFields();
			}
		});

		JButton dischargeButton = new JButton("Discharge Patient");
		dischargeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// int id = Integer.parseInt(idField.getText().trim());

				String allotValue = JOptionPane.showInputDialog(null, "Enter the Patient ID To Discharge:", "Alert",
						JOptionPane.PLAIN_MESSAGE);
				int id = Integer.parseInt(allotValue);
				boolean removed = false;
				LinkedList<Patient> patientList = recordManager.getPatientList();

				for (Patient patient : patientList) {

					if (patient.getId() == id) {
						patientList.remove(patient);
						removed = true;

						displayArea.append("Patient Discharged: " + patient.toString() + "\n");
						break;
					}
				}
				if (!removed){
					JOptionPane.showMessageDialog(null, "Patient ID Not Found!!", "Information",
							JOptionPane.INFORMATION_MESSAGE);
					
	//displayArea.append("Patient with ID " + id + " not found.\n");
				}
				clearFields();
			}
		});

		
		 JScrollPane tableScrollPane = new JScrollPane(table);
		
        searchField = new JTextField(20);

		 searchField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				filterData();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filterData();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filterData();
			}});
		 
		/*JButton searchButton = new JButton("Search Patient");
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int id = Integer.parseInt(idField.getText().trim());
				boolean found = false;
				for (Patient patient : patientList) {
					if (patient.getId() == id) {
						displayArea.append("Patient Found: " + patient.toString() + "\n");
						found = true;
						break;
					}
				}
				if (!found) {
					displayArea.append("Patient with ID " + id + " not found.\n");
				}
				clearFields();
			}
		});*/

		// JScrollPane scrollPane = new JScrollPane(table);
		JButton displayButton = new JButton("View All Patients");
		displayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LinkedList<Patient> patientList = recordManager.getPatientList();
				// System.out.println(patientList);
				// recordManager.displayDataInTable(table);
				// Clear the existing table data
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}

				for (Patient patient : patientList) {
					Object[] rowData = { patient.getName(), patient.getId(), patient.getAge(), patient.getGender() };
					model.addRow(rowData);
				}
			}
		});

		JPanel inputPanel = new JPanel(new GridLayout(4, 2));
		inputPanel.add(new JLabel("ID:"));
		inputPanel.add(idField);
		inputPanel.add(new JLabel("Name:"));
		inputPanel.add(nameField);
		inputPanel.add(new JLabel("Age:"));
		inputPanel.add(ageField);
		inputPanel.add(new JLabel("Gender:"));
		inputPanel.add(genderField);
		
		 JPanel searchPanel = new JPanel();
	        searchPanel.add(new JLabel("Search:"));
	        searchPanel.add(searchField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(admitButton);
		buttonPanel.add(dischargeButton);
	//	buttonPanel.add(searchButton);
		buttonPanel.add(displayButton);

		JPanel displayPanel = new JPanel();
		displayPanel.add(new JScrollPane(displayArea));
		setLayout(new BorderLayout());

		add(inputPanel, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.CENTER);
		add(searchPanel, BorderLayout.AFTER_LINE_ENDS);
		add(displayPanel, BorderLayout.SOUTH);

		// add(displayPanel, BorderLayout.AFTER_LAST_LINE);
		add(new JScrollPane(table), BorderLayout.SOUTH);

		setTitle("Patient Record Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	private void clearFields() {
		idField.setText("");
		nameField.setText("");
		ageField.setText("");
		genderField.setText("");
	}
	  private void filterData() {
	        String searchQuery = searchField.getText().toLowerCase();
	        model.setRowCount(0);

	        for (Patient patient : patientList) {
	            if (patient.getName().toLowerCase().contains(searchQuery) ||
	                String.valueOf(patient.getId()).contains(searchQuery) ||
	                String.valueOf(patient.getAge()).contains(searchQuery) ||
	                patient.getGender().toLowerCase().contains(searchQuery)) {
	                Object[] rowData = {patient.getName(), patient.getId(), patient.getAge(), patient.getGender()};
	                model.addRow(rowData);
	            }
	        }
	    }

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new PatientRecordUI().setVisible(true);
			}
		});
	}
}