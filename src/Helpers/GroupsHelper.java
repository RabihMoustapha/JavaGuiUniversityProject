package Helpers;

import java.io.*;
import java.util.*;
import javax.swing.*;
import Models.Groupe;

public class GroupsHelper {
	public List<Groupe> loadGroupsFromFile() throws ClassNotFoundException {
	    List<Groupe> groupes = new ArrayList<>();
	    File file = new File("Groups.dat");
	    if (file.exists()) {
	        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
	            groupes = (List<Groupe>) ois.readObject();
	            ois.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	    return groupes;
	}
    
    public void saveGroupsToFile(DefaultListModel<Groupe> listModel) {
        List<Groupe> groupes = new ArrayList<>();
        File groupsData = new File("Groups.dat");
        for (int i = 0; i < listModel.getSize(); i++) {
            groupes.add(listModel.getElementAt(i));
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(groupsData))) {
            oos.writeObject(groupes);
            oos.writeObject("\n");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                "Erreur lors de la sauvegarde des groupes", 
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void removeFromList(DefaultListModel<Groupe> listModel, int index) {
        if (index >= 0 && index < listModel.getSize()) {
            listModel.remove(index);
        }
    }
}
