package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class UnitePrixRefBean extends RefBean{
	
	private String name;
	private boolean isDefault;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	
	public static UnitePrixCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException {
		return (UnitePrixCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.UNITE_PRIX_KEY, contexte);
	}
	

}
