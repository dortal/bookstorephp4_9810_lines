package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.MealPlanRefBean;
import com.accor.asa.commun.reference.metier.MealPlanRefCacheList;
import com.accor.asa.commun.reference.metier.RefBean;

public class MealPlanRefDAO extends RefDAO {

	private static MealPlanRefDAO instance = new MealPlanRefDAO();
	
	
	
	private static final String SELECT_PROC_NAME = "ref_selAllMealPlan";;
	private static final String ADMIN_SELECT_PROC_NAME = "ref_selAdminMealPlan";
	private static final String INSERT_PROC_NAME = "ref_addMealPlan";
	private static final String UPDATE_PROC_NAME = "ref_updMealPlan";
	private static final String DELETE_PROC_NAME = "ref_delMealPlan";

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
		return MealPlanRefCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		return (MealPlanRefCacheList) CacheManager.getInstance().getObjectInCache(MealPlanRefCacheList.class, codeLangue);
	}

	protected SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue) {

		MealPlanRefBean temp = (MealPlanRefBean) bean;
		
		switch (type) {
		case SELECT:
			SQLParamDescriptor[] selectParams = { new SQLParamDescriptor(codeLangue) };
			return new SQLParamDescriptorSet(selectParams);
		case ADMIN_SELECT:
			SQLParamDescriptor[] adminSelectParams = {};
			return new SQLParamDescriptorSet(adminSelectParams);
		case INSERT:
			SQLParamDescriptor[] insertDescriptors = { 
					new SQLParamDescriptor(temp.getId(), false, Types.VARCHAR),
					new SQLParamDescriptor(temp.getName(), false, Types.VARCHAR),
					new SQLParamDescriptor(temp.getLibelleLong(), false, Types.VARCHAR), 
					new SQLParamDescriptor(Boolean.valueOf(temp.isPdjInclu()), false, Types.BIT),
					new SQLParamDescriptor(Boolean.valueOf(temp.isPdjNotInclu()), false, Types.BIT), 
					new SQLParamDescriptor(Boolean.valueOf(temp.isAllInclusive()), false, Types.BIT) };
			return new SQLParamDescriptorSet(insertDescriptors);
		case UPDATE:
			SQLParamDescriptor[] updateDescriptors = {
					new SQLParamDescriptor(temp.getId(), false, Types.VARCHAR), 
					new SQLParamDescriptor(temp.getName(), false, Types.VARCHAR),
					new SQLParamDescriptor(temp.getLibelleLong(), false, Types.VARCHAR), 
					new SQLParamDescriptor(Boolean.valueOf(temp.isPdjInclu()), false, Types.BIT),
					new SQLParamDescriptor(Boolean.valueOf(temp.isPdjNotInclu()), false, Types.BIT), 
					new SQLParamDescriptor(Boolean.valueOf(temp.isAllInclusive()), false, Types.BIT)
			};
			return new SQLParamDescriptorSet(updateDescriptors);
		case DELETE:
			SQLParamDescriptor[] deleteDescriptors = { new SQLParamDescriptor(temp.getCode(), false, Types.VARCHAR) };
			return new SQLParamDescriptorSet(deleteDescriptors);
		default:
			return null;
		}
	}

	protected SQLResultSetReader getProcReader(int type) {
		switch (type) {
		case SELECT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					MealPlanRefBean bean = new MealPlanRefBean();
					bean.setId(rs.getString("code"));
					bean.setName(rs.getString("name"));
					bean.setLibelle(rs.getString("libelle"));
					bean.setLibelleLong(rs.getString("libelleLong"));
					bean.setPdjInclu(rs.getBoolean("isPdjInclu"));
					bean.setPdjNotInclu(rs.getBoolean("isPdjNotInclu"));
					bean.setAllInclusive(rs.getBoolean("isAllInclusive"));
					bean.setActif(!rs.getBoolean("supprime"));
					return bean;
				}
			};
		case ADMIN_SELECT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					MealPlanRefBean bean = buildStandardMealPlanFromRS(rs);
					return bean;
				}
			};

		default:
			return null;
		}
	}

	public static MealPlanRefBean buildStandardMealPlanFromRS(ResultSet rs) throws SQLException {
		
		MealPlanRefBean bean = new MealPlanRefBean();
		bean.setId((rs.getString("codeMealPlan")));
		bean.setName(rs.getString("nomCourt"));
		bean.setLibelle(rs.getString("nomCourt"));
		bean.setLibelleLong(rs.getString("nomLong"));
		bean.setPdjInclu(rs.getBoolean("isPdjInclu"));
		bean.setPdjNotInclu(rs.getBoolean("isPdjNotInclu"));
		bean.setAllInclusive(rs.getBoolean("isAllInclusive"));
		bean.setActif(!rs.getBoolean("mealplan_supprime"));
		return bean;
	}

	@SuppressWarnings("unchecked")
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new MealPlanRefCacheList((List<MealPlanRefBean>) data, contexte);
	}

	@SuppressWarnings("unchecked")
	public List<MealPlanRefBean> getMealplansForKey(String codeAsacategory, int idGroupeTarif, Contexte contexte) throws TechnicalException
	{
		try {
			String procName = "refparam_selMealplansForKey";
			SQLParamDescriptorSet procParams = new SQLParamDescriptorSet(
					new SQLParamDescriptor[] { 
							new SQLParamDescriptor(codeAsacategory, false, Types.VARCHAR),
							new SQLParamDescriptor(new Integer(idGroupeTarif), false, Types.INTEGER), 
								
					});
			
			SQLResultSetReader procReader = getProcReader(ADMIN_SELECT);

			List<MealPlanRefBean> result = (List<MealPlanRefBean>) SQLCallExecuter.getInstance()
				.executeSelectProcSansLimite(
				procName,
				procParams,
				"MealplanRefDAO",
				"getMealplansForKey",
				contexte.getContexteAppelDAO(),
				procReader);
			LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), procName, "SELECT", "");
			LogCommun.debug("MealplanRefDAO", "getMealplansForKey", "Taille resultat = " + result.size());
			return result;
		}
		catch (SQLException e) {
			throw new TechnicalException(e);
		}	
	}

	public static MealPlanRefDAO getInstance() {
		return instance;
	}
	
}
