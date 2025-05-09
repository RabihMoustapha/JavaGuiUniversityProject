package Controllers;

import Models.Contact;
import java.awt.event.*;
import Models.PhoneNumber;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ContactController {
    private DefaultListModel<String> listModel;
    private JList<String> contactList;

    public ContactController(DefaultListModel<String> listModel, JList<String> contactList) {
        this.listModel = listModel;
        this.contactList = contactList;
    }

    // Trie par prénom (premier mot avant l'espace)
    public ActionListener getSortByFirstNameListener() {
        return e -> {
            ArrayList<String> contacts = Collections.list(listModel.elements());
            contacts.sort(Comparator.comparing(s -> s.split(" ")[0]));
            updateListModel(contacts);
        };
    }

    // Trie par nom (deuxième mot avant le "-")
    public ActionListener getSortByLastNameListener() {
        return e -> {
            ArrayList<String> contacts = Collections.list(listModel.elements());
            contacts.sort(Comparator.comparing(s -> {
                String[] parts = s.split(" ");
                return parts.length > 1 ? parts[1] : "";
            }));
            updateListModel(contacts);
        };
    }

    // Trie par ville (partie après "-")
    public ActionListener getSortByCityListener() {
        return e -> {
            ArrayList<String> contacts = Collections.list(listModel.elements());
            contacts.sort(Comparator.comparing(s -> {
                String[] parts = s.split("-");
                return parts.length > 1 ? parts[1].trim() : "";
            }));
            updateListModel(contacts);
        };
    }

    private void updateListModel(ArrayList<String> contacts) {
        listModel.clear();
        contacts.forEach(listModel::addElement);
    }



    // Visualisation du contact sélectionné
    public ActionListener getViewListener() {
        return e -> {
            int index = contactList.getSelectedIndex();
            if (index >= 0) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Détails:\n" + listModel.getElementAt(index), 
                    "Fiche contact", 
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                showSelectionWarning();
            }
        };
    }

    // Modification du contact
    public ActionListener getUpdateListener() {
        return e -> {
            int index = contactList.getSelectedIndex();
            if (index >= 0) {
                String newValue = JOptionPane.showInputDialog(
                    null, 
                    "Modifier le contact", 
                    listModel.getElementAt(index)
                );
                if (newValue != null && !newValue.trim().isEmpty()) {
                    listModel.set(index, newValue.trim());
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

                    // Load existing contacts (if any)
                    List<Contact> contacts = new ArrayList<>();
                    File file = new File("Contacts.dat");
                    if (file.exists() && file.length() > 0) {
                        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                            contacts = (List<Contact>) ois.readObject();
                        } catch (EOFException eof) {
                            JOptionPane.showMessageDialog(cancelButton, "Le fichier est vide.");
                        } catch (Exception readEx) {
                            readEx.printStackTrace();
                            JOptionPane.showMessageDialog(contactFrame, "Erreur de lecture du fichier.");
                            return;
                        }
                    }
                    contacts.add(c); // Add new contact
                    
                    // Save full list
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Contacts.dat", true))) {
                        oos.writeObject(contacts);
                        oos.close(); //Close the stream
                    }

                    JOptionPane.showMessageDialog(contactFrame, "Contact enregistré avec succès !");
                    contactFrame.dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(contactFrame, "Le code région et le numéro doivent être des entiers.");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(contactFrame, ex.getMessage());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(contactFrame, "Erreur lors de l'enregistrement.");
                }
            	}
            });

            cancelButton.addActionListener(evt -> contactFrame.dispose());

            buttonPanel.add(saveButton);
            buttonPanel.add(cancelButton);
            mainPanel.add(Box.createVerticalStrut(10));
            mainPanel.add(buttonPanel);

            contactFrame.setContentPane(mainPanel);
            contactFrame.setVisible(true);
        };
    }

    // Suppression du contact
    public ActionListener getDeleteListener() {
        return e -> {
            int index = contactList.getSelectedIndex();
            if (index >= 0) {
                int confirm = JOptionPane.showConfirmDialog(
                    null, 
                    "Supprimer ce contact ?", 
                    "Confirmation", 
                    JOptionPane.YES_NO_OPTION
                );
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
            JOptionPane.WARNING_MESSAGE
        );
    }
}