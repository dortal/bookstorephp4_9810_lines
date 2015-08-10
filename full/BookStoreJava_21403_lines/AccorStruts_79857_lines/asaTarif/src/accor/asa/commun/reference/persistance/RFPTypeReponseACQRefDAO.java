package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.RFPTypeReponseACQRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class RFPTypeReponseACQRefDAO extends RefDAO {

	private static final String SELECT_PROC_NAME = null;
	private static final String ADMIN_SELECT_PROC_NAME = "admin_rfp_sel_type_reponse";
	private static final String INSERT_PROC_NAME = null;
	private static final String UPDATE_PROC_NAME = null;
	private static final String DELETE_PROC_NAME = null;

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
		switch (type) {
			case SELECT :
				return null;
			case ADMIN_SELECT :
				return new SQLParamDescriptorSet();
			case INSERT :
				// Pas utilisé
				return null;
			case UPDATE :
				// Pas utilisé
				return null;
			case DELETE :
				// Pas utilisé
				return null;
			default :
				return null;
		}
	}

	protected SQLResultSetReader getProcReader (int type) {
		switch (type) {
			case SELECT :
				return null;
			case ADMIN_SELECT :
				return new SQLResultSetReader() {
					public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
						RFPTypeReponseACQRefBean rfpTypeReponseAcq = new RFPTypeReponseACQRefBean();
						rfpTypeReponseAcq.setId(rs.getString("CODE_TYPE_REPONSE"));
						rfpTypeReponseAcq.setLibelle(rs.getString("LIBELLE"));
						return rfpTypeReponseAcq;
					}
				};
			default :
				return null;
		}
	}

	protected String getCacheClassName () {
		return null;
	}

	protected CachableInterface getObjectInCache (String codeLangue) {
		return null;
	}


	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return null;
	}

	
}