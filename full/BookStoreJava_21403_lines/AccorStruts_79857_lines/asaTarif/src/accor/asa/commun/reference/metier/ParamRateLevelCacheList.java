package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ParamRateLevelCacheList extends RefParamBeanCacheList implements CachableInterface {

	private List<ParamRateLevelRefBean> elements;
	private Map<String, List<String>> paramRLmap;

	public ParamRateLevelCacheList(List<ParamRateLevelRefBean> elements) {
		this.elements = elements;
		setMap(elements);
		refreshMap();
	}

	public List<ParamRateLevelRefBean> getElements() {
		return elements;
	}
	
	private void refreshMap() {
		paramRLmap = new HashMap<String, List<String>>();
		List<ParamRateLevelRefBean> elements = getElements();
		for( ParamRateLevelRefBean row : elements ) {
			String key = ParamRateLevelMappedRefBean.buildCompositeCode(row.getCodeAsaCategory(), row.getIdFamilleTarif(), row.getIdPeriodeValidite());
			List<String> values = paramRLmap.get(key);
			if (values == null) {
				values = new ArrayList<String>();
				paramRLmap.put(key, values);
			}
			values.add(row.getCodeRateLevel());
		}
	}

	public List<String> getParamData(String codeAsaCategory, int idFamilleTarif, int idPeriodeValidite) {
		
		String key = ParamRateLevelMappedRefBean.buildCompositeCode(codeAsaCategory, idFamilleTarif, idPeriodeValidite);
		List<String> codesRL = paramRLmap.get(key);
		if (codesRL == null) {
			codesRL=new ArrayList<String>();
		}
		return codesRL;
	}

	

}
