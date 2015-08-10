package com.accor.asa.commun.metier;

/**
 * @description   Classe contenant les coordonnees du créature du compte.
 * @pattern       enum
 * @author        A.CHETBANI
 * @extends       (facultatif)
 * @creation date 18/4/2001
 * @Modifications
 */

@SuppressWarnings("serial")
public class Coordinates implements java.io.Serializable{

  /**
  *  paramètres.
  */
  protected String phone;
  protected String fax;
  protected String mail;
  protected String phonePrefix;
  protected String web;
  
  /**
  *  Constructeur.
  */
  public Coordinates() {
  }

  public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( super.toString() );
		sb.append( "[phone=" ).append( phone ).append( "], " );
		sb.append( "[fax=" ).append( fax ).append( "], " );
		sb.append( "[mail=" ).append( mail ).append( "], " );
		sb.append( "[phonePrefix=" ).append( phonePrefix ).append( "], " );
		sb.append( "[web=" ).append( web ).append( "], " );
		return sb.toString();
  }

	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getFax() {
		return fax;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getPhonePrefix() {
		return phonePrefix;
	}
	
	public void setPhonePrefix(String phonePrefix) {
		this.phonePrefix = phonePrefix;
	}
	
	public String getWeb() {
		return web;
	}
	
	public void setWeb(String web) {
		this.web = web;
	}
}