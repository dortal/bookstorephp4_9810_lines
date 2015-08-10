package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;

@SuppressWarnings("serial")
public class ActiviteCommercialeTourOpCacheList extends RefBeanCacheList implements CachableInterface {

	protected List<ActiviteCommercialeTourOpRefBean> activities;
	
	public List<ActiviteCommercialeTourOpRefBean> getElements() {
		return activities;
	}
	
	public ActiviteCommercialeTourOpRefBean get( int idActivite ) {
		return (ActiviteCommercialeTourOpRefBean)getElementByCode( String.valueOf( idActivite ) );
	}
	
	public ActiviteCommercialeTourOpCacheList( List<ActiviteCommercialeTourOpRefBean> activities, 
			Contexte contexte ) {
		this.activities = activities;
		setMap( activities, contexte );
	}
}
