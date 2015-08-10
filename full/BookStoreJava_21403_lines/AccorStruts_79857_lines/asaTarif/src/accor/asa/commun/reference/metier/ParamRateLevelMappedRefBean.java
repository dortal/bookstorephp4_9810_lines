package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.List;

import com.accor.asa.commun.metier.categorie.AsaCategory;

@SuppressWarnings("serial")
public class ParamRateLevelMappedRefBean  extends RefBean{
	
	private String codeAsaCategory;
	private int idFamilleTarif;
	private int idPeriodeValidite;
	
	private List<String> codesRateLevel=new ArrayList<String>(); 

	private AsaCategory asaCategory;
	private FamilleTarifRefBean familleTarif;
	private PeriodeValiditeRefBean periodeValidite;

	public int getIdFamilleTarif() {
		return idFamilleTarif;
	}

	public void setIdFamilleTarif(int idFamilleTarif) {
		this.idFamilleTarif = idFamilleTarif;
		
	}

	public int getIdPeriodeValidite() {
		return idPeriodeValidite;
	}

	public void setIdPeriodeValidite(int idPeriodeValidite) {
		this.idPeriodeValidite = idPeriodeValidite;
	}

	public void setAsaCategory(AsaCategory asaCategory) {
		this.asaCategory = asaCategory;
		if(asaCategory==null)
			setCodeAsaCategory(null);
		else
			setCodeAsaCategory(asaCategory.getCode());
	}

	public FamilleTarifRefBean getFamilleTarif() {
		return familleTarif;
	}

	public void setFamilleTarif(FamilleTarifRefBean familleTarif) {
		this.familleTarif = familleTarif;
		if(familleTarif==null)
			setIdFamilleTarif(-1);
		else
			setIdFamilleTarif(Integer.parseInt(familleTarif.getId()));
	}

	public PeriodeValiditeRefBean getPeriodeValidite() {
		return periodeValidite;
	}

	public void setPeriodeValidite(PeriodeValiditeRefBean periodeValidite) {
		this.periodeValidite = periodeValidite;
		if(periodeValidite==null)
			setIdPeriodeValidite(-1);
		else
			setIdPeriodeValidite(Integer.parseInt(periodeValidite.getCode()));
	}

	public String getCodeAsaCategory() {
		return codeAsaCategory;
	}

	public void setCodeAsaCategory(String codeAsacategory) {
		this.codeAsaCategory = codeAsacategory;
	}

	public String getCode() {
		return buildCompositeCode(getCodeAsaCategory(),getIdFamilleTarif(), getIdPeriodeValidite());
	}

	

	public ParamRateLevelMappedRefBean(String codeAsacategory, int idFamilleTarif, int idPeriodeValidite) {
		super();
		this.codeAsaCategory = codeAsacategory;
		this.idFamilleTarif = idFamilleTarif;
		this.idPeriodeValidite = idPeriodeValidite;
	}

	public List<String> getCodesRateLevels() {
		return codesRateLevel;
	}



	public AsaCategory getAsaCategory() {
		return asaCategory;
	}

	public ParamRateLevelMappedRefBean() {
		super();
	}

	public void addRateLevel(String rateLeveCode)
	{
		codesRateLevel.add(rateLeveCode);
	}
	
	public static String buildCompositeCode(String codeAsaCategory, int idFamilleTarif, int idPeriodeValidite)
	{
		return codeAsaCategory + "_" + idFamilleTarif + "_" + idPeriodeValidite;
	}
}
