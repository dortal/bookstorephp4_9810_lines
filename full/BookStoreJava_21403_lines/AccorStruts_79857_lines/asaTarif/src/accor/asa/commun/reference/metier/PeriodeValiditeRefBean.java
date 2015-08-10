package com.accor.asa.commun.reference.metier;

import java.util.Calendar;
import java.util.Date;

import com.accor.asa.commun.TechnicalException;
import com.accor.asa.commun.metier.AsaDate;
import com.accor.asa.commun.metier.Contexte;
import com.accor.asa.commun.process.IncoherenceException;
import com.accor.asa.commun.process.PoolCommunFactory;
import com.accor.asa.commun.reference.process.RefFacade;

@SuppressWarnings("serial")
public class PeriodeValiditeRefBean extends RefBean {

	private Date dateDebut;
	private Date dateFin;
	private int idGroupeTarif;

	private GroupeTarifRefBean groupeTarif;

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public int getIdGroupeTarif() {
		return idGroupeTarif;
	}

	public void setIdGroupeTarif(int idGroupeTarif) {
		this.idGroupeTarif = idGroupeTarif;
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

	public int getDureeValidite() {
		if (dateDebut == null || dateFin == null)
			return 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(dateDebut);
		c2.setTime(dateFin);
		c2.set(Calendar.DATE, c2.get(Calendar.DATE) + 1);
		int yd = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		int md = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		int d = md + yd * 12;
		return (d % 11);
	}

	public void setDureeValidite(int nyears) {
		if (dateDebut == null)
			throw new RuntimeException("Date debut not set yet!");

		Calendar c1 = Calendar.getInstance();
		c1.setTime(dateDebut);
		c1.set(Calendar.YEAR, c1.get(Calendar.YEAR) + nyears);
		c1.set(Calendar.DATE, c1.get(Calendar.DATE) - 1);
		dateFin = new Date(c1.getTimeInMillis());
	}

	public String getLibelle() {
		StringBuffer sb = new StringBuffer();
		return sb.append(formatDate(getDateDebut())).append(" - ").append(formatDate(getDateFin())).toString();
	}

	private String formatDate(Date d) {
		return AsaDate.formatDateForDisplay(d);
	}

	public static PeriodeValiditeCacheList getCacheList(Contexte contexte) throws TechnicalException, IncoherenceException {
		return (PeriodeValiditeCacheList) PoolCommunFactory.getInstance().getRefFacade().getCacheRefList(RefFacade.PERIODE_VALIDITE_KEY, contexte);
	}
}
