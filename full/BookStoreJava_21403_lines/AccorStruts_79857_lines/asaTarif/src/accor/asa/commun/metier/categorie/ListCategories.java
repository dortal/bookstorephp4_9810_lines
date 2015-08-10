package com.accor.asa.commun.metier.categorie;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 27 mars 2007
 * Time: 15:08:06
 */
@SuppressWarnings("serial")
public class ListCategories  extends AbstractCachable implements CachableInterface {
	
	private List<AsaCategory> categories = null;
    /**
     * Constructeur
     * @param listCategories
     */
    public ListCategories( List<AsaCategory> listCategories, String codeLangue ) {
        this.categories = listCategories;
        this.params = new String[1];
        this.params[0] = codeLangue;
    }
    
    public List<AsaCategory> getElements () {
    	return this.categories;
    }
}