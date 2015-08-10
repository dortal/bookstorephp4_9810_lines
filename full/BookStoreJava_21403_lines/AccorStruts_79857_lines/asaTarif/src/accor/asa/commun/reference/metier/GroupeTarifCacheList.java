package com.accor.asa.commun.reference.metier;

import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;

@SuppressWarnings("serial")
public class GroupeTarifCacheList extends RefBeanCacheList implements CachableInterface {

	protected List<GroupeTarifRefBean> rateGroups;

	public  List<GroupeTarifRefBean> getElements() {
		return rateGroups;
	}

	public  GroupeTarifRefBean getGroupeTarifById(int idGroupeTarif) {
		return (GroupeTarifRefBean) getElementByCode(String.valueOf(idGroupeTarif));
	}

	public GroupeTarifCacheList(List<GroupeTarifRefBean> rateGroups, Contexte contexte) {
		this.rateGroups = rateGroups;
		setMap( rateGroups, contexte );
	}

}
