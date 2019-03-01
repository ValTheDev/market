package Base;

import Connexion.ConnexionFichier;
import Interfaces.InterfaceGestion;
import IConsole.ES;
import MesExceptions.AbandonException;

public class GestionTableDesFactures53 implements InterfaceGestion<TableDesFactures53>{
	
	ES IO = new ES();
	
	private ConnexionFichier<TableDesFactures53> fichin;
	
	public GestionTableDesFactures53(String nomPhysique) {
		fichin = new ConnexionFichier<TableDesFactures53>(nomPhysique);
	}
	
	public void menuGestion(TableDesFactures53 tabFac, Object...objects) {
		TableDesArticles53 tabArt = (TableDesArticles53)objects[0];
		TableDesCommandes53 tabCde = (TableDesCommandes53)objects[1];
		TableDesClients tabCli = (TableDesClients)objects[2];
		int choix;
		try {
			do {
				choix = menuChoix();
				switch(choix) {
					case 1 : ajouter(tabFac, tabArt, tabCde, tabCli); break;
					case 2 : supprimer(tabFac); break;
					case 3 : afficher(tabFac); break;
					case 0 : break;		
				}
			}while(choix!=0);
		}
		catch (AbandonException ae) {
			IO.affiche("RETOUR AU MENU");
		}
		
	}
	
	public int menuChoix(Object...objects) throws AbandonException{
		String menu = "\n\t *** GESTION DES FACTURES *** \n"
				+"\nEDITER UNE FACTURE ............1"
				+"\nSUPPRIMER UNE FACTURE .........2"
				+"\nAFFICHER UNE FACTURE ..........3"
				+"\nFIN ...........................0"
				+"\nVOTRE CHOIX :";
		
		return IO.saisie(menu, 0, 3);
	}
	
// 	Methodes du menuGestion (dans l'ordre)
	
	public void ajouter(Object...objects) throws AbandonException {
		TableDesFactures53 tabFac = (TableDesFactures53)objects[0];
		TableDesArticles53 tabArt = (TableDesArticles53)objects[1];
		TableDesCommandes53 tabCde = (TableDesCommandes53)objects[2];
		TableDesClients tabCli = (TableDesClients)objects[3];
		
		if (tabCde.tailleNonFacturee() == 0) IO.affiche("AUCUNE COMMANDE NON FACTUREE EN COURS");
		else {
			IO.affiche(tabCde.commandesNonFacturee());
			String numCde = IO.saisie("QUELLE COMMANDE FACTURER ?");
			UneCommande53<String> commande = tabCde.retourner(numCde);
			if (commande == null) IO.affiche("AUCUNE COMMANDE CORRESPONDANTE");
			else if (commande.getEtatFacture()) IO.affiche("CETTE COMMANDE A DEJA ETE FACTUREE");
			else {
				String st = tabCde.facturer(numCde, tabArt);
				IO.affiche(st);
				UneFacture53<String> fac = new UneFacture53<String>(numCde, st, commande.getCodeCli(), commande.getValeurCommande());
				tabFac.ajouter(fac);
				commande.setFacturee();
				Client cli = tabCli.retourner(commande.getCodeCli());
				cli.update(commande.getValeurCommande());
			}	
		}
	}
	
	public void supprimer(Object...objects) throws AbandonException {
		TableDesFactures53 tabFac = (TableDesFactures53)objects[0];
		if (tabFac.tailleSupprimable() == 0) IO.affiche("AUCUNE FACTURE SUPPRIMABLE");
		else  {
			IO.affiche(tabFac.cleSupprimable());
			String numFac = IO.saisie("QUELLE FACTURE SUPPRIMER ?");
			UneFacture53<String> fac = tabFac.retourner(numFac);
			if (fac == null) IO.affiche("AUCUNE FACTURE NE CORRESPOND");
			else if (fac.estSupprimable()) tabFac.supprimer(numFac);
			else IO.affiche("CETTE COMMANDE NE PEUT PAS ENCORE ETRE SUPPRIMEE");
		}
	}
	
	public void afficher(TableDesFactures53 tabFac) throws AbandonException {
		if (tabFac.taille() == 0) IO.affiche("AUCUNE FACTURE A AFFICHER");
		else {
			IO.affiche(tabFac.cle());
			String numFac = IO.saisie("QUELLE FACTURE AFFICHER ?");
			UneFacture53<String> fac = tabFac.retourner(numFac);
			if (fac == null) IO.affiche("AUCUNE FACTURE NE CORRESPOND");
			else IO.affiche(fac.getEdition());
		}
	}
	

// 	Autres methodes	
	
	public TableDesFactures53 recuperer() {
		TableDesFactures53 tabFac = fichin.charger();
		if (tabFac == null) tabFac = new TableDesFactures53();
		return tabFac;
	}
	
	public void enregistrer(TableDesFactures53 tabFac) {
		fichin.sauvegarder(tabFac);
	}


	
	

}
