package com.accor.asa.commun.donneesdereference.metier;

import java.util.Map;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class MapMotifsDeStatut extends AbstractCachable implements CachableInterface {

	private Map<String, MotifsDeStatut> motifs = null;

	public MapMotifsDeStatut( Map<String, MotifsDeStatut> motifs ) {
		this.motifs = motifs;
	}

	public Map<String, MotifsDeStatut> getElements () {
		return motifs;
	}
}