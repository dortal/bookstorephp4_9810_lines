package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;

@SuppressWarnings("serial")
public class DivisionSemaineCacheList extends RefBeanCacheList  implements CachableInterface{
	
	protected List<DivisionSemaineRefBean> divisions;
	
	public List<DivisionSemaineRefBean> getElements() {
		return divisions;
	}
	
	public DivisionSemaineRefBean getDivisionSemaine(int idDivisionSemaine) {
		return (DivisionSemaineRefBean) getElementByCode( String.valueOf( idDivisionSemaine ) );
	}
	
	public DivisionSemaineCacheList( List<DivisionSemaineRefBean> divisions, Contexte contexte ) {
		this.divisions = divisions;
		setMap( divisions, contexte );
	}

}
