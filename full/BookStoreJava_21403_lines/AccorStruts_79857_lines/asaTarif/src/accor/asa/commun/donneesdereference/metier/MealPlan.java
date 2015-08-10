package com.accor.asa.commun.donneesdereference.metier;

import javax.servlet.http.HttpServletRequest;

public class MealPlan extends DonneeReference {
	public DonneeReference getInstance(HttpServletRequest req) {
		TypeBreakfast result = new TypeBreakfast();
		result.setCode(req.getParameter("code"));
		result.setLibelle(req.getParameter("libelle"));
		return result;
	}
}
