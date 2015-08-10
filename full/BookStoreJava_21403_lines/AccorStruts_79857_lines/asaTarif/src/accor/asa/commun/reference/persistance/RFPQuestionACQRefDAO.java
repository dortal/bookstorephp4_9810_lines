package com.accor.asa.commun.reference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.cache.metier.CachableInterface;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.reference.metier.RFPQuestionACQRefBean;
import com.accor.asa.commun.reference.metier.RefBean;
import com.accor.asa.vente.rfp.metier.dref.ListACQQuestions;

public class RFPQuestionACQRefDAO extends RefDAO {

	private static final String SELECT_PROC_NAME = null;
	private static final String ADMIN_SELECT_PROC_NAME = "admin_rfp_sel_question";
	private static final String INSERT_PROC_NAME = "admin_rfp_ins_question";
	private static final String UPDATE_PROC_NAME = "admin_rfp_upd_question";
	private static final String DELETE_PROC_NAME = "admin_rfp_del_question";

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
		RFPQuestionACQRefBean temp = (RFPQuestionACQRefBean) bean;
		switch (type) {
			case SELECT :
				return null;
			case ADMIN_SELECT :
				SQLParamDescriptor [] adminSelectParams = {
					new SQLParamDescriptor(new Boolean(true))
				};
				return new SQLParamDescriptorSet(adminSelectParams);
			case INSERT :
				SQLParamDescriptor [] insertParams = {
					new SQLParamDescriptor(temp.getId(), Types.VARCHAR),
					new SQLParamDescriptor(temp.getLibelle(), Types.VARCHAR),
					new SQLParamDescriptor(temp.getLibelleLong(), Types.VARCHAR),
					new SQLParamDescriptor(temp.getIdTypeQuestion(), Types.SMALLINT),
					new SQLParamDescriptor(temp.getIdTypeReponse(), Types.SMALLINT),
					new SQLParamDescriptor(new Boolean(temp.getQuestionParDefaut()), Types.BOOLEAN),
					new SQLParamDescriptor(temp.getReponseParDefaut(), Types.VARCHAR)
				};
				return new SQLParamDescriptorSet(insertParams);
			case UPDATE :
				SQLParamDescriptor [] updateParams = {
					new SQLParamDescriptor(temp.getId(), Types.VARCHAR),
					new SQLParamDescriptor(temp.getLibelle(), Types.VARCHAR),
					new SQLParamDescriptor(temp.getLibelleLong(), Types.VARCHAR),
					new SQLParamDescriptor(temp.getIdTypeQuestion(), Types.SMALLINT),
					new SQLParamDescriptor(temp.getIdTypeReponse(), Types.SMALLINT),
					new SQLParamDescriptor(new Boolean(temp.getQuestionParDefaut()), Types.BOOLEAN),
					new SQLParamDescriptor(temp.getReponseParDefaut(), Types.VARCHAR)
				};
				return new SQLParamDescriptorSet(updateParams);
			case DELETE :
				SQLParamDescriptor [] deleteParams = {
					new SQLParamDescriptor(temp.getId())
				};
				return new SQLParamDescriptorSet(deleteParams);
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
						RFPQuestionACQRefBean rfpQuestionAcq = new RFPQuestionACQRefBean();
						rfpQuestionAcq.setId(rs.getString("CODE_QUESTION_ACQ"));
						rfpQuestionAcq.setLibelle(rs.getString("LIBELLE"));
						rfpQuestionAcq.setLibelleLong(rs.getString("LIBELLE_LONG"));
						rfpQuestionAcq.setIdTypeQuestion(rs.getString("CODE_TYPE_QUESTION"));
						rfpQuestionAcq.setIdTypeReponse(rs.getString("CODE_TYPE_REPONSE"));
						rfpQuestionAcq.setQuestionParDefaut(rs.getBoolean("QUESTION_PAR_DEFAUT"));
						rfpQuestionAcq.setReponseParDefaut(rs.getString("REPONSE_PAR_DEFAUT"));
						rfpQuestionAcq.setActif(!rs.getBoolean("SUPPRIMER"));
						return rfpQuestionAcq;
					}
				};
			default :
				return null;
		}
	}

	protected String getCacheClassName () {
		return ListACQQuestions.class.getName();
	}

	protected CachableInterface getObjectInCache (String codeLangue) {
		return null;
	}


	@Override
	protected CachableInterface createCacheObject(List<?> data, Contexte contexte) throws TechnicalException, IncoherenceException {
		return null;
	}

	
}