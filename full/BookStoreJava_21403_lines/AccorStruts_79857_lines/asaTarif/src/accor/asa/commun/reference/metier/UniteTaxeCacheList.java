package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class UniteTaxeCacheList extends RefBeanCacheList  implements CachableInterface {

	protected List<UniteTaxeRefBean> taxeUnits;

	public  List<UniteTaxeRefBean> getElements() {
		return taxeUnits;
	}

	public  UniteTaxeRefBean getUniteTaxeById(int idUniteTaxe) {
		return (UniteTaxeRefBean)getElementByCode(String.valueOf(idUniteTaxe));
	}

	public UniteTaxeCacheList(List<UniteTaxeRefBean> taxeUnits, Contexte contexte) {
		this.taxeUnits = taxeUnits;
		setMap( taxeUnits, contexte );
	}


}
