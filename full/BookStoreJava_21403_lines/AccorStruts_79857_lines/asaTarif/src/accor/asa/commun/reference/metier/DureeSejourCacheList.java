package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;

@SuppressWarnings("serial")
public class DureeSejourCacheList extends RefBeanCacheList  implements CachableInterface{

	protected List<DureeSejourRefBean> durations;

	public  List<DureeSejourRefBean> getElements() {
		return durations;
	}
	
	public  DureeSejourRefBean getDureSejour( int idDureeSejour ) {
		return (DureeSejourRefBean) getElementByCode( String.valueOf( idDureeSejour ) );
	}

	public DureeSejourCacheList(List<DureeSejourRefBean> durations, Contexte contexte) {
		this.durations = durations;
		setMap( durations, contexte );
	}
}
