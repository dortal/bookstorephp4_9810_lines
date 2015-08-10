package com.accor.asa.commun.reference.metier;

import java.util.HashMap;
import java.util.Map;

public class TypeDocument {

	public static final String CODE_HTML = "HTML";
	public static final String CODE_PDF = "PDF";
	public static final String LIBELLE_HTML = "HTML";
	public static final String LIBELLE_PDF = "PDF";

	private static TypeDocument instance = null;
	private static Map<String, String> listeTypesDocument = null;

	private TypeDocument () {
		listeTypesDocument = new HashMap<String, String>(2);
		listeTypesDocument.put(CODE_HTML, LIBELLE_HTML);
		listeTypesDocument.put(CODE_PDF, LIBELLE_PDF);
	}

	public static TypeDocument getInstance () {
		if (instance == null) {
			instance = new TypeDocument();
		}
		return instance;
	}

	public Map<String, String> getListeTypesDocument () {
		return listeTypesDocument;
	}

}