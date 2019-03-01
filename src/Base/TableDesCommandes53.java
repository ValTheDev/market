package Base;

import java.util.*;
import java.io.*;
import Interfaces.InterfaceStructure;
import Utils.DateUser;

public class TableDesCommandes53 implements Serializable, InterfaceStructure<UneCommande53<String>, String> {
	
	private static final long serialVersionUID = 1L;
	TableDesArticles53 tabArt = new TableDesArticles53();
	
	private Hashtable<String, UneCommande53<String>> tabCde = new Hashtable<String, UneCommande53<String>>();
	
	public TableDesCommandes53() {}
	
	public String toString() {
		String st = "\n*** TABLE DES COMMANDES ***\n";
		for(UneCommande53<String> cde : tabCde.values()) {
			st = st + cde.toString();
		}
		return st;
	}
	
	public UneCommande53<String> retourner(String numCde) {
		return tabCde.get(numCde);
	}
	
	public void ajouter(UneCommande53<String> cde) {
		tabCde.put(cde.getNumCde(), cde);
	}
	
	public void supprimer(String numCde) {
		tabCde.remove(numCde);
	}
	
	public int taille() {return tabCde.size();}
	
	public int tailleNonFacturee() {
		int x=0;
		for (UneCommande53<String> cde : tabCde.values()) {
			if (cde.getEtatFacture() == false) x++;
		}
		return x;
	}
	
	public int tailleFacturee() {
		int x=0;
		for (UneCommande53<String> cde : tabCde.values()) {
			if (cde.getEtatFacture()) x++;
		}
		return x;
	}
	
	public String cle() {
		String st = "\n *** LISTE DES NUMEROS DE COMMANDE ***\n";			
		Enumeration<String> enumCde = tabCde.keys();	
		while(enumCde.hasMoreElements()) {
			st = st + enumCde.nextElement();
		}
		return st;
	}
	
	public String cleNonFacturee() {
		String st = "\n *** LISTE DES COMMANDES NON FACTUREES *** ";
		for (UneCommande53<String> cde : tabCde.values()) {
			if (cde.getEtatFacture() == false) st += "\n" + cde.getNumCde();
		}
		return st;
	}
	
	public String cleFacturee() {
		String st = "\n *** LISTE DES COMMANDES FACTUREES *** ";
		for (UneCommande53<String> cde : tabCde.values()) {
			if (cde.getEtatFacture()) st += "\n" + cde.getNumCde();
		}
		return st;
	}
	
	public String cleSupprimable() {
		String st = "\n *** LISTE DES NUMEROS DES COMMANDES SUPPRIMABLES *** ";
		for (UneCommande53<String> cde : tabCde.values()) {
			if (cde.estSupprimable()) st += "\n" + cde.getNumCde();
		}
		return st;
	}
	
	public String commandesNonFacturee() {
		String st = "\n *** LISTE DES NUMEROS DES COMMANDES NON FACTUREES *** \n";
		Enumeration<String> enumCde = tabCde.keys();
		
		while(enumCde.hasMoreElements()) {
			String commande = enumCde.nextElement();
			if (tabCde.get(commande).getEtatFacture() == false) st+= "\n" + commande;
		}
		return st;
	}
	
	public String facturer(String numCde, TableDesArticles53 tabArt) {
		DateUser date = new DateUser();
		
		String haut = "\t *** FACTURE DE LA COMMANDE NUMERO " + numCde + " ***\n"
				+"\t *** EN DATE DU " + date.toString() +" ***\n\n"
				+"CODE\tLIBELLE\tQUANTITE\tPU(HT)\tPT(HT)\n\n"
				+"___________________________________________________________\n";
		
		UneCommande53<String> cde = retourner(numCde);
		String mid = "";
		float tht = cde.getValeurCommande();
		for (Ldc53 ldc : cde.getCde()) {
			ArticleAbstrait art = tabArt.retourner(ldc.getCode());
			mid += art.facturer(ldc.getQuantite(), cde.getDateCde()) + "\n";
		}
		
		double taxe = tht * 0.196;
		double ttc = tht *1.196;
		
		String bas ="___________________________________________________________"
				+"\n\t\t\t TOTAL HORS TAXE : " +tht
				+"\n\t\t\t TVA (19.6%) : " +taxe
				+"\n\t\t\t TOTAL TTC : " + ttc;
		
		return haut + mid + bas;
	}
	
	public boolean checkArtSupprimable(int code) {
		for (UneCommande53<String> cde : tabCde.values()) {
			if (cde.retourner(code) != null && cde.estSupprimable()) return true;
		}
		return false;
	}
	
	public boolean checkCliSupprimable(String codeCli) {
		for (UneCommande53<String> cde : tabCde.values()) {
			if (cde.getCodeCli().equals(codeCli) && cde.estSupprimable()) return true;
		}
		return false;
	}
	
	public void purgeArticle(int code) {
		Iterator<String> cles = tabCde.keySet().iterator();
		while(cles.hasNext()) {
			String st = cles.next();
			UneCommande53<String> cde = retourner(st);
			cde.supprimer(code);
			if (cde.taille() == 0) cles.remove();
		}
	}
	
	public void purgeClient(String codeCli) {
		Iterator<String> cles = tabCde.keySet().iterator();
		while(cles.hasNext()) {
			String st = cles.next();
			UneCommande53<String> cde = retourner(st);
			if (cde.getCodeCli().equals(codeCli)) cles.remove();
		}
	}
}
