package com.accor.asa.commun.metier.ratelevel;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ListRateLevels extends AbstractCachable implements CachableInterface {

	protected List<RateLevel> rates;
	
	public ListRateLevels( List<RateLevel> elements, String codeLangue ) {
		this.rates = elements;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}

	public List<RateLevel> getElements() {
		return rates;
	}
}
