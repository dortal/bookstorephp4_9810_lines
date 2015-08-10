package com.accor.asa.commun.metier.donneesdereference;

import com.accor.asa.commun.metier.Element;

@SuppressWarnings("serial")
public class Pays extends Element {

	protected Integer flagEtat = null;
	protected String  indicatifTelephone = null;
	protected String  indicatifTelex = null;
	
	public Integer getFlagEtat() {
		return flagEtat;
	}
	public void setFlagEtat(Integer flagEtat) {
		this.flagEtat = flagEtat;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "{" ).append( code );
		sb.append( " - " ).append( libelle );
		sb.append( " - " ).append( flagEtat ).append( "}" );
		return sb.toString();
	}
	public String getIndicatifTelephone() {
		return indicatifTelephone;
	}
	public void setIndicatifTelephone(String indicatifTelephone) {
		this.indicatifTelephone = indicatifTelephone;
	}
	public String getIndicatifTelex() {
		return indicatifTelex;
	}
	public void setIndicatifTelex(String indicatifTelex) {
		this.indicatifTelex = indicatifTelex;
	}
	public Pays() {
		super();
	}
	public Pays(String code, String libelle) {
		super(code, libelle);
	}
	
	public boolean isCountryWithState() {
		return ( new Integer( 1 ) ).equals( flagEtat );
	}
}
