package com.accor.asa.commun.habilitation.metier.visibilite;

import java.util.List;

import com.accor.asa.commun.metier.Element;


/**
 * Title:        Projet Asa
 *   Classe représentant un axe de visibilite (CodeAxe, NomAxe)
 * Copyright:    Copyright (c) 2001
 * Company:      Valtech
 * @author David Dreistadt
 * @version 1.0
 */

@SuppressWarnings("serial")
public class AxeVisibilite extends Element {

	/**
	 * regle de calcul R1, R3, ...
	 */
	private List<TypeVisibilite> 	typeValeurs 		= null;
	private String 					regleHabilitation	= null; 
	private boolean					axeVente			= false;
	private	boolean					axeMultiType		= false;

	
  public String toString() {
	StringBuffer sb = new StringBuffer();
	sb.append( super.toString() );
	sb.append( "[regleHabilitation=" ).append( regleHabilitation ).append( "]," );
	sb.append( "[axeVente=" ).append( axeVente ).append( "]," );
	sb.append( "[axeMultiType=" ).append( axeMultiType ).append( "]," );
	sb.append( "[typeValeurs=" );
	if( typeValeurs != null ) {
		for( int i=0, size=typeValeurs.size(); i<size; i++ ) {
			sb.append( typeValeurs.get(i) ).append( "," );
		}
	}
	sb.append( "]," );
	return sb.toString();
  }
  
  public AxeVisibilite() {
  }

  public AxeVisibilite( String code, String libelle ) {
    super(code, libelle);
  }

  public AxeVisibilite( String code, String libelle, List<TypeVisibilite> typeValeurs ) {
    super(code, libelle);
    this.typeValeurs = typeValeurs;
  }

  public AxeVisibilite( String code, String libelle, String regle ) {
    super(code, libelle);
    if ( regle == null || regle.length() ==  0)
        throw new NullPointerException("regle d'axe non définie");
    regleHabilitation = regle;
  }

  public AxeVisibilite( String code, String libelle, String regle, boolean axeVente ) {
	super( code, libelle );
    if ( regle == null || regle.length() ==  0)
        throw new NullPointerException("regle d'axe non définie");
	this.axeVente = axeVente;   
  }

  public String getRegleHabilitation() {
    return regleHabilitation;
  }

  public boolean isAxeVente() {
	return axeVente;
  }

  public void setAxeVente(boolean axeVente) {
	this.axeVente = axeVente;
  }

  public void setRegleHabilitation(String regleHabilitation) {
	this.regleHabilitation = regleHabilitation;
  }

public List<TypeVisibilite> getTypeValeurs() {
	return typeValeurs;
}

public void setTypeValeurs(List<TypeVisibilite> typeValeurs) {
	this.typeValeurs = typeValeurs;
}

public boolean isAxeMultiType() {
	return axeMultiType;
}

public void setAxeMultiType(boolean axeMultiType) {
	this.axeMultiType = axeMultiType;
}
}