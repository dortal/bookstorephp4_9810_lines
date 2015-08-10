package com.accor.asa.commun.reference.metier;

import java.util.HashMap;
import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;

@SuppressWarnings("serial")
public abstract class RefParamBeanCacheList extends AbstractCachable  {

	private HashMap<String, RefBean> elMap;

	public void setMap(List<?> elements) {
		elMap=new HashMap<String, RefBean>();
		if(elements != null)
		{
			RefBean bean;
			for( int i=0, size=elements.size(); i<size; i++ ) {
				bean = (RefBean) elements.get(i);
				elMap.put(bean.getCode(),bean);
			}
		}
	}
	
	protected RefBean getElement(String code) {
		return (RefBean)elMap.get(code);
	}
}
