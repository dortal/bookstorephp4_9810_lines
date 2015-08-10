package com.accor.asa.commun.reference.metier;


@SuppressWarnings("serial")
public class TypePetitDejRefBean extends RefBean {

	private String description = null;
	private String codeTars = null;

	public TypePetitDejRefBean () {
	}

	public String getDescription () {
		return description;
	}

	public String getCodeTars () {
		return codeTars;
	}

	public void setDescription (String description) {
		this.description = description;
	}

	public void setCodeTars (String codeTars) {
		this.codeTars = codeTars;
	}

}