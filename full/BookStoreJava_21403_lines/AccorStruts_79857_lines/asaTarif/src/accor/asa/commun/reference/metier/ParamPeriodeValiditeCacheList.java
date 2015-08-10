package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ParamPeriodeValiditeCacheList  extends  RefParamBeanCacheList implements CachableInterface{

	private List<ParamPeriodeValiditeRefBean> elements;
	
	public ParamPeriodeValiditeCacheList(List<ParamPeriodeValiditeRefBean> elements)
	{
		this.elements = elements;
		setMap(elements);
	}
	
	public List<ParamPeriodeValiditeRefBean> getElements() {
		return elements;
	}
	
	public ParamPeriodeValiditeRefBean getParamPeriodeValidite(String codeAsaCategory, int idGroupeTarif)
	{
		String key = ParamPeriodeValiditeRefBean.buildKey(codeAsaCategory, idGroupeTarif);
		return (ParamPeriodeValiditeRefBean)getElement(key);
	}
}
