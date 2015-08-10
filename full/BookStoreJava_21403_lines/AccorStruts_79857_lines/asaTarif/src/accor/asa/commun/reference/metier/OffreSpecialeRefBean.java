package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class OffreSpecialeRefBean extends RefBean{

	private String description = null;
	private int idOffreSpeciale;
	private String name;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getCode() {
		if(idOffreSpeciale==0)
			return null;
		return String.valueOf(idOffreSpeciale);
	}
	public void setCode(String newValue) {
		if(newValue==null)
			idOffreSpeciale=0;
		idOffreSpeciale=Integer.parseInt(newValue);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static OffreSpecialeCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException {
		return (OffreSpecialeCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.OFFRE_SPECIALE_KEY, contexte);
	}

}
