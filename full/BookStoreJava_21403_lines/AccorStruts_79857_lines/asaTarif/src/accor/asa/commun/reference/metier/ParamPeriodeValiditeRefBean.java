package com.accor.asa.commun.reference.metier;

import java.util.Date;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.metier.categorie.AsaCategory;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class ParamPeriodeValiditeRefBean extends RefBean {

	public static String buildKey(String codeAsaCategory, int idGroupeTarif)
	{
		return codeAsaCategory + "_" + idGroupeTarif;
	}
	
	private String codeAsaCategory;
	private int idGroupeTarif;
	private int idPeriodeValidite;
	private Date dateOuverture;
	private Date dateFermeture;
	private Date dateValidation;
	private Date dateValidation2;

	private GroupeTarifRefBean groupeTarif;
	private PeriodeValiditeRefBean periodeVlaidite;
	private AsaCategory asaCategory;

	public String getCodeAsaCategory() {
		return codeAsaCategory;
	}

	public void setCodeAsaCategory(String codeAsaCategory) {
		this.codeAsaCategory = codeAsaCategory;
	}

	public int getIdGroupeTarif() {
		return idGroupeTarif;
	}

	public void setIdGroupeTarif(int idGroupeTarif) {
		this.idGroupeTarif = idGroupeTarif;
	}

	public int getIdPeriodeValidite() {
		return idPeriodeValidite;
	}

	public void setIdPeriodeValidite(int idPeriodeValidite) {
		this.idPeriodeValidite = idPeriodeValidite;
	}

	public Date getDateOuverture() {
		return dateOuverture;
	}

	public void setDateOuverture(Date dateUverture) {
		this.dateOuverture = dateUverture;
	}

	public Date getDateFermeture() {
		return dateFermeture;
	}

	public void setDateFermeture(Date dateFermeture) {
		this.dateFermeture = dateFermeture;
	}

	public Date getDateValidation() {
		return dateValidation;
	}

	public void setDateValidation(Date dateValidation) {
		this.dateValidation = dateValidation;
	}

	public Date getDateValidation2() {
		return dateValidation2;
	}

	public void setDateValidation2(Date dateValidation2) {
		this.dateValidation2 = dateValidation2;
	}

	public String getCode() {
		return buildKey(codeAsaCategory, idGroupeTarif);
	}

	

	public GroupeTarifRefBean getGroupeTarif() {
		return groupeTarif;
	}

	public void setGroupeTarif(GroupeTarifRefBean groupeTarif) {
		this.groupeTarif = groupeTarif;
		if (groupeTarif == null)
			setIdGroupeTarif(-1);
		else
			setIdGroupeTarif(Integer.parseInt(groupeTarif.getId()));
	}

	public PeriodeValiditeRefBean getPeriodeVlaidite() {
		return periodeVlaidite;
	}

	public void setPeriodeVlaidite(PeriodeValiditeRefBean periodeVlaidite) {
		this.periodeVlaidite = periodeVlaidite;
		if(periodeVlaidite==null)
			setIdPeriodeValidite(-1);
		else
			setIdPeriodeValidite(Integer.parseInt(periodeVlaidite.getId()));
	}

	public AsaCategory getAsaCategory() {
		return asaCategory;
	}

	public void setAsaCategory(AsaCategory asacategory) {
		this.asaCategory = asacategory;
		if(asacategory==null)
			setCodeAsaCategory(null);
		else
			setCodeAsaCategory(asacategory.getCode());
	}

	public static ParamPeriodeValiditeCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException
	{
		 return (ParamPeriodeValiditeCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.PARAM_PERIODE_VALIDITE_KEY, contexte);
	}
	
}
