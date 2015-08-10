package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ParamPeriodeGenFamTarifMappedRefBean extends RefBean{
	
	private  int idFamilleTarif;
	private List<PeriodeGeneriqueRefBean> periodes=new ArrayList<PeriodeGeneriqueRefBean>();
	private String codeDefaultPeriode;
	
	private FamilleTarifRefBean familleTarif;
	
	
	public int getIdFamilleTarif() {
		return idFamilleTarif;
	}
	public void setIdFamilleTarif(int idFamilleTarif) {
		this.idFamilleTarif = idFamilleTarif;
	}
	public List<PeriodeGeneriqueRefBean> getPeriodes() {
		return periodes;
	}
	public void setPeriodes(List<PeriodeGeneriqueRefBean> periodes) {
		this.periodes = periodes;
	}
	public String getCodeDefaultPeriode() {
		return codeDefaultPeriode;
	}
	public void setCodeDefaultPeriode(String codeDefaultPeriode) {
		this.codeDefaultPeriode = codeDefaultPeriode;
	}
	
	public void setFamilleTarif(FamilleTarifRefBean familleTarif) {
		this.familleTarif = familleTarif;
		if(familleTarif==null)
			setIdFamilleTarif(-1);
		else
			setIdFamilleTarif(Integer.parseInt(familleTarif.getId()));
	}
	public FamilleTarifRefBean getFamilleTarif() {
		return familleTarif;
	}
	
	
	@Override
	public String getCode() {
		return String.valueOf(getIdFamilleTarif());
	}
	

	public void addPeriodeGenerique(PeriodeGeneriqueRefBean pg, boolean isDefault)
	{
		periodes.add(pg);
		if(isDefault)
			setCodeDefaultPeriode(pg.getCode());
	}
}
