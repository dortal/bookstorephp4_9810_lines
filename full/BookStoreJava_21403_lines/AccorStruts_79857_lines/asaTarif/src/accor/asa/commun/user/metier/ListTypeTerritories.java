package com.accor.asa.commun.user.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Element;

@SuppressWarnings("serial")
public class ListTypeTerritories extends AbstractCachable implements
		CachableInterface {

	protected List<Element> types;
	
	public ListTypeTerritories( final List<Element> types, final String codeLangue ) {
		this.types = types;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}
	
	public List<Element> getElements() {
		return types;
	}
}
