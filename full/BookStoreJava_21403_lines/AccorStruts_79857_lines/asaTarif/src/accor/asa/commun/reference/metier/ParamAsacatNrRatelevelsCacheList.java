package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ParamAsacatNrRatelevelsCacheList extends RefParamBeanCacheList implements CachableInterface {

	private List<ParamAsacatNrRateLevelsRefBean> params;

	public ParamAsacatNrRatelevelsCacheList(List<ParamAsacatNrRateLevelsRefBean> params) {
		this.params = params;
		setMap(params);
	}

	public List<ParamAsacatNrRateLevelsRefBean> getElements() {
		return params;
	}
}
