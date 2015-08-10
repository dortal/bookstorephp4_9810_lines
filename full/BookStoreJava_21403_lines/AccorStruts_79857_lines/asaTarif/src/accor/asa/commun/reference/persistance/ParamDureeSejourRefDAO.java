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
import com.accor.asa.commun.reference.ParamDureeSejourRefBean;
import com.accor.asa.commun.reference.metier.ParamDureeSejourCacheList;
import com.accor.asa.commun.reference.metier.RefBean;

public class ParamDureeSejourRefDAO extends RefDAO{
	
	private static final String SELECT_PROC_NAME        = "refparam_selAllDureeSejour";
	private static final String ADMIN_SELECT_PROC_NAME  = null;
	private static final String INSERT_PROC_NAME        = null;
	private static final String UPDATE_PROC_NAME        = null;
	private static final String DELETE_PROC_NAME        = null;

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

	

	protected String getCacheClassName() {
		return ParamDureeSejourCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		  return (CachableInterface) CacheManager.getInstance().getObjectInCache(ParamDureeSejourCacheList.class, codeLangue);
	}

	

	
		protected SQLParamDescriptorSet getProcParameters (int type, RefBean bean, String codeLangue) {
			ParamDureeSejourRefBean temp = (ParamDureeSejourRefBean) bean;
			switch (type) {
				case SELECT :
	                SQLParamDescriptor[] selectParams = {
	                    
	                };
	                return new SQLParamDescriptorSet(selectParams);
				case ADMIN_SELECT :
					SQLParamDescriptor [] adminSelectParams = {
	                };
					return new SQLParamDescriptorSet(adminSelectParams);
				case INSERT:
					SQLParamDescriptor[] insertDescriptors = {
							new SQLParamDescriptor(temp.getCodeChaineHotel(), false, Types.VARCHAR),
							new SQLParamDescriptor(temp.getIdDureeSejour(), false, Types.INTEGER),
							new SQLParamDescriptor(temp.isUsedForSalons(),false, Types.BIT)
					};
					return new SQLParamDescriptorSet(insertDescriptors);
				case UPDATE:
				SQLParamDescriptor[] updateDescriptors = {
						
				};
				return new SQLParamDescriptorSet(updateDescriptors);
				case DELETE:
					SQLParamDescriptor[] deleteDescriptors = 
					{
							new SQLParamDescriptor(temp.getCode(), false, Types.INTEGER)
					};
					return new SQLParamDescriptorSet(deleteDescriptors);
				default :
					return null;
			}
	}

	protected SQLResultSetReader getProcReader(int type) {
		switch(type){
		case SELECT:
			return 	new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs)
						throws SQLException {
					ParamDureeSejourRefBean bean=new ParamDureeSejourRefBean();
					bean.setCodeChaineHotel(rs.getString("codeChaine"));
					bean.setIdDureeSejour(rs.getInt("idDureeSejour"));
					bean.setUsedForSalons(rs.getBoolean("isSalon"));
					return bean;
				}
			};
		case ADMIN_SELECT:
		return 	null;
		
			default:
				return null;
		}
	}

	@SuppressWarnings("unchecked")
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new ParamDureeSejourCacheList( (List<ParamDureeSejourRefBean>) data, contexte);
	}
	

}
