package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.metier.VenteGroupeOffre;
import com.accor.asa.commun.metier.categorie.AsaCategory;

@SuppressWarnings("serial")
public class ParamTarifGORefBean extends RefBean{

	private String codeAsaCategory;
	private int idGroupeOffre;
	private String codeTypeTarif;
	
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
	
	public AsaCategory getAsaCategory() {
		return asaCategory;
	}
	public void setAsaCategory(AsaCategory asaCategory) {
		this.asaCategory = asaCategory;
		if(asaCategory==null)
			setCodeAsaCategory(null);
		else
			setCodeAsaCategory(asaCategory.getCode());
	}
	public VenteGroupeOffre getGroupeOffre() {
		return groupeOffre;
	}
	public void setGroupeOffre(VenteGroupeOffre groupeOffre) {
		this.groupeOffre = groupeOffre;
		if(groupeOffre==null)
			setIdGroupeOffre(-1);
		else
			setIdGroupeOffre(groupeOffre.getCode());
	}
	
	
	
	public String getCode() {
		return getCodeAsaCategory()+"_"+getIdGroupeOffre()+"_"+getCodeTypeTarif();
	}
	
	public String getCodeTypeTarif() {
		return codeTypeTarif;
	}
	public void setCodeTypeTarif(String codeTypeTarif) {
		this.codeTypeTarif = codeTypeTarif;
	}
	
	
}
