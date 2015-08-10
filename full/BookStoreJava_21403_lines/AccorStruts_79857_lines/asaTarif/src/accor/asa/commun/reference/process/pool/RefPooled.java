package com.accor.asa.commun.reference.process.pool;

import java.util.List;

import com.accor.asa.commun.DuplicateKeyException;
import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.ForeignKeyException;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.RefBean;
import com.accor.asa.commun.reference.persistance.IRefDAO;
import com.accor.asa.commun.reference.process.RefDAOProvider;
import com.accor.asa.commun.reference.process.RefFacade;

public class RefPooled implements RefFacade {

	private static final String DAO_PACKAGE = "com.accor.asa.commun.reference.persistance";

	public RefPooled () {
	}

	public CachableInterface getCacheRefList( String refKey, Contexte contexte ) throws TechnicalException, IncoherenceException {
		return getRefDAO(refKey).getCacheRefList(contexte);
	}

	public List<?> getRefList( String refKey, Contexte contexte ) throws TechnicalException {
		return getRefDAO(refKey).getRefList(contexte);
	}

	public List<?> getAdminRefList( String refKey, RefBean bean, Contexte contexte ) throws TechnicalException {
		return getRefDAO(refKey).getAdminRefList(contexte, bean);
	}

	public int insertRef( String refKey, RefBean bean, Contexte contexte ) throws TechnicalException, IncoherenceException, DuplicateKeyException {
		return getRefDAO(refKey).insertRef(contexte, bean);
	}

	public int updateRef( String refKey, RefBean bean, Contexte contexte ) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		return getRefDAO(refKey).updateRef(contexte, bean);
	}

	public int deleteRef( String refKey, RefBean bean, Contexte contexte ) throws TechnicalException, IncoherenceException, ForeignKeyException {
		return getRefDAO(refKey).deleteRef(contexte, bean);
	}

	public void refreshCacheRefList (String refKey, Contexte contexte) throws TechnicalException {
		getRefDAO(refKey).refreshCacheRefList(contexte);
	}

	private IRefDAO getRefDAO (String refKey) throws TechnicalException {
		IRefDAO daoInstance =  RefDAOProvider.getRefDAO(refKey);
		if(daoInstance==null)
		{
			String daoClassName = DAO_PACKAGE + "." + refKey + "RefDAO";
			try {
				Class<?> daoClass = Class.forName(daoClassName);
				daoInstance = (IRefDAO) daoClass.newInstance();
				
			}
			catch (ClassNotFoundException ex) {
				throw new TechnicalException("Pas de classe DAO pour le type : " + refKey + "\n" + ex.getMessage());
			}
			catch (InstantiationException ex) {
				throw new TechnicalException ("Impossible d'instancier la classe DAO : " + daoClassName + "\n" + ex.getMessage());
			}
			catch (IllegalAccessException ex) {
				throw new TechnicalException ("Impossible d'instancier la classe DAO : " + daoClassName + "\n" + ex.getMessage());
			}
		}
		return daoInstance;
	}

	public int doAction(int actionType, String refKey, RefBean bean, Contexte contexte) throws TechnicalException, IncoherenceException, ForeignKeyException, DuplicateKeyException {
		return getRefDAO(refKey).doAction(actionType, contexte, bean);
	}

	public RefBean getByIdentifier(String refKey,Contexte contexte, RefBean identifier) throws TechnicalException, IncoherenceException {
		return getRefDAO(refKey).getByIdentifier(contexte, identifier);
	}

	public List<?> getByCriteria(int actionType, String refKey, Contexte contexte, RefBean criteria) throws TechnicalException, IncoherenceException
	{
		return getRefDAO(refKey).getByCriteria(actionType, contexte, criteria);
	}

	public int doListAction(int actionType, String refKey, Contexte contexte, List<RefBean> beans) throws TechnicalException, IncoherenceException, ForeignKeyException, DuplicateKeyException {
		return getRefDAO(refKey).doListAction(actionType, contexte, beans);
	}

	

	
}