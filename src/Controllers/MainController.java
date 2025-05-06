package Controllers;

import Views.MainFrame;
import Views.ContactWindow;
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
        JFrame contextFrame = new JFrame("Liste des Contacts");
        contextFrame.setTitle("Projet NFA035");
        contextFrame.setSize(800, 500);
        contextFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contextFrame.setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel gauche
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel contextLabel = new JLabel("Contacts");
        contextLabel.setFont(new Font("Arial", Font.BOLD, 14));
        contextLabel.setForeground(Color.BLUE);
        contextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton sortByFirstName = new JButton("Sort by first name");
        JButton sortByLastName = new JButton("Sort by last name");
        JButton sortByCity = new JButton("Sort by City");
        JButton addContactBtn = new JButton("Add new contact");

        leftPanel.add(contextLabel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(sortByFirstName);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(sortByLastName);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(sortByCity);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(addContactBtn);

        // Panel central
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("Gestion des contacts", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        centerPanel.add(titleLabel, BorderLayout.NORTH);

        // Champ de recherche
        JPanel searchPanel = new JPanel(new BorderLayout());
        JLabel searchLabel = new JLabel("Search");
        JTextField searchField = new JTextField();
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        centerPanel.add(searchPanel, BorderLayout.CENTER);

        // Liste de contacts
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String contact : ) {
            listModel.addElement(contact);
        }

        JList<String> contactList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(contactList);
        centerPanel.add(scrollPane, BorderLayout.SOUTH);

        // Panel bas
        JPanel bottomPanel = new JPanel();
        JButton viewBtn = new JButton("View");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        bottomPanel.add(viewBtn);
        bottomPanel.add(updateBtn);
        bottomPanel.add(deleteBtn);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Initialisation du ContactController
        ContactController contactCtrl = new ContactController(listModel, contactList);
        
        // Attribution des listeners
        sortByFirstName.addActionListener(contactCtrl.getSortByFirstNameListener());
        sortByLastName.addActionListener(contactCtrl.getSortByLastNameListener());
        sortByCity.addActionListener(contactCtrl.getSortByCityListener());
        addContactBtn.addActionListener(contactCtrl.getAddContactListener());
        viewBtn.addActionListener(contactCtrl.getViewListener());
        updateBtn.addActionListener(contactCtrl.getUpdateListener());
        deleteBtn.addActionListener(contactCtrl.getDeleteListener());

        contextFrame.add(mainPanel);
        contextFrame.setVisible(true);
    }
    
    private void openGroupsWindow() {
        // ... votre code existant pour openComputation
    }
    
    private void openGroupSubshow() {
        // ... votre code existant pour openGroupSubshow
    }
}