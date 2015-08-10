package com.accor.asa.commun.habilitation.metier;

public class InfoPonderation {

	private Integer codeAxe;
	private	Integer ponderation;
	private Integer codeGroupeEcran;
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "[codeAxe=" ).append( codeAxe ).append( "]," );
		sb.append( "[ponderation=" ).append( ponderation ).append( "]," );
		sb.append( "[codeGroupeEcran=" ).append( codeGroupeEcran ).append( "]," );
		return sb.toString();
	}

	public Integer getCodeAxe() {
		return codeAxe;
	}

	public void setCodeAxe(Integer codeAxe) {
		this.codeAxe = codeAxe;
	}

	public void setCodeAxe(int codeAxe) {
		this.codeAxe = new Integer( codeAxe );
	}

	public Integer getPonderation() {
		return ponderation;
	}

	public void setPonderation(Integer ponderation) {
		this.ponderation = ponderation;
	}

	public void setPonderation(int ponderation) {
		this.ponderation = new Integer( ponderation );
	}

	public Integer getCodeGroupeEcran() {
		return codeGroupeEcran;
	}

	public void setCodeGroupeEcran(Integer codeGroupeEcran) {
		this.codeGroupeEcran = codeGroupeEcran;
	}

	public void setCodeGroupeEcran(int codeGroupeEcran) {
		this.codeGroupeEcran = new Integer( codeGroupeEcran );
	}
}
