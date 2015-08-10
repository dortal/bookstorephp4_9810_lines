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
import com.accor.asa.commun.reference.metier.RefBean;
import com.accor.asa.commun.reference.metier.SupplementCacheList;
import com.accor.asa.commun.reference.metier.SupplementRefBean;

public class SupplementRefDAO extends RefDAO{
	
	
	
	private static final String SELECT_PROC_NAME = "ref_selAllSupplement";
	private static final String ADMIN_SELECT_PROC_NAME = "ref_selAdminSupplement";
	private static final String INSERT_PROC_NAME = "ref_addSupplement";
	private static final String UPDATE_PROC_NAME = "ref_updateSupplement";
	private static final String DELETE_PROC_NAME = "ref_delSupplement";
	
	

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
		
		default:
			return null;
		}
	}
	
	
	protected String getCacheClassName() {
		return SupplementCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		return (CachableInterface)CacheManager.getInstance().getObjectInCache(SupplementCacheList.class, codeLangue);
	}



	protected SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue) {
		
		switch (type) {
		case SELECT:
			SupplementRefBean temp = (SupplementRefBean)bean;
			SQLParamDescriptor[] selectParams = { new SQLParamDescriptor(codeLangue)};
			return new SQLParamDescriptorSet(selectParams);
		case ADMIN_SELECT:
			temp = (SupplementRefBean)bean;
			SQLParamDescriptor[] adminSelectParams = {};
			return new SQLParamDescriptorSet(adminSelectParams);
		case INSERT:
		case UPDATE:
			temp = (SupplementRefBean)bean;
			SQLParamDescriptor[] writeParams = { 
					new SQLParamDescriptor(temp.getCode(),false,Types.VARCHAR),
					new SQLParamDescriptor(temp.getName(), false, Types.VARCHAR),
			};
			return new SQLParamDescriptorSet(writeParams);
		case DELETE:
			temp = (SupplementRefBean)bean;
			SQLParamDescriptor[] deleteParams = {
							new SQLParamDescriptor(temp.getCode(),false,Types.VARCHAR),
			};
			return new SQLParamDescriptorSet(deleteParams);
		
		default:
			return null;
		}
	}

	protected SQLResultSetReader getProcReader(int type) {
		switch (type) {
		case ADMIN_SELECT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					return buildStandardSupplementFromResumt(rs);
				}

			};
		case SELECT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					SupplementRefBean bean = new SupplementRefBean();
					bean.setCode(rs.getString("codeSupplement"));
					bean.setName(rs.getString("name"));
					bean.setLibelle(rs.getString("libelle"));
					bean.setActif(!rs.getBoolean("supprime"));
					return bean;
				}

			};
		default:
			return null;
		}
	}
	
	public static SupplementRefBean buildStandardSupplementFromResumt(ResultSet rs) throws SQLException
	{
		SupplementRefBean bean = new SupplementRefBean();
		bean.setCode(rs.getString("codeSupplement"));
		bean.setName(rs.getString("nomSupplement"));
		bean.setLibelle(bean.getName());
		bean.setActif(!rs.getBoolean("supplement_supprime"));
		return bean;
	}

	@SuppressWarnings("unchecked")
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new SupplementCacheList((List<SupplementRefBean>) data, contexte);
	}
	
}
