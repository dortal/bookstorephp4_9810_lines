package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;

@SuppressWarnings("serial")
public class MealPlanRefCacheList extends RefBeanCacheList implements CachableInterface {

	protected List<MealPlanRefBean> mealPlans;

	public List<MealPlanRefBean> getElements() {
		return mealPlans;
	}

	public MealPlanRefBean getMealplanByCode(String codeMeaplan) {
		return (MealPlanRefBean) getElementByCode(codeMeaplan);
	}

	public MealPlanRefCacheList(List<MealPlanRefBean> mealPlans, Contexte contexte) {
		this.mealPlans = mealPlans;
		setMap( mealPlans, contexte );
	}

}
