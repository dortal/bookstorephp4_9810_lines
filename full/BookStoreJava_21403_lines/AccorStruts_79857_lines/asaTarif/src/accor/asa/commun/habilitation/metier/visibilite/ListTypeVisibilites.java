package com.accor.asa.commun.habilitation.metier.visibilite;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ListTypeVisibilites extends AbstractCachable implements CachableInterface {

	protected List<TypeVisibilite> types;
	
	public ListTypeVisibilites( final List<TypeVisibilite> types ) {
		this.types = types;
	}

	public List<TypeVisibilite> getElements() {
		return types;
	}
}