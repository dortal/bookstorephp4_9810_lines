package com.accor.asa.commun.reference.metier;

import java.util.ArrayList;
import java.util.List;

import com.accor.asa.commun.hotel.metier.HotelLight;

@SuppressWarnings("serial")
public class ParamRateLevelHotelUseMappedRefBean extends RefBean {

	private String codeHotel;
	private int idFamilleTarif;

	private HotelLight hotel;
	private FamilleTarifRefBean familleTarif;

	private List<String> codesRateLevels = new ArrayList<String>();

	public String getCodeHotel() {
		return codeHotel;
	}

	public void setCodeHotel(String codeHotel) {
		this.codeHotel = codeHotel;
	}

	public int getIdFamilleTarif() {
		return idFamilleTarif;
	}

	public void setIdFamilleTarif(int idFamilleTarif) {
		this.idFamilleTarif = idFamilleTarif;
	}

	
	@Override
	public String getCode() {
		return getCodeHotel() + "_" + getIdFamilleTarif();
	}

	public HotelLight getHotel() {
		return hotel;
	}

	public void setHotel(HotelLight hotel) {
		this.hotel = hotel;
		if (hotel == null)
			setCodeHotel(null);
		else
			setCodeHotel(hotel.getCode());
	}

	public FamilleTarifRefBean getFamilleTarif() {
		return familleTarif;
	}

	public void setFamilleTarif(FamilleTarifRefBean familleTarif) {
		this.familleTarif = familleTarif;
		if (familleTarif == null)
			setIdFamilleTarif(-1);
		else
			setIdFamilleTarif(Integer.parseInt(familleTarif.getId()));
	}

	public void addRateLevel(String codeRateLevel) {
		codesRateLevels.add(codeRateLevel);
	}

	public List<String> getCodesRateLevels() {
		return codesRateLevels;
	}

}
