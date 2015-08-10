package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ParamSupplementMappedRefBean extends RefBean{

	private String codeAsaCategory;
	private String codeRateLevel;
	
	public List<SupplementRefBean> supplements;
	
	private ParamGrilleRefBean paramGrille;

	public String getCodeAsaCategory() {
		return codeAsaCategory;
	}

	public void setCodeAsaCategory(String codeAsaCategory) {
		this.codeAsaCategory = codeAsaCategory;
	}

	public String getCodeRateLevel() {
		return codeRateLevel;
	}

	public void setCodeRateLevel(String codeRateLevel) {
		this.codeRateLevel = codeRateLevel;
	}

	

	public ParamSupplementMappedRefBean(String codeAsaCategory, String codeRateLevel) {
		super();
		this.codeAsaCategory = codeAsaCategory;
		this.codeRateLevel = codeRateLevel;
		supplements = new ArrayList<SupplementRefBean>();
	}

	
	public ParamSupplementMappedRefBean(ParamGrilleRefBean paramGrille) {
		setParamGrille(paramGrille);
		supplements = new ArrayList<SupplementRefBean>();
	}

	public ParamGrilleRefBean getParamGrille() {
		return paramGrille;
	}

	public void setParamGrille(ParamGrilleRefBean paramGrille) {
		this.paramGrille = paramGrille;
		if(paramGrille==null)
		{
			setCodeAsaCategory(null);
			setCodeRateLevel(null);
		}
		else
		{
			setCodeAsaCategory(paramGrille.getCodeAsaCategory());
			setCodeRateLevel(paramGrille.getCodeRateLevel());
		}
	}

	
	public void addSupplement(SupplementRefBean supplement)
	{
		supplements.add(supplement);
	}

	public List<SupplementRefBean> getSupplements() {
		return supplements;
	}

	public String getCode() {
		return getCodeAsaCategory()+"_"+getCodeRateLevel();
	}

}
