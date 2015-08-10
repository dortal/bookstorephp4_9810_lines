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
import com.accor.asa.commun.reference.metier.ParamPeriodeGenFamTarifCacheList;
import com.accor.asa.commun.reference.metier.ParamPeriodeGenFamTarifMappedRefBean;
import com.accor.asa.commun.reference.metier.ParamPeriodeGenFamTarifRefBean;
import com.accor.asa.commun.reference.metier.PeriodeGeneriqueRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class ParamPeriodeGenFamTarifRefDAO extends RefDAO {

	private static final String SELECT_PROC_NAME = "refparam_selPGFT";
	private static final String SELECT_ADMIN_PROC_NAME = "refparam_selAdminPGFT";
	private static final String INSERT_PROC_NAME = "refparam_addPGFT";
	private static final String DELETE_PROC_NAME = "refparam_delPGFT";

	protected String getProcName(int type) {
		switch (type) {
		case SELECT:
			return SELECT_PROC_NAME;
		case ADMIN_SELECT:
			return SELECT_ADMIN_PROC_NAME;

		case DELETE:
			return DELETE_PROC_NAME;

		default:
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new ParamPeriodeGenFamTarifCacheList((List<ParamPeriodeGenFamTarifRefBean>) data);
	}

	@Override
	protected String getCacheClassName() {
		return ParamPeriodeGenFamTarifCacheList.class.getName();
	}

	@Override
	protected CachableInterface getObjectInCache(String codeLangue) {
		return (CachableInterface) CacheManager.getInstance().getObjectInCache(ParamPeriodeGenFamTarifCacheList.class);
	}

	@Override
	protected SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue) {

		ParamPeriodeGenFamTarifMappedRefBean temp = (ParamPeriodeGenFamTarifMappedRefBean) bean;
		;
		switch (type) {
		case SELECT:
			SQLParamDescriptor[] selectParams = {

			};
			return new SQLParamDescriptorSet(selectParams);
		case ADMIN_SELECT:
		case DELETE:
			SQLParamDescriptor[] selectAdmParams = { new SQLParamDescriptor(new Integer(temp.getIdFamilleTarif()), false, Types.INTEGER) };
			return new SQLParamDescriptorSet(selectAdmParams);

		default:
			return null;
		}
	}

	@Override
	protected SQLResultSetReader getProcReader(int type) {
		switch (type) {
		case SELECT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					ParamPeriodeGenFamTarifRefBean bean = buildStandardParamPeriodeGenFamTarifRefBeanFromRS(rs);

					return bean;
				}
			};
		case ADMIN_SELECT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					ParamPeriodeGenFamTarifRefBean bean = buildStandardParamPeriodeGenFamTarifRefBeanFromRS(rs);
					PeriodeGeneriqueRefBean pg = PeriodeGeneriqueRefDAO.buildStandardPeriodeGeneriqueFromRS(rs);
					bean.setPeriodeGenerique(pg);
					return bean;
				}
			};
			/*
			 * case LOAD_FOR_EXPORT: return new SQLResultSetReader() { public
			 * Object instanciateFromLine(ResultSet rs) throws
			 * TechnicalException, SQLException { ParamPeriodeGeneriqueRefBean
			 * bean = new ParamPeriodeGeneriqueRefBean();
			 * bean.setCodeAsaCategory(rs.getString("codeAsaCategory"));
			 * bean.setNbMaxPeriodes(rs.getInt("nbMaxPeriodes"));
			 * PeriodeGeneriqueRefBean pg =
			 * PeriodeGeneriqueRefDAO.buildStandardPeriodeGeneriqueFromRS(rs);
			 * bean.setPeriodeGenerique(pg); return bean; } };
			 */
		default:
			return null;
		}
	}

	public static ParamPeriodeGenFamTarifRefBean buildStandardParamPeriodeGenFamTarifRefBeanFromRS(ResultSet rs) throws SQLException {
		ParamPeriodeGenFamTarifRefBean bean = new ParamPeriodeGenFamTarifRefBean();
		bean.setCodePeriode(rs.getString("codePeriode"));
		bean.setIdFamilleTarif(rs.getInt("idFamilleTarif"));
		bean.setDefault(rs.getBoolean("isDefault"));
		return bean;
	}

	@Override
	public int updateRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		deleteRef(contexte, bean);
		SQLParamDescriptorSet[] params = createParams((ParamPeriodeGenFamTarifMappedRefBean) bean);
		try {
			SQLCallExecuter.getInstance().executeListeUpdate(INSERT_PROC_NAME, params, "ParamPeriodeGenFamTarifRefDAO", "updateRef", contexte.getContexteAppelDAO());
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
		return 0;
	}

	private SQLParamDescriptorSet[] createParams(ParamPeriodeGenFamTarifMappedRefBean data) {
		List<PeriodeGeneriqueRefBean> periodes = data.getPeriodes();
		SQLParamDescriptorSet[] params = new SQLParamDescriptorSet[periodes.size()];
		String defCode = data.getCodeDefaultPeriode();
		if (defCode == null)
			defCode = "";
		for (int i = 0; i < periodes.size(); i++) {
			PeriodeGeneriqueRefBean pg = periodes.get(i);
			boolean isDefault = pg.getCode().equals(defCode);
			SQLParamDescriptor[] pd = new SQLParamDescriptor[] { new SQLParamDescriptor(new Integer(data.getIdFamilleTarif()), false, Types.INTEGER),
					new SQLParamDescriptor(pg.getCode(), false, Types.VARCHAR), new SQLParamDescriptor(isDefault, false, Types.BIT), };
			params[i] = new SQLParamDescriptorSet(pd);
		}
		return params;
	}
}
