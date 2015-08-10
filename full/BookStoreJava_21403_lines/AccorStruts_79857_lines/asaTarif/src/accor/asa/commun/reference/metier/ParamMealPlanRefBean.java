package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.categorie.AsaCategory;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class ParamMealPlanRefBean extends RefBean {

	private String codeAsaCategory;
	private int idGroupeTarif;
	private int idFamilleTarif;
	private String codeMealPlan;
	private boolean isDefault;

	private AsaCategory asaCategory;
	private FamilleTarifRefBean familleTarif;
	private MealPlanRefBean mealPlan;

	public String getCodeAsaCategory() {
		return codeAsaCategory;
	}

	public void setCodeAsaCategory(String codeAsaCategory) {
		this.codeAsaCategory = codeAsaCategory;
	}

	public int getIdGroupeTarif() {
		return idGroupeTarif;
	}

	public void setIdGroupeTarif(int idGroupeTarif) {
		this.idGroupeTarif = idGroupeTarif;
	}

	public String getCodeMealPlan() {
		return codeMealPlan;
	}

	public void setCodeMealPlan(String codeMealPlan) {
		this.codeMealPlan = codeMealPlan;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public AsaCategory getAsaCategory() {
		return asaCategory;
	}

	public void setAsaCategory(AsaCategory asaCategory) {
		if (asaCategory == null)
			setCodeAsaCategory(null);
		else
			setCodeAsaCategory(asaCategory.getCode());
		this.asaCategory = asaCategory;
	}

	public void setFamilleTarif(FamilleTarifRefBean familleTarif) {
		this.familleTarif = familleTarif;
		if (familleTarif == null) {
			setIdGroupeTarif(-1);
			setIdFamilleTarif(-1);
		} else {
			setIdGroupeTarif(familleTarif.getIdGroupeTarif());
			setIdFamilleTarif(Integer.parseInt(familleTarif.getId()));
		}
	}

	public MealPlanRefBean getMealPlan() {
		return mealPlan;
	}

	public void setMealPlan(MealPlanRefBean mealPlan) {
		this.mealPlan = mealPlan;
		if (mealPlan == null)
			setCodeMealPlan(null);
		else
			setCodeMealPlan(mealPlan.getCode());
	}

	public String getCode() {
		return getIdFamilleTarif() + "_" + getCodeAsaCategory() + "_" + getCodeMealPlan();
	}

	public int getIdFamilleTarif() {
		return idFamilleTarif;
	}

	public void setIdFamilleTarif(int idFamilleTarif) {
		this.idFamilleTarif = idFamilleTarif;
	}

	public FamilleTarifRefBean getFamilleTarif() {
		return familleTarif;
	}

	public static ParamMealPlanCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException {
		return (ParamMealPlanCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.PARAM_MEALPLAN_KEY, contexte);
	}

}
