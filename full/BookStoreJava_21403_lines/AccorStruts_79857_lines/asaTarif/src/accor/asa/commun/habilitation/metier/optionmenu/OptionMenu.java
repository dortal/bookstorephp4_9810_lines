package com.accor.asa.commun.habilitation.metier.optionmenu;

import java.io.Serializable;

/**
 * Title:        Projet Asa
 * Classe représentant une option du menu de vente (CodeOption, cleInternationalisation)
 * Copyright:    Copyright (c) 2001
 * Company:      Valtech
 * @author David Dreistadt
 * @version 1.0
 */

public class OptionMenu implements Serializable{
	
	private Integer 	codeOption 						= null;
  	private String 		cleInternationalisation 		= null;
  	private Integer		codeGroupeOption 				= null;
  	private String 		cleInternationalisationGroupe 	= null;
  	private boolean		ecranVente = false;
  	private boolean		ecranTarif = false;
  	private boolean		ecranAdmin = false;
  	private boolean		ecranTrans = false;
  
  	public OptionMenu() {}

  	public OptionMenu( Integer code, String cle, Integer codeGroupe, String cleGroupe ) {
	  	codeOption = code;
    	cleInternationalisation = cle;
    	codeGroupeOption = codeGroupe;
    	cleInternationalisationGroupe = cleGroupe;
  	}

  	public OptionMenu( Integer code, String cle, Integer codeGroupe, String cleGroupe, 
		  boolean isEcranVente, boolean isEcranTarif, boolean isEcranAdmin, boolean isEcranTrans ) {
	    codeOption = code;
	    cleInternationalisation = cle;
	    codeGroupeOption = codeGroupe;	
    	cleInternationalisationGroupe = cleGroupe;
	    ecranVente = isEcranVente;
	    ecranTarif = isEcranTarif;
	    ecranAdmin = isEcranAdmin;
	    ecranTrans = isEcranTrans;
  	}

  	public Integer getCodeOption() {
  		return codeOption;
	}

  	public String getCleInternationalisation() {
  		return cleInternationalisation;
	}

  	public Integer getCodeGroupeOption() {
	    return codeGroupeOption;
	}

  	public void setCleInternationalisation(String cleInternationalisation) {
		this.cleInternationalisation = cleInternationalisation;
	}

	public void setCodeGroupeOption(Integer codeGroupeOption) {
		this.codeGroupeOption = codeGroupeOption;
	}

	public void setCodeGroupeOption(int codeGroupeOption) {
		this.codeGroupeOption = new Integer( codeGroupeOption );
	}

	public void setCodeOption(Integer codeOption) {
		this.codeOption = codeOption;
	}
	
	public void setCodeOption(int codeOption) {
		this.codeOption = new Integer(codeOption);
	}

	public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append( "[codeOption=" ).append( codeOption ).append( "]," );
    sb.append( "[cleInternationalisation=" ).append( cleInternationalisation ).append( "]," );
    sb.append( "[codeGroupeOption=" ).append( codeGroupeOption ).append( "]," );
    sb.append( "[cleInternationalisationGroupe=" ).append( cleInternationalisationGroupe ).append( "]," );
    sb.append( "[ecranVente=" ).append( ecranVente ).append( "]," );
    sb.append( "[ecranTarif=" ).append( ecranTarif ).append( "]," );
    sb.append( "[ecranAdmin=" ).append( ecranAdmin ).append( "]," );
    sb.append( "[ecranTrans=" ).append( ecranTrans ).append( "]," );
    return sb.toString();
  }

	public boolean isEcranAdmin() {
		return ecranAdmin;
	}

	public void setEcranAdmin(boolean ecranAdmin) {
		this.ecranAdmin = ecranAdmin;
	}

	public boolean isEcranTarif() {
		return ecranTarif;
	}

	public void setEcranTarif(boolean ecranTarif) {
		this.ecranTarif = ecranTarif;
	}

	public boolean isEcranTrans() {
		return ecranTrans;
	}

	public void setEcranTrans(boolean ecranTrans) {
		this.ecranTrans = ecranTrans;
	}

	public boolean isEcranVente() {
		return ecranVente;
	}

	public void setEcranVente(boolean ecranVente) {
		this.ecranVente = ecranVente;
	}

	public String getCleInternationalisationGroupe() {
		return cleInternationalisationGroupe;
	}

	public void setCleInternationalisationGroupe(
			String cleInternationalisationGroupe) {
		this.cleInternationalisationGroupe = cleInternationalisationGroupe;
	}
}