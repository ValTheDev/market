package Base;

import Interfaces.InterfaceGestion;
import IConsole.ES;
import Connexion.ConnexionFichier;
import MesExceptions.AbandonException;

public class GestionTableDesClients implements InterfaceGestion<TableDesClients> {
	
	ES IO = new ES();
	
	private ConnexionFichier<TableDesClients> fichin;
	
	public GestionTableDesClients(String nomPhysique) {
		fichin = new ConnexionFichier<TableDesClients>(nomPhysique);
	}
	
	public void menuGestion(TableDesClients tabCli, Object...obj) {
		TableDesCommandes53 tabCde = (TableDesCommandes53)obj[0];
		TableDesFactures53 tabFac = (TableDesFactures53)obj[1];
		try {
			int choix;
			do {
				choix = menuChoix();
					switch(choix) {
					case 1 : ajouter(tabCli); break;
					case 2 : supprimer(tabCli, tabCde, tabFac); break;
					case 3 : modifier(tabCli); break;
					case 4 : afficher(tabCli); break;
					case 5 : afficherTous(tabCli); break;
					case 0 : break;
				}
			}while(choix != 0);
		}
		catch (AbandonException ae) {IO.affiche("RETOUR AU MENU");}
	}
	
	public int menuChoix(Object...obj) throws AbandonException{
		String menu = "\n\t *** GESTION TABLE DES CLIENTS *** \n"
				+"\nCREER CLIENT ......................1"
				+"\nSUPPRIMER CLIENT ..................2"
				+"\nMODIFIER DEPARTEMENT CLIENT .......3"
				+"\nAFFICHER UN CLIENT ................4"
				+"\nAFFICHER LES CLIENTS ..............5"
				+"\nFIN ...............................0"
				+"\nVOTRE CHOIX : ";
		return IO.saisie(menu, 0, 5);
	}
	
// 	Methodes du menuGestion (dans l'ordre)
	
	public void ajouter(Object...obj) throws AbandonException {
		TableDesClients tabCli = (TableDesClients)obj[0];
		IO.affiche("CREATION D'UN NOUVEAU CLIENT");
		String nom;
		do {
			nom = IO.saisie("NOM : ");
			if (nom.length()<2) IO.affiche("VEUILLEZ INDIQUER UN NOM D'AU MOINS 2 LETTRES");
		}while(nom.length()<2);
		String prenom = IO.saisie("PRENOM : ");
		String depart = IO.saisie("DEPARTEMENT : ");
		String codeCli = creerCodeClient(tabCli, nom);
		Client cli = new Client(nom, prenom, codeCli, depart);
		tabCli.ajouter(cli);
	}
	
	public void supprimer(Object...obj) throws AbandonException {
		TableDesClients tabCli = (TableDesClients)obj[0];
		TableDesCommandes53 tabCde = (TableDesCommandes53)obj[1];
		TableDesFactures53 tabFac = (TableDesFactures53)obj[2];
		
		if (tabCli.taille() == 0) IO.affiche("AUCUN CLIENT ENREGISTRE");
		else {
			IO.affiche(tabCli.cle());
			String codeCli = IO.saisie("QUEL CLIENT SUPPRIMER ?");
			Client cli = tabCli.retourner(codeCli);
			if (cli == null) IO.affiche("AUCUN CLIENT CORRESPONDANT");
			else if (tabCde.checkCliSupprimable(codeCli) == false || tabFac.checkCliSupprimable(codeCli) == false) {
				IO.affiche("UNE COMMANDE OU UNE FACTURE NON SUPPRIMABLE EXISTE ENCORE POUR CE CLIENT");
			}
			else tabCli.supprimer(codeCli);
			tabCde.purgeClient(codeCli);
			tabFac.purgeClient(codeCli);
		}	
	}
	
	public void modifier(TableDesClients tabCli) throws AbandonException {
		if (tabCli.taille() == 0) IO.affiche("AUCUN CLIENT ENREGISTRE");
		else {
			IO.affiche(tabCli.cle());
			String codeCli = IO.saisie("QUEL CLIENT MODIFIER ?");
			Client cli = tabCli.retourner(codeCli);
			if (cli == null) IO.affiche("AUCUN CLIENT CORRESPONDANT");
			else {
				String depart = IO.saisie("VEUILLEZ INDIQUER LE NOUVEAU DEPARTEMENT : ");
				cli.setDepart(depart);
			}
		}
	}
	
	public void afficher(TableDesClients tabCli) throws AbandonException {
		if (tabCli.taille() == 0) IO.affiche("AUCUN CLIENT ENREGISTRE");
		else {
			IO.affiche(tabCli.cle());
			String codeCli = IO.saisie("QUEL CLIENT MODIFIER ?");
			Client cli = tabCli.retourner(codeCli);
			if (cli == null) IO.affiche("AUCUN CLIENT CORRESPONDANT");
			else IO.affiche(cli.toString());
		}
	}
	
	public void afficherTous(TableDesClients tabCli) {
		IO.affiche(tabCli.toString());
	}
	
	
//	Autres methodes
	
	public String creerCodeClient(TableDesClients tabCli, String nom) {
		if (nom.length()==2) nom += "-";
		int x = 1;
		String st;
		Client cli;
		do {
			st = nom+x;
			cli = tabCli.retourner(st);
			if (cli != null) x++;
		}while(cli != null);
		return st;
	}
	
	public TableDesClients recuperer() {
		TableDesClients tabCli = fichin.charger();
		if (tabCli == null) tabCli = new TableDesClients();
		return tabCli;
	}
	
	public void enregistrer(TableDesClients tabCli) {
		fichin.sauvegarder(tabCli);
	}
}
