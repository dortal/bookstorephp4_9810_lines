package com.accor.asa.rate.decorator;

import java.util.Date;

import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.reference.metier.TypePrixRefBean;
import com.accor.asa.commun.reference.metier.UniteTaxeRefBean;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.model.Taxe;
import com.accor.asa.rate.model.TaxeBean;
import com.opensymphony.xwork2.conversion.annotations.ConversionType;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class TaxeDecorator implements Taxe {

    private TaxeBean bean;
    private Contexte contexte;
    private TypePrixRefBean     typePrix;
    private UniteTaxeRefBean    uniteTaxe;

    public TaxeDecorator(TaxeBean bean, Contexte contexte) {
        if (bean == null)
            throw new RuntimeException("The rate bean for a TaxeDecorator can not be null");
        this.bean = bean;
        this.contexte=contexte;
    }

    public TaxeBean getBean() {
        return bean;
    }

    public void setBean(TaxeBean bean) {
        this.bean = bean;
    }

    public TypePrixRefBean getTypePrix() {
        if(typePrix==null) {
            try {
                typePrix = TypePrixRefBean.getCacheList(contexte).getTypePrixById(getIdTypePrix());
            } catch (Exception ex) {
                Log.critical(contexte.getCodeUtilisateur(), "TaxeDecorator", "getTypePrix", ex.getMessage());
            }
        }
        return typePrix;
    }

    public UniteTaxeRefBean getUniteTaxe() {
        if(uniteTaxe==null) {
            try {
                uniteTaxe = UniteTaxeRefBean.getCacheList(contexte).getUniteTaxeById(getIdUniteTaxe());
            } catch (Exception ex) {
                Log.critical(contexte.getCodeUtilisateur(), "TaxeDecorator", "getUniteTaxe", ex.getMessage());
            }
        }
        return uniteTaxe;
    }

    public String getCode() {
        return bean.getCode();
    }

    public String getNom(){
        return bean.getNom();
    }

    public Long getIdTaxe(){
        return bean.getIdTaxe();
    }

    @TypeConversion(converter="com.accor.asa.rate.util.AsaDateConverter",type= ConversionType.CLASS )
    public Date getDateDebut(){
        return bean.getDateDebut();
    }
    
    @TypeConversion(converter="com.accor.asa.rate.util.AsaDateConverter",type=ConversionType.CLASS )
    public Date getDateFin(){
        return bean.getDateFin();
    }

    public boolean getInclus() {
        return bean.getInclus();
    }

    public Double getMontant(){
        return bean.getMontant();
    }

    public Integer getIdTypePrix(){
        return bean.getIdTypePrix();
    }

    public Integer getIdUniteTaxe(){
        return bean.getIdUniteTaxe();
    }

    public String getCodeDevise(){
        return bean.getCodeDevise();
    }

    public Integer getIdStatut(){
        return bean.getIdStatut();
    }

    public boolean getSupprime(){
        return bean.getSupprime();
    }

    public String[] getRateLevels() {
        return bean.getRateLevels();
    }

    public int getNbRateLevels() {
        return bean.getNbRateLevels();
    }

    public String getRateLevelsOnText() {
        return bean.getRateLevelsOnText();
    }

    public boolean isTransferred() {
        return getIdStatut()==STATUT_TRANSFERE;
    }
    public boolean isInvalidate() {
        return getSupprime();
    }
}
