package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractListCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 9 août 2007
 * Time: 16:44:42
 */
@SuppressWarnings("serial")
public class FacturationModeByTOCachList extends AbstractListCachable implements CachableInterface {

	@SuppressWarnings("unchecked")
	public FacturationModeByTOCachList( List modes) {
		this.elements = modes;
	}
}
