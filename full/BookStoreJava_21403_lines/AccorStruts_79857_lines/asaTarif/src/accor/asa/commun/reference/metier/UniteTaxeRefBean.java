package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
@SuppressWarnings("serial")
public class UniteTaxeRefBean extends RefBean{

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

	public static UniteTaxeCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException {
		return (UniteTaxeCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.UNITE_TAXE_KEY, contexte);
	}

}
