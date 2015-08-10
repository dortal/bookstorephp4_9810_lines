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
import com.accor.asa.commun.reference.metier.DureeSejourCacheList;
import com.accor.asa.commun.reference.metier.DureeSejourRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class DureeSejourRefDAO extends RefDAO{
	
	private static final String SELECT_PROC_NAME        = "ref_selAllDureeSejour";
	private static final String ADMIN_SELECT_PROC_NAME  = "ref_selAdminDureeSejour";
	private static final String INSERT_PROC_NAME        = "ref_addDureeSejour";
	private static final String UPDATE_PROC_NAME        = "ref_updateDureeSejour";
	private static final String DELETE_PROC_NAME        = "ref_delDureeSejour";

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
		return DureeSejourCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		  return (CachableInterface) CacheManager.getInstance().getObjectInCache(DureeSejourCacheList.class, codeLangue);
	}

	

	
		protected SQLParamDescriptorSet getProcParameters (int type, RefBean bean, String codeLangue) {
			DureeSejourRefBean temp = (DureeSejourRefBean) bean;
			switch (type) {
				case SELECT :
	                SQLParamDescriptor[] selectParams = {
	                    new SQLParamDescriptor(codeLangue)
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
							new SQLParamDescriptor(temp.getMinSejour(), false,
									Types.INTEGER),
						   new SQLParamDescriptor(temp.getMaxSejour(), false,
											Types.INTEGER),
							new SQLParamDescriptor(new Integer(0), false,
									Types.BIT)
					};
					return new SQLParamDescriptorSet(insertDescriptors);
				case UPDATE:
				SQLParamDescriptor[] updateDescriptors = {
						new SQLParamDescriptor(temp.getCode(), false, Types.INTEGER),
						new SQLParamDescriptor(temp.getName(), false, Types.VARCHAR),
						new SQLParamDescriptor(temp.getMinSejour(), false,
								Types.INTEGER),
					   new SQLParamDescriptor(temp.getMaxSejour(), false,
										Types.INTEGER)
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
					DureeSejourRefBean ds = new DureeSejourRefBean();
					ds.setCode(String.valueOf(rs.getInt("IdDureeSejour")));
					ds.setName(StringUtils.trimToEmpty(rs.getString("name")));
					ds.setLibelle(StringUtils.trimToEmpty(rs.getString("libelle")));
					ds.setMinSejour((Integer)rs.getObject("minSejour"));
					ds.setMaxSejour((Integer)rs.getObject("maxSejour"));
					ds.setActif(!rs.getBoolean("supprime"));
					return ds;
				}
			};
			
		case ADMIN_SELECT:
		return 	new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs)
						throws SQLException {
					DureeSejourRefBean ds=buildStandardDureeSejourfromRS(rs);
					return ds;
				}
			};
		
			default:
				return null;
		}
	}
	
	public static DureeSejourRefBean buildStandardDureeSejourfromRS(ResultSet rs) throws SQLException
	{
		
		DureeSejourRefBean ds = new DureeSejourRefBean();
		ds.setCode(String.valueOf(rs.getInt("idDureeSejour")));
		ds.setName(StringUtils.trimToEmpty(rs.getString("nomDureeSejour")));
		ds.setLibelle(ds.getName());
		ds.setMinSejour((Integer)rs.getObject("minSejour"));
		ds.setMaxSejour((Integer)rs.getObject("maxSejour"));
		ds.setActif(!rs.getBoolean("dureesejour_supprime"));
		return ds;
	}

	@SuppressWarnings("unchecked")
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new DureeSejourCacheList((List<DureeSejourRefBean>) data, contexte);
	}

	
}
