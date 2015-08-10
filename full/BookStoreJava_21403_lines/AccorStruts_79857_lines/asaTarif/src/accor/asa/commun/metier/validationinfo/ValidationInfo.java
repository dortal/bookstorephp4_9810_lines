package com.accor.asa.commun.metier.validationinfo;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 22 nov. 2005
 * Time: 15:55:46
 * To change this template use File | Settings | File Templates.
 */
public class ValidationInfo  implements Serializable {
    protected String codeHotel;
    protected String nomHotel;
    protected String dateOuvertureBusiness;
    protected String dateOuvertureTourism;
    protected String dateFermetureBusiness;
    protected String dateFermetureTourism;
    protected String dateValidationBusiness1;
    protected String dateValidationTourism1;
    protected String dateValidationBusiness2;
    protected String dateValidationTourism2;

    /**
     * Constructeur
     */
    public ValidationInfo() {
    }

    /**
     * Constructeur
     * @param codeHotel
     * @param nomHotel
     * @param dateOuvertureBusiness
     * @param dateOuvertureTourism
     * @param dateFermetureBusiness
     * @param dateFermetureTourism
     * @param dateValidationBusiness1
     * @param dateValidationTourism1
     * @param dateValidationBusiness2
     * @param dateValidationTourism2
     */
    public ValidationInfo(String codeHotel,
                          String nomHotel,
                          String dateOuvertureBusiness,
                          String dateOuvertureTourism,
                          String dateFermetureBusiness,
                          String dateFermetureTourism,
                          String dateValidationBusiness1,
                          String dateValidationTourism1,
                          String dateValidationBusiness2,
                          String dateValidationTourism2) {
        this.codeHotel = codeHotel;
        this.nomHotel = nomHotel;
        this.dateOuvertureBusiness = dateOuvertureBusiness;
        this.dateOuvertureTourism = dateOuvertureTourism;
        this.dateFermetureBusiness = dateFermetureBusiness;
        this.dateFermetureTourism = dateFermetureTourism;
        this.dateValidationBusiness1 = dateValidationBusiness1;
        this.dateValidationTourism1 = dateValidationTourism1;
        this.dateValidationBusiness2 = dateValidationBusiness2;
        this.dateValidationTourism2 = dateValidationTourism2;
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

    public String getDateOuvertureBusiness() {
        return dateOuvertureBusiness;
    }

    public void setDateOuvertureBusiness(String dateOuvertureBusiness) {
        this.dateOuvertureBusiness = dateOuvertureBusiness;
    }

    public String getDateOuvertureTourism() {
        return dateOuvertureTourism;
    }

    public void setDateOuvertureTourism(String dateOuvertureTourism) {
        this.dateOuvertureTourism = dateOuvertureTourism;
    }

    public String getDateFermetureBusiness() {
        return dateFermetureBusiness;
    }

    public void setDateFermetureBusiness(String dateFermetureBusiness) {
        this.dateFermetureBusiness = dateFermetureBusiness;
    }

    public String getDateFermetureTourism() {
        return dateFermetureTourism;
    }

    public void setDateFermetureTourism(String dateFermetureTourism) {
        this.dateFermetureTourism = dateFermetureTourism;
    }

    public String getDateValidationBusiness1() {
        return dateValidationBusiness1;
    }

    public void setDateValidationBusiness1(String dateValidationBusiness1) {
        this.dateValidationBusiness1 = dateValidationBusiness1;
    }

    public String getDateValidationTourism1() {
        return dateValidationTourism1;
    }

    public void setDateValidationTourism1(String dateValidationTourism1) {
        this.dateValidationTourism1 = dateValidationTourism1;
    }

    public String getDateValidationBusiness2() {
        return dateValidationBusiness2;
    }

    public void setDateValidationBusiness2(String dateValidationBusiness2) {
        this.dateValidationBusiness2 = dateValidationBusiness2;
    }

    public String getDateValidationTourism2() {
        return dateValidationTourism2;
    }

    public void setDateValidationTourism2(String dateValidationTourism2) {
        this.dateValidationTourism2 = dateValidationTourism2;
    }
    /**
     * toString
     * @return
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[codeHotel=").append(codeHotel).append("]  ");
        sb.append("[nomHotel=").append(nomHotel).append("]  ");
        sb.append("[dateOuvertureBusiness=").append(dateOuvertureBusiness).append("]  ");
        sb.append("[dateOuvertureTourism=").append(dateOuvertureTourism).append("]  ");
        sb.append("[dateFermetureBusiness=").append(dateFermetureBusiness).append("]  ");
        sb.append("[dateFermetureTourism=").append(dateFermetureTourism).append("]  ");
        sb.append("[dateValidationBusiness1=").append(dateValidationBusiness1).append("]  ");
        sb.append("[dateValidationTourism1=").append(dateValidationTourism1).append("]  ");
        sb.append("[dateValidationBusiness2=").append(dateValidationBusiness2).append("]  ");
        sb.append("[dateValidationTourism2=").append(dateValidationTourism2).append("]  ");
        return sb.toString();
    }
}
