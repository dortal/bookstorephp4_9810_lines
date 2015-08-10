package com.accor.asa.commun.reference.metier;

import java.util.Date;

public class ParamPeriodeValiditeData {

	private int idPeriodeValidite;
	private Date dateOuverture;
	private Date dateFermeture;
	private Date dateValidation;
	private Date dateValidation2;

	private PeriodeValiditeRefBean periodeVlaidite;

	public ParamPeriodeValiditeData(ParamPeriodeValiditeRefBean row)
	{
		this.idPeriodeValidite=row.getIdPeriodeValidite();
		this.dateOuverture=row.getDateOuverture();
		this.dateFermeture=row.getDateFermeture();
		this.dateValidation = row.getDateValidation();
		this.dateValidation2=row.getDateValidation2();
	}
	
	
	public ParamPeriodeValiditeData() {
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

	public void setDateOuverture(Date dateOuverture) {
		this.dateOuverture = dateOuverture;
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
}
