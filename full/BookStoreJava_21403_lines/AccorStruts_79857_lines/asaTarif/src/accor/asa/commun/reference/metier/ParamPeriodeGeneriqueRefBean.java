package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.categorie.AsaCategory;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class ParamPeriodeGeneriqueRefBean extends RefBean{
	
	private String codePeriode;
	private String codeAsaCategory;
	private int nbMaxPeriodes;

	private PeriodeGeneriqueRefBean periodeGenerique;
	private AsaCategory asaCategory;

	public String getCodePeriode() {
		return codePeriode;
	}

	public void setCodePeriode(String codePeriode) {
		this.codePeriode = codePeriode;
	}

	public String getCodeAsaCategory() {
		return codeAsaCategory;
	}

	public void setCodeAsaCategory(String codeAsaCategory) {
		this.codeAsaCategory = codeAsaCategory;
	}

	public int getNbMaxPeriodes() {
		return nbMaxPeriodes;
	}

	public void setNbMaxPeriodes(int nbMaxPeriodes) {
		this.nbMaxPeriodes = nbMaxPeriodes;
	}

	public PeriodeGeneriqueRefBean getPeriodeGenerique() {
		return periodeGenerique;
	}

	public void setPeriodeGenerique(PeriodeGeneriqueRefBean periodeGenerique) {
		this.periodeGenerique = periodeGenerique;
		if(periodeGenerique==null)
			setCodePeriode(null);
		else
			setCodePeriode(periodeGenerique.getCode());
	}

	public AsaCategory getAsaCategory() {
		return asaCategory;
	}

	public void setAsaCategory(AsaCategory asaCategory) {
		this.asaCategory = asaCategory;
		if (asaCategory == null)
			setCodeAsaCategory(null);
		else
			setCodeAsaCategory(asaCategory.getCode());

	}

	public String toString() {
		return "["+getClass().getName()+": codePeriode="+codePeriode+"; codeAsaCategory="+codeAsaCategory+"; nbMaxPeriodes="+nbMaxPeriodes+"]";
	}

	public String getCode() {
		return buildCompositeKey(getCodePeriode(), getCodeAsaCategory());
	}

	public static String buildCompositeKey(String codePeriode, String codeAsaCategory)
	{
		return codePeriode+"_"+codeAsaCategory;
	}
	
	public static ParamPeriodeGeneriqueCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException
	{
		return (ParamPeriodeGeneriqueCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.PARAM_PERIODE_GENERIQUE_KEY, contexte);	
	}
}
