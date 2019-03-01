package Connexion;

import java.io.*;
import IConsole.ES;

public class ConnexionFichier <TypeStructure> {
	
	ES IO = new ES();
	private String nomPhysique;
	
	public ConnexionFichier(String nomPhysique) {
		this.nomPhysique = nomPhysique;
	}
	
	public TypeStructure charger() {
		
		TypeStructure tab = null;
		
		try {
			FileInputStream fis = new FileInputStream(this.nomPhysique);
			ObjectInputStream ois = new ObjectInputStream(fis);
			tab = (TypeStructure)ois.readObject();
			ois.close();
		}
		
		catch (FileNotFoundException fnf) {IO.affiche("FICHIER NOUVEAU... A CREER");}
		catch (IOException ioe) {IO.affiche("PROBLEME DE LECTURE");}
		catch (ClassNotFoundException cnf) {IO.affiche("TABLE NON COMPATIBLE");}
		
		return tab;
	}
	
	public void sauvegarder(TypeStructure tab) {
		try {
			FileOutputStream fos = new FileOutputStream(this.nomPhysique, false);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(tab);
			oos.close();
		}
		catch (IOException ioe) {IO.affiche("PROBLEME D'ECRITURE");}
	}
		
		
	

}  
