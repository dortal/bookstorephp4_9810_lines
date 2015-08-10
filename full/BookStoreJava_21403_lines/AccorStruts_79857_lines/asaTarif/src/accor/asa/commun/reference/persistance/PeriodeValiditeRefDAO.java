package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.metier.AsaDate;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.GroupeTarifRefBean;
import com.accor.asa.commun.reference.metier.PeriodeValiditeCacheList;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class PeriodeValiditeRefDAO extends RefDAO {

	private static final String SELECT_PROC_NAME = "ref_selAllPeriodeValidite";
	private static final String ADMIN_SELECT_PROC_NAME = "ref_selAdminPeriodeValidite";
	private static final String INSERT_PROC_NAME = "ref_addPeriodeValidite";
	private static final String UPDATE_PROC_NAME = "ref_updPeriodeValidite";
	private static final String DELETE_PROC_NAME = "ref_delPeriodeValidite";

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
		return PeriodeValiditeCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		return (CachableInterface) CacheManager.getInstance().getObjectInCache(PeriodeValiditeCacheList.class, codeLangue);
	}

	protected SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue) {
		PeriodeValiditeRefBean temp = (PeriodeValiditeRefBean) bean;
		switch (type) {
		case SELECT:
			SQLParamDescriptor[] selectParams = { new SQLParamDescriptor(codeLangue)};
			return new SQLParamDescriptorSet(selectParams);
		case ADMIN_SELECT:
			SQLParamDescriptor[] selectAllParams = {};
			return new SQLParamDescriptorSet(selectAllParams);
		case INSERT:
			SQLParamDescriptor[] insertParams = { new SQLParamDescriptor(Integer.valueOf(temp.getCode()), false, Types.INTEGER),
					new SQLParamDescriptor(AsaDate.formatDateForSybase(temp.getDateDebut()), false, Types.VARCHAR),
					new SQLParamDescriptor(AsaDate.formatDateForSybase(temp.getDateFin()), false, Types.VARCHAR), new SQLParamDescriptor(new Integer(temp.getIdGroupeTarif()), false, Types.INTEGER) };
			return new SQLParamDescriptorSet(insertParams);
		case UPDATE:
			SQLParamDescriptor[] updateParams = { new SQLParamDescriptor(Integer.valueOf(temp.getCode()), false, Types.INTEGER),
					new SQLParamDescriptor(AsaDate.formatDateForSybase(temp.getDateDebut()), false, Types.VARCHAR),
					new SQLParamDescriptor(AsaDate.formatDateForSybase(temp.getDateFin()), false, Types.VARCHAR) };
			return new SQLParamDescriptorSet(updateParams);
		case DELETE:
			SQLParamDescriptor[] deleteParams = { new SQLParamDescriptor(Integer.valueOf(temp.getCode()), false, Types.INTEGER) };
			return new SQLParamDescriptorSet(deleteParams);

		default:
			return null;
		}
	}

	protected SQLResultSetReader getProcReader(int type) {
		switch (type) {
		case ADMIN_SELECT:
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws SQLException {
					PeriodeValiditeRefBean pv = buildStandardPeriodeValiditeFromRS(rs);
					GroupeTarifRefBean gt = GroupeTarifRefDAO.buildStandardGroupeTarifFromRS(rs);
					pv.setGroupeTarif(gt);
					return pv;
				}
			};
		case SELECT:	
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws SQLException {
					PeriodeValiditeRefBean pv = buildStandardPeriodeValiditeFromRS(rs);
					return pv;
				}
			};
		default:
			return null;
		}
	}

	public static PeriodeValiditeRefBean buildStandardPeriodeValiditeFromRS(ResultSet rs) throws SQLException {
		PeriodeValiditeRefBean pv = new PeriodeValiditeRefBean();
		pv.setCode(String.valueOf(rs.getInt("idPeriodeValidite")));
		pv.setDateDebut(rs.getDate("dateDebut"));
		pv.setDateFin(rs.getDate("dateFin"));
		pv.setActif(!rs.getBoolean("pv_supprime"));
		pv.setIdGroupeTarif(rs.getInt("idGroupeTarif"));
		return pv;
	}

	@SuppressWarnings("unchecked")
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		PeriodeValiditeCacheList cache = new PeriodeValiditeCacheList(
				(List<PeriodeValiditeRefBean>) data, contexte);
		return cache;
	}

}
