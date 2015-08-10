package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class RateLevelByDureeSejourRefBean extends RefBean {
	
	private int idPeriodeValidite;
	private int idFamilleTarif;
	private String initRateLavel;
	private int idDureeSejour;
	private String finalRateLevel;
	@Override
	public String getCode() {
		return generateCode(idPeriodeValidite, initRateLavel, idDureeSejour);
	}
	
	public static String generateCode(int idPeriode, String codeRL1, int idDureeSejour)
	{
		return idPeriode+"_"+codeRL1+"_"+idDureeSejour;
	}

	public int getIdPeriodeValidite() {
		return idPeriodeValidite;
	}

	public void setIdPeriodeValidite(int idPeriodeValidite) {
		this.idPeriodeValidite = idPeriodeValidite;
	}

	public int getIdFamilleTarif() {
		return idFamilleTarif;
	}

	public void setIdFamilleTarif(int idFamilleTarif) {
		this.idFamilleTarif = idFamilleTarif;
	}

	public String getInitRateLavel() {
		return initRateLavel;
	}

	public void setInitRateLavel(String initRateLavel) {
		this.initRateLavel = initRateLavel;
	}

	public int getIdDureeSejour() {
		return idDureeSejour;
	}

	public void setIdDureeSejour(int idDureeSejour) {
		this.idDureeSejour = idDureeSejour;
	}

	public String getFinalRateLevel() {
		return finalRateLevel;
	}

	public void setFinalRateLevel(String finalRateLevel) {
		this.finalRateLevel = finalRateLevel;
	}
	
	public static RateLevelByDureeSejourCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException {
		return (RateLevelByDureeSejourCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.PARAM_RL_BY_DS_KEY, contexte);
	}
	
	public static String generateReverseCode(int idPeriode, String codeRL2, int idDureeSejour)
	{
		return idPeriode+"_"+codeRL2+"_"+idDureeSejour;
	}
	
	public String getReverseCode()
	{
		return generateReverseCode(idPeriodeValidite, finalRateLevel, idDureeSejour);
	}
}
