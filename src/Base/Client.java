package Base;

import java.io.*;
import Utils.DateUser;

public class Client implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nom;
	private String prenom;
	private String codeCli;
	private String depart;
	private int nbCde = 0;
	private float ca = 0f;
	private DateUser dateCrea;
	
	public Client(String nom, String prenom, String codeCli, String depart) {
		this.nom = nom; this.prenom = prenom; this.codeCli = codeCli; this.depart = depart; this.dateCrea = new DateUser();
	}

	public String toString() {
		String st = nom + " " + prenom + "   CODE CLIENT : " + codeCli + "   DATE DE CREATION : " + dateCrea.toString() + "   Localite : " + depart
				+"\n NOMBRE DE COMMANDE : " + nbCde + "   CA REALISE : " + ca;
		return st;
	}
	
	public String getCodeCli() {return this.codeCli;}
	
	public void setDepart(String depart) {this.depart = depart;}
	
	public void update(float valeurCommande) {
		this.ca += valeurCommande;
		nbCde++;
	}
}
