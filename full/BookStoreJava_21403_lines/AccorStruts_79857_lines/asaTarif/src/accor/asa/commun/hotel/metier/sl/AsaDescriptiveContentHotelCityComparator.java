package com.accor.asa.commun.hotel.metier.sl;

import java.util.Comparator;

public class AsaDescriptiveContentHotelCityComparator implements Comparator<AsaDescriptiveContentHotel> {

  public int compare( AsaDescriptiveContentHotel o1, AsaDescriptiveContentHotel o2) {
    int ret = ( o1.getDescriptiveContentHotel().getAddress().getCityName().compareTo( 
            o2.getDescriptiveContentHotel().getAddress().getCityName() ) );
    return ( ret == 0 )?1:ret;
  }
  
  public boolean equals( AsaDescriptiveContentHotel o1, AsaDescriptiveContentHotel o2  ) {
    return( o1.getDescriptiveContentHotel().getAddress().getCityName().equals( 
          o2.getDescriptiveContentHotel().getAddress().getCityName() ) );
  }

}
