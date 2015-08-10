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
import com.accor.asa.commun.reference.metier.ParamTransfertCacheList;
import com.accor.asa.commun.reference.metier.ParamTransfertMappedRefBean;
import com.accor.asa.commun.reference.metier.ParamTransfertRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class ParamTransfertRefDAO extends RefDAO {
	
	public static final int LOAD_FOR_EXPORT=100; 
	
	private static final String SELECT_PROC_NAME = "refparam_selAllTransferts";
	private static final String ADMIN_SELECT_PROC_NAME = "refparam_selAdmTransfert";
	private static final String INSERT_PROC_NAME = "refparam_addTransfert";
	private static final String UPDATE_PROC_NAME = null;
	private static final String DELETE_PROC_NAME = "refparam_delTransfert";
	
	private static final String EXPORT_PROC_NAME = "refparam_selExportTransfert";
	
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

		deleteRef(contexte, bean);
		return insertRef(contexte, bean);
	}

	public int insertRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException {
		
		SQLParamDescriptorSet[] params = createInsertParams((ParamTransfertMappedRefBean) bean);
		try {
			SQLCallExecuter.getInstance().executeListeUpdate(INSERT_PROC_NAME, params, getClass().getSimpleName(), "insertRef", contexte.getContexteAppelDAO());
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
		return 0;
	}
	
	private SQLParamDescriptorSet[] createInsertParams(ParamTransfertMappedRefBean data) {
		List<AsaCategory> asaCategories =data.getAsaCategories();
		SQLParamDescriptorSet[] params = new SQLParamDescriptorSet[asaCategories.size()];
		int idFamilleTarif = data.getIdFamilleTarif();
		int idPeriodeValidite = data.getIdPeriodeValidite();
	
		for (int i = 0; i < asaCategories.size(); i++) {
			String codeAsaCat = asaCategories.get(i).getCode();
			
			SQLParamDescriptor[] pd = new SQLParamDescriptor[] { 
					new SQLParamDescriptor(codeAsaCat, false, Types.VARCHAR),
					new SQLParamDescriptor(new Integer(idPeriodeValidite), false, Types.INTEGER), 
					new SQLParamDescriptor(new Integer(idFamilleTarif), false, Types.INTEGER) 
			};
			params[i] = new SQLParamDescriptorSet(pd);
		}
		return params;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new ParamTransfertCacheList((List<ParamTransfertRefBean>) data);
	}

	protected String getCacheClassName() {
		return ParamTransfertCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		return (CachableInterface) CacheManager.getInstance().getObjectInCache(ParamTransfertCacheList.class);
	}

	protected SQLResultSetReader getProcReader(int type) {
		switch (type) {
		case SELECT:
			return new SQLResultSetReader() {

				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					return buildStandardParamTRansfertBeanFromResult(rs);
				}
			};
		case ADMIN_SELECT:
			return new SQLResultSetReader() {

				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					return buildStandardParamTRansfertBeanFromResult(rs);
				}
			};
		case LOAD_FOR_EXPORT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					ParamTransfertRefBean pt= buildStandardParamTRansfertBeanFromResult(rs);
					FamilleTarifRefBean ft = FamilleTarifRefDAO.buildStandardFamilleTarifRefDAOFromRS(rs);
					pt.setFamilleTarif(ft);
					return pt;
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
		case LOAD_FOR_EXPORT:
			ParamTransfertMappedRefBean temp = (ParamTransfertMappedRefBean) bean;
			SQLParamDescriptor[] selectExpParams = {
					new SQLParamDescriptor(new Integer(temp.getIdPeriodeValidite()), false, Types.INTEGER)
			};	
			return new SQLParamDescriptorSet(selectExpParams);
		case ADMIN_SELECT:
			 temp = (ParamTransfertMappedRefBean) bean;
			SQLParamDescriptor[] selectAllParams = {
					new SQLParamDescriptor(new Integer(temp.getIdFamilleTarif()), false, Types.INTEGER),
					new SQLParamDescriptor(new Integer(temp.getIdPeriodeValidite()), false, Types.INTEGER)
			};
			return new SQLParamDescriptorSet(selectAllParams);
			
		case DELETE:
			temp = (ParamTransfertMappedRefBean) bean;
			SQLParamDescriptor[] deleteParams = { 
					new SQLParamDescriptor(new Integer(temp.getIdFamilleTarif()), false, Types.INTEGER),
					new SQLParamDescriptor(new Integer(temp.getIdPeriodeValidite()), false, Types.INTEGER) };
			return new SQLParamDescriptorSet(deleteParams);
		default:
			return null;
		}
	}
	
	public static ParamTransfertRefBean buildStandardParamTRansfertBeanFromResult(ResultSet rs) throws SQLException
	{
		ParamTransfertRefBean pt = new ParamTransfertRefBean();
		pt.setCodeAsaCategory(rs.getString("codeAsaCategory"));
		pt.setIdFamilleTarif(rs.getInt("idFamilleTarif"));
		pt.setIdPeriodeValidite(rs.getInt("idPeriodeValidite"));
		return pt;
	}

	
}
