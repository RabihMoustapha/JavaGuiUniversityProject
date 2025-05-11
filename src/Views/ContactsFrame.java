package Views;

import javax.swing.*;
import java.awt.*;

public class ContactsFrame extends JFrame {

    private JButton addContactButton;
    private JButton backButton;
    private JTable contactsTable;

    public ContactsFrame() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Gestion des contacts - Contacts");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);
        JLabel titleLabel = new JLabel("Liste des contacts", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        contactsTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(contactsTable);
        add(tableScrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        addContactButton = createStyledButton("Ajouter un contact");
        backButton = createStyledButton("Retour");
        buttonPanel.add(addContactButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        button.setFocusPainted(false);
        button.setBackground(new Color(66, 133, 244));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(48, 99, 189)),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        return button;
    }

    public JButton getAddContactButton() {
        return addContactButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JTable getContactsTable() {
        return contactsTable;
    }
}
