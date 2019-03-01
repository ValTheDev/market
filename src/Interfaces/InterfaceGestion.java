package Interfaces;

import MesExceptions.AbandonException;

public interface InterfaceGestion <TypeStructure> {
	
	public void menuGestion(TypeStructure t, Object...objects) throws AbandonException;
	public int menuChoix(Object...objects) throws AbandonException;
	public void ajouter(Object ...objects) throws AbandonException;
	public void supprimer(Object ...objects) throws AbandonException;
	public void afficher(TypeStructure t) throws AbandonException;

}
