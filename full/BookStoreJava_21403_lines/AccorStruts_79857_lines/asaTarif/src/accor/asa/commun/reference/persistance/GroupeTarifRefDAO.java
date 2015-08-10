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
import com.accor.asa.commun.reference.metier.DivisionSemaineRefBean;
import com.accor.asa.commun.reference.metier.GroupeTarifCacheList;
import com.accor.asa.commun.reference.metier.GroupeTarifRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class GroupeTarifRefDAO extends RefDAO{
	
	private static GroupeTarifRefDAO instance = new GroupeTarifRefDAO();
	
	
	private static final String SELECT_PROC_NAME        = "ref_selAllGroupeTarif";
	private static final String ADMIN_SELECT_PROC_NAME  = "ref_selAdminGroupeTarif";
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
		DivisionSemaineRefBean temp = (DivisionSemaineRefBean)bean;
		switch (type) {
			case SELECT :
				SQLParamDescriptor [] selectParams = {
					new SQLParamDescriptor(codeLangue, false, Types.CHAR),
				};
				return new SQLParamDescriptorSet(selectParams);
			case ADMIN_SELECT :
				SQLParamDescriptor [] selectAllParams = {
						
					};
					return new SQLParamDescriptorSet(selectAllParams);
			case INSERT :
				SQLParamDescriptor [] insertParams = {
						new SQLParamDescriptor(Integer.valueOf(temp.getCode()), false, Types.INTEGER),
						new SQLParamDescriptor(temp.getName(), false, Types.VARCHAR),
						new SQLParamDescriptor(Boolean.FALSE, false, Types.BIT),
					};
					return new SQLParamDescriptorSet(insertParams);
			case UPDATE :
				SQLParamDescriptor [] updateParams = {
						new SQLParamDescriptor(Integer.valueOf(temp.getCode()), false, Types.INTEGER),
						new SQLParamDescriptor(temp.getName(), false, Types.VARCHAR)
					};
					return new SQLParamDescriptorSet(updateParams);
			case DELETE :
				SQLParamDescriptor [] deleteParams = {
						new SQLParamDescriptor(Integer.valueOf(temp.getCode()), false, Types.INTEGER),
					};
					return new SQLParamDescriptorSet(deleteParams);
			default :
				return null;
		}
	}

	protected SQLResultSetReader getProcReader (int type) {
		switch (type) {
			case SELECT :
				return new SQLResultSetReader() {
					public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
						GroupeTarifRefBean bean = new GroupeTarifRefBean();
						bean.setId(String.valueOf(rs.getInt("idGroupeTarif")));
						bean.setName(rs.getString("name"));
						bean.setLibelle(rs.getString("libelle"));
						bean.setActif(!rs.getBoolean("supprime"));
						return bean;
					}
				};
			case ADMIN_SELECT :
				return new SQLResultSetReader() {
					public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
						GroupeTarifRefBean bean = buildStandardGroupeTarifFromRS(rs);
						return bean;
					}
				};
			default :
				return null;
		}
	}

	protected String getCacheClassName () {
		return GroupeTarifCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache (String codeLangue) {
		 return (CachableInterface) CacheManager.getInstance().getObjectInCache(GroupeTarifCacheList.class, codeLangue);
	}


	public static GroupeTarifRefBean buildStandardGroupeTarifFromRS(ResultSet rs) throws SQLException
	{
		GroupeTarifRefBean bean = new GroupeTarifRefBean();
		bean.setId(String.valueOf(rs.getInt("idGroupeTarif")));
		bean.setName(rs.getString("nomGroupeTarif"));
		bean.setLibelle(bean.getName());
		bean.setActif(!rs.getBoolean("gt_supprime"));
		return bean;
	}

	public static GroupeTarifRefDAO getInstance() {
		return instance;
	}

	

	@SuppressWarnings("unchecked")
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new GroupeTarifCacheList((List<GroupeTarifRefBean>) data, contexte);
	}
	
	

	
}
