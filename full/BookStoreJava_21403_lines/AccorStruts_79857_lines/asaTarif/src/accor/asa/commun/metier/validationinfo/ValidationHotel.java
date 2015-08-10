package com.accor.asa.commun.metier.validationinfo;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 11 avr. 2007
 * Time: 10:19:59
 */
public class ValidationHotel  implements Serializable {

    protected String codeHotel;
    protected String nomHotel;
    protected String dateValidationBusiness;
    protected String dateValidationTourism;

    /**
     * Constructeur
     */
    public ValidationHotel() {
    }

    /**
     * Constructeur
     * @param codeHotel
     * @param nomHotel
     * @param dateValidationBusiness
     * @param dateValidationTourism
     */
    public ValidationHotel(String codeHotel, String nomHotel, String dateValidationBusiness, String dateValidationTourism) {
        this.codeHotel = codeHotel;
        this.nomHotel = nomHotel;
        this.dateValidationBusiness = dateValidationBusiness;
        this.dateValidationTourism = dateValidationTourism;
    }
    // ================ GETTER AND SETTER ======================


    public String getCodeHotel() {
        return codeHotel;
    }

    public void setCodeHotel(String codeHotel) {
        this.codeHotel = codeHotel;
    }

    public String getNomHotel() {
        return nomHotel;
    }

    public void setNomHotel(String nomHotel) {
        this.nomHotel = nomHotel;
    }

    public String getDateValidationBusiness() {
        return dateValidationBusiness;
    }

    public void setDateValidationBusiness(String dateValidationBusiness) {
        this.dateValidationBusiness = dateValidationBusiness;
    }

    public String getDateValidationTourism() {
        return dateValidationTourism;
    }

    public void setDateValidationTourism(String dateValidationTourism) {
        this.dateValidationTourism = dateValidationTourism;
    }
    /**
     * ToString
     * @return
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[codeHotel=").append(codeHotel).append("]  ");
        sb.append("[nomHotel=").append(nomHotel).append("]  ");
        sb.append("[dateValidationBusiness=").append(dateValidationBusiness).append("]  ");
        sb.append("[dateValidationTourism=").append(dateValidationTourism).append("]  ");
        return sb.toString();
    }
}
