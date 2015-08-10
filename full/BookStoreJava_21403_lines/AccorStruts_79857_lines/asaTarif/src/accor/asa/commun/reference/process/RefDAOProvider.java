package com.accor.asa.commun.reference.process;

import java.util.HashMap;

import com.accor.asa.commun.reference.persistance.IRefDAO;
import com.accor.asa.commun.reference.persistance.controle.TarifControleRefDAO;

public class RefDAOProvider {
	
	private static HashMap<String, IRefDAO> refDaoMap = new HashMap<String, IRefDAO>();
	
	static
	{
		registerRefDAO(RefFacade.TARIF_CONTROLE_KEY, TarifControleRefDAO.getInstance());
	}
	public static void registerRefDAO(String refKey, IRefDAO instance)
	{
		refDaoMap.put(refKey, instance);
	}
	
	public static IRefDAO getRefDAO(String refKey)
	{
		return refDaoMap.get(refKey);
	}

}
