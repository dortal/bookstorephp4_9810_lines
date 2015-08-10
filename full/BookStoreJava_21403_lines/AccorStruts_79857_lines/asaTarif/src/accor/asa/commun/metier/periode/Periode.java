package com.accor.asa.commun.metier.periode;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 5 mai 2006
 * Time: 10:58:00
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("serial")
public class Periode  implements Serializable {
    protected int       numero;
    protected int       codePeriodeGenerique;
    protected String    dateDebutPeriode;
    protected String    dateFinPeriode;
    protected int       weRateApplicable;
    protected String    nomSalonOuJourFerie;
    protected String    prixUtilise;

    /**
     * Constructeur
     */
    public Periode() {
    }

    /**
     * Constructeur
     * @param numero
     */
    public Periode(int numero) {
        this.numero = numero;
    }

    /**
     * Constructeur
     * @param numero
     * @param codePeriodeGenerique
     * @param dateDebutPeriode
     * @param dateFinPeriode
     * @param weRateApplicable
     * @param nomSalonOuJourFerie
     * @param prixUtilise
     */
    public Periode(int numero, int codePeriodeGenerique, String dateDebutPeriode, String dateFinPeriode, int weRateApplicable, String nomSalonOuJourFerie, String prixUtilise) {
        this.numero = numero;
        this.codePeriodeGenerique = codePeriodeGenerique;
        this.dateDebutPeriode = dateDebutPeriode;
        this.dateFinPeriode = dateFinPeriode;
        this.weRateApplicable = weRateApplicable;
        this.nomSalonOuJourFerie = nomSalonOuJourFerie;
        this.prixUtilise = prixUtilise;
    }

    // ================ GETTER AND SETTER ======================

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCodePeriodeGenerique() {
        return codePeriodeGenerique;
    }

    public void setCodePeriodeGenerique(int codePeriodeGenerique) {
        this.codePeriodeGenerique = codePeriodeGenerique;
    }

    public String getDateDebutPeriode() {
        return dateDebutPeriode;
    }

    public void setDateDebutPeriode(String dateDebutPeriode) {
        this.dateDebutPeriode = dateDebutPeriode;
    }

    public String getDateFinPeriode() {
        return dateFinPeriode;
    }

    public void setDateFinPeriode(String dateFinPeriode) {
        this.dateFinPeriode = dateFinPeriode;
    }

    public int getWeRateApplicable() {
        return weRateApplicable;
    }

    public void setWeRateApplicable(int weRateApplicable) {
        this.weRateApplicable = weRateApplicable;
    }

    public String getNomSalonOuJourFerie() {
        return nomSalonOuJourFerie;
    }

    public void setNomSalonOuJourFerie(String nomSalonOuJourFerie) {
        this.nomSalonOuJourFerie = nomSalonOuJourFerie;
    }

    public String getPrixUtilise() {
        return prixUtilise;
    }

    public void setPrixUtilise(String prixUtilise) {
        this.prixUtilise = prixUtilise;
    }


    /**
      * To string
      * @return  String
      */
     public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("[numero=").append(numero).append("]  ");
         sb.append("[codePeriodeGenerique=").append(codePeriodeGenerique).append("]  ");
         sb.append("[dateDebutPeriode=").append(dateDebutPeriode).append("]  ");
         sb.append("[dateFinPeriode=").append(dateFinPeriode).append("]  ");
         sb.append("[weRateApplicable=").append(weRateApplicable).append("]  ");
         sb.append("[nomSalonOuJourFerie=").append(nomSalonOuJourFerie).append("]  ");
         sb.append("[prixUtilise=").append(prixUtilise).append("]  ");
         return sb.toString();
     }

}
