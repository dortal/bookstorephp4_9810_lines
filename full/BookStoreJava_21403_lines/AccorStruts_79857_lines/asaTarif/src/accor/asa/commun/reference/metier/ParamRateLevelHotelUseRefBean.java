package com.accor.asa.commun.reference.metier;

import com.accor.asa.commun.hotel.metier.HotelLight;

@SuppressWarnings("serial")
public class ParamRateLevelHotelUseRefBean extends RefBean{
	
	private String codeHotel;
	private int idFamilleTarif;
	private String codeRateLevel;
	
	private HotelLight hotel;
	private FamilleTarifRefBean familleTarif;
	
	
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
	
	public String getCodeRateLevel() {
		return codeRateLevel;
	}
	public void setCodeRateLevel(String codeRateLevel) {
		this.codeRateLevel = codeRateLevel;
	}
	public HotelLight getHotel() {
		return hotel;
	}
	public void setHotel(HotelLight hotel) {
		this.hotel = hotel;
		if(hotel==null)
			setCodeHotel(null);
		else
			setCodeHotel(hotel.getCode());
	}
	public FamilleTarifRefBean getFamilleTarif() {
		return familleTarif;
	}
	public void setFamilleTarif(FamilleTarifRefBean familleTarif) {
		this.familleTarif = familleTarif;
		if(familleTarif==null)
			setIdFamilleTarif(-1);
		else
			setIdFamilleTarif(Integer.parseInt(familleTarif.getId()));
	}
	@Override
	public String getCode() {
		return codeHotel+"_"+idFamilleTarif+"_"+codeRateLevel;
	}
	
	

	
	
}
