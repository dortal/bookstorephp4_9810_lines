package com.accor.asa.commun.metier.marque;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Element;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 12 oct. 2006
 * Time: 10:34:35
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class ListMarques  extends AbstractCachable implements CachableInterface {

    protected List<Element> marks;
	
	/**
     * Constructeur
     * @param listMarques
     */
    public ListMarques( List<Element> listMarques ) {
        this.marks = listMarques;
    }
    
    public List<Element> getElements() {
    	return marks;
    }
}
