package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;

@SuppressWarnings( { "serial" })
public class StatutGrilleCacheList extends RefBeanCacheList implements CachableInterface {

	protected List<StatutGrilleRefBean> status;

	public List<StatutGrilleRefBean> getElements() {
		return status;
	}

	public StatutGrilleRefBean getStatutGrilleById(int idStatutGrille) {
		StatutGrilleRefBean sg= (StatutGrilleRefBean) getElementByCode(String.valueOf(idStatutGrille));
		return sg;
	}

	public StatutGrilleCacheList(List<StatutGrilleRefBean> status, Contexte contexte) {
		this.status = status;
		setMap( status, contexte );
	}

}
