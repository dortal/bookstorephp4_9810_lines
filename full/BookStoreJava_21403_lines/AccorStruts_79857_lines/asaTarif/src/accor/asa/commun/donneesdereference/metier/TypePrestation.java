package com.accor.asa.commun.donneesdereference.metier;

import javax.servlet.http.HttpServletRequest;

public class TypePrestation extends DonneeReference implements Comparable<TypePrestation> {
    
	protected String 	codeGroupe	= null;
	protected boolean	business	= false;
	protected boolean	leisure		= false;
    
    public DonneeReference getInstance(HttpServletRequest req) {
        return null;
    }    
    
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "[code=" ).append( code ).append( "]," );
		sb.append( "[libelle=" ).append( libelle ).append( "]," );
		sb.append( "[codeGroupe=" ).append( codeGroupe ).append( "]," );
		sb.append( "[business=" ).append( business ).append( "]," );
		sb.append( "[leisure=" ).append( leisure ).append( "]," );
		return sb.toString();
	}

    public int compareTo(TypePrestation obj) {
        return this.libelle.compareToIgnoreCase(obj.libelle);
    }

    public String getCodeGroupe() {
        return codeGroupe;
    }

    public void setCodeGroupe(String i) {
    	codeGroupe = i;
    }

	public boolean isBusiness() {
		return business;
	}

	public void setBusiness(boolean business) {
		this.business = business;
	}

	public boolean isLeisure() {
		return leisure;
	}

	public void setLeisure(boolean leisure) {
		this.leisure = leisure;
	}
}
