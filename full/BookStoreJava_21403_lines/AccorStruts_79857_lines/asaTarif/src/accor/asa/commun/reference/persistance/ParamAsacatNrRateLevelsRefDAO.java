package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import com.accor.asa.commun.DuplicateKeyException;
import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.categorie.AsaCategory;
import com.accor.asa.commun.persistance.ForeignKeyException;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.FamilleTarifRefBean;
import com.accor.asa.commun.reference.metier.ParamAsacatNrRateLevelsMappedRefBean;
import com.accor.asa.commun.reference.metier.ParamAsacatNrRateLevelsRefBean;
import com.accor.asa.commun.reference.metier.ParamAsacatNrRatelevelsCacheList;
import com.accor.asa.commun.reference.metier.RefBean;

public class ParamAsacatNrRateLevelsRefDAO extends RefDAO {

	public static final int LOAD_FOR_EXPORT=100;
	
	private static final String SELECT_PROC_NAME = "refparam_selAllAsacatNrRL";
	private static final String ADMIN_SELECT_PROC_NAME = "refparam_selAdmAsacatNrRL";
	private static final String INSERT_PROC_NAME = "refparam_addAsacatNrRateLev";
	private static final String UPDATE_PROC_NAME = null;
	private static final String DELETE_PROC_NAME = "refparam_delAdmAsacatNrRL";

	private static final String EXPORT_PROC_NAME = "refparam_selExpAsacatNrRL";
	
	protected String getProcName(int type) {
		switch (type) {
		case SELECT:
			return SELECT_PROC_NAME;
		case ADMIN_SELECT:
			return ADMIN_SELECT_PROC_NAME;
		case INSERT:
			return INSERT_PROC_NAME;
		case UPDATE:
			return UPDATE_PROC_NAME;
		case DELETE:
			return DELETE_PROC_NAME;
		case LOAD_FOR_EXPORT:
			return EXPORT_PROC_NAME;
		default:
			return null;
		}
	}

	public int updateRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
			deleteRef( contexte, bean);
			return insertRef( contexte, bean);
	}


	public int insertRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException {
		SQLParamDescriptorSet[] params = createInsertParams((ParamAsacatNrRateLevelsMappedRefBean) bean);
		try {
			SQLCallExecuter.getInstance().executeListeUpdate(INSERT_PROC_NAME, params, getClass().getSimpleName(), "insertRef", contexte.getContexteAppelDAO());
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
		return 0;
	}
	
	private SQLParamDescriptorSet[] createInsertParams(ParamAsacatNrRateLevelsMappedRefBean data) {
		List<AsaCategory> asaCategories =data.getAsaCategories();
		SQLParamDescriptorSet[] params = new SQLParamDescriptorSet[asaCategories.size()];
		int idFamilleFilter = data.getIdFamilleTarif();
		int nrRateLevels = data.getNrRateLevels();
		int idPeriodeValidite = data.getIdPeriodeValidite();
	
		for (int i = 0; i < asaCategories.size(); i++) {
			String codeAsaCat = asaCategories.get(i).getCode();
			SQLParamDescriptor[] pd = new SQLParamDescriptor[] { 
					new SQLParamDescriptor(new Integer(idFamilleFilter), false, Types.INTEGER),
					new SQLParamDescriptor(codeAsaCat, false, Types.VARCHAR), 
					new SQLParamDescriptor(new Integer(idPeriodeValidite), false, Types.INTEGER),
					new SQLParamDescriptor(new Integer(nrRateLevels), false, Types.INTEGER)
			};
			params[i] = new SQLParamDescriptorSet(pd);
		}
		return params;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new ParamAsacatNrRatelevelsCacheList((List<ParamAsacatNrRateLevelsRefBean>) data);
	}

	protected String getCacheClassName() {
		return ParamAsacatNrRatelevelsCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		return (CachableInterface) CacheManager.getInstance().getObjectInCache(ParamAsacatNrRatelevelsCacheList.class);
	}

	protected SQLResultSetReader getProcReader(int type) {

		switch (type) {

		case SELECT:
		case ADMIN_SELECT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					return buildStandardBeanFromRS(rs);
				}
			};
		case LOAD_FOR_EXPORT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					ParamAsacatNrRateLevelsRefBean bean= buildStandardBeanFromRS(rs);
					FamilleTarifRefBean familleTarif = FamilleTarifRefDAO.buildStandardFamilleTarifRefDAOFromRS(rs);
					bean.setFamilleTarif(familleTarif);
					return bean;
				}
			};
		default:
			return null;
		}
	}

	protected SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue) {

		switch (type) {
		case SELECT:
			SQLParamDescriptor[] selectParams = {};
			return new SQLParamDescriptorSet(selectParams);
		case ADMIN_SELECT:
			ParamAsacatNrRateLevelsMappedRefBean filter = (ParamAsacatNrRateLevelsMappedRefBean) bean;
			SQLParamDescriptor[] selectAllParams = { 
					new SQLParamDescriptor(new Integer(filter.getIdFamilleTarif()), false, Types.INTEGER),
					new SQLParamDescriptor(new Integer(filter.getIdPeriodeValidite()), false, Types.INTEGER)
					};
			return new SQLParamDescriptorSet(selectAllParams);
			
		case DELETE:
			ParamAsacatNrRateLevelsMappedRefBean temp = (ParamAsacatNrRateLevelsMappedRefBean) bean;
			SQLParamDescriptor[] deleteParams = { 
					new SQLParamDescriptor(new Integer(temp.getIdFamilleTarif()), false, Types.INTEGER),
					new SQLParamDescriptor(new Integer(temp.getNrRateLevels()), false, Types.INTEGER),
					new SQLParamDescriptor(new Integer(temp.getIdPeriodeValidite()), false, Types.INTEGER)
			};
			return new SQLParamDescriptorSet(deleteParams);
		case LOAD_FOR_EXPORT:
			 filter = (ParamAsacatNrRateLevelsMappedRefBean) bean;
			SQLParamDescriptor[] selectExpParams = { 
					new SQLParamDescriptor(new Integer(filter.getIdPeriodeValidite()), false, Types.INTEGER)
					};
			return new SQLParamDescriptorSet(selectExpParams);	
		default:
			return null;
		}
	}
	
	public static ParamAsacatNrRateLevelsRefBean buildStandardBeanFromRS(ResultSet rs) throws SQLException
	{
		ParamAsacatNrRateLevelsRefBean bean = new ParamAsacatNrRateLevelsRefBean();
		bean.setCodeAsaCategory(rs.getString("codeAsacategory"));
		bean.setIdFamilleTarif(rs.getInt("idFamilleTarif"));
		bean.setIdPeriodeValidite(rs.getInt("idPeriodeValidite"));
		bean.setNrRateLevels(rs.getInt("nbreRateLevel"));
		return bean;
	}
}
