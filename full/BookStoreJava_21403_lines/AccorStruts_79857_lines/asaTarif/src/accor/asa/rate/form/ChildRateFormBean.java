package com.accor.asa.rate.form;

import java.util.Date;

import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.rate.model.ChildRateBean;
import com.accor.asa.rate.model.ChildRateServiceData;
import com.accor.asa.rate.model.GrilleBean;
import com.accor.asa.rate.util.RateFormListsProvider;
import com.opensymphony.xwork2.conversion.annotations.ConversionType;
import com.opensymphony.xwork2.conversion.annotations.TypeConversion;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;

public class ChildRateFormBean extends RateFormBean {

	private ChildRateBean bean;
	private RateFormListsProvider listsProvider;

    private boolean ageActiv0;
    private Integer minAge0;
    private Integer maxAge0;
    private Integer accomodationIdTypePrix0;
    private Double  accomodationMontant0;
    private Integer breakfastIdTypePrix0;
    private Double  breakfastMontant0;
    private Integer mealIdTypePrix0;
    private Double  mealMontant0;

    private boolean ageActiv1;
    private Integer minAge1;
    private Integer maxAge1;
    private Integer accomodationIdTypePrix1;
    private Double  accomodationMontant1;
    private Integer breakfastIdTypePrix1;
    private Double  breakfastMontant1;
    private Integer mealIdTypePrix1;
    private Double  mealMontant1;

    private boolean ageActiv2;
    private Integer minAge2;
    private Integer maxAge2;
    private Integer accomodationIdTypePrix2;
    private Double  accomodationMontant2;
    private Integer breakfastIdTypePrix2;
    private Double  breakfastMontant2;
    private Integer mealIdTypePrix2;
    private Double  mealMontant2;


    public ChildRateFormBean(ChildRateBean bean, Contexte contexte, GrilleBean grille) {
        this.bean = bean;
        listsProvider = new RateFormListsProvider(grille, contexte);
        ChildRateServiceData service;

        service = bean.getService(0);
        setAgeActiv0(service.isAgeActiv());
        setMinAge0(service.getMinAge());
        setMaxAge0(service.getMaxAge());
        setAccomodationIdTypePrix0(service.getAccomodationIdTypePrix());
        setAccomodationMontant0(service.getAccomodationMontant());
        setBreakfastIdTypePrix0(service.getBreakfastIdTypePrix());
        setBreakfastMontant0(service.getBreakfastMontant());
        setMealIdTypePrix0(service.getMealIdTypePrix());
        setMealMontant0(service.getMealMontant());

        service = bean.getService(1);
        setAgeActiv1(service.isAgeActiv());
        setMinAge1(service.getMinAge());
        setMaxAge1(service.getMaxAge());
        setAccomodationIdTypePrix1(service.getAccomodationIdTypePrix());
        setAccomodationMontant1(service.getAccomodationMontant());
        setBreakfastIdTypePrix1(service.getBreakfastIdTypePrix());
        setBreakfastMontant1(service.getBreakfastMontant());
        setMealIdTypePrix1(service.getMealIdTypePrix());
        setMealMontant1(service.getMealMontant());

        service = bean.getService(2);
        setAgeActiv2(service.isAgeActiv());
        setMinAge2(service.getMinAge());
        setMaxAge2(service.getMaxAge());
        setAccomodationIdTypePrix2(service.getAccomodationIdTypePrix());
        setAccomodationMontant2(service.getAccomodationMontant());
        setBreakfastIdTypePrix2(service.getBreakfastIdTypePrix());
        setBreakfastMontant2(service.getBreakfastMontant());
        setMealIdTypePrix2(service.getMealIdTypePrix());
        setMealMontant2(service.getMealMontant());
    }

    public void setIdGrille(Long idGrille) {
        bean.setIdGrille(idGrille);
    }

    public Long getIdGrille() {
        return bean.getIdGrille();
    }

    public String getKey() {
        return bean.getKey();
    }

    public ChildRateBean getBean() {
        bean.clearServices();
        if (isAgeActiv0())
            bean.addService(new ChildRateServiceData(
                    getMinAge0(),getMaxAge0(),
                    getAccomodationIdTypePrix0(),getAccomodationMontant0(),
                    getBreakfastIdTypePrix0(),getBreakfastMontant0(),
                    getMealIdTypePrix0(),getMealMontant0()));
        if (isAgeActiv1())
            bean.addService(new ChildRateServiceData(
                    getMinAge1(),getMaxAge1(),
                    getAccomodationIdTypePrix1(),getAccomodationMontant1(),
                    getBreakfastIdTypePrix1(),getBreakfastMontant1(),
                    getMealIdTypePrix1(),getMealMontant1()));
        if (isAgeActiv2())
            bean.addService(new ChildRateServiceData(
                    getMinAge2(),getMaxAge2(),
                    getAccomodationIdTypePrix2(),getAccomodationMontant2(),
                    getBreakfastIdTypePrix2(),getBreakfastMontant2(),
                    getMealIdTypePrix2(),getMealMontant2()));
        return bean;
	}

	public RateFormListsProvider getListsProvider() {
		return listsProvider;
	}

    public void setKey(String key) {
        bean.setKey(key);
    }

    public String getCodeRateLevel() {
		return bean.getCodeRateLevel();
	}

	@RequiredStringValidator(fieldName = "codeRateLevel", type = ValidatorType.SIMPLE, message = "", key = "ENF_MSG_ENTRER_RATELEVEL", shortCircuit = true)
	public void setCodeRateLevel(String codeRateLevel) {
		bean.setCodeRateLevel(codeRateLevel);
	}

	public String[] getCodesProduit() {
		return bean.getCodesProduit();
	}

	@RequiredFieldValidator(fieldName = "codesProduit", type = ValidatorType.SIMPLE, message = "", key = "ENF_MSG_ENTRER_CHAMBRE", shortCircuit = true)
	public void setCodesProduit(String[] codesProduit) {
		bean.setCodesProduit(codesProduit);
	}


    @TypeConversion(converter = "com.accor.asa.rate.util.AsaDateConverter", type = ConversionType.CLASS)
    public Date getDateDebut() {
        return bean.getDateDebut();
    }

    @TypeConversion(converter = "com.accor.asa.rate.util.AsaDateConverter", type = ConversionType.CLASS)
    @RequiredFieldValidator(fieldName = "dateDebut", type = ValidatorType.SIMPLE, message = "", key = "ENF_MSG_ENTRER_DATEDEBUT", shortCircuit = true)
    public void setDateDebut(Date dateDebut) {
        bean.setDateDebut(dateDebut);
    }

    @TypeConversion(converter = "com.accor.asa.rate.util.AsaDateConverter", type = ConversionType.CLASS)
    public Date getDateFin() {
        return bean.getDateFin();
    }

    @TypeConversion(converter = "com.accor.asa.rate.util.AsaDateConverter", type = ConversionType.CLASS)
    @RequiredFieldValidator(fieldName = "dateFin", type = ValidatorType.SIMPLE, message = "", key = "ENF_MSG_ENTRER_DATEFIN", shortCircuit = true)
    public void setDateFin(Date dateFin) {
        bean.setDateFin(dateFin);
    }
    
    public Integer getMaxChild() {
		return bean.getMaxChild() > 0 ? bean.getMaxChild() : null;
	}

	@RequiredFieldValidator(fieldName = "maxChild", type = ValidatorType.SIMPLE, message = "You must enter a value for maxChild.", shortCircuit = true)
	public void setMaxChild(Integer maxChild) {
		bean.setMaxChild(maxChild);
	}

	public Integer getMaxAdult() {
		return bean.getMaxAdult() > 0 ? bean.getMaxAdult() : null;
	}

	@RequiredFieldValidator(fieldName = "maxAdult", type = ValidatorType.SIMPLE, message = "You must enter a value for maxAdult.", shortCircuit = true)
	public void setMaxAdult(Integer maxAdult) {
		bean.setMaxAdult(maxAdult);
	}

	public String getCodeDevise() {
		return bean.getCodeDevise();
	}

	public void setCodeDevise(String codeDevise) {
		bean.setCodeDevise(codeDevise);
	}

    public Boolean getChambreSepare() {
        return bean.isChambreSepare();
    }

    public void setChambreSepare(Boolean chambreSepare) {
        bean.setChambreSepare(chambreSepare);
    }


    public boolean isAgeActiv0() {
        return ageActiv0;
    }

    public void setAgeActiv0(boolean ageActiv0) {
        this.ageActiv0 = ageActiv0;
    }

    public Integer getMinAge0() {
        return minAge0;
    }

    @RequiredFieldValidator(fieldName = "minAge0", type = ValidatorType.SIMPLE, message = "", key = "ENF_MSG_ENTRER_MINAGE0REQUIRED", shortCircuit = true)
    public void setMinAge0(Integer minAge0) {
        this.minAge0 = minAge0;
    }

    public Integer getMaxAge0() {
        return maxAge0;
    }

    @RequiredFieldValidator(fieldName = "maxAge0", type = ValidatorType.SIMPLE, message = "", key = "ENF_MSG_ENTRER_MAXAGE0REQUIRED", shortCircuit = true)
    public void setMaxAge0(Integer maxAge0) {
        this.maxAge0 = maxAge0;
    }

    public Integer getAccomodationIdTypePrix0() {
        return accomodationIdTypePrix0;
    }

    public void setAccomodationIdTypePrix0(Integer accomodationIdTypePrix0) {
        this.accomodationIdTypePrix0 = accomodationIdTypePrix0;
    }

    public Double getAccomodationMontant0() {
        return accomodationMontant0;
    }

    public void setAccomodationMontant0(Double accomodationMontant0) {
        this.accomodationMontant0 = accomodationMontant0;
    }

    public Integer getBreakfastIdTypePrix0() {
        return breakfastIdTypePrix0;
    }

    public void setBreakfastIdTypePrix0(Integer breakfastIdTypePrix0) {
        this.breakfastIdTypePrix0 = breakfastIdTypePrix0;
    }

    public Double getBreakfastMontant0() {
        return breakfastMontant0;
    }

    public void setBreakfastMontant0(Double breakfastMontant0) {
        this.breakfastMontant0 = breakfastMontant0;
    }

    public Integer getMealIdTypePrix0() {
        return mealIdTypePrix0;
    }

    public void setMealIdTypePrix0(Integer mealIdTypePrix0) {
        this.mealIdTypePrix0 = mealIdTypePrix0;
    }

    public Double getMealMontant0() {
        return mealMontant0;
    }

    public void setMealMontant0(Double mealMontant0) {
        this.mealMontant0 = mealMontant0;
    }

    public boolean isAgeActiv1() {
        return ageActiv1;
    }

    public void setAgeActiv1(boolean ageActiv1) {
        this.ageActiv1 = ageActiv1;
    }

    public Integer getMinAge1() {
        return minAge1;
    }

    public void setMinAge1(Integer minAge1) {
        this.minAge1 = minAge1;
    }

    public Integer getMaxAge1() {
        return maxAge1;
    }

    public void setMaxAge1(Integer maxAge1) {
        this.maxAge1 = maxAge1;
    }

    public Integer getAccomodationIdTypePrix1() {
        return accomodationIdTypePrix1;
    }

    public void setAccomodationIdTypePrix1(Integer accomodationIdTypePrix1) {
        this.accomodationIdTypePrix1 = accomodationIdTypePrix1;
    }

    public Double getAccomodationMontant1() {
        return accomodationMontant1;
    }

    public void setAccomodationMontant1(Double accomodationMontant1) {
        this.accomodationMontant1 = accomodationMontant1;
    }

    public Integer getBreakfastIdTypePrix1() {
        return breakfastIdTypePrix1;
    }

    public void setBreakfastIdTypePrix1(Integer breakfastIdTypePrix1) {
        this.breakfastIdTypePrix1 = breakfastIdTypePrix1;
    }

    public Double getBreakfastMontant1() {
        return breakfastMontant1;
    }

    public void setBreakfastMontant1(Double breakfastMontant1) {
        this.breakfastMontant1 = breakfastMontant1;
    }

    public Integer getMealIdTypePrix1() {
        return mealIdTypePrix1;
    }

    public void setMealIdTypePrix1(Integer mealIdTypePrix1) {
        this.mealIdTypePrix1 = mealIdTypePrix1;
    }

    public Double getMealMontant1() {
        return mealMontant1;
    }

    public void setMealMontant1(Double mealMontant1) {
        this.mealMontant1 = mealMontant1;
    }

    public boolean isAgeActiv2() {
        return ageActiv2;
    }

    public void setAgeActiv2(boolean ageActiv2) {
        this.ageActiv2 = ageActiv2;
    }

    public Integer getMinAge2() {
        return minAge2;
    }

    public void setMinAge2(Integer minAge2) {
        this.minAge2 = minAge2;
    }

    public Integer getMaxAge2() {
        return maxAge2;
    }

    public void setMaxAge2(Integer maxAge2) {
        this.maxAge2 = maxAge2;
    }

    public Integer getAccomodationIdTypePrix2() {
        return accomodationIdTypePrix2;
    }

    public void setAccomodationIdTypePrix2(Integer accomodationIdTypePrix2) {
        this.accomodationIdTypePrix2 = accomodationIdTypePrix2;
    }

    public Double getAccomodationMontant2() {
        return accomodationMontant2;
    }

    public void setAccomodationMontant2(Double accomodationMontant2) {
        this.accomodationMontant2 = accomodationMontant2;
    }

    public Integer getBreakfastIdTypePrix2() {
        return breakfastIdTypePrix2;
    }

    public void setBreakfastIdTypePrix2(Integer breakfastIdTypePrix2) {
        this.breakfastIdTypePrix2 = breakfastIdTypePrix2;
    }

    public Double getBreakfastMontant2() {
        return breakfastMontant2;
    }

    public void setBreakfastMontant2(Double breakfastMontant2) {
        this.breakfastMontant2 = breakfastMontant2;
    }

    public Integer getMealIdTypePrix2() {
        return mealIdTypePrix2;
    }

    public void setMealIdTypePrix2(Integer mealIdTypePrix2) {
        this.mealIdTypePrix2 = mealIdTypePrix2;
    }

    public Double getMealMontant2() {
        return mealMontant2;
    }

    public void setMealMontant2(Double mealMontant2) {
        this.mealMontant2 = mealMontant2;
    }
}
