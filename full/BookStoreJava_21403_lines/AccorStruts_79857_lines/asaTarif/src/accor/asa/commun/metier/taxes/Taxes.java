package com.accor.asa.commun.metier.taxes;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: SDEKOUM
 * Date: 31 janv. 2006
 * Time: 10:24:38
 * To change this template use File | Settings | File Templates.
 */
public class Taxes implements Serializable {

    protected String idTaxe;
    protected String codeHotel;
    protected String codeTaxe;
    protected String dateDebut;
    protected String dateFin;
    protected String montant;
    protected String uniteCommission;
    protected String uniteExpression;
    protected String codeDevise;
    protected String inclus;
    protected String supprimer;
    protected String codeStatut;

    protected String libelleTaxe;
    protected String libelleCommission;
    protected String libelleExpression;

    /**
     * Constructeur
     */
    public Taxes() {
    }

    /**
     * Constructeur  
     * @param idTaxe
     */
    public Taxes(String idTaxe) {
        this.idTaxe = idTaxe;
    }
    /**
     * Constructeur
     * @param idTaxe
     * @param codeHotel
     * @param codeTaxe
     * @param dateDebut
     * @param dateFin
     * @param montant
     * @param uniteCommission
     * @param uniteExpression
     * @param codeDevise
     * @param inclus
     * @param supprimer
     * @param codeStatut
     */
    public Taxes(String idTaxe, String codeHotel, String codeTaxe, String dateDebut, String dateFin, String montant, String uniteCommission, String uniteExpression, String codeDevise, String inclus, String supprimer, String codeStatut) {
        this.idTaxe = idTaxe;
        this.codeHotel = codeHotel;
        this.codeTaxe = codeTaxe;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.montant = montant;
        this.uniteCommission = uniteCommission;
        this.uniteExpression = uniteExpression;
        this.codeDevise = codeDevise;
        this.inclus = inclus;
        this.supprimer = supprimer;
        this.codeStatut = codeStatut;
    }

    // ================ GETTER AND SETTER ======================

    public String getIdTaxe() {
        return idTaxe;
    }

    public void setIdTaxe(String idTaxe) {
        this.idTaxe = idTaxe;
    }

    public String getCodeHotel() {
        return codeHotel;
    }

    public void setCodeHotel(String codeHotel) {
        this.codeHotel = codeHotel;
    }

    public String getCodeTaxe() {
        return codeTaxe;
    }

    public void setCodeTaxe(String codeTaxe) {
        this.codeTaxe = codeTaxe;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getUniteCommission() {
        return uniteCommission;
    }

    public void setUniteCommission(String uniteCommission) {
        this.uniteCommission = uniteCommission;
    }

    public String getUniteExpression() {
        return uniteExpression;
    }

    public void setUniteExpression(String uniteExpression) {
        this.uniteExpression = uniteExpression;
    }

    public String getCodeDevise() {
        return codeDevise;
    }

    public void setCodeDevise(String codeDevise) {
        this.codeDevise = codeDevise;
    }

    public String getInclus() {
        return inclus;
    }

    public void setInclus(String inclus) {
        this.inclus = inclus;
    }

    public String getSupprimer() {
        return supprimer;
    }

    public void setSupprimer(String supprimer) {
        this.supprimer = supprimer;
    }

    public String getCodeStatut() {
        return codeStatut;
    }

    public void setCodeStatut(String codeStatut) {
        this.codeStatut = codeStatut;
    }

    public String getLibelleTaxe() {
        return libelleTaxe;
    }

    public void setLibelleTaxe(String libelleTaxe) {
        this.libelleTaxe = libelleTaxe;
    }

    public String getLibelleCommission() {
        return libelleCommission;
    }

    public void setLibelleCommission(String libelleCommission) {
        this.libelleCommission = libelleCommission;
    }

    public String getLibelleExpression() {
        return libelleExpression;
    }

    public void setLibelleExpression(String libelleExpression) {
        this.libelleExpression = libelleExpression;
    }

    /**
     * To string
     * @return  String
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[idTaxe=").append(idTaxe).append("]  ");
        sb.append("[codeHotel=").append(codeHotel).append("]  ");
        sb.append("[codeTaxe=").append(codeTaxe).append("]  ");
        sb.append("[dateDebut=").append(dateDebut).append("]  ");
        sb.append("[dateFin=").append(dateFin).append("]  ");
        sb.append("[montant=").append(montant).append("]  ");
        sb.append("[uniteCommission=").append(uniteCommission).append("]  ");
        sb.append("[uniteExpression=").append(uniteExpression).append("]  ");
        sb.append("[codeDevise=").append(codeDevise).append("]  ");
        sb.append("[inclus=").append(inclus).append("]  ");
        sb.append("[supprimer=").append(supprimer).append("]  ");
        sb.append("[codeStatut=").append(codeStatut).append("]  ");

        sb.append("[libelleTaxe=").append(libelleTaxe).append("]  ");
        sb.append("[libelleCommission=").append(libelleCommission).append("]  ");
        sb.append("[libelleExpression=").append(libelleExpression).append("]  ");
        return sb.toString();
    }


}
