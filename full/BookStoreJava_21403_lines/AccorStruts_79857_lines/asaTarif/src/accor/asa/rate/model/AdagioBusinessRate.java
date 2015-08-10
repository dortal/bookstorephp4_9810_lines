package com.accor.asa.rate.model;

public interface AdagioBusinessRate extends BusinessRate{

	public Double getPrix3Pax();
	public Double getPrix4Pax();
	public Double getPrix5Pax();
	public Double getPrix6Pax();
	
	public Integer getIdDureeSejour();
}
