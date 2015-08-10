package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.metier.Element;

@SuppressWarnings("serial")
public class RefBean extends Element {

	protected String libelleLong = null;
	protected boolean actif = true;
	protected String codeLangue = null;
	protected String oldId = null;

	public RefBean () {
	}

	public String getId () {
		return getCode();
	}

	public String getLibelle () {
		return libelle;
	}

	public String getLibelleLong () {
		return libelleLong;
	}

	public boolean isActif () {
		return actif;
	}

	public String getCodeLangue () {
		return codeLangue;
	}

	public String getOldId () {
		return oldId;
	}

	public void setId (String id) {
		setCode(id);
	}

	public void setLibelle (String libelle) {
		this.libelle = libelle;
	}

	public void setLibelleLong (String libelleLong) {
		this.libelleLong = libelleLong;
	}

	public void setActif (boolean actif) {
		this.actif = actif;
	}

	public void setCodeLangue (String codeLangue) {
		this.codeLangue = codeLangue;
	}

	public void setOldId (String oldId) {
		this.oldId = oldId;
	}

	public String toString () {
		String className = getClass().getName();
		StringBuffer sb = new StringBuffer();
		sb.append(className + " : id=" + getCode() + ", libelle=" + getLibelle() + ", libelleLong=" + libelleLong + ", actif=" + isActif());
		return sb.toString();
	}

}