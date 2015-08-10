/*
 * Created on 14 oct. 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.accor.asa.commun.hotel.metier.sl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author FCHIVAUX
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CriteresCapacitesRechercheHotels {

	private Map<String, Integer> listeCriteres;
  
  public static final String _NB_TOTAL = "HOTEL_RECH_NB_TOTAL_CHAMBRES";
  public static final String _NB_CHAMBRES_DOUBLES = "HOTEL_RECH_NB_CHAMBRES_DOUBLES"; 
  public static final String _NB_CHAMBRES_TRIPLES = "HOTEL_RECH_NB_CHAMBRES_TRIPLES";
  public static final String _NB_CHAMBRES_QUADS = "HOTEL_RECH_NB_CHAMBRES_QUADS";
  public static final String _NB_SALLES_REUNIONS = "HOTEL_RECH_NB_SALLES_REUNIONS";
  public static final String _NB_SALLES_SOUS_COMMM = "HOTEL_RECH_NB_SALLES_SOUS_COMM";
  public static final String _CAPACITE_SALLE_REUNION_PAX = "HOTEL_RECH_CAPACITE_SALLE_PAR_PAX";
  public static final String _CAPACITE_SALLE_REUNION_M2 = "HOTEL_RECH_CAPACITE_SALLE_PAR_M2";
  public static final String _NB_PLACES_RESTAURANT = "HOTEL_RECH_NB_PLACES_RESTAURANT";
  
  public CriteresCapacitesRechercheHotels() {
   listeCriteres = new HashMap<String, Integer>(); 
  }

  public void addCritere( String name, Integer value ) {
   listeCriteres.put( name, value ); 
  }
  
  public Set<String> getCritereName() {
	Set<String> listeCriteresNames = null;
    if( listeCriteres != null && listeCriteres.size() > 0 )
      listeCriteresNames = listeCriteres.keySet();
    return listeCriteresNames;
  }
  
  public Object getCritere( String name ) {
    Object obj = null;
    if( listeCriteres != null && listeCriteres.size() > 0 )
      obj = listeCriteres.get( name ); 
    return obj;
  }

    public int getCritereCount() {
    int size = 0;
    if( listeCriteres != null )
    	size = listeCriteres.size();
    return size;
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    Iterator<String> it = getCritereName().iterator();
    if(it != null )
    	while( it.hasNext() ) {
    		String name = (String) it.next();
        sb.append( name + ":" + listeCriteres.get( name ) + " - " );
      }
    return sb.toString();
  }
}
