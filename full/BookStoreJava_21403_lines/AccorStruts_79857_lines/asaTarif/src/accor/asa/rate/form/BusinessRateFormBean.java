package com.accor.asa.rate.form;

import java.util.Date;

import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.reference.metier.MealPlanRefBean;
import com.accor.asa.rate.model.BusinessRateBean;
import com.accor.asa.rate.model.GrilleBean;
import com.accor.asa.rate.util.DefaultGrilleValuesProvider;
import com.accor.asa.rate.util.RateFormListsProvider;
import com.accor.asa.rate.util.WeekDaysProvider;
import com.opensymphony.xwork2.conversion.annotations.ConversionType;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

public class BusinessRateFormBean extends RateFormBean {

	private BusinessRateBean bean;
	private DefaultGrilleValuesProvider defaultGrilleValuesProvider;
	private RateFormListsProvider listsProvider;

	public BusinessRateFormBean(BusinessRateBean bean, Contexte contexte, GrilleBean grille) {
		this.bean=bean;
		defaultGrilleValuesProvider= new DefaultGrilleValuesProvider(grille, contexte);
		listsProvider = new RateFormListsProvider(grille, contexte);
        setWeekDays(WeekDaysProvider.getWeekDays(contexte));
    }
	
	
	public String getKey() {
		return bean.getKey();
	}

	public Long getIdGrille() {
		return bean.getIdGrille();
	}

    public void setIdGrille(Long idGrille) {
        bean.setIdGrille(idGrille);
    }

    public Boolean getBlackOutDates() {
		return bean.getBlackOutDates();
	}

	public String getCodeDevise() {
		return bean.getCodeDevise();
	}

	public String getCodeMealPlan() {
		return bean.getCodeMealPlan();
	}

	public String getCodePeriode() {
		return bean.getCodePeriode();
	}

	public String getCodePetitDej() {
		return bean.getCodePetitDej();
	}

	public String getCodeRateLevel() {
		return bean.getCodeRateLevel();
	}

	@TypeConversion(converter="com.accor.asa.rate.util.AsaDateConverter",type=ConversionType.CLASS )
	public Date getDateDebut() {
		return bean.getDateDebut();
	}

	@TypeConversion(converter="com.accor.asa.rate.util.AsaDateConverter",type=ConversionType.CLASS )
	public Date getDateFin() {
		return bean.getDateFin();
	}

	public Boolean getDimWe() {
		return bean.getDimWe();
	}

	public Integer getIdDivSemaine() {
		return bean.getIdDivSemaine();
	}

	public Integer getIdDureeSejour() {
		return bean.getIdDureeSejour();
	}

	public Boolean getJeuWe() {
		return bean.getJeuWe();
	}

	public String getLibelleSalon() {
		return bean.getLibelleSalon();
	}

	public Boolean getLunWe() {
		return bean.getLunWe();
	}

	public Boolean getMarWe() {
		return bean.getMarWe();
	}

	public Boolean getMerWe() {
		return bean.getMerWe();
	}

	public Integer getNbreNuitsMax() {
		return bean.getNbreNuitsMax();
	}

	public Integer getNbreNuitsMin() {
		return bean.getNbreNuitsMin();
	}

	public Boolean getOpenNewContrat() {
		return bean.getOpenNewContrat();
	}

	public Double getPrix1Pax() {
		Double d= bean.getPriceForPax(1);
		return d>0?d:null;
	}

	public Double getPrix2Pax() {
		Double d= bean.getPriceForPax(2);
		return d>0?d:null;
	}

	public Boolean getSamWe() {
		return bean.getSamWe();
	}

	public String getUniteCommission() {
		return bean.getUniteCommission();
	}

	public Double getValueCommission() {
		Double d=bean.getValueCommission();
		return d>0?d:null;
	}

	public Boolean getVenWe() {
		return bean.getVenWe();
	}
	
	
	public Double getPrixPdj() {
		Double d= bean.getPrixPdj();
		return d>0?d:null;
	}
	
	

	public String getCommission() {
		if(bean.getValueCommission() >0)
			return bean.getValueCommission() + " " + bean.getUniteCommission();
		return "";
	}

	public String getCodeProduit() {
		return bean.getCodeProduit();
	}
	
	public String[] getCodesProduit() {
		return bean.getCodesProduit();
	}

	public void setPdjInclus(boolean pdjInclus) {
		String codeMealPlan=pdjInclus ? MealPlanRefBean.CODE_MEALPLAN_BED_AND_BREAKFAST : MealPlanRefBean.CODE_MEALPLAN_ROOM_ONLY;
		bean.setCodeMealPlan(codeMealPlan);
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

	public void setCodeRateLevel(String codeRateLevel) {
		bean.setCodeRateLevel(codeRateLevel);
	}

	@RequiredFieldValidator(fieldName = "codesProduit", type = ValidatorType.SIMPLE, key = "BUS_MSG_SELECTEDROOM", message = "", shortCircuit = true)
	public void setCodesProduit(String[] codesProduit) {
		bean.setCodesProduit(codesProduit);
	}

	public void setCodePeriode(String codePeriode) {
		bean.setCodePeriode(codePeriode);
	}

	public void setIdDivSemaine(Integer idDivSemaine) {
		bean.setIdDivSemaine(idDivSemaine);
	}

	@TypeConversion(converter = "com.accor.asa.rate.util.AsaDateConverter", type = ConversionType.CLASS)
	@RequiredFieldValidator(fieldName = "dateDebut", type = ValidatorType.SIMPLE, key = "BUS_MSG_ENTRERBEGINDATE", message = "", shortCircuit = true)
	public void setDateDebut(Date dateDebut) {
		bean.setDateDebut(dateDebut);
	}

	@TypeConversion(converter = "com.accor.asa.rate.util.AsaDateConverter", type = ConversionType.CLASS)
	@RequiredFieldValidator(fieldName = "dateFin", type = ValidatorType.SIMPLE, key = "BUS_MSG_ENTRERENDDATE", message = "", shortCircuit = true)
	public void setDateFin(Date dateFin) {
		bean.setDateFin(dateFin);
	}

	public void setIdDureeSejour(Integer idDureeSejour) {
		bean.setIdDureeSejour(idDureeSejour);
	}

	public void setLibelleSalon(String libelleSalon) {
		bean.setLibelleSalon(libelleSalon);
	}

	public void setCodePetitDej(String codePetitDej) {
		bean.setCodePetitDej(codePetitDej);
	}

	public void setPrixPdj(Double prixPdj) {
		bean.setPrixPdj(prixPdj);
	}

	public void setCodeDevise(String codeDevise) {
		bean.setCodeDevise(codeDevise);
	}

	public void setValueCommission(Double valueCommission) {
		if (valueCommission == null)
			valueCommission = (double) 0;
		bean.setValueCommission(valueCommission);
	}

	public void setUniteCommission(String uniteCommission) {
		bean.setUniteCommission(uniteCommission);
	}

	public void setLunWe(Boolean lunWe) {
		bean.setLunWe(lunWe);
	}

	public void setMarWe(Boolean marWe) {
		bean.setMarWe(marWe);
	}

	public void setMerWe(Boolean merWe) {
		bean.setMerWe(merWe);
	}

	public void setJeuWe(Boolean jeuWe) {
		bean.setJeuWe(jeuWe);
	}

	public void setVenWe(Boolean venWe) {
		bean.setVenWe(venWe);
	}

	public void setSamWe(Boolean samWe) {
		bean.setSamWe(samWe);
	}

	public void setDimWe(Boolean dimWe) {
		bean.setDimWe(dimWe);
	}

	public void setOpenNewContrat(Boolean openNewContrat) {
		bean.setOpenNewContrat(openNewContrat);
	}

	public void setBlackOutDates(Boolean blackOutDates) {
		bean.setBlackOutDates(blackOutDates);
	}

	public void setNbreNuitsMin(Integer nbreNuitsMin) {
		bean.setNbreNuitsMin(nbreNuitsMin);
	}

	public void setNbreNuitsMax(Integer nbreNuitsMax) {
		bean.setNbreNuitsMax(nbreNuitsMax);
	}

	
	
	public void setPrix2Pax(Double prix2Pax) {
		bean.setPriceForPax(2, prix2Pax);
	}
	
	@RequiredFieldValidator(fieldName = "prix1Pax", type = ValidatorType.SIMPLE, key = "BUS_MSG_ENTRER1PAXPRICE", message = "", shortCircuit = true)
	public void setPrix1Pax(Double prix1Pax) {
		bean.setPriceForPax(1, prix1Pax);
	}

	public boolean getPrix1PaxEq2Pax()
	{
		Double p1=getPrix1Pax();
		Double p2=getPrix2Pax();
		return ((p1==null && p2==null) ||(p1!= null && p2 != null && p1.equals(p2)));
	}
	
	public void setKey(String key)
	{
		bean.setKey(key);
	}


	public BusinessRateBean getBean() {
		return bean;
	}
	
	public boolean isPdjInclus() {
		String codeMealPlan = getCodeMealPlan();
		return codeMealPlan!= null && codeMealPlan.equals(MealPlanRefBean.CODE_MEALPLAN_BED_AND_BREAKFAST);
	}

}
