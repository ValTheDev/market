package Base;

import MesExceptions.AbandonException;
import IConsole.ES;

public class ClientJava53 {
	
	static ES IO = new ES();
	
	public static void main (String [] args) {
			
		GestionTableDesArticles53 gta = new GestionTableDesArticles53("TableDesArticles.data");
		GestionTableDesCommandes53 gtc = new GestionTableDesCommandes53("TableDesCommandes.data");
		GestionTableDesFactures53 gtf = new GestionTableDesFactures53("TableDesFactures.data");
		GestionTableDesClients gtcli = new GestionTableDesClients("TableDesClients.data");
		
		TableDesArticles53 tabArt = gta.recuperer();
		TableDesCommandes53 tabCde = gtc.recuperer();
		TableDesFactures53 tabFac = gtf.recuperer();
		TableDesClients tabCli = gtcli.recuperer();
		
		int choix;
		
		do {
			choix=menuChoix();
			switch(choix) {
			case 1 : gta.menuGestion(tabArt, tabCde); break;
			case 2 : gtc.menuGestion(tabCde, tabArt, tabCli); break;
			case 3 : gtf.menuGestion(tabFac, tabArt, tabCde, tabCli); break;
			case 4 : gtcli.menuGestion(tabCli, tabCde, tabFac);
			case 0 : gta.enregistrer(tabArt); gtc.enregistrer(tabCde); gtf.enregistrer(tabFac); gtcli.enregistrer(tabCli); break;
			}
		}while(choix!=0);
		
		IO.affiche("AU REVOIR ET A BIENTOT SUR SUPERETTE");

		}
	
	
	public static int menuChoix() {
		do {	
			try{
				String menu = "\n\t *** MENU GENERAL *** \n"
						+"\nGESTION DES ARTICLES ................1"
						+"\nGESTION DES COMMANDES ...............2"
						+"\nGESTION DES FACTURES ................3"
						+"\nGESTION DES CLIENTS .................4"
						+"\nSAUVEGARDER ET QUIITER ..............0"
						+"\nVOTRE CHOIX : ";	
				return IO.saisie(menu,0,4);
			}
			catch (AbandonException ae) {return 0;}	
		}while(true);
	}
	
	
}
