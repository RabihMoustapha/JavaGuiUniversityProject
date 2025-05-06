package Models;
import java.util.*;

public class Contact {
	private String nom, prenom, ville;
	private List<PhoneNumber> telephoneNumbers;
	
	public Contact(String nom, String prenom, String ville) {
		this.nom = nom != null ? nom : "TOTO";
		this.prenom = prenom != null ? prenom : "TOTO";
		this.ville = ville != null ? ville : "Beirut";
		this.telephoneNumbers = new ArrayList<>();
	}
	
    // Add phone number to list
    public void addPhoneNumber(int regionCode, int number) {
        this.telephoneNumbers.add(new PhoneNumber(regionCode, number));
    }
    
    // Getters
    public String getNom() {
    	return this.nom;
    }
    
    public String getPrenom() {
    	return this.prenom;
    }
    
    public String getVille() {
    	return this.ville;
    }
    
    // Setters
    public void setNom(String nom) {
    	this.nom = nom;
    }
    
    public void setPrenom(String prenom) {
    	this.prenom = prenom;
    }
    
    public void setVille(String ville) {
    	this.ville = ville;
    }
    
    @Override
    public String toString() {
    	return this.nom + " " + this.prenom + ": " + this.ville;
    }
}
