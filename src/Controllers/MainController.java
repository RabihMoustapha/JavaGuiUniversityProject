package Controllers;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import Helpers.ContactsHelper;
import Models.Contact;
import Models.Groupe;
import Views.MainFrame;

public class MainController {
	private MainFrame view;

	public MainController(MainFrame view) {
		this.view = view;
		initController();
	}

	private void initController() {
		view.getContactsButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openContactsWindow();
			}
		});

		view.getGroupsButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openGroupsWindow();
			}
		});
	}

	private void openContactsWindow() {
		JFrame contextFrame = new JFrame("Liste des Contacts");
		contextFrame.setSize(900, 550);
		contextFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contextFrame.setLocationRelativeTo(null);
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		mainPanel.setBackground(Color.WHITE);
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
		leftPanel.setBackground(new Color(245, 245, 245));
		JLabel contextLabel = new JLabel("Tri des contacts");
		contextLabel.setFont(new Font("Arial", Font.BOLD, 14));
		contextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton sortByFirstName = new JButton("Trier par prénom");
		JButton sortByLastName = new JButton("Trier par nom");
		JButton sortByCity = new JButton("Trier par ville");
		JButton addContactBtn = new JButton("Ajouter un contact");
		leftPanel.add(contextLabel);
		leftPanel.add(Box.createVerticalStrut(10));
		leftPanel.add(sortByFirstName);
		leftPanel.add(Box.createVerticalStrut(5));
		leftPanel.add(sortByLastName);
		leftPanel.add(Box.createVerticalStrut(5));
		leftPanel.add(sortByCity);
		leftPanel.add(Box.createVerticalStrut(20));
		leftPanel.add(addContactBtn);
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout(10, 10));
		centerPanel.setBackground(Color.WHITE);
		JLabel titleLabel = new JLabel("Gestion des contacts", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
		centerPanel.add(titleLabel, BorderLayout.NORTH);
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		searchPanel.setBackground(Color.WHITE);
		JLabel searchLabel = new JLabel("Rechercher : ");
		JTextField searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(120, 25));
		searchPanel.add(searchLabel);
		searchPanel.add(searchField);
		centerPanel.add(searchPanel, BorderLayout.CENTER);
		DefaultListModel<Contact> listModel = new DefaultListModel<>();
		ContactsHelper helper = new ContactsHelper();
		List<Contact> contacts = new ArrayList<>();

		try {
			contacts = helper.readData();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur de chargement des contacts.");
		}

		for (Contact c : contacts) {
			listModel.addElement(c);
		}

		JList<Contact> contactList = new JList<>(listModel);
		contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contactList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		JScrollPane scrollPane = new JScrollPane(contactList);
		scrollPane.setPreferredSize(new Dimension(300, 300));
		centerPanel.add(scrollPane, BorderLayout.SOUTH);
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton viewBtn = new JButton("Voir");
		JButton updateBtn = new JButton("Modifier");
		JButton deleteBtn = new JButton("Supprimer");
		bottomPanel.add(viewBtn);
		bottomPanel.add(updateBtn);
		bottomPanel.add(deleteBtn);
		mainPanel.add(leftPanel, BorderLayout.WEST);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		contextFrame.setContentPane(mainPanel);
		contextFrame.setVisible(true);
		ContactController contactCtrl = new ContactController(listModel, contactList);
		sortByFirstName.addActionListener(contactCtrl.getSortByFirstNameListener());
		sortByLastName.addActionListener(contactCtrl.getSortByLastNameListener());
		sortByCity.addActionListener(contactCtrl.getSortByCityListener());
		addContactBtn.addActionListener(contactCtrl.getAddContactListener());
		viewBtn.addActionListener(contactCtrl.getViewListener());
		updateBtn.addActionListener(contactCtrl.getUpdateListener());
		deleteBtn.addActionListener(contactCtrl.getDeleteListener());
	}

	private void openGroupsWindow() {
		JFrame groupsFrame = new JFrame("Gestion des groupes");
		groupsFrame.setSize(900, 550);
		groupsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		groupsFrame.setLocationRelativeTo(null);
		JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		mainPanel.setBackground(Color.WHITE);
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
		leftPanel.setBackground(new Color(245, 245, 245));
		JLabel groupActionLabel = new JLabel("Tri des groupes");
		groupActionLabel.setFont(new Font("Arial", Font.BOLD, 14));
		groupActionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton sortByNameButton = new JButton("Trier par nom");
		JButton sortBySizeButton = new JButton("Trier par taille");
		JButton addGroupButton = new JButton("Ajouter un groupe");
		leftPanel.add(groupActionLabel);
		leftPanel.add(Box.createVerticalStrut(10));
		leftPanel.add(sortByNameButton);
		leftPanel.add(Box.createVerticalStrut(5));
		leftPanel.add(sortBySizeButton);
		leftPanel.add(Box.createVerticalStrut(20));
		leftPanel.add(addGroupButton);
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout(10, 10));
		centerPanel.setBackground(Color.WHITE);
		JLabel titleLabel = new JLabel("Gestion des groupes", SwingConstants.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
		centerPanel.add(titleLabel, BorderLayout.NORTH);
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		searchPanel.setBackground(Color.WHITE);
		JLabel searchLabel = new JLabel("Rechercher : ");
		JTextField searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(120, 25));
		searchPanel.add(searchLabel);
		searchPanel.add(searchField);
		centerPanel.add(searchPanel, BorderLayout.CENTER);
		DefaultListModel<Groupe> listModel = new DefaultListModel<>();
		listModel.addElement(new Groupe("Famille", "5"));
		listModel.addElement(new Groupe("Amis", "10"));
		listModel.addElement(new Groupe("Collègues", "4"));
		listModel.addElement(new Groupe("Université", "14"));
		listModel.addElement(new Groupe("École", "3"));
		JList<Groupe> groupList = new JList<>(listModel);
		groupList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		groupList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		JScrollPane scrollPane = new JScrollPane(groupList);
		scrollPane.setPreferredSize(new Dimension(300, 300));
		centerPanel.add(scrollPane, BorderLayout.SOUTH);
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton viewButton = new JButton("Voir");
		JButton editButton = new JButton("Modifier");
		JButton deleteButton = new JButton("Supprimer");
		bottomPanel.add(viewButton);
		bottomPanel.add(editButton);
		bottomPanel.add(deleteButton);
		mainPanel.add(leftPanel, BorderLayout.WEST);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		groupsFrame.setContentPane(mainPanel);
		groupsFrame.setVisible(true);
		GroupController groupCtrl = new GroupController(listModel, groupList);
		sortByNameButton.addActionListener(groupCtrl.getSortByNameListener());
		sortBySizeButton.addActionListener(groupCtrl.getSortBySizeListener());
		addGroupButton.addActionListener(groupCtrl.getAddGroupListener());
		viewButton.addActionListener(groupCtrl.getViewListener());
		// editButton.addActionListener(groupCtrl.getEditListener());
		deleteButton.addActionListener(groupCtrl.getDeleteListener());
	}
}