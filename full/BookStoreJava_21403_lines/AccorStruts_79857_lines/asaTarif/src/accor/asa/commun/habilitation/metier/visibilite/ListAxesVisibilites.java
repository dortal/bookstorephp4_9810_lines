package com.accor.asa.commun.habilitation.metier.visibilite;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ListAxesVisibilites extends AbstractCachable implements CachableInterface {

	protected List<AxeVisibilite> axes;
	
	public ListAxesVisibilites( final List<AxeVisibilite> axes, final String codeProfil ) {
		this.axes = axes;
		this.params = new String[1];
		this.params[0] = codeProfil;
	}
	
	public List<AxeVisibilite> getElements() {
		return axes;
	}
}
