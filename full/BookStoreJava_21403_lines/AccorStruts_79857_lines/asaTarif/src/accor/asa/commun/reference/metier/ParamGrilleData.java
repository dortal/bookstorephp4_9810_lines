package com.accor.asa.commun.reference.metier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ParamGrilleData implements Serializable{

	//private String codeRateLevel;
	private int idFamilleTarif;
	private Double valueCommission;
	private String uniteCommission;
	private String codeMealPlan;
	private int idDureeSejour;
	private boolean newContrat;
	private boolean blackOutDates;
	
	private boolean onYearOnly;
	private boolean lunWe;
	private boolean marWe;
	private boolean merWe;
	private boolean jeuWe;
	private boolean venWe;
	private boolean samWe;
	private boolean dimWe;
	
	
	private MealPlanRefBean mealPlan;
	
	private List<SupplementRefBean> supplements;
	
	public ParamGrilleData(ParamGrilleRefBean row) {
		idFamilleTarif=row.getIdFamilleTarif();
		valueCommission = row.getValueCommission();
		uniteCommission = row.getUniteCommission();
		codeMealPlan = row.getCodeMealPlan();
		idDureeSejour = row.getIdDureeSejour();
		newContrat=row.isNewContrat();
		blackOutDates = row.isBlackOutDates();
		
		onYearOnly = row.isOneYearOnly();
		lunWe = row.isLunWe();
		marWe = row.isMarWe();
		merWe = row.isMerWe();
		jeuWe = row.isJeuWe();
		venWe = row.isVenWe();
		samWe = row.isSamWe();
		dimWe = row.isDimWe();
		
		supplements = new ArrayList<SupplementRefBean>();
		//codeRateLevel = row.getCodeRateLevel();
	}
	
	public int getIdFamilleTarif() {
		return idFamilleTarif;
	}
	public void setIdFamilleTarif(int idFamilleTarif) {
		this.idFamilleTarif = idFamilleTarif;
	}
    public Double getValueCommission() {
        return valueCommission;
    }
    public void setValueCommission(Double valueCommission) {
        this.valueCommission = valueCommission;
    }
    public String getUniteCommission() {
		return uniteCommission;
	}
	public void setUniteCommission(String uniteCommission) {
		this.uniteCommission = uniteCommission;
	}
	public String getCodeMealPlan() {
		return codeMealPlan;
	}
	public void setCodeMealPlan(String codeMealPlan) {
		this.codeMealPlan = codeMealPlan;
	}
	public int getIdDureeSejour() {
		return idDureeSejour;
	}
	public void setIdDureeSejour(int idDureeSejour) {
		this.idDureeSejour = idDureeSejour;
	}
	public boolean isNewContrat() {
		return newContrat;
	}
	public void setNewContrat(boolean newContrat) {
		this.newContrat = newContrat;
	}
	public boolean isBlackOutDates() {
		return blackOutDates;
	}
	public void setBlackOutDates(boolean blackOutDates) {
		this.blackOutDates = blackOutDates;
	}

	public List<SupplementRefBean> getSupplements() {
		return supplements;
	}

	public void setSupplements(List<SupplementRefBean> supplements) {
		this.supplements = supplements;
	}
	
	public void addSupplement(SupplementRefBean supplement)
	{
		this.supplements.add(supplement);
	}

	public MealPlanRefBean getMealPlan() {
		return mealPlan;
	}

	public void setMealPlan(MealPlanRefBean mealPlan) {
		this.mealPlan = mealPlan;
	}

	public boolean isOnYearOnly() {
		return onYearOnly;
	}

	public void setOnYearOnly(boolean onYearOnly) {
		this.onYearOnly = onYearOnly;
	}

	public boolean isLunWe() {
		return lunWe;
	}

	public void setLunWe(boolean lunWe) {
		this.lunWe = lunWe;
	}

	public boolean isMarWe() {
		return marWe;
	}

	public void setMarWe(boolean marWe) {
		this.marWe = marWe;
	}

	public boolean isMerWe() {
		return merWe;
	}

	public void setMerWe(boolean merWe) {
		this.merWe = merWe;
	}

	public boolean isJeuWe() {
		return jeuWe;
	}

	public void setJeuWe(boolean jeuWe) {
		this.jeuWe = jeuWe;
	}

	public boolean isVenWe() {
		return venWe;
	}

	public void setVenWe(boolean venWe) {
		this.venWe = venWe;
	}

	public boolean isSamWe() {
		return samWe;
	}

	public void setSamWe(boolean samWe) {
		this.samWe = samWe;
	}

	public boolean isDimWe() {
		return dimWe;
	}

	public void setDimWe(boolean dimWe) {
		this.dimWe = dimWe;
	}

}
