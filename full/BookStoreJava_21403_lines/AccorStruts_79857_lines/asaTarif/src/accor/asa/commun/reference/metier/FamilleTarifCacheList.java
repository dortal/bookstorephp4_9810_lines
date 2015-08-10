package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.List;

import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;

@SuppressWarnings("serial")
public class FamilleTarifCacheList extends RefBeanCacheList implements CachableInterface {

	protected List<FamilleTarifRefBean> rateFamillies;

	public  List<FamilleTarifRefBean> getElements()	{
		return rateFamillies;
	}
	
	public  FamilleTarifRefBean getFamilleTarifById(int idFamilleTarif)	{
		return (FamilleTarifRefBean)getElementByCode(String.valueOf(idFamilleTarif));
	}

	public List<FamilleTarifRefBean> getFamillesTarifByGroupeTarif(int idGroupeTarif) {
		List<FamilleTarifRefBean> famillesTarif = new ArrayList<FamilleTarifRefBean>();
		List<FamilleTarifRefBean> all = getElements();
		for(FamilleTarifRefBean ft:all)
		{
			if(ft.getIdGroupeTarif()==idGroupeTarif)
				famillesTarif.add(ft);
		}
		return famillesTarif;
	}
	
	public FamilleTarifCacheList(List<FamilleTarifRefBean> rateFamillies, Contexte contexte) {
		this.rateFamillies = rateFamillies;
		setMap( rateFamillies, contexte );
	}
	
	

	
}
