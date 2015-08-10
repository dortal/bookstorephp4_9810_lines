package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("serial")
public class ParamGrilleMappedRefBean extends RefBean {

	public static String buildKey(int idPeriodeValidite, String codeAsaCategory) {
		return idPeriodeValidite + "_" + codeAsaCategory;
	}

	private int idPeriodeValidite;
	private String codeAsaCategory;

	private List<String> codeRateLevels = new ArrayList<String>();
	private HashMap<String, ParamGrilleData> paramMap = new HashMap<String, ParamGrilleData>();

	public int getIdPeriodeValidite() {
		return idPeriodeValidite;
	}

	public void setIdPeriodeValidite(int idPeriodeValidite) {
		this.idPeriodeValidite = idPeriodeValidite;
	}

	public String getCodeAsaCategory() {
		return codeAsaCategory;
	}

	public void setCodeAsaCategory(String codeAsaCategory) {
		this.codeAsaCategory = codeAsaCategory;
	}

	@SuppressWarnings("unchecked")
	public void addGrilleParam(String codeRateLevel, ParamGrilleData param) {
		paramMap.put(codeRateLevel, param);
        codeRateLevels.add(codeRateLevel);
    }

	public List<String> getCodesRateLevels() {
		return codeRateLevels;
	}

	@Override
	public String getCode() {
		return buildKey(idPeriodeValidite, codeAsaCategory);
	}

	public ParamGrilleData getParamGrilleData(String codeRateLevel)
	{
		return paramMap.get(codeRateLevel);
	}
}
