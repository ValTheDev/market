package Base;

import Utils.DateUser;
import java.io.*;

public class UneFacture53 <TypeCode> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private TypeCode numFacture;
	private String codeCli;
	private String edition;
	private DateUser dateFacture;
	private float valeurFacture;
	
	public UneFacture53(TypeCode numFacture, String edition, String codeCli, float valeurFacture) {
		this.numFacture = numFacture; this.edition = edition; this.codeCli = codeCli; this.valeurFacture = valeurFacture; this.dateFacture = new DateUser();
	}
	
	public TypeCode getNumFacture() {return this.numFacture;}
	public String getEdition() {return this.edition;}
	public DateUser getDateFacture() {return this.dateFacture;}
	public String getCodeCli() {return this.codeCli;}
	public float getValeurFacture() {return this.valeurFacture;}
	
	public void setEdition(String edition) {this.edition = edition;}
	
	public String toString() {
		return this.edition;
	}
	
	public boolean estSupprimable() {
		return this.dateFacture.semainePlus();
	}	

}
