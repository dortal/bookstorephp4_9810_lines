package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;

@SuppressWarnings("serial")
public class ParamMealPlanCacheList extends RefParamBeanCacheList implements CachableInterface {

	private List<ParamMealPlanRefBean> elements;
	private Map<String, List<ParamMealPlanRefBean>> mapByAcaCatAndFamTarif;

	public ParamMealPlanCacheList(List<ParamMealPlanRefBean> elements) {
		this.elements = elements;
		setMap(elements);
		refreshMapps();
	}

	public List<ParamMealPlanRefBean> getElements() {
		return elements;
	}
	
	private void refreshMapps() {
		mapByAcaCatAndFamTarif = new HashMap<String, List<ParamMealPlanRefBean>>();
		List<ParamMealPlanRefBean> elements = getElements();
		for( ParamMealPlanRefBean pmp : elements ) {
			String key = ParamMealPlanMappedRefBean.bulidCompositeKey(pmp.getCodeAsaCategory(), pmp.getIdFamilleTarif());

			List<ParamMealPlanRefBean> codesMealplanes = mapByAcaCatAndFamTarif.get(key);
			if (codesMealplanes == null) {
				codesMealplanes = new ArrayList<ParamMealPlanRefBean>();
				mapByAcaCatAndFamTarif.put(key, codesMealplanes);
				
			}
			codesMealplanes.add(pmp);
		}
	}

	
	public ParamMealPlanMappedRefBean getMealPlanParametrage(String codeAsaCategory, int idFamilleTarif, Contexte contexte) throws TechnicalException, IncoherenceException
	{
		ParamMealPlanMappedRefBean bean = new ParamMealPlanMappedRefBean();
		bean.setCodeAsaCategory(codeAsaCategory);
		bean.setIdFamilleTarif(idFamilleTarif);
		String key =ParamMealPlanMappedRefBean.bulidCompositeKey(codeAsaCategory, idFamilleTarif);
		
		if (mapByAcaCatAndFamTarif == null)
			return bean;
		List<ParamMealPlanRefBean> paramMealplans = mapByAcaCatAndFamTarif.get(key);
		if (paramMealplans != null) {
			MealPlanRefCacheList mealplansCacheList = MealPlanRefBean.getCacheList(contexte);
			for (ParamMealPlanRefBean pmp : paramMealplans) {
				MealPlanRefBean mealplan = (MealPlanRefBean) mealplansCacheList.getElementByCode(pmp.getCodeMealPlan());
				if (mealplan != null)
					bean.addMealPlan(mealplan, pmp.isDefault());
			}
		}
		return bean;
	}

	

}
