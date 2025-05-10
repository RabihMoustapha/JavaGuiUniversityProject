package Controllers;

import Models.Contact;
import Helpers.ContactsHelper;
import Models.Contact;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class ContactController {
    private DefaultListModel<Contact> listModel;
    private JList<Contact> contactList;

    public ContactController(DefaultListModel<Contact> listModel, JList<Contact> contactList) {
        this.listModel = listModel;
        this.contactList = contactList;
    }

    public ActionListener getSortByFirstNameListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Contact> contactList = new ArrayList<>();
                for (int i = 0; i < listModel.getSize(); i++) {
                    contactList.add(listModel.getElementAt(i));
                }
                contactList.sort(Comparator.comparing(Contact::getPrenom));
                listModel.clear();
                for (Contact contact : contactList) {
                    listModel.addElement(contact);
                }
            }
        };
    }

    public ActionListener getSortByLastNameListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Contact> contactList = new ArrayList<>();
                for (int i = 0; i < listModel.getSize(); i++) {
                    contactList.add(listModel.getElementAt(i));
                }
                contactList.sort(Comparator.comparing(Contact::getNom));
                listModel.clear();
                for (Contact contact : contactList) {
                    listModel.addElement(contact);
                }
            }
        };
    }

    public ActionListener getSortByCityListener() {
        return e -> {
            ArrayList<Contact> contacts = new ArrayList<>();
            for (int i = 0; i < listModel.getSize(); i++) {
                contacts.add(listModel.getElementAt(i));
            }
            contacts.sort(Comparator.comparing(Contact::getVille));
            listModel.clear();
            for (Contact c : contacts) {
                listModel.addElement(c);
            }
        };
    }

    private void updateListModel(ArrayList<Contact> contacts) {
        listModel.clear();
        contacts.forEach(listModel::addElement);
    }

    public ActionListener getViewListener() {
        return e -> {
            int index = contactList.getSelectedIndex();
            if (index >= 0) {
                JOptionPane.showMessageDialog(
                        null,
                        "Détails:\n" + listModel.getElementAt(index),
                        "Fiche contact",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                showSelectionWarning();
            }
        };
    }

    public ActionListener getUpdateListener() {
        return e -> {
            int index = contactList.getSelectedIndex();
            if (index >= 0) {
                Contact selected = listModel.getElementAt(index);

                String newPrenom = JOptionPane.showInputDialog(null, "Nouveau prénom :", selected.getPrenom());
                String newNom = JOptionPane.showInputDialog(null, "Nouveau nom :", selected.getNom());
                String newVille = JOptionPane.showInputDialog(null, "Nouvelle ville :", selected.getVille());

                if (newPrenom != null && newNom != null && newVille != null
                        && !newPrenom.trim().isEmpty()
                        && !newNom.trim().isEmpty()
                        && !newVille.trim().isEmpty()) {

                    Contact updated = new Contact(newPrenom.trim(), newNom.trim(), newVille.trim());
                    listModel.set(index, updated);
                }
            } else {
                showSelectionWarning();
            }
        };
    }

    public ActionListener getAddContactListener() {
        return e -> {
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
        };
    }

    public ActionListener getDeleteListener() {
        return e -> {
            int index = contactList.getSelectedIndex();
            if (index >= 0) {
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Supprimer ce contact ?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    listModel.remove(index);
                }
            } else {
                showSelectionWarning();
            }
        };
    }

    private void showSelectionWarning() {
        JOptionPane.showMessageDialog(
                null,
                "Veuillez sélectionner un contact",
                "Aucune sélection",
                JOptionPane.WARNING_MESSAGE);
    }
}