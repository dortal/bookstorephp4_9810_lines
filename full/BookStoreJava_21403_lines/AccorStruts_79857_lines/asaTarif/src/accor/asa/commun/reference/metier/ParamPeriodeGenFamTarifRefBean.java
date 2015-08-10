package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class ParamPeriodeGenFamTarifRefBean extends RefBean{

	private int idFamilleTarif;
	private String codePeriode;
	private boolean isDefault;
	
	private FamilleTarifRefBean familleTarif;
	private PeriodeGeneriqueRefBean periodeGenerique;
	public int getIdFamilleTarif() {
		return idFamilleTarif;
	}
	public void setIdFamilleTarif(int idFamilleTarif) {
		this.idFamilleTarif = idFamilleTarif;
	}
	public String getCodePeriode() {
		return codePeriode;
	}
	public void setCodePeriode(String codePeriode) {
		this.codePeriode = codePeriode;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
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
	
	
	
	public static ParamPeriodeGenFamTarifCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException {
		return (ParamPeriodeGenFamTarifCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.PARAM_PGFT_KEY, contexte);
	}
	@Override
	public String getCode() {
		return getIdFamilleTarif()+"_"+getCodePeriode();
	}
	
	
}
