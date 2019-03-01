package Base;

import java.util.*;
import java.io.*;
import Interfaces.InterfaceStructure;

public class TableDesClients implements InterfaceStructure<Client, String>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private TreeMap<String, Client> tabCli = new TreeMap<String, Client>();
	
	public TableDesClients() {
		Client c1 = new Client("KA", "JEAN", "ka-1", "PARIS");
		Client c2 = new Client("OUABASA", "JEAN-CRISTOPHE", "oua1", "TOULOUSE");
		Client c3 = new Client("OUABASA", "JEAN-CHARLES", "oua2", "MONTPELLIER");
		
		ajouter(c1);
		ajouter(c2);
		ajouter(c3);
	}
	
	public String toString() {
		String st = "\n *** LISTE DES CLIENTS *** \n";
		for (Client cli : tabCli.values()) {
			st += "\n" + cli.toString();
		}
		return st;
	}
	
	public void ajouter(Client cli) {
		tabCli.put(cli.getCodeCli(), cli);
	}
	
	public Client retourner(String codeCli) {
		return tabCli.get(codeCli);
	}
	
	public void supprimer(String codeCli) {
		tabCli.remove(codeCli);
	}
	
	public int taille() {
		return tabCli.size();
	}
	
	public String cle() {
		String st = " *** LISTE DES NUMEROS DE CLIENT *** \n";
		for (Client cli : tabCli.values()) {
			st += "\n" + cli.getCodeCli();
		}
		return st;
	}

}
