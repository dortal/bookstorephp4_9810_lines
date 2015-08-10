package com.accor.asa.commun.hotel.metier.descriptive;

@SuppressWarnings("serial")
public class HotelInterestCenter extends HotelElement {

	protected Float					distanceInMiles;
	protected Float					distanceInKm;
	protected Integer				walkDistance;
	protected Integer				driveDistance;
	protected String				orientation;
	protected boolean 				shuttle;
	protected boolean				freeShuttle;
	protected Float					shuttlePrice;
	protected String				shuttleOnCall;
	protected String				shuttleScheduled;
	protected Float					limousinePrice;
	protected Float					taxiPrice;
	protected Integer				displayOrder;
	protected String				web;
	protected String				vuGdsWeb;
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( super.toString() );
		sb.append( "[distanceInMiles=" ).append( distanceInMiles ).append( "], " );
		sb.append( "[distanceInKm=" ).append( distanceInKm ).append( "], " );
		sb.append( "[walkDistance=" ).append( walkDistance ).append( "], " );
		sb.append( "[driveDistance=" ).append( driveDistance ).append( "], " );
		sb.append( "[orientation=" ).append( orientation ).append( "], " );
		sb.append( "[shuttle=" ).append( shuttle ).append( "], " );
		sb.append( "[freeShuttle=" ).append( freeShuttle ).append( "], " );
		sb.append( "[shuttlePrice=" ).append( shuttlePrice ).append( "], " );
		sb.append( "[shuttleOnCall=" ).append( shuttleOnCall ).append( "], " );
		sb.append( "[shuttleScheduled=" ).append( shuttleScheduled ).append( "], " );
		sb.append( "[limousinePrice=" ).append( limousinePrice ).append( "], " );
		sb.append( "[taxiPrice=" ).append( taxiPrice ).append( "], " );
		sb.append( "[displayOrder=" ).append( displayOrder ).append( "], " );
		sb.append( "[web=" ).append( web ).append( "], " );
		sb.append( "[vuGdsWeb=" ).append( vuGdsWeb ).append( "], " );
		return sb.toString();
	}

	public Float getDistanceInMiles() {
		return distanceInMiles;
	}

	public void setDistanceInMiles(Float distanceInMiles) {
		this.distanceInMiles = distanceInMiles;
	}

	public Float getDistanceInKm() {
		return distanceInKm;
	}

	public void setDistanceInKm(Float distanceInKm) {
		this.distanceInKm = distanceInKm;
	}

	public Integer getWalkDistance() {
		return walkDistance;
	}

	public void setWalkDistance(Integer walkDistance) {
		this.walkDistance = walkDistance;
	}

	public Integer getDriveDistance() {
		return driveDistance;
	}

	public void setDriveDistance(Integer driveDistance) {
		this.driveDistance = driveDistance;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public boolean haveShuttle() {
		return shuttle;
	}

	public void setShuttle(boolean shuttle) {
		this.shuttle = shuttle;
	}

	public boolean haveFreeShuttle() {
		return freeShuttle;
	}

	public void setFreeShuttle(boolean freeShuttle) {
		this.freeShuttle = freeShuttle;
	}

	public Float getShuttlePrice() {
		return shuttlePrice;
	}

	public void setShuttlePrice(Float shuttlePrice) {
		this.shuttlePrice = shuttlePrice;
	}

	public void setShuttleOnCall(String shuttleOnCall) {
		this.shuttleOnCall = shuttleOnCall;
	}

	public void setShuttleScheduled(String shuttleScheduled) {
		this.shuttleScheduled = shuttleScheduled;
	}

	public Float getLimousinePrice() {
		return limousinePrice;
	}

	public void setLimousinePrice(Float limousinePrice) {
		this.limousinePrice = limousinePrice;
	}

	public Float getTaxiPrice() {
		return taxiPrice;
	}

	public void setTaxiPrice(Float taxiPrice) {
		this.taxiPrice = taxiPrice;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getVuGdsWeb() {
		return vuGdsWeb;
	}

	public void setVuGdsWeb(String vuGdsWeb) {
		this.vuGdsWeb = vuGdsWeb;
	}
	
	public Boolean haveShuttleOnCall() {
		if( shuttleOnCall == null )
			return null;
		
		return new Boolean( "1".equals( shuttleOnCall ) );
	}
	
	public Boolean haveShuttleScheduled() {
		if( shuttleScheduled == null )
			return null;
		
		return new Boolean( "1".equals( shuttleScheduled ) );
	}
}
