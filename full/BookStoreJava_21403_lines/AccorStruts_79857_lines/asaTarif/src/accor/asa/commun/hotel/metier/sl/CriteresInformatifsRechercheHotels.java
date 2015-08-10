/*
 * Created on 14 oct. 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.accor.asa.commun.hotel.metier.sl;

import java.util.ArrayList;
import java.util.List;

import com.accor.services.framework.enterprise.common.basictype.BasicType;

/**
 * @author FCHIVAUX
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CriteresInformatifsRechercheHotels {
  
  private List<BasicType> listeCriteres;
  
  public CriteresInformatifsRechercheHotels(){
  	listeCriteres = new ArrayList<BasicType>();
  }

  public void addCritere( BasicType bt ) { 
    listeCriteres.add( bt );
  }
  public void addCritere( String code, String codeType, short flagType ) { 
    BasicType crit = new BasicType ( code, codeType, flagType );  
    listeCriteres.add( crit );
  }

  public List<BasicType> getCriteres() {
   return listeCriteres; 
  }
  
  public BasicType getCritereAt( int index ) {
   BasicType crit = null;
   if( index < getCriteresCount() )
     crit = (BasicType) listeCriteres.get( index ); 
   return crit;
  }
  
  public int getCriteresCount() {
    int size = 0;
    if( listeCriteres != null )
      size = listeCriteres.size();
    return size;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    int size = listeCriteres.size(); 
    BasicType bt;
    for( int i=0; i<size; i++ ) {
      bt = (BasicType) listeCriteres.get( i ); 
      sb.append( bt.getName() + ":" + bt.getCode() + " - ");
    }
    return sb.toString();
  }
}
