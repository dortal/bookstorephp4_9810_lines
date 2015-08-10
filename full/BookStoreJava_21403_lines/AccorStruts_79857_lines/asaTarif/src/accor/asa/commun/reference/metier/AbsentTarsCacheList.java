package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;

@SuppressWarnings("serial")
public class AbsentTarsCacheList extends RefBeanCacheList  implements CachableInterface{

	protected List<AbsentTarsRefBean> priceUnits;

	public  List<AbsentTarsRefBean> getElements() {
		return priceUnits;
	}

	public  AbsentTarsRefBean getUnitePrixById(int idUnitePrix) {
		return (AbsentTarsRefBean)getElementByCode(String.valueOf(idUnitePrix));
	}

	
	public AbsentTarsCacheList(List<AbsentTarsRefBean> priceUnits, Contexte contexte) {
		this.priceUnits = priceUnits;
		setMap( priceUnits, contexte );
	}

	
}
