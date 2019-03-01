package Base;

import Utils.DateUser;

public class ArticleLot extends ArticleAbstrait {
	
	private static final long serialVersionUID = 1L;
	
	private int quantiteLot;
	private float reduction;
	private DateUser dateDebut;
	private DateUser dateFin;
	
	
	public ArticleLot(int code, String designation, float pu, int quantiteLot, float reduction, DateUser dateDebut, DateUser dateFin) {
		super(code, designation, pu);
		this.quantiteLot = quantiteLot; 
		this.reduction = reduction;
		this.dateDebut = dateDebut; 
		this.dateFin = dateFin;
	}
	
	public String toString() {
		return " *** " + code + " *** " + designation + " *** " + pu + " *** "
				+ reduction + "% de reduction par lot de " + quantiteLot + " articles";
	}
	
	public String facturer(int quantite, Object...obj) {
		DateUser dateCde = (DateUser)obj[0];
		return code + "\t" + designation + "\t" + quantite + "\t" + pu + "\t" + prixTotal(quantite, dateCde)
		+ "\t" + reduction +"% de reduction par lot de " + quantiteLot + " articles";
	}
	
	
	public float prixTotal(int quantite, Object...obj) {
		float prix;
		DateUser dateCde = (DateUser)obj[0];
		if (dateCde.validite(this.dateDebut, this.dateFin)) {
			int nbLot = quantite/quantiteLot;
			int restant = quantite%quantiteLot;
			prix = (nbLot*quantiteLot*pu)*(1-reduction/100) + restant*pu;
		}
		else prix = quantite * pu;	
		return prix;
	}

}
