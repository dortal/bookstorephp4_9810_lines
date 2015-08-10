package com.accor.asa.commun.habilitation.metier.ecran;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 9 oct. 2006
 * Time: 14:31:28
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class Ecran implements Serializable {
    protected String 	codeEcran;
    protected String 	codeGroupeEcran;
    protected String 	procHabilitation;
    protected String 	fichierAide;
    protected boolean 	ecranVente;
    protected boolean 	ecranTarif;
    protected boolean 	ecranTranslate;
    protected boolean 	ecranAdmin;

    /**
     * Constructeur
     */
    public Ecran() {
    }


    /**
     * Constructeur
     * @param codeEcran
     * @param codeGroupeEcran
     * @param procHabilitation
     */
    public Ecran(String codeEcran, String codeGroupeEcran, String procHabilitation) {
        this.codeEcran = codeEcran;
        this.codeGroupeEcran = codeGroupeEcran;
        this.procHabilitation = procHabilitation;
    }

    public Ecran(	String codeEcran, String codeGroupeEcran, String procHabilitation, 
    					String fichierAide, boolean isEcranVente, boolean isEcranTarif, 
    					boolean isEcranTranslate, boolean isEcranAdmin ) {
        this.codeEcran = codeEcran;
        this.codeGroupeEcran = codeGroupeEcran;
        this.procHabilitation = procHabilitation;
        this.fichierAide = fichierAide;
        this.ecranVente = isEcranVente;
        this.ecranTarif = isEcranTarif;
        this.ecranTranslate = isEcranTranslate;
        this.ecranAdmin = isEcranAdmin;
    }

    public String getCodeEcran() {
        return codeEcran;
    }

    public void setCodeEcran(String codeEcran) {
        this.codeEcran = codeEcran;
    }

    public String getCodeGroupeEcran() {
        return codeGroupeEcran;
    }

    public void setCodeGroupeEcran(String codeGroupeEcran) {
        this.codeGroupeEcran = codeGroupeEcran;
    }

    public String getProcHabilitation() {
        return procHabilitation;
    }

    public void setProcHabilitation(String procHabilitation) {
        this.procHabilitation = procHabilitation;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Ecran that = (Ecran) o;

        if (!codeEcran.equals(that.codeEcran)) return false;
        if (!codeGroupeEcran.equals(that.codeGroupeEcran)) return false;
        if (!procHabilitation.equals(that.procHabilitation)) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = codeEcran.hashCode();
        result = 29 * result + codeGroupeEcran.hashCode();
        result = 29 * result + procHabilitation.hashCode();
        return result;
    }

    /**
     * To string
     * @return
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[codeEcran=").append(codeEcran).append("]  ");
        sb.append("[codeGroupeEcran=").append(codeGroupeEcran).append("]  ");
        sb.append("[procHabilitation=").append(procHabilitation).append("]  ");
        return sb.toString();
    }


	public boolean isEcranAdmin() {
		return ecranAdmin;
	}


	public void setEcranAdmin(boolean ecranAdmin) {
		this.ecranAdmin = ecranAdmin;
	}


	public boolean isEcranTarif() {
		return ecranTarif;
	}


	public void setEcranTarif(boolean ecranTarif) {
		this.ecranTarif = ecranTarif;
	}


	public boolean isEcranTranslate() {
		return ecranTranslate;
	}


	public void setEcranTranslate(boolean ecranTranslate) {
		this.ecranTranslate = ecranTranslate;
	}


	public boolean isEcranVente() {
		return ecranVente;
	}


	public void setEcranVente(boolean ecranVente) {
		this.ecranVente = ecranVente;
	}


	public String getFichierAide() {
		return fichierAide;
	}


	public void setFichierAide(String fichierAide) {
		this.fichierAide = fichierAide;
	}
}
