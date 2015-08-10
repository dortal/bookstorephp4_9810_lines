package com.accor.asa.commun.reference.metier;


@SuppressWarnings("serial")
public class CibleCommercialeRefBean extends RefBean {

	private String idMarche = null;
	private String libelleMarche = null;
	private String idCompte = null;
	private String libelleCompte = null;

	public CibleCommercialeRefBean () {
	}

	public String getIdMarche () {
		return idMarche;
	}

	public String getLibelleMarche () {
		return libelleMarche;
	}

	public String getIdCompte () {
		return idCompte;
	}

	public String getLibelleCompte () {
		return libelleCompte;
	}

	public void setIdMarche (String idMarche) {
		this.idMarche = idMarche;
	}

	public void setLibelleMarche (String libelleMarche) {
		this.libelleMarche = libelleMarche;
	}

	public void setIdCompte (String idCompte) {
		this.idCompte = idCompte;
	}

	public void setLibelleCompte (String libelleCompte) {
		this.libelleCompte = libelleCompte;
	}

}