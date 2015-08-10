package com.accor.asa.rate.form;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.reference.metier.MealPlanRefBean;
import com.accor.asa.rate.RatesException;
import com.accor.asa.rate.common.Log;
import com.accor.asa.rate.model.GrilleBean;
import com.accor.asa.rate.model.LeisureRateBean;
import com.accor.asa.rate.util.DefaultGrilleValuesProvider;
import com.accor.asa.rate.util.RateFormListsProvider;
import com.accor.asa.rate.util.WeekDaysProvider;
import com.opensymphony.xwork2.conversion.annotations.ConversionType;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class LeisureRateFormBean extends RateFormBean {

    private LeisureRateBean bean;
    private DefaultGrilleValuesProvider defaultGrilleValuesProvider;
    private RateFormListsProvider listsProvider;

    public LeisureRateFormBean(LeisureRateBean bean, Contexte contexte, GrilleBean grille) {
        this.bean = bean;
        defaultGrilleValuesProvider = new DefaultGrilleValuesProvider(grille, contexte);
        listsProvider = new RateFormListsProvider(grille, contexte);
        setWeekDays(WeekDaysProvider.getWeekDays(contexte));
    }

    public String getKey() {
        return bean.getKey();
    }

    public void setKey(String key) {
        bean.setKey(key);
    }

    public Long getIdGrille() {
        return bean.getIdGrille();
    }

    public void setIdGrille(Long idGrille) {
        bean.setIdGrille(idGrille);
    }



    public String getCodeRateLevel() {
        return bean.getCodeRateLevel();
    }

    @RequiredStringValidator(fieldName = "codeRateLevel", type = ValidatorType.SIMPLE, message = "", key="LSR_MSG_ENTRER_RATELEVEL", shortCircuit = true)
    public void setCodeRateLevel(String codeRateLevel) {
        bean.setCodeRateLevel(codeRateLevel);
    }

    public String[] getCodesProduit() {
        return bean.getCodesProduit();
    }

    public String getCodeProduit() {
        return bean.getCodeProduit();
    }

    @RequiredFieldValidator(fieldName = "codesProduit", type = ValidatorType.SIMPLE, message = "", key = "LSR_MSG_ENTRER_CHAMBRE", shortCircuit = true)
    public void setCodesProduit(String[] codeProduit) {
        bean.setCodesProduit(codeProduit);
    }

    public void setCodeProduit(String unCodeProduit) {
        bean.setCodeProduit(unCodeProduit);
    }

    @TypeConversion(converter = "com.accor.asa.rate.util.AsaDateConverter", type = ConversionType.CLASS)
    public Date getDateDebut() {
        return bean.getDateDebut();
    }

    @TypeConversion(converter = "com.accor.asa.rate.util.AsaDateConverter", type = ConversionType.CLASS)
    @RequiredFieldValidator(fieldName = "dateDebut", type = ValidatorType.SIMPLE, message = "", key = "LSR_MSG_ENTRER_DATEDEBUT", shortCircuit = true)
    public void setDateDebut(Date dateDebut) {
        bean.setDateDebut(dateDebut);
    }

    @TypeConversion(converter = "com.accor.asa.rate.util.AsaDateConverter", type = ConversionType.CLASS)
    public Date getDateFin() {
        return bean.getDateFin();
    }

    @TypeConversion(converter = "com.accor.asa.rate.util.AsaDateConverter", type = ConversionType.CLASS)
    @RequiredFieldValidator(fieldName = "dateFin", type = ValidatorType.SIMPLE, message = "", key = "LSR_MSG_ENTRER_DATEFIN", shortCircuit = true)
    public void setDateFin(Date dateFin) {
        bean.setDateFin(dateFin);
    }

    public Integer getIdDureeSejour() {
        return bean.getIdDureeSejour();
    }

    public void setIdDureeSejour(Integer idDureeSejour) {
        bean.setIdDureeSejour(idDureeSejour);
    }

    public Integer getIdDivSemaine() {
        return bean.getIdDivSemaine();
    }

    public void setIdDivSemaine(Integer idDivSemaine) {
        bean.setIdDivSemaine(idDivSemaine);
    }

    public String getCodePeriode() {
        return bean.getCodePeriode();
    }

    public void setCodePeriode(String codePeriode) {
        bean.setCodePeriode(codePeriode);
    }

    public String getLibelleSalon() {
        return bean.getLibelleSalon();
    }

    public void setLibelleSalon(String libelleSalon) {
        bean.setLibelleSalon(libelleSalon);
    }

    public String getCodeMealPlan() {
        return bean.getCodeMealPlan();
    }

    public void setCodeMealPlan(String codeMealPlan) {
        bean.setCodeMealPlan(codeMealPlan);
    }

    public String getCodePetitDej() {
        return bean.getCodePetitDej();
    }

    public void setCodePetitDej(String codePetitDej) {
        bean.setCodePetitDej(codePetitDej);
    }

    public String getCodeDevise() {
        return bean.getCodeDevise();
    }

    public void setCodeDevise(String codeDevise) {
        bean.setCodeDevise(codeDevise);
    }

    public String getUniteCommission() {
        return bean.getUniteCommission();
    }

    public void setUniteCommission(String uniteCommission) {
        bean.setUniteCommission(uniteCommission);
    }

    public Double getValueCommission() {
        Double d=bean.getValueCommission();
        return d>0?d:null;
    }

    public void setValueCommission(Double valueCommission) {
        bean.setValueCommission(valueCommission);
    }


    public String getCommission() {
        if (bean.getValueCommission() > 0)
            return bean.getValueCommission() + " " + bean.getUniteCommission();
        return "";
    }

    public Boolean getLunWe() {
        return bean.getLunWe();
    }

    public void setLunWe(Boolean lunWe) {
        bean.setLunWe(lunWe);
    }

    public Boolean getMarWe() {
        return bean.getMarWe();
    }

    public void setMarWe(Boolean marWe) {
        bean.setMarWe(marWe);
    }

    public Boolean getMerWe() {
        return bean.getMerWe();
    }

    public void setMerWe(Boolean merWe) {
        bean.setMerWe(merWe);
    }

    public Boolean getJeuWe() {
        return bean.getJeuWe();
    }

    public void setJeuWe(Boolean jeuWe) {
        bean.setJeuWe(jeuWe);
    }

    public Boolean getVenWe() {
        return bean.getVenWe();
    }

    public void setVenWe(Boolean venWe) {
        bean.setVenWe(venWe);
    }

    public Boolean getSamWe() {
        return bean.getSamWe();
    }

    public void setSamWe(Boolean samWe) {
        bean.setSamWe(samWe);
    }

    public Boolean getDimWe() {
        return bean.getDimWe();
    }

    public void setDimWe(Boolean dimWe) {
        bean.setDimWe(dimWe);
    }


    public Double getPrix() {
        return bean.getPrix();
    }

    @RequiredFieldValidator(fieldName = "prix", type = ValidatorType.SIMPLE, message = "", key = "LSR_MSG_ENTRERPRICE", shortCircuit = true)
    public void setPrix(Double prix) {
        this.bean.setPrix(prix);
    }

    public Double getPrixSupSGL() {
        return bean.getPrixSupSGL();
    }

    @RequiredFieldValidator(fieldName = "prixSupSGL", type = ValidatorType.SIMPLE, message = "", key = "LSR_MSG_ENTRERSUPPSGL", shortCircuit = true)
    public void setPrixSupSGL(Double prixSupSGL) {
        bean.setPrixSupSGL(prixSupSGL);
    }

    public Double getPrixSupTRI() {
        return bean.getPrixSupTRI();
    }

    public void setPrixSupTRI(Double prixSupTRI) {
        bean.setPrixSupTRI(prixSupTRI);
    }

    public Double getPrixSupQUAD() {
        return bean.getPrixSupQUAD();
    }

    public void setPrixSupQUAD(Double prixSupQUAD) {
        bean.setPrixSupQUAD(prixSupQUAD);
    }

    public Double getBagageInOrOut() {
        return bean.getBagageInOrOut();
    }

    public void setBagageInOrOut(Double bagageInOrOut) {
        bean.setBagageInOrOut(bagageInOrOut);
    }

    public Double getBagageInAndOut() {
        return bean.getBagageInAndOut();
    }

    public void setBagageInAndOut(Double bagageInAndOut) {
        bean.setBagageInAndOut(bagageInAndOut);
    }

    public Double getSupplDemPens() {
        return bean.getSupplDemPens();
    }

    public void setSupplDemPens(Double supplDemPens) {
        bean.setSupplDemPens(supplDemPens);
    }

    public Double getSupplPensCompl() {
        return bean.getSupplPensCompl();
    }

    public void setSupplPensCompl(Double supplPensCompl) {
        bean.setSupplPensCompl(supplPensCompl);
    }

    public Double getPrixPdj() {
        Double d = bean.getPrixPdj();
        return d > 0 ? d : null;
    }

    public void setPrixPdj(Double prixPdj) {
        bean.setPrixPdj(prixPdj);
    }

    public boolean isPdjInclus() {
        boolean isPdjInclus = false;
        try{
            String codeMealPlan = getCodeMealPlan();
            if(StringUtils.isNotBlank(codeMealPlan)) {
                for (MealPlanRefBean mp : listsProvider.getListMealplans()) {
                    if (codeMealPlan.equals(mp.getCode()) && mp.isPdjInclu()) {
                        isPdjInclus = true;
                        break;
                    }
                }
            }
        } catch(RatesException e) {
            Log.critical("","LeisureRateFormBean","isPdjInclus",e.getMessage());
        }
        return isPdjInclus;
    }

    public LeisureRateBean getBean() {
        return bean;
    }

    public DefaultGrilleValuesProvider getDefaultGrilleValuesProvider() {
        return defaultGrilleValuesProvider;
    }

    public void setDefaultGrilleValuesProvider(DefaultGrilleValuesProvider defaultGrilleValuesProvider) {
        this.defaultGrilleValuesProvider = defaultGrilleValuesProvider;
    }

    public RateFormListsProvider getListsProvider() {
        return listsProvider;
    }

    public void setListsProvider(RateFormListsProvider listsProvider) {
        this.listsProvider = listsProvider;
    }
}
