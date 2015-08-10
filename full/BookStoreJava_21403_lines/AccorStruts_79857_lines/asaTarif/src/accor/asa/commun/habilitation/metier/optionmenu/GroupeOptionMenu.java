package com.accor.asa.commun.habilitation.metier.optionmenu;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Title:        Projet Asa
 * Classe représentant un groupe d'option de menu (CodeGroupeOption, cleInternationalisation)
 * 
 * Copyright:    Copyright (c) 2001
 * Company:      Valtech
 * @author David Dreistadt
 * @version 1.0
 */

public class GroupeOptionMenu implements Serializable{
  private Integer 	codeGroupeOption = null;
  private String 	cleInternationalisation = null;

  public GroupeOptionMenu() {
  }

  public GroupeOptionMenu(Integer code, String cle) {
	codeGroupeOption = code;
    cleInternationalisation = cle;
  }

  public int hashCode() {
    return codeGroupeOption.hashCode();
  }

  public boolean equals(Object obj) {
    if((obj == null) || !(obj instanceof GroupeOptionMenu)) {
        return false;
    }

    GroupeOptionMenu groupe = (GroupeOptionMenu)obj;

    return groupe.getCodeGroupeOption().equals(codeGroupeOption);
  }

  public String toString() {
	    StringBuffer sb = new StringBuffer();
	    sb.append( "[codeGroupeOption=" ).append( codeGroupeOption ).append( "]," );
	    sb.append( "[cleInternationalisation=" ).append( cleInternationalisation ).append( "]," );
	    return sb.toString();
  }

	public Integer getCodeGroupeOption() {
		return codeGroupeOption;
	}
	
	public void setCodeGroupeOption(Integer codeGroupeOption) {
		this.codeGroupeOption = codeGroupeOption;
	}
	
	public void setCodeGroupeOption(int codeGroupeOption) {
		this.codeGroupeOption = new Integer(codeGroupeOption);
	}
	
	public String getCleInternationalisation() {
		return cleInternationalisation;
	}

	public void setCleInternationalisation(String cleInternationalisation) {
		this.cleInternationalisation = cleInternationalisation;
	}

	/***
     * Renvoie l'objet GroupeOption associé au code 
     * @param codeGroupe
     * @return
     */
    public static GroupeOptionMenu getGroupeOptionsMenu( String codeGroupe, 
    		Map<GroupeOptionMenu,List<OptionMenu>> groups ) {
    	
    	Iterator<GroupeOptionMenu> it = groups.keySet().iterator();
    	GroupeOptionMenu paramGroupe = new GroupeOptionMenu( new Integer( codeGroupe ), "" );
    	GroupeOptionMenu currentGroupe;

    	while( it.hasNext() ) {
    		currentGroupe = it.next();
    		if( paramGroupe.equals( currentGroupe ) )
    			return currentGroupe;
    	}
    	
    	return null;
    }
}