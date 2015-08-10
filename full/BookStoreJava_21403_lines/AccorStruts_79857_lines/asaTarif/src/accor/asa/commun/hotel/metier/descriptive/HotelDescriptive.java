package com.accor.asa.commun.hotel.metier.descriptive;

import java.io.Serializable;

import com.accor.asa.commun.metier.Element;
import com.accor.asa.commun.metier.devise.Devise;
import com.accor.asa.commun.user.metier.SaleRegion;
import com.accor.asa.commun.user.metier.SaleZone;

@SuppressWarnings("serial")
public class HotelDescriptive implements Serializable {

	protected String 		creationDate;
	protected String 		buildDate;
	protected String 		renovationDate;

	protected Integer		nbLifts;
	protected Integer		nbRooms;
	protected Integer		nbFloors;
	protected Float			commission;
	protected String		commissionUnit;
	protected Element		CRO;
	protected Element		operationalArea;
	protected Devise		currency;
	protected Element		environment;
	protected Element		lodgingType;
	protected Element		location;
	protected SaleRegion	region;
	protected Element		standard;
	protected String		timeDifference;
	protected String		bookingLimitTime;
	protected String		languageCode;
	protected SaleZone		zone;
	
	protected String		resaDay;
	protected String		closed;
	protected String		indicResaPlace;
	protected String		collected;
	protected String		segmentBypass;
	protected String		deleted;
	
	public HotelDescriptive() {}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "[creationDate=" ).append( creationDate ).append( "], " );
		sb.append( "[buildDate=" ).append( buildDate ).append( "], " );
		sb.append( "[renovationDate=" ).append( renovationDate ).append( "], " );
		sb.append( "[nbLifts=" ).append( nbLifts ).append( "], " );
		sb.append( "[nbRooms=" ).append( nbRooms ).append( "], " );
		sb.append( "[nbFloors=" ).append( nbFloors ).append( "], " );
		sb.append( "[commission=" ).append( commission ).append( "], " );
		sb.append( "[commissionUnit=" ).append( commissionUnit ).append( "], " );
		sb.append( "[CRO=" ).append( CRO ).append( "], " );
		sb.append( "[operationalArea=" ).append( operationalArea ).append( "], " );
		sb.append( "[currency=" ).append( currency ).append( "], " );
		sb.append( "[environment=" ).append( environment ).append( "], " );
		sb.append( "[lodgingType=" ).append( lodgingType ).append( "], " );
		sb.append( "[location=" ).append( location ).append( "], " );
		sb.append( "[region=" ).append( region ).append( "], " );
		sb.append( "[standard=" ).append( standard ).append( "], " );
		sb.append( "[timeDifference=" ).append( timeDifference ).append( "], " );
		sb.append( "[bookingLimitTime=" ).append( bookingLimitTime ).append( "], " );
		sb.append( "[languageCode=" ).append( languageCode ).append( "], " );
		sb.append( "[zone=" ).append( zone ).append( "], " );
		sb.append( "[resaDay=" ).append( resaDay ).append( "], " );
		sb.append( "[closed=" ).append( closed ).append( "], " );
		sb.append( "[indicResaPlace=" ).append( indicResaPlace ).append( "], " );
		sb.append( "[collected=" ).append( collected ).append( "], " );
		sb.append( "[segmentBypass=" ).append( segmentBypass ).append( "], " );
		sb.append( "[deleted=" ).append( deleted ).append( "], " );
		return sb.toString();
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(String buildDate) {
		this.buildDate = buildDate;
	}

	public String getRenovationDate() {
		return renovationDate;
	}

	public void setRenovationDate(String renovationDate) {
		this.renovationDate = renovationDate;
	}

	public Integer getNbLifts() {
		return nbLifts;
	}

	public void setNbLifts(Integer nbLifts) {
		this.nbLifts = nbLifts;
	}

	public Integer getNbRooms() {
		return nbRooms;
	}

	public void setNbRooms(Integer nbRooms) {
		this.nbRooms = nbRooms;
	}

	public Integer getNbFloors() {
		return nbFloors;
	}

	public void setNbFloors(Integer nbFloors) {
		this.nbFloors = nbFloors;
	}

	public Float getCommission() {
		return commission;
	}

	public void setCommission(Float commission) {
		this.commission = commission;
	}

	public String getCommissionUnit() {
		return commissionUnit;
	}

	public void setCommissionUnit(String commissionUnit) {
		this.commissionUnit = commissionUnit;
	}

	public Element getCRO() {
		return CRO;
	}

	public void setCRO(Element cro) {
		CRO = cro;
	}

	public Element getOperationalArea() {
		return operationalArea;
	}

	public void setOperationalArea(Element operationalArea) {
		this.operationalArea = operationalArea;
	}

	public Devise getCurrency() {
		return currency;
	}

	public void setCurrency(Devise currency) {
		this.currency = currency;
	}

	public Element getEnvironment() {
		return environment;
	}

	public void setEnvironment(Element environment) {
		this.environment = environment;
	}

	public Element getLodgingType() {
		return lodgingType;
	}

	public void setLodgingType(Element lodgingType) {
		this.lodgingType = lodgingType;
	}

	public Element getLocation() {
		return location;
	}

	public void setLocation(Element location) {
		this.location = location;
	}

	public SaleRegion getRegion() {
		return region;
	}

	public void setRegion(SaleRegion region) {
		this.region = region;
	}

	public Element getStandard() {
		return standard;
	}

	public void setStandard(Element standard) {
		this.standard = standard;
	}

	public String getTimeDifference() {
		return timeDifference;
	}

	public void setTimeDifference(String timeDifference) {
		this.timeDifference = timeDifference;
	}

	public String getBookingLimitTime() {
		return bookingLimitTime;
	}

	public void setBookingLimitTime(String bookingLimitTime) {
		this.bookingLimitTime = bookingLimitTime;
	}

	public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public SaleZone getZone() {
		return zone;
	}

	public void setZone(SaleZone zone) {
		this.zone = zone;
	}

	public void setResaDay(String resaDay) {
		this.resaDay = resaDay;
	}

	public void setClosed(String closed) {
		this.closed = closed;
	}

	public String getIndicResaPlace() {
		return indicResaPlace;
	}

	public void setIndicResaPlace(String indicResaPlace) {
		this.indicResaPlace = indicResaPlace;
	}

	public void setCollected(String collected) {
		this.collected = collected;
	}

	public void setSegmentBypass(String segmentBypass) {
		this.segmentBypass = segmentBypass;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public Boolean haveResaDay() {
		return new Boolean( "1".equals( resaDay ) );
	}
	
	public Boolean isClosed() {
		return new Boolean( "0".equals( closed ) );
	}
	
	public Boolean isCollected() {
		if( collected == null )
			return null;
		
		return new Boolean( "1".equals( collected  ) );
	}
	
	public Boolean haveSegmentBypass() {
		if( segmentBypass == null )
			return null;

		return new Boolean( "1".equals( segmentBypass ) );
	}
	
	public Boolean isDeleted() {
		if( deleted == null )
			return null;

		return new Boolean( "1".equals( deleted ) );
	}
}
