package com.accor.asa.commun.reference.metier;


@SuppressWarnings("serial")
public class TemplateRefBean extends RefBean {

	private String nomDocument = null;
	private String typeDocument = null;
	private String version = null;
	private String dateVersion = null;

	private String idPays = null;
	private String libellePays = null;

	private boolean ohActive = false;

	private String idGroupeOffre = null;
	private String typesOffre = null;

	private String statuts = null;
	private String idStatutCible = null;
	private String libelleStatutCible = null;

	private String statutOhCible = null;
	private String typeAffichageTarif = null;

	public TemplateRefBean () {
	}

	public String getNomDocument () {
		return nomDocument;
	}

	public String getTypeDocument () {
		return typeDocument;
	}

	public String getVersion  () {
		return version;
	}

	public String getDateVersion  () {
		return dateVersion;
	}

	public String getIdPays () {
		return idPays;
	}

	public String getLibellePays () {
		return libellePays;
	}

	public boolean getOhActive () {
		return ohActive;
	}

	public String getIdGroupeOffre () {
		return idGroupeOffre;
	}

	public String getTypesOffre () {
		return typesOffre;
	}

	public String getStatuts () {
		return statuts;
	}

	public String getIdStatutCible () {
		return idStatutCible;
	}

	public String getLibelleStatutCible () {
		return libelleStatutCible;
	}

	public String getStatutOhCible () {
		return statutOhCible;
	}

	public String getTypeAffichageTarif () {
		return typeAffichageTarif;
	}

	public void setNomDocument (String nomDocument) {
		this.nomDocument = nomDocument;
	}

	public void setTypeDocument (String typeDocument) {
		this.typeDocument = typeDocument;
	}

	public void setVersion (String version) {
		this.version = version;
	}

	public void setDateVersion (String dateVersion) {
		this.dateVersion = dateVersion;
	}

	public void setIdPays (String idPays) {
		this.idPays = idPays;
	}

	public void setLibellePays (String libellePays) {
		this.libellePays = libellePays;
	}

	public void setOhActive (boolean ohActive) {
		this.ohActive = ohActive;
	}

	public void setIdGroupeOffre (String idGroupeOffre) {
		this.idGroupeOffre = idGroupeOffre;
	}

	public void setTypesOffre (String typesOffre) {
		this.typesOffre = typesOffre;
	}

	public void setStatuts (String statuts) {
		this.statuts = statuts;
	}

	public void setIdStatutCible (String idStatutCible) {
		this.idStatutCible = idStatutCible;
	}

	public void setLibelleStatutCible (String libelleStatutCible) {
		this.libelleStatutCible = libelleStatutCible;
	}

	public void setStatutOhCible (String statutOhCible) {
		this.statutOhCible = statutOhCible;
	}

	public void setTypeAffichageTarif (String typeAffichageTarif) {
		this.typeAffichageTarif = typeAffichageTarif;
	}

}