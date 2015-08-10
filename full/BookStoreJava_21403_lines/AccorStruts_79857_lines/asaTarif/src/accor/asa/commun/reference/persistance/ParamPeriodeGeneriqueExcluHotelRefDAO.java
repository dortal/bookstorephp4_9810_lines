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
import com.accor.asa.commun.reference.metier.ParamPeriodeGeneriqueExcluHotelCacheList;
import com.accor.asa.commun.reference.metier.ParamPeriodeGeneriqueExcluHotelRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class ParamPeriodeGeneriqueExcluHotelRefDAO extends RefDAO{
	
	public final static int LOAD_FOR_EXPORT=100;
	public final static int DUPLICATE=101;
	
	private static final String SELECT_PROC_NAME = "refparam_selAllPerGenExHot";
	private static final String FILTER_PROC_NAME = "refparam_selAdmPerGenExHot";
	private static final String INSERT_PROC_NAME = "refparam_addAdmPerGenExHot";
	private static final String UPDATE_PROC_NAME = "refparam_updAdmPerGenExHot";
	private static final String DELETE_PROC_NAME = "refparam_delAdmPerGenExHot";

	private static final String EXPORT_PROC_NAME = "refparam_ExpAdmPerGenExHot";
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

	private static ParamPeriodeGeneriqueExcluHotelRefDAO instance = new ParamPeriodeGeneriqueExcluHotelRefDAO();

	public static ParamPeriodeGeneriqueExcluHotelRefDAO getInstance() {
		return instance;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return 	new ParamPeriodeGeneriqueExcluHotelCacheList((List<ParamPeriodeGeneriqueExcluHotelRefBean>) data);
	}


	protected String getCacheClassName() {
		return ParamPeriodeGeneriqueExcluHotelCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		return (CachableInterface) CacheManager.getInstance().getObjectInCache(
				ParamPeriodeGeneriqueExcluHotelCacheList.class);
	}

	protected SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue) {
		ParamPeriodeGeneriqueExcluHotelRefBean filter;
		switch (type) {
            case SELECT:
            case LOAD_FOR_EXPORT:
            	filter = (ParamPeriodeGeneriqueExcluHotelRefBean)bean;
                if(filter == null){
                    filter = new ParamPeriodeGeneriqueExcluHotelRefBean();
                }
                SQLParamDescriptor[] adminExportParams = {
                        new SQLParamDescriptor(filter.getCodePeriode(),false,Types.VARCHAR),
                        new SQLParamDescriptor(filter.getCodeAsaCategory(),false,Types.VARCHAR)
                };
                return new SQLParamDescriptorSet(adminExportParams);
            case ADMIN_SELECT:
                filter = (ParamPeriodeGeneriqueExcluHotelRefBean)bean;
                if(filter == null){
                    filter = new ParamPeriodeGeneriqueExcluHotelRefBean();
                }
                SQLParamDescriptor[] adminSelectParams = {
                        new SQLParamDescriptor(filter.getCodePeriode(),false,Types.VARCHAR),
                        new SQLParamDescriptor(filter.getCodeAsaCategory(),false,Types.VARCHAR)
                };
                return new SQLParamDescriptorSet(adminSelectParams);
            case INSERT:
                ParamPeriodeGeneriqueExcluHotelRefBean temp = (ParamPeriodeGeneriqueExcluHotelRefBean)bean;
                SQLParamDescriptor[] addParams = {
                        new SQLParamDescriptor(temp.getCodePeriode(),false,Types.VARCHAR),
                        new SQLParamDescriptor(temp.getCodeAsaCategory(),false, Types.VARCHAR),
                        new SQLParamDescriptor(temp.getCodeHotel(),false, Types.VARCHAR),
                        new SQLParamDescriptor(temp.getNbMaxPeriodes(), false, Types.INTEGER)
                };
                return new SQLParamDescriptorSet(addParams);
            case UPDATE:
                temp = (ParamPeriodeGeneriqueExcluHotelRefBean)bean;
                SQLParamDescriptor[] writeParams = {
                        new SQLParamDescriptor(temp.getCodePeriode(),false,Types.VARCHAR),
                        new SQLParamDescriptor(temp.getCodeAsaCategory(),false, Types.VARCHAR),
                        new SQLParamDescriptor(temp.getCodeHotel(),false, Types.VARCHAR),
                        new SQLParamDescriptor(temp.getNbMaxPeriodes(), false, Types.INTEGER)
                };
                return new SQLParamDescriptorSet(writeParams);
            case DELETE:
                temp = (ParamPeriodeGeneriqueExcluHotelRefBean)bean;
                SQLParamDescriptor[] deleteParams = {
                        new SQLParamDescriptor(temp.getCodePeriode(),false,Types.VARCHAR),
                        new SQLParamDescriptor(temp.getCodeAsaCategory(),false, Types.VARCHAR),
                        new SQLParamDescriptor(temp.getCodeHotel(),false, Types.VARCHAR)
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
				ParamPeriodeGeneriqueExcluHotelRefBean bean = new ParamPeriodeGeneriqueExcluHotelRefBean();
				bean.setCodePeriode(rs.getString("codePeriode"));
				bean.setCodeAsaCategory(rs.getString("codeAsaCategory"));
				bean.setCodeHotel(rs.getString("codeHotel"));
				bean.setNbMaxPeriodes(rs.getInt("nbMaxPeriodes"));
				bean.setNomHotel(rs.getString("nomHotel"));
				
				return bean;
			}
		};
		case LOAD_FOR_EXPORT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs)
					throws TechnicalException, SQLException {
				ParamPeriodeGeneriqueExcluHotelRefBean bean = new ParamPeriodeGeneriqueExcluHotelRefBean();
				bean.setCodePeriode(rs.getString("codePeriode"));
				bean.setCodeAsaCategory(rs.getString("codeAsaCategory"));
				bean.setCodeHotel(rs.getString("codeHotel"));
				bean.setNbMaxPeriodes(rs.getInt("nbMaxPeriodes"));
				bean.setNomHotel(rs.getString("nomHotel"));
				
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
		SQLParamDescriptorSet[] params = createParams((ParamPeriodeGeneriqueExcluHotelRefBean) bean);
		try {
			SQLCallExecuter.getInstance().executeListeUpdate(DUPLICATE_PROC_NAME, params, "ParamPeriodeGeneriqueRefDAO", "duplicate", contexte.getContexteAppelDAO());
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
		return 0;
	}
	
	
	private SQLParamDescriptorSet[] createParams(ParamPeriodeGeneriqueExcluHotelRefBean data) {
//		String codesAsaCategory = data.getCodeAsaCategory();
		SQLParamDescriptorSet[] params = null;
//		SQLParamDescriptorSet[] params = new SQLParamDescriptorSet[codesAsaCategory.size()];
//		String codePeriode = data.getCodePeriode();
//		String oldCodeAsa = data.getOldId();
//	
//		for (int i = 0; i < codesAsaCategory.size(); i++) {
//			String codeAsaCat = codesAsaCategory.get(i);
//			
//			SQLParamDescriptor[] pd = new SQLParamDescriptor[] { 
//					new SQLParamDescriptor(filter.getCodeHotel(),false,Types.VARCHAR),
//					new SQLParamDescriptor(filter.getCodeAsaCategory(),false,Types.VARCHAR)
//			};
//			params[i] = new SQLParamDescriptorSet(pd);
//		}
		return params;
	}

}
