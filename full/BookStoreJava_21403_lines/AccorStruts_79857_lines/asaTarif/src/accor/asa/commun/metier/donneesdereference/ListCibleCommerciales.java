package com.accor.asa.commun.metier.donneesdereference;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ListCibleCommerciales extends AbstractCachable implements CachableInterface {

	protected List<CibleCommerciale> cibles;
	
	public ListCibleCommerciales( List<CibleCommerciale> ciblesCommerciales, String codeLangue ) {
		this.cibles = ciblesCommerciales;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}
	
	public List<CibleCommerciale> getElements() {
		return cibles;
	}
}
