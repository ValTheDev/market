package Interfaces;

public interface InterfaceStructure <TypeMetier, TypeCle> {
	
	public void ajouter(TypeMetier metier);
	public void supprimer(TypeCle cle);
	public TypeMetier retourner(TypeCle cle);
	public int taille();
	
}
