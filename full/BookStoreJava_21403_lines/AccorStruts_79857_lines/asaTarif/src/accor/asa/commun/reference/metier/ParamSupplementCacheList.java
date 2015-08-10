package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;

@SuppressWarnings("serial")
public class ParamSupplementCacheList extends RefParamBeanCacheList  implements CachableInterface{

	private List<ParamSupplementRefBean> elements;
	private Map<String, List<ParamSupplementRefBean>> mapByGrille;
	
	public ParamSupplementCacheList(List<ParamSupplementRefBean> elements) {
		this.elements = elements;
		setMap(elements);
		refreshMaps();
	}
	
	public List<ParamSupplementRefBean> getElements() {
		return elements;
	}
	
	private void refreshMaps()
	{
		mapByGrille = new HashMap<String, List<ParamSupplementRefBean>>();
		for( ParamSupplementRefBean ps : elements )
		{
			String mapKey = ParamGrilleRefBean.buildKey(ps.getIdPeriodeValidite(),ps.getCodeAsaCategory(), ps.getCodeRateLevel());
			List<ParamSupplementRefBean> listPS = mapByGrille.get(mapKey);
			if(listPS==null)
			{
				listPS = new ArrayList<ParamSupplementRefBean>();
				mapByGrille.put(mapKey, listPS);
			}
			listPS.add(ps);
		}
	}
	
	public List<SupplementRefBean> getSupplements(ParamGrilleRefBean paramGrille, Contexte contexte) throws IncoherenceException, TechnicalException
	{
		List<SupplementRefBean> results = new ArrayList<SupplementRefBean>();
		List<ParamSupplementRefBean> paramS = mapByGrille.get(paramGrille.getCode());
		if(paramS != null)
		{
			SupplementCacheList suppCache = SupplementRefBean.getCacheList(contexte);
			for(ParamSupplementRefBean ps:paramS)
			{
				SupplementRefBean supplement = suppCache.getSupplementeByCode(ps.getCode());
				if(supplement != null)
					results.add(supplement);
			}
		}
		return results;
	}
}
