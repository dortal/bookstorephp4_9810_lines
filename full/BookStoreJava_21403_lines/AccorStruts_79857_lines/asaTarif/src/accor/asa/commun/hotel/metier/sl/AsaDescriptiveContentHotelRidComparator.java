package com.accor.asa.commun.hotel.metier.sl;

import java.util.Comparator;

public class AsaDescriptiveContentHotelRidComparator implements Comparator<AsaDescriptiveContentHotel> {

  public int compare( AsaDescriptiveContentHotel o1, AsaDescriptiveContentHotel o2 ) {
    return( o1.getDescriptiveContentHotel().getCode().compareTo( 
            o2.getDescriptiveContentHotel().getCode() ) );
  }
  
  public boolean equals( AsaDescriptiveContentHotel o1, AsaDescriptiveContentHotel o2  ) {
    return( o1.getDescriptiveContentHotel().getCode().equals( 
          	o2.getDescriptiveContentHotel().getCode() ) );
  }

}
