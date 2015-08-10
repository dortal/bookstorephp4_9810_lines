package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;

@SuppressWarnings( {"serial" })
public class PeriodeValiditeCacheList  extends RefBeanCacheList  implements CachableInterface{
	
	protected List<PeriodeValiditeRefBean> validityPeriods;

	public  List<PeriodeValiditeRefBean> getElements() {
		return validityPeriods;
	}

	public  PeriodeValiditeRefBean getPeriodeValiditeById(int idPeriodeValidite)  {
		return (PeriodeValiditeRefBean)getElementByCode(String.valueOf(idPeriodeValidite));
	}

	public PeriodeValiditeCacheList(List<PeriodeValiditeRefBean> validityPeriods, Contexte contexte) {
		this.validityPeriods = validityPeriods;
		setMap( validityPeriods, contexte );
	}

	public List<PeriodeValiditeRefBean> getPeriodesForGroupeTarif(int idGroupeTarif)
	{
		List<PeriodeValiditeRefBean> results = new ArrayList<PeriodeValiditeRefBean>();
		for( PeriodeValiditeRefBean periode: validityPeriods ) {
			if(periode.getIdGroupeTarif()==idGroupeTarif)
				results.add(periode);
		}
		return results;
	}
	
	
	
}
