package com.accor.asa.commun.donneesdereference.persistance;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.donneesdereference.metier.DonneeReference;
import com.accor.asa.commun.donneesdereference.metier.MealPlan;
import com.accor.asa.commun.persistance.SQLParamDescriptorSet;
import com.accor.asa.commun.persistance.SQLResultSetReader;

public class MealPlanDAO extends DonneeRefDAOBase {

	protected String getSelectAdminProcName() {
		throw new RuntimeException("Il n'existe pas de procédure SELECT pour administrer les Meal Plans.");
	}

	public String getSelectProcName() {
		return "vente_sel_meal_plan";
	}

	public SQLResultSetReader getSQLResultSetReader() {
		SQLResultSetReader result = new SQLResultSetReader() {
			public Object instanciateFromLine(ResultSet rs) throws TechnicalException, SQLException {
				MealPlan meal = new MealPlan();
				meal.setCode(rs.getString("CODEMEALPLAN"));
				meal.setLibelle(rs.getString("NOMMEALPLAN"));
				meal.setCodeLangue(rs.getString("CODELANGUE"));
				return meal;
			}
		};
		return result;
	}

	public String getInsertProcName() {
		throw new RuntimeException("Il n'existe pas de procédure INSERT pour la données de ref. Meal Plans.");
	}

	public SQLParamDescriptorSet getInsertParameters(DonneeReference donnee) {
		throw new RuntimeException("Il n'existe pas de procédure INSERT pour la données de ref. Meal Plans.");
	}

	public String getUpdateProcName() {
		throw new RuntimeException("Il n'existe pas de procédure UPDATE pour la données de ref. Meal Plans.");
	}

	public SQLParamDescriptorSet getUpdateParameters(DonneeReference donnee) {
		throw new RuntimeException("Il n'existe pas de procédure UPDATE pour la données de ref. Meal Plans.");
	}

	public String getDeleteProcName() {
		throw new RuntimeException("Il n'existe pas de procédure DELETE pour la données de ref. Meal Plans.");
	}
}
