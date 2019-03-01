package Base;

public class ArticlePromo extends ArticleAbstrait {
	
	private static final long serialVersionUID = 1L;
	
	private int quantiteMini;
	private float reduction;
	
	public ArticlePromo(int code, String designation, float pu, int quantiteMini, float reduction) {
		super(code, designation, pu); 
		this.quantiteMini = quantiteMini; 
		this.reduction = reduction;
	}
	
	public ArticlePromo() {}

	public int getQuantiteMini() {return this.quantiteMini;}
	public float getReduction() {return this.reduction;}
	
	public void setQuantiteMini(int quantiteMini) {this.quantiteMini = quantiteMini;}
	public void setReduction(float reduction) {this.reduction = reduction;}
	
	public String toString() {
		return " *** " + code + " *** " + designation + " *** " + pu + " *** " 
				+ reduction + "% de reduction pour " + quantiteMini + " articles commandes";
	}
	
	public String facturer(int quantite, Object...obj) {
		return code + "\t" + designation + "\t" + quantite + "\t" + pu + "\t" + prixTotal(quantite)
				+"\t" + reduction + "% de reduction pour " +quantiteMini + " articles" ;
	}
	
	public float prixTotal(int quantite, Object...obj) {
		float prix = pu * quantite;
		if (quantite < quantiteMini) return prix;
		else return prix * (1-reduction / 100);
	}

}
