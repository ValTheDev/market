package Base;

import java.io.*;

public class Ldc53 implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int code;
	private int quantite;
	
	public Ldc53(int code, int quantite) {
		this.code = code;
		this.quantite = quantite;
	}
	
	public Ldc53() {}
	
	public int getCode() {return code;}
	public int getQuantite() {return quantite;}
	
	public void setCode(int code) {this.code = code;}
	public void setQuantite(int quantite) {this.quantite=quantite;}
	
	public String toString() {
		return "\n*** " + code + " *** " + quantite +" ***";
	}
	
	public void augmenterQuantite(int quantite) {this.quantite += quantite;}
}