package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ParamTarifGOCacheList extends RefParamBeanCacheList  implements CachableInterface{

	private List<ParamTarifGORefBean> elements;
	
	public ParamTarifGOCacheList(List<ParamTarifGORefBean> elements) {
		this.elements = elements;
		setMap(elements);
	}
	
	public List<ParamTarifGORefBean> getElements() {
		return elements;
	}
}
