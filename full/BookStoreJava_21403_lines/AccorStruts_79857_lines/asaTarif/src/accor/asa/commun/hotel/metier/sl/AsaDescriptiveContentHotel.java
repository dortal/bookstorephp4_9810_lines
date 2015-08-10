/*
 * Created on 28 oct. 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.accor.asa.commun.hotel.metier.sl;

import com.accor.services.framework.enterprise.hotel.amenities.DescriptiveContentHotel;

/**
 * @author FCHIVAUX
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public final class AsaDescriptiveContentHotel {
	
  final DescriptiveContentHotel dch;
  final int[] capacites;
  final boolean[] infos;
  final boolean segment;

	public AsaDescriptiveContentHotel(DescriptiveContentHotel dch, int[] capacites, boolean[] infos, boolean segment ) {
		this.dch = dch;
    this.capacites = capacites;
    this.infos = infos;
    this.segment = segment;
  }
  
  public int[] getCapacites() {
		return capacites;
	}
	public DescriptiveContentHotel getDescriptiveContentHotel() {
		return dch;
	}
	public boolean[] getInfos() {
		return infos;
	}
  public boolean getSegment() {
   return segment; 
  }

  public String toString() {
  	StringBuffer sb = new StringBuffer();
  	sb.append( dch.toString() );
  	if( capacites != null ) {
  		sb.append( "\n\tCapacities[" );
  		for( int i=0, size=capacites.length; i<size; i++ )
    		sb.append( capacites[i] ).append( "|" );
  		sb.append( "]" );
  	}
  	if( infos != null ) {
  		sb.append( "\n\tProducts[" );
  		for( int i=0, size=infos.length; i<size; i++ )
    		sb.append( infos[i] ).append( "|" );
  		sb.append( "]" );
  	}
  	sb.append( "\n\tSegment[" ).append( segment ).append( "]" );
  	return sb.toString();
  }
}
