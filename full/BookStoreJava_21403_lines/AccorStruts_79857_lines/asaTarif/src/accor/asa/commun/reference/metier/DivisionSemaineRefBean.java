package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class DivisionSemaineRefBean extends RefBean {

    public static final int ID_DIVSEM_COMPLETE  = 1;
    public static final int ID_DIVSEM_WEEKEND   = 2;
    public static final int ID_DIVSEM_WEEK      = 3;

    private String name;
	
	public DivisionSemaineRefBean () {
		super();
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public static DivisionSemaineCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException
	{
		return (DivisionSemaineCacheList)PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.DIVISION_SEMAINE_REF_KEY, contexte);
	}
}