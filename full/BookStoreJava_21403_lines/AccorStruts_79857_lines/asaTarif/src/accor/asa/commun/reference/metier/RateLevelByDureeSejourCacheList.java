package com.accor.asa.commun.reference.metier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class RateLevelByDureeSejourCacheList extends RefParamBeanCacheList implements CachableInterface{

	private List<RateLevelByDureeSejourRefBean> elements;
	private Map<String, RateLevelByDureeSejourRefBean> reverseLookupMap = new HashMap<String, RateLevelByDureeSejourRefBean>();
	
	public RateLevelByDureeSejourCacheList(List<RateLevelByDureeSejourRefBean> elements) {
		this.elements = elements;
		setMap(elements);
		if(elements!=null)
		{
			for(RefBean bean:elements)
			{
				RateLevelByDureeSejourRefBean temp = (RateLevelByDureeSejourRefBean)bean;
				reverseLookupMap.put(temp.getReverseCode(),temp);
			}
		}
	}
	
	public List<RateLevelByDureeSejourRefBean> getElements() {
		return elements;
	}
	
	public String getFinalRateLevel(int idPeriodeValidite, String initRateLevel, int idDureeSejour)
	{
		
		String key = RateLevelByDureeSejourRefBean.generateCode(idPeriodeValidite, initRateLevel, idDureeSejour);
		RateLevelByDureeSejourRefBean bean=(RateLevelByDureeSejourRefBean)getElement(key);
		if(bean==null)
			return null;
		return bean.getFinalRateLevel();
	}
	
	public String getInitialRateLevel(int idPeriodeValidite, String finalRateLevel, int idDureeSejour)
	{
		String key = RateLevelByDureeSejourRefBean.generateReverseCode(idPeriodeValidite, finalRateLevel, idDureeSejour);
		RateLevelByDureeSejourRefBean bean=reverseLookupMap.get(key);
		if(bean==null)
			return null;
		return bean.getInitRateLavel();
	}
	
	
}
