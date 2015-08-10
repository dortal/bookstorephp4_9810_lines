package com.accor.asa.commun.habilitation.metier.acces;

import com.accor.asa.commun.habilitation.metier.Droit;


public class Acces {

	protected String 	codeRole;
	protected String	codeAxe;
	protected String	codeGroupeEcran;
	protected Droit		droit;
	protected Integer	regle;
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[codeRole=").append(codeRole).append("],");
		sb.append("[codeAxe=").append(codeAxe).append("],");
		sb.append("[codeGroupeEcran=").append(codeGroupeEcran).append("],");
		sb.append("[droit=").append(droit).append("],");
		sb.append("[regle=").append(regle).append("],");
		return sb.toString();
	}

	public Acces() {
		this.codeRole = null;
		this.codeAxe = null;
		this.codeGroupeEcran = null;
		this.droit = null;
		this.regle = null;
	}

	public Acces( final String codeRole, final String codeAxe, final String codeGroupeEcran, 
					final Droit droit, final Integer regle ) {
		this.codeRole = codeRole;
		this.codeAxe = codeAxe;
		this.codeGroupeEcran = codeGroupeEcran;
		this.droit = droit;
		this.regle = regle;
	}
	
	public String getCodeAxe() {
		return codeAxe;
	}

	public void setCodeAxe(String codeAxe) {
		this.codeAxe = codeAxe;
	}

	public String getCodeGroupeEcran() {
		return codeGroupeEcran;
	}

	public void setCodeGroupeEcran(String codeGroupeEcran) {
		this.codeGroupeEcran = codeGroupeEcran;
	}

	public String getCodeRole() {
		return codeRole;
	}

	public void setCodeRole(String codeRole) {
		this.codeRole = codeRole;
	}

	public Droit getDroit() {
		return droit;
	}

	public void setDroit(Droit droit) {
		this.droit = droit;
	}

	public Integer getRegle() {
		return regle;
	}

	public void setRegle(Integer regle) {
		this.regle = regle;
	}

	public void setRegle(int regle) {
		this.regle = new Integer(regle);
	}
}
