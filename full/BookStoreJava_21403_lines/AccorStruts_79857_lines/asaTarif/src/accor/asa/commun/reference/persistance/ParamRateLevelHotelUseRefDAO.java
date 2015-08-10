package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import com.accor.asa.commun.DuplicateKeyException;
import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.hotel.metier.HotelLight;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.ForeignKeyException;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.FamilleTarifRefBean;
import com.accor.asa.commun.reference.metier.ParamRateLevelHotelUseCacheList;
import com.accor.asa.commun.reference.metier.ParamRateLevelHotelUseMappedRefBean;
import com.accor.asa.commun.reference.metier.ParamRateLevelHotelUseRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class ParamRateLevelHotelUseRefDAO extends RefDAO{
	
	public static final int SELECT_RATELEVELS_FOR_HOTEL=101; 
	//public static final int SELECT_HOTEL_BY_CODE=102;
	public static final int SELECT_FOR_EXPORT=103;
	public static final int MULTIPLICATE_FOR_ASA_CATEGORY=104;
	
	
	private static final String SELECT_PROC_NAME = "refparam_selRLHU";
	private static final String ADMIN_SELECT_BY_HOTEL_PROC_NAME = "refparam_selRLHUForHotel";
	private static final String SELECT_EXPORT_PROC_NAME="refparam_selExportRLHU";
	private static final String INSERT_PROC_NAME = "refparam_addRLHU";
	private static final String DELETE_PROC_NAME = "refparam_delRLHU";
	
	private static final String SELECT_RL_BY_HOTEL_PROC_NAME = "refparam_selRLAvailForRLHU";
	//private static final String SELECT_HOTEL_BY_CODE_PROC_NAME="tars_selHotelByCode";
	private static final String MULTIPLICATE_FOR_ASA_CATEGORY_PROC_NAME="refparam_multiplicateRLHU";
	
	@SuppressWarnings("unchecked")
	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new ParamRateLevelHotelUseCacheList((List<ParamRateLevelHotelUseRefBean>) data);
	}


	protected String getCacheClassName() {
		return ParamRateLevelHotelUseCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		return (CachableInterface) CacheManager.getInstance().getObjectInCache(ParamRateLevelHotelUseCacheList.class);
	}

	protected String getProcName(int type) {
		switch (type) {
		case SELECT:
			return SELECT_PROC_NAME;
		
		case ADMIN_SELECT:
			return ADMIN_SELECT_BY_HOTEL_PROC_NAME;
		case SELECT_RATELEVELS_FOR_HOTEL:
			return SELECT_RL_BY_HOTEL_PROC_NAME;
		case SELECT_FOR_EXPORT:
			return SELECT_EXPORT_PROC_NAME;
		case MULTIPLICATE_FOR_ASA_CATEGORY:
			return MULTIPLICATE_FOR_ASA_CATEGORY_PROC_NAME;
		case DELETE:
			return DELETE_PROC_NAME;
		
		default:
			return null;
		}
	}

	protected SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue) {
		switch (type) {
		
		case SELECT:
		case SELECT_FOR_EXPORT:
			SQLParamDescriptor[] selectParams = {  };
			return new SQLParamDescriptorSet(selectParams);
			
		case ADMIN_SELECT:
		case SELECT_RATELEVELS_FOR_HOTEL:
		case MULTIPLICATE_FOR_ASA_CATEGORY:
			ParamRateLevelHotelUseMappedRefBean data = (ParamRateLevelHotelUseMappedRefBean)bean;
			//data = (ParamRateLevelHotelUseMappedRefBean)bean;
			SQLParamDescriptor[] adminSelectParams = {
					new SQLParamDescriptor(data.getCodeHotel(), false, Types.VARCHAR),
					new SQLParamDescriptor(new Integer(data.getIdFamilleTarif()), false, Types.INTEGER)
			};
			return new SQLParamDescriptorSet(adminSelectParams);
		
			
		case DELETE:
			data = (ParamRateLevelHotelUseMappedRefBean)bean;
			SQLParamDescriptor[] deleteDescriptors = {
					new SQLParamDescriptor(data.getCodeHotel(), false, Types.VARCHAR),
					new SQLParamDescriptor(new Integer(data.getIdFamilleTarif()), false, Types.INTEGER),
			};
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
					ParamRateLevelHotelUseRefBean bean = buildParamrateLevelHotelUseByRS(rs);
					return bean;
				}
			};
		
		case ADMIN_SELECT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					ParamRateLevelHotelUseRefBean bean = buildParamrateLevelHotelUseByRS(rs);
					return bean;
				}
			};
		case SELECT_FOR_EXPORT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					ParamRateLevelHotelUseRefBean bean = buildParamrateLevelHotelUseByRS(rs);
					HotelLight hotel = new HotelLight();
					hotel.setCode(bean.getCodeHotel());
					hotel.setName(rs.getString("nomhotel"));
					bean.setHotel(hotel);
					FamilleTarifRefBean ft = new FamilleTarifRefBean();
					ft.setId(String.valueOf(bean.getIdFamilleTarif()));
					ft.setName(rs.getString("nomFamilleTarif"));
					bean.setFamilleTarif(ft);
					return bean;
				}
			};	
		
		case SELECT_RATELEVELS_FOR_HOTEL:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					return rs.getString("codetarif");
				}
			};	
		case MULTIPLICATE_FOR_ASA_CATEGORY:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					String[] txt = new String[2];
					txt[0]= rs.getString("codeHotel");
					txt[1]= rs.getString("codeRateLevel");
					return txt;
				}
			};		
		default:
			return null;
		}
	}

	public static ParamRateLevelHotelUseRefBean buildParamrateLevelHotelUseByRS(ResultSet rs)  throws  SQLException
	{
		ParamRateLevelHotelUseRefBean bean = new ParamRateLevelHotelUseRefBean();
		bean.setCodeHotel(rs.getString("codeHotel"));
		bean.setIdFamilleTarif(rs.getInt("idFamilleTarif"));
		bean.setCodeRateLevel(rs.getString("codeRateLevel"));
		return bean;
	}
	
	public int insertRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException {
		
		SQLParamDescriptorSet[] params = createInsertParams((ParamRateLevelHotelUseMappedRefBean) bean);
		try {
			SQLCallExecuter.getInstance().executeListeUpdate(INSERT_PROC_NAME, params, getClass().getSimpleName(), "insertRef", contexte.getContexteAppelDAO());
		} catch (SQLException e) {
			throw new TechnicalException(e);
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	private SQLParamDescriptorSet[] createInsertParams(ParamRateLevelHotelUseMappedRefBean data)
	{
		String codeHotel = data.getCodeHotel();
		int idFamilleTarif = data.getIdFamilleTarif();
		List<String> codesRL = data.getCodesRateLevels();
		SQLParamDescriptorSet[] params = new SQLParamDescriptorSet[codesRL.size()];
		for (int i = 0; i < codesRL.size(); i++) {
			String codeRateLevel=codesRL.get(i);
			params[i]=
					new SQLParamDescriptorSet(
							new SQLParamDescriptor[]{
							new SQLParamDescriptor(codeHotel, false, Types.VARCHAR),
							new SQLParamDescriptor(new Integer(idFamilleTarif), false, Types.INTEGER),
							new SQLParamDescriptor(codeRateLevel, false, Types.VARCHAR),
							});
		}
		return params;
	}

	@Override
	public int updateRef(Contexte contexte, RefBean bean) throws TechnicalException, IncoherenceException, DuplicateKeyException, ForeignKeyException {
		deleteRef(contexte, bean);
		return insertRef(contexte, bean);
	}
	

}
