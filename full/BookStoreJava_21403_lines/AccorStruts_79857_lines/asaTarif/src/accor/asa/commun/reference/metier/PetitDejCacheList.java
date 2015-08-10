package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;

@SuppressWarnings( { "serial" })
public class PetitDejCacheList extends RefBeanCacheList implements CachableInterface {
	
	protected List<PetitDejRefBean> breakfast;

	public List<PetitDejRefBean> getElements() {
		return breakfast;
	}

	public PetitDejCacheList(List<PetitDejRefBean> breakfast, Contexte contexte) {
		this.breakfast = breakfast;
		setMap( breakfast, contexte );
	}

	public PetitDejRefBean getPetitDejByCode(String codePetitDej) {
		return (PetitDejRefBean) getElementByCode(codePetitDej);
	}
}
