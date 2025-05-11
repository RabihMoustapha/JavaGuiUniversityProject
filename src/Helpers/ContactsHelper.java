package Helpers;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Models.Contact;

public class ContactsHelper {
	public List<Contact> readData() throws ClassNotFoundException{
	    List<Contact> contacts = new ArrayList<>();
	    File ContactsData = new File("Contacts.dat");
	    if (ContactsData.exists() && ContactsData.length() > 0) {
	        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ContactsData))) {
	            contacts = (List<Contact>) ois.readObject();
	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Erreur de lecture du fichier de contacts.");
	        }
	    }
	    return contacts;
	}
}
