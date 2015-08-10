package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;

@SuppressWarnings( { "serial" })
public class PeriodeGeneriqueCacheList extends RefBeanCacheList implements CachableInterface {

	protected List<PeriodeGeneriqueRefBean> genericPeriods;

	public  List<PeriodeGeneriqueRefBean> getElements() {
		return genericPeriods;
	}

	public  PeriodeGeneriqueRefBean getPeriodeGeneriqueByCode(String codePeriodeGenerique) {
		return (PeriodeGeneriqueRefBean)getElementByCode(codePeriodeGenerique);
	}

	public PeriodeGeneriqueCacheList(List<PeriodeGeneriqueRefBean> genericPeriods, Contexte contexte) {
		this.genericPeriods = genericPeriods;
		setMap( genericPeriods, contexte );
	}

	

}
