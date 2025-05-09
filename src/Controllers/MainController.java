package Controllers;

import Views.MainFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class MainController extends JFrame {
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

        // ---- PANEL GAUCHE ----
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

        // ---- PANEL CENTRAL ----
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Gestion des contacts", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        centerPanel.add(titleLabel, BorderLayout.NORTH);

        // Barre de recherche
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5)); // changé pour FlowLayout pour plus de
                                                                                // contrôle
        searchPanel.setBackground(Color.WHITE);
        JLabel searchLabel = new JLabel("Rechercher : ");
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(120, 25)); // Réduit la largeur ici
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        centerPanel.add(searchPanel, BorderLayout.CENTER);

        // Liste des contacts
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> contactList = new JList<>(listModel);
        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(contactList);
        scrollPane.setPreferredSize(new Dimension(300, 300));
        centerPanel.add(scrollPane, BorderLayout.SOUTH);

        // ---- PANEL BAS ----
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton viewBtn = new JButton("Voir");
        JButton updateBtn = new JButton("Modifier");
        JButton deleteBtn = new JButton("Supprimer");
        bottomPanel.add(viewBtn);
        bottomPanel.add(updateBtn);
        bottomPanel.add(deleteBtn);

        // ---- AJOUT DES PANELS ----
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        contextFrame.setContentPane(mainPanel);
        contextFrame.setVisible(true);

        // ---- CONTROLLERS ----
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
        JFrame GroupManagerWindow = new JFrame();
        GroupManagerWindow.setTitle("Projet NFA035");
        GroupManagerWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GroupManagerWindow.setSize(600, 400);
        GroupManagerWindow.setLocationRelativeTo(null);
        GroupManagerWindow.setLayout(new BorderLayout());

        // ----- Titre -----
        JLabel titleLabel = new JLabel("Gestion des contacts", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLUE);
        add(titleLabel, BorderLayout.NORTH);

        // ----- Panel principal -----
        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        // ----- Panel gauche (Groupes) -----
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);
        JLabel groupLabel = new JLabel("Groups", JLabel.CENTER);
        groupLabel.setForeground(Color.RED);
        groupLabel.setFont(new Font("Arial", Font.BOLD, 16));
        leftPanel.add(groupLabel, BorderLayout.NORTH);

        JButton addGroupButton = new JButton("Add new Group");
        leftPanel.add(addGroupButton, BorderLayout.SOUTH);

        mainPanel.add(leftPanel, BorderLayout.WEST);

        // ----- Espace central (vide/bleu) -----
        JPanel centerSpacer = new JPanel();
        centerSpacer.setPreferredSize(new Dimension(100, 0));
        centerSpacer.setBackground(Color.CYAN); // pour illustration
        mainPanel.add(centerSpacer, BorderLayout.CENTER);

        // ----- Panel droit -----
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Liste des groupes
        String[] groupColumns = { "Group name", "Nb. of contacts" };
        Object[][] groupData = {
                { "Family", 5 },
                { "Friends", 10 },
                { "Co-Workers", 4 },
                { "University", 14 },
                { "School", 3 },
                { "High School", 10 },
                { "Temporary", 5 }
        };
        JTable groupTable = new JTable(groupData, groupColumns);
        JScrollPane groupScroll = new JScrollPane(groupTable);
        rightPanel.add(groupScroll, BorderLayout.NORTH);

        // Table des contacts (vide ici)
        String[] contactColumns = { "Contact Name", "Contact City" };
        JTable contactTable = new JTable(new DefaultTableModel(contactColumns, 0));
        JScrollPane contactScroll = new JScrollPane(contactTable);
        rightPanel.add(contactScroll, BorderLayout.CENTER);

        // Boutons bas
        JPanel bottomButtons = new JPanel();
        JButton updateButton = new JButton("Update Group");
        JButton deleteButton = new JButton("Delete");
        bottomButtons.add(updateButton);
        bottomButtons.add(deleteButton);
        rightPanel.add(bottomButtons, BorderLayout.SOUTH);

        mainPanel.add(rightPanel, BorderLayout.EAST);
    }
}