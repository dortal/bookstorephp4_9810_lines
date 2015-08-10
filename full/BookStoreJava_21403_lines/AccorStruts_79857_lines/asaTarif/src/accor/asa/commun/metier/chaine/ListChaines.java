package com.accor.asa.commun.metier.chaine;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Element;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 27 mars 2007
 * Time: 14:53:37
 */
@SuppressWarnings("serial")
public class ListChaines extends AbstractCachable implements CachableInterface {

	private List<Element> chaines = null;

	/**
	 * Constructeur
	 * @param ListChaines
	 */
	public ListChaines( List<Element> listChaines ) {
		this.chaines = listChaines;
	}

	public List<Element> getElements () {
		return chaines;
	}

}
