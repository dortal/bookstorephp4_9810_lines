package com.accor.asa.commun.donneesdereference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.donneesdereference.metier.DonneeReference;
import com.accor.asa.commun.donneesdereference.metier.TypeSejour;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;


public class TypeSejourDAO extends DonneeRefDAOBase {

	private static final String SELECT_PROC_NAME = "vente_sel_typeSejour";
	
	protected String getSelectAdminProcName() {
		return null;
	}

	public String getSelectProcName() {
		return SELECT_PROC_NAME;
	}

	public SQLResultSetReader getSQLResultSetReader() {
		SQLResultSetReader result = new SQLResultSetReader() {
			public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
				TypeSejour typeSejour = new TypeSejour();
				typeSejour.setCode(rs.getString("CODE_TYPESEJOUR"));
				typeSejour.setLibelle(rs.getString("LIBELLE_TYPESEJOUR"));
				return typeSejour;
			}
		};
		return result;
	}

	public String getInsertProcName() {
		return null;
	}

	public SQLParamDescriptorSet getInsertParameters(DonneeReference donnee) {
		return null;
	}

	public String getUpdateProcName() {
		return null;
	}

	public SQLParamDescriptorSet getUpdateParameters(DonneeReference donnee) {
		return null;
	}

	public String getDeleteProcName() {
		return null;
	}
}
