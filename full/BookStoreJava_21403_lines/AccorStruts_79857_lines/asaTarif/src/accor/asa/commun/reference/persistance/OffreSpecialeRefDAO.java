package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.OffreSpecialeCacheList;
import com.accor.asa.commun.reference.metier.OffreSpecialeRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class OffreSpecialeRefDAO extends RefDAO{
	
	private static final String SELECT_PROC_NAME        = "ref_selAllOffreSpeciale";
	private static final String ADMIN_SELECT_PROC_NAME  = "ref_selAdminOffreSpeciale";
	private static final String INSERT_PROC_NAME        = "ref_addOffreSpeciale";
	private static final String UPDATE_PROC_NAME        = "ref_updOffreSpeciale";
	private static final String DELETE_PROC_NAME        = "ref_delOffreSpeciale";

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
		return OffreSpecialeCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		  return (CachableInterface) CacheManager.getInstance().getObjectInCache(OffreSpecialeCacheList.class, codeLangue);
	}

	

	
		protected SQLParamDescriptorSet getProcParameters (int type, RefBean bean, String codeLangue) {
			OffreSpecialeRefBean temp = (OffreSpecialeRefBean) bean;
			switch (type) {
				case SELECT :
	                SQLParamDescriptor[] selectParams = {
	                    new SQLParamDescriptor(codeLangue),
	                };
	                return new SQLParamDescriptorSet(selectParams);
				case ADMIN_SELECT :
					SQLParamDescriptor [] adminSelectParams = {
	                };
					return new SQLParamDescriptorSet(adminSelectParams);
				case INSERT:
					SQLParamDescriptor[] insertDescriptors = {
							new SQLParamDescriptor(Integer.valueOf(temp.getId()), false, Types.INTEGER),
							new SQLParamDescriptor(temp.getName(), false, Types.VARCHAR),
							new SQLParamDescriptor(temp.getDescription())
					};
					return new SQLParamDescriptorSet(insertDescriptors);
				case UPDATE:
				SQLParamDescriptor[] updateDescriptors = {
						new SQLParamDescriptor(temp.getCode(), false, Types.INTEGER),
						new SQLParamDescriptor(temp.getName(), false, Types.VARCHAR),
						new SQLParamDescriptor(temp.getDescription())
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
					OffreSpecialeRefBean offre = new OffreSpecialeRefBean();
					offre.setCode(String.valueOf(rs.getInt("code")));
					offre.setName(StringUtils.trimToEmpty(rs.getString("name")));
					offre.setLibelle(StringUtils.trimToEmpty(rs.getString("libelle")));
					offre.setDescription(StringUtils.trimToEmpty(rs.getString("description")));
					offre.setActif(!rs.getBoolean("supprime"));
					return offre;
				}
			};
		case ADMIN_SELECT:
		return 	new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs)
						throws SQLException {
					OffreSpecialeRefBean offre = new OffreSpecialeRefBean();
					offre.setCode(String.valueOf(rs.getInt("codeOffreSpeciale")));
					offre.setName(StringUtils.trimToEmpty(rs.getString("nomOffreSpeciale")));
					offre.setDescription(StringUtils.trimToEmpty(rs.getString("descOffreSpeciale")));
					offre.setActif(!rs.getBoolean("supprime"));
					return offre;
				}
			};
		
			default:
				return null;
		}
	}

	@SuppressWarnings("unchecked")
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new OffreSpecialeCacheList((List<OffreSpecialeRefBean>) data, contexte);
	}
	

}
