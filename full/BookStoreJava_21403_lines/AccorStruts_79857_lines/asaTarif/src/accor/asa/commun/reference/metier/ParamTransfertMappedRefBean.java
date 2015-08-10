package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.List;

import com.accor.asa.commun.metier.categorie.AsaCategory;

@SuppressWarnings("serial")
public class ParamTransfertMappedRefBean extends RefBean {

	private int idFamilleTarif;
	private int idPeriodeValidite;
	private PeriodeValiditeRefBean periodeValidite;
	private FamilleTarifRefBean familleTarif;

	private List<AsaCategory> asaCategories = new ArrayList<AsaCategory>();

	public PeriodeValiditeRefBean getPeriodeValidite() {
		return periodeValidite;
	}

	public FamilleTarifRefBean getFamilleTarif() {
		return familleTarif;
	}

	public List<AsaCategory> getAsaCategories() {
		return asaCategories;
	}

	public void setAsaCategories(List<AsaCategory> asaCategories) {
		this.asaCategories = asaCategories;
	}

	public void setFamilleTarif(FamilleTarifRefBean familleTarif) {
		this.familleTarif = familleTarif;
		if (familleTarif == null)
			setIdFamilleTarif(-1);
		else
			setIdFamilleTarif(Integer.parseInt(familleTarif.getId()));
	}

	public void setPeriodeValidite(PeriodeValiditeRefBean periodeValidite) {
		this.periodeValidite = periodeValidite;
		if (periodeValidite == null)
			setIdPeriodeValidite(-1);
		else
			setIdPeriodeValidite(Integer.parseInt(periodeValidite.getCode()));
	}

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

	public void addAsaCategory(AsaCategory asa) {
		asaCategories.add(asa);
	}

	public String getCode() {
		return getIdFamilleTarif() + "_" + getIdPeriodeValidite();
	}

}
