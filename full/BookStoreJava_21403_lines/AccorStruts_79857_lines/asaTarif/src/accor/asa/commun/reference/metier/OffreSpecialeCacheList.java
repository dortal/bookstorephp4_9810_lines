package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;

@SuppressWarnings("serial")
public class OffreSpecialeCacheList extends RefBeanCacheList  implements CachableInterface{

	protected List<OffreSpecialeRefBean> specialOffers;

	public List<OffreSpecialeRefBean> getElements() {
		return specialOffers;
	}

	
	public OffreSpecialeRefBean getOffreSpecialeByCode(int idOffre) {
		return (OffreSpecialeRefBean)getElementByCode(String.valueOf(idOffre));
	}
	
	public OffreSpecialeCacheList(List<OffreSpecialeRefBean> specialOffers, Contexte contexte) {
		this.specialOffers = specialOffers;
		setMap( specialOffers, contexte );
	}
	
	
}
