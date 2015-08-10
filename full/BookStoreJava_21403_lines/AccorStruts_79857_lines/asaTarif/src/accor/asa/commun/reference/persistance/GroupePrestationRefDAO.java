package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.reference.ListGroupesPrestations;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.GroupePrestationRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class GroupePrestationRefDAO extends RefDAO {

	private static final String SELECT_PROC_NAME = "vente_Dref_Select_Groupe_Prest";
	private static final String ADMIN_SELECT_PROC_NAME = null;
	private static final String INSERT_PROC_NAME = null;
	private static final String UPDATE_PROC_NAME = null;
	private static final String DELETE_PROC_NAME = null;

	protected String getProcName (int type) {
		switch (type) {
			case SELECT :
				return SELECT_PROC_NAME;
			case ADMIN_SELECT :
				return ADMIN_SELECT_PROC_NAME;
			case INSERT :
				return INSERT_PROC_NAME;
			case UPDATE :
				return UPDATE_PROC_NAME;
			case DELETE :
				return DELETE_PROC_NAME;
			default :
				return null;
		}
	}

	protected SQLParamDescriptorSet getProcParameters (int type, RefBean bean, String codeLangue) {
		switch (type) {
			case SELECT :
				return new SQLParamDescriptorSet();
			case ADMIN_SELECT :
				// Pas utilisé
				return null;
			case INSERT :
				// Pas utilisé
				return null;
			case UPDATE :
				// Pas utilisé
				return null;
			case DELETE :
				// Pas utilisé
				return null;
			default :
				return null;
		}
	}

	protected SQLResultSetReader getProcReader (int type) {
		switch (type) {
			case SELECT :
				return new SQLResultSetReader() {
					public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
						GroupePrestationRefBean groupePrestation = new GroupePrestationRefBean();
						groupePrestation.setId(Integer.toString(rs.getInt("CODE")));
						groupePrestation.setLibelle(rs.getString("LIBELLE_GROUPE_PRESTATION"));
						return groupePrestation;
					}
				};
			case ADMIN_SELECT :
				return null;
			default :
				return null;
		}
	}

	protected String getCacheClassName () {
		return ListGroupesPrestations.class.getName();
	}

	protected CachableInterface getObjectInCache (String codeLangue) {
		return (CachableInterface) CacheManager.getInstance().getObjectInCache(ListGroupesPrestations.class);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new ListGroupesPrestations((List<GroupePrestationRefBean>) data);
	}

	

}