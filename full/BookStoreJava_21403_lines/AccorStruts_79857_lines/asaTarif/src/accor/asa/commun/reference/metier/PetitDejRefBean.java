package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class PetitDejRefBean extends RefBean{
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static PetitDejCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException {
		return (PetitDejCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.PETITDEJ_KEY, contexte);
	}
}
