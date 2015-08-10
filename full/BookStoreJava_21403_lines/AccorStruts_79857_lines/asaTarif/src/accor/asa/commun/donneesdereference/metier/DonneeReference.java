package com.accor.asa.commun.donneesdereference.metier;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public abstract class DonneeReference {
	public static final String MODIFIE = "Modifié";
	public static final String NOUVEAU = "Nouveau";
	public static final String INCHANGE = "Inchangé";

	protected String codeLangue;
	protected String code;
	protected String libelle;
	protected String statut;

	public static DonneeReference getDonneeRef(List<DonneeReference> list, String code) {
        for (DonneeReference element : list) {
            if (element.getCode().trim().equals(code.trim()))
                return element;
        }
        throw new RuntimeException("code inconnu : " + code);
	}

	public String getCodeLangue() {
		return codeLangue;
	}

	public String getCode() {
		return code;
	}

	public String getLibelle() {
		return libelle;
	}

	public String getStatut() {
		return statut;
	}

	public void setCodeLangue(String string) {
		codeLangue = string;
	}

	public void setCode(String string) {
		code = string;
	}

	public void setLibelle(String string) {
		libelle = string;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public String toString() {
		String shortClassName = getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1);
		return "{" + shortClassName + " : code='" + code + "' ; libelle='" + libelle + "'}";
	}

	public abstract DonneeReference getInstance(HttpServletRequest req);
}