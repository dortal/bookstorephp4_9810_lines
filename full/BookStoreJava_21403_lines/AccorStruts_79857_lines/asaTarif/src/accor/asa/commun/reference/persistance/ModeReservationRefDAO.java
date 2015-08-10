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
import com.accor.asa.commun.reference.metier.ModeReservationCachList;
import com.accor.asa.commun.reference.metier.ModeReservationRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class ModeReservationRefDAO extends RefDAO {

    private static final String SELECT_PROC_NAME        = "ref_selReservationMode";
	private static final String ADMIN_SELECT_PROC_NAME  = "ref_selReservationMode";
	private static final String INSERT_PROC_NAME        = "ref_addReservationMode";
	private static final String UPDATE_PROC_NAME        = "ref_updReservationMode";
	private static final String DELETE_PROC_NAME        = "ref_delReservationMode";

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
		ModeReservationRefBean temp = (ModeReservationRefBean) bean;
		switch (type) {
			case SELECT :
                SQLParamDescriptor[] selectParams = {
                    new SQLParamDescriptor(Boolean.FALSE),
                    new SQLParamDescriptor(codeLangue)
                };
                return new SQLParamDescriptorSet(selectParams);
			case ADMIN_SELECT :
				SQLParamDescriptor [] adminSelectParams = {
					new SQLParamDescriptor(Boolean.TRUE),
                    new SQLParamDescriptor(codeLangue)
				};
				return new SQLParamDescriptorSet(adminSelectParams);
			case INSERT :
				SQLParamDescriptor [] insertParams = {
					new SQLParamDescriptor(temp.getId(), Types.VARCHAR),
					new SQLParamDescriptor(temp.getLibelle(), Types.VARCHAR),
					new SQLParamDescriptor(Boolean.valueOf(temp.getTars())),
					new SQLParamDescriptor(Boolean.valueOf(temp.getGds()))
				};
				return new SQLParamDescriptorSet(insertParams);
			case UPDATE :
				SQLParamDescriptor [] updateParams = {
					new SQLParamDescriptor(temp.getId(), Types.VARCHAR),
					new SQLParamDescriptor(temp.getLibelle(), Types.VARCHAR),
					new SQLParamDescriptor(Boolean.valueOf(temp.getTars())),
					new SQLParamDescriptor(Boolean.valueOf(temp.getGds())),
				};
				return new SQLParamDescriptorSet(updateParams);
			case DELETE :
				SQLParamDescriptor [] deleteParams = {
					new SQLParamDescriptor(temp.getId(), Types.VARCHAR)
				};
				return new SQLParamDescriptorSet(deleteParams);
			default :
				return null;
		}
	}

	protected SQLResultSetReader getProcReader (int type) {
		switch (type) {
			case SELECT :
			case ADMIN_SELECT :
				return new SQLResultSetReader() {
					public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
						ModeReservationRefBean bean = new ModeReservationRefBean();
						bean.setId(rs.getString("code"));
						bean.setLibelle(rs.getString("libelle"));
						bean.setTars(rs.getBoolean("TARS"));
						bean.setGds(rs.getBoolean("GDS"));
						bean.setActif(rs.getString("supprime").equals("0"));
						return bean;
					}
				};
			default :
				return null;
		}
	}

	protected String getCacheClassName () {
		return ModeReservationCachList.class.getName();
	}

	protected CachableInterface getObjectInCache (String codeLangue) {
		return (CachableInterface) CacheManager.getInstance().getObjectInCache(ModeReservationCachList.class, codeLangue);
	}


	@SuppressWarnings("unchecked")
	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new ModeReservationCachList((List<ModeReservationRefBean>) data, contexte.getCodeLangue());
	}

	
}