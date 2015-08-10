package com.accor.asa.commun.hotel.metier.descriptive;

import com.accor.asa.commun.metier.AsaDate;

@SuppressWarnings("serial")
public class HotelRestaurant extends HotelElement {

	protected String	openingHours;
	protected Integer	capacity;
	protected Float		averagePrice;
	protected String	cuisineTypeCode;
	protected String	amexCode;
	protected AsaDate	openingDate;
	protected AsaDate	closedDate;
	protected String	openDays;

	protected boolean 	airConditionning;
	protected boolean	smokeForbidden;
	protected String	terrace;
	protected String	mealsServedAtSwimmingPool;
	protected String	petsAccepted;
	protected String  	themedActivities;
	protected String 	childMeal;
	protected String	fullBoard;
	protected String	halfBoard;

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( super.toString() );
		sb.append( "[openingHours=" ).append( openingHours ).append( "], " );
		sb.append( "[capacity=" ).append( capacity ).append( "], " );
		sb.append( "[averagePrice=" ).append( averagePrice ).append( "], " );
		sb.append( "[cuisineTypeCode=" ).append( cuisineTypeCode ).append( "], " );
		sb.append( "[amexCode=" ).append( amexCode ).append( "], " );
		sb.append( "[openingDate=" ).append( openingDate ).append( "], " );
		sb.append( "[closedDate=" ).append( closedDate ).append( "], " );
		sb.append( "[openDays=" ).append( openDays ).append( "], " );
		sb.append( "[airConditionning=" ).append( airConditionning ).append( "], " );
		sb.append( "[smokeForbidden=" ).append( smokeForbidden ).append( "], " );
		sb.append( "[terrace=" ).append( terrace ).append( "], " );
		sb.append( "[mealsServedAtSwimmingPool=" ).append( mealsServedAtSwimmingPool ).append( "], " );
		sb.append( "[petsAccepted=" ).append( petsAccepted ).append( "], " );
		sb.append( "[themedActivities=" ).append( themedActivities ).append( "], " );
		sb.append( "[childMeal=" ).append( childMeal ).append( "], " );
		sb.append( "[fullBoard=" ).append( fullBoard ).append( "], " );
		sb.append( "[halfBoard=" ).append( halfBoard ).append( "], " );
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

	public String getCuisineTypeCode() {
		return cuisineTypeCode;
	}

	public void setCuisineTypeCode(String cuisineTypeCode) {
		this.cuisineTypeCode = cuisineTypeCode;
	}

	public String getAmexCode() {
		return amexCode;
	}

	public void setAmexCode(String amexCode) {
		this.amexCode = amexCode;
	}

	public boolean haveAirConditionning() {
		return airConditionning;
	}

	public void setAirConditionning(boolean airConditionning) {
		this.airConditionning = airConditionning;
	}

	public boolean isSmokeForbidden() {
		return smokeForbidden;
	}

	public void setSmokeForbidden(boolean smokeForbidden) {
		this.smokeForbidden = smokeForbidden;
	}

	public void setTerrace(String terrace) {
		this.terrace = terrace;
	}

	public void setMealsServedAtSwimmingPool(String mealsServedAtSwimmingPool) {
		this.mealsServedAtSwimmingPool = mealsServedAtSwimmingPool;
	}

	public void setPetsAccepted(String petsAccepted) {
		this.petsAccepted = petsAccepted;
	}

	public void setThemedActivities(String themedActivities) {
		this.themedActivities = themedActivities;
	}

	public void setChildMeal(String childMeal) {
		this.childMeal = childMeal;
	}

	public void setFullBoard(String fullBoard) {
		this.fullBoard = fullBoard;
	}

	public void setHalfBoard(String halfBoard) {
		this.halfBoard = halfBoard;
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

	public Boolean haveTerrace() {
		if( terrace == null )
			return null;

		return new Boolean( "1".equals( terrace ) );
	}
	
	public Boolean haveMealsServedAtSwimmingPool() {
		if( mealsServedAtSwimmingPool == null )
			return null;

		return new Boolean( "1".equals( mealsServedAtSwimmingPool ) );
	}
	
	public Boolean havePetsAccepted() {
		if( petsAccepted == null )
			return null;

		return new Boolean( "1".equals( petsAccepted ) );
	}
	
	public Boolean haveThemedActivites() {
		if( themedActivities == null )
			return null;

		return new Boolean( "1".equals( themedActivities ) );
	}
	
	public Boolean haveChildMeal() {
		if( childMeal == null )
			return null;

		return new Boolean( "1".equals( childMeal ) );
	}
	
	public Boolean haveFullBoard() {
		if( fullBoard == null )
			return null;

		return new Boolean( "1".equals( fullBoard ) );
	}
	
	public Boolean haveHalfBoard() {
		if( halfBoard == null )
			return null;

		return new Boolean( "1".equals( halfBoard ) );
	}

}
