package com.accor.asa.commun.metier.periodedevalidite;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/***
 * 
 * @author FCHIVAUX
 *
 */
@SuppressWarnings("serial")
public class ListPeriodeValidite extends AbstractCachable implements CachableInterface {

    protected List<PeriodeDeValidite> periods;
	
	/**
     * Constructeur
     * @param elements
     */
    public ListPeriodeValidite( List<PeriodeDeValidite> elements ) {
        this.periods = elements;
    }
    
    public List<PeriodeDeValidite> getElements() {
    	return periods;
    }
}
