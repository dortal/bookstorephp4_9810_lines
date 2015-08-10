package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("serial")
public class ParamPeriodeValiditeMappedRefBean extends RefBean{

	private int idGroupeTarif;
    private int idPeriodeValidite;
    private HashMap<String, ParamPeriodeValiditeData> paramMap = new HashMap<String, ParamPeriodeValiditeData>();
	
	private List<String> codesAsaCategory = new ArrayList<String>();
	
	public int getIdGroupeTarif() {
		return idGroupeTarif;
	}
	public void setIdGroupeTarif(int idGroupeTarif) {
		this.idGroupeTarif = idGroupeTarif;
	}

    public int getIdPeriodeValidite() {
        return idPeriodeValidite;
    }

    public void setIdPeriodeValidite(int idPeriodeValidite) {
        this.idPeriodeValidite = idPeriodeValidite;
    }

    public void addParamData(String codeAsaCategory, ParamPeriodeValiditeData data)
	{
		codesAsaCategory.add(codeAsaCategory);
		paramMap.put(codeAsaCategory,data);
	}
	
	public ParamPeriodeValiditeData getParamData(String codeAsaCategory)
	{
		return paramMap.get(codeAsaCategory);
	}
	
	public List<String> getCodesAsaCategory() {
		return codesAsaCategory;
	}
	@Override
	public String getCode() {
		return String.valueOf(idGroupeTarif);
	}
	
	
}
