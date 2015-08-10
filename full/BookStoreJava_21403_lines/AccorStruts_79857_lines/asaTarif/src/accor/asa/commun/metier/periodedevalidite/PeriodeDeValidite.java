package com.accor.asa.commun.metier.periodedevalidite;

import java.io.Serializable;

import com.accor.asa.commun.metier.AsaDate;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 2 avr. 2007
 * Time: 14:09:38
 */
@SuppressWarnings("serial")
public class PeriodeDeValidite  implements Serializable {

	public static final int CODE_GROUPE_BUSINESS 	= 0;
	public static final int CODE_GROUPE_TOURISM 	= 1;
	
	protected int     idPeriodeValidite;
	protected AsaDate dateDebut;
	protected AsaDate dateFin;
    protected int idGroupeTarifs;

    /**
     * Constructeur
     */
    public PeriodeDeValidite () {
	}

    /**
     * Constructeur
     * @param idPeriodeValidite
     */
    public PeriodeDeValidite(int idPeriodeValidite) {
        this.idPeriodeValidite = idPeriodeValidite;
    }

    /**
     * Constructeur
     * @param idPeriodeValidite
     * @param dateDebut
     * @param dateFin
     */
    public PeriodeDeValidite(int idPeriodeValidite, AsaDate dateDebut, AsaDate dateFin) {
        this.idPeriodeValidite = idPeriodeValidite;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    /**
     * Constructeur
     * @param idPeriodeValidite
     * @param dateDebut
     * @param dateFin
     * @param idGroupeTarifs
     */
    public PeriodeDeValidite(int idPeriodeValidite, AsaDate dateDebut, AsaDate dateFin, int idGroupeTarifs) {
        this.idPeriodeValidite = idPeriodeValidite;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.idGroupeTarifs = idGroupeTarifs;
    }

    // ================ GETTER AND SETTER ======================

    public int getIdPeriodeValidite() {
        return idPeriodeValidite;
    }

    public void setIdPeriodeValidite(int idPeriodeValidite) {
        this.idPeriodeValidite = idPeriodeValidite;
    }

    public AsaDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(AsaDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public AsaDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(AsaDate dateFin) {
        this.dateFin = dateFin;
    }

    public int getIdGroupeTarifs() {
        return idGroupeTarifs;
    }

    public void setIdGroupeTarifs(int idGroupeTarifs) {
        this.idGroupeTarifs = idGroupeTarifs;
    }

    /**
      * To string
      * @return  String
      */
     public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("[idPeriodeValidite=").append(idPeriodeValidite).append("]  ");
         sb.append("[dateDebut=").append(dateDebut.getFormatedDate(AsaDate.defaulDateFormat)).append("]  ");
         sb.append("[dateFin=").append(dateFin.getFormatedDate(AsaDate.defaulDateFormat)).append("]  ");
         sb.append("[idGroupeTarifs=").append(idGroupeTarifs).append("]  ");
         return sb.toString();
     }


}
