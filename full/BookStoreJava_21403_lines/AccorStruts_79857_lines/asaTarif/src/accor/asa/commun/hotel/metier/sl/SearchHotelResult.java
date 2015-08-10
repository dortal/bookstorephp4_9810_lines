package com.accor.asa.commun.hotel.metier.sl;

import java.util.List;

import com.accor.services.framework.enterprise.hotel.Hotel;
import com.accor.services.framework.enterprise.hotel.geo.GeographicalZone;

public class SearchHotelResult {

	List<Hotel> 			hotels;
	List<GeographicalZone>	geoZones;
	int						type		= Integer.MIN_VALUE;
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append( "[type=" ).append( type ).append( "]," );
		
		if( hotels != null) {
			sb.append( "[hotels=" );
			for( int i=0, size=hotels.size(); i<size; i++ )
				sb.append( "[" ).append( hotels.get(i) ).append( "]," );
			sb.append(  "]," );
		}
		
		if( geoZones != null) {
			sb.append( "[geoZones=" );
			for( int i=0, size=geoZones.size(); i<size; i++ )
				sb.append( "[" ).append( geoZones.get(i) ).append( "]," );
			sb.append(  "]," );
		}
		
		return sb.toString();
	}
	
	public List<Hotel> getHotels() {
		return hotels;
	}
	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}
	public void addHotels(List<Hotel> hotels) {
		this.hotels.addAll( hotels );
	}
	public List<GeographicalZone> getGeoZones() {
		return geoZones;
	}
	public void setGeoZones(List<GeographicalZone> geoZones) {
		this.geoZones = geoZones;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
