package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import com.accor.asa.commun.DuplicateKeyException;
import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.metier.AsaDate;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.ForeignKeyException;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.GroupeTarifRefBean;
import com.accor.asa.commun.reference.metier.ParamPeriodeValiditeCacheList;
import com.accor.asa.commun.reference.metier.ParamPeriodeValiditeMappedRefBean;
import com.accor.asa.commun.reference.metier.ParamPeriodeValiditeRefBean;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class ParamPeriodeValiditeRefDAO extends RefDAO{

		
	public static final int DUPLICATE = 100;
	public static final int LOAD_FOR_EXPORT =101;
	
	
	private static final String SELECT_PROC_NAME = "refparam_selAllPeriodeValidite";
	private static final String SELECT_ADMIN_PROC_NAME = "refparam_selAdmPeriodeValidite";
	private static final String INSERT_PROC_NAME = "refparam_addAdmPeriodeValidite";
	private static final String UPDATE_PROC_NAME = "refparam_updAdmPeriodeValidite";
	private static final String DELETE_PROC_NAME = "refparam_delAdmPeriodeValidite";
	
	private static final String DUPLICATE_PROC_NAME= "refparam_duplPeriodeValidite";
	private static final String EXPORT_PROC_NAME= "refparam_selExpPeriodeValidite";
	
	protected String getProcName(int type) {
		switch (type) {
		case SELECT:
			return SELECT_PROC_NAME;
		case ADMIN_SELECT:
			return SELECT_ADMIN_PROC_NAME;
		case INSERT:
			return INSERT_PROC_NAME;
		case UPDATE:
			return UPDATE_PROC_NAME;
		case DELETE:
			return DELETE_PROC_NAME;
		/*case DUPLICATE:
			return DUPLICATE_PROC_NAME;*/
		case LOAD_FOR_EXPORT:
			return EXPORT_PROC_NAME;
		default:
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new ParamPeriodeValiditeCacheList((List<ParamPeriodeValiditeRefBean>) data);
	}

	protected String getCacheClassName() {
		return ParamPeriodeValiditeCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		return (CachableInterface)CacheManager.getInstance().getObjectInCache(ParamPeriodeValiditeCacheList.class);
	}

	

	protected SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue) {
		ParamPeriodeValiditeRefBean temp = (ParamPeriodeValiditeRefBean)bean;
		switch (type) {
		case SELECT:
		case LOAD_FOR_EXPORT:
			SQLParamDescriptor[] selectParams = {

			};
			return new SQLParamDescriptorSet(selectParams);
		case ADMIN_SELECT:
			SQLParamDescriptor[] adminSelectParams = {
					new SQLParamDescriptor(new Integer(temp.getIdGroupeTarif()), false, Types.INTEGER)
			};
			return new SQLParamDescriptorSet(adminSelectParams);
		case INSERT:
		case UPDATE:
			SQLParamDescriptor[] writeParams = {
					new SQLParamDescriptor(temp.getCodeAsaCategory(),false,Types.VARCHAR),
					new SQLParamDescriptor( new Integer(temp.getIdGroupeTarif()),false, Types.INTEGER),
					new SQLParamDescriptor(new Integer(temp.getIdPeriodeValidite()), false, Types.INTEGER),
					new SQLParamDescriptor(AsaDate.formatDateForSybase(temp.getDateOuverture()), false, Types.VARCHAR),
					new SQLParamDescriptor(AsaDate.formatDateForSybase(temp.getDateFermeture()), false, Types.VARCHAR),
					new SQLParamDescriptor(AsaDate.formatDateForSybase(temp.getDateValidation()), false, Types.VARCHAR),
					new SQLParamDescriptor(AsaDate.formatDateForSybase(temp.getDateValidation2()), false, Types.VARCHAR)
			};
			return new SQLParamDescriptorSet(writeParams);
		case DELETE:
			SQLParamDescriptor[] deleteParams = {
					new SQLParamDescriptor(temp.getCodeAsaCategory(),false,Types.VARCHAR),
					new SQLParamDescriptor( new Integer(temp.getIdGroupeTarif()),false, Types.INTEGER),
                    new SQLParamDescriptor(new Integer(temp.getIdPeriodeValidite()), false, Types.INTEGER)
            };
			return new SQLParamDescriptorSet(deleteParams);
		/*case DUPLICATE:
			SQLParamDescriptor[] duplicateParams = {
					new SQLParamDescriptor(temp.getCodeAsaCategory(),false,Types.VARCHAR),
					new SQLParamDescriptor( new Integer(temp.getIdGroupeTarif()),false, Types.INTEGER),
					new SQLParamDescriptor(temp.getOldId(), false, Types.VARCHAR)
			};
			return new SQLParamDescriptorSet(duplicateParams);*/
		default:
			return null;
		}
	}

	protected SQLResultSetReader getProcReader(int type) {
		
		switch (type) {
		case ADMIN_SELECT:
		case LOAD_FOR_EXPORT:	
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs)
						throws TechnicalException, SQLException {
					ParamPeriodeValiditeRefBean bean = buildStandardParamPeriodeValiditeFromRS(rs);
					GroupeTarifRefBean gt = GroupeTarifRefDAO.buildStandardGroupeTarifFromRS(rs);
					PeriodeValiditeRefBean pv = PeriodeValiditeRefDAO.buildStandardPeriodeValiditeFromRS(rs);
					bean.setGroupeTarif(gt);
					bean.setPeriodeVlaidite(pv);
					return bean;
				}

			};
		
		case SELECT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs)
					throws TechnicalException, SQLException {
					ParamPeriodeValiditeRefBean bean = buildStandardParamPeriodeValiditeFromRS(rs);
					return bean;
				}
			};
			
		
		default:
			return null;
		}
		
	}
	
	public static ParamPeriodeValiditeRefBean buildStandardParamPeriodeValiditeFromRS(ResultSet rs) throws SQLException
	{
		ParamPeriodeValiditeRefBean bean = new ParamPeriodeValiditeRefBean();
		bean.setCodeAsaCategory(rs.getString("codeAsacategory"));
		bean.setIdGroupeTarif(rs.getInt("idGroupeTarif"));
		bean.setIdPeriodeValidite(rs.getInt("idPeriodeValidite"));
		bean.setDateOuverture(rs.getDate("dateOuverture"));
		bean.setDateFermeture(rs.getDate("dateFermeture"));
		bean.setDateValidation(rs.getDate("dateValidation"));
		bean.setDateValidation2(rs.getDate("dateValidation2"));
		return bean;
	}
	
	

	public int doAction(int actionType, Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		if(actionType==DUPLICATE)
			duplicate(contexte, bean);
		return 0;
	}
	
	private int duplicate(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException 
	{
		SQLParamDescriptorSet[] params = createParams((ParamPeriodeValiditeMappedRefBean) bean);
		try {
			SQLCallExecuter.getInstance().executeListeUpdate(DUPLICATE_PROC_NAME, params, getClass().getSimpleName(), "duplicate", contexte.getContexteAppelDAO());
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
		return 0;
	}
	
	
	private SQLParamDescriptorSet[] createParams(ParamPeriodeValiditeMappedRefBean data) {
		List<String> codesAsaCategory = data.getCodesAsaCategory();
		SQLParamDescriptorSet[] params = new SQLParamDescriptorSet[codesAsaCategory.size()];
		int idGroupeTarif = data.getIdGroupeTarif();
        int idPeriodeValidite = data.getIdPeriodeValidite();
        String oldCodeAsa = data.getOldId();
		for (int i = 0; i < codesAsaCategory.size(); i++) {
			String codeAsaCat = codesAsaCategory.get(i);
			SQLParamDescriptor[] pd = new SQLParamDescriptor[] {
					new SQLParamDescriptor(oldCodeAsa, false, Types.VARCHAR),
					new SQLParamDescriptor(idGroupeTarif, false, Types.INTEGER),
                    new SQLParamDescriptor(idPeriodeValidite, false, Types.INTEGER),
                    new SQLParamDescriptor(codeAsaCat, false, Types.VARCHAR), };
			params[i] = new SQLParamDescriptorSet(pd);
		}
		return params;
	}
	
	
}
