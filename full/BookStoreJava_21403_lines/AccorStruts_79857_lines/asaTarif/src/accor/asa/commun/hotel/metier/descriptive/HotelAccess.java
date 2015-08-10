package com.accor.asa.commun.hotel.metier.descriptive;

@SuppressWarnings("serial")
public class HotelAccess extends HotelElement {

	protected String	direction;
	protected String	line;
	protected String	station;

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( super.toString() );
		sb.append( "[openingHours=" ).append( direction ).append( "], " );
		sb.append( "[capacity=" ).append( line ).append( "], " );
		sb.append( "[averagePrice=" ).append( station ).append( "], " );
		return sb.toString();
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}
}
