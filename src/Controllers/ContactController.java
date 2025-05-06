package Controllers;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import java.util.Comparator;

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

    // Ajout d'un nouveau contact
    public ActionListener getAddContactListener() {
        return e -> {
            String input = JOptionPane.showInputDialog(
                null, 
                "Format: Prénom Nom - Ville", 
                "Nouveau contact", 
                JOptionPane.PLAIN_MESSAGE
            );
            
            if (input != null && !input.trim().isEmpty()) {
                if (!input.contains("-")) {
                    JOptionPane.showMessageDialog(
                        null, 
                        "Format invalide. Utilisez 'Prénom Nom - Ville'", 
                        "Erreur", 
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                listModel.addElement(input.trim());
            }
        };
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