package com.accor.asa.rate.model;

public interface LeisureRate extends Rate {

	public Double getBagageInAndOut();

	public Double getBagageInOrOut();

	public String getCodeDevise();

	public String getCodeProduit();

	public String getCodeMealPlan();

	public String getCodePeriode();

	public String getCodePetitDej();

	//public String[] getCodesProduit();

	public String getCodeRateLevel();


	public Boolean getDimWe();


	public Integer getIdDivSemaine();

	public Integer getIdDureeSejour();

	public Boolean getJeuWe();

	public String getLibelleSalon();

	public Boolean getLunWe();

	public Boolean getMarWe();


	public Boolean getMerWe();

	public Double getPrix();

	public Double getPrixPdj();

	public Double getPrixSupQUAD();

	public Double getPrixSupSGL();

	public Double getPrixSupTRI();

	public Boolean getSamWe();

	public Double getSupplDemPens();

	public Double getSupplPensCompl();

	public String getUniteCommission();

	public Double getValueCommission();

	public Boolean getVenWe();

}
