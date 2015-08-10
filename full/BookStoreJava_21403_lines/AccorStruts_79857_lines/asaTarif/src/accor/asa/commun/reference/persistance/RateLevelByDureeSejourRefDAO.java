package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.RateLevelByDureeSejourCacheList;
import com.accor.asa.commun.reference.metier.RateLevelByDureeSejourRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class RateLevelByDureeSejourRefDAO extends RefDAO{

	
	private static final String SELECT_PROC_NAME = "refparam_selRateLevelsByDS";
	private static final String ADMIN_SELECT_PROC_NAME = "refparam_selRateLevelsByDS";
	private static final String INSERT_PROC_NAME = null;
	private static final String UPDATE_PROC_NAME = null;
	private static final String DELETE_PROC_NAME = null;
	
	@SuppressWarnings("unchecked")
	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new RateLevelByDureeSejourCacheList((List<RateLevelByDureeSejourRefBean>) data);
	}

	@Override
	protected String getCacheClassName() {
		return RateLevelByDureeSejourCacheList.class.getName();
	}

	@Override
	protected CachableInterface getObjectInCache(String codeLangue) {
		return (CachableInterface)CacheManager.getInstance().getObjectInCache(RateLevelByDureeSejourCacheList.class, codeLangue);
	}

	

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

	@Override
	protected SQLParamDescriptorSet getProcParameters(int type, RefBean bean, String codeLangue) {
		//RateLevelByDureeSejourBean temp = (RateLevelByDureeSejourBean)bean;
		switch (type) {
		case SELECT:
			SQLParamDescriptor[] selectParams = {};
			return new SQLParamDescriptorSet(selectParams);
		case ADMIN_SELECT:
			
			SQLParamDescriptor[] adminSelectParams = {};
			return new SQLParamDescriptorSet(adminSelectParams);
		case INSERT:
		case UPDATE:
		case DELETE:
			
		default:
			return null;
		}
	}

	@Override
	protected SQLResultSetReader getProcReader(int type) {
		switch (type) {
		case SELECT:
		case ADMIN_SELECT:
		
			return new SQLResultSetReader() {
				public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
					RateLevelByDureeSejourRefBean bean = new RateLevelByDureeSejourRefBean();
					bean.setIdPeriodeValidite(rs.getInt("idPeriodeValidite"));
					bean.setIdFamilleTarif(rs.getInt("idFamilleTarif"));
					bean.setInitRateLavel(rs.getString("rateLevelInit"));
					bean.setIdDureeSejour(rs.getInt("idDureeSejour"));
					bean.setFinalRateLevel(rs.getString("rateLevelFinal"));
					return bean;
				}
			};
		default:
			return null;
		}
	}

	
}
