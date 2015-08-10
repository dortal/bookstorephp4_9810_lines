package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class AbsentTarsRefBean extends RefBean{
	
	private String codeHotel;
	private String idFamille;
	private String codeRateLevel;
	
	public String getCodeRateLevel() {
		return codeRateLevel;
	}

	public void setCodeRateLevel(String codeRateLevel) {
		this.codeRateLevel = codeRateLevel;
	}

	public String getCodeHotel() {
		return codeHotel;
	}

	public void setCodeHotel(String codeHotel) {
		this.codeHotel = codeHotel;
	}

	public String getIdFamille() {
		return idFamille;
	}

	public void setIdFamille(String idFamille) {
		this.idFamille = idFamille;
	}

	public static AbsentTarsCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException {
		return (AbsentTarsCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.ABSENT_TARS_KEY, contexte);
	}
	
	public String toString() {
		return "codeHotel =" + codeHotel + " idFamille = " + idFamille;
	}

	public AbsentTarsRefBean(String codeRateLevel) {
		super();
		this.codeRateLevel = codeRateLevel;
	}
	
	
	
	
}
