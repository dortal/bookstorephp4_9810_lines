package com.accor.asa.commun.metier.groupe;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 5 mai 2006
 * Time: 10:57:33
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class Groupe  implements Serializable {


    protected int    code;
    protected String nom;
    protected String idPeriodeValidite;
    protected String dateDebutPeriodeValidite;
    protected String dateFinPeriodeValidite;

    /**
     * Constucteur
     */
    public Groupe() {
    }

    /**
     * Constucteur
     * @param code
     */
    public Groupe(int code) {
        this.code = code;
    }

    /**
     * Constucteur
     * @param code
     * @param nom
     * @param idPeriodeValidite
     * @param dateDebutPeriodeValidite
     * @param dateFinPeriodeValidite
     */

    public Groupe(int code, String nom, String idPeriodeValidite, String dateDebutPeriodeValidite, String dateFinPeriodeValidite) {
        this.code = code;
        this.nom = nom;
        this.idPeriodeValidite = idPeriodeValidite;
        this.dateDebutPeriodeValidite = dateDebutPeriodeValidite;
        this.dateFinPeriodeValidite = dateFinPeriodeValidite;
    }

    // ================ GETTER AND SETTER ======================

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdPeriodeValidite() {
        return idPeriodeValidite;
    }

    public void setIdPeriodeValidite(String idPeriodeValidite) {
        this.idPeriodeValidite = idPeriodeValidite;
    }

    public String getDateDebutPeriodeValidite() {
        return dateDebutPeriodeValidite;
    }

    public void setDateDebutPeriodeValidite(String dateDebutPeriodeValidite) {
        this.dateDebutPeriodeValidite = dateDebutPeriodeValidite;
    }

    public String getDateFinPeriodeValidite() {
        return dateFinPeriodeValidite;
    }

    public void setDateFinPeriodeValidite(String dateFinPeriodeValidite) {
        this.dateFinPeriodeValidite = dateFinPeriodeValidite;
    }

    /**
     * To string
     * @return  String
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[code=").append(code).append("]  ");
        sb.append("[nom=").append(nom).append("]  ");
        sb.append("[idPeriodeValidite=").append(idPeriodeValidite).append("]  ");
        sb.append("[dateDebutPeriodeValidite=").append(dateDebutPeriodeValidite).append("]  ");
        sb.append("[dateFinPeriodeValidite=").append(dateFinPeriodeValidite).append("]  ");
        return sb.toString();
    }

}
