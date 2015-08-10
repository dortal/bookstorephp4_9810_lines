package com.accor.asa.rate.decorator;

import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.rate.model.AdagioBusinessRate;
import com.accor.asa.rate.model.BusinessRateBean;

public class AdagioBusinessRateDecorator  extends BusinessRateDecorator implements AdagioBusinessRate{

	
	public AdagioBusinessRateDecorator(BusinessRateBean bean, Contexte contexte) {
		super(bean, contexte);
	}

	
	public Double getPrix3Pax() {
		Double d= getBean().getPriceForPax(3);
		return d>0?d:null;
	}
	
	public String getPrix3() {
		Double prix = getPrix3Pax();
		if (prix == null)
			return "";
		return prix + " " +getBean().getCodeDevise();
	}

	public Double getPrix4Pax() {
		Double d= getBean().getPriceForPax(4);
		return d>0?d:null;
	}

	public String getPrix4() {
		Double prix = getPrix4Pax();
		if (prix == null)
			return "";
		return prix + " " +getBean().getCodeDevise();
	}
	
	public Double getPrix5Pax() {
		Double d= getBean().getPriceForPax(5);
		return d>0?d:null;
	}

	public String getPrix5() {
		Double prix = getPrix5Pax();
		if (prix == null)
			return "";
		return prix + " " +getBean().getCodeDevise();
	}
	
	public Double getPrix6Pax() {
		Double d= getBean().getPriceForPax(6);
		return d>0?d:null;
	}

	public String getPrix6() {
		Double prix = getPrix6Pax();
		if (prix == null)
			return "";
		return prix + " " +getBean().getCodeDevise();
	}

	public Double getPrix7Pax() {
		Double d= getBean().getPriceForPax(7);
		return d>0?d:null;
	}
	
	public Double getPrix8Pax() {
		Double d= getBean().getPriceForPax(8);
		return d>0?d:null;
	}
	
	public Double getPrix9Pax() {
		Double d= getBean().getPriceForPax(9);
		return d>0?d:null;
	}
	
	
	
}
