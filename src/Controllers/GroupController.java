package Controllers;

import Helpers.GroupsHelper;
import Models.Contact;
import Models.Groupe;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class GroupController {
    private DefaultListModel<Groupe> listModel;
    private JList<Groupe> GroupeList;

    public GroupController(DefaultListModel<Groupe> listModel, JList<Groupe> GroupeList) {
        this.listModel = listModel;
        this.GroupeList = GroupeList;
    }

    public ActionListener getSortByNameListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Groupe> GroupeList = new ArrayList<>();
                for (int i = 0; i < listModel.getSize(); i++) {
                    GroupeList.add(listModel.getElementAt(i));
                }
                GroupeList.sort(Comparator.comparing(Groupe::getNom));
                listModel.clear();
                for (Groupe Groupe : GroupeList) {
                    listModel.addElement(Groupe);
                }
            }
        };
    }

    public ActionListener getSortBySizeListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Groupe> groupes = new ArrayList<>();
                for (int i = 0; i < listModel.getSize(); i++) {
                    groupes.add(listModel.getElementAt(i));
                }

                Collections.sort(groupes, new Comparator<Groupe>() {
                    @Override
                    public int compare(Groupe g1, Groupe g2) {
                        return g2.getNombreContacts() - g1.getNombreContacts();
                    }
                });
                listModel.clear();
                for (Groupe g : groupes) {
                    listModel.addElement(g);
                }
            }
        };
    }

    private void updateListModel(ArrayList<Groupe> Groupes) {
        listModel.clear();
        Groupes.forEach(listModel::addElement);
    }

    public ActionListener getViewListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = GroupeList.getSelectedIndex();
                if (index >= 0) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Détails:\n" + listModel.getElementAt(index),
                            "Fiche Groupe",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    showSelectionWarning();
                }
            }
        };
    }

    public ActionListener getUpdateListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = GroupeList.getSelectedIndex();
                if (index >= 0) {
                    Groupe selected = listModel.getElementAt(index);

                    String newNom = JOptionPane.showInputDialog(null, "Nouveau prénom :", selected.getNom());
                    String newVille = JOptionPane.showInputDialog(null, "Nouvelle ville :", selected.getVille());

                    if (newPrenom != null && newNom != null && newVille != null
                            && !newPrenom.trim().isEmpty()
                            && !newNom.trim().isEmpty()
                            && !newVille.trim().isEmpty()) {

                        Groupe updated = new Groupe(newPrenom.trim(), newNom.trim(), newVille.trim());
                        listModel.set(index, updated);
                    }
                } else {
                    showSelectionWarning();
                }
            }
        };
    }

    public ActionListener getAddGroupeListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame GroupeFrame = new JFrame("Gestion des Groupes - Nouveau Groupe");
                GroupeFrame.setSize(500, 450);
                GroupeFrame.setLocationRelativeTo(null);
                GroupeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
                mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
                JLabel titleLabel = new JLabel("Ajouter un nouveau Groupe");
                titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
                titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                mainPanel.add(titleLabel);
                mainPanel.add(Box.createVerticalStrut(10));
                JPanel namePanel = new JPanel(new GridLayout(2, 2, 10, 5));
                namePanel.setBorder(BorderFactory.createTitledBorder("Nom du Groupe"));
                JTextField firstNameField = new JTextField();
                JTextField lastNameField = new JTextField();
                namePanel.add(new JLabel("Prénom :"));
                namePanel.add(firstNameField);
                namePanel.add(new JLabel("Nom :"));
                namePanel.add(lastNameField);
                mainPanel.add(namePanel);
                mainPanel.add(Box.createVerticalStrut(10));
                JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 5));
                infoPanel.setBorder(BorderFactory.createTitledBorder("Informations de Groupe"));
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
                            Groupe c = new Groupe(firstName, lastName, city);
                            c.addPhoneNumber(regionCode, phoneNumber);
                            List<Groupe> Groupes = new ArrayList<>();
                            File GroupesData = new File("Groupes.dat");
                            if (GroupesData.exists() && GroupesData.length() > 0) {
                                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GroupesData))) {
                                    Groupes = (List<Groupe>) ois.readObject();
                                } catch (EOFException eof) {
                                    JOptionPane.showMessageDialog(GroupeFrame, "Le fichier est vide.");
                                } catch (Exception readEx) {
                                    readEx.printStackTrace();
                                    JOptionPane.showMessageDialog(GroupeFrame, "Erreur de lecture du fichier.");
                                    return;
                                }
                            }
                            Groupes.add(c);
                            try (ObjectOutputStream oos = new ObjectOutputStream(
                                    new FileOutputStream(GroupesData))) {
                                oos.writeObject(Groupes);
                            }
                            JOptionPane.showMessageDialog(GroupeFrame, "Groupe enregistré avec succès !");
                            GroupeFrame.dispose();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(GroupeFrame,
                                    "Le code région et le numéro doivent être des entiers.");
                        } catch (IllegalArgumentException ex) {
                            JOptionPane.showMessageDialog(GroupeFrame, ex.getMessage());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(GroupeFrame, "Erreur lors de l'enregistrement.");
                        }
                    }
                });
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        GroupeFrame.dispose();
                    }
                });
                buttonPanel.add(saveButton);
                buttonPanel.add(cancelButton);
                mainPanel.add(Box.createVerticalStrut(10));
                mainPanel.add(buttonPanel);
                GroupeFrame.setContentPane(mainPanel);
                GroupeFrame.setVisible(true);
            }
        };
    }

    public ActionListener getDeleteListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = GroupeList.getSelectedIndex();
                if (index >= 0) {
                    int confirm = JOptionPane.showConfirmDialog(
                            null,
                            "Supprimer ce Groupe ?",
                            "Confirmation",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        listModel.remove(index);
                    }
                } else {
                    showSelectionWarning();
                }
            }
        };
    }

    private void showSelectionWarning() {
        JOptionPane.showMessageDialog(
                null,
                "Veuillez sélectionner un Groupe",
                "Aucune sélection",
                JOptionPane.WARNING_MESSAGE);
    }

    public ActionListener getAddGroupListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
                JTextField nameField = new JTextField();
                namePanel.add(new JLabel("Nom :"));
                namePanel.add(nameField);
                mainPanel.add(namePanel);
                mainPanel.add(Box.createVerticalStrut(10));
                JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 5));
                infoPanel.setBorder(BorderFactory.createTitledBorder("Informations de contact"));
                JTextField descriptionField = new JTextField();
                infoPanel.add(new JLabel("Description :"));
                infoPanel.add(descriptionField);
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
                            String Name = nameField.getText().trim();
                            String Description = descriptionField.getText().trim();
                            if (Name.isEmpty() || Description.isEmpty()) {
                                throw new IllegalArgumentException("Tous les champs doivent être remplis.");
                            }
                            Groupe c = new Groupe(Name, Description);
                            List<Groupe> contacts = new ArrayList<>();
                            File ContactsData = new File("Contacts.dat");
                            if (ContactsData.exists() && ContactsData.length() > 0) {
                                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ContactsData))) {
                                    contacts = (List<Groupe>) ois.readObject();
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
        };
    }

    // public ActionListener getEditListener() {
    // return new ActionListener() {
    // public void actionPerformed(ActionEvent e) {
    //
    // }
    // }
    // }
}