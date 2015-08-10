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
public class ParamGrilleCacheList extends RefParamBeanCacheList implements CachableInterface {

	private List<ParamGrilleRefBean> params;
	private Map<String, List<ParamGrilleRefBean>> goupedMap;

	public ParamGrilleCacheList(List<ParamGrilleRefBean> params) {
		this.params = params;
		setMap(params);
		refreshMaps();
	}

	public List<ParamGrilleRefBean> getElements() {
		return params;
	}
	
	private void refreshMaps() {
		goupedMap = new HashMap<String, List<ParamGrilleRefBean>>();
		List<ParamGrilleRefBean> elements = getElements();
		for( ParamGrilleRefBean pg : elements ) {
			String mapKey = ParamGrilleMappedRefBean.buildKey(pg.getIdPeriodeValidite(), pg.getCodeAsaCategory());
			List<ParamGrilleRefBean> params = goupedMap.get(mapKey);
			if (params == null) {
				params = new ArrayList<ParamGrilleRefBean>();
				goupedMap.put(mapKey, params);
			}
			params.add(pg);
		}
	}

	public ParamGrilleMappedRefBean getParamGrille(int idPeriodeValidite, String codeAsaCategory, boolean supplemeltsIncluded, Contexte contexte) throws IncoherenceException, TechnicalException {
		ParamGrilleMappedRefBean result = new ParamGrilleMappedRefBean();
		result.setIdPeriodeValidite(idPeriodeValidite);
		result.setCodeAsaCategory(codeAsaCategory);
		List<ParamGrilleRefBean> paramsGrille = goupedMap.get(ParamGrilleMappedRefBean.buildKey(idPeriodeValidite, codeAsaCategory));
		if (paramsGrille != null) {
			for (ParamGrilleRefBean pg : paramsGrille) {
				ParamGrilleData pgData = new ParamGrilleData(pg);
				if (supplemeltsIncluded) {
					List<SupplementRefBean> supplements = ParamSupplementRefBean.getCacheList(contexte).getSupplements(pg, contexte);
					pgData.setSupplements(supplements);
				}
				result.addGrilleParam(pg.getCodeRateLevel(), pgData);
			}
		}
		return result;
	}
}
