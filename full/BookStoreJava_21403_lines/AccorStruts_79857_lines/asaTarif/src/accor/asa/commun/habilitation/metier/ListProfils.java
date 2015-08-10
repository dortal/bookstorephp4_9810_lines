package com.accor.asa.commun.habilitation.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ListProfils extends AbstractCachable implements CachableInterface {

	protected List<Profil> profils;
	
	public ListProfils( List<Profil> profils ) {
		this.profils = profils;
	}
	
	public List<Profil> getElements() {
		return profils;
	}
}
