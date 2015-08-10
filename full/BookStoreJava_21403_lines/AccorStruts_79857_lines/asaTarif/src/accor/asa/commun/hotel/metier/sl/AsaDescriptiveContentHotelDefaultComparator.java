package com.accor.asa.commun.hotel.metier.sl;

import java.util.Comparator;

public class AsaDescriptiveContentHotelDefaultComparator implements Comparator<AsaDescriptiveContentHotel> {

  public int compare( AsaDescriptiveContentHotel o1, AsaDescriptiveContentHotel o2 ) {
    
    int ret = 0;
    
    boolean[] infos = o1.getInfos(); 
    
    if( infos == null && ( ! o1.getSegment() ) && ( ! o2.getSegment() ) )
      ret = ( o1.getDescriptiveContentHotel().getName().compareTo( 
    		  o2.getDescriptiveContentHotel().getName() ) );
    else 
      if( o1.getSegment() == o2.getSegment() ) {
        if( infos != null ) {
          for( int i=0, size =infos.length; i<size; i++ ) {
            if( infos[i] != o2.getInfos()[i] ) {
              ret = ( infos[i] )?-1:1;
              i = infos.length;
            }
          }
        }
        if( ret == 0 )
          ret = o1.getDescriptiveContentHotel().getName().compareTo( 
        		  	o2.getDescriptiveContentHotel().getName() );
          
      } else {
        ret = ( o1.getSegment() )?-1:1;
      }

    return ret;
  }
  
  public boolean equals( AsaDescriptiveContentHotel o1, AsaDescriptiveContentHotel o2 ) {

    boolean ret = true;
    
    if( o1.getSegment() == o2.getSegment() ) {
      boolean[] infos = o1.getInfos(); 
      if( infos != null )
        for( int i=0, size =infos.length; i<size; i++ ) {
          if( infos[i] != o2.getInfos()[i] ) {
            ret = false;
            i = infos.length;
          }
        }
    } else {
      ret = false;
    }
    return ret;
  }
}
