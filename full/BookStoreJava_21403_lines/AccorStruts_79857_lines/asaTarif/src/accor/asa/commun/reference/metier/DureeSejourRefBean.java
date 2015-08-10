package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class DureeSejourRefBean extends RefBean {
	
	public static int DEFAULT_ID=1;

	private Integer minSejour;
	private Integer maxSejour;
	private String name;

	public Integer getMinSejour() {
		return minSejour;
	}

	public void setMinSejour(Integer minSejour) {
		this.minSejour = minSejour;
	}

	public Integer getMaxSejour() {
		return maxSejour;
	}

	public void setMaxSejour(Integer maxSejour) {
		this.maxSejour = maxSejour;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static DureeSejourCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException {
		return (DureeSejourCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.DUREE_SEJOUR_KEY, contexte);
	}

	

}
