package com.accor.asa.commun.metier.categorie;

import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.Element;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;

@SuppressWarnings("serial")
public class AsaCategory extends Element{

	public AsaCategory() {
		super();
	}

	public AsaCategory(String code, String libelle) {
		super(code, libelle);
	}

	
	public static List<AsaCategory> getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException
	{
		return PoolCommunFactory.getInstance().getCommunUtilsFacade().getCategories( contexte );
	}
	
}