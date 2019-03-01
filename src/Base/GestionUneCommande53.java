package Base;

import MesExceptions.AbandonException;
import java.util.*;
import IConsole.ES;
import Interfaces.InterfaceGestion;
import java.io.*;

public class GestionUneCommande53 implements Serializable, InterfaceGestion<UneCommande53<String>> {
	
	private static final long serialVersionUID = 1L;
	ES IO = new ES();
	
	Scanner scan = new Scanner(System.in);
	
	public void menuGestion(UneCommande53<String> cde, Object...objects) {
		TableDesArticles53 tabArt = (TableDesArticles53)objects[0];
		TableDesCommandes53 tabCde = (TableDesCommandes53)objects[1];
		int choix;
		try{
			do {choix = menuChoix(cde);
				switch(choix) {
					case 1 : ajouter(tabArt, cde); break;
					case 2 : supprimer(tabCde, cde); break;
					case 3 : afficher(cde); break;
					case 0 : break;
				}
			}while (choix != 0);
		}
		catch (AbandonException ae) {
			IO.affiche("RETOUR AU MENU");
		}
	}
	
	public int menuChoix(Object...objects) throws AbandonException {
		UneCommande53<String> cde = (UneCommande53<String>)objects[0];
		String menu = "\n\t*** GESTION COMMANDE "+ cde.getNumCde() +" ***"
				+"\n\t*** TOTAL = " + cde.getValeurCommande()  + " *** \n"
				+"\nAJOUTER ARTICLE .............1"
				+"\nSUPPRIMER ARTICLE ...........2"
				+"\nAFFICHER LA COMMANDE ........3"
				+"\nFIN .........................0";
		return IO.saisie(menu,0,3);
	}
	
	public void ajouter(Object...objects) throws AbandonException {
		TableDesArticles53 tabArt = (TableDesArticles53)objects[0];
		UneCommande53<String> cde = (UneCommande53<String>)objects[1];
		
		int code = IO.saisie("CODE : ", 0, Integer.MAX_VALUE);
		ArticleAbstrait art = tabArt.retourner(code);
		if (art == null) IO.affiche("AUCUN ARTICLE NE POSSEDE CE CODE");
		else {
			int quantite = IO.saisie("QUANTITE : ", 1, Integer.MAX_VALUE);
			Ldc53 ldc = new Ldc53(code, quantite);
			Ldc53 baseLdc = cde.retourner(code);
			if (baseLdc != null) {
				cde.soustraireValeur(art.prixTotal(baseLdc.getQuantite(), cde.getDateCde()));
				baseLdc.augmenterQuantite(quantite);
				cde.ajouterValeur(art.prixTotal(baseLdc.getQuantite(), cde.getDateCde()));
			}
			else {
				cde.ajouter(ldc);
				cde.ajouterValeur(art.prixTotal(quantite, cde.getDateCde()));
			}
			
		}
	}
	
	public void supprimer(Object...objects) throws AbandonException {
		TableDesCommandes53 tabCde = (TableDesCommandes53)objects[0];
		UneCommande53<String> cde = (UneCommande53<String>)objects[1];
		int code = IO.saisie("QUEL ARTICLE SUPPRIMER : ", 0, Integer.MAX_VALUE);
		if (cde.retourner(code) == null) IO.affiche("CET ARTICLE N'EST PAS DANS LA COMMANDE");
		else cde.supprimer(code);
		if (cde.taille() == 0) {tabCde.supprimer(cde.getNumCde());
		}
	}
	
	public void afficher(UneCommande53<String> cde) {
		IO.affiche(cde.toString());
	}
	

}
