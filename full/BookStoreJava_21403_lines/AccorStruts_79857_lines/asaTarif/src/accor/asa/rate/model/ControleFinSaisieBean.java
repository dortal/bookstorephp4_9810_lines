package com.accor.asa.rate.model;

import java.io.Serializable;

public class ControleFinSaisieBean implements Serializable {

	private String codeHotel = null;
	private int idInstance = 0;
	private int idControle = 0;
	private int idGroupeTarif = 0;
	private int idPeriodeValidite = 0;
	private String libelleMessage = null;
	private boolean isBloquant = false;

	public ControleFinSaisieBean () {
		super();
	}

	public String getCodeHotel () {
		return this.codeHotel;
	}

	public int getIdInstance () {
		return this.idInstance;
	}

	public int getIdControle () {
		return this.idControle;
	}

	public int getIdGroupeTarif () {
		return this.idGroupeTarif;
	}

	public int getIdPeriodeValidite () {
		return this.idPeriodeValidite;
	}

	public String getLibelleMessage () {
		return this.libelleMessage;
	}

	public boolean isBloquant () {
		return this.isBloquant;
	}

	public void setCodeHotel (String codeHotel) {
		this.codeHotel = codeHotel;
	}

	public void setIdInstance (int idInstance) {
		this.idInstance = idInstance;
	}

	public void setIdControle (int idControle) {
		this.idControle = idControle;
	}

	public void setIdGroupeTarif (int idGroupeTarif) {
		this.idGroupeTarif = idGroupeTarif;
	}

	public void setIdPeriodeValidite (int idPeriodeValidite) {
		this.idPeriodeValidite = idPeriodeValidite;
	}

	public void setLibelleMessage (String libelleMessage) {
		this.libelleMessage = libelleMessage;
	}

	public void setBloquant (boolean isBloquant) {
		this.isBloquant = isBloquant;
	}
}