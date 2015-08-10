package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class ParamPeriodeGeneriqueExcluHotelRefBean extends RefBean{
	
	private String codePeriode;
	private String codeAsaCategory;
	private String codeHotel;
	private int nbMaxPeriodes;
	private String nomHotel;

	public String getNomHotel() {
		return nomHotel;
	}

	public void setNomHotel(String nomHotel) {
		this.nomHotel = nomHotel;
	}

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

	public String getCodeHotel() {
		return codeHotel;
	}

	public void setCodeHotel(String codeHotel) {
		this.codeHotel = codeHotel;
	}

	public String toString() {
		return "["+getClass().getName()
		+": codePeriode="+codePeriode
		+"; codeAsaCategory="+codeAsaCategory
		+"; codeHotel="+codeHotel
		+"; nbMaxPeriodes="+nbMaxPeriodes
		+"; nomhotel="+nomHotel
		+"]";
	}

	public String getCode() {
		return buildCompositeKey(getCodePeriode(), getCodeAsaCategory(), getCodeHotel());
	}

	public static String buildCompositeKey(String codePeriode, String codeAsaCategory, String codeHotel) {
		return codePeriode+"_"+codeAsaCategory+"_"+codeHotel;
	}
	
	public static ParamPeriodeGeneriqueExcluHotelCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException {
		return (ParamPeriodeGeneriqueExcluHotelCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.PARAM_PERIODE_GENERIQUE_EXCLUHOTEL_KEY, contexte);	
	}
}
