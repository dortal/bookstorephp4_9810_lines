package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.ParamGrilleRefBean;
import com.accor.asa.commun.reference.metier.ParamSupplementCacheList;
import com.accor.asa.commun.reference.metier.ParamSupplementRefBean;
import com.accor.asa.commun.reference.metier.RefBean;
import com.accor.asa.commun.reference.metier.SupplementRefBean;

public class ParamSupplementRefDAO extends RefDAO {

	public static final int GET_FOR_PARAMGRILLE = 100;
	public static final int GET_FOR_PERIODE_VALIDITE = 101;
	
	private static final String SELECT_PROC_NAME = "refparam_selAllSupplement";
	private static final String GET_FOR_PARAMGRILLE_PROC_NAME="refparam_selSupplForParGrille";
	private static final String GET_FOR_PV_PROC_NAME="refparam_selSupplementsForPV";

	protected String getProcName(int type) {
		switch (type) {
		case SELECT:
			return SELECT_PROC_NAME;
		case GET_FOR_PARAMGRILLE:
			return GET_FOR_PARAMGRILLE_PROC_NAME;
		case GET_FOR_PERIODE_VALIDITE:
			return GET_FOR_PV_PROC_NAME;	
		default:
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new ParamSupplementCacheList((List<ParamSupplementRefBean>) data);
	}


	protected String getCacheClassName() {
		return ParamSupplementCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		return (CachableInterface) CacheManager.getInstance().getObjectInCache(ParamSupplementCacheList.class, codeLangue);
	}

	protected SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue) {
		
		switch (type) {
		case SELECT:
			SQLParamDescriptor[] selectParams = {
			};
			return new SQLParamDescriptorSet(selectParams);
		
		case GET_FOR_PARAMGRILLE:
			ParamGrilleRefBean pg = (ParamGrilleRefBean)bean;
			SQLParamDescriptor[] pgParams = {
					new SQLParamDescriptor(pg.getCodeAsaCategory(),false,Types.VARCHAR),
					new SQLParamDescriptor(new Integer(pg.getIdPeriodeValidite()),false,Types.INTEGER),
					new SQLParamDescriptor(pg.getCodeRateLevel(),false,Types.VARCHAR),
				};
			return new SQLParamDescriptorSet(pgParams);
		case GET_FOR_PERIODE_VALIDITE:
			 pg = (ParamGrilleRefBean)bean;
			SQLParamDescriptor[] pvParams = {
					new SQLParamDescriptor(new Integer(pg.getIdPeriodeValidite()),false,Types.INTEGER),
				};
			return new SQLParamDescriptorSet(pvParams);
	
		default:
			return null;
		}
	}

	protected SQLResultSetReader getProcReader(int type) {
		switch (type) {
		case SELECT:
		return new SQLResultSetReader() {
			public Object instanciateFromLine(ResultSet rs)
					throws TechnicalException, SQLException {
				ParamSupplementRefBean bean = buildStandardParamSupplementFromRS(rs);
				return bean;
			}
		};
		case GET_FOR_PARAMGRILLE:	
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					return SupplementRefDAO.buildStandardSupplementFromResumt(rs);
				}
			};
			
		case GET_FOR_PERIODE_VALIDITE:	
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					ParamSupplementRefBean ps= buildStandardParamSupplementFromRS(rs);
					SupplementRefBean supp = SupplementRefDAO.buildStandardSupplementFromResumt(rs);
					ps.setSupplement(supp);
					return ps;
				}
			};
		default:
			return null;
	}
			
	}
	
	public static ParamSupplementRefBean buildStandardParamSupplementFromRS(ResultSet rs) throws SQLException
	{
		ParamSupplementRefBean bean = new ParamSupplementRefBean();
		bean.setCodeAsaCategory(rs.getString("codeAsacategory"));
		bean.setCodeRateLevel(rs.getString("codeRateLevel"));
		bean.setIdPeriodeValidite(rs.getInt("idPeriodeValidite"));
		bean.setCodeSupplement(rs.getString("codeSupplement"));
		return bean;
	}

	
	
	
	

}
