package com.accor.asa.commun.hotel.metier.descriptive;

import com.accor.asa.commun.metier.AsaDate;

@SuppressWarnings("serial")
public class HotelBar extends HotelElement {

	protected String	openingHours;
	protected Integer	capacity;
	protected Float		averagePrice;
	protected String	amexCode;
	protected AsaDate	openingDate;
	protected AsaDate	closedDate;
	protected String	openDays;

	protected String	petsAccepted;
	protected String	roomService;
	protected String	lightMeal;
	protected String	happyHour;
	protected String	music;

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( super.toString() );
		sb.append( "[openingHours=" ).append( openingHours ).append( "], " );
		sb.append( "[capacity=" ).append( capacity ).append( "], " );
		sb.append( "[averagePrice=" ).append( averagePrice ).append( "], " );
		sb.append( "[amexCode=" ).append( amexCode ).append( "], " );
		sb.append( "[openingDate=" ).append( openingDate ).append( "], " );
		sb.append( "[closedDate=" ).append( closedDate ).append( "], " );
		sb.append( "[openDays=" ).append( openDays ).append( "], " );
		sb.append( "[petsAccepted=" ).append( petsAccepted ).append( "], " );
		sb.append( "[roomService=" ).append( roomService ).append( "], " );
		sb.append( "[lightMeal=" ).append( lightMeal ).append( "], " );
		sb.append( "[happyHour=" ).append( happyHour ).append( "], " );
		sb.append( "[music=" ).append( music ).append( "], " );
		return sb.toString();
	}

	public String getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Float getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(Float averagePrice) {
		this.averagePrice = averagePrice;
	}

	public String getAmexCode() {
		return amexCode;
	}

	public void setAmexCode(String amexCode) {
		this.amexCode = amexCode;
	}

	public AsaDate getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(AsaDate openingDate) {
		this.openingDate = openingDate;
	}

	public AsaDate getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(AsaDate closedDate) {
		this.closedDate = closedDate;
	}

	public String getOpenDays() {
		return openDays;
	}

	public void setOpenDays(String openDays) {
		this.openDays = openDays;
	}

	public void setPetsAccepted(String petsAccepted) {
		this.petsAccepted = petsAccepted;
	}

	public void setRoomService(String roomService) {
		this.roomService = roomService;
	}

	public void setLightMeal(String lightMeal) {
		this.lightMeal = lightMeal;
	}

	public void setHappyHour(String happyHour) {
		this.happyHour = happyHour;
	}

	public void setMusic(String music) {
		this.music = music;
	}

	public Boolean havePetsAccepted() {
		if( petsAccepted == null )
			return null;

		return new Boolean( "1".equals( petsAccepted ) );
	}
	
	public Boolean haveRoomService() {
		if( roomService == null )
			return null;

		return new Boolean( "1".equals( roomService ) );
	}
	
	public Boolean haveLightMeal() {
		if( lightMeal == null )
			return null;

		return new Boolean( "1".equals( lightMeal ) );
	}
	
	public Boolean haveHappyHour() {
		if( happyHour == null )
			return null;

		return new Boolean( "1".equals( happyHour ) );
	}
	
	public Boolean haveMusic() {
		if( music == null )
			return null;

		return new Boolean( "1".equals( music ) );
	}
}

