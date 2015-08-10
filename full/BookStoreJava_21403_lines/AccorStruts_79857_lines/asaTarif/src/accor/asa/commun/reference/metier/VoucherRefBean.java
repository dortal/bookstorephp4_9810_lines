package com.accor.asa.commun.reference.metier;


@SuppressWarnings("serial")
public class VoucherRefBean extends RefBean {

	private boolean supplementInclus = false;

	public VoucherRefBean () {
	}

	public boolean getSupplementInclus () {
		return supplementInclus;
	}

	public void setSupplementInclus (boolean supplementInclus) {
		this.supplementInclus = supplementInclus;
	}

}