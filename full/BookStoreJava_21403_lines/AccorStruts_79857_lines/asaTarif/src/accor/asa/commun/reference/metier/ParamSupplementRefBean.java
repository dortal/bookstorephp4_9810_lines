package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class ParamSupplementRefBean extends RefBean {

	private String codeAsaCategory;
	private int idPeriodeValidite;
	private String codeRateLevel;
	private String codeSupplement;
	
	
	
	public ParamSupplementRefBean() {
		super();
	}
	
	public ParamSupplementRefBean(ParamGrilleRefBean paramGrille, String codeSupplement)
	{
		codeAsaCategory=paramGrille.getCodeAsaCategory();
		idPeriodeValidite=paramGrille.getIdPeriodeValidite();
		codeRateLevel = paramGrille.getCodeRateLevel();
		this.codeSupplement=codeSupplement;
	}

	private ParamGrilleRefBean paramGrille;
	private SupplementRefBean supplement;

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

	public String getCodeSupplement() {
		return codeSupplement;
	}

	public void setCodeSupplement(String codeSupplement) {
		this.codeSupplement = codeSupplement;
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

	public SupplementRefBean getSupplement() {
		return supplement;
	}

	public void setSupplement(SupplementRefBean supplement) {
		this.supplement = supplement;
		if(supplement==null)
			setCodeSupplement(null);
		else
			setCodeSupplement(supplement.getCode());
	}

	public int getIdPeriodeValidite() {
		return idPeriodeValidite;
	}

	public void setIdPeriodeValidite(int idPeriodeValidite) {
		this.idPeriodeValidite = idPeriodeValidite;
	}

	public static ParamSupplementCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException {
		return (ParamSupplementCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.PARAM_SUPPLEMENT_KEY, contexte);
	}

	@Override
	public String getCode() {
		return codeAsaCategory+"_"+codeRateLevel+"_"+idPeriodeValidite+"_"+codeSupplement;
	}
	
	
}
