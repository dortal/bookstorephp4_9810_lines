package com.accor.asa.commun.user.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ListTerritories extends AbstractCachable implements
		CachableInterface {

	protected List<Territory> territories;
	
	public ListTerritories( final List<Territory> territories ) {
		this.territories = territories;
	}
	
	public List<Territory> getElements() {
		return territories;
	}
}
