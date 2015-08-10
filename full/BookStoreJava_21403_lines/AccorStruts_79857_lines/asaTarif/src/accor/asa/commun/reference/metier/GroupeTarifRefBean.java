package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class GroupeTarifRefBean extends RefBean{

    public static final int ID_GROUPETARIF_BUSINESS  = 0;
    public static final int ID_GROUPETARIF_TOURISM   = 1;

    private String name;
	
	public GroupeTarifRefBean () {
		super();
	}
	
	public void setId(String id) {
		setCode(id);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public static GroupeTarifCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException {
		return (GroupeTarifCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.GROUPE_TARIF_KEY, contexte);
	}

}
