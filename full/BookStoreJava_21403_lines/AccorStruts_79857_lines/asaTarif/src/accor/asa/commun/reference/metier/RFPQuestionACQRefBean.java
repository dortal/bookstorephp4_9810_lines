package com.accor.asa.commun.reference.metier;


@SuppressWarnings("serial")
public class RFPQuestionACQRefBean extends RefBean {

	protected String libelleLong = null;
	protected String idTypeQuestion = null;
	protected String libelleTypeQuestion = null;
	protected String idTypeReponse = null;
	protected String libelleTypeReponse = null;
	protected boolean isQuestionParDefaut = false;
	protected String reponseParDefaut = null;

	public RFPQuestionACQRefBean () {
	}

	public String getLibelleLong () {
		return libelleLong;
	}

	public String getIdTypeQuestion () {
		return idTypeQuestion;
	}

	public String getLibelleTypeQuestion () {
		return libelleTypeQuestion;
	}

	public String getIdTypeReponse () {
		return idTypeReponse;
	}

	public String getLibelleTypeReponse () {
		return libelleTypeReponse;
	}

	public boolean getQuestionParDefaut () {
		return isQuestionParDefaut;
	}

	public String getReponseParDefaut () {
		return reponseParDefaut;
	}

	public void setLibelleLong (String libelleLong) {
		this.libelleLong = libelleLong;
	}

	public void setIdTypeQuestion (String idTypeQuestion) {
		this.idTypeQuestion = idTypeQuestion;
	}

	public void setLibelleTypeQuestion (String libelleTypeQuestion) {
		this.libelleTypeQuestion = libelleTypeQuestion;
	}

	public void setIdTypeReponse (String idTypeReponse) {
		this.idTypeReponse = idTypeReponse;
	}

	public void setLibelleTypeReponse (String libelleTypeReponse) {
		this.libelleTypeReponse = libelleTypeReponse;
	}

	public void setQuestionParDefaut (boolean isQuestionParDefaut) {
		this.isQuestionParDefaut = isQuestionParDefaut;
	}

	public void setReponseParDefaut (String reponseParDefaut) {
		this.reponseParDefaut = reponseParDefaut;
	}
}