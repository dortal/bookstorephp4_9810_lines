package com.accor.asa.commun.donneesdereference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.accor.asa.commun.donneesdereference.metier.DonneeReference;
import com.accor.asa.commun.donneesdereference.metier.TypeBreakfast;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;

public class TypeBreakfastDAO extends DonneeRefDAOBase {
	private static final String SELECT_PROC_NAME = "vente_sel_petitdej";
	private static final String SELECT_ADMIN_PROC_NAME = "vente_DRef_select_petitdej";
	private static final String INSERT_ADMIN_PROC_NAME = "vente_DRef_insert_petitdej";
	private static final String UPDATE_ADMIN_PROC_NAME = "vente_DRef_update_petitdej";
	private static final String DELETE_ADMIN_PROC_NAME = "vente_DRef_delete_petitdej";

	public String getSelectProcName() {
		return SELECT_PROC_NAME;
	}
	public SQLResultSetReader getSQLResultSetReader() {
		return new SQLResultSetReader() {
			public Object instanciateFromLine(ResultSet rs) throws SQLException {
				TypeBreakfast typeBreakfast = new TypeBreakfast();
				typeBreakfast.setCode(rs.getString("CODE_PETITDEJ"));
				typeBreakfast.setLibelle(rs.getString("LIBELLE_PETITDEJ"));
				typeBreakfast.setCodeLangue(rs.getString("CODE_LANGUE"));
				typeBreakfast.setDescription(rs.getString("DESC_PETITDEJ"));
				typeBreakfast.setRefTars(rs.getString("CODE_TARS"));
				return typeBreakfast;
			}
		};
	}

	public String getInsertProcName() {
		return INSERT_ADMIN_PROC_NAME;
	}

	public SQLParamDescriptorSet getInsertParameters(DonneeReference donnee) {
		TypeBreakfast typeBreakfast = (TypeBreakfast) donnee;
		SQLParamDescriptor[] params = new SQLParamDescriptor[5];
		SQLParamDescriptorSet result = new SQLParamDescriptorSet(params);
		params[0] = new SQLParamDescriptor(typeBreakfast.getCode());
		params[1] = new SQLParamDescriptor(typeBreakfast.getCodeLangue());
		params[2] = new SQLParamDescriptor(typeBreakfast.getLibelle());
		params[3] = new SQLParamDescriptor(typeBreakfast.getDescription());
		params[4] = new SQLParamDescriptor(typeBreakfast.getRefTars());
		return result;
	}

	public String getUpdateProcName() {
		return UPDATE_ADMIN_PROC_NAME;
	}

	public SQLParamDescriptorSet getUpdateParameters(DonneeReference donnee) {
		return getInsertParameters(donnee);
	}

	public String getDeleteProcName() {
		return DELETE_ADMIN_PROC_NAME;
	}
	protected String getSelectAdminProcName() {
		return SELECT_ADMIN_PROC_NAME;
	}
}
