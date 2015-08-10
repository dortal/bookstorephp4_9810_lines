package com.accor.asa.commun.metier.donneesdereference;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Element;

@SuppressWarnings("serial")
public class ListLangues extends AbstractCachable implements CachableInterface {

	protected List<Element> langues;
	
	public ListLangues( List<Element> langues ) {
		this.langues = langues;
	}
		
	public List<Element> getElements() {
		return langues;
	}
}
