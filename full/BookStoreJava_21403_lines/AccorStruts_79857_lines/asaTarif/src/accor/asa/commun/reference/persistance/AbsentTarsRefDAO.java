package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.accor.asa.administration.commun.log.Log;
import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.cache.metier.CacheManager;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.ContexteAppelDAO;
import com.accor.asa.commun.persistance.DAO;
import com.accor.asa.commun.persistance.SQLCallExecuter;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.AbsentTarsCacheList;
import com.accor.asa.commun.reference.metier.AbsentTarsRSRefBean;
import com.accor.asa.commun.reference.metier.AbsentTarsRefBean;

public class AbsentTarsRefDAO extends DAO{
	static private AbsentTarsRefDAO _singleton;
	private static final String PROC_NAME = "admin_get_dif_rate_tars_asa";
	
	public static AbsentTarsRefDAO getInstance () {
		if (_singleton == null) {
			_singleton = new AbsentTarsRefDAO();
		}
		return  _singleton;
	}

	protected String getCacheClassName() {
		return AbsentTarsCacheList.class.getName();
	}

	protected CachableInterface getObjectInCache(String codeLangue) {
		return (CachableInterface)CacheManager.getInstance().getObjectInCache(AbsentTarsCacheList.class, codeLangue);
	}

	@SuppressWarnings("unchecked")
	public AbsentTarsRSRefBean getListAbsentTars(String codeHotel, int idFamily, int idPeriode, ContexteAppelDAO contexte) 
	throws TechnicalException{
		AbsentTarsRSRefBean bean = new AbsentTarsRSRefBean();
		
		SQLParamDescriptor[] paramDescriptors = new SQLParamDescriptor[3];
		paramDescriptors[0] = new SQLParamDescriptor(codeHotel);
		paramDescriptors[1] = new SQLParamDescriptor(idFamily);
		paramDescriptors[2] = new SQLParamDescriptor(idPeriode);
		
		
        SQLResultSetReader[] recups = new SQLResultSetReader[3];
        recups[0] = new SQLResultSetReader() {
            public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
            	int toto = rs.getInt("nbRateAsa");
                return toto;
            }
        };
        recups[1] = new SQLResultSetReader() {
            public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
            	return rs.getInt("nbRateTars");
            }
        };
        recups[2] = new SQLResultSetReader() {
        	public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
        		return new AbsentTarsRefBean(rs.getString( "coderateLevel"));
            }
        };
		
		
		
		try {
			List<?>[] results = SQLCallExecuter.getInstance().executeMultipleSelectProc(PROC_NAME,
                    new SQLParamDescriptorSet(paramDescriptors), "AbsentTarsRefDAO", "getListAbsentTars",
                    contexte, recups, false);
            // Liste contenant un seul élément : nbRateAsa
            List<Integer> nbRateAsa = (List<Integer>) results[0];
            if (nbRateAsa != null && nbRateAsa.size() > 0)
            	bean.setNbRateAsa(nbRateAsa.get(0));
         // Liste contenant un seul élément : nbRateAsa
            List<Integer> nbRateTars = (List<Integer>) results[1];
            if (nbRateTars != null && nbRateTars.size() > 0)
            	bean.setNbRateTars(nbRateTars.get(0));
            // Liste des code rate levels absent de tars
            List<AbsentTarsRefBean> listeAbsent = (List<AbsentTarsRefBean>) results[2];
            if (listeAbsent != null && listeAbsent.size() > 0)
            	bean.setListeAbsence(listeAbsent);
			
		} catch (SQLException e) {
			Log.major( contexte.getLogin(), "AbsentTarsRefDAO", "getListAbsentTars", e.getMessage() );
			throw new TechnicalException(e);
		}
		return bean;
	}

	@SuppressWarnings("unchecked")
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return new AbsentTarsCacheList((List<AbsentTarsRefBean>) data, contexte);
	}
}
