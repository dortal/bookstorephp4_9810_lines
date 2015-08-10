package com.accor.asa.commun.metier.grille;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 6 avr. 2007
 * Time: 15:03:14
 */
@SuppressWarnings("serial")
public class Grille implements Serializable {

    protected String idGrilleTarifs;
    protected String codeHotel;
    protected String idFamilleTarif;
    protected String statutTarif;

    /**
     * Constructeur
     */
    public Grille() {
    }

    /**
     * Constructeur
     * @param idGrilleTarifs
     * @param codeHotel
     * @param idFamilleTarif
     * @param statutTarif
     */
    public Grille(String idGrilleTarifs, String codeHotel, String idFamilleTarif, String statutTarif) {
        this.idGrilleTarifs = idGrilleTarifs;
        this.codeHotel = codeHotel;
        this.idFamilleTarif = idFamilleTarif;
        this.statutTarif = statutTarif;
    }


    public String getIdGrilleTarifs() {
        return idGrilleTarifs;
    }

    public void setIdGrilleTarifs(String idGrilleTarifs) {
        this.idGrilleTarifs = idGrilleTarifs;
    }

    public String getCodeHotel() {
        return codeHotel;
    }

    public void setCodeHotel(String codeHotel) {
        this.codeHotel = codeHotel;
    }

    public String getIdFamilleTarif() {
        return idFamilleTarif;
    }

    public void setIdFamilleTarif(String idFamilleTarif) {
        this.idFamilleTarif = idFamilleTarif;
    }

    public String getStatutTarif() {
        return statutTarif;
    }

    public void setStatutTarif(String statutTarif) {
        this.statutTarif = statutTarif;
    }


    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[codeDevise=").append(idGrilleTarifs).append("]  ");
        sb.append("[nomDevise=").append(codeHotel).append("]  ");
        sb.append("[coursUnEuro=").append(idFamilleTarif).append("]  ");
        sb.append("[deviseHotel=").append(statutTarif).append("]  ");
        return sb.toString();
    }
}