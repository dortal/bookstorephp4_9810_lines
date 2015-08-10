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
import com.accor.asa.commun.persistance.ForeignKeyException;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.ParamPeriodeGeneriqueCacheList;
import com.accor.asa.commun.reference.metier.ParamPeriodeGeneriqueMappedRefBean;
import com.accor.asa.commun.reference.metier.ParamPeriodeGeneriqueRefBean;
import com.accor.asa.commun.reference.metier.PeriodeGeneriqueRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class ParamPeriodeGeneriqueRefDAO extends RefDAO{
	
	public final static int LOAD_FOR_EXPORT=100;
	public final static int DUPLICATE=101;
	
	private static final String SELECT_PROC_NAME = "refparam_selAllPeriodeGen";
	private static final String FILTER_PROC_NAME = "refparam_selAdmPeriodeGen";
	private static final String INSERT_PROC_NAME = "refparam_addParamPeriodeGen";
	private static final String UPDATE_PROC_NAME = "refparam_updParamPeriodeGen";
	private static final String DELETE_PROC_NAME = "refparam_delParamPeriodeGen";

	private static final String EXPORT_PROC_NAME = "refparam_selExpPeriodeGener";
	private static final String DUPLICATE_PROC_NAME = "refparam_duplicatePeriodeGen";
	
	protected String getProcName(int type) {
		switch (type) {
		case SELECT:
			return SELECT_PROC_NAME;
		case ADMIN_SELECT:
			return FILTER_PROC_NAME;
		case INSERT:
			return INSERT_PROC_NAME;
		case UPDATE:
			return UPDATE_PROC_NAME;
		case DELETE:
			return DELETE_PROC_NAME;
		case LOAD_FOR_EXPORT :
			return EXPORT_PROC_NAME;
			
		case DUPLICATE :
			return DUPLICATE_PROC_NAME;
		default:
			return null;
		}
	}

	private static ParamPeriodeGeneriqueRefDAO instance = new ParamPeriodeGeneriqueRefDAO();

	public static ParamPeriodeGeneriqueRefDAO getInstance() {
		return instance;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return 	new ParamPeriodeGeneriqueCacheList((List<ParamPeriodeGeneriqueRefBean>) data);
	}


	protected String getCacheClassName() {
		return ParamPeriodeGeneriqueCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		return (CachableInterface) CacheManager.getInstance().getObjectInCache(
				ParamPeriodeGeneriqueCacheList.class);
	}

	protected SQLParamDescriptorSet getProcParameters(int type,
			RefBean bean, String codeLangue) {
		
		switch (type) {
		case SELECT:
		case LOAD_FOR_EXPORT:
			SQLParamDescriptor[] selectParams = {

			};
			return new SQLParamDescriptorSet(selectParams);
		case ADMIN_SELECT:
			PeriodeGeneriqueRefBean filter = (PeriodeGeneriqueRefBean)bean;
			SQLParamDescriptor[] adminSelectParams = {
					new SQLParamDescriptor(filter.getCode(),false,Types.VARCHAR)
			};
			return new SQLParamDescriptorSet(adminSelectParams);
		case INSERT:
		case UPDATE:
			ParamPeriodeGeneriqueRefBean temp = (ParamPeriodeGeneriqueRefBean)bean;
			SQLParamDescriptor[] writeParams = {
					new SQLParamDescriptor(temp.getCodePeriode(),false,Types.VARCHAR),
					new SQLParamDescriptor(temp.getCodeAsaCategory(),false, Types.VARCHAR),
					new SQLParamDescriptor(new Integer(temp.getNbMaxPeriodes()), false, Types.INTEGER)
			};
			return new SQLParamDescriptorSet(writeParams);
		case DELETE:
			temp = (ParamPeriodeGeneriqueRefBean)bean;
			SQLParamDescriptor[] deleteParams = {
					new SQLParamDescriptor(temp.getCodePeriode(),false,Types.VARCHAR),
					new SQLParamDescriptor(temp.getCodeAsaCategory(),false, Types.VARCHAR),
			};
			return new SQLParamDescriptorSet(deleteParams);
		default:
			return null;
		}
	}

	protected SQLResultSetReader getProcReader(int type) {
		switch (type) {
		case SELECT:
		case ADMIN_SELECT:
		return new SQLResultSetReader() {
			public Object instanciateFromLine(ResultSet rs)
					throws TechnicalException, SQLException {
				ParamPeriodeGeneriqueRefBean bean = new ParamPeriodeGeneriqueRefBean();
				bean.setCodeAsaCategory(rs.getString("codeAsaCategory"));
				bean.setCodePeriode(rs.getString("codePeriode"));
				bean.setNbMaxPeriodes(rs.getInt("nbMaxPeriodes"));
				return bean;
			}
		};
		case LOAD_FOR_EXPORT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs)
						throws TechnicalException, SQLException {
					ParamPeriodeGeneriqueRefBean bean = new ParamPeriodeGeneriqueRefBean();
					bean.setCodeAsaCategory(rs.getString("codeAsaCategory"));
					bean.setNbMaxPeriodes(rs.getInt("nbMaxPeriodes"));
					PeriodeGeneriqueRefBean pg = PeriodeGeneriqueRefDAO.buildStandardPeriodeGeneriqueFromRS(rs);
					bean.setPeriodeGenerique(pg);
					return bean;
				}

			};
		default:
			return null;
		}
		
	}


	@Override
	public int doAction(int actionType, Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		if(actionType==DUPLICATE)
			return duplicate(contexte, bean);
		return super.doAction(actionType, contexte, bean);
	}
	
	private int duplicate(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException 
	{
		SQLParamDescriptorSet[] params = createParams((ParamPeriodeGeneriqueMappedRefBean) bean);
		try {
			SQLCallExecuter.getInstance().executeListeUpdate(DUPLICATE_PROC_NAME, params, "ParamPeriodeGeneriqueRefDAO", "duplicate", contexte.getContexteAppelDAO());
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
		return 0;
	}
	
	
	private SQLParamDescriptorSet[] createParams(ParamPeriodeGeneriqueMappedRefBean data) {
		List<String> codesAsaCategory = data.getCodesAsaCategory();
		SQLParamDescriptorSet[] params = new SQLParamDescriptorSet[codesAsaCategory.size()];
		String codePeriode = data.getCodePeriode();
		String oldCodeAsa = data.getOldId();
	
		for (int i = 0; i < codesAsaCategory.size(); i++) {
			String codeAsaCat = codesAsaCategory.get(i);
			
			SQLParamDescriptor[] pd = new SQLParamDescriptor[] { 
					new SQLParamDescriptor(codePeriode, false, Types.VARCHAR),
					new SQLParamDescriptor(oldCodeAsa, false, Types.VARCHAR), 
					new SQLParamDescriptor(codeAsaCat, false, Types.VARCHAR), };
			params[i] = new SQLParamDescriptorSet(pd);
		}
		return params;
	}

}
