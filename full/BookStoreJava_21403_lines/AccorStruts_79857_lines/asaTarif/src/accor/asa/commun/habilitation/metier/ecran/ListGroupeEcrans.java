package com.accor.asa.commun.habilitation.metier.ecran;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ListGroupeEcrans extends AbstractCachable implements CachableInterface {

	protected List<GroupeEcran> groupes;
	
	public ListGroupeEcrans( final List<GroupeEcran> groupes, final String module ) {
		this.groupes = groupes;
		this.params = new String[1];
		this.params[0] = module;
	}
	
	public List<GroupeEcran> getElements() {
		return groupes;
	}
}
