package Base;

import Connexion.ConnexionFichier;
import MesExceptions.AbandonException;
import Utils.DateUser;
import IConsole.ES;
import Interfaces.InterfaceGestion;

public class GestionTableDesCommandes53 implements InterfaceGestion<TableDesCommandes53> {
	
	ES IO = new ES();
	GestionUneCommande53 gcde = new GestionUneCommande53();
	private ConnexionFichier<TableDesCommandes53> fichin;
	
	public GestionTableDesCommandes53(String nomPhysique) {
		fichin = new ConnexionFichier<TableDesCommandes53>(nomPhysique);
	}
	
	
	public void menuGestion(TableDesCommandes53 tabCde, Object...objects) {
		TableDesArticles53 tabArt = (TableDesArticles53)objects[0];
		TableDesClients tabCli = (TableDesClients)objects[1];
		int choix;
		try{
			do {choix=menuChoix(tabCde);
				switch(choix) {
					case 1 : ajouter(tabArt, tabCde, tabCli); break;
					case 2 : gerer(tabCde, tabArt); break;
					case 3 : afficher(tabCde); break;
					case 4 : afficherFacturee(tabCde); break;
					case 5 : supprimer(tabCde); break;
					case 0 : break;
				}
			}while(choix!=0);
		}
		catch (AbandonException ae) {
			IO.affiche("RETOUR AU MENU");
		}
	}
	
	public int menuChoix(Object...objects) throws AbandonException {
		TableDesCommandes53 tabCde = (TableDesCommandes53)objects[0];
		String menu = "\n\t*** GESTION DES COMMANDES *** \n"
				+"\nCREER COMMANDE NUMERO "+premierNumeroDispo(tabCde)+" ........1"
				+"\nGERER UNE COMMANDE ....................2"
				+"\nAFFICHER UNE COMMANDE NON FACTUREE ....3"
				+"\nAFFICHER UNE COMMANDE FACTUREE ........4"
				+"\nSUPPRIMER UNE COMMANDE ................5"
				+"\nFIN....................................0"
				+"\nVOTRE CHOIX : ";
		return IO.saisie(menu,0,5);
	}
	
//  Methodes du menuGestion (dans l'ordre)
	
	public void ajouter(Object...objects) throws AbandonException {
		TableDesArticles53 tabArt = (TableDesArticles53)objects[0];
		TableDesCommandes53 tabCde = (TableDesCommandes53)objects[1];
		TableDesClients tabCli = (TableDesClients)objects[2];
		
		String codeCli = IO.saisie("VEUILLEZ INDIQUER VOTRE CODE CLIENT : ");
		if (tabCli.retourner(codeCli) == null) IO.affiche("AUCUN CLIENT CORRESPONDANT");
		else {
			String numCde = premierNumeroDispo(tabCde);
			UneCommande53<String> cde = new UneCommande53<String>(numCde, codeCli);
			gcde.menuGestion(cde, tabArt, tabCde);
			if (cde.taille() != 0) tabCde.ajouter(cde);
		}
	}
	
	public void gerer(TableDesCommandes53 tabCde, TableDesArticles53 tabArt) throws AbandonException {
		if (tabCde.taille() == 0) IO.affiche("AUCUNE COMMANDE EN COURS");
		else {
			IO.affiche(tabCde.cle());
			String numCde = IO.saisie("QUELLE COMMANDE GERER : ");
			UneCommande53<String> cde = tabCde.retourner(numCde);
			if (cde == null) IO.affiche("CETTE COMMANDE N'EXISTE PAS");
			else if (cde.getEtatFacture()) IO.affiche("CETTE COMMANDE A DEJA ETE FACTUREE");
			else gcde.menuGestion(cde, tabArt, tabCde);
		}	
	}
	
	public void afficher(TableDesCommandes53 tabCde) throws AbandonException {
		if (tabCde.tailleNonFacturee() == 0) IO.affiche("AUCUNE COMMANDE FACTUREE");
		else {
			IO.affiche(tabCde.cleNonFacturee());
			String numCde = IO.saisie("QUELLE COMMANDE AFFICHER ?");
			UneCommande53<String> cde = tabCde.retourner(numCde);
			if (cde == null) IO.affiche("AUCUNE COMMANDE NE CORRESPOND");
			else if (cde.getEtatFacture()) IO.affiche("CETTE COMMANDE A ETE FACTUREE"); 
			else IO.affiche(cde.toString());
		}	
	}
	
	public void afficherFacturee(TableDesCommandes53 tabCde) throws AbandonException {
		if (tabCde.tailleFacturee() == 0) IO.affiche("AUCUN COMMANDE NON FACTUREE EN COURS");
		else {
			IO.affiche(tabCde.cleFacturee());
			String numCde = IO.saisie("QUELLE COMMANDE AFFICHER ?");
			UneCommande53<String> cde = tabCde.retourner(numCde);
			if (cde == null) IO.affiche("AUCUNE COMMANDE NE CORRESPOND");
			else if (cde.getEtatFacture() == false) IO.affiche("CETTE COMMANDE N'A PAS ENCORE ETE FACTUREE");
			else IO.affiche(cde.toString());
		}
	}
	
	public void supprimer(Object...objects) throws AbandonException {
		TableDesCommandes53 tabCde = (TableDesCommandes53)objects[0];
		if (tabCde.taille() == 0) IO.affiche("AUCUNE COMMANDE EN COURS");
		else {
			IO.affiche(tabCde.cleSupprimable());
			String numCde = IO.saisie("QUELLE COMMANDE SUPPRIMER ?");
			UneCommande53<String> cde = tabCde.retourner(numCde);
			if (cde == null) IO.affiche("AUCUNE COMMANDE CORRESPONDANTE");
			else if (cde.estSupprimable()) tabCde.supprimer(numCde);
			else IO.affiche("CETTE COMMANDE NE PEUT PAS ENCORE ETRE SUPPRIMEE");
		}
	}
	
// 	Autres methodes
	
	public String premierNumeroDispo(TableDesCommandes53 tabCde) {
		DateUser date = new DateUser();
		int annee = date.getAnnee();
		int mois = date.getMois();
		int jour = date.getJour();
		String st = "" + annee + mois + jour;
		
		boolean existe = true;
		int check =1;
		do {
			String test = st + check;
			if (tabCde.retourner(test) != null) check++;
			else existe = false;
		}while(existe);
		st += check;
		return st;
	}
		
	public TableDesCommandes53 recuperer() {
		TableDesCommandes53 tabCde = (TableDesCommandes53)fichin.charger();
		if (tabCde == null) tabCde = new TableDesCommandes53();
		return tabCde;
	}
	
	public void enregistrer(TableDesCommandes53 tabCde) {
		fichin.sauvegarder(tabCde);
	}

}
