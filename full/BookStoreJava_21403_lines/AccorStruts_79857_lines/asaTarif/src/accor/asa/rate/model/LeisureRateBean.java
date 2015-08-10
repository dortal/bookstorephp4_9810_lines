package com.accor.asa.rate.model;

import java.util.Date;

import com.accor.asa.commun.metier.AsaDate;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class LeisureRateBean extends RateBean implements LeisureRate {

    protected String codeRateLevel;
    protected String[] codesProduit;
    protected Date dateDebut;
    protected Date dateFin;
    protected Double prix;
    protected Double prixSupSGL;
    protected Double prixSupTRI;
    protected Double prixSupQUAD;
    protected Integer idDureeSejour;
    protected Integer idDivSemaine;
    protected Double bagageInOrOut;
    protected Double bagageInAndOut;
    protected String codePeriode;
    protected String libelleSalon;
    protected String codeMealPlan;
    protected Double supplDemPens;
    protected Double supplPensCompl;
    protected String codePetitDej;
    protected Double prixPdj;
    protected String codeDevise;
    protected Double valueCommission;
    protected String uniteCommission;
    protected Boolean lunWe;
    protected Boolean marWe;
    protected Boolean merWe;
    protected Boolean jeuWe;
    protected Boolean venWe;
    protected Boolean samWe;
    protected Boolean dimWe;


    public String getCodeRateLevel() {
        return codeRateLevel;
    }

    public void setCodeRateLevel(String codeRateLevel) {
        this.codeRateLevel = codeRateLevel;
    }

    public String[] getCodesProduit() {
        return codesProduit;
    }

    public String getCodeProduit() {
        if (codesProduit != null && codesProduit.length > 0)
            return codesProduit[0];
        else
            return null;
    }

    public void setCodesProduit(String[] codeProduit) {
        this.codesProduit = codeProduit;
    }

    public void setCodeProduit(String unCodeProduit) {
        if (codesProduit == null)
            codesProduit = new String[1];
        codesProduit[0] = unCodeProduit;
    }

    public int getNbreProduits() {
        return (codesProduit!=null)?codesProduit.length:0;
    }
    
    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Integer getIdDureeSejour() {
        return idDureeSejour;
    }

    public void setIdDureeSejour(Integer idDureeSejour) {
        this.idDureeSejour = idDureeSejour;
    }

    public Integer getIdDivSemaine() {
        return idDivSemaine;
    }

    public void setIdDivSemaine(Integer idDivSemaine) {
        this.idDivSemaine = idDivSemaine;
    }

    public String getCodePeriode() {
        return codePeriode;
    }

    public void setCodePeriode(String codePeriode) {
        this.codePeriode = codePeriode;
    }

    public String getLibelleSalon() {
        return libelleSalon;
    }

    public void setLibelleSalon(String libelleSalon) {
        this.libelleSalon = libelleSalon;
    }

    public String getCodeMealPlan() {
        return codeMealPlan;
    }

    public void setCodeMealPlan(String codeMealPlan) {
        this.codeMealPlan = codeMealPlan;
    }

    public String getCodePetitDej() {
        return codePetitDej;
    }

    public void setCodePetitDej(String codePetitDej) {
        this.codePetitDej = codePetitDej;
    }

    public String getCodeDevise() {
        return codeDevise;
    }

    public void setCodeDevise(String codeDevise) {
        this.codeDevise = codeDevise;
    }

    public String getUniteCommission() {
        return uniteCommission;
    }

    public void setUniteCommission(String uniteCommission) {
        this.uniteCommission = uniteCommission;
    }

    public Boolean getLunWe() {
        return lunWe;
    }

    public void setLunWe(Boolean lunWe) {
        this.lunWe = lunWe;
    }

    public Boolean getMarWe() {
        return marWe;
    }

    public void setMarWe(Boolean marWe) {
        this.marWe = marWe;
    }

    public Boolean getMerWe() {
        return merWe;
    }

    public void setMerWe(Boolean merWe) {
        this.merWe = merWe;
    }

    public Boolean getJeuWe() {
        return jeuWe;
    }

    public void setJeuWe(Boolean jeuWe) {
        this.jeuWe = jeuWe;
    }

    public Boolean getVenWe() {
        return venWe;
    }

    public void setVenWe(Boolean venWe) {
        this.venWe = venWe;
    }

    public Boolean getSamWe() {
        return samWe;
    }

    public void setSamWe(Boolean samWe) {
        this.samWe = samWe;
    }

    public Boolean getDimWe() {
        return dimWe;
    }

    public void setDimWe(Boolean dimWe) {
        this.dimWe = dimWe;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Double getPrixSupSGL() {
        return prixSupSGL;
    }


    public void setPrixSupSGL(Double prixSupSGL) {
        this.prixSupSGL = prixSupSGL;
    }


    public Double getPrixSupTRI() {
        return prixSupTRI;
    }

    public void setPrixSupTRI(Double prixSupTRI) {
        this.prixSupTRI = prixSupTRI;
    }

    public Double getPrixSupQUAD() {
        return prixSupQUAD;
    }

    public void setPrixSupQUAD(Double prixSupQUAD) {
        this.prixSupQUAD = prixSupQUAD;
    }

    public Double getBagageInOrOut() {
        return bagageInOrOut;
    }

    public void setBagageInOrOut(Double bagageInOrOut) {
        this.bagageInOrOut = bagageInOrOut;
    }

    public Double getBagageInAndOut() {
        return bagageInAndOut;
    }

    public void setBagageInAndOut(Double bagageInAndOut) {
        this.bagageInAndOut = bagageInAndOut;
    }

    public Double getSupplDemPens() {
        return supplDemPens;
    }

    public void setSupplDemPens(Double supplDemPens) {
        this.supplDemPens = supplDemPens;
    }

    public Double getSupplPensCompl() {
        return supplPensCompl;
    }

    public void setSupplPensCompl(Double supplPensCompl) {
        this.supplPensCompl = supplPensCompl;
    }

    public Double getPrixPdj() {
        return prixPdj;
    }

    public void setPrixPdj(Double prixPdj) {
        this.prixPdj = prixPdj;
    }

    public Double getValueCommission() {
        return (valueCommission == null ? (double) 0 : valueCommission);
    }

    public void setValueCommission(Double valueCommission) {
        this.valueCommission = valueCommission;
    }

    //=========================================================================================
    //=============                       OTHER                  ==============================
    //=========================================================================================
    
    public String generateBeanKey() {
        StringBuffer sb = new StringBuffer(String.valueOf(getIdGrille()));
        sb.append("_").append(getCodeRateLevel())
                .append("_").append(getCodeProduit())
                .append("_").append(getCodeMealPlan())
                .append("_").append(getCodePeriode())
                .append("_").append(getIdDivSemaine())
                .append("_").append(new AsaDate(getDateDebut()));
        return sb.toString();
    }


    /**
     * To String
     *
     * @return
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("(").append(key).append(")");
        sb.append("_").append(idGrille).append("_").append(codeRateLevel);
        if (codesProduit != null) {
            sb.append("_[");
            for (String chambre : codesProduit)
                sb.append((sb.toString().endsWith("[")) ? chambre : "," + chambre);
            sb.append("]");
        } else {
            sb.append("_").append("null");
        }
        sb.append("_").append(codeMealPlan).
                append("_").append(codePeriode).
                append("_").append(idDivSemaine).
                append("_").append(new AsaDate(dateDebut)).
                append("_").append(new AsaDate(dateFin)).
                append("_").append(prix).
                append("_").append(prixSupSGL).
                append("_").append(prixSupTRI).
                append("_").append(prixSupQUAD).
                append("_").append(idDureeSejour).
                append("_").append(libelleSalon).
                append("_").append(codePetitDej).
                append("_").append(prixPdj).
                append("_").append(codeDevise).
                append("_").append(valueCommission).
                append("_").append(uniteCommission).
                append("_").append(lunWe).
                append("_").append(marWe).
                append("_").append(merWe).
                append("_").append(jeuWe).
                append("_").append(venWe).
                append("_").append(samWe).
                append("_").append(dimWe).
                append("_").append(bagageInOrOut).
                append("_").append(bagageInAndOut).
                append("_").append(supplDemPens).
                append("_").append(supplPensCompl);
        return sb.toString();
    }
}
