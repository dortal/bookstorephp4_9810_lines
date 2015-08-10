package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class TypePrixRefBean extends RefBean {
    static public String ID_TYPEPRIX_NONE       = "0";
    static public String ID_TYPEPRIX_INCLUDED   = "3";

    private String name;
	private boolean discount;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDiscount() {
		return discount;
	}

	public void setDiscount(boolean discount) {
		this.discount = discount;
	}

	public static TypePrixCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException {
		return (TypePrixCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.TYPE_PRIX_KEY, contexte);
	}
}
