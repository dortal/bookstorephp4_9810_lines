package com.accor.asa.rate.form;

import java.util.Date;
import java.util.List;

import com.accor.asa.commun.hotel.metier.Hotel;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.rate.model.Taxe;
import com.accor.asa.rate.model.TaxeBean;
import com.accor.asa.rate.util.TaxeFormListsProvider;
import com.opensymphony.xwork2.conversion.annotations.ConversionType;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class TaxeFormBean implements Taxe {

    private TaxeBean bean;
    private TaxeFormListsProvider listsProvider;

    public TaxeFormBean(TaxeBean bean, Hotel hotel, Contexte contexte) {
        this.bean = bean;
        this.listsProvider = new TaxeFormListsProvider(hotel, contexte);
    }

    public TaxeBean getBean() {
        return bean;
    }

    public void setBean(TaxeBean bean) {
        this.bean = bean;
    }

    public TaxeFormListsProvider getListsProvider() {
        return listsProvider;
    }

    public void setListsProvider(TaxeFormListsProvider listsProvider) {
        this.listsProvider = listsProvider;
    }


    public String getCode() {
        return bean.getCode();
    }

    public void setCode(String code) {
        bean.setCode(code);
    }

    public String getCodeHotel() {
        return bean.getCodeHotel();
    }

    public void setCodeHotel(String codeHotel) {
        bean.setCodeHotel(codeHotel);
    }

    public Long getIdTaxe() {
        return bean.getIdTaxe();
    }

    public void setIdTaxe(Long idTaxe) {
        bean.setIdTaxe(idTaxe);
    }

    @TypeConversion(converter="com.accor.asa.rate.util.AsaDateConverter",type= ConversionType.CLASS )
    public Date getDateDebut() {
        return bean.getDateDebut();
    }

    @TypeConversion(converter = "com.accor.asa.rate.util.AsaDateConverter", type = ConversionType.CLASS)
    @RequiredFieldValidator(fieldName = "dateDebut", type = ValidatorType.SIMPLE, key = "TAX_MSG_ENTRERBEGINDATE", message = "", shortCircuit = true)
    public void setDateDebut(Date dateDebut) {
        bean.setDateDebut(dateDebut);
    }

    @TypeConversion(converter="com.accor.asa.rate.util.AsaDateConverter",type=ConversionType.CLASS )
    public Date getDateFin() {
        return bean.getDateFin();
    }

    @TypeConversion(converter = "com.accor.asa.rate.util.AsaDateConverter", type = ConversionType.CLASS)
    @RequiredFieldValidator(fieldName = "dateFin", type = ValidatorType.SIMPLE, key = "TAX_MSG_ENTRERENDDATE", message = "", shortCircuit = true)
    public void setDateFin(Date dateFin) {
        bean.setDateFin(dateFin);
    }


    public boolean getInclus() {
        return bean.getInclus();
    }

    public void setInclus(boolean inclus) {
        bean.setInclus(inclus);
    }

    public Double getMontant() {
        return bean.getMontant();
    }

    public void setMontant(Double montant) {
        bean.setMontant(montant);
    }

    public Integer getIdTypePrix() {
        return bean.getIdTypePrix();
    }

    public void setIdTypePrix(Integer idTypePrix) {
        bean.setIdTypePrix(idTypePrix);
    }

    public Integer getIdUniteTaxe() {
        return bean.getIdUniteTaxe();
    }

    public void setIdUniteTaxe(Integer idUniteTaxe) {
        bean.setIdUniteTaxe(idUniteTaxe);
    }

    public String getCodeDevise() {
        return bean.getCodeDevise();
    }

    public void setCodeDevise(String codeDevise) {
        bean.setCodeDevise(codeDevise);
    }

    public Integer getIdStatut() {
        return bean.getIdStatut();
    }

    public void setIdStatut(Integer idStatut) {
        bean.setIdStatut(idStatut);
    }

    public boolean getSupprime() {
        return bean.getSupprime();
    }

    public void setSupprime(boolean supprime) {
        bean.setSupprime(supprime);
    }

    public String[] getRateLevels() {
        return bean.getRateLevels();
    }

    public void setRateLevels(String[] rateLevels) {
        bean.setRateLevels(rateLevels);
    }

    public int getNbRateLevels() {
        return bean.getNbRateLevels();
    }

    public List<String> getListRateLevels() {
        return bean.getListRateLevels();
    }

    public void setListRateLevels(List<String> rateLevels) {
        bean.setListRateLevels(rateLevels);
    }

    public String getRateLevel(int index) {
        return bean.getRateLevel(index);
    }

    public String getRateLevelsOnText() {
        return bean.getRateLevelsOnText();
    }

    public String toString() {
        return bean.toString();
    }


    //------------------------------------ STATIC --------------------------------
    public Integer getStatutNonTransfere() { return STATUT_NONTRANSFERE;}
    public Integer getStatutTransfere() { return STATUT_TRANSFERE;}
    public Integer getTypePrixNone() { return TYPEPRIX_NONE;}
    public Integer getTypePrixMontant() { return TYPEPRIX_MONTANT;}
    public Integer getTypePrixPourcentage() { return TYPEPRIX_POURCENTAGE;}
    public Integer getUniteTaxeNone() { return UNITETAXE_NONE;}
}
