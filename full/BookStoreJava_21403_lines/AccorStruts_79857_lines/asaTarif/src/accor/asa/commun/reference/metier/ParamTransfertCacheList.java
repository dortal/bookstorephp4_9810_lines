package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ParamTransfertCacheList extends RefParamBeanCacheList  implements CachableInterface{

	private List<ParamTransfertRefBean> elements;
	
	public ParamTransfertCacheList(List<ParamTransfertRefBean> elements) {
		this.elements = elements;
		setMap(elements);
	}

	public List<ParamTransfertRefBean> getElements() {
		return elements;
	}
}
