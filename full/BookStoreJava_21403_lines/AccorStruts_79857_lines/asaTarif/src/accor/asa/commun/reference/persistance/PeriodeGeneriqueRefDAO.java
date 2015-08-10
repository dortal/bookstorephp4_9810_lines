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
import com.accor.asa.commun.reference.metier.GroupeTarifRefBean;
import com.accor.asa.commun.reference.metier.PeriodeGeneriqueCacheList;
import com.accor.asa.commun.reference.metier.PeriodeGeneriqueRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class PeriodeGeneriqueRefDAO extends RefDAO {

	private static final String SELECT_PROC_NAME = "ref_selAllPeriodeGenerique";
	private static final String ADMIN_SELECT_PROC_NAME = "ref_selAdminPeriodeGenerique";
	private static final String INSERT_PROC_NAME = "ref_addPeriodeGenerique";
	private static final String UPDATE_PROC_NAME = "ref_updPeriodeGenerique";
	private static final String DELETE_PROC_NAME = "ref_delPeriodeGenerique";
	

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
		PeriodeGeneriqueRefBean temp = (PeriodeGeneriqueRefBean)bean;
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
						new SQLParamDescriptor(temp.getCode(), false, Types.VARCHAR),
						new SQLParamDescriptor(temp.getName(), false, Types.VARCHAR),
						new SQLParamDescriptor(temp.getLibelleVente(),false, Types.VARCHAR),
						new SQLParamDescriptor(new Integer(temp.getIdGroupeTarif()), false, Types.INTEGER)
					};
					return new SQLParamDescriptorSet(insertParams);
			case UPDATE :
				SQLParamDescriptor [] updateParams = {
						new SQLParamDescriptor(temp.getCode(), false, Types.VARCHAR),
						new SQLParamDescriptor(temp.getName(), false, Types.VARCHAR),
						new SQLParamDescriptor(temp.getLibelleVente(),false, Types.VARCHAR),
						new SQLParamDescriptor(new Integer(temp.getIdGroupeTarif()), false, Types.INTEGER)
					};
					return new SQLParamDescriptorSet(updateParams);
			case DELETE :
				SQLParamDescriptor [] deleteParams = {
						new SQLParamDescriptor(temp.getCode(), false, Types.VARCHAR),
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
						PeriodeGeneriqueRefBean bean = new PeriodeGeneriqueRefBean();
						bean.setId(rs.getString("code"));
						bean.setName(rs.getString("name"));
						bean.setLibelle(rs.getString("libelle"));
						bean.setLibelleVente(rs.getString("libVente"));
						bean.setIdGroupeTarif(rs.getInt("idGroupeTarif"));
						bean.setActif(!rs.getBoolean("supprime"));
						return bean;
					}
				};
			case ADMIN_SELECT :
				return new SQLResultSetReader() {
					public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
						
						PeriodeGeneriqueRefBean bean = buildStandardPeriodeGeneriqueFromRS(rs);
						GroupeTarifRefBean groupeTarif = GroupeTarifRefDAO.buildStandardGroupeTarifFromRS(rs);
						bean.setGroupeTarif(groupeTarif);
						return bean;
					}
				};
				
			default :
				return null;
		}
	}

	

	protected String getCacheClassName() {
		return PeriodeGeneriqueCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		  return (CachableInterface) CacheManager.getInstance().getObjectInCache(PeriodeGeneriqueCacheList.class, codeLangue);
	}

	@SuppressWarnings("unchecked")
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		
		PeriodeGeneriqueCacheList cache=  new PeriodeGeneriqueCacheList(
				(List<PeriodeGeneriqueRefBean>) data, contexte );
		return cache;
	}

	public static PeriodeGeneriqueRefBean buildStandardPeriodeGeneriqueFromRS(ResultSet rs) throws SQLException
	{
		PeriodeGeneriqueRefBean bean = new PeriodeGeneriqueRefBean();
		bean.setId(rs.getString("codePeriode"));
		bean.setName(rs.getString("nomPeriode"));
		bean.setLibelle(bean.getName());
		bean.setLibelleVente(rs.getString("libVente"));
		bean.setIdGroupeTarif(rs.getInt("idGroupeTarif"));
		bean.setActif(!rs.getBoolean("pg_supprime"));
		return bean;
	}
	
}