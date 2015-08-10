package com.accor.asa.commun.reference.metier;


@SuppressWarnings("serial")
public class TypePrestationRefBean extends RefBean {

	private String idGroupePrestation = null;
	private String libelleGroupePrestation = null;
	private boolean affaire = false;
	private boolean loisir = false;

	public TypePrestationRefBean () {
	}

	public String getIdGroupePrestation () {
		return idGroupePrestation;
	}

	public String getLibelleGroupePrestation () {
		return libelleGroupePrestation;
	}

	public boolean getAffaire () {
		return affaire;
	}

	public boolean getLoisir () {
		return loisir;
	}

	public void setIdGroupePrestation (String idGroupePrestation) {
		this.idGroupePrestation = idGroupePrestation;
	}

	public void setLibelleGroupePrestation (String libelleGroupePrestation) {
		this.libelleGroupePrestation = libelleGroupePrestation;
	}

	public void setAffaire (boolean affaire) {
		this.affaire = affaire;
	}

	public void setLoisir (boolean loisir) {
		this.loisir = loisir;
	}

}