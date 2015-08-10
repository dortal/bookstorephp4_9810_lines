package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.ParamDureeSejourRefBean;

@SuppressWarnings("serial")
public class ParamDureeSejourCacheList extends RefBeanCacheList implements CachableInterface {

	private List<ParamDureeSejourRefBean> elements;
	HashMap<String, List<ParamDureeSejourRefBean>> mapByChaine = new HashMap<String, List<ParamDureeSejourRefBean>>();

	public ParamDureeSejourCacheList(List<ParamDureeSejourRefBean> elements, Contexte contexte) {
		this.elements = elements;

		for (RefBean temp : elements) {
			ParamDureeSejourRefBean paramDS = (ParamDureeSejourRefBean) temp;
			List<ParamDureeSejourRefBean> params = mapByChaine.get(paramDS.getCodeChaineHotel());
			if (params == null) {
				params = new ArrayList<ParamDureeSejourRefBean>();
				mapByChaine.put(paramDS.getCodeChaineHotel(), params);
			}
			params.add(paramDS);

		}
	}

	public List<DureeSejourRefBean> getDureesSejourForHotel(String codeChaine, boolean forSalons, Contexte contexte) throws IncoherenceException, TechnicalException {
		List<DureeSejourRefBean> results = new ArrayList<DureeSejourRefBean>();
		List<ParamDureeSejourRefBean> params = mapByChaine.get(codeChaine);
		if (params != null) {
			DureeSejourCacheList dureeSejourCache = DureeSejourRefBean.getCacheList(contexte);
			for (ParamDureeSejourRefBean paramDS : params) {
				if (!forSalons || paramDS.isUsedForSalons()) {
					DureeSejourRefBean ds = dureeSejourCache.getDureSejour(paramDS.getIdDureeSejour());
					if (ds != null)
						results.add(ds);
				}
			}
		}
		return results;
	}
	
	public List<ParamDureeSejourRefBean> getElements() {
		return elements;
	}
}
