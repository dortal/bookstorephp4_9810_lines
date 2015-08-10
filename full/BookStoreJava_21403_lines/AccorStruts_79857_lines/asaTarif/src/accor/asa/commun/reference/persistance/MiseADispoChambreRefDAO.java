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
import com.accor.asa.commun.reference.metier.MiseADispoChambreRefBean;
import com.accor.asa.commun.reference.metier.RefBean;

public class MiseADispoChambreRefDAO extends RefDAO {

	private static final String SELECT_PROC_NAME = "vente_DRef_sel_mode_dispoChamb";
	private static final String ADMIN_SELECT_PROC_NAME = "vente_DRef_sel_mode_dispoChamb";
	private static final String INSERT_PROC_NAME = "vente_DRef_ins_mode_dispoChamb";
	private static final String UPDATE_PROC_NAME = "vente_DRef_upd_mode_dispoChamb";
	private static final String DELETE_PROC_NAME = "vente_DRef_del_mode_dispoChamb";

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
		MiseADispoChambreRefBean temp = (MiseADispoChambreRefBean) bean;
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
					new SQLParamDescriptor(new Boolean(temp.getDefaut()), Types.BOOLEAN),
					new SQLParamDescriptor(new Boolean(temp.getInactif()), Types.BOOLEAN),
					new SQLParamDescriptor(new Boolean(temp.getAllotement()), Types.BOOLEAN),
					new SQLParamDescriptor(new Boolean(temp.getTauxMaterialisation()), Types.BOOLEAN),
					new SQLParamDescriptor(temp.getCodeLangue(), Types.VARCHAR)
				};
				return new SQLParamDescriptorSet(insertParams);
			case UPDATE :
				SQLParamDescriptor [] updateParams = {
					new SQLParamDescriptor(temp.getId(), Types.VARCHAR),
					new SQLParamDescriptor(temp.getLibelle(), Types.VARCHAR),
					new SQLParamDescriptor(new Boolean(temp.getDefaut()), Types.BOOLEAN),
					new SQLParamDescriptor(new Boolean(temp.getInactif()), Types.BOOLEAN),
					new SQLParamDescriptor(new Boolean(temp.getAllotement()), Types.BOOLEAN),
					new SQLParamDescriptor(new Boolean(temp.getTauxMaterialisation()), Types.BOOLEAN)
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
				return null;
			case ADMIN_SELECT :
				return new SQLResultSetReader() {
					public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
						MiseADispoChambreRefBean bean = new MiseADispoChambreRefBean();
						bean.setId(rs.getString("CODE_DISPO"));
						bean.setLibelle(rs.getString("LIBELLE_DISPO"));
						bean.setCodeLangue(rs.getString("CODE_LANGUE"));
						bean.setAllotement(rs.getBoolean("ALLOTEMENT"));
						bean.setTauxMaterialisation(rs.getBoolean("TAUX_MATERIALISATION"));
						bean.setDefaut(rs.getBoolean("DEFAUT"));
						bean.setInactif(rs.getBoolean("INACTIF"));
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