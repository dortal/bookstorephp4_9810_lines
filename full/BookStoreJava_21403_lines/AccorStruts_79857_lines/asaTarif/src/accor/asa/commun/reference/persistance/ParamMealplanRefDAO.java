package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import com.accor.asa.commun.DuplicateKeyException;
import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.log.LogCommun;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.ForeignKeyException;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.FamilleTarifRefBean;
import com.accor.asa.commun.reference.metier.MealPlanRefBean;
import com.accor.asa.commun.reference.metier.ParamMealPlanCacheList;
import com.accor.asa.commun.reference.metier.ParamMealPlanMappedRefBean;
import com.accor.asa.commun.reference.metier.ParamMealPlanRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class ParamMealplanRefDAO extends RefDAO {
	
	public static final int DUPLICATE = 100; 
	public static final int LOAD_FOR_EXPORT = 101; 
	
	private static final String SELECT_PROC_NAME = "refparam_selAllMealplan";
	private static final String ADMIN_SELECT_PROC_NAME = "refparam_selAdmMealplan";
	private static final String INSERT_PROC_NAME = "refparam_addMealplan";
	private static final String UPDATE_PROC_NAME = null;
	private static final String DELETE_PROC_NAME = "refparam_delMealplan";
	private static final String DUPLICATE_PROC_NAME="refparam_duplicateMealplan";
	private static final String EXPORT_PROC_NAME="refparam_selExpMealplan";
	
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
		case DUPLICATE:
			return DUPLICATE_PROC_NAME;
		case LOAD_FOR_EXPORT:
			return EXPORT_PROC_NAME;
		default:
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new ParamMealPlanCacheList((List<ParamMealPlanRefBean>) data);
	}

	protected String getCacheClassName() {
		return ParamMealPlanCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		return (CachableInterface) CacheManager.getInstance().getObjectInCache(ParamMealPlanCacheList.class);
	}

	protected SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue) {

		switch (type) {
		case SELECT:
		case LOAD_FOR_EXPORT:
			SQLParamDescriptor[] selectParams = {

			};
			return new SQLParamDescriptorSet(selectParams);
		case ADMIN_SELECT:
			ParamMealPlanRefBean temp = (ParamMealPlanRefBean) bean;
			SQLParamDescriptor[] adminSelectParams = { 
					new SQLParamDescriptor(
							new Integer(temp.getIdFamilleTarif()), false, Types.INTEGER) };
			return new SQLParamDescriptorSet(adminSelectParams);
		case INSERT:
			temp = (ParamMealPlanRefBean) bean;
			SQLParamDescriptor[] writeParams = { 
					new SQLParamDescriptor(temp.getCodeAsaCategory(), false, Types.VARCHAR),
					new SQLParamDescriptor(new Integer(temp.getIdFamilleTarif()), false, Types.INTEGER), 
					new SQLParamDescriptor(new Integer(temp.getIdGroupeTarif()), false, Types.INTEGER), 
					new SQLParamDescriptor(temp.getCodeMealPlan(), false, Types.VARCHAR),
					new SQLParamDescriptor(Boolean.valueOf(temp.isDefault()), false, Types.BIT) };
			return new SQLParamDescriptorSet(writeParams);
		case DELETE:
			ParamMealPlanMappedRefBean mappingBean = (ParamMealPlanMappedRefBean) bean;
			SQLParamDescriptor[] deleteParams = { 
					new SQLParamDescriptor(mappingBean.getCodeAsaCategory(), false, Types.VARCHAR),
					new SQLParamDescriptor(new Integer(mappingBean.getIdFamilleTarif()), false, Types.INTEGER) };
			return new SQLParamDescriptorSet(deleteParams);
		
		default:
			return null;
		}
	}

	
	public int insertRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException {
		SQLParamDescriptorSet[] params = createInsertParams((ParamMealPlanMappedRefBean) bean);
		try {
			SQLCallExecuter.getInstance().executeListeUpdate(INSERT_PROC_NAME, params, getClass().getSimpleName(), "insertRef", contexte.getContexteAppelDAO());
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
		return 0;
	}

	private SQLParamDescriptorSet[] createInsertParams(ParamMealPlanMappedRefBean data) {
		List<MealPlanRefBean> mealplans = data.getMealPlans();
		SQLParamDescriptorSet[] params = new SQLParamDescriptorSet[mealplans.size()];
		for(int i = 0;i<mealplans.size();i++)
		{
			MealPlanRefBean mp = mealplans.get(i);
			boolean isDefault=mp.getCode().equals(data.getDefalutMealplanCode());
			params[i]=
			new SQLParamDescriptorSet(
					new SQLParamDescriptor[] { 
							new SQLParamDescriptor(data.getCodeAsaCategory(), false, Types.VARCHAR),
							new SQLParamDescriptor(new Integer(data.getIdFamilleTarif()), false, Types.INTEGER),
							new SQLParamDescriptor(new Integer(data.getFamilleTarif().getIdGroupeTarif()), false, Types.INTEGER), 
							new SQLParamDescriptor(mp.getCode(), false, Types.VARCHAR),
							new SQLParamDescriptor(Boolean.valueOf(isDefault), false, Types.BIT) 		
					});
			
		}
		return params;
	}


	public int updateRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		deleteRef(contexte, bean);
		return insertRef(contexte, bean);
		
	}

	protected SQLResultSetReader getProcReader(int type) {
		switch (type) {
		case ADMIN_SELECT:

			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					ParamMealPlanRefBean bean = buildStandardParamMealplanFromRS(rs);
					MealPlanRefBean mealPlan = MealPlanRefDAO.buildStandardMealPlanFromRS(rs);
					bean.setMealPlan(mealPlan);
					return bean;
				}

			};
		case LOAD_FOR_EXPORT:

			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					ParamMealPlanRefBean bean = buildStandardParamMealplanFromRS(rs);
					MealPlanRefBean mealPlan = MealPlanRefDAO.buildStandardMealPlanFromRS(rs);
					bean.setMealPlan(mealPlan);
					FamilleTarifRefBean familleTarif = FamilleTarifRefDAO.buildStandardFamilleTarifRefDAOFromRS(rs);
					bean.setFamilleTarif(familleTarif);
					return bean;
				}

			};
		case SELECT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					ParamMealPlanRefBean bean = new ParamMealPlanRefBean();
					bean.setCodeAsaCategory(rs.getString("codeAsaCategory"));
					bean.setIdFamilleTarif(rs.getInt("idFamilleTarif"));
					bean.setIdGroupeTarif(rs.getInt("idGroupeTarif"));
					bean.setCodeMealPlan(rs.getString("codeMealPlan"));
					bean.setDefault(rs.getBoolean("isDefault"));
					return bean;
				}

			};
			
		default:
			return null;
		}
	}
	

	
	
	public int duplicateRef( Contexte contexte, ParamMealPlanMappedRefBean bean ) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		try {
			int res = -1;
			String procName = getProcName(DUPLICATE);
			SQLParamDescriptorSet procParams = getProcParameters(DUPLICATE, bean, ADMIN_LANGUAGE);

			res = SQLCallExecuter.getInstance().executeUpdate(
				procName,
				procParams,
				"ParamMealplanRefDAO",
				"duplicateRef",
				contexte.getContexteAppelDAO());
			LogCommun.traceFonctionnelle(contexte.getCodeUtilisateur(), procName, "UPDATE", bean.toString());
			LogCommun.debug("ParamMealplanRefDAO", "duplicateRef", "Code retour = " + res);
			return res;
		}
		catch (SQLException e) {
			if (e.getErrorCode() == SYBASE_PRIMARY_KEY_ERROR_CODE) {
				throw new DuplicateKeyException(e);
			}
			throw new TechnicalException(e);
		}
	}
	
	

	
	
	public static ParamMealPlanRefBean buildStandardParamMealplanFromRS(ResultSet rs) throws SQLException
	{
		ParamMealPlanRefBean bean = new ParamMealPlanRefBean();
		bean.setCodeAsaCategory(rs.getString("codeAsaCategory"));
		bean.setIdGroupeTarif(rs.getInt("idGroupeTarif"));
		bean.setIdFamilleTarif(rs.getInt("idFamilleTarif"));
		bean.setCodeMealPlan(rs.getString("codeMealPlan"));
		bean.setDefault(rs.getBoolean("isDefault"));
		return bean;
	}

	@Override
	public int doListAction(int actionType, Contexte contexte, List<RefBean> beans) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		switch (actionType) {
		case DUPLICATE:
			return duplicateParamMealplan(actionType, contexte, beans);

		default:
			break;
		}
		return super.doListAction(actionType, contexte, beans);
	}
	
	private int duplicateParamMealplan(int actionType, Contexte contexte, List<RefBean> beans) throws TechnicalException, IncoherenceException, DuplicateKeyException
	{
		if(beans.size()<2)
			return 0;
		SQLParamDescriptorSet[] params = createDuplicateParams( beans);
		try {
			SQLCallExecuter.getInstance().executeListeUpdate(DUPLICATE_PROC_NAME, params, getClass().getSimpleName(), "duplicateParamMealplan", contexte.getContexteAppelDAO());
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
		return 0;
	}
	
	private SQLParamDescriptorSet[] createDuplicateParams(List<RefBean> beans) {
		ParamMealPlanMappedRefBean source = (ParamMealPlanMappedRefBean)beans.get(0);
		String sourceCodeAsaCategory=source.getCodeAsaCategory();
		int idFamilleTarif = source.getIdFamilleTarif();
		
		SQLParamDescriptorSet[] params = new SQLParamDescriptorSet[beans.size()-1];
		for(int i = 1;i<beans.size();i++)
		{
			ParamMealPlanMappedRefBean target = (ParamMealPlanMappedRefBean)beans.get(i);
			params[i-1]=
			new SQLParamDescriptorSet(
					new SQLParamDescriptor[] { 
							new SQLParamDescriptor(sourceCodeAsaCategory, false, Types.VARCHAR),
							new SQLParamDescriptor(new Integer(idFamilleTarif), false, Types.INTEGER),
							new SQLParamDescriptor(target.getCodeAsaCategory(), false, Types.VARCHAR) 
					});
		}
		return params;
	}

}
