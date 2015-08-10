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
import com.accor.asa.commun.reference.metier.ParamTarifGOCacheList;
import com.accor.asa.commun.reference.metier.ParamTarifGOMappedRefBean;
import com.accor.asa.commun.reference.metier.ParamTarifGORefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class ParamTarifGORefDAO extends RefDAO {

	public static final int ACTION_GET_CODES_TYPE_TARIF = 100;
	public static final int LOAD_FOR_EXPORT = 101;

	private static final String SELECT_PROC_NAME = "refparam_selAllTarifGO";
	private static final String ADMIN_SELECT_PROC_NAME = "refparam_selAdmTarifGO";
	private static final String INSERT_PROC_NAME = "refparam_addTarifGO";
	private static final String UPDATE_PROC_NAME = null;
	private static final String DELETE_PROC_NAME = "refparam_delAdmTarifGO";

	private static final String ACTION_GET_CODES_TYPE_TARIF_PROC_NAME = "refparam_selCodesTypeTarif";

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
		case ACTION_GET_CODES_TYPE_TARIF:
			return ACTION_GET_CODES_TYPE_TARIF_PROC_NAME;
		case LOAD_FOR_EXPORT:
			return SELECT_PROC_NAME;
		default:
			return null;
		}
	}

	
	@SuppressWarnings("unchecked")
	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new ParamTarifGOCacheList((List<ParamTarifGORefBean>) data);
	}

	protected String getCacheClassName() {
		return ParamTarifGOCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		return (CachableInterface) CacheManager.getInstance().getObjectInCache(ParamTarifGOCacheList.class);
	}

	public int updateRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		deleteRef(contexte, bean);
		return insertRef(contexte, bean);
	}

	

	public int insertRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException {
		ParamTarifGOMappedRefBean data = (ParamTarifGOMappedRefBean) bean;
		if(data.getCodesTypeTarif()==null || data.getCodesTypeTarif().isEmpty())
			return 0;
		SQLParamDescriptorSet[] params = createInsertParams(data);
		try {
			SQLCallExecuter.getInstance().executeListeUpdate(INSERT_PROC_NAME, params, getClass().getSimpleName(), "insertRef", contexte.getContexteAppelDAO());
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
		return 0;
	}

	private SQLParamDescriptorSet[] createInsertParams(ParamTarifGOMappedRefBean data) {
		List<String> codesTars = data.getCodesTypeTarif();
		SQLParamDescriptorSet[] params = new SQLParamDescriptorSet[codesTars.size()];

		for (int i = 0; i < codesTars.size(); i++) {
			String codeTars = (String) codesTars.get(i);

			SQLParamDescriptor[] pd = new SQLParamDescriptor[] { new SQLParamDescriptor(data.getCodeAsaCategory(), false, Types.VARCHAR),
					new SQLParamDescriptor(new Integer(data.getIdGroupeOffre()), false, Types.INTEGER), new SQLParamDescriptor(codeTars, false, Types.VARCHAR) };
			params[i] = new SQLParamDescriptorSet(pd);
		}
		return params;
	}

	protected SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue) {

		switch (type) {
		case ACTION_GET_CODES_TYPE_TARIF:
		case SELECT:
		case LOAD_FOR_EXPORT:
			SQLParamDescriptor[] empty = {};
			return new SQLParamDescriptorSet(empty);
		case ADMIN_SELECT:
			ParamTarifGOMappedRefBean mappedTemp = (ParamTarifGOMappedRefBean) bean;
			SQLParamDescriptor[] selectAllParams = { 
					new SQLParamDescriptor(new Integer(mappedTemp.getIdGroupeOffre()), false, Types.INTEGER) };
			return new SQLParamDescriptorSet(selectAllParams);

		case DELETE:
			mappedTemp = (ParamTarifGOMappedRefBean) bean;
			SQLParamDescriptor[] deleteParams = { new SQLParamDescriptor(mappedTemp.getCodeAsaCategory(), false, Types.VARCHAR),
					new SQLParamDescriptor(new Integer(mappedTemp.getIdGroupeOffre()), false, Types.INTEGER) };
			return new SQLParamDescriptorSet(deleteParams);
		default:
			return null;
		}
	}

	protected SQLResultSetReader getProcReader(int type) {
		switch (type) {
		case ADMIN_SELECT:
		case SELECT:
		case LOAD_FOR_EXPORT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					ParamTarifGORefBean bean = new ParamTarifGORefBean();
					bean.setCodeAsaCategory(rs.getString("codeAsaCategory"));
					bean.setIdGroupeOffre(rs.getInt("idGroupeOffre"));
					bean.setCodeTypeTarif(rs.getString("codeTypeTarif"));
					return bean;
				}
			};
		case ACTION_GET_CODES_TYPE_TARIF:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					return rs.getString("codeTypeTarif");
				}
			};
		default:
			return null;
		}
	}



}
