package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.categorie.AsaCategory;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class ParamRateLevelRefBean extends RefBean {

	private String codeAsaCategory;
	private int idFamilleTarif;
	private int idPeriodeValidite;
	private String codeRateLevel;

	private AsaCategory asaCategory;
	private FamilleTarifRefBean familleTarif;
	private PeriodeValiditeRefBean periodeValidite;

	public int getIdFamilleTarif() {
		return idFamilleTarif;
	}

	public void setIdFamilleTarif(int idFamilleTarif) {
		this.idFamilleTarif = idFamilleTarif;
	}

	public int getIdPeriodeValidite() {
		return idPeriodeValidite;
	}

	public void setIdPeriodeValidite(int idPeriodeValidite) {
		this.idPeriodeValidite = idPeriodeValidite;
	}

	public void setAsaCategory(AsaCategory asaCategory) {
		this.asaCategory = asaCategory;
		if(asaCategory==null)
			setCodeAsaCategory(null);
		else
			setCodeAsaCategory(asaCategory.getCode());
	}

	public FamilleTarifRefBean getFamilleTarif() {
		return familleTarif;
	}

	public void setFamilleTarif(FamilleTarifRefBean familleTarif) {
		this.familleTarif = familleTarif;
		if(familleTarif==null)
			setIdFamilleTarif(-1);
		else
			setIdFamilleTarif(Integer.parseInt(familleTarif.getId()));
	}

	public PeriodeValiditeRefBean getPeriodeValidite() {
		return periodeValidite;
	}

	public void setPeriodeValidite(PeriodeValiditeRefBean periodeValidite) {
		this.periodeValidite = periodeValidite;
		if(periodeValidite==null)
			setIdPeriodeValidite(-1);
		else
			setIdPeriodeValidite(Integer.parseInt(periodeValidite.getCode()));
	}

	public String getCodeAsaCategory() {
		return codeAsaCategory;
	}

	public void setCodeAsaCategory(String codeAsacategory) {
		this.codeAsaCategory = codeAsacategory;
	}

	public String getCode() {
		return buildCompositeKey(codeAsaCategory,  idPeriodeValidite, codeRateLevel);	
	}

	

	public AsaCategory getAsaCategory() {
		return asaCategory;
	}

	public String getCodeRateLevel() {
		return codeRateLevel;
	}

	public void setCodeRateLevel(String codeRateLevel) {
		this.codeRateLevel = codeRateLevel;
	}

	
	public static String buildCompositeKey(String codeAsaCategory,  int idPeriodeValidite, String codeRateLevel)
	{
		return codeAsaCategory + "_" + idPeriodeValidite+"_"+codeRateLevel;
	}

	public static ParamRateLevelCacheList getCacheList(Contexte contexte) throws IncoherenceException, TechnicalException
	{
		return (ParamRateLevelCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.PARAM_RATE_LEVEL_KEY, contexte);
	}
}
