package com.accor.asa.rate.model;

import java.io.Serializable;


public class ChildRateServiceData implements Serializable {

    private Integer minAge;
    private Integer maxAge;
    private Integer accomodationIdTypePrix;
    private Double  accomodationMontant;
    private Integer breakfastIdTypePrix;
    private Double  breakfastMontant;
    private Integer mealIdTypePrix;
    private Double  mealMontant;

    private boolean ageActiv;
    private String  accomodationLabelTypePrix;
    private String  breakfastLabelTypePrix;
    private String  mealLabelTypePrix;

    public ChildRateServiceData(Integer minAge, Integer maxAge, Integer accomodationIdTypePrix, Double accomodationMontant, Integer breakfastIdTypePrix, Double breakfastMontant, Integer mealIdTypePrix, Double mealMontant) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.accomodationIdTypePrix = accomodationIdTypePrix;
        this.accomodationMontant = accomodationMontant;
        this.breakfastIdTypePrix = breakfastIdTypePrix;
        this.breakfastMontant = breakfastMontant;
        this.mealIdTypePrix = mealIdTypePrix;
        this.mealMontant = mealMontant;
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

    public boolean isAgeActiv() {
        return ageActiv;
    }

    public void setAgeActiv(boolean ageActiv) {
        this.ageActiv = ageActiv;
    }

    public String getAccomodationLabelTypePrix() {
        return accomodationLabelTypePrix;
    }

    public void setAccomodationLabelTypePrix(String accomodationLabelTypePrix) {
        this.accomodationLabelTypePrix = accomodationLabelTypePrix;
    }

    public String getBreakfastLabelTypePrix() {
        return breakfastLabelTypePrix;
    }

    public void setBreakfastLabelTypePrix(String breakfastLabelTypePrix) {
        this.breakfastLabelTypePrix = breakfastLabelTypePrix;
    }

    public String getMealLabelTypePrix() {
        return mealLabelTypePrix;
    }

    public void setMealLabelTypePrix(String mealLabelTypePrix) {
        this.mealLabelTypePrix = mealLabelTypePrix;
    }

    public static ChildRateServiceData createFromRow(ChildRateRowBean row) {
		return new ChildRateServiceData(row.getMinAge(),row.getMaxAge(),
                row.getAccomodationIdTypePrix(),row.getAccomodationMontant(),
                row.getBreakfastIdTypePrix(), row.getBreakfastMontant(),
                row.getMealIdTypePrix(), row.getMealMontant());
	}

    //=========================================================================================
    //=============                       OTHER                  ==============================
    //=========================================================================================
    
    /**
     * To String
     *
     * @return
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(minAge).
                append("_").append(maxAge).
                append("_").append(accomodationIdTypePrix).
                append("_").append(accomodationMontant).
                append("_").append(breakfastIdTypePrix).
                append("_").append(breakfastMontant).
                append("_").append(mealIdTypePrix).
                append("_").append(mealMontant);
        return sb.toString();
    }

}
