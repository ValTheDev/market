package Base;

import MesExceptions.AbandonException;
import Utils.DateUser;
import Connexion.ConnexionFichier;
import IConsole.ES;
import Interfaces.InterfaceGestion;

public class GestionTableDesArticles53 implements InterfaceGestion<TableDesArticles53> {
	
	ES IO = new ES();
	
	private ConnexionFichier<TableDesArticles53> fichin;
	
	public GestionTableDesArticles53(String nomPhysique) {
		fichin = new ConnexionFichier<TableDesArticles53>(nomPhysique);
	}
	
	 public void menuGestion(TableDesArticles53 tabArt, Object...obj){
		TableDesCommandes53 tabCde = (TableDesCommandes53)obj[0];
		int choix;
		try {
			do{
				choix = menuChoix();
				switch(choix) {
					case 1 : ajouter(tabArt); break;
					case 2 : supprimer(tabArt, tabCde); break;
					case 3 : afficher(tabArt) ; break;
					case 4 : afficherPromo(tabArt); break;
					case 5 : menuTransfo(tabArt); break;
					case 0 : break;
				}
			}while(choix!=0);
		}
		catch (AbandonException ae) {
			IO.affiche("RETOUR AU MENU");
		}
	}
	
	public int menuChoix(Object...objects) throws AbandonException{
		String menu = "\n\t*** GESTION TABLE DES ARTICLES *** \n"
				+"\nCREER UN ARTICLE .......................1"
				+"\nSUPPRIMER UN ARTICLE....................2"
				+"\nAFFICHER TOUS LES ARTICLES .............3"
				+"\nAFFICHER LES ARTICLES PROMO ............4"
				+"\nTRANSFORMER TYPE D'UN ARTICLE ..........5"
				+"\nFIN ....................................0"
				+"\nVOTRE CHOIX : ";
		return IO.saisie(menu,0,5);
	}
	
// 	Methodes du menuGestion (dans l'ordre)
	
	public void ajouter(Object...objects) throws AbandonException {
		TableDesArticles53 tabArt = (TableDesArticles53)objects[0];
		int code = IO.saisie("CODE : ", 0, Integer.MAX_VALUE);
		if(tabArt.retourner(code) != null) IO.affiche("UN ELEMENT DE LA LISTE POSSEDE DEJA CE CODE");
		else {
			String designation = IO.saisie("DESIGNATION : ");
			float pu = IO.saisie("PRIX : ", 1.0f);
			boolean promo = IO.saisieOuiNon("APPLIQUER UNE PROMOTION? (O/N)");
			if(promo) {
				int quantiteMini = IO.saisie("QUANTITE MINI : ", 2);
				float reduction = IO.saisie("REDUCTION : ", 1);
				ArticleAbstrait art = new ArticlePromo(code, designation, pu, quantiteMini, reduction);
				tabArt.ajouter(art);
			}
			else {
				boolean lot = IO.saisieOuiNon("APPLIQUER UNE PROMOTION EN LOT? (O/N)");
				if (lot) {
					int quantiteLot = IO.saisie("QUANTITE DU LOT :", 2);
					float reduction = IO.saisie("POURCENTAGE DE REDUCTION : ", 1.0f);
					do {
						DateUser dateDebut = IO.saisieDate("VEUILLEZ INDIQUER LA DATE DE DEBUT DE LA PROMOTION : ");
						DateUser dateFin = IO.saisieDate("VEUILLEZ INDIQUER LA DATE DE FIN DE LA PROMOTION : ");
						if (dateDebut.valiDate() == false || dateFin.valiDate() == false) IO.affiche("UNE DES DATES INDIQUEES N'EST PAS VALIDE");
						else if (dateDebut.avant(dateFin)) {
							ArticleAbstrait art = new ArticleLot(code, designation, pu, quantiteLot, reduction, dateDebut, dateFin);
							tabArt.ajouter(art);
						}
						else IO.affiche("LA DATE DE DEBUT DE LA PROMOTION DOIT ETRE ANTERIEURE A LA DATE DE FIN DE LA PROMOTION");
					}while (IO.saisieOuiNon("INDIQUER DE NOUVELLES DATES ? (O/N) "));
				}
				else {
					ArticleAbstrait art = new Article(code, designation, pu);
					tabArt.ajouter(art);
				}
			}
			
		}
	}
	
	public void supprimer(Object...obj) throws AbandonException {
		TableDesArticles53 tabArt = (TableDesArticles53)obj[0];
		TableDesCommandes53 tabCde = (TableDesCommandes53)obj[1];
		int code = IO.saisie("\nCODE : ", 1, Integer.MAX_VALUE);
		if (tabArt.retourner(code) == null) IO.affiche("AUCUN ELEMENT DE LA LISTE NE POSSEDE CE CODE");
		else {
			if (tabCde.checkArtSupprimable(code) == false) IO.affiche("UNE COMMANDE NON SUPPRIMABLE CONCERNANT CET ARTICLE EXISTE");
			else {
				tabArt.supprimer(code);
				tabCde.purgeArticle(code);
			}
		}
	}
	
	public void afficher(TableDesArticles53 tabArt) {
		IO.affiche(tabArt.toString());
	}
	
	public void afficherPromo(TableDesArticles53 tabArt) {
		IO.affiche(tabArt.toStringPromo());
	}
	
// 	Gestion des transformations
	
	public void menuTransfo(TableDesArticles53 tabArt) throws AbandonException {
		if (tabArt.taille() == 0) IO.affiche("AUCUN ARTICLE DANS LA TABLE DES ARTICLES");
		else {
			int code = IO.saisie("QUEL ARTICLE VOULEZ-VOUS TRANSFORMER ?", 0);
			ArticleAbstrait art = tabArt.retourner(code);
			if (art == null) IO.affiche("AUCUN ARTICLE NE CORRESPOND A CE CODE");
			else {
				int choix = choixTransfo();
				switch (choix) {
				case 1 : transfoNormal(tabArt, art); break;
				case 2 : transfoPromo(tabArt, art); break;
				case 3 : transfoLot(tabArt, art); break;
				case 0 : break;
				}
			}
		}
	}
	
	public int choixTransfo() throws AbandonException {
		String st = "\nTRANSFORMER EN ARTICLE NORMAL .............1"
				   +"\nTRANSFORMER EN ARTICLE PROMOTIONNEL .......2"
			 	   +"\nTRANSFORMER EN ARTICLE LOT ................3"
				   +"\nFIN .......................................0"
				   +"\nVOTRE CHOIX : ";
		return IO.saisie(st, 0, 3);
	}
	
	public void transfoNormal(TableDesArticles53 tabArt, ArticleAbstrait art) {
		ArticleAbstrait newArt = new Article(art.getCode(), art.getDesignation(), art.getPu());
		tabArt.ajouter(newArt);
	}
	
	public void transfoPromo(TableDesArticles53 tabArt, ArticleAbstrait art) throws AbandonException {
		int quantiteMini = IO.saisie("QUANTITE MINIMUM : ", 2);
		float reduction = IO.saisie("POURCENTAGE DE REDUCTION : ", 1.0f);
		ArticleAbstrait newArt = new ArticlePromo(art.getCode(), art.getDesignation(), art.getPu(), quantiteMini, reduction);
		tabArt.ajouter(newArt);
		
	}
	
	public void transfoLot(TableDesArticles53 tabArt, ArticleAbstrait art) throws AbandonException {	
		int quantiteLot = IO.saisie("QUANTITE DU LOT :", 2);
		float reduction = IO.saisie("POURCENTAGE DE REDUCTION : ", 1.0f);
		do {
			DateUser dateDebut = IO.saisieDate("VEUILLEZ INDIQUER LA DATE DE DEBUT DE LA PROMOTION : ");
			DateUser dateFin = IO.saisieDate("VEUILLEZ INDIQUER LA DATE DE FIN DE LA PROMOTION : ");
			if (dateDebut.valiDate() == false || dateFin.valiDate() == false) IO.affiche("UNE DES DATES INDIQUEES N'EST PAS VALIDE");
			else if (dateDebut.avant(dateFin)) {
				ArticleAbstrait newArt = new ArticleLot(art.getCode(), art.getDesignation(), art.getPu(), quantiteLot, reduction, dateDebut, dateFin);
				tabArt.ajouter(newArt);
			}
			else IO.affiche("LA DATE DE DEBUT DE LA PROMOTION DOIT ETRE ANTERIEURE A LA DATE DE FIN DE LA PROMOTION");
		}while (IO.saisieOuiNon("INDIQUER DE NOUVELLES DATES ? (O/N) "));
	}

	
//	Autres methodes
	
	public TableDesArticles53 recuperer() {
		TableDesArticles53 tabArt = (TableDesArticles53)fichin.charger();
		if (tabArt == null) tabArt = new TableDesArticles53();
		return tabArt;
	}
	
	public void enregistrer(TableDesArticles53 tabArt) {
		fichin.sauvegarder(tabArt);
	}

}
