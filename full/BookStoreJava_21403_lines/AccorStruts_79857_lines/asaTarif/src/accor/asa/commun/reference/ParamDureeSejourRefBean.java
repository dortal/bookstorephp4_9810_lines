package com.accor.asa.commun.reference;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.metier.ParamDureeSejourCacheList;
import com.accor.asa.commun.reference.metier.RefBean;
import com.accor.asa.commun.reference.process.RefFacade;

public class ParamDureeSejourRefBean extends RefBean{

	private String codeChaineHotel;
	private int idDureeSejour;
	private boolean usedForSalons;
	public String getCodeChaineHotel() {
		return codeChaineHotel;
	}
	public void setCodeChaineHotel(String codeChaineHotel) {
		this.codeChaineHotel = codeChaineHotel;
	}
	public int getIdDureeSejour() {
		return idDureeSejour;
	}
	public void setIdDureeSejour(int idDureeSejour) {
		this.idDureeSejour = idDureeSejour;
	}
	public boolean isUsedForSalons() {
		return usedForSalons;
	}
	public void setUsedForSalons(boolean usedForSalons) {
		this.usedForSalons = usedForSalons;
	}
	@Override
	public String getCode() {
		return codeChaineHotel+"_"+idDureeSejour;
	}
	
	public static ParamDureeSejourCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException {
		return (ParamDureeSejourCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.PARAM_DUREE_SEJOUR_KEY, contexte);
	}

	
}
