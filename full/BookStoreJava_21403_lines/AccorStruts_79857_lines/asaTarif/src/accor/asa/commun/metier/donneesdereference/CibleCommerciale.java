package com.accor.asa.commun.metier.donneesdereference;

import com.accor.asa.commun.metier.Element;

/**
 * @description   Classe contenant des infos sur la Cible Commerciale
 * @pattern       enum
 * @author        A.CHETBANI
 * @extends       (facultatif)
 * @creation date 18/4/2001
 * @Modifications
 */

@SuppressWarnings("serial")
public class CibleCommerciale extends Element{

  /**
  *  paramètres.
  */
  String typeCompte ;
  String idMarche;
  String nomMarche;
	
  public String getIdMarche() {
		return idMarche;
	}
	public void setIdMarche(String idMarche) {
		this.idMarche = idMarche;
	}
	public String getTypeCompte() {
		return typeCompte;
	}
	public void setTypeCompte(String typeCompte) {
		this.typeCompte = typeCompte;
	}
	public String getNomMarche() {
		return nomMarche;
	}
	public void setNomMarche(String nomMarche) {
		this.nomMarche = nomMarche;
	}
}