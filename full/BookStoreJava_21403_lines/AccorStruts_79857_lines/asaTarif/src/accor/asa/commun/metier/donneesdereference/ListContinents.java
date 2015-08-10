package com.accor.asa.commun.metier.donneesdereference;

import java.util.HashMap;
import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

public class ListContinents extends AbstractCachable implements CachableInterface{

	private List<Continent> continents;
	private HashMap<String, Continent> mapByCode=new HashMap<String, Continent>();
	
	public ListContinents(List<Continent> continents) {
		super();
		this.continents = continents;
		for(Continent continent:continents)
		{
			mapByCode.put(continent.getCode(), continent);
		}
	}
	
	@Override
	public List<Continent> getElements() {
		return continents;
	}

	public Continent getContinentByCode(String codeContinent)
	{
		if(codeContinent==null)
			return null;
		return mapByCode.get(codeContinent);
	}
}
