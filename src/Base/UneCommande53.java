package Base;

import java.util.*;
import java.io.*;
import Utils.DateUser;

public class UneCommande53 <TypeCode> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private TypeCode numCde;
	private String codeCli;
	private Vector<Ldc53> cde = new Vector<>();
	private DateUser dateCde;
	private boolean etatFacture = false;
	private float valeurCommande = 0f;
	
	public UneCommande53(TypeCode numCde, String codeCli) {
		this.numCde = numCde; this.codeCli = codeCli; this.dateCde = new DateUser();
	}
	
	public String toString() {
		String st = "\n\t*** CONTENU DE LA COMMANDE ***\n";
		for (Ldc53 ldc : cde) {
			st = st + ldc.toString();
		}
		return st;
	}
	
	public TypeCode getNumCde() {return numCde;}
	public String getCodeCli() {return this.codeCli;}
	public DateUser getDateCde() {return this.dateCde;}
	public Vector<Ldc53> getCde() {return this.cde;}
	public boolean getEtatFacture() {return this.etatFacture;}
	public float getValeurCommande() {return this.valeurCommande;}
	
	public void setCde(Vector<Ldc53> cde) {this.cde = cde;}
	public void setCodeCli(String codeCli) {this.codeCli = codeCli;}
	public void setNumCde(TypeCode numCde) {this.numCde = numCde;}
	
	public void ajouterValeur(float valeur) {this.valeurCommande += valeur;}
	public void soustraireValeur(float valeur) {this.valeurCommande -= valeur;}
	
	
	
	public Ldc53 retourner(int code) {
		for (Ldc53 ldc : cde) {
			if (ldc.getCode() == code) return ldc;
		}
		return null;
	}
	
	public void ajouter(Ldc53 ldc) {
		cde.addElement(ldc);
	}
	
	public void supprimer(int code) {
		Ldc53 ligne = retourner(code);
		cde.remove(ligne);
	}
	
	public int taille() {return cde.size();}
	
	public void setFacturee() {
		this.etatFacture = true;
	}
	
	public boolean estSupprimable() {
		if (this.etatFacture == true) return this.dateCde.semainePlus();
		return true;
	}
	
	
}
