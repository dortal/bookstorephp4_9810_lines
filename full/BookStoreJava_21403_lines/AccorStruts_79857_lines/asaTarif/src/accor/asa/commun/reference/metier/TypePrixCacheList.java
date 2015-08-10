package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;

@SuppressWarnings( { "serial" })
public class TypePrixCacheList extends RefBeanCacheList implements CachableInterface {

	protected List<TypePrixRefBean> priceTypes;

	public List<TypePrixRefBean> getElements() {
		return priceTypes;
	}

	public TypePrixRefBean getTypePrixById(int idTypePrix) {
		return (TypePrixRefBean) getElementByCode(String.valueOf(idTypePrix));
	}

	public TypePrixCacheList(List<TypePrixRefBean> priceTypes, Contexte contexte) {
		this.priceTypes = priceTypes;
		setMap( priceTypes, contexte );
	}

    public List<TypePrixRefBean> getElementsWithOutCriteria(boolean WithOutNone, boolean WithOutIncluded, boolean WithOutDiscount) {
        List<TypePrixRefBean>  typesPrix = new ArrayList<TypePrixRefBean>();
        List<TypePrixRefBean> all = getElements();
        for(TypePrixRefBean tp:all) {
            if( (!WithOutNone || !tp.getId().equals(TypePrixRefBean.ID_TYPEPRIX_NONE)) &&
                (!WithOutIncluded || !tp.getId().equals(TypePrixRefBean.ID_TYPEPRIX_INCLUDED)) &&
                (!WithOutDiscount || !tp.isDiscount()) )
                typesPrix.add(tp);
        }
        return typesPrix;
    }
}
