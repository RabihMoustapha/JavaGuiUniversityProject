package Helpers;

import java.io.*;
import java.util.*;
import javax.swing.*;
import Models.Contact;

public class ContactsHelper {
	public Set<Contact> readData(){
		Set<Contact> contacts = new HashSet<>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Contacts.dat"))) {
			contacts = (Set<Contact>) ois.readObject();
		} catch (IOException | ClassNotFoundException ioe) {
			ioe.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erreur de lecture du fichier de contacts.");
		}
		return contacts;
	}
}