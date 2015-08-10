package com.accor.asa.commun.reference.persistance;

import java.util.List;

import com.accor.asa.commun.DuplicateKeyException;
import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.ForeignKeyException;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.RefBean;

public interface IRefDAO {

	public CachableInterface getCacheRefList( Contexte contexte ) throws TechnicalException, IncoherenceException;
	public List<?> getRefList( Contexte contexte ) throws TechnicalException;
	public List<?> getAdminRefList( Contexte contexte, RefBean bean ) throws TechnicalException;
	public int insertRef( Contexte contexte, RefBean bean ) throws TechnicalException, IncoherenceException, DuplicateKeyException;
	public int updateRef( Contexte contexte, RefBean bean ) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException;
	public int deleteRef( Contexte contexte, RefBean bean ) throws TechnicalException, IncoherenceException, ForeignKeyException;
	public void refreshCacheRefList( Contexte contexte ) throws TechnicalException;

	public int doAction(int actionType, Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException;
	public int doListAction(int actionType, Contexte contexte, List<RefBean> beans) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException;
	public RefBean getByIdentifier( Contexte contexte, RefBean identifier) throws TechnicalException, IncoherenceException;
	public List<?> getByCriteria(int actionType, Contexte contexte, RefBean criteria) throws TechnicalException, IncoherenceException;
}