package com.accor.asa.commun.metier.donneesdereference;

import java.util.HashMap;
import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ListPays extends AbstractCachable implements CachableInterface {

	private List<Pays> listePays = null;
	private HashMap<String, Pays> mapByCode = new HashMap<String, Pays>();
	
	public ListPays( List<Pays> pays ) {
		this.listePays = pays;
		for(Pays p:pays)
		{
			mapByCode.put(p.getCode(), p);
		}
	}
	
	public List<Pays> getElements () {
		return listePays;
	}
	
	public Pays getPaysByCode(String codePays)
	{
		if(codePays==null)
			return null;
		return mapByCode.get(codePays);
	}
}