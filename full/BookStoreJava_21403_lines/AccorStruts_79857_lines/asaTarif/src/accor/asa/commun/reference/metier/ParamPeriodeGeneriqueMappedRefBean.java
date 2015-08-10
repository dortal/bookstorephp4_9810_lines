package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("serial")
public class ParamPeriodeGeneriqueMappedRefBean extends RefBean{

	private String codePeriode;
	
	private HashMap<String, Integer> paramMap= new HashMap<String, Integer>();

	private List<String> codesAsaCategory=new ArrayList<String>();
	public String getCodePeriode() {
		return codePeriode;
	}

	public void setCodePeriode(String codePeriode) {
		this.codePeriode = codePeriode;
	}
	
	public List<String> getCodesAsaCategory()
	{
		
		return codesAsaCategory;
	}
	
	public Integer getNbMaxPeriodes(String codeAsaCategory)
	{
		return paramMap.get(codeAsaCategory);
	}
	
	public void addParam(String codeAsaCategory, int nbMaxPeriodes)
	{
		codesAsaCategory.add(codeAsaCategory);
		paramMap.put(codeAsaCategory, nbMaxPeriodes);
	}

	@Override
	public String getCode() {
		return getCodePeriode();
	}
	
	
}
