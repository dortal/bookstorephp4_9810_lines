package com.accor.asa.commun.metier.state;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Element;

@SuppressWarnings("serial")
public class ListStates extends AbstractCachable implements CachableInterface {
	
	protected List<Element> states;
	
	public ListStates( final List<Element> states, final String codePays ) {
		this.states = states;
		this.params = new String[1];
		this.params[0] = codePays;
	}
	
	@Override
	public List<Element> getElements() {
		return states;
	}

}
