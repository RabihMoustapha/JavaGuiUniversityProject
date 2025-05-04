package Controllers;

import Views.MainFrame;
import Models.Contact;
import Models.PhoneNumber;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class MainController extends JFrame {
	private MainFrame view;

	public MainController(MainFrame view) {
		this.view = view;
		initController();
	}

	private void initController() {
		view.getContactsButton().addActionListener(e -> openContactsWindow());
		view.getGroupsButton().addActionListener(e -> openGroupsWindow());
	}

	private void openContactsWindow() {
		JFrame contactsFrame = new JFrame("Liste des Contacts");
		contactsFrame.setSize(600, 400);
		contactsFrame.setLocationRelativeTo(null);
		contactsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel(new BorderLayout());
		String[] colsName = { "Nom", "Prénom", "Téléphone", "Ville" };
		DefaultTableModel model = new DefaultTableModel(colsName, 0);
		File d = new File("ContactData.dat");
		if (d.exists()) {
			try {
				FileInputStream fis = new FileInputStream(d);
				DataInputStream dis = new DataInputStream(fis);
				while(dis.available() > 0) {
					model.addRow(new Object[]{dis.readUTF(), dis.readUTF(), dis.readUTF(), dis.readUTF()});
				}
			} catch (IOException ioe) {
				JOptionPane.showMessageDialog(null, "Error");
			}
		} else {

		}
		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);

		panel.add(scrollPane, BorderLayout.CENTER);

		contactsFrame.setContentPane(panel);
		contactsFrame.setVisible(true);
	}

	private void openGroupsWindow() {

	}
}
