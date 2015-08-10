package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class MealPlanRefBean extends RefBean{
    public static final String CODE_MEALPLAN_ALL_INCLUSIVE      = "AL";
    public static final String CODE_MEALPLAN_BED_AND_BREAKFAST  = "BB";
    public static final String CODE_MEALPLAN_ROOM_ONLY          = "EP";
    public static final String CODE_MEALPLAN_FULL_BOARD         = "FB";
    public static final String CODE_MEALPLAN_HALF_BOARD         = "HB";

    private boolean pdjInclu;
	private boolean pdjNotInclu;
	private boolean allInclusive;
	private String name;
	
	
	public boolean isPdjInclu() {
		return pdjInclu;
	}
	public void setPdjInclu(boolean pdjInclu) {
		this.pdjInclu = pdjInclu;
	}
	public boolean isPdjNotInclu() {
		return pdjNotInclu;
	}
	public void setPdjNotInclu(boolean pdjNotInclu) {
		this.pdjNotInclu = pdjNotInclu;
	}
	public boolean isAllInclusive() {
		return allInclusive;
	}
	public void setAllInclusive(boolean allInclusive) {
		this.allInclusive = allInclusive;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public static MealPlanRefCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException {
		return (MealPlanRefCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.MEALPLAN_KEY, contexte);
	}
}
