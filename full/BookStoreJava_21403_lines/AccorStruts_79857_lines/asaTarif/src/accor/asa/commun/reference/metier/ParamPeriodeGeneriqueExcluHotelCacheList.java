package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;

@SuppressWarnings("serial")
public class ParamPeriodeGeneriqueExcluHotelCacheList extends RefParamBeanCacheList implements CachableInterface {

	private List<ParamPeriodeGeneriqueExcluHotelRefBean> elements;
	
	public ParamPeriodeGeneriqueExcluHotelCacheList(List<ParamPeriodeGeneriqueExcluHotelRefBean> elements) {
		this.elements = elements;
		setMap(elements);
	}

	public List<ParamPeriodeGeneriqueExcluHotelRefBean> getElements() {
		return elements;
	}
	
	public int getNbMaxPeriodes(String codePeriode, String codeAsaCategory, String codeHotel) {
		ParamPeriodeGeneriqueExcluHotelRefBean bean = (ParamPeriodeGeneriqueExcluHotelRefBean) getElement(ParamPeriodeGeneriqueExcluHotelRefBean.buildCompositeKey(codePeriode, codeAsaCategory, codeHotel));
		if (bean == null)
			return 0;
		return bean.getNbMaxPeriodes();
	}

}
