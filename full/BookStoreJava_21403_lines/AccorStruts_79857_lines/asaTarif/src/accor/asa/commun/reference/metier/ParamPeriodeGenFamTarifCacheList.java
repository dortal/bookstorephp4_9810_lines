package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;

@SuppressWarnings( "serial")
public class ParamPeriodeGenFamTarifCacheList extends RefParamBeanCacheList implements CachableInterface {

	private List<ParamPeriodeGenFamTarifRefBean> elements;
	private Map<String, List<ParamPeriodeGenFamTarifRefBean>> mapByFamilleTarif;

	public ParamPeriodeGenFamTarifCacheList(List<ParamPeriodeGenFamTarifRefBean> elements) {
		this.elements = elements;
		setMap(elements);
		refrashMaps();
	}

	public List<ParamPeriodeGenFamTarifRefBean> getElements() {
		return elements;
	}
	
	private void refrashMaps() {
		mapByFamilleTarif = new HashMap<String, List<ParamPeriodeGenFamTarifRefBean>>();
		List<ParamPeriodeGenFamTarifRefBean> elements = getElements();
		for( ParamPeriodeGenFamTarifRefBean bean : elements ) {
			String key = String.valueOf(bean.getIdFamilleTarif());
			List<ParamPeriodeGenFamTarifRefBean> paramPeriodes = mapByFamilleTarif.get(key);
			if (paramPeriodes == null) {
				paramPeriodes = new ArrayList<ParamPeriodeGenFamTarifRefBean>();
				mapByFamilleTarif.put(key, paramPeriodes);
			}
			paramPeriodes.add(bean);
		}
	}

	private List<ParamPeriodeGenFamTarifRefBean> getParamPeriodes(int idFamilleTarif) {
		if (mapByFamilleTarif == null)
			return new ArrayList<ParamPeriodeGenFamTarifRefBean>();
		List<ParamPeriodeGenFamTarifRefBean> results = mapByFamilleTarif.get(String.valueOf(idFamilleTarif));
	
		return results;
	}

	

	public ParamPeriodeGenFamTarifMappedRefBean getParametrageForFamilleTarif(int idFamilleTarif, Contexte contexte) throws IncoherenceException, TechnicalException
	{
		ParamPeriodeGenFamTarifMappedRefBean bean = new ParamPeriodeGenFamTarifMappedRefBean();
		bean.setIdFamilleTarif(idFamilleTarif);
		
		List<ParamPeriodeGenFamTarifRefBean> paramPeriodes = getParamPeriodes(idFamilleTarif);
		if (paramPeriodes!= null && !paramPeriodes.isEmpty()) {
			PeriodeGeneriqueCacheList cache = PeriodeGeneriqueRefBean.getCacheList(contexte);
			for (ParamPeriodeGenFamTarifRefBean paramPeriode : paramPeriodes) {
				PeriodeGeneriqueRefBean pg =(PeriodeGeneriqueRefBean)cache.getElementByCode(paramPeriode.getCodePeriode());
				if(pg != null)
					bean.addPeriodeGenerique(pg, paramPeriode.isDefault());
				
			}
		}
		return bean;
	}
	
}
