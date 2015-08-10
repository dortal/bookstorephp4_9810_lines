package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;

@SuppressWarnings( {"serial" })
public class SupplementCacheList extends RefBeanCacheList implements CachableInterface {

	protected List<SupplementRefBean> supplements;

	public List<SupplementRefBean> getElements() {
		return supplements;
	}

	public SupplementRefBean getSupplementeByCode(String codeSupplement) {
		return (SupplementRefBean) getElementByCode(codeSupplement);
	}

	public SupplementCacheList(List<SupplementRefBean> supplements, Contexte contexte) {
		this.supplements = supplements;
		setMap( supplements, contexte );
	}

}
