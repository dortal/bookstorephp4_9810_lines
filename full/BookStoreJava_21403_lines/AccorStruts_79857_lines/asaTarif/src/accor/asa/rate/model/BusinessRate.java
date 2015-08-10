package com.accor.asa.rate.model;


public interface BusinessRate extends Rate {

	public String getCodeRateLevel();
	
	public String getCodePeriode();

	public Integer getIdDivSemaine();

	public Integer getIdDureeSejour();

	public String getLibelleSalon();

	public String getCodePetitDej();

	public Double getPrixPdj();

	public String getCodeDevise();

	public Double getValueCommission();

	public String getUniteCommission();

	

	public Boolean getOpenNewContrat();

	public Boolean getBlackOutDates();

	public Integer getNbreNuitsMin();

	public Integer getNbreNuitsMax();

}
