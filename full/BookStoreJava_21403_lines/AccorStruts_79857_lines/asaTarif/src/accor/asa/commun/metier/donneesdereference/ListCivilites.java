package com.accor.asa.commun.metier.donneesdereference;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Element;

@SuppressWarnings("serial")
public class ListCivilites extends AbstractCachable implements
		CachableInterface {

	protected List<Element> civs;
	
	public ListCivilites( List<Element> civs, String codeLangue ) {
		this.civs = civs;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}
	
	public List<Element> getElements() {
		return civs;
	}
}
