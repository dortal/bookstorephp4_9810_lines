package com.accor.asa.commun.reference.metier;


@SuppressWarnings("serial")
public class TypeActionRefBean extends RefBean {

	protected boolean dispoCompte = false;
	protected boolean defautCompte = false;
	protected boolean dispoContact = false;
	protected boolean defautContact = false;
	protected boolean dispoOffre = false;
	protected boolean defautOffre = false;
	protected boolean dispoDossier = false;
	protected boolean defautDossier = false;

	public TypeActionRefBean () {
	}

	public boolean getDispoCompte () {
		return dispoCompte;
	}

	public boolean getDefautCompte () {
		return defautCompte;
	}

	public boolean getDispoContact () {
		return dispoContact;
	}

	public boolean getDefautContact () {
		return defautContact;
	}

	public boolean getDispoOffre () {
		return dispoOffre;
	}

	public boolean getDefautOffre () {
		return defautOffre;
	}

	public boolean getDispoDossier () {
		return dispoDossier;
	}

	public boolean getDefautDossier () {
		return defautDossier;
	}

	public void setDispoCompte (boolean dispoCompte) {
		this.dispoCompte = dispoCompte;
	}

	public void setDefautCompte (boolean defautCompte) {
		this.defautCompte = defautCompte;
	}

	public void setDispoContact (boolean dispoContact) {
		this.dispoContact = dispoContact;
	}

	public void setDefautContact (boolean defautContact) {
		this.defautContact = defautContact;
	}

	public void setDispoOffre (boolean dispoOffre) {
		this.dispoOffre = dispoOffre;
	}

	public void setDefautOffre (boolean defautOffre) {
		this.defautOffre = defautOffre;
	}

	public void setDispoDossier (boolean dispoDossier) {
		this.dispoDossier = dispoDossier;
	}

	public void setDefautDossier (boolean defautDossier) {
		this.defautDossier = defautDossier;
	}

}