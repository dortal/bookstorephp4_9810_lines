package com.accor.asa.commun.reference.metier;

@SuppressWarnings("serial")
public class ParamAsacatNrRateLevelsRefBean extends RefBean{
	
	private String codeAsaCategory;
	private int idFamilleTarif;
	private int idPeriodeValidite;
	private int nrRateLevels;
	
	private FamilleTarifRefBean familleTarif;
	private PeriodeValiditeRefBean periodeValidite;
	
	
	public String getCodeAsaCategory() {
		return codeAsaCategory;
	}
	public void setCodeAsaCategory(String codeAsaCategory) {
		this.codeAsaCategory = codeAsaCategory;
	}
	public int getIdFamilleTarif() {
		return idFamilleTarif;
	}
	public void setIdFamilleTarif(int idFamilleTarif) {
		this.idFamilleTarif = idFamilleTarif;
	}
	public int getNrRateLevels() {
		return nrRateLevels;
	}
	public void setNrRateLevels(int nrRateLevels) {
		this.nrRateLevels = nrRateLevels;
	}
	public String getId() {
		return getCodeAsaCategory()+"_"+getIdFamilleTarif()+"_"+getIdPeriodeValidite();
	}
	public String getCode() {
		return getCode();
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
	public int getIdPeriodeValidite() {
		return idPeriodeValidite;
	}
	public void setIdPeriodeValidite(int idPeriodeValidite) {
		this.idPeriodeValidite = idPeriodeValidite;
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
	
	
	

}
