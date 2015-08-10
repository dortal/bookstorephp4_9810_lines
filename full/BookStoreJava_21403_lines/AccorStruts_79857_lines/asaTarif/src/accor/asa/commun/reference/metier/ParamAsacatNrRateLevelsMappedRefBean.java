package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.List;

import com.accor.asa.commun.metier.categorie.AsaCategory;

@SuppressWarnings("serial")
public class ParamAsacatNrRateLevelsMappedRefBean extends RefBean{

	private int idFamilleTarif;
	private List<AsaCategory> asaCategories;
	private int nrRateLevels ;
	private int idPeriodeValidite;
	
	private FamilleTarifRefBean familleTarif;
	private PeriodeValiditeRefBean periodeValidite;
	
	public int getIdFamilleTarif() {
		return idFamilleTarif;
	}
	public void setIdFamilleTarif(int idFamilleTarif) {
		this.idFamilleTarif = idFamilleTarif;
	}
	public List<AsaCategory> getAsaCategories() {
		return asaCategories;
	}
	public void setAsaCategories(List<AsaCategory> asaCategories) {
		this.asaCategories = asaCategories;
	}
	public int getNrRateLevels() {
		return nrRateLevels;
	}
	public void setNrRateLevels(int nrRateLevels) {
		this.nrRateLevels = nrRateLevels;
	}
	
	public String getCode() {
		return getIdFamilleTarif()+"_"+getIdPeriodeValidite()+"_"+getNrRateLevels();
	}
	
	
	
	public void addAsaCategory(AsaCategory asa)
	{
		if(asaCategories==null)
			asaCategories = new ArrayList<AsaCategory>();
		asaCategories.add(asa);
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
