package com.accor.asa.commun.reference.metier;

import java.util.HashMap;

public class Compte {

	public static final String CODE_COMPTE_SOCIETE = "SC";
	public static final String CODE_COMPTE_ASSOCIATION = "AS";
	public static final String CODE_COMPTE_IATA = "IA";
	private static final String LIBELLE_COMPTE_SOCIETE = "ADMIN_TYPE_COMPTE_SOCIETE";
	private static final String LIBELLE_COMPTE_ASSOCIATION = "ADMIN_TYPE_COMPTE_ASSOCIATION";
	private static final String LIBELLE_COMPTE_IATA = "ADMIN_TYPE_COMPTE_IATA";

	private static Compte instance = null;
	private static HashMap<String, String> listeComptes = null;

	private Compte () {
		listeComptes = new HashMap<String, String>(3);
		listeComptes.put(CODE_COMPTE_SOCIETE, LIBELLE_COMPTE_SOCIETE);
		listeComptes.put(CODE_COMPTE_ASSOCIATION, LIBELLE_COMPTE_ASSOCIATION);
		listeComptes.put(CODE_COMPTE_IATA, LIBELLE_COMPTE_IATA);
	}

	public static Compte getInstance () {
		if (instance == null) {
			instance = new Compte();
		}
		return instance;
	}

	public HashMap<String, String> getListeComptes () {
		return listeComptes;
	}

}