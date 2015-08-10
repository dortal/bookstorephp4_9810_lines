package com.accor.asa.commun.metier.devise;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 9 oct. 2006
 * Time: 17:07:55
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class ListDevisesCache extends AbstractCachable implements CachableInterface {

	private List<Devise> devises = null;
	
    /**
     * Constructeur
     * @param listDevises
     */
    public ListDevisesCache( List<Devise> listDevises ) {
        this.devises = listDevises;
    }
    
    public List<Devise> getElements () {
    	return devises;
    }

}