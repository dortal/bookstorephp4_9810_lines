package com.accor.asa.rate.model;

import java.util.Date;

import com.accor.asa.commun.metier.AsaDate;

public class ChildRateRowBean extends RateBean{

	private String codeRateLevel;
	private String codeProduit;
    private Date dateDebut;
    private Date dateFin;
    private Integer minAge;
	private Integer maxAge;
    private Boolean chambreSepare;
    private Integer maxChild;
	private Integer maxAdult;
	private String codeDevise;
    private Integer accomodationIdTypePrix;
    private Double  accomodationMontant;
    private Integer breakfastIdTypePrix;
    private Double  breakfastMontant;
    private Integer mealIdTypePrix;
    private Double  mealMontant;

    public String getCodeRateLevel() {
        return codeRateLevel;
    }

    public void setCodeRateLevel(String codeRateLevel) {
        this.codeRateLevel = codeRateLevel;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
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

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Boolean getChambreSepare() {
        return chambreSepare;
    }

    public void setChambreSepare(Boolean chambreSepare) {
        this.chambreSepare = chambreSepare;
    }

    public Integer getMaxChild() {
        return maxChild;
    }

    public void setMaxChild(Integer maxChild) {
        this.maxChild = maxChild;
    }

    public Integer getMaxAdult() {
        return maxAdult;
    }

    public void setMaxAdult(Integer maxAdult) {
        this.maxAdult = maxAdult;
    }

    public String getCodeDevise() {
        return codeDevise;
    }

    public void setCodeDevise(String codeDevise) {
        this.codeDevise = codeDevise;
    }

    public Integer getAccomodationIdTypePrix() {
        return accomodationIdTypePrix;
    }

    public void setAccomodationIdTypePrix(Integer accomodationIdTypePrix) {
        this.accomodationIdTypePrix = accomodationIdTypePrix;
    }

    public Double getAccomodationMontant() {
        return accomodationMontant;
    }

    public void setAccomodationMontant(Double accomodationMontant) {
        this.accomodationMontant = accomodationMontant;
    }

    public Integer getBreakfastIdTypePrix() {
        return breakfastIdTypePrix;
    }

    public void setBreakfastIdTypePrix(Integer breakfastIdTypePrix) {
        this.breakfastIdTypePrix = breakfastIdTypePrix;
    }

    public Double getBreakfastMontant() {
        return breakfastMontant;
    }

    public void setBreakfastMontant(Double breakfastMontant) {
        this.breakfastMontant = breakfastMontant;
    }

    public Integer getMealIdTypePrix() {
        return mealIdTypePrix;
    }

    public void setMealIdTypePrix(Integer mealIdTypePrix) {
        this.mealIdTypePrix = mealIdTypePrix;
    }

    public Double getMealMontant() {
        return mealMontant;
    }

    public void setMealMontant(Double mealMontant) {
        this.mealMontant = mealMontant;
    }

    @Override
	public String generateBeanKey() {
		StringBuffer sb = new StringBuffer(String.valueOf(idGrille));
				sb.append("_").append(getCodeRateLevel());
        		sb.append("_").append(getCodeProduit()).
                append("_").append(new AsaDate(getDateDebut())).
                append("_").append(getChambreSepare());
        return sb.toString();
	}
	
	
	
}
