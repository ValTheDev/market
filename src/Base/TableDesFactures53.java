package Base;

import Interfaces.InterfaceStructure;
import java.util.*;
import java.io.*;

public class TableDesFactures53 implements Serializable, InterfaceStructure<UneFacture53<String>, String> {
	
	private static final long serialVersionUID = 1L;
	
	private Hashtable<String, UneFacture53<String>> tabFac = new Hashtable<String, UneFacture53<String>>();

	public TableDesFactures53() {}
	
	public void ajouter(UneFacture53<String> fac) {
		tabFac.put(fac.getNumFacture(), fac);
	}
	
	public void supprimer(String numFac) {
		tabFac.remove(numFac);
	}
	
	public UneFacture53<String> retourner(String numFac){
		return tabFac.get(numFac);
	}
	
	public int taille() {
		return tabFac.size();
	}
	
	public String cle() {
		String st = "\n\t *** LISTE DES NUMEROS DE FACTURES *** \n";
		Enumeration<String> enumFac = tabFac.keys();
		while(enumFac.hasMoreElements()) {
			st += "\n" + enumFac.nextElement();
		}
		return st;
	}
	
	public int tailleSupprimable() {
		int x = 0;
		for (UneFacture53<String> fac : tabFac.values()) {
			if (fac.estSupprimable()) x++;
		}
		return x;
	}
	
	public String cleSupprimable() {
		String st = "\n\t *** LISTE DES NUMEROS DE FACTURE SUPPRIMABLES *** \n";
		for (UneFacture53<String> fac : tabFac.values()) {
			if (fac.estSupprimable()) st += "\n" + fac.getNumFacture();
		}
		return st;
	}
	
	public boolean checkCliSupprimable(String codeCli) {
		for (UneFacture53<String> fac : tabFac.values()) {
			if (fac.getCodeCli().equals(codeCli) && fac.estSupprimable()) return true;
		}
		return false;
	}
	
	public void purgeClient(String codeCli) {
		Iterator<String> cles = tabFac.keySet().iterator();
		while (cles.hasNext()) {
			String st = cles.next();
			UneFacture53<String> fac = retourner(st);
			if (fac.getCodeCli().equals(codeCli)) cles.remove();
		}
	}
	
}
