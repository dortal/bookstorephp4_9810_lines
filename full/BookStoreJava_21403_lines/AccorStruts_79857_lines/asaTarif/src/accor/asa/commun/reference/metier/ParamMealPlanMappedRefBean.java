package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.List;

import com.accor.asa.commun.metier.categorie.AsaCategory;

@SuppressWarnings("serial")
public class ParamMealPlanMappedRefBean extends RefBean {

	private String codeAsaCategory;
	private int idGroupTarif;
	private int idFamilleTarif;
	private List<MealPlanRefBean> mealPlans=new ArrayList<MealPlanRefBean>();
	private MealPlanRefBean defaultMealPlan;

	private AsaCategory asaCategory;
	private FamilleTarifRefBean familleTarif;

	public String getCodeAsaCategory() {
		return codeAsaCategory;
	}

	public void setCodeAsaCategory(String codeAsaCategory) {
		this.codeAsaCategory = codeAsaCategory;
	}

	public int getIdGroupTarif() {
		return idGroupTarif;
	}

	public void setIdGroupTarif(int idGroupTarif) {
		this.idGroupTarif = idGroupTarif;
	}

	public List<MealPlanRefBean> getMealPlans() {
		return mealPlans;
	}

	public void setMealPlans(List<MealPlanRefBean> mealPlans) {
		this.mealPlans = mealPlans;
	}

	public String getDefalutMealplanCode() {
		if(defaultMealPlan==null)
			return null;
		return defaultMealPlan.getCode();
	}

	public ParamMealPlanMappedRefBean() {
		super();
	}

	public String getCode() {
		return bulidCompositeKey(codeAsaCategory, idFamilleTarif);
	}

	public void addMealPlan(MealPlanRefBean mealplan, boolean asDefault) {
		if (asDefault) {
			mealPlans.add(0, mealplan);
			//defalutMealplanCode = mealplan.getCode();
			defaultMealPlan=mealplan;
		} else
			mealPlans.add(mealplan);
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

	public int getIdFamilleTarif() {
		return idFamilleTarif;
	}

	public void setIdFamilleTarif(int idFamilleTarif) {
		this.idFamilleTarif = idFamilleTarif;
	}

	

	public void setFamilleTarif(FamilleTarifRefBean familleTarif) {
		this.familleTarif = familleTarif;
		if (familleTarif == null) {
			setIdGroupTarif(-1);
			setIdFamilleTarif(-1);
		} else {
			setIdGroupTarif(familleTarif.getIdGroupeTarif());
			setIdFamilleTarif(Integer.parseInt(familleTarif.getId()));
		}
	}

	public FamilleTarifRefBean getFamilleTarif() {
		return familleTarif;
	}

	public static String bulidCompositeKey(String codeAsaCategory, int idFamilleTarif) {
		return codeAsaCategory + "_" + idFamilleTarif;
	}

	public MealPlanRefBean getDefaultMealPlan() {
		return defaultMealPlan;
	}

	public void setDefaultMealPlan(MealPlanRefBean defaultMealPlan) {
		this.defaultMealPlan = defaultMealPlan;
	}
	
	
}
