package com.accor.asa.commun.donneesdereference.metier;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("serial")
public class TypeSejour extends DonneeReference implements Serializable {

	public static final String STOP_LUNCH = "SL";
	public static final String DAY_USE = "DU";
	public static final String CHAMBRE = "CH";

	public DonneeReference getInstance(HttpServletRequest req) {
		throw new UnsupportedOperationException("méthode non utilisée pour les TypeSejour");
	}
}
