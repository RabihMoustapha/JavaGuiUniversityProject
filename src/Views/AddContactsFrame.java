package Views;

import javax.swing.*;

import Models.Contact;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AddContactsFrame extends JFrame {

    private JButton addContactButton;
    private JButton backButton;
    private JTable contactsTable;

    public AddContactsFrame() {
        initializeUI();
    }

    private void initializeUI() {
    	JFrame contactFrame = new JFrame("Gestion des contacts - Nouveau Contact");
        contactFrame.setSize(500, 450);
        contactFrame.setLocationRelativeTo(null);
        contactFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JLabel titleLabel = new JLabel("Ajouter un nouveau contact");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        JPanel namePanel = new JPanel(new GridLayout(2, 2, 10, 5));
        namePanel.setBorder(BorderFactory.createTitledBorder("Nom du contact"));
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        namePanel.add(new JLabel("Prénom :"));
        namePanel.add(firstNameField);
        namePanel.add(new JLabel("Nom :"));
        namePanel.add(lastNameField);
        mainPanel.add(namePanel);
        mainPanel.add(Box.createVerticalStrut(10));
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Informations de contact"));
        JTextField cityField = new JTextField();
        JTextField regionCodeField = new JTextField();
        JTextField phoneNumberField = new JTextField();
        infoPanel.add(new JLabel("Ville :"));
        infoPanel.add(cityField);
        infoPanel.add(new JLabel("Code Région :"));
        infoPanel.add(regionCodeField);
        infoPanel.add(new JLabel("Téléphone :"));
        infoPanel.add(phoneNumberField);
        mainPanel.add(infoPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));
        groupPanel.setBorder(BorderFactory.createTitledBorder("Groupes"));
        ArrayList<String> groups = new ArrayList<>();
        JCheckBox[] groupBoxes;

        try (DataInputStream d = new DataInputStream(new FileInputStream("Groups.dat"))) {
            while (d.available() > 0) {
                groups.add(d.readUTF());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        groupBoxes = new JCheckBox[groups.size()];
        for (int i = 0; i < groups.size(); i++) {
            groupBoxes[i] = new JCheckBox(groups.get(i));
            groupPanel.add(groupBoxes[i]);
        }
        mainPanel.add(groupPanel);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String firstName = firstNameField.getText().trim();
                    String lastName = lastNameField.getText().trim();
                    String city = cityField.getText().trim();
                    int regionCode = Integer.parseInt(regionCodeField.getText().trim());
                    int phoneNumber = Integer.parseInt(phoneNumberField.getText().trim());
                    if (firstName.isEmpty() || lastName.isEmpty() || city.isEmpty()) {
                        throw new IllegalArgumentException("Tous les champs doivent être remplis.");
                    }
                    Contact c = new Contact(firstName, lastName, city);
                    c.addPhoneNumber(regionCode, phoneNumber);
                    List<Contact> contacts = new ArrayList<>();
                    File ContactsData = new File("Contacts.dat");
                    if (ContactsData.exists() && ContactsData.length() > 0) {
                        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ContactsData))) {
                            contacts = (List<Contact>) ois.readObject();
                        } catch (EOFException eof) {
                            JOptionPane.showMessageDialog(contactFrame, "Le fichier est vide.");
                        } catch (Exception readEx) {
                            readEx.printStackTrace();
                            JOptionPane.showMessageDialog(contactFrame, "Erreur de lecture du fichier.");
                            return;
                        }
                    }
                    contacts.add(c);
                    try (ObjectOutputStream oos = new ObjectOutputStream(
                            new FileOutputStream(ContactsData))) {
                        oos.writeObject(contacts);
                    }
                    JOptionPane.showMessageDialog(contactFrame, "Contact enregistré avec succès !");
                    contactFrame.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(contactFrame,
                            "Le code région et le numéro doivent être des entiers.");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(contactFrame, ex.getMessage());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(contactFrame, "Erreur lors de l'enregistrement.");
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contactFrame.dispose();
            }
        });
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);
        contactFrame.setContentPane(mainPanel);
        contactFrame.setVisible(true);
    }
}