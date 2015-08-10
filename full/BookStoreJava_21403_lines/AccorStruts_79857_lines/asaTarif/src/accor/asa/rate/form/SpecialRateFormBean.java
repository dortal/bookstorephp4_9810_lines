package com.accor.asa.rate.form;

import java.util.Date;

import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.rate.model.GrilleBean;
import com.accor.asa.rate.model.SpecialRateBean;
import com.accor.asa.rate.util.DefaultGrilleValuesProvider;
import com.accor.asa.rate.util.RateFormListsProvider;
import com.opensymphony.xwork2.conversion.annotations.ConversionType;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

/**
 * @author <a href="mailto:Saddek.dekoum@accor.com>">Saddek.dekoum@accor.com</a>
 * @version $Id: $
 */
public class SpecialRateFormBean extends RateFormBean {

    private SpecialRateBean bean;
    private DefaultGrilleValuesProvider defaultGrilleValuesProvider;
    private RateFormListsProvider listsProvider;

    public SpecialRateFormBean(SpecialRateBean bean, Contexte contexte, GrilleBean grille) {
        this.bean = bean;
        defaultGrilleValuesProvider = new DefaultGrilleValuesProvider(grille, contexte);
        listsProvider = new RateFormListsProvider(grille, contexte);
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

    public SpecialRateBean getBean() {
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

    public Integer getCodeOffreSpeciale() {
        return bean.getCodeOffreSpeciale();
    }

    public void setCodeOffreSpeciale(Integer codeOffreSpeciale) {
        bean.setCodeOffreSpeciale(codeOffreSpeciale);
    }

    @TypeConversion(converter = "com.accor.asa.rate.util.AsaDateConverter", type = ConversionType.CLASS)
    public Date getDateDebut() {
        return bean.getDateDebut();
    }

    @TypeConversion(converter = "com.accor.asa.rate.util.AsaDateConverter", type = ConversionType.CLASS)
    @RequiredFieldValidator(fieldName = "dateDebut", type = ValidatorType.SIMPLE, message = "", key = "SPE_MSG_ENTRER_DATEDEBUT", shortCircuit = true)
    public void setDateDebut(Date dateDebut) {
        bean.setDateDebut(dateDebut);
    }

    @TypeConversion(converter = "com.accor.asa.rate.util.AsaDateConverter", type = ConversionType.CLASS)
    public Date getDateFin() {
        return bean.getDateFin();
    }

    @TypeConversion(converter = "com.accor.asa.rate.util.AsaDateConverter", type = ConversionType.CLASS)
    @RequiredFieldValidator(fieldName = "dateFin", type = ValidatorType.SIMPLE, message = "", key = "SPE_MSG_ENTRER_DATEFIN", shortCircuit = true)
    public void setDateFin(Date dateFin) {
        bean.setDateFin(dateFin);
    }

    public boolean isObligatoire() {
        return bean.isObligatoire();
    }

    public void setObligatoire(boolean obligatoire) {
        bean.setObligatoire(obligatoire);
    }

    public Double getPrix() {
        Double d= bean.getPrix();
        return d>0?d:null;
    }

    public void setPrix(Double prix) {
        this.bean.setPrix(prix);
    }

    public Integer getIdUnitePrix() {
        return bean.getIdUnitePrix();
    }

    public void setIdUnitePrix(Integer idUnitePrix) {
        bean.setIdUnitePrix(idUnitePrix);
    }

    public Integer getIdTypePrix() {
        return bean.getIdTypePrix();
    }

    public void setIdTypePrix(Integer idTypePrix) {
        bean.setIdTypePrix(idTypePrix);
    }

    public String getCodeDevise() {
        return bean.getCodeDevise();
    }

    public void setCodeDevise(String codeDevise) {
        bean.setCodeDevise(codeDevise);
    }

    public boolean isTousMarches() {
        return bean.isTousMarches();
    }

    public void setTousMarches(boolean tousMarches) {
        bean.setTousMarches(tousMarches);
    }

    public String getCodePays1() {
        return bean.getCodePays1();
    }

    public void setCodePays1(String codePays1) {
        bean.setCodePays1(codePays1);
    }

    public String getCodePays2() {
        return bean.getCodePays2();
    }

    public void setCodePays2(String codePays2) {
        bean.setCodePays2(codePays2);
    }

    public String getCodePays3() {
        return bean.getCodePays3();
    }

    public void setCodePays3(String codePays3) {
        bean.setCodePays3(codePays3);
    }

    public String getCodePays4() {
        return bean.getCodePays4();
    }

    public void setCodePays4(String codePays4) {
        bean.setCodePays4(codePays4);
    }

    public String getCodePays5() {
        return bean.getCodePays5();
    }

    public void setCodePays5(String codePays5) {
        bean.setCodePays5(codePays5);
    }

    public String getCodeContinent1() {
        return bean.getCodeContinent1();
    }

    public void setCodeContinent1(String codeContinent1) {
        bean.setCodeContinent1(codeContinent1);
    }

    public String getCodeContinent2() {
        return bean.getCodeContinent2();
    }

    public void setCodeContinent2(String codeContinent2) {
        bean.setCodeContinent2(codeContinent2);
    }

    public String getCodeContinent3() {
        return bean.getCodeContinent3();
    }

    public void setCodeContinent3(String codeContinent3) {
        bean.setCodeContinent3(codeContinent3);
    }

    public String getCommentaire() {
        return bean.getCommentaire();
    }

    public void setCommentaire(String commentaire) {
        bean.setCommentaire(commentaire);
    }
}

