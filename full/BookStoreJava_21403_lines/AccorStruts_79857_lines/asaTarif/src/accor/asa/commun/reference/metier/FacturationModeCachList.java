package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractListCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 9 août 2007
 * Time: 16:44:29
 */
@SuppressWarnings("serial")
public class FacturationModeCachList extends AbstractListCachable implements CachableInterface {

	@SuppressWarnings("unchecked")
	public FacturationModeCachList( List modes, String codeLangue ) {
		this.elements = modes;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}
}
