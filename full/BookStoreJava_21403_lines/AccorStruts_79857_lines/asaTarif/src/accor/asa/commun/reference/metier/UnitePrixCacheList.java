package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;

@SuppressWarnings("serial")
public class UnitePrixCacheList extends RefBeanCacheList  implements CachableInterface{

	protected List<UnitePrixRefBean> priceUnits;

	public  List<UnitePrixRefBean> getElements() {
		return priceUnits;
	}

	public  UnitePrixRefBean getUnitePrixById(int idUnitePrix) {
		return (UnitePrixRefBean)getElementByCode(String.valueOf(idUnitePrix));
	}

	
	public UnitePrixCacheList(List<UnitePrixRefBean> priceUnits, Contexte contexte) {
		this.priceUnits = priceUnits;
		setMap( priceUnits, contexte );
	}

	
}
