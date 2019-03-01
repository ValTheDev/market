package Base;

public class Article extends ArticleAbstrait {
	
	private static final long serialVersionUID = 1L;

	public Article(int code, String designation, float pu) {
		super(code, designation, pu);
	}
	
	public Article() {}
	
	public String toString() {
		return " *** " + code + " *** " + designation + " *** " + pu + " *** ";
	}
	
	public String facturer(int quantite, Object...obj) {
		return code + "\t" + designation + "\t" + quantite + "\t" + pu + "\t" + prixTotal(quantite);
	}
	
	public float prixTotal(int quantite, Object...obj) {
		return pu * quantite;
	}
}
