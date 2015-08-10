package com.accor.asa.commun.hotel.metier.sl;

import java.util.Comparator;

import com.accor.services.framework.enterprise.hotel.geo.GeographicalZone;

public class GeographicalZoneDefaultComparator implements Comparator<GeographicalZone> {

  public int compare( GeographicalZone o1, GeographicalZone o2 ) {
    int res = o1.getTitle().compareTo( o2.getTitle() );
    if( res == 0 )
    	res = o1.getValue().compareTo( o2.getValue() );
    return res;
  }

  public boolean equals( GeographicalZone o1, GeographicalZone o2  ) {
    return ( o1.getType().equals( o2.getType() ) 
           && o1.getValue().equals( o2.getValue() ) );
  }
}
