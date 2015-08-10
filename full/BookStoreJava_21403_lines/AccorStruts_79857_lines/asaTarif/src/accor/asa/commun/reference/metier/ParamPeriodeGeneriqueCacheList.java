package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ParamPeriodeGeneriqueCacheList extends RefParamBeanCacheList implements CachableInterface {

	private List<ParamPeriodeGeneriqueRefBean> elements;
	
	public ParamPeriodeGeneriqueCacheList(List<ParamPeriodeGeneriqueRefBean> elements) {
		this.elements = elements;
		setMap(elements);
	}

	public List<ParamPeriodeGeneriqueRefBean> getElements() {
		return elements;
	}
	
	public int getNbMaxPeriodes(String codePeriode, String codeAsaCategory) {
		ParamPeriodeGeneriqueRefBean bean = (ParamPeriodeGeneriqueRefBean) getElement(ParamPeriodeGeneriqueRefBean.buildCompositeKey(codePeriode, codeAsaCategory));
		if (bean == null)
			return 0;
		return bean.getNbMaxPeriodes();
	}

}
