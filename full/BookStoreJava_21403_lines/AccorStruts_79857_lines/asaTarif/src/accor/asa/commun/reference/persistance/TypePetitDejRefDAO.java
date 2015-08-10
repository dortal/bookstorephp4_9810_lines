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
import com.accor.asa.commun.reference.metier.RefBean;
import com.accor.asa.commun.reference.metier.TypePetitDejRefBean;

public class TypePetitDejRefDAO extends RefDAO {

	private static final String SELECT_PROC_NAME = null;
	private static final String ADMIN_SELECT_PROC_NAME = "vente_DRef_select_petitdej";
	private static final String INSERT_PROC_NAME = "vente_DRef_insert_petitdej";
	private static final String UPDATE_PROC_NAME = "vente_DRef_update_petitdej";
	private static final String DELETE_PROC_NAME = "vente_DRef_delete_petitdej";

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
		TypePetitDejRefBean temp = (TypePetitDejRefBean) bean;
		switch (type) {
			case SELECT :
				return null;
			case ADMIN_SELECT :
				SQLParamDescriptor [] adminSelectParams = {
					new SQLParamDescriptor(new Boolean(true))
				};
				return new SQLParamDescriptorSet(adminSelectParams);
			case INSERT :
			case UPDATE :
				SQLParamDescriptor [] insertParams = {
					new SQLParamDescriptor(temp.getId(), Types.VARCHAR),
					new SQLParamDescriptor(ADMIN_LANGUAGE),
					new SQLParamDescriptor(temp.getLibelle(), Types.VARCHAR),
					new SQLParamDescriptor(temp.getDescription(), Types.VARCHAR),
					new SQLParamDescriptor(temp.getCodeTars(), Types.VARCHAR)
				};
				return new SQLParamDescriptorSet(insertParams);
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
				return null;
			case ADMIN_SELECT :
				return new SQLResultSetReader() {
					public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
						TypePetitDejRefBean bean = new TypePetitDejRefBean();
						bean.setId(rs.getString("CODE_PETITDEJ"));
						bean.setLibelle(rs.getString("LIBELLE_PETITDEJ"));
						bean.setCodeLangue(rs.getString("CODE_LANGUE"));
						bean.setDescription(rs.getString("DESC_PETITDEJ") == null ? "" : rs.getString("DESC_PETITDEJ"));
						bean.setCodeTars(rs.getString("CODE_TARS") == null ? "" : rs.getString("CODE_TARS"));
						bean.setActif(rs.getString("SUPPRIMER").equals("0"));
						return bean;
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