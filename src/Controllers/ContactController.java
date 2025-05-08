package Controllers;

import Models.Contact;
import java.awt.event.*;
import Models.PhoneNumber;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.Comparator;
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

            // Titre
            JLabel titleLabel = new JLabel("Ajouter un nouveau contact");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(titleLabel);
            mainPanel.add(Box.createVerticalStrut(10));

            // Nom et prénom
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

            // Informations : ville, code région, téléphone
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

            // Groupes
            JPanel groupPanel = new JPanel();
            groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));
            groupPanel.setBorder(BorderFactory.createTitledBorder("Groupes"));

            ArrayList<String> groups = new ArrayList<>();
            try {
            	File GroupsData = new File("Groups.dat");
                FileInputStream groupFile = new FileInputStream(GroupsData);
                DataInputStream d = new DataInputStream(groupFile);
                
                while (d.available() > 0) {
                    groups.add(d.readUTF());
                }
                d.close();
                JCheckBox[] groupBoxes = new JCheckBox[groups.size()];
                for (int i = 0; i < groups.size(); i++) {
                    groupBoxes[i] = new JCheckBox(groups.get(i));
                    groupPanel.add(groupBoxes[i]);
                }

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            mainPanel.add(groupPanel);

            // Boutons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton saveButton = new JButton("Enregistrer");
            JButton cancelButton = new JButton("Annuler");

            saveButton.addActionListener(new ActionListener(){
            	public void actionPerformed(ActionEvent evt) {
            	try{
            		File getContactFile = new File("Contacts.dat");
            		if(!getContactFile.exists()) getContactFile = new File("Contacts.dat");
            		FileOutputStream f = new FileOutputStream("Contacts.dat", true); 
            		DataOutputStream d = new DataOutputStream(f);
            		Contact c = new Contact(firstNameField.getText(), lastNameField.getText(), cityField.getText());
            		PhoneNumber p = new PhoneNumber(Integer.parseInt(regionCodeField.getText()), Integer.parseInt(phoneNumberField.getText()));
            		d.writeUTF(c.getNom()); d.writeUTF(c.getPrenom()); d.writeUTF(c.getVille()); d.writeInt(p.getRegionCode()); d.writeInt(p.getNumber());
            	}catch(IOException ioe) {
            		ioe.printStackTrace();
            		JOptionPane.showMessageDialog(null, "Error");
            	}
                JOptionPane.showMessageDialog(contactFrame, "Contact enregistré !");
                contactFrame.dispose();
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