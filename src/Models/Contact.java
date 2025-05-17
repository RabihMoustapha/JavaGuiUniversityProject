package Models;
import java.util.*;
import java.io.*;
import Observers.MyObserver;

public class Contact extends MyObserver{
	private String nom, prenom, ville;
	private List<PhoneNumber> telephoneNumbers;
	
	public Contact(String nom, String prenom, String ville) {
		this.nom = nom;
		this.prenom = prenom;
		this.ville = ville;
		this.telephoneNumbers = new ArrayList<>();
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
