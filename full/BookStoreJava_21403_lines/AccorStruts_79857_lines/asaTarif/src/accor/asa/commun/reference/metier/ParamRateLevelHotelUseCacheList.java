package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ParamRateLevelHotelUseCacheList extends RefParamBeanCacheList implements CachableInterface{

	private List<ParamRateLevelHotelUseRefBean> elements;
	
	public ParamRateLevelHotelUseCacheList(List<ParamRateLevelHotelUseRefBean> elements) {
		this.elements = elements;
		setMap(elements);
	}
	
	public List<ParamRateLevelHotelUseRefBean> getElements() {
		return elements;
	}
	
}
