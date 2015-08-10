package com.accor.asa.commun.metier.statutgrillehotel;

import java.io.Serializable;

import com.accor.asa.commun.hotel.metier.HotelLight;
import com.accor.asa.commun.reference.metier.FamilleTarifRefBean;
import com.accor.asa.commun.reference.metier.GroupeTarifRefBean;
import com.accor.asa.commun.reference.metier.PeriodeValiditeRefBean;
import com.accor.asa.commun.reference.metier.StatutGrilleRefBean;

public class StatutGrilleHotel implements Serializable {

	private HotelLight hotel = null;
	private GroupeTarifRefBean groupeTarif = null;
	private FamilleTarifRefBean familleTarif = null;
	private PeriodeValiditeRefBean periodeValidite = null;
	private StatutGrilleRefBean statutGrille = null;
	
	public HotelLight getHotel () {
		return this.hotel;
	}
	
	public GroupeTarifRefBean getGroupeTarif () {
		return this.groupeTarif;
	}
	
	public FamilleTarifRefBean getFamilleTarif () {
		return this.familleTarif;
	}
	
	public PeriodeValiditeRefBean getPeriodeValidite () {
		return this.periodeValidite;
	}
	
	public StatutGrilleRefBean getStatut () {
		return this.statutGrille;
	}
	
	public void setHotel (HotelLight hotel) {
		this.hotel = hotel;
	}
	
	public void setGroupeTarif (GroupeTarifRefBean groupeTarif) {
		this.groupeTarif = groupeTarif;
	}
	
	public void setFamilleTarif (FamilleTarifRefBean familleTarif) {
		this.familleTarif = familleTarif;
	}
	
	public void setPeriodeValidite (PeriodeValiditeRefBean periodeValidite) {
		this.periodeValidite = periodeValidite;
	}
	
	public void setStatutGrille (StatutGrilleRefBean statutGrille) {
		this.statutGrille = statutGrille;
	}
}