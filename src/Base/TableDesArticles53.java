package Base;

import java.util.TreeMap;
import Interfaces.InterfaceStructure;

import java.io.*;

public class TableDesArticles53 implements Serializable, InterfaceStructure<ArticleAbstrait, Integer>{
	
	private static final long serialVersionUID = 1L;
	
	private TreeMap<Integer, ArticleAbstrait> tabArt = new TreeMap<Integer, ArticleAbstrait>();
	
	public String toString() {
		String st = "\t*** TABLE DES ARTICLES ***\n";
		
		for (ArticleAbstrait art : tabArt.values()) {
			st = st + art.toString() + "\n";
		}
		return st;
	}
	
	public String toStringPromo() {
		String st = "\t *** LISTE DES ARTICLES EN PROMO ***\n";
		for (ArticleAbstrait art : tabArt.values()) {
			if (art instanceof ArticlePromo) st += art.toString() +"\n";
		}
		return st;
	}
	
	public void ajouter(ArticleAbstrait art) {
		tabArt.put(art.getCode(), art);
	}
	
	public ArticleAbstrait retourner(Integer code) {
		return tabArt.get(code);
	}
	
	public void supprimer (Integer code) {
		tabArt.remove(code);
	} 
	
	public int taille() {
		return tabArt.size();
	}
	
}
