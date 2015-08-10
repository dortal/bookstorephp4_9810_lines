package com.accor.asa.commun.reference.persistance;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.DuplicateKeyException;
import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.DAO;
import com.accor.asa.commun.persistance.ForeignKeyException;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.RefBean;

public abstract class RefDAO extends DAO implements IRefDAO {

	// Type
	protected static final int SELECT = 1;
	protected static final int ADMIN_SELECT = 2;
	protected static final int INSERT = 3;
	protected static final int UPDATE = 4;
	protected static final int DELETE = 5;
	protected static final int GET_BY_ID = 6;
	protected static final int ADMIN_EXPORT = 7;

	// Langue de l'administrateur
	protected static final String ADMIN_LANGUAGE = "GB";

	// Codes erreur
	protected static final int SYBASE_INTEGRITY_CONSTRAINT_ERROR_CODE = 547;

	// Divers (utilisé principalement dans les anciennes procs)
	protected static final String IS_NEW = "y";
	protected static final String IS_NOT_NEW = "n";
	protected static final int ID_UNKNOWN = 2;
	protected static final int DELETE_ERROR = 3;
	protected static final int UPDATE_ERROR = 4;

	protected abstract String getProcName(int type);

	protected abstract SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue);

	protected abstract SQLResultSetReader getProcReader(int type);

	protected abstract String getCacheClassName();

	protected abstract CachableInterface getObjectInCache(String codeLangue);

	//protected abstract CachableInterface createCacheObject(List<RefBean> data, String codeLangue);

	private String checkProcName(int type) throws TechnicalException {
		String procName = getProcName(type);
		if (StringUtils.isBlank(procName))
			throw new TechnicalException(getClass() + ": getProcName(" + type + ") : methode non-implementee");
		return procName;
	}

	private SQLParamDescriptorSet checkProcParameters(int type, RefBean refBean, String codeLangue) throws TechnicalException {
		SQLParamDescriptorSet procParameters = getProcParameters(type, refBean, codeLangue);
		if (procParameters == null)
			throw new TechnicalException("getProcParameters(" + type + ") : methode non-implementee");
		return procParameters;
	}

	private SQLResultSetReader checkProcReader(int type) throws TechnicalException {
		SQLResultSetReader procReader = getProcReader(type);
		if (procReader == null)
			throw new TechnicalException("getProcReader(" + type + ") : methode non-implementee");
		return procReader;
	}

	private String checkCacheClassName() throws TechnicalException {
		String cacheClassName = getCacheClassName();
		if (StringUtils.isBlank(cacheClassName))
			throw new TechnicalException("getCacheClassName() : methode non-implementee");
		return cacheClassName;
	}

	/**
	 * Méthode permettant de récupérer la liste des données de référence en
	 * cache
	 */
	public CachableInterface getCacheRefList(Contexte contexte) throws TechnicalException, IncoherenceException {
		String codeLangue = contexte.getCodeLangue();
		CachableInterface cacheRefList = getObjectInCache(codeLangue);
		if (cacheRefList == null) {
			List<?> result = getRefList(contexte);
			cacheRefList = createCacheObject(result, contexte);
			if (cacheRefList == null)
				throw new TechnicalException("createCacheObject() : methode non-implementee");
			CacheManager.getInstance().setObjectInCache(cacheRefList);
		}
		return cacheRefList;
	}

	protected abstract CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException ;
	/*{
		return createCacheObject(data, contexte.getCodeLangue());
	}*/

	/**
	 * Méthode permettant de récupérer la liste des données de référence
	 */
	public List<?> getRefList(Contexte contexte) throws TechnicalException {
		try {
			String procName = checkProcName(SELECT);
			SQLParamDescriptorSet procParams = checkProcParameters(SELECT, null, contexte.getCodeLangue());
			SQLResultSetReader procReader = checkProcReader(SELECT);
			List<?> result = SQLCallExecuter.getInstance().executeSelectProcSansLimite(procName, procParams, "RefDAO", "getRefList", contexte.getContexteAppelDAO(), procReader);
			LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), procName, "SELECT", "");
			LogCommun.debug("RefDAO", "getRefList", "Taille resultat = " + result.size());
			return result;
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}

	/**
	 * Méthode permettant de récupérer la liste des données de référence pour
	 * asaAdmin Il n'y a aucune limite de taille de résultat
	 * 
	 * @param Le
	 *            contexte d'appel
	 * @param Le
	 *            bean contenant les paramètres de filtrage, s'il y en a
	 * @throws TechnicalException
	 */
	public List<?> getAdminRefList(Contexte contexte, RefBean bean) throws TechnicalException {
		try {
			String procName = getProcName(ADMIN_SELECT);
			SQLParamDescriptorSet procParams = checkProcParameters(ADMIN_SELECT, bean, ADMIN_LANGUAGE);
			SQLResultSetReader procReader = checkProcReader(ADMIN_SELECT);

			List<?> result = SQLCallExecuter.getInstance().executeSelectProcSansLimite(procName, procParams, "RefDAO", "getAdminRefList", contexte.getContexteAppelDAO(), procReader);
			LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), procName, "SELECT", "");
			LogCommun.debug("RefDAO", "getAdminRefList", "Taille resultat = " + result.size());
			return result;
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}

	/**
	 * Méthode permettant d'insérer une donnée de référence Est parfois
	 * redéfinie dans certaines sous-classes, lorsqu'on a besoin de récupérer le
	 * code retour
	 * 
	 * @throws DuplicateKeyException
	 *             Lorsqu'on essaie d'insérer un objet avec un ID déjà existant
	 */
	public int insertRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException {
		try {
			int res = -1;
			String procName = getProcName(INSERT);
			SQLParamDescriptorSet procParams = checkProcParameters(INSERT, bean, ADMIN_LANGUAGE);

			res = SQLCallExecuter.getInstance().executeUpdate(procName, procParams, "RefDAO", "insertRef", contexte.getContexteAppelDAO());
			LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), procName, "INSERT", bean.toString());
			LogCommun.debug("RefDAO", "insertRef", "Code retour = " + res);
			return res;
		} catch (SQLException e) {
			if (e.getErrorCode() == SYBASE_PRIMARY_KEY_ERROR_CODE) {
				throw new DuplicateKeyException(e);
			}
			throw new TechnicalException(e);
		}
	}

	/**
	 * Méthode permettant de mettre à jour une donnée de référence Est parfois
	 * redéfinie dans certaines sous-classes, lorsqu'on a besoin de récupérer le
	 * code retour
	 * 
	 * @throws DuplicateKeyException
	 *             Lorsqu'on essaie d'insérer un objet avec un ID déjà existant
	 * @throws ForeignKeyException
	 *             Lorsqu'on essaie de supprimer un objet référencé ailleurs
	 */
	public int updateRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		try {
			int res = -1;
			String procName = getProcName(UPDATE);
			SQLParamDescriptorSet procParams = checkProcParameters(UPDATE, bean, ADMIN_LANGUAGE);

			res = SQLCallExecuter.getInstance().executeUpdate(procName, procParams, "RefDAO", "updateRef", contexte.getContexteAppelDAO());
			LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), procName, "UPDATE", bean.toString());
			LogCommun.debug("RefDAO", "updateRef", "Code retour = " + res);
			return res;
		} catch (SQLException e) {
			if (e.getErrorCode() == SYBASE_PRIMARY_KEY_ERROR_CODE) {
				throw new DuplicateKeyException(e);
			}
			throw new TechnicalException(e);
		}
	}

	/**
	 * Méthode permettant de supprimer une donnée de référence Est parfois
	 * redéfinie dans certaines sous-classes, lorsqu'on a besoin de récupérer le
	 * code retour
	 * 
	 * @throws ForeignKeyException
	 *             Lorsqu'on essaie de supprimer un objet référencé ailleurs
	 * @throws TechnicalException
	 */
	public int deleteRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, ForeignKeyException {
		try {
			int res;
            String procName = getProcName(DELETE);
            SQLParamDescriptorSet procParams = checkProcParameters(DELETE, bean, ADMIN_LANGUAGE);
			res = SQLCallExecuter.getInstance().executeUpdate(procName, procParams, "RefDAO", "deleteRef", contexte.getContexteAppelDAO());
            LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), procName, "DELETE", bean.getId());
            LogCommun.debug("RefDAO", "deleteRef", "Code retour = " + res);
            return res;
		} catch (SQLException e) {
            if (    e.getErrorCode() == SYBASE_FOREIGN_KEY_ERROR_CODE_2 ||
                    e.getErrorCode() == SYBASE_FOREIGN_KEY_ERROR_CODE   )
                throw new ForeignKeyException(e); 
            else
                throw new TechnicalException(e);
		}
	}

	/**
	 * Méthode permettant de rafraichir le cache d'une donnée de référence Est
	 * parfois redéfinie dans certaines sous-classes, lorsqu'il y a plusieurs
	 * caches à rafraichir par exemple
	 * 
	 * @throws TechnicalException
	 */
	public void refreshCacheRefList(Contexte contexte) throws TechnicalException {
		String idOriginator = CacheManager.getInstance().getIdOriginator();
		String cacheClassName = checkCacheClassName();
		CacheManager.getInstance().refreshInstanceCaches(cacheClassName, idOriginator, null);
		LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), cacheClassName, "REFRESH", "");
		LogCommun.debug("RefDAO", "refreshCacheRefList", "ID = " + idOriginator);
	}

	public int doAction(int actionType, Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		try {
			int res = -1;
			String procName = getProcName(actionType);
			SQLParamDescriptorSet procParams = getProcParameters(actionType, bean, ADMIN_LANGUAGE);

			res = SQLCallExecuter.getInstance().executeUpdate(procName, procParams, "ParamGrilleRefDAO", "doAction " + actionType, contexte.getContexteAppelDAO());
			LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), procName, "UPDATE", bean.toString());
			LogCommun.debug("RefDAO", "doAction " + actionType, "Code retour = " + res);
			return res;
		} catch (SQLException e) {
			if (e.getErrorCode() == SYBASE_PRIMARY_KEY_ERROR_CODE) {
				throw new DuplicateKeyException(e);
			}
			throw new TechnicalException(e);
		}
	}

	public List<?> getByCriteria(int actionType, Contexte contexte, RefBean criteria) throws TechnicalException, IncoherenceException {
		try {
			String procName = checkProcName(actionType);
			SQLParamDescriptorSet procParams = checkProcParameters(actionType, criteria, contexte.getCodeLangue());
			SQLResultSetReader procReader = checkProcReader(actionType);
			List<?> result = SQLCallExecuter.getInstance().executeSelectProc(procName, procParams, getClass().getName(), "getByCriteria:" + actionType, contexte.getContexteAppelDAO(), procReader);
			LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), procName, "SELECT", "");
			LogCommun.debug(getClass().getName(), "getByCriteria:" + actionType, "Taille resultat = " + result.size());
			return result;
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
	}

	public RefBean getByIdentifier(Contexte contexte, RefBean identifier) throws TechnicalException, IncoherenceException {
		RefBean result = null;
		try {
			String procName = checkProcName(GET_BY_ID);
			SQLParamDescriptorSet procParams = checkProcParameters(GET_BY_ID, identifier, contexte.getCodeLangue());
			SQLResultSetReader procReader = checkProcReader(GET_BY_ID);

			List<?> results = SQLCallExecuter.getInstance().executeSelectProc(procName, procParams, "RefDAO", "getByIdentifier", contexte.getContexteAppelDAO(), procReader);
			LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), procName, "SELECT", "");
			if (!results.isEmpty())
				result = (RefBean) results.get(0);
			LogCommun.debug("RefDAO", "getByIdentifier", "resultat = " + result);
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
		return result;
	}

	
	public int doListAction(int actionType, Contexte contexte, List<RefBean> beans) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		return 0;
	}

}