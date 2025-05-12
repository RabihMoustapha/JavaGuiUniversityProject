package Models;
import java.util.*;
import java.io.*;

public class Contact implements Serializable{
	private static final long serialVersionUID = 1L;
	int id, nextID = 1;
	private String nom, prenom, ville;
	private List<PhoneNumber> telephoneNumbers;
	
	public Contact(int id, String nom, String prenom, String ville) {
		this.id = nextID;
		this.nom = nom;
		this.prenom = prenom;
		this.ville = ville;
		this.telephoneNumbers = new ArrayList<>();
		nextID++;
	}
	
    public void addPhoneNumber(int regionCode, int number) {
        this.telephoneNumbers.add(new PhoneNumber(regionCode, number));
    }
    
    public String getNom() {
    	return this.nom;
    }
    
    public String getPrenom() {
    	return this.prenom;
    }
    
    public String getVille() {
    	return this.ville;
    }
    
    public void setNom(String nom) {
    	this.nom = nom;
    }
    
    public void setPrenom(String prenom) {
    	this.prenom = prenom;
    }
    
    public void setVille(String ville) {
    	this.ville = ville;
    }
    
    public String toString() {
    	return this.nom + " " + this.prenom + ": " + this.ville;
    }
}
