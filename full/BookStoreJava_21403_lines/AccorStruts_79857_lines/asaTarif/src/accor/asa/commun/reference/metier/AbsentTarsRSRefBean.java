package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean utilisé afin de récupérer et transmettre les résultats de la procédure
 * admin_get_dif_rate_tars_asa
 * @author JHOURSIN
 *
 */
@SuppressWarnings("serial")
public class AbsentTarsRSRefBean extends RefBean{
	
	private List<AbsentTarsRefBean> listeAbsence;
	private int nbRateAsa;
	private int nbRateTars;
	
	public String toString() {
		return "nbRateAsa =" + nbRateAsa + " nbRateTars = " + nbRateTars;
	}
	
	public AbsentTarsRSRefBean(){
		listeAbsence = new ArrayList<AbsentTarsRefBean>();
		nbRateAsa = 0;
		nbRateTars = 0;
	}

	public List<AbsentTarsRefBean> getListeAbsence() {
		return listeAbsence;
	}

	public void setListeAbsence(List<AbsentTarsRefBean> listeAbsence) {
		this.listeAbsence = listeAbsence;
	}

	public int getNbRateAsa() {
		return nbRateAsa;
	}

	public void setNbRateAsa(int nbRateAsa) {
		this.nbRateAsa = nbRateAsa;
	}

	public int getNbRateTars() {
		return nbRateTars;
	}

	public void setNbRateTars(int nbRateTars) {
		this.nbRateTars = nbRateTars;
	}

}
