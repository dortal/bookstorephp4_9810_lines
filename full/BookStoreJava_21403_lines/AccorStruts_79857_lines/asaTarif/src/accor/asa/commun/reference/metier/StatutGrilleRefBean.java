package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class StatutGrilleRefBean extends RefBean{
	
	public static final int ID_STATUT_NO_SEIZED         = 0;
    public static final int ID_STATUT_NOW_SEIZED        = 1;
    public static final int ID_STATUT_SEIZED            = 2;
    public static final int ID_STATUT_VALID_1ST_LEVEL   = 3;
    public static final int ID_STATUT_REFUS_1ST_LEVEL   = 4;
    public static final int ID_STATUT_VALID_2ND_LEVEL   = 5;
    public static final int ID_STATUT_REFUS_2ND_LEVEL   = 7;
    public static final int ID_STATUT_TRANSFERRED       = 6;

    private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static StatutGrilleCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException {
		return (StatutGrilleCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.STATUTGRILLE_KEY, contexte);
	}
}
