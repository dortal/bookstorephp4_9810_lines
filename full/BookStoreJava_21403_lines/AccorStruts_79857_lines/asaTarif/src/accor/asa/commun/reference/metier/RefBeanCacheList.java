package com.accor.asa.commun.reference.metier;

import java.util.HashMap;
import java.util.List;

import com.accor.asa.commun.cache.metier.AbstractCachable;
import com.accor.asa.commun.metier.Contexte;

public abstract class RefBeanCacheList extends AbstractCachable {
	
	private HashMap<String, RefBean> elMap;
	
	public void setMap( List<?> elements, Contexte contexte ) {
		this.params = new String[1];
		this.params[0] = contexte.getCodeLangue();
		elMap=new HashMap<String, RefBean>();
		if(elements != null) {
			RefBean bean;
			for( int i=0, size=elements.size(); i<size; i++ ) {
				bean = (RefBean) elements.get(i);
				elMap.put( bean.getCode(), bean );
			}
		}
	}

	public RefBean getElementByCode( String code ) {
		if(code==null)
			return null;
		return elMap.get(code);
	}
}
