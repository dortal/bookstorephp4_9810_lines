package com.accor.asa.commun.reference.process.pool;

import java.util.List;

import com.accor.asa.commun.DuplicateKeyException;
import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.ForeignKeyException;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.pool.GlobalPoolException;
import com.accor.asa.commun.process.pool.GlobalPoolable;
import com.accor.asa.commun.reference.metier.RefBean;
import com.accor.asa.commun.reference.process.RefFacade;

public class RefPoolable extends GlobalPoolable implements RefFacade {

	/** Init du pool en static */
	static {
		initPool(RefFacade.POOL_NAME);
	}

	public CachableInterface getCacheRefList( String refKey, Contexte contexte ) throws TechnicalException, IncoherenceException {
		RefPooled objectPooled = null;
		try {
			objectPooled = (RefPooled) getObjectPooled(RefFacade.POOL_NAME);
			return objectPooled.getCacheRefList(refKey, contexte);
		}
		catch (GlobalPoolException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "getCacheRefList", "Exception au call en mode pool", ex);
			throw new TechnicalException(ex);
		}
		catch (TechnicalException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "getCacheRefList", "Exception au call en mode pool", ex);
			throw ex;
		}
		finally {
			returnObjectToPool(RefFacade.POOL_NAME, objectPooled);
		}
	}

	public List<?> getRefList (String refKey, Contexte contexte) throws TechnicalException {
		RefPooled objectPooled = null;
		try {
			objectPooled = (RefPooled) getObjectPooled(RefFacade.POOL_NAME);
			return objectPooled.getRefList(refKey, contexte);
		}
		catch (GlobalPoolException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "getRefList", "Exception au call en mode pool", ex);
			throw new TechnicalException(ex);
		}
		catch (TechnicalException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "getRefList", "Exception au call en mode pool", ex);
			throw ex;
		}
		finally {
			returnObjectToPool(RefFacade.POOL_NAME, objectPooled);
		}
	}

	public List<?> getAdminRefList (String refKey, RefBean bean, Contexte contexte) throws TechnicalException {
		RefPooled objectPooled = null;
		try {
			objectPooled = (RefPooled) getObjectPooled(RefFacade.POOL_NAME);
			return objectPooled.getAdminRefList(refKey, bean, contexte);
		}
		catch (GlobalPoolException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "getAdminRefList", "Exception au call en mode pool", ex);
			throw new TechnicalException(ex);
		}
		catch (TechnicalException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "getAdminRefList", "Exception au call en mode pool", ex);
			throw ex;
		}
		finally {
			returnObjectToPool(RefFacade.POOL_NAME, objectPooled);
		}
	}

	public int insertRef (String refKey, RefBean bean, Contexte contexte) throws TechnicalException, IncoherenceException, DuplicateKeyException {
		RefPooled objectPooled = null;
		try {
			objectPooled = (RefPooled) getObjectPooled(RefFacade.POOL_NAME);
			return objectPooled.insertRef(refKey, bean, contexte);
		}
		catch (GlobalPoolException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "insertRef", "Exception au call en mode pool", ex);
			throw new TechnicalException(ex);
		}
		catch (TechnicalException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "insertRef", "Exception au call en mode pool", ex);
			throw ex;
		}
		finally {
			returnObjectToPool(RefFacade.POOL_NAME, objectPooled);
		}
	}

	public int updateRef (String refKey, RefBean bean, Contexte contexte) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		RefPooled objectPooled = null;
		try {
			objectPooled = (RefPooled) getObjectPooled(RefFacade.POOL_NAME);
			return objectPooled.updateRef(refKey, bean, contexte);
		}
		catch (GlobalPoolException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "updateRef", "Exception au call en mode pool", ex);
			throw new TechnicalException(ex);
		}
		catch (TechnicalException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "updateRef", "Exception au call en mode pool", ex);
			throw ex;
		}
		finally {
			returnObjectToPool(RefFacade.POOL_NAME, objectPooled);
		}
	}

	public int deleteRef (String refKey, RefBean bean, Contexte contexte) throws TechnicalException, IncoherenceException, ForeignKeyException {
		RefPooled objectPooled = null;
		try {
			objectPooled = (RefPooled) getObjectPooled(RefFacade.POOL_NAME);
			return objectPooled.deleteRef(refKey, bean, contexte);
		}
		catch (GlobalPoolException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "deleteRef", "Exception au call en mode pool", ex);
			throw new TechnicalException(ex);
		}
		catch (TechnicalException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "deleteRef", "Exception au call en mode pool", ex);
			throw ex;
		}
		finally {
			returnObjectToPool(RefFacade.POOL_NAME, objectPooled);
		}
	}

	public void refreshCacheRefList (String refKey, Contexte contexte) throws TechnicalException {
		RefPooled objectPooled = null;
		try {
			objectPooled = (RefPooled) getObjectPooled(RefFacade.POOL_NAME);
			objectPooled.refreshCacheRefList(refKey, contexte);
		}
		catch (GlobalPoolException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "refreshCacheRefList", "Exception au call en mode pool", ex);
			throw new TechnicalException(ex);
		}
		catch (TechnicalException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "refreshCacheRefList", "Exception au call en mode pool", ex);
			throw ex;
		}
		finally {
			returnObjectToPool(RefFacade.POOL_NAME, objectPooled);
		}
	}

	public int doAction(int actionType, String refKey, RefBean bean, Contexte contexte) throws TechnicalException, IncoherenceException, ForeignKeyException, DuplicateKeyException {
		RefPooled objectPooled = null;
		try {
			objectPooled = (RefPooled) getObjectPooled(RefFacade.POOL_NAME);
			return objectPooled.doAction(actionType, refKey, bean, contexte);
		}
		catch (GlobalPoolException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "doAction", "Exception au call en mode pool", ex);
			throw new TechnicalException(ex);
		}
		catch (TechnicalException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "doAction", "Exception au call en mode pool", ex);
			throw ex;
		}
		finally {
			returnObjectToPool(RefFacade.POOL_NAME, objectPooled);
		}
	}

	public RefBean getByIdentifier(String refKey, Contexte contexte, RefBean identifier) throws TechnicalException, IncoherenceException {
		RefPooled objectPooled = null;
		try {
			objectPooled = (RefPooled) getObjectPooled(RefFacade.POOL_NAME);
			return objectPooled.getByIdentifier(refKey, contexte, identifier);
		}
		catch (GlobalPoolException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "getByIdentifier", "Exception au call en mode pool", ex);
			throw new TechnicalException(ex);
		}
		catch (TechnicalException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "getByIdentifier", "Exception au call en mode pool", ex);
			throw ex;
		}
		finally {
			returnObjectToPool(RefFacade.POOL_NAME, objectPooled);
		}
	}

	public List<?> getByCriteria(int actionType, String refKey, Contexte contexte, RefBean criteria) throws TechnicalException, IncoherenceException {
		RefPooled objectPooled = null;
		try {
			objectPooled = (RefPooled) getObjectPooled(RefFacade.POOL_NAME);
			return objectPooled.getByCriteria(actionType, refKey, contexte, criteria);
		}
		catch (GlobalPoolException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "getByCriteria", "Exception au call en mode pool", ex);
			throw new TechnicalException(ex);
		}
		catch (TechnicalException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "getByCriteria", "Exception au call en mode pool", ex);
			throw ex;
		}
		finally {
			returnObjectToPool(RefFacade.POOL_NAME, objectPooled);
		}
	}

	public int doListAction(int actionType, String refKey,  Contexte contexte, List<RefBean> beans) throws TechnicalException, IncoherenceException,ForeignKeyException, DuplicateKeyException {
		RefPooled objectPooled = null;
		try {
			objectPooled = (RefPooled) getObjectPooled(RefFacade.POOL_NAME);
			return objectPooled.doListAction(actionType, refKey, contexte, beans);
		}
		catch (GlobalPoolException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "doListAction", "Exception au call en mode pool", ex);
			throw new TechnicalException(ex);
		}
		catch (TechnicalException ex) {
			LogCommun.minor("COMMUN", "RefPoolable", "doListAction", "Exception au call en mode pool", ex);
			throw ex;
		}
		finally {
			returnObjectToPool(RefFacade.POOL_NAME, objectPooled);
		}
	}


	
}