package com.accor.asa.commun.metier.donneesdereference;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;
// ANO 3971
@SuppressWarnings("serial")
public class ListPaysGEO extends AbstractCachable implements CachableInterface {

	protected List<Pays> pays;
	
	public ListPaysGEO( List<Pays> pays, final String codeLangue ) {
		this.pays = pays;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}
	
	public List<Pays> getElements() {
		return pays;
	}
}
