package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;

@SuppressWarnings("serial")
public class SolutionConnectiviteTourOpCacheList extends RefBeanCacheList
		implements CachableInterface {

	protected List<SolutionConnectiviteTourOpRefBean> soluces;

	public  List<SolutionConnectiviteTourOpRefBean> getElements() {
		return soluces;
	}
	
	public  SolutionConnectiviteTourOpRefBean getSolutionConnectivite(int idSolution) {
		return (SolutionConnectiviteTourOpRefBean)getElementByCode(String.valueOf(idSolution));
	}
	
	public SolutionConnectiviteTourOpCacheList( List<SolutionConnectiviteTourOpRefBean> soluces, 
			Contexte contexte ) {
		this.soluces = soluces;
		setMap( soluces, contexte );
	}
}
