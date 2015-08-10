package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;

import com.accor.asa.commun.metier.VenteGroupeOffre;
import com.accor.asa.commun.metier.categorie.AsaCategory;

@SuppressWarnings("serial")
public class ParamTarifGOMappedRefBean extends RefBean {

	private String codeAsaCategory;
	private int idGroupeOffre;

	private java.util.List<String> codesTypeTarif=new ArrayList<String>();

	private AsaCategory asaCategory;
	private VenteGroupeOffre groupeOffre;

	public String getCodeAsaCategory() {
		return codeAsaCategory;
	}

	public void setCodeAsaCategory(String codeAsaCategory) {
		this.codeAsaCategory = codeAsaCategory;
	}

	public int getIdGroupeOffre() {
		return idGroupeOffre;
	}

	public void setIdGroupeOffre(int idGroupeOffre) {
		this.idGroupeOffre = idGroupeOffre;
	}

	public java.util.List<String> getCodesTypeTarif() {
		return codesTypeTarif;
	}

	public void setCodesTypeTarif(java.util.List<String> codesTypeTarif) {
		this.codesTypeTarif = codesTypeTarif;
	}

	public String getCode() {
		return getCodeAsaCategory() + "_" + getIdGroupeOffre();
	}

	public AsaCategory getAsaCategory() {
		return asaCategory;
	}

	public void setAsaCategory(AsaCategory asaCategory) {
		this.asaCategory = asaCategory;
		if (asaCategory == null)
			setCodeAsaCategory(null);
		else
			setCodeAsaCategory(asaCategory.getCode());
	}

	public void addCodeTypeTarif(String codeTypeTarif) {
	
		codesTypeTarif.add(codeTypeTarif);
	}

	public ParamTarifGOMappedRefBean(AsaCategory asa, VenteGroupeOffre groupeOffre) {
		super();
		setAsaCategory(asa);
		setGroupeOffre(groupeOffre);

	}

	public ParamTarifGOMappedRefBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VenteGroupeOffre getGroupeOffre() {
		return groupeOffre;
	}

	public void setGroupeOffre(VenteGroupeOffre groupeOffre) {
		this.groupeOffre = groupeOffre;
		if (groupeOffre != null)
			this.idGroupeOffre = groupeOffre.getCode();
		else
			this.idGroupeOffre = -1;
	}

}
