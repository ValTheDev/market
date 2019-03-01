package Base;

import java.io.*;

public abstract class ArticleAbstrait implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public ArticleAbstrait(int code, String designation, float pu) {
		this.code = code;
		this.designation = designation;
		this.pu = pu;
	}
	
	public ArticleAbstrait() {}
	
	protected int code;
	protected String designation;
	protected float pu;
	
	public int getCode() {return this.code;}
	public String getDesignation() {return this.designation;}
	public float getPu() {return this.pu;}
	
	public void setCode(int code) {this.code = code;}
	public void setDesignation(String designation) {this.designation = designation;}
	public void setPu(float pu) {this.pu = pu;}
	
	public abstract String toString();
	public abstract String facturer(int i, Object...obj);
	public abstract float prixTotal(int i, Object...obj);

}
