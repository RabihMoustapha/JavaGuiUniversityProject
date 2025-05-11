package Models;
import java.util.*;

public class Groupe {
	private String nom, description;
	private Set<Contact> contacts;

	public Groupe(String nom, String description) {
		this.nom = nom;
		this.description = description;
		this.contacts = new HashSet<>();
	}
	
	public void ajouterContact(Contact contact) {
		if(!contacts.contains(contact)) {
			contacts.add(contact);
		}
	}
	
	public void retirerContact(Contact contact) {
		contacts.remove(contact);
	}
	
    public String getNom() {
        return nom;
    }
    
    public String getDescription() {
        return description;
    }
    

    public Set<Contact> getContacts() {
        return new HashSet<>(contacts);
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
   
    public void setDescription(String description) {
        this.description = description;
    }

    public int getNombreContacts() {
        return contacts.size();
    }

    @Override
    public String toString() {
        return "Groupe [nom=" + nom + ", description=" + description 
             + ", nombreContacts=" + getNombreContacts() + "]";
    }
}