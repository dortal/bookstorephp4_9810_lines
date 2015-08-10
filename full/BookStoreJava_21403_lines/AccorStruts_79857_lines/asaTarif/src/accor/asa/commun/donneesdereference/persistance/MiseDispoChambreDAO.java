package com.accor.asa.commun.donneesdereference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.donneesdereference.metier.DonneeReference;
import com.accor.asa.commun.donneesdereference.metier.MiseDispoChambre;
import com.accor.asa.commun.persistance.SQLParamDescriptor;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;

public class MiseDispoChambreDAO extends DonneeRefDAOBase {
	private static final String SELECT_ADMIN_PROC_NAME = "vente_DRef_sel_mode_dispoChamb";
	private static final String SELECT_PROC_NAME = "vente_sel_mode_dispoChamb";
	private static final String INSERT_ADMIN_PROC_NAME = "vente_DRef_ins_mode_dispoChamb";
	private static final String UPDATE_ADMIN_PROC_NAME = "vente_DRef_upd_mode_dispoChamb";
	private static final String DELETE_ADMIN_PROC_NAME = "vente_DRef_del_mode_dispoChamb";

	public String getSelectProcName() {
		return SELECT_PROC_NAME;
	}

	public String getInsertProcName() {
		return INSERT_ADMIN_PROC_NAME;
	}

	public String getUpdateProcName() {
		return UPDATE_ADMIN_PROC_NAME;
	}

	public String getDeleteProcName() {
		return DELETE_ADMIN_PROC_NAME;
	}

	protected String getSelectAdminProcName() {
		return SELECT_ADMIN_PROC_NAME;
	}

	public SQLResultSetReader getSQLResultSetReader() {
		SQLResultSetReader result = new SQLResultSetReader() {
			public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
				MiseDispoChambre mode = new MiseDispoChambre();
				mode.setCode(rs.getString("CODE_DISPO"));
				mode.setCodeLangue(rs.getString("CODE_LANGUE"));
				mode.setAllotement(rs.getBoolean("ALLOTEMENT"));
				mode.setDefaut(rs.getBoolean("DEFAUT"));
				mode.setLibelle(rs.getString("LIBELLE_DISPO"));
				mode.setInactif(rs.getBoolean("INACTIF"));
				mode.setTauxMaterialisation(rs.getBoolean("TAUX_MATERIALISATION"));
				return mode;
			}
		};
		return result;
	}

	public SQLParamDescriptorSet getInsertParameters(DonneeReference donnee) {
		MiseDispoChambre mode = (MiseDispoChambre) donnee;
		SQLParamDescriptor[] params = getCommonParameters(donnee, 7);
		params[6] = new SQLParamDescriptor(mode.getCodeLangue(), Types.VARCHAR);
		return new SQLParamDescriptorSet(params);
	}

	public SQLParamDescriptorSet getUpdateParameters(DonneeReference donnee) {
		return new SQLParamDescriptorSet(getCommonParameters(donnee, 6));
	}

	private SQLParamDescriptor[] getCommonParameters(DonneeReference donnee, int size) {
		MiseDispoChambre mode = (MiseDispoChambre) donnee;
		SQLParamDescriptor[] params = new SQLParamDescriptor[size];
		params[0] = new SQLParamDescriptor(mode.getCode(), Types.VARCHAR);
		params[1] = new SQLParamDescriptor(mode.getLibelle(), Types.VARCHAR);
		params[2] = new SQLParamDescriptor(new Boolean(mode.getDefaut()));
		params[3] = new SQLParamDescriptor(new Boolean(mode.getInactif()));
		params[4] = new SQLParamDescriptor(new Boolean(mode.getAllotement()));
		params[5] = new SQLParamDescriptor(new Boolean(mode.getTauxMaterialisation()));
		return params;
	}
}
