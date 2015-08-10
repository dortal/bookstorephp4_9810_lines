package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;

@SuppressWarnings( { "serial" })
public class PeriodeGeneriqueExcluHotelCacheList extends RefBeanCacheList implements CachableInterface {

	protected List<ParamPeriodeGeneriqueExcluHotelRefBean> genericPeriods;

	public  List<ParamPeriodeGeneriqueExcluHotelRefBean> getElements() {
		return genericPeriods;
	}

	public  ParamPeriodeGeneriqueExcluHotelRefBean getPeriodeGeneriqueByCode(String codePeriodeGenerique) {
		return (ParamPeriodeGeneriqueExcluHotelRefBean)getElementByCode(codePeriodeGenerique);
	}

	public PeriodeGeneriqueExcluHotelCacheList(List<ParamPeriodeGeneriqueExcluHotelRefBean> genericPeriods, Contexte contexte) {
		this.genericPeriods = genericPeriods;
		setMap( genericPeriods, contexte );
	}

	

}
