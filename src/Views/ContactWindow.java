package Views;

import javax.swing.*;
import java.awt.*;

public class ContactWindow extends JFrame {

	private JButton btnSortByFirstName;
	private JButton btnSortByLastName;
	private JButton btnSortByCity;
	private JButton btnAddContact;
	private JButton btnUpdateContact;
	private JButton btnDeleteContact;
	private JButton btnViewContact;
	private JTextField searchField;
	private JList<String> contactList; // à remplacer plus tard par un modèle personnalisé

	public ContactWindow() {
		setTitle("Contacts");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Panel haut : recherche
		JPanel topPanel = new JPanel(new BorderLayout());
		searchField = new JTextField();
		topPanel.add(new JLabel("Search: "), BorderLayout.WEST);
		topPanel.add(searchField, BorderLayout.CENTER);
		add(topPanel, BorderLayout.NORTH);

		// Centre : liste des contacts
		contactList = new JList<>();
		add(new JScrollPane(contactList), BorderLayout.CENTER);

		// Bas : boutons d'action
		JPanel bottomPanel = new JPanel(new GridLayout(2, 4, 5, 5));
		btnSortByFirstName = new JButton("Sort by First Name");
		btnSortByLastName = new JButton("Sort by Last Name");
		btnSortByCity = new JButton("Sort by City");
		btnAddContact = new JButton("Add new Contact");
		btnUpdateContact = new JButton("Update Contact");
		btnDeleteContact = new JButton("Delete Contact");
		btnViewContact = new JButton("View Contact");

		bottomPanel.add(btnSortByFirstName);
		bottomPanel.add(btnSortByLastName);
		bottomPanel.add(btnSortByCity);
		bottomPanel.add(btnAddContact);
		bottomPanel.add(btnUpdateContact);
		bottomPanel.add(btnDeleteContact);
		bottomPanel.add(btnViewContact);

		add(bottomPanel, BorderLayout.SOUTH);
	}

	// Getters pour que le contrôleur accède aux composants
	public JButton getBtnSortByFirstName() {
		return btnSortByFirstName;
	}

	public JButton getBtnSortByLastName() {
		return btnSortByLastName;
	}

	public JButton getBtnSortByCity() {
		return btnSortByCity;
	}

	public JButton getBtnAddContact() {
		return btnAddContact;
	}

	public JButton getBtnUpdateContact() {
		return btnUpdateContact;
	}

	public JButton getBtnDeleteContact() {
		return btnDeleteContact;
	}

	public JButton getBtnViewContact() {
		return btnViewContact;
	}

	public JTextField getSearchField() {
		return searchField;
	}

	public JList<String> getContactList() {
		return contactList;
	}
}