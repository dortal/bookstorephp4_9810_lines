package com.accor.asa.commun.reference.metier;


@SuppressWarnings("serial")
public class MiseADispoChambreRefBean extends RefBean {

	private boolean allotement = false;
	private boolean tauxMaterialisation = false;
	private boolean defaut = false;
	private boolean inactif = false;

	public MiseADispoChambreRefBean () {
	}

	public boolean getAllotement () {
		return allotement;
	}

	public boolean getTauxMaterialisation () {
		return tauxMaterialisation;
	}

	public boolean getDefaut () {
		return defaut;
	}

	public boolean getInactif () {
		return inactif;
	}

	public void setAllotement (boolean allotement) {
		this.allotement = allotement;
	}

	public void setTauxMaterialisation (boolean tauxMaterialisation) {
		this.tauxMaterialisation = tauxMaterialisation;
	}

	public void setDefaut (boolean defaut) {
		this.defaut = defaut;
	}

	public void setInactif (boolean inactif) {
		this.inactif = inactif;
	}

}