package IConsole;

import java.util.*;
import Utils.DateUser;
import MesExceptions.AbandonException;

public class ES {
	
	Scanner scan = new Scanner (System.in);
	
	public void affiche (String mes) {System.out.println(mes);}
	
	public int saisie(String mes, int mini, int maxi)  throws AbandonException {
		
		int data;
		do {
			affiche(mes);
			try {
				data = scan.nextInt();
				if (data >= mini && data <= maxi) return data;
				throw new AbandonException();
			}
			
			catch (InputMismatchException ie) {
				affiche("SAISIE NON NUMERIQUE RESSAISSSEZ SVP\n");
				scan.nextLine();
			}
			
			catch(AbandonException ae) {
				boolean abandon = saisieOuiNon("VOULEZ-VOUS ABANDONNER ? (O/N) ");
				if (abandon) throw ae;
				else affiche("HORS INTERVALLE " +mini +" et " +maxi +" , RESSAISISSEZ SVP \n");
			}
			
		}while(true);
	}
	
	public float saisie(String mes, float mini, float maxi) throws AbandonException {
		
		float data;
		do {
			affiche(mes);
			try {
				data = scan.nextFloat();
				if (data >= mini && data <= maxi) return data;
				throw new AbandonException();
			}
			
			catch (InputMismatchException ie) {
				affiche("SAISIE NON NUMERIQUE RESSAISSSEZ SVP\n");
			}
			
			catch(AbandonException ae) {
				boolean abandon = saisieOuiNon("VOULEZ-VOUS ABANDONNER ? (O/N) ");
				if (abandon) throw ae;
				affiche("HORS INTERVALLE " +mini +" et " +maxi +" , RESSAISISSEZ SVP");
			}
			
		}while(true);
	}
	
	public int saisie(String mes, int mini) throws AbandonException {
		do {
			affiche(mes);
			try {
				int data;
				data = scan.nextInt();
				if (data >= mini) return data;
				throw new AbandonException();
			}
			catch (InputMismatchException ie) {
				affiche("SAISIE NON VALIDE");
				scan.nextLine();
			}
			catch (AbandonException ae) {
				boolean abandon = saisieOuiNon("VOULEZ-VOUS ABANDONNER ? (O/N) ");
				if (abandon) throw ae;
				affiche("VALEUR INFERIEURE A " +mini +", RESSAISISSEZ SVP");
			}
		}while(true);
	} 
	
	public float saisie(String mes, float mini) throws AbandonException {
		do {	
			affiche(mes);
			try {
				float data;
				data = scan.nextFloat();
				if (data >= mini) return data;
				throw new AbandonException();
			}
			catch (InputMismatchException ie) {
				affiche("SAISIE NON NUMERIQUE");
				scan.nextLine();
			}
			catch (AbandonException ae) {
				boolean abandon = saisieOuiNon("VOULEZ-VOUS ABANDONNER ? (O/N) ");
				if (abandon) throw ae;
				affiche("VALEUR INFERIEURE A " +mini +", RESSAISISSEZ SVP");
			}
		}while(true);
	} 
	
	public String saisie(String mes) throws AbandonException{
		do{
			affiche(mes);
			scan.nextLine();
			String saisie = scan.nextLine();
			if (saisie.contentEquals("")) {
				boolean abandon = saisieOuiNon("VOULEZ VOUS ABANDONNER (O/N)");
				if (abandon) throw new AbandonException();
			}
			return saisie;
		}while(true);
	}
	
	public boolean saisieOuiNon(String mes) {
		affiche(mes);
		char c = scan.next().charAt(0);
		if (c == 'O' || c == 'o') return true;
		else return false;
	}
	
	public DateUser saisieDate(String mes) throws AbandonException {
		affiche(mes);
		DateUser date;
		do {
			int jour = saisie("JOUR : ", 1, 31);
			int mois = saisie("MOIS : ", 1, 12);
			int annee = saisie("ANNEE : ", 2018, Integer.MAX_VALUE);
			date = new DateUser(jour, mois, annee);
			if (date.valiDate() == false) affiche("DATE NON VALIDE, VEUILLEZ RECOMMENCER SVP");
		}while (date.valiDate() == false);
		return date;		
	}
	
}
