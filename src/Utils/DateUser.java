package Utils;

import java.util.Calendar;
import java.io.*;

public class DateUser implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int jour;
	private int mois;
	private int annee;
	
	public DateUser (int jour, int mois, int annee) {
		this.jour = jour;
		this.mois = mois;
		this.annee = annee;
	}
	
	public DateUser() {
		Calendar d = Calendar.getInstance();
		this.jour = d.get(Calendar.DAY_OF_MONTH);
		this.mois = d.get(Calendar.MONTH) + 1;
		this.annee = d.get(Calendar.YEAR);
	}
	
	public int getJour() {return this.jour;}
	public int getMois() {return this.mois;}
	public int getAnnee() {return this.annee;}
	
	public void setJour(int jour) {this.jour = jour;}
	public void setMois(int mois) {this.mois = mois;}
	public void setAnnee(int annee) {this.annee = annee;}
	
	public String toString() {return jour + "/" + mois + "/" + annee + "\n";}
	
// 	Methodes elementaires	
	
	public boolean valiDate() {
		return this.jour <= nbMaxJourDuMois(this.mois, this.annee);
	}
	
	static int nbMaxJourDuMois (int mois, int annee) {
		switch(mois) {
		case 4: case 6: case 9: case 11: return 30;
		case 2: if (bissextile(annee)) return 29; else return 28;
		default: return 31;
		}
	}
	
	static boolean bissextile(int annee) {
		return (annee%400==0) || (annee%4==0 && annee%100!=0);
	}
	
// 	Methodes Superette
	
	public boolean semainePlus() {
		DateUser today = new DateUser();
		DateUser dateFac = this;
		dateFac.ajouterJours(7);
		return dateFac.avant(today);
	}
	
	public void ajouterJours(int nbJour) {
		do {
			int joursRestant = nbMaxJourDuMois(this.mois, this.annee) - this.jour;
			if (nbJour <= joursRestant) {
				this.jour += joursRestant;
				nbJour -= joursRestant;
			}
			else {
				this.jour = 1;
				this.mois++;
				if (mois>12) {
					this.mois = 1;
					this.annee++;
					nbJour -= joursRestant+1;
				}
			}
		}while(nbJour>0);
	}
	
	public boolean avant(DateUser d) {
		if (this.annee<d.annee) return true;
		else if (this.annee == d.annee) {
			if (this.mois < d.mois) return true;
			else if (this.mois == d.mois) {
				if (this.jour < d.jour) return true;
				else return false;
			}
			else return false;
		}
		else return false;
	}
	
// 	Verification de validité dans un créneau
	
	public boolean validite(DateUser debut, DateUser fin) {
		return (this.validApres(debut) && this.validAvant(fin));
	}
	
	public boolean validApres(DateUser debut) {
		if (this.annee>debut.annee) return true;
		else if (this.annee == debut.annee) {
			if (this.mois > debut.mois) return true;
			else if (this.mois == debut.mois) {
				if (this.jour >= debut.jour) return true;
				else return false;
			}
			else return false;
		}
		else return false;
	}
	
	public boolean validAvant(DateUser fin) {
		if (this.annee<fin.annee) return true;
		else if (this.annee == fin.annee) {
			if (this.mois<fin.mois) return true;
			else if (this.mois == fin.mois) {
				if (this.jour <= fin.jour) return true;
				else return false;
			}
			else return false;
		}
		else return false;
	}

// 	Methodes Client DateUser
	
	public void lendemain() {
		jour++;
		if(jour>nbMaxJourDuMois(mois, annee)) {
			jour = 1;
			mois ++;
			if(mois>12) {
				mois = 1;
				annee++;
			}
		}					
	}
	
	public DateUser lendemain2() {
		DateUser dat = this;
		dat.lendemain();
		return dat;
	}
	
	public void hier() {
		jour--;
		if(jour<1) {
			mois--;
			if(mois<1) {
				annee--;
				mois = 12;
			}
			jour = nbMaxJourDuMois(mois, annee);
		}
	}
	
	public DateUser hier2() {
		DateUser dat = this;
		dat.hier();
		return dat;
	}
	
	public int age() {
		DateUser d = new DateUser();
		int data = d.annee - this.annee;
		if (d.mois<this.mois) {
			data--;
			return data;
			}
		else if (d.mois==this.mois) {
			if(d.jour<this.jour) data--;			
		}
		return data;
	}
	
	public String zeller(int jour, int mois, int annee) {
		int anneeZ = this.annee;
		int mZ = this.mois - 2;
		if (mZ<1) {
			mZ += 12;
			anneeZ --;
		}
		int jj = this.jour;
		int aZ = anneeZ%100;
		int sZ = (int)anneeZ/100;
	    int data = ((int)(2.6*mZ-0.2)+jj+aZ+(int)(aZ/4)+(int)(sZ/4)-2*sZ)%7;
	    switch (data) {
	    case 0 : return "dimanche";
	    case 1 : return "lundi";
	    case 2 : return "mardi";
	    case 3 : return "mercredi";
	    case 4 : return "jeudi";
	    case 5 : return "vendredi";
	    case 6 : return "samedi";
	    default : return "un jour inconnu";
	    }
	}

}