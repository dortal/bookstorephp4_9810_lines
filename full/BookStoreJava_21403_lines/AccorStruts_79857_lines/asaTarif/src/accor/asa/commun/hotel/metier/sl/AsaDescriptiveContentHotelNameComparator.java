package com.accor.asa.commun.hotel.metier.sl;

import java.util.Comparator;

public class AsaDescriptiveContentHotelNameComparator implements Comparator<AsaDescriptiveContentHotel> {

  public int compare( AsaDescriptiveContentHotel o1, AsaDescriptiveContentHotel o2 ) {
    int ret = ( o1.getDescriptiveContentHotel().getName().compareTo( 
            	o2.getDescriptiveContentHotel().getName() ) );
    
    return ( ret == 0 )?1:ret;
  }
  
  public boolean equals( AsaDescriptiveContentHotel o1, AsaDescriptiveContentHotel o2  ) {
    return( o1.getDescriptiveContentHotel().getName().equals( 
    		o2.getDescriptiveContentHotel().getName() ) );
  }

}
