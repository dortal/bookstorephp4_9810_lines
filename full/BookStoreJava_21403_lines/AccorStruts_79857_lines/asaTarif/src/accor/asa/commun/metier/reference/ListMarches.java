package com.accor.asa.commun.metier.reference;

import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.reference.metier.MarcheRefBean;

@SuppressWarnings("serial")
public class ListMarches extends AbstractCachable implements CachableInterface {

	private List<MarcheRefBean> markets;
	
	public ListMarches (List<MarcheRefBean> marches, String codeLangue) {
		this.markets = marches;
		this.params = new String[1];
		this.params[0] = codeLangue;
	}
	
	public List<MarcheRefBean> getElements() {
		return markets;
	}

}
