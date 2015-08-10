package com.accor.asa.rate.form;

import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.rate.model.BusinessRateBean;
import com.accor.asa.rate.model.GrilleBean;

public class AdagioBusinessRateFormBean extends BusinessRateFormBean {

	public AdagioBusinessRateFormBean(BusinessRateBean bean, Contexte contexte, GrilleBean grille) {
		super(bean, contexte, grille);
	}

	public Double getPrix3Pax() {
		Double d = getBean().getPriceForPax(3);
		return d > 0 ? d : null;
	}

	

	public Double getPrix4Pax() {
		Double d = getBean().getPriceForPax(4);
		return d > 0 ? d : null;
	}

	

	public Double getPrix5Pax() {
		Double d = getBean().getPriceForPax(5);
		return d > 0 ? d : null;
	}

	

	public Double getPrix6Pax() {
		Double d = getBean().getPriceForPax(6);
		return d > 0 ? d : null;
	}

	

	public Double getPrix7Pax() {
		Double d = getBean().getPriceForPax(7);
		return d > 0 ? d : null;
	}

	public Double getPrix8Pax() {
		Double d = getBean().getPriceForPax(8);
		return d > 0 ? d : null;
	}

	public Double getPrix9Pax() {
		Double d = getBean().getPriceForPax(9);
		return d > 0 ? d : null;
	}

	public void setPrix3Pax(Double price) {
		getBean().setPriceForPax(3, price);
	}

	public void setPrix4Pax(Double price) {
		getBean().setPriceForPax(4, price);
	}

	public void setPrix5Pax(Double price) {
		getBean().setPriceForPax(5, price);
	}

	public void setPrix6Pax(Double price) {
		getBean().setPriceForPax(6, price);
	}

	public void setPrix7Pax(Double price) {
		getBean().setPriceForPax(7, price);
	}

	public void setPrix8Pax(Double price) {
		getBean().setPriceForPax(8, price);
	}

	public void setPrix9Pax(Double price) {
		getBean().setPriceForPax(9, price);
	}

}